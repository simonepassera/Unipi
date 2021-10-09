/**
 *	Laboratorio di Reti A
 *	Soluzione del secondo assignment
 *	@author Matteo Loporchio
 */

import java.util.concurrent.*;

public class Ufficio {
	// Numero degli sportelli dell'ufficio.
	public static final int numSportelli = 4;
	// Dimensione della coda davanti agli sportelli.
	public static final int dimCoda = 10;
	// Numero di clienti da fare entrare nell'ufficio.
	public static final int numClienti = 500;
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
		// Metto in coda i clienti nella prima sala.
		for (int i = 0; i < numClienti; i++) coda.add(new Persona(i));
		// Faccio passare i clienti dalla prima sala alla seconda.
		while (!coda.isEmpty()) {
			Persona p = (Persona) coda.peek();
			try {	
				// Invio il task al pool.
				pool.execute(p);
				// Rimuovo il task dalla coda.
				coda.poll();
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
				catch (InterruptedException x) {}
			}
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


