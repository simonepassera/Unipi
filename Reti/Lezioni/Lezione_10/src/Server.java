/**
 *  Laboratorio di Reti A
 *  Soluzione dell'esercizio del 30/11/2021
 *  @author Matteo Loporchio
 */

import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.*;

public class Server {
    // Numero di porta per il registry RMI.
    public static final int port = 12120;
    // Nome del servizio offerto dal server.
    public static final String serviceName = "RMIUserList";

    public static void main(String[] args) {
        try {
            // Creo e inizializzo la lista degli utenti.
            UserList list = new UserList();
            // Esporto l'oggetto, ottenendo lo stub corrispondente.
            UserListInterface stub = (UserListInterface) UnicastRemoteObject.exportObject(list, 0);
            // Creazione di un registry sulla porta specificata.
            LocateRegistry.createRegistry(port);
            Registry r = LocateRegistry.getRegistry(port);
            // Pubblicazione dello stub nel registry.
            r.rebind(serviceName, stub);
            System.out.printf(
            "Server pronto (nome servizio = %s, porta registry = %d)\n",
            serviceName, port);
        }
        catch (RemoteException e) {
            System.err.println("Errore: " + e.getMessage());
        }
    }
}
