#import:
from Problem import *


class ToyProblem1(Problem):
    """ Questa classe specializza la classe Problem
        implementando le funzioni actions e result.
        Gli stati e le azioni posibili sono definiti come stringhe:
        Gli stati possibili sono:
        'A', 'B', 'C', 'D', 'E', 'F', 'G'
        Le azioni possibili sono:
        'A->B','A->C','B->A','B->C','C->B','C->D','D->E','D->F',
        'E->D','E->G','F->D','F->G','G->E','G->F'
        dove e' usato il delimitatore '->' tra lo stato e il suo successore."""

    # Usa il costruttore di default della classe Problem
    
    def actions(self, state):
        # Per ogni stato restituisce l'insieme delle possibili azioni
        possible_actions = []; #inizializza la lista delle psosibili azioni

        #possibili azioni per ogni stato valido:
        if state == 'A':
            possible_actions = ['A->B','A->C']
        elif state == 'B':
            possible_actions = ['B->A','B->C']
        elif state == 'C':
            possible_actions = ['C->B','C->D']
        elif state == 'D':
            possible_actions = ['D->E','D->F']
        elif state == 'E':
            possible_actions = ['E->D','E->G']
        elif state == 'F':
            possible_actions = ['F->D','F->G']
        elif state == 'G':
            possible_actions = ['G->E','G->F']
        else:
            #default case
            possible_actions = []

        return possible_actions
    


    def result(self, state, action):
        # Dato lo stato state e l'azione action,
        # restituisce il nuovo stato

        new_state = '' # inizializza il nuovo stato
        # in questo caso il nuovo stato e' semplicemente l'ultimo carattere
        # della stringa che definisce l'azione
        new_state = action[-1:]
        
        return new_state
    



class ToyProblem2(ToyProblem1):
    """ Questa classe specializza la classe ToyProblem1,
        reimplementando la funzione step_cost
        per cambiare il costo di alcuni cammini."""

    # Usa il costruttore di default della classe Problem

    # Usa le funzioni actions e result definite nella classe
    # ToyProblem1

    def step_cost(self, action, stateA= None, stateB = None):
        if action == 'A->C':
            return 3
        elif action == 'E->G':
            return 2
        else:
            return 1
    

