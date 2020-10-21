import java.util.Set;

public interface SimpleSortedMap<K extends Comparable<K>, V> {
    // Overview: Tipo modificabile delle funzioni parziali con dominio un ordine totale K
    // e codominio V, definite solo su un sotto-insieme finito di K
    // Typical Element: una funzione parziale f: K -> V per la quale { k | f(k) definito }
    // e' un insieme finito

    /* associa il valore value alla chiave key, e restituisce il valore
     precedentemente associato a key se questo esisteva, e null altrimenti
     */
    public V put(K key, V value) throws NullPointerException;
    /*
     REQUIRES: key != null && value != null
     THROWS: se key = null o value = null lancia una NullPointerException
             (eccezione disponibile in Java, unchecked)
     MODIFIES: f
     EFFECTS: dopo l'invocazione f(key) = value, e se prima dell'invocazione
              f(key) e' definito e vale v, restituisce v, null altrimenti
     */

    /* restituisce il valore associato alla chiave key se questo esiste, e
     null altrimenti
     */
    public V get(K key) throws NullPointerException;
    /*
     REQUIRES: key != null
     THROWS: se key = null lancia una NullPointerException
             (eccezione disponibile in Java, unchecked)
    EFFECTS: restituisce f(key) se questo e' definito, null altrimenti
     */

    /* restituisce l'insieme delle chiavi che hanno associato un valore */
    public Set<K> keySet();
    /*
     EFFECTS: restituisce l'insieme di tutte e sole le chiavi sulle quali
              f e' definito
     */

    /* restituisce la chiave piu' grande che ha associato un valore */
    public K lastKey() throws EmptyListException;
    /*
     REQUIRES: l'insieme { k | f(k) definito } non e' vuoto
     THROWS: se { k | f(k) definito } e' vuoto lancia una EmptyListException
            (eccezione non disponibile in Java, checked)
     EFFECTS: restituisce la chiave piu' grande contenuto nell'insieme
              { k | f(k) definito }
     */

    class EmptyListException extends Exception {
        public EmptyListException() {
            super();
        }

        public EmptyListException(String s) {
            super(s);
        }
    }
}

