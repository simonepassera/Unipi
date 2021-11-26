/**
 *  Laboratorio di Reti A
 *  Soluzione dell'assignment
 *  @author Matteo Loporchio
 *
 *  Compilazione con: javac -cp .:./gson-2.8.9.jar *.java
 *  Esecuzione con: java -cp .:./gson-2.8.9.jar Generator
 */

import com.google.gson.*;
import com.google.gson.stream.*;
import java.io.*;
import java.nio.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;

public class Reader {
    // Nome del file di input.
    public static final String inputFile = "accounts.json";
    // Numero di thread consumer da creare.
    public static final int nConsumer = 4;
    // Coda condivisa fra Producer e Consumer.
    public static BlockingQueue<Account> queue = new LinkedBlockingQueue<>();
    // HashMap concorrente usata per contare le occorrenze delle causali.
    public static ConcurrentHashMap<Reason,Integer> count = new ConcurrentHashMap<>();

    public static void main(String[] args) throws Exception {
        // Inizializzo la map per contare le occorrenze.
        // Per ciascuna causale, il contatore nell'array viene messo a zero.
        for (Reason r : Reason.values()) count.put(r, 0);
        // Creo e avvio il thread Producer e i vari Consumer.
        List<Thread> consumers = new ArrayList<>();
        Thread producer = new Thread(new Producer(inputFile, queue, nConsumer));
        producer.start();
        for (int i = 0; i < nConsumer; i++) {
            Thread c = new Thread(new Consumer(queue, count));
            c.start();
            consumers.add(c);
        }
        // Attendo la terminazione dei Consumer.
        for (Thread c : consumers) c.join();
        // Stampo i risultati su schermo.
        for (var e : count.entrySet())
            System.out.println(e.getKey() + ": " + e.getValue());
    }
}
