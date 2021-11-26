/**
 *	Laboratorio di Reti A
 *	Soluzione del settimo assignment
 *	@author Matteo Loporchio
 */

import java.io.*;
import java.net.*;
import java.util.*;

public class Worker implements Runnable {
    // Dimensione del buffer usato per l'invio dei pacchetti.
    public final int dimBuffer = 1024;
    // Tempo massimo di attesa prima di inviare una risposta.
    public final int attesaMax = 2000;
    // Socket per comunicare con il client.
    private DatagramSocket socket;
    // Pacchetto ricevuto dal client.
    private DatagramPacket packet;
    // Generatore di numeri (pseudo)casuali.
    private Random gen;

    /**
     *  Costruttore della classe Worker.
     *  @param socket la socket per comunicare con il client
     *  @param packet il pacchetto ricevuto dal client
     *  @param seed il seme per il generatore (pseudo)casuale
     */
    public Worker(DatagramSocket socket, DatagramPacket packet, long seed) {
        this.socket = socket;
        this.packet = packet;
        this.gen = new Random(seed);
    }

    /**
     *  Metodo run contenente la logica del thread Worker.
     */
    public void run() {
        // Estraggo l'identificativo associato al pacchetto ricevuto.
        int id = Integer.parseInt(new String(packet.getData()).split(" ")[1]);
        // Decido se scartare o tenere il pacchetto. Per farlo, genero
        // un intero nell'intervallo [1, 100] e controllo se è minore o
        // uguale a 25. In caso affermativo, scarto il pacchetto.
        if (gen.nextInt(100) + 1 <= 25) {
            System.out.printf("Scartato: id=%d\n", id);
            return;
        }
        // A questo punto, decido quanto attendere prima di inviare il
        // pacchetto di risposta al client. Scelgo un tempo casuale
        // nell'intervallo [0, 100].
        long attesa = gen.nextInt(attesaMax + 1);
        try {Thread.sleep(attesa);}
        catch (InterruptedException e) {
            System.err.println("Interruzione durante l'attesa");
            return;
        }
        // Preparo il pacchetto di risposta e lo invio.
        byte[] content = (new String("PONG " + id + " "
        + System.currentTimeMillis())).getBytes();
        DatagramPacket reply = new DatagramPacket(content, content.length,
        packet.getAddress(), packet.getPort());
        try {
            socket.send(reply);
            System.out.printf("Inviato: id=%d dopo %d ms\n", id, attesa);
        }
        catch (IOException e) {
            System.err.println("Errore di I/O: " + e.getMessage());
        }
    }
}
