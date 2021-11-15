import com.google.gson.*;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.nio.*;
import java.nio.channels.FileChannel;
import java.util.*;
import java.util.concurrent.*;

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

            Calendar bound = Calendar.getInstance();
            Calendar origin = Calendar.getInstance();
            origin.add(Calendar.YEAR, -2);

            // Alloco un buffer avente la dimensione fissata.
            ByteBuffer buf = ByteBuffer.allocate(bufSize);

            buf.clear();
            buf.put("[\n".getBytes());
            buf.flip();
            while (buf.hasRemaining()) ch.write(buf);

            for (int i = 0; i < numContiCorrente; i++) {
                cc = new ContoCorrente("User-test-" + (i+1));

                for (int j = 0; j < (ThreadLocalRandom.current().nextInt(numMaxMovimenti) + 1); j++) {
                    cc.add(new Date(ThreadLocalRandom.current().nextLong(origin.getTimeInMillis(), bound.getTimeInMillis())), causali[ThreadLocalRandom.current().nextInt(causali.length)], ThreadLocalRandom.current().nextInt(1000));
                }

                buf.clear();
                buf.put(gson.toJson(cc).getBytes());
                if(i != (numContiCorrente - 1)) buf.put(",\n".getBytes());
                buf.flip();

                while (buf.hasRemaining()) ch.write(buf);
            }

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


        try (
                FileInputStream inFile = new FileInputStream(fileJson)
        ) {
            JsonReader reader = new JsonReader(new InputStreamReader(inFile));

            reader.beginArray();

            while (reader.hasNext()) {
                pool.execute(new Scan(gson.fromJson(reader, ContoCorrente.class)));
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

        // TODO

    }
}
