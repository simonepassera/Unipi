import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Server {
    public static int PORT = 6789;
    public static String BASE_PATH = "/home/simo/Git/Unipi/Reti/Assignments/Assignment_6/src/html/";

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(PORT)) {
            System.out.println("Server is running ...");
            System.out.println("HTTP GET requests -> http://localhost:6789/");

            while (true) {
                try (Socket connection = server.accept(); BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String request = in.readLine();

                    if(request == null) continue;

                    StringTokenizer tokenizer = new StringTokenizer(request, " ");
                    ArrayList<String> requestArray = new ArrayList<>();

                    while(tokenizer.hasMoreTokens()) {
                        requestArray.add(tokenizer.nextToken());
                    }

                    OutputStream out = connection.getOutputStream();

                    if(!requestArray.get(0).equals("GET")) {
                        String err = "HTTP/1.1 405 Method Not Allowed\r\n";

                        try {
                            out.write(err.getBytes());
                            out.flush();
                            out.close();
                        } catch (IOException e) {
                            System.err.println(e.getMessage());
                        }

                        continue;
                    }

                    if(requestArray.get(1).equals("/")) {
                        requestArray.add(1, "index.html");
                    }

                    request = BASE_PATH + requestArray.get(1);

                    File requestFile = new File(request);
                    FileInputStream fileInputStream;

                    try {
                        fileInputStream = new FileInputStream(requestFile);
                    } catch (FileNotFoundException e) {
                        String err = "HTTP/1.1 404 Not Found\r\n" + "Server: FileServerHTTP\r\n" + "Content-Type: text/html\r\n\r\n" + "<html><body style=\"font-size: 2em;display: flex;height: 100vh;justify-content: center;align-items: center;background: #34495e;color: #ecf0f1;\"><h1>ERROR 404</h1></body></html>";

                        try {
                            out.write(err.getBytes());
                            out.flush();
                            out.close();
                        } catch (IOException ex) {
                            System.err.println(ex.getMessage());
                        }

                        continue;
                    }

                    int index = requestArray.get(1).indexOf(".");
                    String extension = requestArray.get(1).substring(index);

                    StringBuilder responseHeaders =  new StringBuilder( "HTTP/1.1 200 OK\r\n" + "Server: FileServerHTTP\r\n");

                    switch (extension) {
                        case ".txt":
                            responseHeaders.append("Content-Type: text/plain\r\n");
                            break;
                        case ".png":
                            responseHeaders.append("Content-Type: image/png\r\n");
                            break;
                        case ".html":
                            responseHeaders.append("Content-Type: text/html\r\n");
                            break;
                    }

                    responseHeaders.append("\r\n");

                    try {
                        byte[] responseBody = new byte[(int) requestFile.length()];

                        if(fileInputStream.read(responseBody) == -1) {
                            String err = "HTTP/1.1 500 Internal Server Error\r\n";

                            try {
                                fileInputStream.close();
                                out.write(err.getBytes());
                                out.flush();
                                out.close();
                            } catch (IOException ex) {
                                System.err.println(ex.getMessage());
                            }

                            continue;
                        }

                        fileInputStream.close();

                        out.write(responseHeaders.toString().getBytes());
                        out.write(responseBody);
                        out.flush();
                        out.close();
                    } catch (IOException e) {
                        System.err.println(e.getMessage());
                    }
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}
