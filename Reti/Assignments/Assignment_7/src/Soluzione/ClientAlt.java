/**
 *	Laboratorio di Reti A
 *	Soluzione del settimo assignment
 *	@author Matteo Loporchio
 *
 *  Questo file contiene un'implementazione alternativa del client.
 *  In questo caso, assumiamo che il server possa inviare un pacchetto
 *  di risposta dopo un qualsiasi numero di secondi. Il client in attesa
 *  di un pacchetto con identificativo i scarterà tutti i pacchetti
 *  con identificativo j < i. Si noti che il tempo massimo di attesa
 *  complessivo per il pacchetto i è sempre pari a 2 secondi,
 *  indipendentemente dal fatto che l'attesa possa essere
 *  interrotta dalla ricezione di altri pacchetti.
 */

import java.io.*;
import java.net.*;
import java.util.*;

public class ClientAlt {
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
    // Riferimento al thread listener che attende le risposte del server.
    public static Thread listener = null;
    // Monitor "temporizzato" per la sincronizzazione fra main e listener.
    public static PacketMonitor monitor = new PacketMonitor();

    public static void main(String[] args) {
        // Leggo la porta e l'indirizzo del server da riga di comando.
        if (args.length < 2) {
            System.err.println("Esegui come: Client <indirizzo> <porta>");
            System.exit(1);
        }
        String address = args[0];
        int port = Integer.parseInt(args[1]);
        // Inizializzo la socket per inviare le richieste.
        // NOTA: quando la socket viene chiusa (in automatico, dato che
        // ho usato il costrutto try-with-resources), il thread listener
        // bloccato sulla receive() gestisce la IOException che viene
        // sollevata in modo tale da terminare.
        try (DatagramSocket socket = new DatagramSocket()) {
            // Ottengo l'indirizzo del server.
            InetAddress addr = InetAddress.getByName(address);
            // Credo e avvio il thread listener che attende pacchetti.
            listener = new Thread(new Listener(socket, monitor));
            listener.start();
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
                // per un massimo di 2 secondi. Uso il metodo `check`
                // del monitor per controllare se arriva il pacchetto.
                if (monitor.check(i, timeout) == i) {
                    long rtt = System.currentTimeMillis() - startTime;
                    rttTot += rtt;
                    rttMin = Long.min(rttMin, rtt);
                    rttMax = Long.max(rttMax, rtt);
                    ricevuti++;
                }
                // Se sono qui, significa che non ho ricevuto il pacchetto
                // che stavo aspettando prima del timeout.
                else System.out.printf("Non ricevuto: id=%d\n", i);
            }
            // A questo punto, ho terminato l'invio e la ricezione di tutti i
            // pacchetti e posso stampare le statistiche su schermo.
            printStats();
        }
        catch (Exception e) {
            System.out.printf("Errore: " + e.getMessage());
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

/**
 *  Questa classe rappresenta un monitor utilizzato dal thread main
 *  e dal listener per la sincronizzazione. Il monitor contiene
 *  l'identificativo dell'ultimo pacchetto ricevuto. Tale identificativo
 *  viene aggiornato dal listener a mano a mano che arrivano i pacchetti
 *  e letto dal main per capire se l'ultimo pacchetto spedito ha ricevuto
 *  un riscontro da parte del server.
 */
class PacketMonitor {
    // Identificativo dell'ultimo pacchetto ricevuto.
    private int latest = 0;

    /**
     *  Metodo che aggiorna lo stato interno del monitor
     *  con un nuovo identificativo di pacchetto.
     *  Al momento dell'aggiornamento, viene risvegliato
     *  uno dei thread in attesa, in modo da segnalare l'arrivo di un
     *  nuovo pacchetto.
     *
     *  @param id l'identificativo del nuovo pacchetto
     */
    public synchronized void update(int id) {
        latest = id;
        notify();
    }

    /**
     *  Metodo che consente di attendere l'arrivo di un pacchetto
     *  con un dato identificativo per un certo intervallo di tempo.
     *  Al termine dell'attesa, viene restituito l'identificativo
     *  dell'ultimo pacchetto arrivato, che può non coincidere con
     *  quello che stavo aspettando.
     *
     *  @param id l'identificativo del pacchetto atteso
     *  @param timeout intervallo di tempo massimo per l'attesa
     *  @return l'identificativo dell'ultimo pacchetto arrivato
     */
    public synchronized int check(int id, int timeout)
    throws InterruptedException {
        // Determino l'istante di tempo in cui finirò di attendere.
        long expiration = System.currentTimeMillis() + timeout;
        while (latest != id) {
            // Controllo quanto mi rimane da aspettare.
            long remaining = expiration - System.currentTimeMillis();
            // Se il valore dei millisecondi da attendere è <= 0,
            // significa che il tempo massimo è scaduto.
            if (remaining <= 0) break;
            // Altrimenti, mi metto in attesa sul monitor per il
            // tempo che mi rimane, usando una wait() con un timeout.
            // Si tratta di una variante della wait() vista a lezione.
            // Come al solito, se un thread mi risveglia con una notify(),
            // controllo la guardia del while e verifico se è arrivato
            // il pacchetto che aspettavo. In tal caso, posso terminare.
            wait(remaining);
        }
        // In ogni caso, restituisco comunque l'identificativo
        // dell'ultimo pacchetto arrivato.
        return latest;
    }
}

/**
 *  Implementazione del thread listener che attende i pacchetti
 *  in arrivo dal server in risposta ai messaggi di ping.
 */
class Listener implements Runnable {
    // Dimensione del buffer usato dal listener per la ricezione di pacchetti.
    public final int dimBuffer = 256;
    // Socket per la comunicazione con il server.
    private DatagramSocket socket;
    // Riferimento al monitor condiviso con il thread main.
    private PacketMonitor monitor;

    /**
     *  Costruttore della classe Listener.
     *  @param socket socket per la comunicazione con il server
     *  @param monitor riferimento al monitor
     */
    public Listener(DatagramSocket socket, PacketMonitor monitor) {
        this.socket = socket;
        this.monitor = monitor;
    }

    /**
     *  Metodo che implementa la logica del thread listener.
     *  Il thread resta costantemente in attesa di pacchetti
     *  da parte del server e aggiorna di conseguenza lo stato del monitor.
     */
    public void run() {
        System.out.println("Listener avviato");
        while (true) {
            byte[] buf = new byte[dimBuffer];
            DatagramPacket rpkt = new DatagramPacket(buf, buf.length);
            try {socket.receive(rpkt);}
            // Questa eccezione viene sollevata quando la socket
            // viene chiusa (dall'esterno). Oltre al timeout,
            // è l'unico modo per sbloccare un thread bloccato
            // sulla receive().
            catch (IOException e) {break;}
            // Ricavo l'identificativo del pacchetto appena arrivato.
            int rid = Integer.parseInt(((new String(rpkt.getData())).split(" "))[1]);
            // Aggiorno lo stato del monitor segnalando al thread in attesa
            // l'arrivo del pacchetto con questo identificativo.
            monitor.update(rid);
        }
        System.out.println("Listener terminato");
    }
}
