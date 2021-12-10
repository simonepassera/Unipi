/*
 *  Laboratorio di Reti A
 *  Soluzione dell'esercizio del 30/11/2021
 *  @author Matteo Loporchio
 */

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface UserListInterface extends Remote {
    /**
     *  Metodo per aggiungere un utente alla lista.
     *  @param username il nome dell'utente
     */
    public void addUser(String username) throws RemoteException;

    /**
     *  Metodo che restituisce la lista degli utenti attualmente registrati.
     *  @return la lista degli utenti registrati
     */
    public List<String> getList() throws RemoteException;
}
