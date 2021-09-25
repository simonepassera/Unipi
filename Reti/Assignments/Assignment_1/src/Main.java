import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        // Input da tastiera
        System.out.print("Accuracy: ");
        double accuracy = in.nextFloat();
        System.out.print("Time: ");
        long time = in.nextLong();

        // Creazione thread "T"
        Thread pi_thread = new Thread(new PiCalculator(accuracy), "T");
        pi_thread.start();

        try {
            pi_thread.join(time);
        } catch (InterruptedException e) {
            System.out.println("Join interrupted");
            return;
        }

        pi_thread.interrupt();
    }
}
