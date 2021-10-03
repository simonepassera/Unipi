/*
 * @author Simone Passera
 */

import java.util.concurrent.*;

public class Ufficio {
    public static final int numSportelli = 4;
    public static final int dimSala2 = 8;

    public static void main(String[] args) {
        // Leggo il numero di persone da riga di comando.
        if(args.length != 1) {
            System.err.println("Usage: java Ufficio <numero_persone>");
            System.exit(1);
        }

        int numPersone = Integer.parseInt(args[0]);

        // Creo una coda bloccante per memorizzare i task (persone) in attesa nella sala1.
        BlockingQueue<Runnable> sala1 = new LinkedBlockingDeque<Runnable>();

        // Creo una coda bloccante per memorizzare i task (persone) in attesa nella sala2.
        BlockingQueue<Runnable> sala2 = new ArrayBlockingQueue<Runnable>(dimSala2);

        // Creo un pool di thread con al massimo 'numSportelli' thread.
        ExecutorService sportelli = new ThreadPoolExecutor(
                numSportelli, // Numero di thread da mantenere nel pool.
                numSportelli, // Numero massimo di thread possibili nel pool.
                0L, // Tempo di keep-alive per i thread.
                TimeUnit.SECONDS,
                sala2, // Coda bloccante per i task.
                new RejectedExecutionHandler() {
                    @Override
                    public void rejectedExecution(Runnable persona, ThreadPoolExecutor sportelli) {
                        try {
                            sportelli.getQueue().put(persona);
                        } catch(InterruptedException e) {
                            System.err.println("Interruzione put (sala2).");
                        }
                    }
                } // Politica di rifiuto: la persona rifiutata in sala2 aspetta che sia libera
        );

        // Creazione dei task (persone).
        for(int i = 1; i <= numPersone; i++) {
            sala1.add(new Persona(i));
        }

        // Sposto le persone dalla sala1 in sala2.
        for(int i = 1; i <= numPersone; i++) {
            try {
                sportelli.execute(sala1.take());
            } catch(RejectedExecutionException e) {
                System.err.printf("Persona %d: sala2 esaurita\n", i);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }

        sportelli.shutdown(); // Chiudo gli sportelli, dopo che la sala2 è vuota
    }
}

