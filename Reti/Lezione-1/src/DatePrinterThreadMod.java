/**
 *  Questa è una classe di esempio basata su DatePrinterThread.
 *  Il suo unico scopo è quello di dimostrare che è sempre possibile
 *  aggiungere metodi (pubblici o privati) dentro una classe X che estende
 *  la classe Thread. X è una classe come tutte le altre!
 *
 *  @author Matteo Loporchio
 */

import java.util.Calendar;

public class DatePrinterThreadMod extends Thread
{
    /**
     *  Questo metodo restituisce sempre il valore 0.
     */
    public int zero()
    {
        return 0;
    }

    /**
     *  Questo è il metodo contenente la logica del thread.
     */
    public void run()
    {
        while(true)
        {
            // Stampa data e nome del thread corrente.
            System.out.println("Date: " + Calendar.getInstance().getTime());
            System.out.println("Name: " + Thread.currentThread().getName());

            try
            {
                // Metti in pausa il thread per 2 secondi.
                Thread.sleep(2000);
            }
            catch(InterruptedException e)
            {
                // Gestione delle eccezioni.
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

    /**
     *  Metodo main che viene invocato non appena si esegue questa classe.
     */
    public static void main(String[] args)
    {
        // Crea e avvia un nuovo thread DatePrinterThreadMod.
        DatePrinterThreadMod dp = new DatePrinterThreadMod();
        dp.start();
        // Invoca il metodo pubblico 'zero' presente in ogni oggetto
        // di tipo DatePrinterThreadMod e stampa il risultato.
        System.out.println(dp.zero());
        // Stampa il nome del thread main.
        System.out.println("Name: " + Thread.currentThread().getName());
    }
}