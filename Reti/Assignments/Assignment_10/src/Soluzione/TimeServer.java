/**
 *  Laboratorio di Reti A
 *  Soluzione dell'assignment
 *  @author Matteo Loporchio
 */

import java.io.*;
import java.net.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class TimeServer {
    // Intervallo di tempo (in ms) fra l'invio di un messaggio e l'altro.
    public static final long waitDelay = 2000;

    public static void main(String[] args) {
        // Leggo i parametri da riga di comando.
        if (args.length < 2) {
            System.err.println("Esegui come: TimeServer <indirizzo> <porta>");
            System.exit(1);
        }
        String address = args[0];
        int port = Integer.parseInt(args[1]);
        // Creo una DatagramSocket per l'invio dei pacchetti.
        try (DatagramSocket socket = new DatagramSocket()) {
            // Ottengo l'indirizzo del gruppo e ne controllo la validità.
            InetAddress group = InetAddress.getByName(address);
            if (!group.isMulticastAddress()) {
                throw new IllegalArgumentException(
                "Indirizzo multicast non valido: " + group.getHostAddress());
            }
            // Entro in un ciclo infinito in cui invio la data
            // ad intervalli regolari.
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            while (true) {
                String message = df.format(
                new Date(System.currentTimeMillis()));
                byte[] content = message.getBytes();
                DatagramPacket packet = new DatagramPacket(content,
                content.length, group, port);
                // Invio il pacchetto.
                socket.send(packet);
                System.out.println("Server: " + message);
                // Attendo `waitDelay` millisecondi.
                Thread.sleep(waitDelay);
            }
        }
        catch (Exception e) {
            System.err.println("Errore server: " + e.getMessage());
        }
    }
}
