/**
 *	Laboratorio di Reti A
 *	Soluzione del sesto assignment
 *	@author Matteo Loporchio
 */

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import java.net.URLDecoder;

public class HTTPServer {
	// Tempo massimo di attesa per la terminazione del server.
	public static final int terminationDelay = 60000;
	// Pool di thread per servire le richieste in arrivo.
	public static ExecutorService pool = Executors.newCachedThreadPool();
	// ServerSocket usata per ricevere le richieste dai client.
	public static ServerSocket serverSocket = null;

	public static void main(String[] args) {
		// Controllo che sia stato specificato il numero di porta.
		if (args.length < 1) {
			System.err.println("Esegui come: HTTPServer <porta>");
			System.exit(1);
		}
		// Leggo il numero di porta.
		int port = Integer.parseInt(args[0]);
		try {
			// Creo e inizializzo la ServerSocket.
			serverSocket = new ServerSocket(port);
			serverSocket.setSoTimeout(terminationDelay);
			System.out.printf("Server pronto su porta %d\n", port);
			while (true) {
				// Mi metto in attesa di richieste di connessione.
				Socket sk = serverSocket.accept();
				pool.execute(new Manager(sk));
			}
		}
		// Con questo blocco, intercetto la SocketTimeoutException 
		// sollevata nel momento in cui scade il tempo massimo di attesa
		// per la ServerSocket. In questo modo, posso uscire dal
		// ciclo while in cui sto aspettando richieste di connessione.
		catch (SocketTimeoutException e) {
			System.out.println("Server in chiusura!");
		}
		catch (Exception e) {
			System.err.println("Errore nel main: " + e.getMessage());
		}
		finally {
			// Chiudo la socket 
			// (e smetto di accettare nuove connessioni).
			try {serverSocket.close();}
			catch (IOException e) {
				System.err.println(
				"Errore nella chiusura della socket!");
			}
			// Faccio terminare il pool di thread.
			pool.shutdown();
			try {
				if (!pool.awaitTermination(terminationDelay, 
				TimeUnit.MILLISECONDS)) pool.shutdownNow();
			}
			catch (InterruptedException e) {pool.shutdownNow();}
			System.out.println("Server terminato!");
		}
	}
}

/**
 *	Thread dedicato alla gestione delle richieste da parte dei client.
 */
class Manager implements Runnable {
	// Riferimento alla socket usata per comunicare con il client.
	private Socket sk;
	// Costruttore della classe Manager (prende in input il riferimento
	// alla socket del client).
	public Manager(Socket sk) {this.sk = sk;}
	// Metodo run contenente la logica del thread.
	public void run() {
		try {
			// Inizializzo gli stream di input e di output.
			BufferedReader in = new BufferedReader(
			new InputStreamReader(sk.getInputStream()));
			DataOutputStream out = new DataOutputStream(
			sk.getOutputStream());
			// Leggo la richiesta e controllo la prima riga.
			// Mi aspetto qualcosa tipo: "GET /nomefile.ext HTTP/1.1".
			String firstLine = in.readLine();
			// Controllo se si tratta di una richiesta di tipo GET.
			StringTokenizer tkLine = new StringTokenizer(firstLine);
			if (!tkLine.nextToken().equals("GET")) {
				System.err.println("Errore: richiesta non valida!");
				sk.close();
				return;
			}
			// Leggo il nome del file e lo decodifico. 
			// Il nome del file nella richiesta e' codificato
			// nel formato "URL Encoding". 
			// (Vedi: https://www.w3schools.com/tags/ref_urlencode.ASP)
			// Grazie allo studente che mi ha fornito la soluzione!
			String filename = tkLine.nextToken();
			if (filename.startsWith("/")) 
				filename = filename.substring(1);
			filename = java.net.URLDecoder.decode(filename, 
			StandardCharsets.UTF_8);
			System.out.printf("Richiesto il file %s\n", filename);
			// Provo ad aprire il file.
			File f = new File(filename);
			if (!f.exists() || f.isDirectory()) {
				String message = String.format(
				"Errore: file %s non valido!\r\n", filename);
				System.err.print(message);
				sendReply(out, "404 Not Found", 
				"text/plain", message.getBytes());
				sk.close();
				return;
			}
			// A questo punto, identifico il tipo MIME del file,
			// leggo il suo contenuto e lo invio tramite la socket. 
			// I dati che invio devono essere formattati come
			// una risposta HTTP.
			String mimeType = Files.probeContentType(f.toPath());
			FileInputStream is = new FileInputStream(f);
			int length = (int) f.length();
			byte[] buf = new byte[length];
			is.read(buf);
			is.close();
			sendReply(out, "200 OK", mimeType, buf);
			System.out.printf("File %s (MIME: %s) inviato\n", 
			filename, mimeType);
			sk.close();
		}
		catch (Exception e) {
			System.err.println("Errore nel manager: " + e.getMessage());
		}
	}

	/**
	 *	Metodo che invia una risposta HTTP al client.
	 *	@param os stream di output su cui inviare la risposta
	 *	@param statusCode il codice HTTP associato alla risposta
	 *	@param contentType il tipo MIME associato al contenuto
	 *	@param content array con il contenuto da inviare al client
	 */
	private void sendReply(DataOutputStream out, String statusCode,
	String contentType, byte[] content) throws IOException {
		out.writeBytes("HTTP/1.1 " + statusCode + "\r\n");
		out.writeBytes("Content-Type: " + 
		((contentType != null) ? contentType : "text/plain") + "\r\n");
		out.writeBytes("Content-Length: " + 
		((content != null) ? content.length : 0) + "\r\n"); 
		out.writeBytes("\r\n");	
		if (content != null) out.write(content, 0, content.length);
	}
}
