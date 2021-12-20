/**
 *  Laboratorio di Reti A
 *  Soluzione dell'assignment
 *  @author Matteo Loporchio
 */

import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.*;

public class Server {
    public static void main(String[] args) {
        // Leggo i parametri da riga di comando.
        if (args.length < 2) {
            System.err.println("Esegui come: Server <serviceName> <port>");
            System.exit(1);
        }
        // Nome del servizio offerto dal server.
        String serviceName = args[0];
        // Numero di porta per il registry RMI.
        int port = Integer.parseInt(args[1]);
        try {
            // Creo e inizializzo l'oggetto remoto.
            Manager manager = new Manager();
            // Esporto l'oggetto, ottenendo lo stub corrispondente.
            ManagerInterface stub = (ManagerInterface)
            UnicastRemoteObject.exportObject(manager, 0);
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
