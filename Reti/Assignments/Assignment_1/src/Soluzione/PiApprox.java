/**
 *	Assignment 1 - Laboratorio di Reti A.A. 2021/2022
 *	
 *	Questo programma calcola un'approssimazione del Pi Greco 
 *	usando un thread dedicato.
 *	
 *	Puo' essere eseguito nel modo seguente:
 *	
 *		java PiApprox <eps> <timeout>
 *	
 *	dove <eps> rappresenta la precisione desiderata (es. 1E-8)
 *	e <timeout> e' il massimo numero di millisecondi da aspettare
 *	prima della terminazione (es. 10000).
 *	
 *	@author Matteo Loporchio
 */

import java.io.*;

public class PiApprox {
	public static void main(String[] args) {
		// Controllo il numero dei parametri da riga di comando
		// ed eseguo il parsing delle stringhe corrispondenti.
		if (args.length < 2) {
			System.err.println("Esegui come: PiApprox <eps> <timeout>");
			System.exit(1);
		}
		double eps = Double.parseDouble(args[0]);
		long timeout = Long.parseLong(args[1]);
		System.out.printf(
		"Avvio di PiApprox con eps = %.10f e timeout = %d ms\n", eps, timeout);
		// Avvio il calcolo di Pi in un thread dedicato.
		Thread calculator = new Thread(new PiApproxComp(eps));
		calculator.start();
		// Eseguo una join aspettando al massimo `timeout` millisecondi. 
		try {
			calculator.join(timeout);
			// Interrompo il thread di calcolo, se necessario.
			if (calculator.isAlive()) calculator.interrupt();
		}
		catch (InterruptedException e) {
			// Finisco qui solo se il main e' stato interrotto.
			e.printStackTrace();
			System.exit(1);
		}
	}
}

/**
 *	Con questa classe definisco il thread dedicato
 *	che esegue il calcolo di Pi Greco.
 */
class PiApproxComp implements Runnable {
	public double eps;

	/**
	 *	Costruttore della classe.
	 *	@param eps precisione desiderata
	 */
	public PiApproxComp(double eps) {
		this.eps = eps;
	}

	/**
	 *	Metodo run dove viene definita la logica
	 *	del thread.
	 */
	public void run() {
		boolean interrupted = false;
		double pi = 0;
		int i = 0, sign = 1;
		while (!(interrupted = Thread.currentThread().isInterrupted()) && 
		Math.abs(Math.PI - pi) > eps) 
		{
			pi += sign * (4.0 / (2 * i + 1));
			sign = -sign;
			i++;
		}
		// Se il thread e' stato interrotto dopo l'intervallo di tempo
		// stampo un messaggio per segnalare l'evento.
		if (interrupted) System.out.println("Limite di tempo raggiunto");
		// Infine, stampo il valore calcolato di pi.
		System.out.printf("pi = %.10f\n", pi);
	}
}
