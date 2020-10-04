public class IntMultiSetApp implements IntMultiSet {

    protected int[] set;
    protected int[] occ;
    protected int size;
    protected int cur_pos;

    public IntMultiSetApp() {
        set = new int[10];
        occ = new int[10];
        size = 0;
        cur_pos = 0;
    }

    private void resize() {
        int[] new_set = new int[set.length * 2];
        int[] new_occ = new int[occ.length * 2];

        System.arraycopy(set, 0, new_set,0, set.length);
        System.arraycopy(occ, 0, new_occ,0, occ.length);

        set = new_set;
        occ =new_occ;
    }

    // Restituisce l'indice di elem relativo al vettore set.
    // Se elem non appartiene al multi-insieme restituisce 0.
    private int getindexOf(int elem) {
        for(int i=0; i<cur_pos; i++) {
            if(set[i] == elem) {
                return i;
            }
        }

        return -1;
    }

    // Restituisce il numero delle occorrenze dell’intero elem nel multi-insieme.
    // Se elem non appartiene al multi-insieme restituisce 0.
    public int getCount(int elem) {
        int i = getindexOf(elem);

        if(i == -1) {
            return 0;
        }
        else {
            return occ[i];
        }
    }

    // Aggiunge al multi-insieme una occorrenza di elem
    public void add(int elem) {
        int i = getindexOf(elem);
        boolean b = true;

        if(i == -1) {
            while(b) {
                try {
                    set[cur_pos] = elem;
                    occ[cur_pos]++;
                    cur_pos++;
                    b = false;
                } catch (ArrayIndexOutOfBoundsException e) {
                    resize();
                }
            }
        }
        else {
            occ[i]++;
        }

        size++;
    }

    // Aggiunge n copie di elem al multi-insieme
    public void add(int elem, int n) {
        int i = getindexOf(elem);

        if(i == -1) {
            add(elem);
            occ[cur_pos-1] += n-1;
            size += n-1;
        }
        else {
            occ[i] += n;
            size += n;
        }
    }

    // Rimuove tutte le occorrenze di elem dal multi-insieme.
    // Restituisce false se elem non e’ presente
    public boolean remove(int elem) {
        int i = getindexOf(elem);

        if(i == -1) {
            return false;
        }
        else {
            cur_pos--;
            size -= occ[i];

            for(int j=i; j<cur_pos; j++)
            {
                set[j] = set[j+1];
                occ[j] = occ[j+1];
            }
        }

        return true;
    }

    // Rimuove n copie di elem dal multi-insieme,
    // se n è maggiore o uguale delle occorrenze di elem, sarà rimosso dal set.
    // Restituisce false se elem non e’ presente.
    public boolean remove(int elem, int n) {
        int i = getindexOf(elem);

        if(i == -1) {
            return false;
        }
        else {
            if(occ[i] <= n) {
                remove(elem);
            }
            else {
                occ[i]-=n;
                size-=n;
            }
        }

        return true;
    }

    // Restituisce il numero degli elementi del multi-insieme.
    public int size() {
        return size;
    }
}