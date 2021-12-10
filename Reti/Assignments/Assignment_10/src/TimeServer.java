// @Author Simone Passera

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class TimeServer {
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
                System.err.println("Usage: java TimerServer <dategroup_address> <port>");
                System.exit(1);
            }
        } else {
            try {
                dategroup = InetAddress.getByName("239.255.1.3");
            }  catch (UnknownHostException e) {}
        }

        try {
            MulticastSocket ms = new MulticastSocket();
            ms.setTimeToLive(1);
            ms.joinGroup(dategroup);

            byte[] buf;
            DatagramPacket timeUpdate;

            System.out.println("Server running ...");

            while (true) {
                buf = new Date().toString().getBytes(StandardCharsets.US_ASCII);
                timeUpdate = new DatagramPacket(buf, buf.length, dategroup, port);

                ms.send(timeUpdate);
                Thread.sleep(2000);
            }
        } catch (IOException | InterruptedException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}
