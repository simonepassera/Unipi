import java.rmi.*;
import java.rmi.registry.*;

public class Congresso {
    // Numero di porta per il registry RMI.
    public static final int port = 12120;
    // Nome del servizio offerto dal server.
    public static final String serviceName = "GestioneCongresso";

    public static void main(String[] args) {
        try {
            ProgrammaCongresso programma = new ProgrammaCongresso();
            // Creazione di un registry sulla porta specificata.
            LocateRegistry.createRegistry(port);
            Registry r = LocateRegistry.getRegistry(port);
            r.rebind(serviceName, programma);

            System.out.printf("Server running ... (Nome servizio = %s, Porta registry = %d)\n", serviceName, port);
        }
        catch (RemoteException e) {
            System.err.println(e.getMessage());
        }
    }
}
