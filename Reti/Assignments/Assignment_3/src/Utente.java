import java.util.concurrent.ThreadLocalRandom;

public class Utente implements Runnable, Comparable<Utente> {
    static final int STUDENTE = 1;
    static final int TESISTA = 2;
    static final int PROFESSORE = 3;

    private int max_tempo = 3000;
    private int tipo;
    private int id;
    private int num_pc;
    private Laboratorio lab;

    public Utente(int id, int tipo, int num_pc, Laboratorio lab) {
        this.id = id;
        this.tipo = tipo;
        this.lab = lab;
        this.num_pc = num_pc;
    }

    public void run() {

        System.out.println("Utente " + this.id + " ENTRATO");

        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(1, max_tempo));
        } catch (InterruptedException ignored) {}

        try {
            if (this.tipo == Utente.PROFESSORE) {
                lab.resetAll();
            } else {
                lab.reset(this.num_pc);
            }
        } catch (InterruptedException ignored) {}

        System.out.println("Utente " + this.id + " USCITO");
    }

    public int getTipo() {
        return this.tipo;
    }

    public void setPc(int i) {
        this.num_pc = i;
    }

    public int getNum_pc() {
        return this.num_pc;
    }

    @Override
    public int compareTo(Utente u) {
        return u.getTipo() - this.tipo;
    }
}