import java.util.Date;

public class Movimento {
    private Integer valuta;
    private ContoCorrente.Causale causale;
    private Date data;

    public Movimento(Date data, ContoCorrente.Causale causale, Integer valuta) {
        this.data = data;
        this.causale = causale;
        this.valuta = valuta;
    }

    public ContoCorrente.Causale getCausale() {
        return causale;
    }
}
