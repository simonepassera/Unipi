// @author Simone Passera

import java.util.*;

public class Laboratorio {
	// Numero di computer nel laboratorio (costante).
	public final int numComputer = 20;
	// Identificativo del computer richiesto dai tesisti.
	public final int idComputerTesisti = 19;
	// Lista dove memorizzo i riferimenti ai thread.
	private final List<Thread> thread;
	// Array per tenere traccia dei computer liberi e occupati.
	// L' elemento i-esimo è false se e solo se il computer è libero.
	private final boolean[] computer;
	
	/**
	 *	Costruttore della classe Laboratorio
	 *	che inizializza tutte le strutture dati.
	 */
	public Laboratorio() {
		thread = new ArrayList<>();
		computer = new boolean[numComputer];
	}
	
	/**
	 *	Avvia la simulazione facendo entrare gli utenti.
	 *	@param numProf numero di professori nel laboratorio
	 *	@param numTesisti numero di tesisti nel laboratorio
	 *	@param numStudenti numero di studenti nel laboratorio
	 */
	public void avvio(int numProf, int numTesisti, int numStudenti) {
		System.out.println("Laboratorio aperto.");
		// Creo gli utenti, associando un thread a ciascuno di essi.
		for (int i = 0; i < numProf; i++) {
			thread.add(new Thread(
			           new Utente(Categoria.PROFESSORE, i, this)));
		}
		for (int i = 0; i < numTesisti; i++) {
			thread.add(new Thread(
			           new Utente(Categoria.TESISTA, i, this)));
		}
		for (int i = 0; i < numStudenti; i++) {
			thread.add(new Thread(
			           new Utente(Categoria.STUDENTE, i, this)));
		}
		// Avvio i thread.
		for (Thread t : thread) t.start();
		// Attendo la terminazione di tutti i thread.
		for (Thread t : thread) {
			try {
				t.join();
			} catch (InterruptedException e) {
				System.out.println("Join interrupted");
				return;
			}
		}

		System.out.println("Laboratorio chiuso.");
	}

	/**
	 *	Metodo invocato da un utente quando per richiedere
	 *	l' accesso al laboratorio.
	 *	@param u l' utente che desidera entrare nel laboratorio
	 *	@return una lista con gli id dei computer assegnati all' utente
	 */
	public synchronized List<Integer> entrata(Utente u) throws InterruptedException {
		List<Integer> assegnati = new ArrayList<>();

		System.out.printf("%s con id=%d in attesa di entrare.\n", u.categoria.name(), u.id);
		// Procedo in maniera diversa a seconda
		// del tipo di utente.

		switch (u.categoria) {
			// I professori attendono finché tutti i computer non
			// sono disponibili e quindi occupano tutto il laboratorio.
			case PROFESSORE:
				while (!libero()) wait();
				for (int i = 0; i < computer.length; i++) {
					computer[i] = true;
					assegnati.add(i);
				}
				break;
			// I tesisti occupano sempre uno specifico computer.
			case TESISTA:
				while (computer[idComputerTesisti]) wait();
				computer[idComputerTesisti] = true;
				assegnati.add(idComputerTesisti);
				break;
			// Gli studenti occupano il primo computer libero.
			case STUDENTE:
				int id = primoComputerLibero();
				while (id == -1) {
					wait();
					id = primoComputerLibero();
				}
				computer[id] = true;
				assegnati.add(id);
				break;
		}

		System.out.printf("%s con id=%d entrato.\n", 
		u.categoria.name(), u.id);
		return assegnati;
	}

	/**
	 *	@param u l' utente che desidera uscire dal laboratorio
	 *	@param occupati lista con gli id dei computer da liberare
	 */
	public synchronized void uscita(Utente u, List<Integer> occupati) {
		// Libero tutti i computer occupati.
		for (Integer id : occupati) computer[id] = false;

		// Sveglio un utente
		notify();

		System.out.printf("%s con id=%d uscito.\n", u.categoria.name(), u.id);
	}

	/**
	 *	Restituisce true se e solo se tutto il laboratorio è libero.
	 *	@return true se tutti i computer non sono occupati
	 */
	private boolean libero() {
		for (boolean b : computer) {
			if(b) return false;
		}

		return true;
	}

	/**
	 *	Restituisce l' identificativo del primo computer libero.
	 *	@return l'id del primo computer libero
	 */
	private int primoComputerLibero() {
		for (int i = 0; i < computer.length; i++) 
			if (!computer[i]) return i;
		return -1;
	}

	public static void main(String[] args) {
		// Verifica e parsing dei parametri da riga di comando.
		if (args.length < 3) {
			System.err.println("Esegui come: Laboratorio " +
			"<numProf> <numTesisti> <numStudenti>");
			System.exit(1);
		}

		int numProf = Integer.parseInt(args[0]),
		    numTesisti = Integer.parseInt(args[1]),
		    numStudenti = Integer.parseInt(args[2]);

		// Creo il laboratorio e faccio partire la simulazione.
		Laboratorio lab = new Laboratorio();
		lab.avvio(numProf, numTesisti, numStudenti);
	}
}