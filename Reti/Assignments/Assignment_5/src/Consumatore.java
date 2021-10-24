import java.io.File;

public class Consumatore implements Runnable {
    CodaDirectory coda;

    public Consumatore(CodaDirectory coda) {
        this.coda = coda;
    }

    public void run() {
        File dir;
        File[] list;
        StringBuilder contenuto = new StringBuilder();

        while (true) {
            dir = coda.remove();

            contenuto.append(dir.getName()).append(":\n");
            list = dir.listFiles();

            for (File file : list) {
                if(!file.isDirectory()) {
                    contenuto.append("\t").append(file.getName()).append("\n");
                }
            }

            System.out.println(contenuto);
        }
    }
}
