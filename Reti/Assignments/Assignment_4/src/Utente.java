// @author Simone Passera

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

class Utente implements Comparable<Utente>, Runnable {
	// Tipologia di utente.
	public final Categoria categoria;
	// Identificativo numerico dell' utente.
	public final int id;
	// Numero di accessi previsti per l' utente.
	public final int numAccessi;
	// Tempo in cui l' utente utilizza le risorse del laboratorio.
	public final long workDelay;
	// Tempo che intercorre fra un accesso e l' altro.
	public final long breakDelay;
	// Massimo numero di accessi, massimo tempo di lavoro e pausa.
	public final int maxAccessi = 5;
	public final long maxWork = 5000;
	public final long maxBreak = 2000;
	// Riferimento al laboratorio.
	private final Laboratorio lab;
	
	/**
	 *	Costruttore della classe Utente.
	 *	@param categoria la categoria dell' utente
	 *	@param id identificativo numerico dell' utente
	 *	@param lab riferimento al laboratorio
	 */
	public Utente(Categoria categoria, int id, Laboratorio lab) {
		this.categoria = categoria;
		this.id = id;
		this.lab = lab;
		numAccessi = ThreadLocalRandom.current().nextInt(1, maxAccessi+1);
		workDelay = ThreadLocalRandom.current().nextLong(maxWork+1);
		breakDelay = ThreadLocalRandom.current().nextLong(maxBreak+1);
	}

	/**
	 *	Tutti gli utenti richiedono l' accesso al laboratorio,
	 *	lo utilizzano per un certo intervallo di tempo e poi
	 *	escono per fare una pausa. Tutto viene ripetuto per
	 *	`numAccessi` volte.
	 */
	public void run() {
		try {
			for (int i = 0; i < numAccessi; i++) {
				List<Integer> assegnati = lab.entrata(this);
				Thread.sleep(workDelay);
				lab.uscita(this, assegnati);
				Thread.sleep(breakDelay);
			}
		}
		catch (InterruptedException e) {
			System.out.printf("%s con id=%d interrotto.\n", categoria.name(), id);
			return;
		}

		System.out.printf("%s con id=%d ha terminato il lavoro.\n", categoria.name(), id);
	}

	/**
	 *	Metodo per confrontare due utenti sulla base della loro priorità.
	 *	@param u l' utente da confrontare con quello corrente
	 */
	public int compareTo(Utente u) {
		return Integer.compare(categoria.ordinal(), u.categoria.ordinal()); 
	}
}
