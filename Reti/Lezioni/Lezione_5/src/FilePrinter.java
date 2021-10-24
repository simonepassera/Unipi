/*
 *	Laboratorio di Reti A
 *	Soluzione dell'esercizio del 12/10/2021
 *	@author Matteo Loporchio
 */

import java.io.*;
import java.util.*;

public class FilePrinter {
	// Questa coda serve per esplorare ricorsivamente la directory.
	public static LinkedList<File> dirs = new LinkedList<>();
	// Nome del file su cui scrivere i file incontrati.
	public static String outFile1 = "files";
	// Nome del file su cui scrivere le directory incontrate.
	public static String outFile2 = "directories";

	public static void main(String[] args) {
		// Leggo gli argomenti da riga di comando.
		if (args.length < 1) {
			System.err.println(
			"Esegui come: FilePrinter <path>");
			System.exit(1);
		}
		// Provo ad aprire la cartella avente il percorso specificato.
		File rootDir = new File(args[0]);
		if (!rootDir.isDirectory()) {
			System.err.printf("%s non e' una directory valida!", args[0]);
			System.exit(1);
		}
		// Apro i due file su cui scrivere come stream di output.
		FileOutputStream fs1 = null, fs2 = null;
		try {
			fs1 = new FileOutputStream(new File(outFile1));
			fs2 = new FileOutputStream(new File(outFile2));
		}
		catch (Exception e) {
			System.err.println(
			"Errore nell'apertura dei file di output!");
			System.exit(1);
		}
		// Parto dalla directory specificata dall'utente ed
		// esploro ricorsivamente tutte le sottodirectory.
		dirs.add(rootDir);
		try {while (!dirs.isEmpty()) {
			File[] files = dirs.removeFirst().listFiles();
			for (File f : files) {
				// Ottengo il nome del file.
				String name = f.getName() + "\n";
				// Se incontro una directory, la metto in coda
				// per l'esplorazione e scrivo il suo nome sul
				// secondo file di output.
				if (f.isDirectory()) {
					dirs.add(f);
					fs2.write(name.getBytes());
				}
				// Altrimenti si tratta di un file e scrivo
				// il suo nome sul primo file di output.
				else fs1.write(name.getBytes());	
			}
		}}
		catch (IOException e) {
			// Finisco qui se qualcosa "va storto" durante la scrittura
			// sui file di output.
			System.err.println("Errore nella scrittura su file!");
			System.exit(1);
		}
		// Chiudo i file di output aperti in precedenza.
		try {fs1.close(); fs2.close();}
		catch (IOException e) {
			System.err.println("Errore nella chiusura dei file!");
			System.exit(1);
		}	
	}
}
