/*
 * Scrivere un programma che attiva un thread T che effettua il calcolo approssimato di π.

/**
 * @author Andrea Michienzi
 *
 * Soluzione che utilizza un Task e il thread del calcolo piGreco interrompe il Thread main
 * appena ha raggiunto l'approssimazione desiderata.
 */
public class PiGrecoTaskMainInterrupted implements Runnable {
	double piApprox = 0;
	private double accuracy;
	private Thread main;
	public PiGrecoTaskMainInterrupted(double accuracy, Thread main) {
		this.accuracy = accuracy;
		this.main = main;
	}
	@Override
	public void run() {
		System.out.printf("%s - Math.PI= %.30f \n", Thread.currentThread().getName(), Math.PI);
		int iteration = 0;
		while(!Thread.currentThread().isInterrupted() && Math.abs(piApprox - Math.PI) > accuracy) {
			if (iteration % 2 == 0)
				piApprox = piApprox + (4.0 / (2 * iteration + 1));
			else
				piApprox = piApprox - (4.0 / (2 * iteration + 1));
			iteration++;
		}
		if(Math.abs(piApprox - Math.PI) > accuracy)
			System.out.printf("%s - interrotto dal main \n", Thread.currentThread().getName());
		else
			main.interrupt();
		System.out.printf("%s - Approx PI: %.30f\n", Thread.currentThread().getName(),
				piApprox);
		System.out.printf("%s - Accuracy: %f\n", Thread.currentThread().getName(), accuracy);
	}
	
	public static void main(String[] args) {
		double accuracy = 0.0001;
		int timeout = 100000;
		
		System.out.printf("main - accuracy %f\n", accuracy);
		Thread calculator = new Thread(new PiGrecoTaskMainInterrupted(accuracy,Thread.currentThread())); //NB passo il riferimento al main
		calculator.start();
		try {
			Thread.sleep(timeout);
			System.out.print("main - send interrupt to thread\n");
			calculator.interrupt();
		} catch (InterruptedException e) {
			System.out.print("main - interrupted by thread\n");
		}
	}
}
