import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

public class Accessi {

    public static void main(String[] args) {

        final int LabInfSize = 20;
        final int max_num_accessi = 5;

        if (args.length != 3) {
            System.err.println("Usage: java Accessi <num_studenti> <num_tesisti> <numero_professori>");
            System.exit(1);
        }

        int numStudenti = Integer.parseInt(args[0]);
        int numTesisti = Integer.parseInt(args[1]);
        int numProfessori = Integer.parseInt(args[2]);

        int numUtenti = numStudenti + numTesisti + numProfessori;

        ArrayList<Utente> utenti = new ArrayList<Utente>(numUtenti);

        Laboratorio LabInf = new Laboratorio(LabInfSize);

        for(int i = 0; i < numStudenti; i++) {

            int k = ThreadLocalRandom.current().nextInt(1, max_num_accessi + 1);

            Utente u = new Utente(i, Utente.STUDENTE, 0, LabInf);

            for(int j = 0; j < k; j++) {
                utenti.add(u);
            }
        }

        for(int i = 0; i < numTesisti; i++) {

            int k = ThreadLocalRandom.current().nextInt(1, max_num_accessi + 1);
            int pc = ThreadLocalRandom.current().nextInt(1, LabInfSize + 1);

            Utente u = new Utente(i + numStudenti, Utente.TESISTA, pc, LabInf);

            for(int j = 0; j < k; j++) {
                utenti.add(u);
            }
        }

        for(int i = 0; i < numProfessori; i++) {

            int k = ThreadLocalRandom.current().nextInt(1, max_num_accessi + 1);

            Utente u = new Utente(i + numTesisti, Utente.PROFESSORE, 0, LabInf);

            for(int j = 0; j < k; j++) {
                utenti.add(u);
            }
        }

        Collections.shuffle(utenti);

        Tutor tutor = new Tutor(numUtenti, LabInf);

        for (Utente u : utenti) {
            tutor.richiesta(u);
        }

        Thread t = new Thread(tutor);

        t.start();
    }
}
