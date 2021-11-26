/**
 *  Laboratorio di Reti A
 *  Soluzione dell'assignment
 *  @author Matteo Loporchio
 *
 *  Compilazione con: javac -cp .:./gson-2.8.9.jar *.java
 *  Esecuzione con: java -cp .:./gson-2.8.9.jar Generator
 */

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class Consumer implements Runnable {
    // Riferimento alla coda bloccante da cui estrarre i conti correnti.
    private BlockingQueue<Account> queue;
    // Riferimento alla HashMap usata per contare le occorrenze delle causali.
    private ConcurrentHashMap<Reason,Integer> count;

    /**
     *  Costruttore della classe Producer.
     *  @param queue riferimento alla coda bloccante
     *  @param count riferimento alla HashMap
     */
    public Consumer(BlockingQueue<Account> queue,
    ConcurrentHashMap<Reason,Integer> count) {
        this.queue = queue;
        this.count = count;
    }

    /**
     *  Il Consumer esegue un ciclo in cui estrae un oggetto dalla coda
     *  (corrispondente a un conto corrente) e analizza tutti i suoi movimenti.
     *  Per ciascun movimento, si individua la causale e si aggiorna il
     *  contatore delle occorrenze per tale causale.
     */
    public void run() {
        while (true) {
            Account a = null;
            try {a = queue.take();}
            catch (InterruptedException e) {break;}
            // Se ho letto un oggetto con il campo titolare uguale a null
            // si tratta di un oggetto poison e devo terminare.
            if (a.getOwner() == null) break;
            List<Record> records = a.getRecords();
            // Uso il metodo computeIfPresent per aggiornare l'array
            // associativo che tiene traccia delle occorrenze.
            for (Record r : records)
                count.computeIfPresent(r.getReason(), (k,v) -> v+1);
        }
        System.out.println("Consumer terminato!");
    }
}
