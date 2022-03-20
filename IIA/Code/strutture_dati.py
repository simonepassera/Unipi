class Problem:
    """
    Questa classe implementa l'astrazione di un problema.
    Per rappresentare un problema specializzare questa classe implementando (almeno) i metodi
    actions e result
    """

    def __init__(self, initial_state, goal_state=None):
        """ Costruttore. Specifica lo stato iniziale e lo stato (o la lista di stati) obiettivo """
        self.initial_state = initial_state  # lo stato iniziale del problema
        self.goal_state = goal_state  # lo stato obiettivo, o la lista di stati obiettivo

    def actions(self, state):
        """
        Dato lo stato state, restituisce una lista di azioni che possono essere eseguite.
        Questa funzione deve essere implementata nella sottoclasse che specializza Problem
        """

        raise NotImplementedError

    def result(self, state, action):
        """
        Dato lo stato state e l'azione action, questa funzione restituisce lo stato risultante
        in base al modello di transizione del problema.
        Questa funzione deve essere implementata nella sottoclasse che specializza Problem
        """

        raise NotImplementedError

    def goal_test(self, state):
        """
        Dato lo stato state, restituisce True se state è uno stato obiettivo e False altrimenti.
        Questa funzione implementa il test obiettivo del problema.
        L'implementazione di default per questa funzione restituisce:
            True: se state è nella lista degli stati obiettivo
            False: altrimenti
        """

        if isinstance(self.goal_state, list):
            # in questo caso self.goal_state è una lista (quindi ci sono piu' stati obiettivo)
            try:
                # cerca state tra gli stati obiettivo
                # un'eccezione è lanciata nel caso in cui state non
                # sia presente in self.goal_state
                self.goal_state.index(state)
                # in questo caso state è stato trovato tra gli stati obiettivo
                found = True
            except ValueError:
                # in questo caso state non è stato trovato tra gli stati obiettivo
                found = False

            return found
        else:
            # in questo caso self.goal_state è un solo stato (quindi c'è un solo stato obiettivo)
            return state == self.goal_state

    def step_cost(self, action, stateA=None, stateB=None):
        """
        Data l'azione action, lo stato A e lo stato B,
        restituisce il costo dell'esecuzione dell'azione action che porti
        dallo stato A allo stato B.
        L'implementazione di default per questa funzione restituisce
        1 come costo di ogni azione
        """

        return 1

    def path_cost(self, partial_cost, action, stateA=None, stateB=None):
        """
        Dato il costo partial_cost del cammino fino al precedente nodo, l'azione action,
        lo stato A e lo stato B, restituisce il costo aggiornato del cammino.
        L'implementazione di default di questa funzione assume che i costi siano additivi
        e usa la funzione self.step_cost per calcolare il costo dell'ultima azione
        """

        return partial_cost + self.step_cost(action, stateA, stateB)


