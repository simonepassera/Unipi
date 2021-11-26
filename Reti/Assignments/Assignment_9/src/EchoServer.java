import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class EchoServer {
    private static int port = 9999;
    private static int bufSize = 2048;

    public static void main(String[] args) {
        if (args.length >= 1) {
            try {
                port = Integer.parseInt(args[0]);
                if (port < 0 || port > 65535) throw new NumberFormatException();
            } catch (NumberFormatException e) {
                System.err.println("Usage: java EchoServer <port>");
                System.exit(1);
            }
        }

        ServerSocketChannel serverChannel = null;
        Selector selector = null;

        try {
            serverChannel = ServerSocketChannel.open();
            serverChannel.configureBlocking(false);

            ServerSocket serverSocket = serverChannel.socket();
            serverSocket.bind(new InetSocketAddress(port));

            selector = Selector.open();
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }

        Set<SelectionKey> readyKeys;
        Iterator<SelectionKey> iterator;
        SelectionKey key;
        SocketChannel clientChannel;

        System.out.println("Server runnning ...");

        while (true) {
            try {
                selector.select();
            } catch (IOException e) {
                System.err.println(e.getMessage());
                System.exit(1);
            }

            readyKeys = selector.selectedKeys();
            iterator = readyKeys.iterator();

            while (iterator.hasNext()) {
                key = iterator.next();

                try {
                    if (key.isAcceptable()) {
                        clientChannel = serverChannel.accept();
                        System.out.println("Accepted connection from " + clientChannel.getRemoteAddress());

                        clientChannel.configureBlocking(false);

                        ByteBuffer attachBuf = ByteBuffer.allocate(bufSize);
                        attachBuf.clear();

                        clientChannel.register(selector, SelectionKey.OP_WRITE | SelectionKey.OP_READ, attachBuf);
                    }

                    if (key.isReadable()) {
                        clientChannel = (SocketChannel) key.channel();
                        ByteBuffer receiveBuf = (ByteBuffer) key.attachment();

                        if (receiveBuf.remaining() == bufSize) {
                            clientChannel.read(receiveBuf);
                        }
                    }

                    if (key.isWritable()) {
                        clientChannel = (SocketChannel) key.channel();
                        ByteBuffer sendBuf = (ByteBuffer) key.attachment();

                        if (sendBuf.remaining() != bufSize) {
                            sendBuf.put(" - echoed by server".getBytes(StandardCharsets.US_ASCII));
                            sendBuf.flip();

                            clientChannel.write(sendBuf);
                            sendBuf.clear();
                        }
                    }
                } catch (IOException e) {
                    try {
                        key.channel().close();
                    } catch (IOException cex) {}

                    key.cancel();
                }

                iterator.remove();
            }
        }
    }
}