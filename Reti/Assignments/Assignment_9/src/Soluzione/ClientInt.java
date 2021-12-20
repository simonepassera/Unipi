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

public class ClientInt {
    // Dimensione del buffer di risposta.
    public static final int bufSize = 2048;

    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.err.println(
            "Esegui come: Client <hostname> <porta>");
            System.exit(1);
        }
        // Leggo il nome host, il numero di porta e il messaggio
        // da riga di comando.
        // Interpreto il messaggio come una sequenza di byte.
        String hostname = args[0];
        int port = Integer.parseInt(args[1]);
        // Alloco un buffer con la dimensione fissata.
        ByteBuffer buffer = ByteBuffer.allocate(bufSize);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            //
            System.out.println("Inserisci il messaggio:");
            String inputStr = scanner.nextLine();
            if (inputStr.equals("EXIT")) break;
            byte[] message = inputStr.getBytes();
            // Apro il SocketChannel per la comunicazione con il server.
            SocketChannel sc = SocketChannel.open(
            new InetSocketAddress(hostname, port));
            // Preparo il buffer per la scrittura.
            buffer.clear();
            // Scrivo la lunghezza e poi il messaggio nel buffer.
            buffer.putInt(message.length);
            buffer.put(message);
            // Preparo il buffer per la lettura.
            buffer.flip();
            // Leggo il messaggio dal buffer e lo invio al server.
            sc.write(buffer);
            // A questo punto devo attendere la risposta dal server.
            // Preparo il buffer per la scrittura.
            buffer.clear();
            // Leggo il messaggio dal canale e lo scrivo nel buffer.
            sc.read(buffer);
            // Preparo il buffer per la lettura.
            buffer.flip();
            int replyLength = buffer.getInt();
            byte[] replyBytes = new byte[replyLength];
            buffer.get(replyBytes);
            System.out.println("Ricevuto: " + new String(replyBytes));
            // Chiudo il canale.
            sc.close();
        }

    }
}
