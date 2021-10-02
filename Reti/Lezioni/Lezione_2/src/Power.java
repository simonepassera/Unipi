/*
 *  Soluzione dell'esercizio 3. (21/9/2021)
 *
 *  Per eseguire il programma:
 *    java Power <base>
 *  dove <base> è il valore da elevare a potenza.
 *
 *  @author Matteo Loporchio
 */

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.*;

public class Power implements Callable<Double> {
  public double base;
  public int exp;

  /**
   *  Costruttore della classe.
   *  @param base base per l'elevamento a potenza
   *  @param exp esponente per l'elevamento a potenza
   */
  public Power(double base, int exp) {
    this.base = base;
    this.exp = exp;
  }

  /**
   *  Questo è il metodo principale dell'interfaccia Callable.
   */
  public Double call() throws Exception {
    // Controllo se l'esponente è compreso nell'intervallo [2, 50].
    // In caso contrario, lancio un'eccezione.
    if (!(2 <= exp && exp <= 50)) {
      throw new Exception("Esponente " + exp + " non valido!");
    }
    // Stampo le informazioni sul task corrente.
    System.out.printf("Esecuzione (%f)^(%d) in %d\n", base, exp,
    Thread.currentThread().getId());
    // Calcolo e restituisco il risultato.
    return Math.pow(base, exp);
  }

  public static void main(String[] args) {
    // Leggo l'esponente dalla riga di comando.
    if (args.length != 1) {
      System.err.println("Esegui come: java Power <base>");
      System.exit(1);
    }
    double base = Double.parseDouble(args[0]);
    // Creo un pool di thread.
    ExecutorService pool = Executors.newCachedThreadPool();
    // Creo i nuovi task e li invio al pool.
    List<Future<Double>> tasks = new ArrayList<Future<Double>>();
    for (int i = 2; i <= 50; i++) tasks.add(pool.submit(new Power(base, i)));
    // Calcolo il risultato finale, scorrendo la lista dei future
    // e accumulando i risultati parziali nella variabile result.
    double result = 0;
    try {for (Future<Double> f : tasks) result += f.get();}
    catch (Exception e) {
      // Catturo eventuali eccezioni.
      e.printStackTrace();
    }
    // Chiudo il pool di thread.
    pool.shutdown();
    // Stampa del risultato finale.
    System.out.println("Risultato: " + result);
  }
}
