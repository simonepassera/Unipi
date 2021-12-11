// @Author Simone Passera

import java.rmi.RemoteException;
import java.rmi.registry.*;
import java.util.*;

public class Speaker {
    // Numero di porta per il registry RMI.
    public static final int port = 12120;
    // Nome del servizio offerto dal server.
    public static final String serviceName = "GestioneCongresso";

    public static void main(String[] args) {
        try {
            // Ottengo un riferimento al registry.
            Registry r = LocateRegistry.getRegistry(port);

            ProgrammaCongressoInterface congresso = (ProgrammaCongressoInterface) r.lookup(serviceName);

            Scanner in = new Scanner(System.in);
            String request;
            String[] requestSplit;
            Integer day, session;

            while(true) {
                System.out.print("Request: ");
                request = in.nextLine();

                if(request.equals("exit")) break;

                requestSplit = request.split(" ");

                switch (requestSplit[0]) {
                    case "help":
                        System.out.println("Usage: program <day>");
                        System.out.println("       register <speaker_name> <day> <session>");
                        break;
                    case "program":
                        if (requestSplit.length != 2) { error(); break;}

                        try {
                            day = Integer.parseInt(requestSplit[1]);
                        } catch (NumberFormatException e) { error(); break; }

                        printProgram(congresso, day);

                        break;
                    case "register":
                        if (requestSplit.length != 4) { error(); break;}

                        try {
                            day = Integer.parseInt(requestSplit[2]);
                            session = Integer.parseInt(requestSplit[3]);
                        } catch (NumberFormatException e) { error(); break; }

                        switch (congresso.register(requestSplit[1], day, session)) {
                            case 0:
                                System.out.println("Speaker " + requestSplit[1] + " registered");
                                break;
                            case 1:
                                System.out.println("The day not exist!");
                                break;
                            case 2:
                                System.out.println("The session not exist!");
                                break;
                            case 3:
                                System.out.println("Session full!");
                                break;
                            case 4:
                                System.out.println("Speaker already exist!");
                        }

                        break;
                    default: error();
                }
            }
        }
        catch (Exception e) {
            System.err.println("Errore: " + e.getMessage());
        }
    }

    private static void error() {
        System.out.println("Bad request! (Try \"help\")");
    }

    private static void printProgram(ProgrammaCongressoInterface congresso, int day) throws RemoteException {
        HashMap<String, List<String>> program = congresso.program(day);

        System.out.println("DAY "+ day);

        for (int i = 1; i <= 12; i++) {
            System.out.print("S" + i + "\t| ");

            for (String name : program.get("S" + i)) {
                System.out.print(name + " ");
            }

            System.out.print("\n");
        }
    }
}
