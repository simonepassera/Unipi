import java.util.concurrent.atomic.AtomicInteger;

public class Scan implements Runnable {
    private ContoCorrente cc;
    private AtomicInteger[] counter;

    public Scan(ContoCorrente cc, AtomicInteger[] counter) {
        this.cc = cc;
        this.counter = counter;
    }

    @Override
    public void run() {
        for (Movimento m : cc.get()) {
            counter[m.getCausale().ordinal()].incrementAndGet();
        }
    }
}
