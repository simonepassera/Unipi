/**
 *	Laboratorio di Reti A
 *	Soluzione dell'esercizio del 9/11/2021
 *	@author Matteo Loporchio
 */

import java.io.*;
import java.nio.*;
import java.nio.channels.FileChannel;
import java.util.*;

public class Counter {
	// Nome del file di input.
	public static final String inputFile = "random.txt";
	// Nome del file di output.
	public static final String outputFile = "freq.txt";
	// Dimensione del buffer (in byte).
	public static final int bufSize = 8192;
	// Map per memorizzare le frequenze dei caratteri.
	public static Map<Character, Integer> freq = new HashMap<>();

	public static void main(String[] args) {
		// Apro i file e i canali associati agli stream.
		// Uso il costrutto "try with resources" per semplificare la chiusura.
		try (
			FileInputStream is = new FileInputStream(inputFile);
			FileOutputStream os = new FileOutputStream(outputFile);
			FileChannel ic = is.getChannel();
			FileChannel oc = os.getChannel();
		) {
			// Alloco un buffer avente la dimensione fissata.
			ByteBuffer buf = ByteBuffer.allocate(bufSize);
			// Leggo il contenuto del canale e lo scrivo nel buffer.
			while (ic.read(buf) > 0) {
				// Preparo il buffer per la lettura.
				// NOTA: con la read() precedente ho scritto dati nel buffer.
				buf.flip();
				while (buf.hasRemaining()) {
					char c = (char) buf.get();
					// Controllo se il carattere è una lettera.
					// In caso contrario, leggo il prossimo.
					if (!Character.isLetter(c)) continue;
					// In caso affermativo, lo interpreto come minuscolo.
					c = Character.toLowerCase(c);
					Integer count = freq.get(c);
					if (count == null) freq.put(c, 1);
					else freq.put(c, count + 1);
				}
				// Preparo il buffer per la successiva scrittura
				// che avviene nella guardia del while.
				buf.clear();
			}
			// Stampo le frequenze su schermo e sul file di output.
			for (var e : freq.entrySet()) {
				String line = e.getKey() + "\t" + e.getValue() + "\r\n";
    			System.out.print(line);
				// Preparo il buffer per la scrittura.
				buf.clear();
				// Scrivo la riga nel buffer.
				buf.put(line.getBytes());
				// Preparo il buffer per la lettura.
				buf.flip();
				// Leggo il contenuto del buffer e lo scrivo sul canale.
				while (buf.hasRemaining()) oc.write(buf);
			}
		}
		catch (FileNotFoundException e) {
			System.err.println("File non trovato: " + e.getMessage());
			System.exit(1);
		}
		catch (IOException e) {
			System.err.println("Errore di I/O: " + e.getMessage());
			System.exit(1);
		}
	}
}
