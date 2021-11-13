/**
 *	Laboratorio di Reti A
 *	Soluzione del quinto assignment
 *	@author Matteo Loporchio
 */

import java.io.*;
import java.util.*;

public class Crawler {
	// Massimo tempo di attesa per la terminazione dei thread.
	public static final long waitDelay = 1000;
	public static void main(String args[]) {
		// Leggo gli argomenti da riga di comando.
		if (args.length < 2) {
			System.err.println(
			"Esegui come: Crawler <path> <numConsumer>");
			System.exit(1);
		}
		// Provo ad aprire la cartella avente il percorso specificato.
		File rootDir = new File(args[0]);
		if (!rootDir.isDirectory()) {
			System.err.printf(
			"%s non e' una directory valida!", args[0]);
			System.exit(1);
		}
		// Leggo il numero di thread consumatori.
		int numConsumer = Integer.parseInt(args[1]);
		// Inizializzo la coda condivisa.
		CustomQueue<File> queue = new CustomQueue<>();
		// Creo e avvio il Producer e i thread Consumer.
		List<Thread> threads = new ArrayList<>();
		threads.add(new Thread(new Producer(rootDir, queue)));
		for (int i = 0; i < numConsumer; i++) 
			threads.add(new Thread(new Consumer(i, queue)));
		for (Thread t : threads) t.start();
		// Mi metto in attesa della loro terminazione.
		for (Thread t : threads) {
			try {t.join(waitDelay);}
			catch (InterruptedException e) {
				System.err.println("Thread main interrotto!");
				System.exit(1);
			}
			// Invio l'interrupt dopo aver aspettato la terminazione.
			if (t.isAlive()) t.interrupt();
		}
	}
}

/**
 *	Il Producer esplora ricorsivamente tutto il sottoalbero
 *	radicato nella directory di partenza e mette in coda i nomi
 *	di tutte le cartelle che incontra.
 */
class Producer implements Runnable {
	private File rootDir;
	private CustomQueue<File> queue;

	/**
	 *	Costruttore della classe Producer.
	 *	@param rootDir la directory da cui cominciare l'esplorazione
	 *	@param queue la coda condivisa in cui inserire i nomi
	 */
	public Producer(File rootDir, CustomQueue<File> queue) {
		this.rootDir = rootDir;
		this.queue = queue;
	}
	
	public void run() {
		// Questa coda serve solo al Producer per esplorare la cartella.
		LinkedList<File> dirs = new LinkedList<File>();
		dirs.add(rootDir);
		queue.put(rootDir);
		while (!dirs.isEmpty()) {
			// Esamino i file nella directory corrente.
			File[] files = dirs.removeFirst().listFiles();
			for (File f : files) {
				// Se incontro una directory, la metto in coda
				// per l'esplorazione e aggiungo il suo nome
				// nella coda condivisa con i Consumer.
				if (f.isDirectory()) {
					dirs.add(f);
					queue.put(f);
				}
			}
		}
		System.out.println("Producer terminato!");
	}
}
		
/**
 *	Implementazione del generico Consumer che estrae un nome
 *	dalla coda e lo stampa su schermo.
 */
class Consumer implements Runnable {
	public final int id;
	private CustomQueue<File> queue;
	
	/**
	 *	Costruttore della classe Consumer.
	 *	@param id identificativo numerico del Consumer
	 *	@param queue riferimento alla coda
	 */
	public Consumer(int id, CustomQueue<File> queue) {
		this.id = id;
		this.queue = queue;
	}
	
	public void run() {
		while (true) {
			try {
				// Estraggo una directory dalla coda.
				File dir = queue.take();
				// Esamino il contenuto della directory corrente
				// e stampo il nome di tutto cio' che incontro.
				for (File f : dir.listFiles()) {
					System.out.printf(
					"Consumer %d: %s\n", id, f.getName());
				}
			}
			// Il `break` serve per far terminare il thread.
			catch (InterruptedException e) {break;}
		}
		System.out.printf("Consumer %d terminato!\n", id);
	}
}