class Node:
    """
    Implementa l'astrazione di un nodo in un albero di ricerca
    """

    def __init__(self, state, parent=None, action=None, path_cost=0, depth=0):
        """
        Costruttore. Specifica lo stato corrispondente al nodo, il nodo padre,
        l'azione che ha generato il nodo in questione a partire dal nodo padre
        e il costo del cammino dalla radice al nodo.
        I valori di default per parent, action e path_cost si riferiscono
        alla radice dell'albero
        """

        self.state = state  # lo stato associato al nodo
        self.parent = parent  # il nodo padre (dev'essere un altro oggetto di tipo Node)
        self.action = action  # l'azione che è stata eseguita nello stato self.parent.state
        # per ottenere lo stato self.state
        self.path_cost = path_cost  # il costo del cammino dalla radice fino a questo nodo
        self.depth = depth  # la profondità del nodo

    def __repr__(self):
        """
        Specifica la stringa da stampare per rappresentare il nodo
        """

        description = f"state: {self.state} - path cost {self.path_cost}"
        return description

    def child_node(self, problem, action):
        """
        Restituisce il nodo (nello spazio di ricerca del problema in input)
        ottenuto eseguendo l'azione action quando
        lo stato attuale è self.state
        """

        new_state = problem.result(self.state, action)  # il nuovo stato
        # il nuovo nodo
        new_node = Node(new_state, self, action, problem.path_cost(self.path_cost, action, self.state), self.depth + 1)
        return new_node

    def path(self):
        """
        Restituisce la lista di nodi nel cammino dalla radice dell’albero fino al nodo self.

        Costruisce iterativamente il cammino fino a self
        aggiungendo in testa a una lista tutti i nodi che
        si incontrano seguendo a ritroso i puntatori in parent
        """

        node = self  # il nodo di arrivo
        path_back = []  # lista di nodi dalla radice al nodo in questione

        while node is not None:  # continua finché ci sono nodi nel cammino
            path_back.insert(0, node)  # inserisce il padre del nodo all'inizio della lista
            node = node.parent

        return path_back

    def solution(self, explored_set=None):
        """
        Restituisce la soluzione corrispondente al nodo self.
        La soluzione è rappresentata per mezzo di una lista che contiene i seguenti elementi:
        - in posizione 0: la lista delle azioni da eseguire per andare dalla radice dell'albero di ricerca fino al nodo self
        - in posizione 1: la lista degli stati lungo il cammino dalla radice fino al nodo self
        - in posizione 2: il costo del cammino dalla radice fino al nodo self
        - in posizione 3: l'insieme degli stati esplorati (con default None per tree search)
        """

        node_path = self.path()  # lista di nodi nel cammino dalla radice fino a self
        action_list = []  # lista (ordinata) delle azioni da eseguire per andare dalla radice fino al nodo self
        state_list = []  # lista (ordinata) degli stati nello spazio di ricerca dallo stato iniziale fino a self.state

        for node in node_path:
            if node.action is not None:  # se non si è raggiunta la radice
                action_list.append(node.action)  # aggiungi l'azione
            # in ogni caso (anche se si è raggiunta la radice)
            # aggiungi lo stato
            state_list.append(node.state)

        return [action_list, state_list, self.path_cost, explored_set]


class Queue:
    """
    Implementa l'astrazione di una coda
    """

    def __init__(self):
        """ Costruttore"""

        # inizializza la lista degli elementi con una lista vuota
        self.elements = []

    def is_empty(self):
        """ Restituisce True se la coda è vuota, False altrimenti """

        return len(self.elements) == 0

    def insert(self, element):
        """ Inserisce l'elemento 'element' nella coda """

        # Questo metodo deve essere necessariamente implementato nella sottoclasse
        raise NotImplementedError

    def pop(self):
        """ Estrae e restituisce un elemento dalla coda """

        # Questo metodo deve essere necessariamente implementato nella sottoclasse
        raise NotImplementedError

    def __repr__(self):
        """ Specifica la stringa da stampare per rappresentare la coda """

        return 'Gli elementi nella coda sono: ' + str(self.elements)

    def contains(self, element):
        """ Restituisce True se la coda contiene element, False altrimenti. """
        return element in self.elements

    def index_state(self, state):
        """
        Cerca nella coda un nodo con stato specificato.
        Se lo stato viene trovato restituisce l'indice nella lista corrispondente,
        altrimenti restituisce -1
        """

        found = -1

        for index in range(len(self.elements)):
            # cerca lo stato tra gli elementi della coda
            if self.elements[index].state == state:
                return index

        return found

    def contains_state(self, state):
        """ Restituisce True se nella coda c'e' un nodo con stato state, False altrimenti """

        return self.index_state(state) > -1


class FIFOQueue(Queue):
    """
    Implementa una coda FIFO.
    """

    def insert(self, E):
        """ Inserisce l'elemento E nella coda."""

        # l'elemento è inserito come primo elemento della coda
        self.elements.insert(0, E)

    def pop(self):
        """ Estrae e restituisce il primo elemento della coda """

        # estrae e restituisce l'elemento piu' vecchio nella coda
        # (quello corrispondente all'ultimo elemento nella lista)
        return self.elements.pop()


