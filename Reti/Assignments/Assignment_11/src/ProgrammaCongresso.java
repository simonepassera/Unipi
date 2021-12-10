import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.concurrent.locks.*;

public class ProgrammaCongresso extends UnicastRemoteObject implements ProgrammaCongressoInterface {

    HashMap<String, List<String>> day1, day2, day3;
    ReadWriteLock locksDay1;
    Lock readLockDay1;
    Lock writeLockDay1;
    ReadWriteLock locksDay2;
    Lock readLockDay2;
    Lock writeLockDay2;
    ReadWriteLock locksDay3;
    Lock readLockDay3;
    Lock writeLockDay3;


    public ProgrammaCongresso() throws RemoteException {
        super();

        day1 = new HashMap<>();
        day2 = new HashMap<>();
        day3 = new HashMap<>();

        for (int i = 1; i <= 12; i++) {
            day1.put("S" + i, new ArrayList<>(5));
            day2.put("S" + i, new ArrayList<>(5));
            day3.put("S" + i, new ArrayList<>(5));
        }

        locksDay1 = new ReentrantReadWriteLock();
        readLockDay1 = locksDay1.readLock();
        writeLockDay1 = locksDay1.writeLock();
        locksDay2 = new ReentrantReadWriteLock();
        readLockDay2 = locksDay2.readLock();
        writeLockDay2 = locksDay2.writeLock();
        locksDay3 = new ReentrantReadWriteLock();
        readLockDay3 = locksDay3.readLock();
        writeLockDay3 = locksDay3.writeLock();
    }

    public HashMap<String, List<String>> program(int day) throws RemoteException {
        switch (day) {
            case 1:
                readLockDay1.lock();
                try { return day1; } finally { readLockDay1.unlock(); }
            case 2:
                readLockDay2.lock();
                try { return day2; } finally { readLockDay2.unlock(); }
            case 3:
                readLockDay3.lock();
                try { return day3; } finally { readLockDay3.unlock(); }
            default:
                return null;
        }
    }

    public int register(String name, int day, int session) throws RemoteException {
        if (day < 1 || day > 3) return 1;
        if (session < 1 || session > 12) return 2;

        HashMap<String, List<String>> dayRequest = null;
        Lock lock = null;

        switch (day) {
            case 1:
                dayRequest = day1;
                lock = writeLockDay1;
                break;
            case 2:
                dayRequest = day2;
                lock = writeLockDay2;
                break;
            case 3:
                dayRequest = day3;
                lock = writeLockDay3;
        }

        lock.lock();

        try {
            List<String> interventi = dayRequest.get("S" + session);

            if (interventi.size() == 5) return 3;
            if (interventi.contains(name)) return 4;

            interventi.add(name);
            dayRequest.put("S" + session, interventi);
        } finally {
            lock.unlock();
        }

        return 0;
    }
}
