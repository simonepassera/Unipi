import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class Client {
    private final static int PORT = 9999;

    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setSoTimeout(15000);
            InetAddress host = InetAddress.getByName("localhost");
            DatagramPacket request = new DatagramPacket(new byte[1], 1, host, PORT);
            DatagramPacket response = new DatagramPacket(new byte[4], 4);
            socket.send(request);
            socket.receive(response);
            String ping_response = new String(response.getData(), 0, response.getLength(), StandardCharsets.UTF_8);
            System.out.println(ping_response);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
