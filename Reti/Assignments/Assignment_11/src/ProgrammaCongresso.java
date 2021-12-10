import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.concurrent.locks.*;

public class ProgrammaCongresso extends UnicastRemoteObject implements ProgrammaCongressoInterface {

    HashMap<String, List<String>> day1, day2, day3;
    ReadWriteLock locks = new ReentrantReadWriteLock();
    Lock readLock = locks.readLock();
    Lock writeLock = locks.writeLock();

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
    }

    public HashMap<String, List<String>> program(int day) throws RemoteException {
        readLock.lock();

        try {
            switch (day) {
                case 1:
                    return day1;
                case 2:
                    return day2;
                case 3:
                    return day3;
                default:
                    return null;
            }
        } finally {
            readLock.unlock();
        }
    }

    public int register(String name, int day, int session) throws RemoteException {
        writeLock.lock();

        try {
            if (day < 1 || day > 3) return 1;
            if (session < 1 || session > 12) return 2;

            HashMap<String, List<String>> dayRequest = null;

            switch (day) {
                case 1:
                    dayRequest = day1;
                    break;
                case 2:
                    dayRequest = day2;
                    break;
                case 3:
                    dayRequest = day3;
            }

            List<String> interventi = dayRequest.get("S" + session);

            if (interventi.size() == 5) return 3;

            if (interventi.contains(name)) return 4;

            interventi.add(name);
            dayRequest.put("S" + session, interventi);

            return 0;
        } finally {
            writeLock.unlock();
        }
    }
}
