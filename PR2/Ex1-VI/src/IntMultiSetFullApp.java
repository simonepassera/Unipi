public class IntMultiSetFullApp extends IntMultiSetApp{

    public IntMultiSetFullApp() {
        super();
    }

    public void union(IntMultiSetFullApp set) {
        for(int i=0; i<set.cur_pos; i++) {
            add(set.set[i], set.occ[i]);
        }
    }

    public void intersection(IntMultiSetFullApp set) {
        int j;

        for(int i=0; i<cur_pos; i++) {
            j = set.getindexOf(this.set[i]);

            if(j == -1) {
                remove(this.set[i]);
                i--;
            }
            else {
                if(this.occ[i] >= set.occ[j])
                {
                    remove(this.set[i], this.occ[i]-set.occ[j]);
                }
            }
        }
    }
}
