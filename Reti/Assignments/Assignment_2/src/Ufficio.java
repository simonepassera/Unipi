import java.util.concurrent.*;

public class Ufficio {
    public static final int numSportelli = 4;
    public static final int dimSala = 10;

    public static void main(String[] args) {
        // Leggo il numero di persone da riga di comando.
        if(args.length != 1) {
            System.err.println("Esegui come: java Ufficio <numero_persone>");
            System.exit(1);
        }
        int numPersone = Integer.parseInt(args[0]);

        // Creo una coda bloccante per memorizzare i task (persone) in arrivo.
        BlockingQueue<Runnable> sala = new LinkedBlockingDeque<Runnable>(dimSala);

        // Creo un pool di thread con al massimo 'numSportelli' thread.
        ExecutorService sportelli = new ThreadPoolExecutor(
                numSportelli, // Numero di thread da mantenere nel pool.
                numSportelli, // Numero massimo di thread possibili nel pool.
                0L, // Tempo di keep-alive per i thread.
                TimeUnit.SECONDS,
                sala, // Coda bloccante per i task.
                new ThreadPoolExecutor.AbortPolicy() // Politica di rifiuto di default
                // che solleva una RejectedExecutionException.
        );

        // Creazione dei task.
        for(int i = 0; i < numPersone; i++) {
            try {
                sportelli.execute(new Persona(i));
            }
            catch(RejectedExecutionException e) {
                System.err.printf("Viaggiatore %d: sala esaurita\n", i);
            }
        }
    }
}

