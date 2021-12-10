/*
 *  Laboratorio di Reti A
 *  Soluzione dell'esercizio del 30/11/2021
 *  @author Matteo Loporchio
 */

import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.*;
import java.util.*;

public class Client {
    // Numero di porta per il registry RMI.
    public static final int port = 12120;
    // Nome del servizio offerto dal server.
    public static final String serviceName = "RMIUserList";

    public static void main(String[] args) {
        // Leggo il parametro (nome utente da aggiungere) da riga di comando.
        if (args.length < 1) {
            System.err.println("Esegui come: Client <username>");
            System.exit(1);
        }
        String username = args[0];
        try {
            // Ottengo un riferimento al registry.
            Registry r = LocateRegistry.getRegistry(port);
            // Ottengo un riferimento alla lista remota.
            UserListInterface list = (UserListInterface) r.lookup(serviceName);
            // Invoco il metodo remoto per aggiungere l'utente alla lista.
            list.addUser(username);
            System.out.println("Aggiunto l'utente: " + username);
            // Ora stampo la lista di utenti, uno per riga.
            System.out.println("Utenti attualmente registrati:");
            // Ottengo la lista tramite invocazione del metodo remoto.
            List<String> users = list.getList();
            for (String u : users) System.out.println(u);
        }
        catch (Exception e) {
            System.err.println("Errore: " + e.getMessage());
        }
    }
}
