/**
 *	Laboratorio di Reti A
 *	Soluzione dell'esercizio del 2/11/2021
 *	@author Matteo Loporchio
 */

import java.io.*;
import java.net.*;

public class Server {
	// Tempo massimo di attesa prima di far terminare il server.
	public static final int timeout = 90000;
	// Dimensione del buffer in cui memorizzare la risposta.
	public static final int bufSize = 1024;
	public static void main(String[] args) {
		// Inizializzo la socket per ricevere le richieste.
		// Imposto anche il timeout in maniera tale da
		// sollevare una SocketTimeoutException 
		// nel caso in cui non ricevo richieste entro
		// un certo intervallo di tempo.
		DatagramSocket serverSocket = null;
		try {
			serverSocket = new DatagramSocket(port);
			serverSocket.setSoTimeout(timeout);
		}
		catch (SocketException e) {
			System.err.println(
			"Errore nell'inizializzazione della socket");
			System.exit(1);
		}
		byte[] buf = new byte[bufSize];
		System.out.printf("Server pronto sulla porta %s\n", port);
		try {
			while (true) {
				// Mi metto in attesa di richieste.
				DatagramPacket rp = new DatagramPacket(
				buf, buf.length); 
				serverSocket.receive(rp);
				System.out.printf("Richiesta ricevuta: %s\n",
				new String(rp.getData()));
				// Preparo la risposta prelevando indirizzo
				// e porta dal pacchetto che ho ricevuto.
				InetAddress addr = rp.getAddress();
				int port = rp.getPort();
				// Il contenuto del pacchetto di risposta 
				// e' semplicemente la stringa "PONG".
				byte[] reply = new String("PONG").getBytes(); 
				DatagramPacket sp = new DatagramPacket(reply,
				reply.length, addr, port);
				serverSocket.send(sp);
				System.out.println("Risposta inviata");
			}
		}
		catch (SocketTimeoutException e) {
			// Qui catturo la SocketTimeoutException che
			// viene sollevata al momento del timeout.
			System.out.println("Terminazione del server...");
		}
		catch (IOException e) {
			System.err.println("Errore di I/O: " + e.getMessage());
		}
		finally {
			serverSocket.close();
			System.out.println("Server terminato.");
		}
	}
}
