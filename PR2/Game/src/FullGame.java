public class FullGame<V extends Comparable<V>> extends ArrayGame<V> {
    // AF: a(dim, ress, last) = (dim, ress, last)
    // IR: come la super-classe
    
    public FullGame(int lun) throws IllegalArgumentException {
        super(lun);
    }
    
    // assegna al concorrente num il risultato res, se migliore di quello attuale
    public void setResult(int num, V res) throws IllegalArgumentException, NullPointerException {
        if (res == null) throw new NullPointerException();

        V tmp = getResult(num);
        
        if ((tmp == null) || (tmp.compareTo(res) < 0))
            super.setResult(num, res);
    }
    /*
     REQUIRES: 0 < num <= dim && res != null
     THROWS: se num <= 0 o num > dim lancia NoAthleteException
             se res = null lancia NullPointerException
     MODIFIES: ress ed eventualmente firstIndex
     EFFECTS: se res >= ress[num-1] allora ress[num-1] = res
              ed eventualmente firstIndex = num-1
     */
}
