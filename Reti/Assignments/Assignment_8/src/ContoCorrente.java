import java.util.*;

public class ContoCorrente {
    private String nome;
    private List<Movimento> movimenti;
    public enum Causale { BONIFICO, ACCREDITO, BOLLETTINO, F24, PAGOBANCOMAT }

    public ContoCorrente(String nome) {
        this.nome = nome;
        movimenti = new ArrayList<>();
    }

    public void add(Date data, Causale causale, Integer valuta) {
        movimenti.add(new Movimento(new Date(data.getTime()), causale, valuta));
    }

    public List<Movimento> get() {
        return new ArrayList<>(movimenti);
    }
}
