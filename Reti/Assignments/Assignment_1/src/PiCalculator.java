public class PiCalculator implements Runnable {

    private double accuracy;

    public PiCalculator(double accuracy) {
        this.accuracy = accuracy;
    }

    public void run() {
        double pi = 0;
        long i = 0, j = 1;
        boolean run = true;

        while(run && !Thread.interrupted()) {
            pi += Math.pow(-1, i++) * ((double)4/j);
            j+=2;

            if(Math.abs(pi - Math.PI) < accuracy) run = false;
        }

        System.out.println("PI: " + pi);
    }
}
