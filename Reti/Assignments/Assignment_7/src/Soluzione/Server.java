/**
 *	Laboratorio di Reti A
 *	Soluzione del settimo assignment
 *	@author Matteo Loporchio
 */

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class Server {
    // Timeout della socket.
    public static final int timeout = 60000;
    // Dimensione del buffer per la ricezione dei pacchetti.
    public static final int dimBuffer = 1024;
    // Pool di thread per servire le richieste in arrivo.
    public static ExecutorService pool = Executors.newCachedThreadPool();

    public static void main(final String[] array) {
        // Leggo i parametri (porta, seed) da riga di comando.
        if (array.length < 2) {
            System.err.println("Esegui come: Server <porta> <seme>");
            System.exit(1);
        }
        final int port = Integer.parseInt(array[0]);
        final long seed = Long.parseLong(array[1]);
        // Apro la socket per la comunicazione.
        try (DatagramSocket socket = new DatagramSocket(port)) {
            // Imposto il timeout sulla socket.
            socket.setSoTimeout(timeout);
            // Inizializzo il generatore di numeri casuali.
            Random random = new Random(seed);
            System.out.printf("Server: pronto sulla porta %s\n", port);
            // Entro in un ciclo infinito in cui ricevo un pacchetto
            // dal client e lo gestisco in un thread apposito.
            while (true) {
                byte[] buf = new byte[dimBuffer];
                DatagramPacket pkt = new DatagramPacket(buf, buf.length);
                socket.receive(pkt);
                pool.execute(new Worker(socket, pkt, random.nextLong()));
            }
        }
        catch (SocketTimeoutException e) {
            System.out.println("Server: terminazione avviata...");
        }
        catch (SocketException e) {
            System.err.println("Server: errore nell'inizializzazione della socket");
        }
        catch (IOException e) {
            System.err.println("Server: errore di I/O");
        }
        // Faccio terminare il pool di thread.
        finally {pool.shutdown();}
    }
}
