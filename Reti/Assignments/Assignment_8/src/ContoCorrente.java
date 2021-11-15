import java.util.*;

public class ContoCorrente {
    private String nome;
    private List<Movimento> movimenti;
    public enum Causale { BONIFICO, ACCREDITO, BOLLETTINO, F24, PAGOBANCOMAT }

    private class Movimento {
        private Integer valuta;
        private Causale causale;
        private Date data;

        private Movimento(Date data, Causale causale, Integer valuta) {
            this.data = data;
            this.causale = causale;
            this.valuta = valuta;
        }
    }

    public ContoCorrente(String nome) {
        this.nome = nome;
        movimenti = new ArrayList<>();
    }

    public void add(Date data, Causale causale, Integer valuta) {
        movimenti.add(new Movimento(new Date(data.getTime()), causale, valuta));
    }
}
