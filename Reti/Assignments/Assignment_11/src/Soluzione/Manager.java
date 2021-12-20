/**
 *  Laboratorio di Reti A
 *  Soluzione dell'assignment
 *  @author Matteo Loporchio
 */

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class Manager implements ManagerInterface {
    // Numero di giorni previsti per l'evento.
    public final int numDays = 3;
    // Numero di sessioni per ciascun giorno.
    public final int numSessions = 12;
    // Numero di speaker per ciascun giorno.
    public final int numSpeakers = 5;
    // Lista di liste per memorizzare i partecipanti al congresso.
    // Ogni lista interna rappresenta una sessione.
    private List<List<String>> congress;

    /**
     *  Costruttore della classe Manager che inizializza la struttura dati
     *  per la memorizzazione delle liste.
     */
    public Manager() {
        congress = new ArrayList<>();
        for (int i = 0; i < (numDays * numSessions); i++)
            congress.add(new ArrayList<String>());
    }

    /**
     *  Metodo per registrare uno speaker nel congresso
     *  @param sessionId identificativo della sessione
     *  @param name nome dello speaker
     */
    public void addSpeaker(int sessionId, String name) throws RemoteException {
        // Controllo la validità della sessione.
        if (0 <= sessionId && sessionId <= (congress.size() - 1)) {
            // Se lo slot è disponibile, registro lo speaker.
            List<String> session = congress.get(sessionId);
            if (session.size() < numSpeakers) {
                session.add(name);
                return;
            }
            // Altrimenti sollevo un'eccezione per notificare
            // il fatto che lo slot è già occupato da un altro speaker.
            throw new RemoteException("Sessione piena");
        }
        // Se sono qui, il numero di sessione indicato non è valido.
        throw new RemoteException("Sessione non valida");
    }

    /**
     *  Metodo che restituisce la lista degli speaker per una sessione
     *  @param sessionId identificativo della sessione
     *  @return la lista degli speaker per quella sessione
     */
    public List<String> getSpeakers(int sessionId) throws RemoteException {
        // Controllo se l'identificativo della sessione è valido.
        if (0 <= sessionId && sessionId <= (congress.size() - 1)) {
            return congress.get(sessionId);
        }
        // Se non lo è, sollevo una eccezione.
        throw new RemoteException("Sessione non valida");
    }
}
