import java.io.File;

public class FileCrawler {
    // Questa coda serve per esplorare ricorsivamente la directory.
    public static CodaDirectory dirs;
    public static int k = 10;

    public static void main(String[] args) {
        // Leggo il filepath da riga di comando.
        if (args.length != 1) {
            System.err.println("Usage: FileCrawler <directory>");
            System.exit(1);
        }

        // Provo ad aprire la cartella avente il percorso specificato.
        File rootDir = new File(args[0]);

        if (!rootDir.isDirectory()) {
            System.err.printf("%s non e' una directory valida!\n", args[0]);
            System.exit(1);
        }

        dirs = new CodaDirectory();

        for (int i = 0; i < k; i++) {
            new Thread(new Consumatore(dirs)).start();
        }

        new Thread(new Produttore(dirs, rootDir)).start();
    }
}