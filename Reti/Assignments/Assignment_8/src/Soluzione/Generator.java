/**
 *  Laboratorio di Reti A
 *  Soluzione dell'assignment
 *  @author Matteo Loporchio
 *
 *  Compilazione con: javac -cp .:./gson-2.8.9.jar *.java
 *  Esecuzione con: java -cp .:./gson-2.8.9.jar Generator
 */

import com.google.gson.*;
import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;

public class Generator {
    // Numero totale di conti correnti.
    public static final int numAccounts = 100;
    // Numero dei movimenti per ciascun conto corrente.
    public static final int numRecords = 1000;
    // Lunghezza dei nomi degli utenti.
    public static final int ownerLength = 10;
    // Nome del file di output.
    public static final String outputFile = "accounts.json";
    // Dimensione del buffer.
    public static final int bufSize = 8192;
    // Generatore per numeri e stringhe casuali.
    public static Random gen = new Random();

    public static void main(String[] args) {
        GsonBuilder builder = new GsonBuilder();
        builder.setDateFormat("yyyy/MM/dd HH:mm:ss");
        Gson gson = builder.create();
        // Apro il file di output e il canale associato allo stream.
		try (
			FileOutputStream os = new FileOutputStream(outputFile);
			FileChannel oc = os.getChannel();
		) {
			// Creo un buffer avente la dimensione fissata.
            // Lo userò per scrivere gli oggetti corrispondenti ai
            // conti correnti.
			ByteBuffer buf = ByteBuffer.allocate(bufSize);
            // Definisco gli estremi dell'intervallo di date
            // per generare movimenti casuali.
            Date minDate = (new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")).
            parse("2019/01/01 00:00:00"),
            maxDate = new Date(System.currentTimeMillis());
            // Scrivo una parentesi quadra sul file per segnalare l'inizio di
            // una lista di oggetti (ovvero i conti correnti).
            writeChar(oc, '[');
            // A questo punto, creo i conti correnti, generando per ciascuno
            // di essi un nome titolare (ovvero una stringa di caratteri
            // casuali) e una lista di movimenti casuali.
            for (int i = 0; i < numAccounts; i++) {
                String owner = randomString(ownerLength);
                Account account = new Account(owner);
                // Per ogni movimento, genero una data e una causale
                // in modo casuale.
                for (int j = 0; j < numRecords; j++) {
                    Date date = randomDate(minDate, maxDate);
                    Reason reason = randomReason();
                    account.addRecord(new Record(date, reason));
                }
                // Serializzo l'intero account come stringa JSON.
                byte[] data = gson.toJson(account).getBytes();
                // La stringa viene scritta "a blocchi" usando il ByteBuffer
                // avente dimensione fissata. Il contenuto del buffer
                // viene poi scritto sul canale associato al file.
                for (int l = 0; l < data.length; l += bufSize) {
                    // Preparo il buffer per la scrittura.
    				buf.clear();
    				// Scrivo la riga nel buffer.
    				buf.put(data, l, Math.min(bufSize, data.length - l));
    				// Preparo il buffer per la lettura.
    				buf.flip();
    				// Leggo il contenuto del buffer e lo scrivo sul canale.
    				while (buf.hasRemaining()) oc.write(buf);
                }
                // Scrivo sempre una virgola dopo l'oggetto in modo da
                // separare i conti correnti nel file JSON.
                if (i < numAccounts - 1) writeChar(oc, ',');
            }
            // Scrivo una parentesi quadra chiusa sul file per segnalare
            // la fine della lista di conti correnti.
            writeChar(oc, ']');
		}
		catch (FileNotFoundException e) {
			System.err.println("File non trovato: " + e.getMessage());
			System.exit(1);
		}
		catch (IOException e) {
			System.err.println("Errore di I/O: " + e.getMessage());
			System.exit(1);
		}
        catch (ParseException e) {
            System.err.println("Errore di formattazione data: "
            + e.getMessage());
            System.exit(1);
        }
    }

    /**
     *  Metodo per generare una stringa casuale di lunghezza fissata.
     *  La stringa contiene solo caratteri alfabetici (a-z).
     *  @param length lunghezza della stringa
     *  @return la stringa casuale
     *
     *  Fonte: https://www.baeldung.com/java-random-string
     */
    public static String randomString(int length) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        return (gen.ints(leftLimit, rightLimit + 1)
            .limit(length)
            .collect(StringBuilder::new, StringBuilder::appendCodePoint,
            StringBuilder::append)
            .toString());
    }

    /**
     *  Genera una data causale compresa fra due estremi.
     *  @return una data (completa di orario) scelta a caso nell'intervallo
     */
    public static Date randomDate(Date start, Date end) {
        // NOTA: Qui uso ThreadLocalRandom solo per comodità
        // e solo perché mi permette di generare long in un dato intervallo.
        return new Date(ThreadLocalRandom.current().nextLong(
        start.getTime(), end.getTime()));
    }

    /**
     *  Restituisce una "causale casuale" fra quelle esistenti.
     *  @return una causale generata in modo casuale
     */
    public static Reason randomReason() {
        Reason[] values = Reason.values();
        int i = gen.nextInt(values.length);
        return values[i];
    }

    /**
     *  Metodo per scrivere un singolo carattere sul canale.
     *  @param channel riferimento al canale su cui scrivere
     *  @param c il carattere da scrivere
     */
    public static void writeChar(FileChannel channel, char c)
    throws IOException {
        CharBuffer charBuffer = CharBuffer.wrap(new char[]{c});
        ByteBuffer byteBuffer = Charset.forName("UTF-8").encode(charBuffer);
        // Leggo il contenuto del buffer e lo scrivo sul canale.
        channel.write(byteBuffer);
    }
}
