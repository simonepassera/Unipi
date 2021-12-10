// @Author Simone Passera

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class TimeClient {
    private static int port = 9999;

    public static void main(String[] args) {

        InetAddress dategroup = null;

        if (args.length >= 1) {
            try {
                dategroup = InetAddress.getByName(args[0]);
                if (!dategroup.isMulticastAddress()) throw new UnknownHostException();

                if (args.length >= 2) {
                    port = Integer.parseInt(args[1]);
                    if (port < 0 || port > 65535) throw new NumberFormatException();
                }
            } catch (UnknownHostException | NumberFormatException e) {
                System.err.println("Usage: java WelcomeClient <multicast_address> <port>");
                System.exit(1);
            }
        } else {
            try {
                dategroup = InetAddress.getByName("239.255.1.3");
            }  catch (UnknownHostException e) {}
        }

        try (MulticastSocket ms = new MulticastSocket(port)) {
            ms.joinGroup(dategroup);

            byte[] buf = new byte[1024];
            DatagramPacket timeUpdate = new DatagramPacket(buf, buf.length);

            for (int i = 0; i < 10; i++) {
                ms.receive(timeUpdate);
                System.out.println("Receive : " + new String(timeUpdate.getData(), StandardCharsets.US_ASCII));
            }

            ms.leaveGroup(dategroup);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}
