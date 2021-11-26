/**
 *	Laboratorio di Reti A
 *	Soluzione del settimo assignment
 *	@author Matteo Loporchio
 *
 *  Questo file contiene una possibile implementazione del Client, funzionante
 *  a patto che il server invii le risposte ai pacchetti entro un tempo
 *  massimo di due secondi.
 */

import java.io.*;
import java.net.*;
import java.util.*;

public class Client {
    // Numero di richieste PING inviate dal client.
    public static final int n = 10;
    // Tempo di attesa massima prima di considerare un pacchetto come perso.
    public static final int timeout = 2000;
    // Dimensione del buffer.
    public static final int dimBuffer = 1024;
    // Parametri usati per il calcolo delle statistiche.
    public static long rttTot = 0;
    public static long rttMin = Long.MAX_VALUE;
    public static long rttMax = Long.MIN_VALUE;
    public static int ricevuti = 0;

    public static void main(String[] args) {
        // Leggo la porta e l'indirizzo del server da riga di comando.
        if (args.length < 2) {
            System.err.println("Esegui come: Client <indirizzo> <porta>");
            System.exit(1);
        }
        String address = args[0];
        int port = Integer.parseInt(args[1]);
        // Inizializzo la socket per inviare le richieste.
        try (DatagramSocket socket = new DatagramSocket()) {
            // Imposto il timeout sulla socket.
            socket.setSoTimeout(timeout);
            // Ottengo l'indirizzo del server.
            InetAddress addr = InetAddress.getByName(address);
            // Invio i pacchetti.
            for (int i = 1; i <= n; i++) {
                // Preparo il pacchetto di ping.
                long startTime = System.currentTimeMillis();
                String contentStr = String.format("PING %d %d", i, startTime);
                byte[] content = contentStr.getBytes();
                DatagramPacket pkt = new DatagramPacket(content,
                content.length, addr, port);
                // Invio il pacchetto.
                socket.send(pkt);
                System.out.printf("Inviato: id=%d\n", i);
                // A questo punto, devo attendere la risposta del server
                // per un massimo di 2 secondi.
                // Se la riposta non arriva, considero il pacchetto
                // come se fosse andato perso.
                byte[] buf = new byte[dimBuffer];
                DatagramPacket rpkt = new DatagramPacket(buf, buf.length);
                try {
                    socket.receive(rpkt);
                    // Dopo l'arrivo della risposta, aggiorno le statistiche
                    // sulla base del RTT dell'ultimo pacchetto.
                    long rtt = System.currentTimeMillis() - startTime;
                    rttTot += rtt;
                    rttMin = Long.min(rttMin, rtt);
                    rttMax = Long.max(rttMax, rtt);
                    ricevuti++;
                }
                catch (SocketTimeoutException e) {
                    // Se sono qui, significa che non ho ricevuto alcun
                    // pacchetto prima del timeout.
                    System.out.printf("Non ricevuto: id=%d\n", i);
                }
            }
            // A questo punto, ho terminato l'invio e la ricezione di tutti i
            // pacchetti e posso stampare le statistiche su schermo.
            printStats();
        }
        catch (Exception e) {
            System.out.printf("Errore: " + e.getMessage());
            System.exit(1);
        }
    }

    // Metodo per la stampa su schermo delle statistiche.
    private static void printStats() {
        System.out.printf("---- PING Statistics ----\n" +
        "%d packets transmitted, %d packets received, %d%% packet loss\n",
        n, ricevuti, 100*(n-ricevuti)/n);
        if (ricevuti != 0) {
            System.out.printf("round-trip (ms) min/avg/max = %d / %.2f / %d\n",
            rttMin, ((double) rttTot / (double) ricevuti), rttMax);
        }
    }
}