class LIFOQueue(Queue):
    """
    Implementa una coda LIFO
    """

    def insert(self, E):
        """ Inserisce l'elemento E nella coda """

        # ogni nuovo elemento è inserito alla fine della lista
        self.elements.append(E)

    def pop(self):
        """ Estrae e restituisce il primo elemento della coda """

        # estrae e restituisce l'elemento piu' recentemente inserito nella coda
        # (quello corrispondente all'ultimo elemento nella lista)

        return self.elements.pop()


class PriorityQueue(Queue):
    def __init__(self, f=lambda x: x):
        """
        Costruttore.
        L'argomento f specifica la funzione da usare per calcolare la priorità
        associata a ciascun elemento nella coda.
        """

        # inizializza la lista degli elementi come una lista vuota
        super().__init__()
        self.f = f

    def insert(self, element):
        """ Inserisce l'elemento element nella coda """

        # per gestire la priorità di ciascun elemento nella lista
        # per ogni inserimento si aggiunge anche la sua priorità

        E = [element, self.f(element)]
        self.elements.append(E)
        # ri-ordina gli elementi nella lista in base alla funzione di ordinamento
        self.elements.sort(key=lambda x: x[1])  # x[1] è il secondo elemento cioè il valore in base al quale ordinare

    def pop(self):
        """ Estrae e restituisce il primo elemento della coda in base all'ordinamento definito """

        return self.elements.pop(0)[0]

    def __repr__(self):
        """ Specifica la stringa da stampare per rappresentare la coda """

        return 'Gli elementi nella coda sono (in ordine crescente): ' + str(self.elements)

    def contains(self, element):
        """ Restituisce True se la coda contiene element, False altrimenti """

        return element in self.elements

    # --- ulteriori funzionalità utili per gestire la frontiera ----

    def index_state(self, state):
        """
        Cerca nella coda un nodo con stato specificato.
        Se lo stato viene trovato restituisce l'indice nella lista corrispondente,
        altrimenti restituisce -1
        """

        found = -1

        for index in range(len(self.elements)):
            # cerca lo stato tra gli elementi della coda
            if self.elements[index][0].state == state:
                return index

        return found

    def contains_state(self, state):
        """ Restituisce True se nella coda c'e' un nodo con stato state, False altrimenti """

        return self.index_state(state) > -1

    def remove(self, index):
        """ Rimuove dalla coda l'elemento d' indice specificato da index """

        del self.elements[index]

    def get_node(self, index):
        """ Restituisce il nodo nella coda di indice specificato """

        if len(self.elements) > index:
            # se ci sono almeno index elementi restituisci l'elemento
            # della lista di posizione index
            return self.elements[index][0]
        else:
            # altrimenti restituisce None
            return None


class Game:
    """
    Questa classe implementa l'astrazione di un gioco.
    Per rappresentare un gioco specializzare questa classe implementandone i metodi.
    """

    def player(self, state):
        """ Restituisce il giocatore la cui mossa è in questo stato."""
        raise NotImplementedError

    def actions(self, state):
        """ Restituisce la lista delle possibili mosse nello stato state. """
        raise NotImplementedError

    def result(self, state, action):
        """ Restituisce lo stato che risulta dalla mossa action nello stato state. """
        raise NotImplementedError

    def terminal_test(self, state):
        """Restituisce True se state è uno stato finale (se il gioco è terminato),
           False altrimenti."""
        return len(self.actions(state)) == 0

    def utility(self, state):
        """ Restituisce il valore di utilita' dello stato state per il giocatore. """
        raise NotImplementedError


# main()
if __name__ == '__main__':
    q = PriorityQueue()
    q.insert(7)
    q.insert(6)
    q.insert(5)

    print(q)
    print(q.pop())
    print(q)
