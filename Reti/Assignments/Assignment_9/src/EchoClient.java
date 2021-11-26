import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class EchoClient {
    private static int port = 9999;
    private static int bufSize = 2048;
    private static InetAddress addr;

    public static void main(String[] args) {
        if (args.length >= 1) {
            try {
                addr = InetAddress.getByName(args[0]);

                if (args.length >= 2) {
                    port = Integer.parseInt(args[1]);
                    if (port < 0 || port > 65535) throw new NumberFormatException();
                }
            } catch (UnknownHostException e) {
                System.out.println("No IP address for the host could be found!");
                System.exit(1);
            } catch (NumberFormatException e) {
                System.out.println(e.getMessage());
                System.exit(1);
            }
        } else {
            System.err.println("Usage: java EchoClient hostname <port>");
            System.exit(1);
        }

        try (Scanner in = new Scanner(System.in); SocketChannel clientChannel = SocketChannel.open(new InetSocketAddress(addr, port))) {
            ByteBuffer sendBuf = ByteBuffer.allocate(bufSize);
            String line;

            System.out.println("Connected to " +clientChannel.getRemoteAddress());

            while (true)
            {
                sendBuf.clear();
                System.out.print("Send: ");

                line = in.nextLine();

                if (line.equals("exit")) {
                    break;
                }

                sendBuf.put(line.getBytes(StandardCharsets.US_ASCII));
                sendBuf.flip();
                clientChannel.write(sendBuf);
                sendBuf.clear();
                clientChannel.read(sendBuf);
                sendBuf.flip();

                System.out.println("Receive: " + new String(sendBuf.array(), StandardCharsets.US_ASCII));
            }

            System.out.println("Connection close!");
        } catch(IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}
