#class Problem

class Problem:
    """
    Questa classe impementa l'astrazione di un problema.
    Per rappresentare un problema specializzare questa classe implementando (almeno) i metodi
    actions e result
    """

    def __init__(self, initial_state, goal_state = None):
        """ Costruttore. Specifica lo stato iniziale e lo stato (o la lista di stati) obiettivo."""
        self.initial_state = initial_state # lo stato iniziale del problema
        self.goal_state = goal_state # lo stato obiettivo, o la lista di stati obiettivo

    def actions(self,state):
        """ Dato lo stato state, restituisce una lista di azioni che possono essere eseguite.
            Questa funzione deve essere implementata nella sottoclasse che specializza Problem."""
        raise NotImplementedError

    def result(self,state,action):
        """ Dato lo stato state e l'azione action, questa funzione restituisce lo stato risultante
            in base al modello di transizione del problema.
            Questa funzione deve essere implementata nella sottoclasse che specializza Problem.""" 
        raise NotImplementedError

    def goal_test(self,state):
        """ Dato lo stato state, restituisce True se state e' uno stato obiettivo e False altrimenti.
            Questa funzione implementa il test obiettivo del problema. """
        # L'implementazione di default per questa funzione restituisce:
        # True: se state e' nella lista degli stati obiettivo
        # False: altrimenti

        if isinstance(self.goal_state,list):
            # in questo caso self.goal_state e' una lista (quindi ci sono piu' stati obiettivo)
            try:
                # cerca state tra gli stati obiettivo
                # un'eccezione e' lanciata nel caso in cui state non sia presente in self.goal_state
                index = self.goal_state.index(state)
                #in questo caso state e' stato trovato tra gli stati obiettivo
                found = True
            except:
                #in questo caso state non e' stato trovato tra gli stati obiettivo
                index = -1
                found = False
            return found
        else:
            # in questo caso self.goal_state e' un solo stato (quindi c'e' un solo stato obiettivo)
            return (state==self.goal_state)

    def step_cost(self, action, stateA = None, stateB = None):
        """ Data l'azione action, lo stato A e lo stato B,
            restituisce il costo dell'esecuzione dell'azione action che porti
            dallo stato A allo stato B."""
        #L'implementazione di default per questa funzione restituisce
        # 1 come costo di ogni azione

        return 1

    
    def path_cost(self, partial_cost, action, stateA = None, stateB=None):
        """ Dato il costo partial_cost del cammino fino al precedente nodo, l'azione action,
            lo stato A e lo stato B, restituisce il costo aggiornato del cammino. """
        # L'implementazione di default di questa funzione assume che i costi siano additivi
        # e usa la funzione self.step_cost per calcolare il costo dell'ultima azione

        return partial_cost + self.step_cost(action,stateA,stateB)