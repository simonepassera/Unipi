import java.io.File;
import java.util.LinkedList;

public class CodaDirectory {
    private LinkedList<File> coda;

    public CodaDirectory() {
        coda = new LinkedList<File>();
    }

    public synchronized void add(File file) {
        coda.add(file);
        notifyAll();
    }

    public synchronized File remove() {
        while (coda.size() == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println("Consumatore interrotto!");
            }
        }

        return coda.removeFirst();
    }
}
