/**
 *  Laboratorio di Reti A
 *  Soluzione dell'assignment
 *  @author Matteo Loporchio
 *
 *  Il client RMI è implementato in maniera interattiva.
 *  Ci sono tre comandi possibili:
 *
 *      1) "exit" per far terminare il programma
 *      2) "add <sessionId> <speaker>" per aggiungere un utente a una sessione
 *      3) "show <sessionId>" per visualizzare i partecipanti a una sessione
 */

import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.*;
import java.util.*;

public class Client {
    public static void main(String[] args) {
        // Leggo i parametri da riga di comando.
        if (args.length < 2) {
            System.err.println("Esegui come: Client <serviceName> <port>");
            System.exit(1);
        }
        // Nome del servizio offerto dal server.
        String serviceName = args[0];
        // Numero di porta per il registry RMI.
        int port = Integer.parseInt(args[1]);
        try {
            // Ottengo un riferimento al registry.
            Registry r = LocateRegistry.getRegistry(port);
            // Ottengo un riferimento alla lista remota.
            ManagerInterface m = (ManagerInterface) r.lookup(serviceName);
            Scanner inputScanner = new Scanner(System.in);
            while (true) {
                System.out.println("Inserisci un comando:");
                // Leggo l'input dell'utente.
                String line = inputScanner.nextLine();
                // Se il comando dato è "exit", allora termino.
                if (line.equals("exit")) break;
                String[] parts = line.split(" ");
                // Interpreto il comando "add <sessionId> <speaker>".
                if (parts[0].equals("add") && parts.length == 3) {
                    int sessionId = Integer.parseInt(parts[1]);
                    try {
                        m.addSpeaker(sessionId, parts[2]);
                        System.out.printf(
                        "Aggiunto l'utente %s nella sessione %d\n", parts[2],
                        sessionId);
                    }
                    catch (RemoteException e) {
                        System.err.println("Errore lato server: "
                        + e.getMessage());
                    }
                }
                // Interpreto il comando "show <sessionId>".
                else if (parts[0].equals("show") && parts.length == 2) {
                    int sessionId = Integer.parseInt(parts[1]);
                    try {
                        List<String> speakers = m.getSpeakers(sessionId);
                        System.out.printf("Sessione %d: ", sessionId);
                        for (String s : speakers) System.out.printf("%s ", s);
                        System.out.print("\n");
                    }
                    catch (RemoteException e) {
                        System.err.println("Errore lato server: "
                        + e.getMessage());
                    }
                }
                // Altrimenti si tratta di un comando non riconosciuto
                // oppure di un comando con una sintassi non corretta.
                else System.err.println("Errore: sintassi comando non valida");
            }
        }
        catch (Exception e) {
            System.err.println("Errore: " + e.getMessage());
        }
    }
}
