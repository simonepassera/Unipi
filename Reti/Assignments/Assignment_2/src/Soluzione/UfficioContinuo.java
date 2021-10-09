/**
 *	Laboratorio di Reti A
 *	Soluzione del secondo assignment
 *	@author Matteo Loporchio
 */

import java.util.concurrent.*;

public class UfficioContinuo {
	// Numero degli sportelli dell'ufficio.
	public static final int numSportelli = 4;
	// Dimensione della coda davanti agli sportelli.
	public static final int dimCoda = 10;
	// Tempo di attesa se la coda sportelli e' piena.
	public static final long queueDelay = 500;
	// Tempo di attesa per la terminazione del pool.
	public static final long terminationDelay = 5000;

	public static void main(String[] args) {
		// Contatore delle persone servite.
		int count = 0;
		// Stampo un messaggio per segnalare l'apertura.
		System.out.println("Ufficio aperto!");
		// Creo la coda per la prima sala. 
		// Nota: non ci sono limiti sulla sua dimensione. 
		BlockingQueue<Runnable> coda = new LinkedBlockingQueue<Runnable>();
		// A questo punto creo il pool di thread personalizzato
		// con `numSportelli` sportelli e una coda bloccante
		// di dimensione pari a `dimCoda`.
		ExecutorService pool = new ThreadPoolExecutor(
		numSportelli, numSportelli, 0, TimeUnit.MILLISECONDS,
		new ArrayBlockingQueue<Runnable>(dimCoda),
		new ThreadPoolExecutor.AbortPolicy());		
		// Avvio il thread producer che fa entrare i clienti in sala.
		Thread producer = new Thread(new Producer(coda));
		producer.start();
		// Registro l'handler per il Ctrl+C.
		CtrlCHandler h = new CtrlCHandler(producer);
		// Faccio passare i clienti dalla prima sala alla seconda.
		Persona p = null;
		while (true) {
			// Attendo finche' non c'e' un elemento in coda.
			try {p = (Persona) coda.take();}
			catch (InterruptedException e) {
				System.err.println("Interruzione del main!");
				System.exit(1);
			};
			// Se l'elemento ha id pari a -1, devo terminare.
			if (p.id == -1) {
				System.out.println("Fine dei clienti!");
				break;
			}
			try {	
				// Invio il task al pool.
				pool.execute(p);
				count++;
			}
			catch (RejectedExecutionException e) {
				// Se sono qui, significa che la coda davanti
				// agli sportelli (ovvero la coda del pool)
				// e' piena. Aspetto un certo intervallo di
				// tempo affinche' si svuoti per mettere in
				// coda il prossimo cliente.
				System.out.printf("Coda sportelli piena. " +
				"Il cliente con id=%d resta in attesa.\n", p.id);
				try {Thread.sleep(queueDelay);}
				catch (InterruptedException x) {
					System.err.println(
						"Interruzione del main!");
					System.exit(1);
				}
			}
		}
		// Attendo la terminazione del thread Producer.
		try {producer.join();}
		catch (InterruptedException e) {
			System.err.println("Interruzione del main!");
			System.exit(1);
		}	
		// A questo punto, posso chiudere l'ufficio,
		// ovvero faccio terminare il pool. 
		// Adottiamo il seguente metodo:
		// 1) 	Smetto di accettare nuovi task.
		// 2) 	Successivamente attendo un certo intervallo di 
		// 	tempo affinche' tutti i thread possano terminare.
		// 3) 	Passato l'intervallo, l'esecuzione del pool 
		//	viene interrotta immediatamente.
		pool.shutdown();
		try {
			if (!pool.awaitTermination(terminationDelay, 
			TimeUnit.MILLISECONDS)) pool.shutdownNow();
		}
		catch (InterruptedException e) {pool.shutdownNow();}
		// Stampo un messaggio di chiusura.
		System.out.printf("Ufficio chiuso. Persone servite: %d\n", count);
	}
}

/**
 *	Il Producer fa entrare persone (ovvero aggiunge task alla coda)
 *	ad intervalli regolari. 
 */
class Producer implements Runnable {
	// Tempo trascorso fra la creazione di un cliente e l'altro.
	public final long clientDelay = 500;

	// Coda in cui inserire i task prodotti.
	private BlockingQueue<Runnable> queue;

	// Costruttore che prende in input un riferimento a una coda.
	public Producer(BlockingQueue<Runnable> queue) {this.queue = queue;}

	// Metodo run che genera task ad intervalli regolari.
	public void run() {
		int id = 0;
		while (!Thread.currentThread().isInterrupted()) {
			try {
				queue.put(new Persona(id));
				id++;
				Thread.sleep(clientDelay);
			}
			catch (InterruptedException e) {break;}
		}
		// Prima di terminare, metto in coda l'elemento speciale
		// avente id uguale a -1 che segnala la terminazione del programma.
		try {queue.put(new Persona(-1));}
		catch (InterruptedException e) {}
	}
}

/**
 *	Questo e' uno speciale handler che "intercetta" la pressione
 *	dei tasti Ctrl+C e fa terminare tutto il programma, provocando
 *	la chiusura dell'ufficio postale.
 */
class CtrlCHandler {
	// Riferimento al thread da interrompere.
	private Thread producer;
	// Costruttore della classe. 
	// Prende in input un riferimento al thread da interrompere
	// non appena si preme la combinazione di tasti Ctrl+C.
	public CtrlCHandler(Thread producer) {
		this.producer = producer;
		// Creo "al volo" un Runnable che verra' eseguito
		// alla pressione della combinazione di tasti.
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				// Stampo il messaggio di chiusura.
				System.out.println("Ufficio in chiusura!");
				// Interrompo il thread Producer.
				if (producer.isAlive()) producer.interrupt();
     			}
		});
	}
}
