import java.util.*;

public class  CoinsCollectionImpl implements CoinsCollection{
    /*  OVERVIEW: Una CoinsCollection e' una collezione di monete del
	valore di 50 centesimi, 1 o 2 euro. Un valore tipico e'
	[v1*50C, v2*1E, v3*2E]
	dove v1, v2 e v3 sono valori interi maggiori di zero.
   */

    private List<Integer> monete;
    // lista della monete presenti nella collezione
    /*   FUNZIONE DI ASTRAZIONE
	 a(c) =  [v1*50C, v2*1E, v3*2E] dove
	 v1 = #{ x | 0 <= x < monete.size() && monete.get(x) = 50}
	 v2 = #{ x | 0 <= x < monete.size() && monete.get(x) = 100}
	 v3 = #{ x | 0 <= x < monete.size() && monete.get(x) = 200}
	 Inoltre se vi = 0, il corrispondente termine non compare in a(c), 
	 per i = 1, 2, 3.
	 INVARIANTE DI RAPPRESENTAZIONE
	 I(c) = (monete != null) && (forall x. x \in [0, monete.size()) => monete.get(x) \in {50,100,200})
    */

    public CoinsCollectionImpl(){}
    
    public void createCC(){
    /* MODIFIES: this
       EFFECTS: inizializza this a una CoinsCollection vuota
    */
	monete = new ArrayList<Integer>();
    }

    public void addCoin(Integer coin) throws IllegalArgumentException{
    /* MODIFIES: this
       EFFECTS: inserisce la moneta di valore coin nella collezione, se coin e' 
       in {50, 100, 200}. Se coin = null o ha valore diverso, lancia una IllegalArgumentException.
    */
	if (coin == null) throw new IllegalArgumentException("adding "+coin+" to CoinsCollection...");
	int c = coin;
	if (c == 50 || c == 100 || c == 200) monete.add(coin);
	else
	    throw new IllegalArgumentException("adding "+coin+" to CoinsCollection...");
    }

    public int balance(){
    /* EFFECTS: restituisce il valore totale della collezione, in centesimi di Euro.
    */
	int res = 0;
	for (Integer i : monete) res += i;
	return res;
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
       remove, che non puo' invalidare l'invariante, e addCoin() che assumiamo lo preservi.
       Inoltre la collezione Coll lo soddisfa, perche' inizializzata alla collezione vuota
       con createCC, e poi modificata solo con addCoins e con remove.
    */
	if (amount == null || amount < 0 || amount > balance() || amount % 50 != 0)
	    throw new IllegalArgumentException();
	CoinsCollectionImpl coll = new CoinsCollectionImpl();
	coll.createCC();
	int tot = amount;
	int i = monete.size() -1;
	while (i >= 0 && tot >=200){
	    if (monete.get(i) == 200) {
		coll.addCoin(200);
		monete.remove(i);
		tot -= 200;
	    }
	    i--;
	}
	i = monete.size() -1;
	while (i >= 0 && tot >=100){
	    if (monete.get(i) == 100) {
		coll.addCoin(100);
		monete.remove(i);
		tot -= 100;
	    }
	    i--;
	}
	i = monete.size() -1;
	while (i >= 0 && tot >=50){
	    if (monete.get(i) == 50) {
		coll.addCoin(50);
		monete.remove(i);
		tot -= 50;
	    }
	    i--;
	}
	if (tot == 0) return coll;
	// cambio non possibile, rimetto le monete a posto
	for (i = coll.monete.size() -1; i >= 0; i--){
	    addCoin(coll.monete.get(i));
	    coll.monete.remove(i);
	}
	throw new NoChangeException("Failed to change " + amount);
    }
}
