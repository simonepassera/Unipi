/**
 *	Laboratorio di Reti A
 *	Soluzione del quarto assignment
 *	@author Matteo Loporchio
 */

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;

public class LaboratorioMonitor {
	// Numero di computer nel laboratorio (costante).
	public final int numComputer = 20;
	// Identificativo del computer richiesto dai tesisti.
	public final int idComputerTesisti = 19;
	// Lista dove memorizzo i riferimenti ai thread.
	private List<Thread> thread;
	// Array per tenere traccia dei computer liberi e occupati.
	// L'elemento i-esimo e' false se e solo se il computer e' libero.
	private boolean[] computer;
	// Utilizzo questi contatori per tenere traccia di professori
	// e tesisti in attesa di entrare nel laboratorio.
	private int profWaiting = 0;
	private int tesiWaiting = 0;
	
	/**
	 *	Costruttore della classe LaboratorioMonitor
	 *	che inizializza tutte le strutture dati.
	 */
	public LaboratorioMonitor() {
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
			try {t.join();}
			catch (InterruptedException e) {}
		}
		System.out.println("Laboratorio chiuso.");
	}

	/**
	 *	Metodo invocato da un utente quando per richiedere
	 *	l'accesso al laboratorio.
	 *	@param u l'utente che desidera entrare nel laboratorio
	 *	@return una lista con gli id dei computer assegnati all'utente
	 */
	public synchronized List<Integer> entrata(Utente u)
	throws InterruptedException {
		List<Integer> assegnati = new ArrayList<>();
		System.out.printf("%s con id=%d in attesa di entrare.\n", 
		u.categoria.name(), u.id);
		// Quindi procedo in maniera diversa a seconda
		// del tipo di utente.
		switch (u.categoria) {
			// I professori attendono finche' tutti i computer non
			// sono disponibili e quindi occupano tutto il laboratorio.
			case PROFESSORE:
				profWaiting++;
				while (!libero()) wait();
				profWaiting--;
				for (int i = 0; i < computer.length; i++) {
					computer[i] = true;
					assegnati.add(i);
				}
			break;
			// I tesisti occupano sempre uno specifico computer.
			case TESISTA:
				tesiWaiting++;
				while (profWaiting > 0 || 
				computer[idComputerTesisti]) wait();
				tesiWaiting--;
            			computer[idComputerTesisti] = true;
				assegnati.add(idComputerTesisti);
			break;
			// Gli studenti occupano il primo computer libero.
			case STUDENTE:
				int id = primoComputerLibero();
				// Lo studente attende finche' ci sono professori
				// che stanno aspettando, oppure se non ci sono
				// computer disponibili o se il computer assegnato
				// e' quello dei tesisti e ci sono gia' tesisti
				// prenotati per l'entrata.
				while (profWaiting > 0 || id == -1 ||
				(tesiWaiting > 0 && id == idComputerTesisti)) {
					wait();
					id = primoComputerLibero();
				}
				computer[id] = true;
				assegnati.add(id);
			break;
			default: break;
		}
		System.out.printf("%s con id=%d entrato.\n", 
		u.categoria.name(), u.id);
		return assegnati;
	}

	/**
	 *	Metodo invocato dall'utente all'uscita dal laboratorio.
	 *	@param u l'utente che desidera uscire dal laboratorio
	 *	@param occupati lista con gli id dei computer da liberare
	 */
	public synchronized void uscita(Utente u, List<Integer> occupati) {
		// Libero tutti i computer occupati.
		for (Integer id : occupati) computer[id] = false;
		// Risveglio tutti gli utenti in attesa.
		// NOTA: al proprio risveglio, ciascun utente controllera'
		// la validita' della propria condizione di attesa. 
		notifyAll();
		System.out.printf("%s con id=%d uscito.\n", 
		u.categoria.name(), u.id);
	}

	/**
	 *	Restituisce true se e solo se tutto il laboratorio e' libero.
	 *	@return true se tutti i computer non sono occupati
	 */
	private boolean libero() {
		for (int i = 0; i < computer.length; i++) 
			if (computer[i]) return false;
		return true;
	}

	/**
	 *	Restituisce l'identificativo del primo computer libero.
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
			System.err.println("Esegui come: LaboratorioMonitor " +
			"<numProf> <numTesisti> <numStudenti>");
			System.exit(1);
		}
		int numProf = Integer.parseInt(args[0]),
		numTesisti = Integer.parseInt(args[1]),
		numStudenti = Integer.parseInt(args[2]);
		// Creo il laboratorio e faccio partire la simulazione.
		LaboratorioMonitor lab = new LaboratorioMonitor();
		lab.avvio(numProf, numTesisti, numStudenti);
	}
}

/**
 *	Questa classe rappresenta il generico Utente del laboratorio.
 *	A seconda della Categoria, puo' trattarsi di uno Studente,
 *	di un Tesista oppure di un Professore.
 */
class Utente implements Runnable {
	// Tipologia di utente.
	public final Categoria categoria;
	// Identificativo numerico dell'utente.
	public final int id;
	// Numero di accessi previsti per l'utente.
	public final int numAccessi;
	// Tempo in cui l'utente utilizza le risorse del laboratorio.
	public final long workDelay;
	// Tempo che intercorre fra un accesso e l'altro.
	public final long breakDelay;
	// Massimo numero di accessi, massimo tempo di lavoro e pausa.
	public final int maxAccessi = 5;
	public final long maxWork = 5000;
	public final long maxBreak = 2000;
	// Riferimento al laboratorio.
	private final LaboratorioMonitor lab;
	
	/**
	 *	Costruttore della classe Utente.
	 *	@param categoria la categoria dell'utente
	 *	@param id identificativo numerico dell'utente
	 *	@param lab riferimento al laboratorio
	 */
	public Utente(Categoria categoria, int id, LaboratorioMonitor lab) {
		this.categoria = categoria;
		this.id = id;
		this.lab = lab;
		numAccessi = ThreadLocalRandom.current().nextInt(1, maxAccessi+1);
		workDelay = ThreadLocalRandom.current().nextLong(maxWork+1);
		breakDelay = ThreadLocalRandom.current().nextLong(maxBreak+1);
	}

	/**
	 *	Tutti gli utenti richiedono l'accesso al laboratorio,
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
			System.out.printf("%s con id=%d interrotto.\n", 
			categoria.name(), id);
			return;
		}
		System.out.printf("%s con id=%d ha terminato il lavoro.\n",
		categoria.name(), id);
	}

}

/**
 *	Tipo enumerato per rappresentare le varie tipologie di utenti
 *	che accedono al laboratorio.
 */
enum Categoria {
	PROFESSORE,
	TESISTA, 
	STUDENTE
}

