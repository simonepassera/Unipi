public interface IntMultiSet {

    // Restituisce il numero delle occorrenze dell’intero elem nel multi-insieme.
    // Se elem non appartiene al multi-insieme restituisce 0.
    int getCount(int elem);

    // Aggiunge al multi-insieme una occorrenza di elem
    void add(int elem);

    // Aggiunge n copie di elem al multi-insieme
    void add(int elem, int n);

    // Rimuove tutte le occorrenze di elem dal multi-insieme.
    // Restituisce false se elem non e’ presente
    boolean remove(int elem);

    // Rimuove n copie di elem dal multi-insieme
    // Restituisce false se elem non e’ presente
    boolean remove(int elem, int n);

    // Restituisce il numero degli elementi del multi-insieme.
    int size();
}