class RomaniaProblem(Problem):
    """ Questa classe implementa il problema di ricerca
        relativo alla mappa della Romania
        (Fig. 3.2)."""

    # posizioni delle citta' sulla mappa
    # utili per il calcolo dell'euristica h
    posizione = {'Arad':[91, 492], 'Bucharest':[400, 327], 'Craiova':[253, 288],
                 'Drobeta':[165, 299], 'Eforie':[562, 293],'Fagaras':[305, 449],
                 'Giurgiu':[375, 270], 'Hirsova':[534, 350],'Iasi':[473, 506],
                 'Lugoj':[165, 379], 'Mehadia':[168, 339], 'Neamt':[406, 537],
                 'Oradea':[131, 571], 'Pitesti':[320, 368], 'Rimnicu':[233, 410],
                 'Sibiu':[207, 457], 'Timisoara':[94, 410], 'Urziceni':[456, 350],
                 'Vaslui':[509, 444], 'Zerind':[108, 531]}

    def actions(self, state):
        if state == 'Arad':
            possible_actions = ['Arad->Zerind','Arad->Sibiu','Arad->Timisoara']
        elif state == 'Bucharest':
            possible_actions = ['Bucharest->Urziceni','Bucharest->Pitesti','Bucharest->Giurgiu','Bucharest->Fagaras']
        elif state == 'Craiova':
            possible_actions = ['Craiova->Drobeta', 'Craiova->Rimnicu','Craiova->Pitesti']
        elif state == 'Drobeta':
            possible_actions = ['Drobeta->Mehadia','Drobeta->Craiova']
        elif state == 'Eforie':
            possible_actions = ['Eforie->Hirsova']
        elif state == 'Fagaras':
            possible_actions = ['Fagaras->Bucharest','Faragas->Sibiu']
        elif state == 'Hirsova':
            possible_actions = ['Hirsova->Urziceni','Hirsova->Eforie']
        elif state == 'Iasi':
            possible_actions = ['Iasi->Vaslui','Iasi->Neamt']
        elif state == 'Lugoj':
            possible_actions =['Lugoj->Timisoara','Lugoj->Mehadia']
        elif state == 'Oradea':
            possible_actions = ['Oradea->Zerind', 'Oradea->Sibiu']
        elif state == 'Pitesti':
            possible_actions = ['Pitesti->Rimnicu','Pitesti->Craiova','Pitesti->Bucharest']
        elif state == 'Rimnicu':
            possible_actions = ['Rimnicu->Sibiu','Rimnicu->Craiova','Rimnicu->Pitesti']
        elif state == 'Urziceni':
            possible_actions = ['Urziceni->Vaslui','Urziceni->Bucharest','Urziceni->Hirsova']
        elif state == 'Zerind':
            possible_actions = ['Zerind->Arad','Zerind->Oradea']
        elif state == 'Sibiu':
            possible_actions = ['Sibiu->Fagaras','Sibiu->Rimnicu','Sibiu->Arad','Sibiu->Oradea']
        elif state == 'Timisoara':
            possible_actions = ['Timisoara->Arad','Timisoara->Lugoj']
        elif state == 'Giurgiu':
            possible_actions = ['Giurgiu->Bucharest']
        elif state == 'Mehadia':
            possible_actions = ['Mehadia->Drobeta','Mehadia->Lugoj']
        elif state == 'Vaslui':
            possible_actions = ['Vaslui->Iasi','Vaslui->Urziceni']
        elif state == 'Neamt':
            possible_actions = ['Neamt->Iasi']
        else:
            possible_actions = []
        return possible_actions


    def result(self, state, action):

        #restituisce la seconda parte della stringa che definisce l'azione
        #cioe' la sottostringa da '>' in poi

        new_state = action[action.index('>')+1:]
        return new_state
    
    def step_cost(self, action, stateA= None, stateB = None):
        if action == 'Arad->Zerind' or  action == 'Zerind->Arad':
            return 75
        elif action == 'Arad->Sibiu' or action == 'Sibiu->Arad':
            return 140
        elif action == 'Arad->Timisoara' or action == 'Timisoara->Arad':
            return 118
        elif action == 'Bucharest->Urziceni' or action == 'Urziceni->Bucharest':
            return 85
        elif action == 'Bucharest->Pitesti' or action == 'Pitesti->Bucharest':
            return 101
        elif action == 'Bucharest->Giurgiu'or action == 'Giurgiu->Bucharest':
            return 90
        elif action == 'Bucharest->Fagaras' or action == 'Fagaras->Bucharest':
            return 211
        elif action == 'Craiova->Drobeta' or action == 'Drobeta->Craiova':
            return 120
        elif action ==  'Craiova->Rimnicu' or action == 'Rimnicu->Craiova':
            return 146
        elif action == 'Craiova->Pitesti' or action == 'Pitesti->Craiova':
            return 138
        elif action == 'Drobeta->Mehadia' or action == 'Mehadia->Drobeta':
            return 75
        elif action == 'Eforie->Hirsova' or action == 'Hirsova->Eforie':
            return 86
        elif action == 'Fagaras->Sibiu' or action == 'Sibiu->Fagaras':
            return 99
        elif action == 'Hirsova->Urziceni' or action == 'Urziceni->Hirsova':
            return 98
        elif action == 'Iasi->Vaslui' or action == 'Vaslui->Iasi':
            return 92
        elif action == 'Iasi->Neamt' or action == 'Neamt->Iasi':
            return 87
        elif action == 'Lugoj->Timisoara' or action == 'Timisoara->Lugoj':
            return 111
        elif action == 'Lugoj->Mehadia' or action == 'Mehadia->Lugoj':
            return 70
        elif action == 'Oradea->Zerind' or action == 'Zerind->Oradea':
            return 71
        elif action == 'Oradea->Sibiu' or action == 'Sibiu->Oradea':
            return 151
        elif action == 'Pitesti->Rimnicu' or action == 'Rimnicu->Pitesti':
            return 97
        elif action == 'Rimnicu->Sibiu' or action == 'Sibiu->Rimnicu':
            return 80
        elif action == 'Urziceni->Vaslui' or action == 'Vaslui->Urziceni':
            return 142
        else:
            return 0 #

    def distanza_euclidea(self, posizione1, posizione2):
        """ Restituisce la distanza Euclidea tra due punti su un piano."""
        return ((posizione1[0] - posizione2[0])**2 +
                    (posizione1[1] - posizione2[1])**2)**0.5

    def h(self, node, goal_state = None):
        """ Restituisce la stima dal costo per andare da node.state allo stato obiettivo.
            La stima calcolata e' la distanza sulla mappa della Romania tra le due citta'
            corrispondenti a state e allo stato obiettivo."""
        state = node.state
        citta1 = state
        if goal_state is None:
            citta2 = self.goal_state
        

        return self.distanza_euclidea(self.posizione[citta1],self.posizione[citta2])


    def h1(self, node):
        """ Come la funzione h, ma in caso di piu' stati obiettivo, restituisce la distanza rispetto allo stato
            obiettivo piu' vicino."""
        state = node.state
        if isinstance(self.goal_state,list):
            distanze = []
            for index_goal in range(len(self.goal_state)):
                distanze[index_goal] = self.h(state, self.goal_state[index_goal])
            return min(distanze)
        else:
            return self.h(state)
        
        
