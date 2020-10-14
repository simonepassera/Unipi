public class MyIndexedBook extends MyBook implements IndexedBook {
    private int codice;
    private String genere;

    public MyIndexedBook(String autore, String titolo, String editore, int anno, int codice, String genere) {
        super(autore, titolo, editore, anno);
        
        if (genere == null)
            throw new NullPointerException();
        
        this.codice = codice;
        this.genere = genere;
    }

    public MyIndexedBook(Book b, int codice, String genere) {
        this(b.getAuthor(), b.getTitle(), b.getPublisher(), b.getYear(), codice, genere);
    }

    public MyIndexedBook(Book b, int codice) {
        this(b, codice, "undefined");
    }

    public int getIndex() {
        return this.codice;
    }

    public String getGenre() {
        return this.genere;
    }
    
    public boolean sameGenre(IndexedBook b) {
        if (b == null) throw new NullPointerException();
 
        return this.genere.equals(b.getGenre());
    }

    public String toString() {
        return this.codice + " - " + super.toString() + " - " + this.genere;
    }
    
    public boolean equals(IndexedBook b) {
        return (this.codice == b.getIndex()) && super.equals(b);
        // cosi' si tralascia il genere.
        // come mai e' una scelta sensata?
    }
}
