#class node

class Node:
    """
    Impementa l'astrazione di un nodo in un albero di ricerca.
    """

    def __init__(self, state, parent = None, action = None, path_cost = 0, depth = 0):
        """Costruttore. Specifica lo stato corrispondente al nodo, il nodo padre,
           l'azione che ha generato il nodo in questione a partire dal nodo padre
           e il costo del cammino dalla radice al nodo.
           I valori di dafault per parent, action e path_cost si riferiscono alla radice dell'albero."""
        
        
        self.state = state # lo stato associato al nodo
        self.parent = parent # il nodo padre (dev'essere un altro oggetto di tipo Node)
        self.action = action # l'azione che e' stata eseguita nello stato self.parent.state per ottenere lo stato self.state
        self.path_cost = path_cost # il costo del cammino dalla radice fino a questo nodo
        self.depth = depth # la profondita' del nodo


    def __repr__(self):
        """ Specifica la stringa da stampare
            per rappresentare il nodo."""
        
        description = 'state: %s - path cost %s' % (self.state,self.path_cost);
        return description;
    
    def child_node(self, problem, action):
        """ Restituisce il nodo (nello spazio di ricerca del problema in input)
            ottenuto eseguendo l'azione action quando
            lo stato attuale e' self.state."""
        
        new_state = problem.result(self.state,action); #il nuovo stato

        #il nuovo nodo
        new_node = Node(new_state,self,action,problem.path_cost(self.path_cost, action,self.state),self.depth+1);
        return new_node

    def path(self):
        """ Restituisce la lista di nodi nel cammino dalla radice dell’albero fino al nodo self."""

        #costruisce iterativamente il cammino fino a self
        #aggiungendo in testa ad una lista tutti i nodi che
        #si incontrano seguendo a ritroso i puntatori in parent
        
        node = self #il nodo di arrivo
        path_back = [] #lista di nodi dalla radice al nodo in questione
        while node is not None: #continua finche' ci sono nodi nel cammino
            path_back.insert(0,node) #inserisce il padre del nodo all'inizio della lista
            node = node.parent
        return path_back

    def solution(self, explored_set = None):
        """ Restituisce la soluzione corrispondente al nodo self.
            La soluzione e' rappresentata per mezzo di una lista che contiene
            i seguenti elementi:
            - in posizione 0: la lista delle azioni da eseguire per andare dalla radice dell'albero
                              di ricerca fino al nodo self
            - in posizione 1: la lista degli stati lungo il cammino dalla radice fino al nodo self
            - in posizione 2: il costo del cammino dalla radice fino al nodo self.
            - in posizione 3: l'insieme degli stati esplorati (con default None per tree search)."""

        node_path = self.path() #lista di nodi nel cammino dalla radice fino a self
        action_list = [] #lista (ordinata) delle azioni da eseguire per andare dalla radice
                         #fino al nodo self
        state_list = [] #lista (ordinata) degli stati nello spazio di ricerca
                        #dallo stato iniziale fino a self.state
        for node in node_path:
            if node.action is not None: #se non si e' raggiunta la radice
                action_list.append(node.action) #aggiungi l'azione                 
            #in ogni caso (anche se si e' raggiunta la radice)
            #aggiungi lo stato
            state_list.append(node.state)
            
        return [action_list, state_list, self.path_cost,explored_set]
    
        
        

    

    