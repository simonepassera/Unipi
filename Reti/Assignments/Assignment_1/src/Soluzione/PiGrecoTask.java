/*
 * Scrivere un programma che attiva un thread T che effettua il calcolo approssimato di π.

/**
 * @author Andrea Michienzi
 *
 * Soluzione con l'utilizzo di un Task
 */
public class PiGrecoTask implements Runnable {
	double piApprox = 0;
	private double accuracy;
	public PiGrecoTask(double accuracy) {
		this.accuracy = accuracy;
	}

	@Override
	public void run() {
		System.out.printf("%s - Math.PI= %.20f \n", Thread.currentThread().getName(), Math.PI);
		int iteration = 0;
		while(!Thread.currentThread().isInterrupted() && Math.abs(piApprox - Math.PI) > accuracy) {
			if (iteration % 2 == 0)
				piApprox = piApprox + (4.0 / (2 * iteration + 1));
			else
				piApprox = piApprox - (4.0 / (2 * iteration + 1));
			iteration++;
		}
		System.out.printf("%s - Approx PI: %.20f\n", Thread.currentThread().getName(), piApprox);
		System.out.printf("%s - Accuracy: %.10f\n", Thread.currentThread().getName(), accuracy);
	}


	public static void main(String[] args) {
		// MainClass accuracy mseconds
		// accuray (double) accuracy da raggungere in decimale
		// mseconds (int) tempo in millisecondi prima di scattare il timeout
		double accuracy = 0.00000000001;
		int timeout = 1000;

		System.out.printf("main - accuracy %.10f\n", accuracy);

		Thread calculator = new Thread(new PiGrecoTask(accuracy));

		calculator.start();

		try {
			Thread.sleep(timeout);
			System.out.print("main - timeout expired, send interrupt to thread \n");
			calculator.interrupt();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
