/**
 *  Implementazione dell'esercizio 2 basata sulle lambda expression.
 *  @author Matteo Loporchio
 */

import java.util.Calendar;

public class DatePrinterLambda
{
    public static void main(String[] args)
    {
        // Creo un nuovo oggetto Runnable utilizzando una lambda expression.
        Runnable run = () -> {
            while (true)
            {
                // Stampo ora e data.
                System.out.println("Date: " + Calendar.getInstance().getTime());
                // Stampo il nome del thread corrente.
                System.out.println("Name: " + Thread.currentThread().getName());

                try
                {
                    Thread.sleep(2000);
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                    return;
                }
            }
        };

        // L'oggetto runnable viene poi passato (come di consueto)
        // al metdodo costruttore di un Thread.
        (new Thread(run)).start();
        // Questa istruzione stampa il nome del thread main.
        System.out.println(Thread.currentThread().getName());
    }
}