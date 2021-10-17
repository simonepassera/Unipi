/**
 *	Laboratorio di Reti A
 *	Soluzione dell'esercizio del 5 ottobre 2021
 *	@author Matteo Loporchio
 */

import java.util.concurrent.*;

public class DropboxTest {
	// Massimo numero di millisecondi per l'attesa dei thread.
	public static final long maxDelay = 5000;

	// L'esecuzione del programma inizia da qui.
	public static void main(String[] args) {
		// Creo un nuovo oggetto Dropbox.
		Dropbox dropbox = new Dropbox();
		// Creo due thread Consumer e un Producer.
		Thread c1 = new Thread(new Consumer(true, dropbox)),
		c2 = new Thread(new Consumer(false, dropbox)),
		p = new Thread(new Producer(dropbox));

		// Avvio tutti i thread.
		c1.start(); c2.start(); p.start();
		try {
			// Attendo `maxDelay` millisecondi e poi forzo
			// la terminazione di tutti i thread.
			c1.join(maxDelay); c2.join(maxDelay); p.join(maxDelay);
			if (c1.isAlive()) c1.interrupt();
			if (c2.isAlive()) c2.interrupt();
			if (p.isAlive()) p.interrupt();
		}
		catch (InterruptedException e) {
			System.err.println("Main: interrupted during join");
			System.exit(1);
		}
	}
}

class Dropbox {
	// Variabile che memorizza il valore corrente del buffer.
	private int value;
	
	// Valore booleano che segnala la disponibilita' di un valore.
	private boolean ready;
	
	// Costruttore della classe.
	public Dropbox() {ready = false;}
	
	/**
	 *	Restituisce il valore corrente del buffer.
	 *	@param req true se e solo se il valore da recuperare e' pari
	 *	@return il valore corrente del buffer
	 */
	public synchronized int take(boolean req) throws InterruptedException {
		// Aspetto finche' il buffer non contiene un valore
		// corrispondente alla mia richiesta.
		while (!ready || ((value % 2 == 0) != req)) this.wait();
		ready = false;
		this.notifyAll();
		return value;
	}	

	/**
	 *	Inserisce il valore specificato nel buffer.
	 *	@param v il valore da inserire nel buffer
	 */
	public synchronized void put(int v) throws InterruptedException {
		// Attendo finche' il buffer e' pieno.
		while (ready) this.wait();
		// Al risveglio, scrivo il valore nel buffer.
		value = v;
		ready = true;
		this.notifyAll();
	}	
}

/**
 *	Il Producer genera un intero casuale fra 0 e 99 e lo inserisce
 *	nel buffer che gli viene passato al momento della creazione.
 */
class Producer implements Runnable {
	// Valore minimo (incluso) per gli interi casuali.
	public final int minValue = 0;
	// Valore massimo (escluso) per gli interi casuali.
	public final int maxValue = 100;
	// Riferimento ad un'istanza di Dropbox.
	private Dropbox dropbox;
	// Costruttore che prende in input il riferimento.
	public Producer(Dropbox dropbox) {this.dropbox = dropbox;}
	// Metodo run contenente la logica del Producer.
	public void run() {
		// Genero un intero casuale fra 0 e 99.
		int v = ThreadLocalRandom.current().nextInt(minValue, maxValue);
		try {
			// Inserisco il valore nel buffer.
			dropbox.put(v);
			// Stampo il valore inserito.
			System.out.printf("Producer: inserito il valore %d\n", v);
		}
		catch (InterruptedException e) {
			System.out.println("Producer interrotto!");
		}
	}
}

/**
 *	Il Consumer prende in input un'istanza di Dropbox
 *	e legge un valore (pari o dispari) dal buffer.
 */
class Consumer implements Runnable {
	// Riferimento ad un'istanza di Dropbox.
	private Dropbox dropbox;
	// True se e solo se il valore da consumare e' pari.
	private boolean req;

	/**
	 *	Costruttore della classe Consumer.
	 *	@param req true se e solo se il Consumer richiede valori pari
	 *	@param dropbox riferimento all'istanza di Dropbox
	 */
	public Consumer(boolean req, Dropbox dropbox) {
		this.req = req;
		this.dropbox = dropbox;
	}

	/**
	 *	Il Consumer invoca semplicemente il metodo take()
	 *	sull'istanza di Dropbox, leggendo il valore corrente.
	 */
	public void run() {
		try {
			// Leggo il valore nel buffer.
			int v = dropbox.take(req);
			// Stampo il valore letto.
			System.out.printf("Consumer: letto il valore %d\n", v);
		}
		catch (InterruptedException e) {
			System.out.println("Consumer interrotto!");
		}
	}
}
