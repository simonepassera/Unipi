/**
 *  Laboratorio di Reti A
 *  Soluzione dell'assignment
 *  @author Matteo Loporchio
 *
 *  Questo file contiene l'implementazione della classe Record,
 *  la quale modella il generico movimento bancario. A ciascun movimento
 *  sono associate una data e una causale.
 */

import java.util.Date;

public class Record {
    // Data relativa al movimento.
    Date date;
    // Causale del movimento.
    Reason reason;

    // Costruttore della classe.
    public Record(Date date, Reason reason) {
        this.date = date;
        this.reason = reason;
    }

    // Restituisce la data associata al movimento.
    public Date getDate() {return date;}
    // Restituisce la causale del movimento.
    public Reason getReason() {return reason;}
}
