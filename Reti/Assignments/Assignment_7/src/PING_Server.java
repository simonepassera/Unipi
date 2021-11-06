// @author Simone Passera

import java.net.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class PING_Server {
    // Numero di porta del server
    private static int port;
    // Tempo massimo di attesa prima di far terminare il server
    private static final int timeout = 30000;
    // Dimensione del buffer in cui memorizzare la risposta
    private static final int bufSize = 512;
    // Seed utilizzato in random per la generazione di latenze e perdita di pacchetti
    private static long seed;
    private static Random random;

    public static void main(String[] args) {
        if (args.length >= 1) {
            try {
                port = Integer.parseInt(args[0]);
                if (port < 0 || port > 65535) throw new NumberFormatException();

                random = new Random();

                if (args.length >= 2) {
                    try {
                        seed = Long.parseLong(args[1]);
                    } catch (NumberFormatException e) {
                        System.out.println("ERR -arg 2");
                        System.exit(1);
                    }

                    random.setSeed(seed);
                }
            } catch (NumberFormatException e) {
                System.out.println("ERR -arg 1");
                System.exit(1);
            }
        } else {
            System.out.println("Usage: java PING_Server <port> [seed]");
            System.exit(1);
        }

        DatagramSocket udpServerSocket;

        try {
            udpServerSocket = new DatagramSocket(port);
            System.out.println("Server running ...");

            udpServerSocket.setSoTimeout(timeout);

            byte[] buf = new byte[bufSize];
            DatagramPacket ping = new DatagramPacket(buf, buf.length);

            DatagramPacket echo;
            InetAddress client_addr;
            int client_port, delay;
            String data;

            while (true) {
                try {
                    udpServerSocket.receive(ping);
                } catch (SocketTimeoutException e) {
                    udpServerSocket.close();
                    System.out.println("Server closed!");
                    System.exit(0);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                    continue;
                }

                client_addr = ping.getAddress();
                client_port = ping.getPort();

                data = new String(ping.getData(), 0, ping.getLength(), StandardCharsets.US_ASCII);

                // Perdita del pacchetto con probabilità del 25%
                if(random.nextInt(100) < 25) {
                    System.out.println("Client ip=" + client_addr + " port=" + client_port + " \"" + data + "\" " + "PING non inviato");
                    continue;
                } else {
                    delay = random.nextInt(2000);

                    try {
                        Thread.sleep(delay);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }

                echo = new DatagramPacket(ping.getData(), ping.getLength(), client_addr, client_port);

                try {
                    udpServerSocket.send(echo);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }

                System.out.println("Client ip=" + client_addr + " port=" + client_port + " \"" + data + "\" " + "PING ritardato di " + delay + " ms");
            }
        } catch (SocketException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
}
