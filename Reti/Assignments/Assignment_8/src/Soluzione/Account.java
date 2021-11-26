/**
 *  Laboratorio di Reti A
 *  Soluzione dell'assignment
 *  @author Matteo Loporchio
 *
 *  Questo file contiene l'implementazione della classe Account
 *  che rappresenta il generico conto corrente.
 */

import java.util.*;

public class Account {
    // Titolare del conto.
    private String owner;
    // Lista di movimenti associati al conto.
    private List<Record> records;

    /**
     *  Costruttore della classe Account.
     *  @param owener il nome del titolare del conto
     */
    public Account(String owner) {
        this.owner = owner;
        this.records = new ArrayList<>();
    }

    /**
     *  Costruttore della classe Account.
     *  @param owener il nome del titolare del conto
     *  @param records la lista di movimenti del conto
     */
    public Account(String owner, List<Record> records) {
        this.owner = owner;
        this.records = records;
    }

    // Restituisce il nome del titolare del conto.
    public String getOwner() {return owner;}

    // Aggiunge un nuovo movimento alla lista.
    public void addRecord(Record r) {records.add(r);}

    // Ritorna la lista dei movimenti associati al conto.
    public List<Record> getRecords() {return records;}
}
