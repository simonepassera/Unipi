public class MyBook implements Book {
    private String autore, titolo, editore;
    private int anno;
    
    public MyBook(String autore, String titolo) {
        this(autore, titolo, "sconosciuto", 1900);
    }

    public MyBook(String autore, String titolo, String editore, int anno) {
        if ((autore == null) || (titolo == null) || (editore == null))
            throw new NullPointerException();

        if (anno < 1900)
            throw new IllegalArgumentException();
        
        this.autore  = autore;
        this.titolo  = titolo;
        this.editore = editore;
        this.anno    = anno;
    }

    public String getAuthor() {
        return this.autore;
    }

    public String getTitle() {
        return this.titolo;
    }

    public String getPublisher() {
        return this.editore;
    }

    public int getYear() {
        return this.anno;
    }

    public boolean sameAuthor(Book b) {
        if (b == null) throw new NullPointerException();
 
        return this.autore.equals(b.getAuthor());
    }

    public boolean sameTitle(Book b) {
        if (b == null) throw new NullPointerException();
        
        return this.titolo.equals(b.getTitle());
    }

    public boolean samePublisher(Book b) {
        if (b == null) throw new NullPointerException();
        
        return this.editore.equals(b.getPublisher());
    }

    public boolean sameYear(Book b) {
        if (b == null) throw new NullPointerException();
        
        return this.anno == b.getYear();
    }

    public boolean sameWork(Book b) {
        if (b == null) throw new NullPointerException();
        
        return this.sameAuthor(b) && this.sameTitle(b);
    }

    public String toString() {
        return this.autore + " - " + this.titolo + " - " + this.editore + " - " + this.anno;
    }
    
    public boolean equals(Book b) {
        if (b == null) throw new NullPointerException();

        return this.sameWork(b) && this.samePublisher(b) && this.sameYear(b);
        // return this.toString().equals(b.toString());
        // un po' "tricky", non esattamente da manuale...
    }
}
