import java.util.*;

class BookNode {
    private Book bk;
    private BookNode next;
    // richiede getNode e getBook per gestire i metodi di MyLibrary
    // senza il modificatore private si sarebbero potute manipolare
    // direttamente in MyLibrary le variabili bk e next
    
    public BookNode(Book bk) {
        this(bk, null);
    }

    public BookNode(Book bk, BookNode n) {
        this.bk = bk;
        this.next = n;
    }
    
    public Book getBook( ) {
        return this.bk;
    }

    public BookNode getNext( ) {
        return this.next;
    }

    public void setNext(BookNode n) {
        this.next = n;
    }
}

class MissingBookException extends Exception {
    public MissingBookException(String s) {
        super(s);
    }
}

public class MyLibrary implements Library {
    protected BookNode root;
    
    public MyLibrary( ) {
        root = null;
    }
    
    public MyLibrary(Book[] start) {
        if (start == null) throw new NullPointerException();

        for(int i = 0; i < start.length; i++)
            insert(clone(start[i])); // forse un overkill...

        /*
         if (start.length == 0) throw new IllegalArgumentException();
         Node tmp = root = new Node(start[0]);
         for(int i = 1; i < start.length; i++) {
             tmp.setNext() = new Node(start[i]); // qua piu' lasco...
             tmp = tmp.getNext();
         }
         */
    }
    
    public String[] getByAuthor(String aut) {
        if (aut == null) throw new NullPointerException();

        Vector tmpV = new Vector(); // controllare i warning
        for(BookNode tmp = root; tmp != null; tmp = tmp.getNext()) {
            Book bk = tmp.getBook();
            if (bk.getAuthor().equals(aut))
                tmpV.add(bk);
        }
        
        return fromVecToArr(tmpV);
    }

    public String[] getByTitle(String tit) {
        if (tit == null) throw new NullPointerException();
        
        Vector tmpV = new Vector();
        for(BookNode tmp = root; tmp != null; tmp = tmp.getNext()) {
            Book bk = tmp.getBook();
            if (bk.getTitle().equals(tit))
                tmpV.add(bk);
        }
        
        return fromVecToArr(tmpV);
    }

    public String[] getByPublisher(String pub) {
        if (pub == null) throw new NullPointerException();

        Vector tmpV = new Vector();
        for(BookNode tmp = root; tmp != null; tmp = tmp.getNext()) {
            Book bk = tmp.getBook();
            if (bk.getPublisher().equals(pub))
                tmpV.add(bk);
        }
        
        return fromVecToArr(tmpV);
    }

    public String[] getByYear(int year) {
        if (year < 1900) throw new IllegalArgumentException();
        
        Vector tmpV = new Vector();
        for(BookNode tmp = root; tmp != null; tmp = tmp.getNext()) {
            Book bk = tmp.getBook();
            if (bk.getYear() == year)
                tmpV.add(bk);
        }
        
        return fromVecToArr(tmpV);
    }

    public String[] getAuthorByPublisher(String pub) {
        if (pub == null) throw new NullPointerException();
        
        Vector tmpV = new Vector();
        for(BookNode tmp = root; tmp != null; tmp = tmp.getNext()) {
            Book bk = tmp.getBook();
            if (bk.getPublisher().equals(pub))
                if (tmpV.indexOf(bk.getAuthor()) == -1)
                    tmpV.add(bk.getAuthor());
        }
        
        return fromVecToArr(tmpV);
    }

    public String[] getTitleByYear(int year) {
        Vector tmpV = new Vector();
        for(BookNode tmp = root; tmp != null; tmp = tmp.getNext()) {
            Book bk = tmp.getBook();
            if (bk.getYear() == year)
                if (tmpV.indexOf(bk.getTitle()) == -1)
                    tmpV.add(bk.getTitle());
        }
        
        return fromVecToArr(tmpV);
    }

    private String[] fromVecToArr(Vector vec) {
        String[] tmp = new String[vec.size()];
        for (int i = 0; i < vec.size(); i++)
            tmp[i] = vec.get(i).toString();
        
        return tmp;
    }
    
    private Book clone(Book b) {
        return new MyBook(b.getAuthor(), b.getTitle(), b.getPublisher(), b.getYear());
    }

    public void insert(Book b) {
        if (b == null) throw new NullPointerException();
        
        root = new BookNode(b, root); // senza clonazione o check duplicazione...
        /*
         BookNode tmp = new BookNode(b);
         tmp.setNext() = root;
         root = tmp;
         */
    }

    public void remove(Book b) throws MissingBookException {
        if (b == null) throw new NullPointerException();
        boolean found = false;
        
        for(BookNode prev = null, tmp = root; tmp != null; tmp = tmp.getNext()) {
            Book bk = tmp.getBook();
            if (bk.equals(b)) {
                if (prev == null)
                    root = tmp.getNext();
                else prev.setNext(tmp.getNext());
                found = true; // scelta implementativa: elimino tutti i libri duplicati
            } else prev = tmp;
        }
        if (! found) throw new MissingBookException(b.toString());
        // e se avessi lanciato una Exception? Cosa avrei stampato?
     }
}
