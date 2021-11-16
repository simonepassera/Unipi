// @Author Simone Passera

import com.google.gson.*;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.nio.*;
import java.nio.channels.FileChannel;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Banca {
    private static final String fileJson = "banca.json";
    private static int numContiCorrente = 10;
    private static int numMaxMovimenti = 100;
    private static final int bufSize = 8192;
    private static final int terminationDelay = 10; // SECONDS

    public static void main(String[] args) {
        if (args.length >= 1) {
            try {
                numContiCorrente = Integer.parseInt(args[0]);
                if (numContiCorrente <= 0) throw new NumberFormatException();

                if (args.length >= 2) {
                    numMaxMovimenti = Integer.parseInt(args[1]);
                    if (numMaxMovimenti <= 0) throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                System.out.println("Usage: java Banca [numero_conti_correnti] [numero_massimo_movimenti]");
                System.exit(1);
            }
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (
                FileOutputStream outFile = new FileOutputStream(fileJson);
                FileChannel ch = outFile.getChannel()
        ) {
            ContoCorrente cc;
            ContoCorrente.Causale[] causali = ContoCorrente.Causale.values();

            // Creo due date (quella di oggi e di due anni fa) per generare date random tra le due
            Calendar bound = Calendar.getInstance();
            Calendar origin = Calendar.getInstance();
            origin.add(Calendar.YEAR, -2);

            // Alloco un buffer avente la dimensione fissata
            ByteBuffer buf = ByteBuffer.allocateDirect(bufSize);

            // Scrivo sul file "["
            buf.clear();
            buf.put("[\n".getBytes());
            buf.flip();
            while (buf.hasRemaining()) ch.write(buf);

            for (int i = 0; i < numContiCorrente; i++) {
                // Creo un conto corrente
                cc = new ContoCorrente("Utente-test-" + (i+1));

                // Aggiungo al massimo "numMaxMovimenti" al conto corrente, con data, valuta e causale random
                for (int j = 0; j < (ThreadLocalRandom.current().nextInt(numMaxMovimenti) + 1); j++) {
                    cc.add(new Date(ThreadLocalRandom.current().nextLong(origin.getTimeInMillis(), bound.getTimeInMillis())), causali[ThreadLocalRandom.current().nextInt(causali.length)], ThreadLocalRandom.current().nextInt(1000));
                }

                // Serializzo il conto corrente in json e lo scrivo sul file
                buf.clear();
                buf.put(gson.toJson(cc).getBytes());
                if(i != (numContiCorrente - 1)) buf.put(",\n".getBytes());
                buf.flip();

                while (buf.hasRemaining()) ch.write(buf);
            }

            // Scrivo sul file "]"
            buf.clear();
            buf.put("\n]".getBytes());
            buf.flip();
            while (buf.hasRemaining()) ch.write(buf);
        } catch (FileNotFoundException e) {
            System.err.println("Errore file " + fileJson + ": " + e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Errore di I/O: " + e.getMessage());
            System.exit(1);
        }

        ExecutorService pool = Executors.newFixedThreadPool(10);

        // Array che contiene i contatori globali del numero totale delle causali
        AtomicInteger[] counter = new AtomicInteger[ContoCorrente.Causale.values().length];

        // Inizializzo i contatori a 0
        for (int i = 0; i < counter.length; i++) {
            counter[i] = new AtomicInteger();
        }

        try (
                FileInputStream inFile = new FileInputStream(fileJson)
        ) {
            JsonReader reader = new JsonReader(new InputStreamReader(inFile));

            reader.beginArray();

            // Deserializzo il conto corrente json e passo l'oggetto al pool di thread
            while (reader.hasNext()) {
                pool.execute(new Scan(gson.fromJson(reader, ContoCorrente.class), counter));
            }

            reader.endArray();
        } catch (FileNotFoundException e) {
            System.err.println("Errore file " + fileJson + ": " + e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Errore di I/O: " + e.getMessage());
            System.exit(1);
        }

        pool.shutdown();

        try {
            if(!pool.awaitTermination(terminationDelay, TimeUnit.SECONDS)) {
                pool.shutdownNow();
            }
        } catch (InterruptedException e) {
            pool.shutdownNow();
        }

        System.out.println("Numero totale di movimenti per ogni causale:\n");

        for (ContoCorrente.Causale c : ContoCorrente.Causale.values()) {
            System.out.println(c.name() + ": " + counter[c.ordinal()]);
        }
    }
}
