/*
 *  Soluzione degli esercizi 1 e 2. (21/9/2021)
 *  @author Matteo Loporchio
 */

import java.util.concurrent.*;

public class Sala {
  public static final int numViaggiatori = 50;
  public static final int numEmettitrici = 5;
  public static final int dimCoda = 10;
  public static final int taskDelay = 50;
  public static final int terminationDelay = 5000;

  public static void main(String[] args) {
    // Creo una coda bloccante per memorizzare i task (viaggiatori) in arrivo.
    BlockingQueue<Runnable> q = new ArrayBlockingQueue<Runnable>(dimCoda);
    // Creo un pool di thread con al massimo `numEmettitrici` thread.
    ExecutorService pool = new ThreadPoolExecutor(
      numEmettitrici, // Numero di thread da mantenere nel pool.
      numEmettitrici, // Numero massimo di thread possibili nel pool.
      terminationDelay, // Tempo di keep-alive per i thread.
      TimeUnit.MILLISECONDS,
      q, // Coda bloccante per i task.
      // Politica di rifiuto di default
      // che solleva una RejectedExecutionException.
      new ThreadPoolExecutor.AbortPolicy()
    );
    // Creazione dei task.
    for (int i = 0; i < numViaggiatori; i++) {
      try {pool.execute(new Viaggiatore(i));}
      // Catturo qui l'eccezione sollevata con la AbortPolicy
      // in caso di coda piena.
      catch (RejectedExecutionException e) {
        System.err.printf("Viaggiatore %d: sala esaurita\n", i);
      }
      // Attendo un intervallo di tempo prima di creare un nuovo task.
      try {Thread.sleep(taskDelay);}
      catch (InterruptedException e) {
        System.err.println("Interruzione su sleep.");
        System.exit(1);
      }
    }
    // Terminazione del thread pool. Adottiamo il seguente metodo:
    // 1) Smetto di accettare nuovi task.
    // 2) Successivamente si aspetta un certo intervallo di tempo affinché
    //    tutti i thread possano terminare.
    // 3) Passato l'intervallo, l'esecuzione del pool viene interrotta
    //    immediatamente.
    pool.shutdown();
    try {
      if (!pool.awaitTermination(terminationDelay, TimeUnit.MILLISECONDS))
        pool.shutdownNow();
    }
    catch (InterruptedException e) {pool.shutdownNow();}
  }
}

/**
 *  Ogni viaggiatore viene simulato da un task che esegue le seguenti
 *  operazioni:
 *  1) Stampare "Viaggiatore {id}: sto acquistando un biglietto";
 *  2) Aspettare per un intervallo di tempo random tra 0 e 1000 ms;
 *  3) Stampare "Viaggiatore {id}: ho acquistato il biglietto".
 */
class Viaggiatore implements Runnable {
  public final int minDelay = 0;
  public final int maxDelay = 1000;
  public final int id;

  /**
   *  Costruttore della classe Viaggiatore.
   *  @param id l'identificativo del viaggiatore
   */
  public Viaggiatore(int id) {
    this.id = id;
  }

  /**
   *  Il metodo principale del task.
   */
  public void run() {
    System.out.printf("Viaggiatore %d: sto acquistando un biglietto\n", id);
    // Aspetto un intervallo di tempo compreso fra 0 e 1000 ms (inclusi).
    int delay = ThreadLocalRandom.current().nextInt(minDelay, maxDelay + 1);
    try {Thread.sleep(delay);}
    catch (InterruptedException e) {
      System.err.println("Interruzione su sleep.");
    }
    System.out.printf("Viaggiatore %d: ho acquistato il biglietto\n", id);
  }
}