class Labirinto_Teseo(Problem):
    """ Questa classe implementa il problema di ricerca
        relativo alla esercitazione 'Teseo e il labirinto'."""

    def __init__(self, possible_actions = ['UP','RIGHT','DOWN','LEFT'], initial_state = '(2,1)', goal_state = '(2,4)'):
        """ Costruttore.
            In input e possibile indicare l'ordine con il quale vegono prese in
            considerazione le azioni.
            Come ulteriori input opzionali e' anche possibile specificare
            stato iniziale (default = '(2,1)') e 
            stat obiettivo (default  '(2,4)'). """
        self.initial_state = initial_state # posizione iniziale di Teseo
        self.goal_state = goal_state # posizione dell'uscita dal labirinto
        self.possible_actions = possible_actions

    def actions(self, state):
        # Per ogni casella restituisce l'insieme delle possibili azioni
        possible_actions = self.possible_actions[:]; # inizializza la lista delle psosibili azioni     
        # elimina le azioni che non possono essere eseguite
        if (state == '(2,1)') or (state == '(3,2)') or (state == '(1,2)') or (state == '(1,3)') \
            or (state == '(4,1)') or (state == '(4,2)') or (state == '(4,3)') or (state == '(4,4)'):
            possible_actions.remove('DOWN')
        #
        if (state == '(3,1)') or (state == '(2,2)') or (state == '(2,3)') or (state == '(4,2)') \
           or (state == '(1,1)') or (state == '(1,2)') or (state == '(1,3)') or (state == '(1,4)'):
            possible_actions.remove('UP')
        #
        if (state == '(3,2)') or (state == '(2,3)') or (state == '(3,3)') or \
        (state == '(1,4)') or (state == '(2,4)') or (state == '(3,4)') or (state == '(4,4)'):
            possible_actions.remove('RIGHT')
        #
        if (state == '(4,2)') or (state == '(3,4)') or (state == '(3,3)') or \
        (state == '(1,1)') or (state == '(2,1)') or (state == '(3,1)') or (state == '(4,1)'):
            possible_actions.remove('LEFT')
        return possible_actions
    
        
    def result(self,state,action):
        # Restituisce lo stato che si ottiene quando viene eseguita l'azione indicata
        # mentre si e' nella casella indicata da state

        # ottiene le coordinate RIGA - COLONNA corrispondenti a state
        row = int(state[1:2])
        col = int(state[-2:-1])
        if action == 'UP':
            row = row - 1
        elif action == 'DOWN':
            row = row + 1
        elif action == 'RIGHT':
            col = col + 1
        elif action == 'LEFT':
            col = col - 1
        # costruisci la nuova stringa di stato e restituiscila
        new_state = '('+str(row)+','+str(col)+')'
        return new_state

    def h(self, node):
        # Restituisce l'opposto della profondita' del nodo
        # nota che viene sottratto anche il costo del cammino affinche'
        # f = g + h sia uguale esattamente a - node.depth
        return -node.depth-node.path_cost

class Labirinto_Teseo_F(Labirinto_Teseo):
    # in questo caso viene ri-definita solo la funzione h 

    def h(self,node):
        # l'euristica in questo caso e' data da:
        # |row - row_goal| + |col - col_goal|

        # calcola row e col dello stato corrispondente al nodo
        row = int(node.state[1:2])
        col = int(node.state[-2:-1])
        # calcola row e col dello stato goal
        row_goal = int(self.goal_state[1:2])
        col_goal = int(self.goal_state[-2:-1])

        return abs(row-row_goal) + abs(col-col_goal)
    
class Labirinto_Teseo_H(Labirinto_Teseo):
    # in questo caso viene ri-definita solo la funzione h 

    def h(self,node):
        # l'euristica in questo caso e' data da:
        # |row - row_goal| + |col - col_goal|
        # siccome vogliamo che f = h, sottraiamo il valore di g

        # calcola row e col dello stato corrispondente al nodo
        row = int(node.state[1:2])
        col = int(node.state[-2:-1])
        # calcola row e col dello stato goal
        row_goal = int(self.goal_state[1:2])
        col_goal = int(self.goal_state[-2:-1])

        return abs(row-row_goal) + abs(col-col_goal) - node.path_cost


class Labirinto_Teseo_Local(Labirinto_Teseo):
    """ versione del problema del labirinto di Teseo
        in cui e' definita una funzione value da usare
        per algoritmi di ricerca locale."""

    def value(self,node):
        # questa funzione calcola il valore della funzione da massimizzare
        # nel nostro caso e' basata sul numero minimo di mosse da compiere
        # per arrivare alla soluzione
        
        # calcola row e col dello stato corrispondente al nodo
        row = int(node.state[1:2])
        col = int(node.state[-2:-1])
        # calcola row e col dello stato goal
        row_goal = int(self.goal_state[1:2])
        col_goal = int(self.goal_state[-2:-1])

        return - (abs(row-row_goal) + abs(col-col_goal))