import java.util.*;

public class  CoinsCollectionImplAlt implements CoinsCollection{
    /*  OVERVIEW: Una CoinsCollection e' una collezione di monete del
	valore di 50 centesimi, 1 o 2 euro. Un valore tipico e'
	[v1*50C, v2*1E, v3*2E]
	dove v1, v2 e v3 sono valori interi maggiori di zero.
   */

    private List<Integer> monete;
    // lista di tre elementi contenenti numero di monete da 50, 100 e 200 centesimi
    /*   FUNZIONE DI ASTRAZIONE
	 a(c) =  [v1*50C, v2*1E, v3*2E] dove
	 v1 = monete.get(0)
	 v2 = monete.get(1)
	 v3 = monete.get(2)
	 Inoltre se vi = 0, il corrispondente termine non compare in a(c), 
	 per i = 1, 2, 3.
	 INVARIANTE DI RAPPRESENTAZIONE
	 I(c) = (monete != null) && (monete.size() = 3) &&
	 (forall x. x \in [0, monete.size()) => monete.get(x) >= 0)
    */

    public CoinsCollectionImplAlt(){}

    public void createCC(){
    /* MODIFIES: this
       EFFECTS: inizializza this a una CoinsCollection vuota
    */
	monete = new ArrayList<Integer>();
	monete.add(0);	monete.add(0);	monete.add(0);
    }

    public void addCoin(Integer coin) throws IllegalArgumentException{
    /* MODIFIES: this
       EFFECTS: inserisce la moneta di valore coin nella collezione, se coin e' 
       in {50, 100, 200}. Se coin = null o ha valore diverso, lancia una IllegalArgumentException.
    */
	if (coin == null) throw new IllegalArgumentException("adding "+coin+" to CoinsCollection...");
	int c = coin;
	if (c == 50) monete.set(0, 1 + monete.get(0)); 
	else if (c == 100) monete.set(1, 1 + monete.get(1));
	else if (c == 200) monete.set(2, 1 + monete.get(2));
	else
	    throw new IllegalArgumentException("adding "+coin+" to CoinsCollection...");
    }

    public int balance(){
    /* EFFECTS: restituisce il valore totale della collezione, in centesimi di Euro.
    */
	return monete.get(0) * 50 + monete.get(1) * 100 + monete.get(2) * 200;
    }
  
    public CoinsCollection  makeChange(Integer amount) throws IllegalArgumentException, NoChangeException{
    /* MODIFIES: this
       EFFECTS:  Restituisce, se possibile, una CoinsCollection con un valore totale 
       uguale a amount, togliendo le monete corrispondenti da this. Se amount < 0, 
       amount > balance() o amount % 50 != 0 lancia una IllegalArgumentException. 
       Se 0 < amount < balance() ma il cambio non e' possibile (es: this contiene solo 
       una moneta da 1E, e amount = 50) lancia una NoChangeException, checked.


       INVARIANTE DI RAPPRESENTAZIONE: Assumendo che this lo soddisfi all'invocazione di 
       makeChange, lo soddisfera' anche alla fine, poiche' su monete si invoca il metodo 
       set che non modifica la lunghezza della lista, e sempre con valori non minori di zero.
       Anche la collezione coll soddisfa l'invariante, per costruzione.

    */
	if (amount == null || amount < 0 || amount > balance() || amount % 50 != 0)
	    throw new IllegalArgumentException();
	CoinsCollectionImplAlt coll = new CoinsCollectionImplAlt();
	coll.createCC();
	int tot = amount;
	int m = Math.min(tot / 200, monete.get(2));
	tot = tot - m * 200;
	monete.set(2,monete.get(2)-m);
	coll.monete.set(2,m);
	m = Math.min(tot / 100, monete.get(1));
	tot = tot - m * 100;
	coll.monete.set(1,m);
	monete.set(1,monete.get(1)-m);
	m = Math.min(tot / 50, monete.get(0));
	tot = tot - m * 50;
	coll.monete.set(0,m);
	monete.set(0,monete.get(0)-m);
	if (tot==0) return  coll;
	monete.set(0, monete.get(0) + coll.monete.get(0));
	coll.monete.set(0,0);
	monete.set(1, monete.get(1) + coll.monete.get(1));
	coll.monete.set(1,0);
	monete.set(2, monete.get(2) + coll.monete.get(2));
	coll.monete.set(2,0);
	throw new NoChangeException("Failed to change " + amount);
    }
}
