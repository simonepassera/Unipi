/**
 *	Laboratorio di Reti A
 *	Soluzione dell'esercizio del 19/10/2021
 *	@author Matteo Loporchio
 */

import java.io.*;
import java.net.*;

public class Server {
	// Nome del file da inviare al client.
	public static final String filename = "content.txt";
	// Porta associata alla socket.
	public static final int port = 9999;
	// Timeout per le connessioni in arrivo.
	// Dopo questo intervallo di tempo, il server smette di attendere
	// nuove richieste di connessione da parte di eventuali client.
	public static final int timeout = 10000;
	// Socket per l'interazione fra client e server.
	public static ServerSocket serverSocket = null;
	// Array per memorizzare il contenuto del file da inviare.
	public static byte[] content = null;
	
	public static void main(String[] args) {
		// Inizializzo la ServerSocket su cui ricevere connessioni.
		// Imposto un timeout per fare terminare il server
		// nel caso in cui non si ricevano richieste per un
		// determinato intervallo di tempo.
		try {
			serverSocket = new ServerSocket(port);
			serverSocket.setSoTimeout(timeout);
		}
		catch (Exception e) {
			System.err.println(
			"Errore nell'inizializzazione della ServerSocket!");
			System.exit(1);
		}
		// Leggo il file da inviare ai client e memorizzo il contenuto
		// in un array. Cosi' facendo, ogni volta che un client si
		// collega, posso leggere direttamente i dati dall'array 
		// senza dover aprire il file.
		try {
			File f = new File(filename);
			FileInputStream is = new FileInputStream(f);
			content = new byte[(int) f.length()];
			is.read(content, 0, content.length);
			if (is != null) is.close();
		}
		catch (Exception e) {
			System.err.printf(
			"Errore durante la lettura del file %s!", filename);
			System.exit(1);
		}
		// A questo punto, sono pronto per rispondere alle richieste
		// in arrivo da parte dei client.
		System.out.printf("Server avviato su porta %d\n", port);
		while (true) {
			try {
				// Mi metto in attesa di una richiesta 
				// di connessione da parte di un client.
				Socket sock = serverSocket.accept();
				System.out.println("Client connesso");
				// Invio il file al client.
				OutputStream os = sock.getOutputStream();
				os.write(content, 0, content.length);
				os.flush();
				// Chiudo la connessione con il client.
				if (sock != null) sock.close();
				System.out.println("Client disconnesso");
			}
			// Quando arriva il timeout sulla ServerSocket,
			// posso far terminare il server.
			catch (SocketTimeoutException e) {
				System.out.println("Timeout connessione!");
				break;
			}
			catch (IOException e) {
				System.err.println(
				"Errore di I/O nella connessione!");
				break;
			}
		}
		System.out.println("Server terminato.");
	}
}
