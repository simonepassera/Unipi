/**
 *  Laboratorio di Reti A
 *  Soluzione dell'assignment
 *  @author Matteo Loporchio
 */

import java.io.*;
import java.net.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.*;

public class Server {
    // Dimensione del buffer di risposta.
    public static final int bufSize = 2048;

    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.err.println("Esegui come: Server <porta>");
            System.exit(1);
        }
        // Leggo il numero di porta da riga di comando.
        int port = Integer.parseInt(args[0]);
        // Apro il selettore e inizializzo il canale relativo
        // alla ServerSocket
        Selector selector = Selector.open();
        ServerSocketChannel serverSocket = ServerSocketChannel.open();
        serverSocket.bind(new InetSocketAddress("localhost", port));
        serverSocket.configureBlocking(false);
        serverSocket.register(selector, SelectionKey.OP_ACCEPT);
        // Alloco un buffer avente la dimensione fissata.
        ByteBuffer buffer = ByteBuffer.allocate(bufSize);
        System.out.printf("Server pronto su porta %d\n", port);
        while (true) {
            selector.select();
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> iter = selectedKeys.iterator();
            while (iter.hasNext()) {
                SelectionKey key = iter.next();
                // Controllo se sul canale associato alla chiave
                // c'è la possibilità di accettare una nuova connessione.
                if (key.isAcceptable()) {
                    // Accetto la connessione e registro il canale ottenuto
                    // sul selettore.
                    SocketChannel client = serverSocket.accept();
                    System.out.println("Nuova connessione ricevuta");
                    client.configureBlocking(false);
                    client.register(selector, SelectionKey.OP_READ);
                }
                // Se il canale associato alla chiave è leggibile,
                // allora procedo con l'invio del messaggio di risposta.
                if (key.isReadable()) sendEcho(key, buffer);
                iter.remove();
            }
        }
    }

    /**
     *  Metodo per l'invio del messaggio di risposta.
     */
    private static void sendEcho(SelectionKey key, ByteBuffer buffer)
    throws IOException {
        SocketChannel client = (SocketChannel) key.channel();
        // Come prima cosa, devo ricevere quello che mi è stato inviato
        // dal client. Leggo i dati dal canale e li scrivo nel buffer.
        buffer.clear();
        client.read(buffer);
        buffer.flip();
        // Ora estraggo il contenuto dal buffer. Il primo dato che ho
        // ricevuto è la lunghezza del messaggio.
        int receivedLength = buffer.getInt();
        // Quindi estraggo il messaggio vero e proprio.
        byte[] receivedBytes = new byte[receivedLength];
        buffer.get(receivedBytes);
        String receivedStr = new String(receivedBytes);
        System.out.println("Ricevuto: " + receivedStr);
        // A questo punto, preparo il messaggio di risposta,
        // concatenando la stringa "(echoed by server)" a quella ricevuta.
        String replyStr = new String(receivedStr + " (echoed by server)");
        byte[] replyBytes = replyStr.getBytes();
        buffer.clear(); // Preparo il buffer per la scrittura.
        // Scrivo nel buffer la lunghezza della risposta e poi la risposta
        // vera e propria.
        buffer.putInt(replyBytes.length);
        buffer.put(replyBytes);
        // Quindi scrivo il contenuto del buffer nel canale.
        buffer.flip(); // Preparo il buffer per la lettura.
        client.write(buffer);
        // Chiudo la connessione con il client.
        client.close();
    }

}
