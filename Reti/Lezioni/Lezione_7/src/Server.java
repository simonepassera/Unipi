import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.StandardCharsets;

public class Server {
    private final static int PORT = 9999;

    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket(PORT)) {
            while (true) {
                try {
                    DatagramPacket request = new DatagramPacket(new byte[4], 4);
                    socket.receive(request);

                    System.out.println("Ricevuto un pacchetto da " + request.getAddress() + " " + request.getPort());

                    String pong_response = "Pong";
                    byte[] data = pong_response.getBytes(StandardCharsets.UTF_8);

                    DatagramPacket response = new DatagramPacket(data, data.length, request.getAddress(), request.getPort());

                    socket.send(response);
                } catch (IOException e) {
                    e.getMessage();
                }
            }
        } catch (IOException e) {
            e.getMessage();
        }
    }
}
