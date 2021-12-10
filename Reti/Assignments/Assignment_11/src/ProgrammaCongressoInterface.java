import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.*;

public interface ProgrammaCongressoInterface extends Remote {
    // @Return map <Session, Speaker list> for day "day"
    //         null -> if day not exist
    public HashMap<String, List<String>> program(int day) throws RemoteException;

    // @Return 0 -> ok
    //         1 -> day not exist
    //         2 -> session not exist
    //         3 -> session full
    //         4 -> speaker already exist
    public int register(String name, int day, int session) throws RemoteException;
}
