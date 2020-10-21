public interface CoinsCollection{
    /*  OVERVIEW: Una CoinsCollection e' una collezione di monete del
	valore di 50 centesimi, 1 o 2 euro. Un valore tipico e'
	[v1*50C, v2*1E, v3*2E]
	dove v1, v2 e v3 sono valori interi maggiori di zero.
    */

    public void createCC();
    /* MODIFIES: this
       EFFECTS: inizializza this a una CoinsCollection vuota
    */

    public void addCoin(Integer coin) throws IllegalArgumentException;
    /* MODIFIES: this
       EFFECTS: inserisce la moneta di valore coin nella collezione, se coin e' 
       in {50, 100, 200}. Se coin = null o ha valore diverso, lancia una 
       IllegalArgumentException (eccezione disponibile in Java, unchecked).
    */

    public int balance();
    /* EFFECTS: restituisce il valore totale della collezione, in centesimi di Euro.
    */
  
    public CoinsCollection  makeChange(Integer amount) throws IllegalArgumentException, NoChangeException;
    /* MODIFIES: this
       EFFECTS:  Restituisce, se possibile, una CoinsCollection con un valore totale 
       uguale a amount, togliendo le monete corrispondenti da this. Se amount < 0, 
       amount > balance() o amount % 50 != 0 lancia una IllegalArgumentException. 
       Se 0 < amount < balance() ma il cambio non e' possibile (es: this contiene solo 
       una moneta da 1E, e amount = 50) lancia una NoChangeException, checked.
    */
}
