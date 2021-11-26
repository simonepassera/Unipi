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

public class Producer implements Runnable {
    // Nome del file di input da cui prelevare le informazioni.
    private String filename;
    // Riferimento alla coda bloccante in cui inserire i conti correnti.
    private BlockingQueue<Account> queue;
    // Numero di oggetti poison da inserire in coda prima della terminazione
    // con lo scopo di far terminare i thread Consumer.
    private int poison;

    // Costruttore della classe Producer.
    public Producer(String filename, BlockingQueue<Account> queue, int poison) {
        this.filename = filename;
        this.queue = queue;
        this.poison = poison;
    }

    /**
     *  Metodo contenente la logica del thread Producer.
     *  Il producer legge il file JSON su cui sono stati scritti
     *  tutti i conti correnti e lo deserializza, creando un oggetto
     *  di tipo Account per ciascun conto. Gli oggetti sono inseriti
     *  in una coda bloccante condivisa con i thread Consumer.
     */
    public void run() {
        Gson gson = new Gson();
        try {
            InputStream is = new FileInputStream(new File(filename));
            JsonReader reader = new JsonReader(new InputStreamReader(is));
            reader.setLenient(true);
            // Leggo la parentesi quadra aperta "[".
            reader.beginArray();
            while (reader.hasNext()) {
                String owner = null;
                List<Record> records = null;
                // Leggo la parentesi graffa aperta "{".
                reader.beginObject();
                while (reader.hasNext()) {
                    String name = reader.nextName();
                    if (name.equals("owner")) {
                        owner = reader.nextString();
                    } else if (name.equals("records")) {
                        records = readRecords(reader);
                    } else {reader.skipValue();}
                }
                // Leggo la parentesi graffa aperta "}".
                reader.endObject();
                // Inserisco nella coda condivisa il conto appena letto.
                queue.put(new Account(owner, records));
            }
            // Leggo la parentesi quadra chiusa "]".
            reader.endArray();
            reader.close();
            // Inserisco in coda gli oggetti poison per far terminare
            // i consumer.
            for (int i = 0; i < poison; i++) queue.put(new Account(null));
        } catch (Exception e) {
            System.err.println("Errore nel Producer: " + e.getMessage());
        }
        finally {
            System.err.println("Producer terminato!");
        }
    }

    /**
     *  Metodo per leggere la lista di movimenti associati ad un conto
     *  a partire dal file JSON.
     *  @param reader il JsonReader gestito da GSON
     *  @return la lista di movimenti
     */
    private List<Record> readRecords(JsonReader reader) throws Exception {
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        List<Record> result = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
            String dateStr = null;
            String reasonStr = null;
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                if (name.equals("date")) {
                    dateStr = reader.nextString();
                } else if (name.equals("reason")) {
                    reasonStr = reader.nextString();
                } else {reader.skipValue();}
            }
            reader.endObject();
            Date date = df.parse(dateStr);
            Reason reason = Reason.valueOf(reasonStr);
            result.add(new Record(date, reason));
        }
        reader.endArray();
        return result;
    }

}
