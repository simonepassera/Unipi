import java.util.PriorityQueue;

public class Tutor implements Runnable {

    private PriorityQueue<Utente> fila;
    private Laboratorio lab;

    public Tutor(int size, Laboratorio lab) {
        fila = new PriorityQueue<>(size);
        this.lab = lab;
    }

    public void richiesta(Utente u){
        fila.add(u);
    }

    @Override
    public void run() {
        Utente u;

        while(fila.size() > 0) {
            u = fila.remove();

            switch(u.getTipo()) {

                case Utente.PROFESSORE:

                    try {
                        lab.setAll();
                    } catch (InterruptedException ignored) {}

                    Thread professore = new Thread(u);
                    professore.start();

                    break;
                case Utente.TESISTA:

                    try {
                        lab.setPc(u.getNum_pc());
                    } catch (InterruptedException ignored) {}
                    Thread tesista = new Thread(u);
                    tesista.start();

                    break;

                case Utente.STUDENTE:
                    try {
                        u.setPc(lab.set());
                    } catch (InterruptedException ignored) {}


                    Thread studente = new Thread(u);
                    studente.start();

                    break;
            }
        }
    }
}
