import java.util.concurrent.locks.*;

public class Laboratorio {

    private Boolean[] computers;

    private Lock lock;
    private Condition cond_set;
    private Condition cond_reset;

    private int num_occupati;

    public Laboratorio(int size) {
        computers = new Boolean[size];

        for(int i = 0; i < size; i++)
        {
            computers[i] = false;
        }

        lock = new ReentrantLock();
        cond_set = lock.newCondition();
        cond_reset = lock.newCondition();
    }

    public void setPc(int i) throws InterruptedException {
        try {
            lock.lock();
            while(num_occupati == computers.length && computers[i]) {
                cond_reset.await();
            }
            computers[i] = true;
            num_occupati++;
            cond_set.signal();
        } finally {
            lock.unlock();
        }
    }

    public void setAll() throws InterruptedException {
        try {
            lock.lock();

            while(num_occupati > 0) {
                cond_reset.await();
            }

            for(int i = 0; i < computers.length; i++) {
                num_occupati++;
                computers[i] = true;
            }
            cond_set.signal();
        } finally {
            lock.unlock();
        }
    }

    public int set() throws InterruptedException {

        int pc = 0;

        try {
            lock.lock();

            while(num_occupati == computers.length) {
                cond_reset.await();
            }

            for(int i = 0; i < computers.length; i++) {
                if(!computers[i]) {
                    computers[i] = true;
                    pc = i;
                    break;
                }
            }

            num_occupati++;
            cond_set.signal();
        } finally {
            lock.unlock();
        }

        return pc;
    }

    public void reset(int i) throws InterruptedException {
        try {
            lock.lock();

            while(num_occupati == 0 && !computers[i]) {
                cond_set.await();
            }

            computers[i] = false;
            num_occupati--;
            cond_reset.signal();
        } finally {
            lock.unlock();
        }
    }

    public void resetAll() throws InterruptedException {
        try {
            lock.lock();

            while (num_occupati < computers.length) {
                cond_set.await();
            }

            for(int i = 0; i < computers.length; i++) {
                num_occupati--;
                computers[i] = true;
            }
            cond_reset.signal();
        } finally {
            lock.unlock();
        }
    }
}
