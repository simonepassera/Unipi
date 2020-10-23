import java.util.List;

public interface Game<V> {
    // assegna al concorrente num il risultato res
    public void setResult(int num, V res);

    // restituisce il miglior risultato fra quelli conseguiti fino a quel momento
    public int first() throws NoAthleteException;

    // restituisce la lista dei risultati conseguiti fino a quel momento
    public List<V> results();
}
