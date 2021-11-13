/**
 *	Laboratorio di Reti A
 *	Soluzione dell'esercizio del 19/10/2021
 *	@author Matteo Loporchio
 */

import java.io.*;
import java.net.*;

public class Client {
	// Nome dell'host a cui connettersi.
	public static final String hostname = "localhost";
	// Porta associata alla socket.
	public static final int port = 9999;
	// Dimensione massima del file da ricevere (in byte).
	public static final int maxSize = 32768;
	// Array per memorizzare i dati del server.
	public static byte[] content = new byte[maxSize];
	
	public static void main(String[] args) {
		// Creo la socket per connettermi al server.
		Socket socket = null;
		try {socket = new Socket(hostname, port);}
		catch (Exception e) {
			System.err.println("Errore nella creazione della socket!");
			System.exit(1);
		}
		System.out.printf("Client avviato su porta %d\n", port);
		// Ricevo i dati dal server.
		try {
			InputStream is = socket.getInputStream();
			is.read(content, 0, content.length);
			System.out.write(content);
		}	
		catch (Exception e) {
			System.err.println("Errore nella ricezione del file!");
			System.exit(1);
		}
		// Chiudo la connessione con il server.
		try {if (socket != null) socket.close();}
		catch (IOException e) {
			System.err.println(
			"Errore nella chisura della connesione!");
			System.exit(1);
		}
		System.out.println("Client terminato.");	
	}
}
