import java.io.File;

public class Produttore implements Runnable {
    CodaDirectory coda;
    File rootDir;

    public Produttore(CodaDirectory coda, File rootDir) {
        this.coda = coda;
        this.rootDir = rootDir;
    }

    private void scanDir(File dir, CodaDirectory coda) {
        File[] list = dir.listFiles();

        if (list != null) {
            for (File file : list) {
                if (file.isDirectory()) {
                    coda.add(file);
                    scanDir(file, coda);
                }
            }
        }
    }

    public void run() {
        coda.add(rootDir);
        scanDir(rootDir, coda);
    }
}
