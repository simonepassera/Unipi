import java.util.List;
import java.util.ArrayList;

public class LimitedTwoListsSSMap<K extends Comparable<K>, V> extends TwoListsSSMap<K, V> {
    // Overview: Tipo modificabile delle funzioni parziali con dominio un ordine totale K
    // e codominio V, definite solo su un sottoinsieme finito e limitato di K
    // AF = <lim, dim, keys, values> --> <dim, keys, values>
    // IR = come sopra e inoltre dim <= lim

    private int lim;

    public LimitedTwoListsSSMap(int limite) throws IllegalArgumentException {
        super();
        if (limite <= 0) throw new IllegalArgumentException();
        lim = limite;
    }
    /*
     REQUIRES: limite > 0
     THROWS: se limite <= 0 lancia una IllegalArgumentException
             (eccezione disponibile in Java, unchecked)
     EFFECTS: pone la dimensione massima dell'insieme delle chiavi a limite
     */

    /* se non e' stato raggiunto il limite massimo di chiavi definite,
     associa il valore value alla chiave key, e restituisce il valore
     precedentemente associato a key se questo esisteva, e null altrimenti
     */
    public V put(K key, V value) throws IllegalStateException, NullPointerException {
        if (super.size() == lim) throw new IllegalStateException();
        return super.put(key,value);
    }
    /*
     REQUIRES: key != null && value != null && size() < lim
     THROWS: se lim = size lancia una FullMapException
             se key = null o value = null lancia una NullPointerException
             (eccezione disponibile in Java, unchecked)
     MODIFIES: dim, keys, values
     EFFECTS: dopo l'invocazione assegna value a values(i) se key.equals(keys.get(i)),
              e se prima dell'invocazione values.get(i) vale v, restituisce il valore v;
              se key non compare in keys, esegue keys.add(key) e values.add(value) e
              restituisce null
     */

    /*
     Notare che per essere una sotto-classe il costruttore e il metodo put devono
     lanciare eccezioni unchecked, e non checked. Detto questo, e' un sotto-tipo o meno?
     */
}