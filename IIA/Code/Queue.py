#class Queue
class Queue:
    """
    Implementa l'astrazione di una coda.
    """

    def __init__(self):
        """ Costruttore."""
        #inizializza la lista degli elementi con una lista vuota
        self.elements = []

    def isempty(self):
        """ Restituisce True se la coda e' vuota,
            False altrimenti."""
        return (len(self.elements)==0)

    def insert(self, element):
        """ Inserisce l'elemento E nella coda."""
        #Questo metodo deve essere necessariamente implementato nella sottoclasse
        raise NotImplementedError

    def pop(self):
        """ Estrae e restituisce un elemento dalla coda."""
        #Questo metodo deve essere necessariamente implementato nella sottoclasse
        raise NotImplementedError

    def __repr__(self):
        """ Specifica la stringa da stampare
            per rappresentare la coda."""
        return 'Gli elementi nella coda sono: '+ str(self.elements);

    def contains(self, element):
        """ Restituisce True se la coda contiene element,
            False altrimenti. """
        return element in self.elements

    # --- ulteriori funzionalita' utili per gestire la frontiera ----
    def index_state(self, state):
        """ Cerca nella coda un nodo con stato specificato.
            Se lo stato viene trovato restituisce l'indice nella lista corrispondente,
            altrimenti restituisce -1. """  
        found = -1
        for index in range(len(self.elements)):
            # cerca lo stato tra gli elementi della coda
            if self.elements[index].state == state:
                return index
        return found

    def contains_state(self, state):
        """ Restituisce True se nella coda c'e' un nodo con stato state,
            False altrimenti."""  
        return self.index_state(state)>-1
    

#class FIFOqueue - il primo elemento inserito e' il primo ad essere rimosso
class FIFOQueue(Queue):
    """
    Implementa una coda FIFO.
    """
    def insert(self,E):
        """ Inserisce l'elemento E nella coda."""

        #l'elemento e' inserito come primo elemento della coda
        self.elements.insert(0,E)

    def pop(self):
        """ Estrae e restituisce il primo elemento della coda."""

        #estrae e restituisce l'elemento piu' vecchio nella coda
        #(quello corrispondente all'ultimo elemento nella lista)
        return self.elements.pop(); 



#class LIFOqueue - l'ultimo elemento ad essere inserito e' il primo ad essere rimosso
class LIFOQueue(Queue):
    """
    Implementa una coda LIFO.
    """

    def insert(self,E):
        """ Inserisce l'elemento E nella coda. """
        # ogni nuovo elemento e' inserito alla fine della lista
        self.elements.append(E)

    def pop(self):
        """ Estrae e restituisce il primo elemento della coda."""
        # estrae e restituisce l'elemento piu' recentemente inserito nella coda
        # (quello corrispondente all'ultimo elemento nella lista)
        
        return self.elements.pop()
   




#class Priorityqueue - gli elementi nella coda sono ordinati
#                      in base ad una funzione di ordinamento
class PriorityQueue(Queue):
       
    def __init__(self, f = lambda x:x):
        """Costruttore.
           L'argomento f specifica la funzione da usare per calcolare la priorita'
           associata a ciascun elemento nella coda."""

        #inizializza la lista degli elementi come una lista vuota
        self.elements = []
        self.f = f
      
    def insert(self,element):
        """ Inserisce l'elemento element nella coda. """
        # per gestire la priorita' di ciascun elemento nella lista
        # per ogni inserimento si aggiunge anche la sua priorita'
        E = [element, self.f(element)]
        self.elements.append(E)
        # ri-ordina gli elementi nella lista in base alla funzione di ordinamento
        self.elements.sort(key = lambda x:x[1]) # x[1] e' il secondo elemento
                                                # cioe' il valore in base al quale e' fatto l'ordinamento

    def pop(self):
        """ Estrae e restituisce il primo elemento della coda in base all'ordinamento definito."""
        return self.elements.pop(0)[0]; 

    def __repr__(self):
        """ Specifica la stringa da stampare
            per rappresentare la coda."""
        return 'Gli elementi nella coda sono (in ordine crescente): '+ str(self.elements)

    def contains(self, element):
        """ Restituisce True se la coda contiene element,
            False altrimenti. """    
        return element in self.elements

   
    def remove(self, index):
        """ Rimuove dalla coda l'elemento di indice specificato da index."""
        del self.elements[index]

    def get_node(self, index):
        """ Restituisce il nodo nella coda di indice specificato. """
        if len(self.elements)> index:
            # se ci sono almeno index elementi restituisci l'elemento
            # della lista di posizione index
            return self.elements[index][0]
        else:
            # altrimenti restituisce None
            return None

    def index_state(self, state):
        """ Cerca nella coda un nodo con stato specificato.
            Se lo stato viene trovato restituisce l'indice nella lista corrispondente,
            altrimenti restituisce -1. """  
        found = -1
        for index in range(len(self.elements)):
            # cerca lo stato tra gli elementi della coda
            if self.elements[index][0].state == state:
                return index
        return found
        

    