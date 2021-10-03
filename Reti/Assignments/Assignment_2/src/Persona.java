import java.util.concurrent.ThreadLocalRandom;

public class Persona implements Runnable{
    private final int numero;
    private final int minDelay = 0;
    private final int maxDelay = 1000;

    // Costruttore.
    // @param numero: il numero della numeratrice.
    public Persona(int numero) {
        this.numero = numero;
    }

    // Metodo principale.
    @Override
    public void run() {
        System.out.printf("Persona con il num %d: sono allo sportello\n", numero);

        // Aspetto un intervallo di tempo compreso fra minDelay e maxDelay ms (inclusi).
        int delay = ThreadLocalRandom.current().nextInt(minDelay, maxDelay + 1);
        try {
            Thread.sleep(delay);
        } catch(InterruptedException e) {
            System.err.println("Interruzione su sleep.");
        }

        System.out.printf("Persona con il num %d: esco dall'ufficio\n", numero);
    }
}
