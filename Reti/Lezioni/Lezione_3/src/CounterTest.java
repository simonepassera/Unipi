/**
 *	Soluzione dell'esercizio del 28/9/2021.
 *	@author Matteo Loporchio
 */

import java.util.concurrent.*;
import java.util.concurrent.locks.*;

public class CounterTest {
	// Numero di thread da creare.
	public static final int numThread = 20;
	// Tempo massimo di attesa (in millisecondi) per la terminazione del pool.
	public static final int maxDelay = 2000;

	/**
	 *	Questo metodo prende in input un pool di thread e un contatore,
	 *	crea numThread task Worker + numThread task Reader, li invia
	 *	al pool per l'esecuzione e restituisce il tempo trascorso
	 *	dall'invio dei task fino alla terminazione del pool.
	 *	@param pool il pool di thread
	 *	@param c il contatore
	 *	@return intervallo di tempo in nanosecondi
	 */
	public static long runTest(ExecutorService pool, Counter c) {
		// Creo ed eseguo i thread Reader e Writer.
		long start = System.nanoTime();
		for (int i = 0; i < numThread; i++) pool.execute(new Writer(c));
		for (int i = 0; i < numThread; i++) pool.execute(new Reader(c));
		// Termino il pool.
		pool.shutdown();
		try {
			if (!pool.awaitTermination(maxDelay, TimeUnit.MILLISECONDS)) 
				pool.shutdownNow();
		}
		catch (InterruptedException e) {pool.shutdownNow();}
		long end = System.nanoTime();
		// Restituisco il tempo di esecuzione.
		return (end-start);
	}

	public static void main(String[] args) {
		// Primo esperimento: creo un nuovo contatore
		// che utilizza le ReentrantLock e invio i task a un CachedThreadPool.
		Counter c1 = new ReentrantCounter();
		ExecutorService p1 = Executors.newCachedThreadPool();
		long t1 = runTest(p1, c1);
		
		// Secondo esperimento (analogo al precedente): creo un nuovo contatore
		// basato sulle ReadWriteLock e invio i task al CachedThreadPool.
		Counter c2 = new RWCounter();
		ExecutorService p2 = Executors.newCachedThreadPool();
		long t2 = runTest(p2, c2);
		
		// Stampo i risultati dei primi due esperimenti.		
		System.out.printf("Tempo impiegato con ReentrantLock: %.3f ms\n"
		+ "Tempo impiegato con ReadWriteLock: %.3f ms\n", 
		(double) t1 / (double) 1E6, (double) t2 / (double) 1E6);
		
		// Terzo esperimento (parte facoltativa): 
		// uso un FixedThreadPool di dimensione variabile
		// da un minimo di 2^0 = 1 fino a 2^6 = 64 thread.
		int k = 6;
		long[] time = new long[k+1];
		for (int i = 0; i <= k; i++) {
			int size = (int) Math.pow(2, i);
			ExecutorService p = Executors.newFixedThreadPool(size);
			Counter c = new RWCounter();
			time[i] = runTest(p, c);
		}
		for (int i = 0; i <= k; i++) {
			System.out.printf(
			"Tempo con FixedThreadPool di %d thread: %.3f ms\n",
			(int) Math.pow(2, i), (double) time[i] / (double) 1E6);
		}
	}	
}

/**
 *	Rappresento il generico contatore con una classe astratta
 *	dato che la logica dei metodi varia a seconda del tipo
 *	di lock che useremo per implementare la mutua esclusione.
 */
abstract class Counter {
	/**
	 *	Rappresento il contatore con una variabile intera.
	 */
	protected int counter;
	
	/**
	 *	Metodo che incrementa il valore del contatore.
	 */
	abstract void increment();
	
	/**
	 *	Metodo che restituisce il valore del contatore.
	 *	@return il valore corrente del contatore
	 */
	abstract int get();
}

/**
 *	Implementazione del thread Writer che incrementa
 *	il valore corrente del contatore.
 */
class Writer implements Runnable {
	/** 
	 *	Riferimento al contatore.
	 */
	private Counter counter;
	
	/**
	 *	Metodo costruttore del thread Writer.
	 *	@param counter riferimento al contatore
	 */
	public Writer(Counter counter) {
		this.counter = counter;
	}

	/**
	 *	Logica del thread Writer: incremento il contatore.
	 */
	public void run() {
		counter.increment();
	}
}

/**
 *	Implementazione del thread Reader che legge il valore
 *	corrente del contatore e lo stampa.
 */
class Reader implements Runnable {
	// Riferimento al contatore.
	private Counter counter;

	/**
	 *	Metodo costruttore del thread Reader.
	 *	@param counter riferimento al contatore
	 */
	public Reader(Counter counter) {
		this.counter = counter;
	}

	/**
	 *	Logica del thread Reader: leggo il valore
	 *	del contatore e lo stampo.
	 */
	public void run() {
		System.out.printf("Thread %d: letto %d\n",
		Thread.currentThread().getId(), counter.get());
	}
}

/**
 *	Implementazione della classe ReentrantCounter
 *	che utilizza le ReentrantLock per aggiungere
 *	la mutua esclusione al generico Counter.
 */
class ReentrantCounter extends Counter {
	private ReentrantLock lock;

	public ReentrantCounter() {
		lock = new ReentrantLock();
		counter = 0;
	}

	void increment() {
		try {
			lock.lock();
			counter++;
		}
		finally {lock.unlock();}		
	}
	
	int get() {
		int value;
		try {
			lock.lock();
			value = counter;
		}
		finally {lock.unlock();}
		return value;
	}
}

/**
 *	Implementazione della classe RWCounter
 *	che utilizza le ReadWriteLock per aggiungere
 *	la mutua esclusione alla classe Counter.
 */
class RWCounter extends Counter {
	private ReadWriteLock lock;
	
	public RWCounter() {
		lock = new ReentrantReadWriteLock();
		counter = 0;
	}

	void increment() {
		try {
			lock.writeLock().lock();
			counter++;
		}
		finally {lock.writeLock().unlock();}
	}

	int get() {
		int value;
		try {
			lock.readLock().lock();
			value = counter;
		}
		finally {lock.readLock().unlock();}
		return value;
	}
}	
