/**
 *	Laboratorio di Reti A
 *	Soluzione del secondo assignment
 *	@author Matteo Loporchio
 */

import java.util.concurrent.*;

/**
 *	I clienti dell'ufficio sono rappresentati dalla classe Persona.
 *	Ogni persona ha associato un identificativo univoco che le
 *	viene assegnato nel momento in cui accede all'ufficio stesso.
 */
public class Persona implements Runnable {
	// Identificativo del cliente.
	public final int id;
	// Minimo intervallo di tempo per le operazioni del cliente.
	public final long minDelay = 0;
	// Massimo intervallo di tempo per le operazioni del cliente.
	public final long maxDelay = 1000;
	
	/** 
	 *	Costruttore della classe Persona.
	 *	@param id l'id del cliente
	 */
	public Persona(int id) {
		this.id = id;
	}

	/**
	 *	Metodo contenente la logica del cliente.
	 *	Ogni cliente dell'ufficio genera un intervallo di tempo
	 *	casuale e attende per tale numero di millisecondi prima
	 *	di terminare.
	 */
	public void run() {
		System.out.printf(
		"Cliente con id=%d arrivato allo sportello.\n", id);
		long delay = ThreadLocalRandom.current()
			.nextLong(minDelay, maxDelay);
		try {Thread.sleep(delay);}
		catch (InterruptedException e) {
			System.err.println("Interruzione su sleep.");
			return;
		}
		System.out.printf(
		"Cliente con id=%d ha abbandonato l'ufficio.\n", id);
	}
}
