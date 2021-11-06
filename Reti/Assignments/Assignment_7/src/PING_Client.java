// @author Simone Passera

import java.net.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class PING_Client {
    // Numero di porta del server
    private static int port;
    // Indirizzo del server
    private static InetAddress addr;
    // Hostname del server
    private static String hostname;
    // Dimensione del buffer per i pacchetti di risposta
    private static final int bufSize = 512;
    // Tempo massimo di attesa per la risposta del server
    private static final int timeout = 2000;
    // Messaggi inviati
    private static final int msg = 10;

    public static void main(String[] args) {
        if (args.length >= 2) {
            try {
                hostname = args[0];
                addr = InetAddress.getByName(hostname);
                port = Integer.parseInt(args[1]);
                if (port < 0 || port > 65535) throw new NumberFormatException();
            } catch (UnknownHostException e) {
                System.out.println("ERR -arg 1");
                System.exit(1);
            } catch (NumberFormatException e) {
                System.out.println("ERR -arg 2");
                System.exit(1);
            }
        } else {
            System.out.println("Usage: java PING_Client <hostname> <port>");
            System.exit(1);
        }

        try {
            // Inizializzo la socket per inviare i messaggi
            DatagramSocket udpSocket = new DatagramSocket();
            udpSocket.setSoTimeout(timeout);

            byte[] buf = new byte[bufSize];
            DatagramPacket echo = new DatagramPacket(buf, buf.length);

            DatagramPacket ping;
            String packet_data;
            byte[] packet_data_array;
            long currentTime, min = timeout, max = 0;
            long[] timeArray = new long[msg];
            int packets_trasmitted = 0, received = 0;

            for (int i = 0; i < msg; i++) {
                currentTime = System.currentTimeMillis();
                packet_data = "PING " + i + " " + currentTime;
                packet_data_array = packet_data.getBytes(StandardCharsets.US_ASCII);

                ping = new DatagramPacket(packet_data_array, packet_data_array.length, addr, port);

                try {
                    udpSocket.send(ping);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                    continue;
                }

                packets_trasmitted++;

                try {
                    udpSocket.receive(echo);
                } catch (SocketTimeoutException e) {
                    System.out.println("PING seq=" + i + " *");
                    continue;
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                    continue;
                }

                timeArray[i] = System.currentTimeMillis() - currentTime;

                if(!new String(echo.getData(), 0, echo.getLength(), StandardCharsets.US_ASCII).equals(packet_data)) {
                    timeArray[i] = 0;
                    System.out.println("PING seq=" + i + " *");
                    continue;
                }

                received++;

                if (timeArray[i] > max) max = timeArray[i];
                if (timeArray[i] < min) min = timeArray[i];

                System.out.println("PING seq=" + i + " time=" + timeArray[i] + " ms");
            }

            udpSocket.close();

            System.out.println("--- " + hostname + " ping statistics ---");

            if (received == 0) min = 0;

            long sum = 0;

            for (long t : timeArray) {
                sum += t;
            }

            double avg = (double) sum / received;

            System.out.print(packets_trasmitted + " packets trasmitted, " + received + " received, " + ((float)(packets_trasmitted - received) / packets_trasmitted) * 100 + "% packet loss, round-trip (ms) min/avg/max = " + min + "/");
            System.out.printf("%.2f/%d\n", avg, max);
        } catch (SocketException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
}
