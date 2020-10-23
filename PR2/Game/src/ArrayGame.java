import java.util.List;
import java.util.ArrayList;

public class ArrayGame<V extends Comparable<V>> implements Game<V> {
    // AF: a(firstIndex, ress) = f: [1...ress.length] -> V tale che f(i) = res[i-1]
    //                           se res[i-1] diverso da null, e indefinito altrimenti
    // IR: ress != null &&
    //     (firstIndex = -1 || forall i. 0 <= i < ress.length => (ress[i] = null || ress[i] <= ress[firstIndex]))
    
    private V[] ress;            // array che memorizza i risultati
    private int firstIndex;      // dell'atleta in testa al momento
    
    public ArrayGame(int lun) throws IllegalArgumentException {
        ress = (V[]) new Object[lun];
        firstIndex = -1;
    }
    
    private int searchMax(int ex) {
        int tmp = ex;
        for (int i = ress.length-1; i > 0; i--)
            if ((ress[i] != null) && (ress[i].compareTo(ress[tmp]) >= 0))
                tmp = i;
        return tmp;
    }
    
    // restituisce il risultato del concorrente num
    public V getResult(int num) throws IllegalArgumentException {
        if ((num <= 0) || (num > ress.length)) throw new IllegalArgumentException();

        return ress[num-1];
    }
    /*
     REQUIRES: 0 < num <= ress.length && res != null
     THROWS: se num <= 0 o num > ress.length lancia IllegalArgumentException
     */

    // assegna al concorrente num il risultato res
    public void setResult(int num, V res) throws IllegalArgumentException, NullPointerException {
        if (res == null) throw new NullPointerException();
        if ((num <= 0) || (num > ress.length)) throw new IllegalArgumentException();

        if ((firstIndex == -1) || (res.compareTo(ress[firstIndex]) > 0)) {
            ress[num-1] = res;
            firstIndex = num-1;
        } else if ((firstIndex == num-1) && (res.compareTo(ress[firstIndex]) < 0)) {
                    ress[num-1] = res;
                    firstIndex = searchMax(num-1);
                }
    }
    /*
     REQUIRES: 0 < num <= ress.length && res != null
     THROWS: se num <= 0 o num > ress.length lancia IllegalArgumentException
             se res = null lancia NullPointerException
     MODIFIES: ress ed eventualmente firstIndex
     EFFECTS: ress[num-1] = res ed eventualmente firstIndex e' aggiornato
     */
    
    // restituisce il miglior risultato fra quelli conseguiti fino a quel momento
    public int first() throws NoAthleteException {
        if (firstIndex == -1) throw new NoAthleteException();

        return firstIndex+1;
    }
    /*
     EFFECTS: restituisce i tale che
              for all j. 0 <= j < ress.length ==> ress[j] = null o ress[j].compareTo(ress[i-1]) <= 0
     THROWS: se non esiste tale i lancia NoAthleteException
    */
    
    // restituisce la lista dei risultati conseguiti fino a quel momento
    public List<V> results() {
        List<V> tmp = new ArrayList<V>();
    
        for (V elem: ress)
          tmp.add(elem);
        
        return tmp;
    }
    /*
     EFFECTS: restituisce una lista tmp tale che tmp.get(i) = ress[i]
              (ovvero, restituisce una shallow copy di ress); si noti che
              per preservare l'ordine, la lista include anche null
     */
}
