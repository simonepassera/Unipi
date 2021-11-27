import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class WelcomeServer {
    private static int port = 9999;

    public static void main(String[] args) {

        InetAddress ip_group = null;

        if (args.length >= 1) {
            try {
                ip_group = InetAddress.getByName(args[0]);
                if (!ip_group.isMulticastAddress()) throw new UnknownHostException();

                if (args.length >= 2) {
                    port = Integer.parseInt(args[1]);
                    if (port < 0 || port > 65535) throw new NumberFormatException();
                }
            } catch (UnknownHostException | NumberFormatException e) {
                System.err.println("Usage: java WelcomeServer <multicast_address> <port>");
                System.exit(1);
            }
        } else {
            try {
                ip_group = InetAddress.getByName("239.255.1.3");
            }  catch (UnknownHostException e) {}
        }

        try {
            MulticastSocket ms = new MulticastSocket();
            ms.setTimeToLive(1);
            ms.joinGroup(ip_group);

            byte[] buf = "Welcome".getBytes(StandardCharsets.US_ASCII);

            DatagramPacket welcomeMessage = new DatagramPacket(buf, buf.length, ip_group, port);

            System.out.println("Server running ...");

            while (true) {
                ms.send(welcomeMessage);
                Thread.sleep(2000);
            }
        } catch (IOException | InterruptedException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}
