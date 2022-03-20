import math
import random
import sys
import strutture_dati as sd


class ToyProblem1(sd.Problem):
    """
    Questa classe specializza la classe Problem
    implementando le funzioni actions e result.
    Gli stati e le azioni possibili sono definiti come stringhe:
        Gli stati possibili sono:
            'A', 'B', 'C', 'D', 'E', 'F', 'G'
        Le azioni possibili sono:
            'A->B','A->C','B->A','B->C','C->B','C->D','D->E','D->F',
            'E->D','E->G','F->D','F->G','G->E','G->F'
            dove è usato il delimitatore '->' tra lo stato e il suo successore.
    """

    # Usa il costruttore di default della classe Problem

    def actions(self, state):
        """ Per ogni stato restituisce l'insieme delle possibili azioni """

        possible_actions = []  # inizializza la lista delle possibili azioni

        # possibili azioni per ogni stato valido:
        if state == 'A':
            possible_actions = ['A->B', 'A->C']
        elif state == 'B':
            possible_actions = ['B->A', 'B->C']
        elif state == 'C':
            possible_actions = ['C->B', 'C->D']
        elif state == 'D':
            possible_actions = ['D->E', 'D->F']
        elif state == 'E':
            possible_actions = ['E->D', 'E->G']
        elif state == 'F':
            possible_actions = ['F->D', 'F->G']
        elif state == 'G':
            possible_actions = ['G->E', 'G->F']

        return possible_actions

    def result(self, state, action):
        """ Dato lo stato state e l'azione action, restituisce il nuovo stato """

        # il nuovo stato è semplicemente l'ultimo carattere
        # della stringa che definisce l'azione
        new_state = action[-1]

        return new_state


class RomaniaProblem(sd.Problem):
    """ Questa classe implementa il problema di ricerca
        relativo alla mappa della Romania
        (Fig. 3.2). """

    # posizioni delle città sulla mappa
    # utili per il calcolo dell'euristica h
    posizione = {'Arad': [91, 492], 'Bucharest': [400, 327], 'Craiova': [253, 288],
                 'Drobeta': [165, 299], 'Eforie': [562, 293], 'Fagaras': [305, 449],
                 'Giurgiu': [375, 270], 'Hirsova': [534, 350], 'Iasi': [473, 506],
                 'Lugoj': [165, 379], 'Mehadia': [168, 339], 'Neamt': [406, 537],
                 'Oradea': [131, 571], 'Pitesti': [320, 368], 'Rimnicu': [233, 410],
                 'Sibiu': [207, 457], 'Timisoara': [94, 410], 'Urziceni': [456, 350],
                 'Vaslui': [509, 444], 'Zerind': [108, 531]}

    def actions(self, state):
        if state == 'Arad':
            possible_actions = ['Arad->Zerind', 'Arad->Sibiu', 'Arad->Timisoara']
        elif state == 'Bucharest':
            possible_actions = ['Bucharest->Urziceni', 'Bucharest->Pitesti', 'Bucharest->Giurgiu', 'Bucharest->Fagaras']
        elif state == 'Craiova':
            possible_actions = ['Craiova->Drobeta', 'Craiova->Rimnicu', 'Craiova->Pitesti']
        elif state == 'Drobeta':
            possible_actions = ['Drobeta->Mehadia', 'Drobeta->Craiova']
        elif state == 'Eforie':
            possible_actions = ['Eforie->Hirsova']
        elif state == 'Fagaras':
            possible_actions = ['Fagaras->Bucharest', 'Faragas->Sibiu']
        elif state == 'Hirsova':
            possible_actions = ['Hirsova->Urziceni', 'Hirsova->Eforie']
        elif state == 'Iasi':
            possible_actions = ['Iasi->Vaslui', 'Iasi->Neamt']
        elif state == 'Lugoj':
            possible_actions = ['Lugoj->Timisoara', 'Lugoj->Mehadia']
        elif state == 'Oradea':
            possible_actions = ['Oradea->Zerind', 'Oradea->Sibiu']
        elif state == 'Pitesti':
            possible_actions = ['Pitesti->Rimnicu', 'Pitesti->Craiova', 'Pitesti->Bucharest']
        elif state == 'Rimnicu':
            possible_actions = ['Rimnicu->Sibiu', 'Rimnicu->Craiova', 'Rimnicu->Pitesti']
        elif state == 'Urziceni':
            possible_actions = ['Urziceni->Vaslui', 'Urziceni->Bucharest', 'Urziceni->Hirsova']
        elif state == 'Zerind':
            possible_actions = ['Zerind->Arad', 'Zerind->Oradea']
        elif state == 'Sibiu':
            possible_actions = ['Sibiu->Fagaras', 'Sibiu->Rimnicu', 'Sibiu->Arad', 'Sibiu->Oradea']
        elif state == 'Timisoara':
            possible_actions = ['Timisoara->Arad', 'Timisoara->Lugoj']
        elif state == 'Giurgiu':
            possible_actions = ['Giurgiu->Bucharest']
        elif state == 'Mehadia':
            possible_actions = ['Mehadia->Drobeta', 'Mehadia->Lugoj']
        elif state == 'Vaslui':
            possible_actions = ['Vaslui->Iasi', 'Vaslui->Urziceni']
        elif state == 'Neamt':
            possible_actions = ['Neamt->Iasi']
        else:
            possible_actions = []
        return possible_actions

    def result(self, state, action):

        # restituisce la seconda parte della stringa che definisce l'azione
        # cioè la sottostringa da '>' in poi

        new_state = action[action.index('>') + 1:]
        return new_state

    def step_cost(self, action, stateA=None, stateB=None):
        if action == 'Arad->Zerind' or action == 'Zerind->Arad':
            return 75
        elif action == 'Arad->Sibiu' or action == 'Sibiu->Arad':
            return 140
        elif action == 'Arad->Timisoara' or action == 'Timisoara->Arad':
            return 118
        elif action == 'Bucharest->Urziceni' or action == 'Urziceni->Bucharest':
            return 85
        elif action == 'Bucharest->Pitesti' or action == 'Pitesti->Bucharest':
            return 101
        elif action == 'Bucharest->Giurgiu' or action == 'Giurgiu->Bucharest':
            return 90
        elif action == 'Bucharest->Fagaras' or action == 'Fagaras->Bucharest':
            return 211
        elif action == 'Craiova->Drobeta' or action == 'Drobeta->Craiova':
            return 120
        elif action == 'Craiova->Rimnicu' or action == 'Rimnicu->Craiova':
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
            return 0  #

    def distanza_euclidea(self, posizione1, posizione2):
        """ Restituisce la distanza Euclidea tra due punti su un piano."""
        return ((posizione1[0] - posizione2[0]) ** 2 +
                (posizione1[1] - posizione2[1]) ** 2) ** 0.5

    def h(self, node, goal_state=None):
        """ Restituisce la stima dal costo per andare da node.state allo stato obiettivo.
            La stima calcolata è la distanza sulla mappa della Romania tra le due città
            corrispondenti a state e allo stato obiettivo."""
        state = node.state
        citta1 = state
        if goal_state is None:
            citta2 = self.goal_state

        return self.distanza_euclidea(self.posizione[citta1], self.posizione[citta2])

    def h1(self, node):
        """ Come la funzione h, ma in caso di piu' stati obiettivo, restituisce la distanza rispetto allo stato
            obiettivo piu' vicino. """
        state = node.state
        if isinstance(self.goal_state, list):
            distanze = []
            for index_goal in range(len(self.goal_state)):
                distanze[index_goal] = self.h(state, self.goal_state[index_goal])
            return min(distanze)
        else:
            return self.h(state)


class Labirinto_Teseo(sd.Problem):
    """ Questa classe implementa il problema di ricerca
        relativo alla esercitazione 'Teseo e il labirinto'."""

    def __init__(self, possible_actions=['UP', 'RIGHT', 'DOWN', 'LEFT'], initial_state='(2,1)', goal_state='(2,4)'):
        """ Costruttore.
            In input e possibile indicare l'ordine con il quale vegono prese in
            considerazione le azioni.
            Come ulteriori input opzionali e' anche possibile specificare
            stato iniziale (default = '(2,1)') e
            stat obiettivo (default  '(2,4)'). """
        self.initial_state = initial_state  # posizione iniziale di Teseo
        self.goal_state = goal_state  # posizione dell'uscita dal labirinto
        self.possible_actions = possible_actions

    def actions(self, state):
        # Per ogni casella restituisce l'insieme delle possibili azioni
        possible_actions = self.possible_actions[:];  # inizializza la lista delle psosibili azioni
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

    def result(self, state, action):
        # Restituisce lo stato che si ottiene quando viene eseguita l'azione indicata
        # mentre si è nella casella indicata da state

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
        new_state = '(' + str(row) + ',' + str(col) + ')'
        return new_state

    def h(self, node):
        # Restituisce l'opposto della profondita' del nodo
        # nota che viene sottratto anche il costo del cammino affinche'
        # f = g + h sia uguale esattamente a - node.depth
        return -node.depth - node.path_cost


class Labirinto_Teseo_F(Labirinto_Teseo):
    # in questo caso viene ri-definita solo la funzione h

    def h(self, node):
        # l'euristica in questo caso e' data da:
        # |row - row_goal| + |col - col_goal|

        # calcola row e col dello stato corrispondente al nodo
        row = int(node.state[1:2])
        col = int(node.state[-2:-1])
        # calcola row e col dello stato goal
        row_goal = int(self.goal_state[1:2])
        col_goal = int(self.goal_state[-2:-1])

        return abs(row - row_goal) + abs(col - col_goal)


class Labirinto_Teseo_H(Labirinto_Teseo):
    # in questo caso viene ri-definita solo la funzione h

    def h(self, node):
        # l'euristica in questo caso e' data da:
        # |row - row_goal| + |col - col_goal|
        # siccome vogliamo che f = h, sottraiamo il valore di g

        # calcola row e col dello stato corrispondente al nodo
        row = int(node.state[1:2])
        col = int(node.state[-2:-1])
        # calcola row e col dello stato goal
        row_goal = int(self.goal_state[1:2])
        col_goal = int(self.goal_state[-2:-1])

        return abs(row - row_goal) + abs(col - col_goal) - node.path_cost


class Labirinto_Teseo_Local(Labirinto_Teseo):
    """ versione del problema del labirinto di Teseo
        in cui e' definita una funzione value da usare
        per algoritmi di ricerca locale."""

    def value(self, node):
        # questa funzione calcola il valore della funzione da massimizzare
        # nel nostro caso e' basata sul numero minimo di mosse da compiere
        # per arrivare alla soluzione

        # calcola row e col dello stato corrispondente al nodo
        row = int(node.state[1:2])
        col = int(node.state[-2:-1])
        # calcola row e col dello stato goal
        row_goal = int(self.goal_state[1:2])
        col_goal = int(self.goal_state[-2:-1])

        return - (abs(row - row_goal) + abs(col - col_goal))


def print_solution(solution):
    """ Stampa a video una descrizione testuale della soluzione."""
    if solution is None:
        print('Fallimento.')
    else:
        print('Lista delle azioni: %s ' % (solution[0]))
        print('Lista degli stati: %s ' % (solution[1]))
        if solution[3] is not None:
            print('Stati esplorati: %s ' % (solution[3]))
        print('Costo della soluzione: %s' % (solution[2]))


def recursive_depth_first_search(problem, node):
    """Ricerca in profondità ricorsiva """
    # controlla se lo stato del nodo è uno stato obiettivo
    if problem.goal_test(node.state):
        return node.solution()
    # in caso contrario continua con la ricerca
    for action in problem.actions(node.state):
        child_node = node.child_node(problem, action)
        result = recursive_depth_first_search(problem, child_node)
        if result is not None:
            return result
    return None


def breadth_first_search(problem):
    """ Ricerca-grafo in ampiezza """

    explored = []  # insieme (implementato come una lista) degli stati gia' visitati
    node = sd.Node(problem.initial_state)  # il costo del cammino è inizializzato
    # nel costruttore del nodo
    # controlla se lo stato iniziale è uno stato obiettivo

    if problem.goal_test(node.state):
        return node.solution(explored_set=explored)

    frontier = sd.FIFOQueue()  # la frontiera e' una coda FIFO
    frontier.insert(node)

    while not frontier.is_empty():
        # seleziona il nodo per l'espansione
        node = frontier.pop()
        explored.append(node.state)  # inserisce il nodo nell'insieme dei nodi esplorati

        for action in problem.actions(node.state):
            child_node = node.child_node(problem, action)
            # controlla se lo stato del nodo figlio non è nell'insieme dei nodi esplorati e non è nella frontiera
            if (child_node.state not in explored) and (not frontier.contains_state(child_node.state)):
                # controlla se lo stato del nodo figlio è uno stato obiettivo
                if problem.goal_test(child_node.state):
                    return child_node.solution(explored_set=explored)
                # se lo stato non è uno stato obiettivo allora inserisci il nodo nella frontiera
                frontier.insert(child_node)

    return None  # in questo caso ritorna con fallimento


def uniform_cost_search(problem):
    """ Ricerca-grafo UC """

    explored = []  # insieme (implementato come una lista) degli stati gia' visitati
    node = sd.Node(problem.initial_state)  # il costo del cammino è inizializzato

    # nel costruttore del nodo
    # la frontiera è una coda con priorità
    frontier = sd.PriorityQueue(f=lambda x: x.path_cost)  # lambda serve a definire una funzione anonima a runtime
    frontier.insert(node)

    while not frontier.is_empty():
        # seleziona il nodo per l'espansione
        node = frontier.pop()  # estrae il nodo con costo minore
        # controlla se lo stato del nodo è uno stato obiettivo
        if problem.goal_test(node.state):
            return node.solution(explored_set=explored)
        else:
            # se non lo è inserisci lo stato nell'insieme degli esplorati
            explored.append(node.state)

        for action in problem.actions(node.state):
            child_node = node.child_node(problem, action)
            # controlla se lo stato del nodo figlio non è nell'insieme dei nodi esplorati
            # e non è nella frontiera
            if (child_node.state not in explored) and (not frontier.contains_state(child_node.state)):
                frontier.insert(child_node)
                # se lo stato del nodo figlio è gia' nella frontiera, ma con un costo piu' alto
                # allora sostituisci il nodo nella frontiera con il nodo figlio
            elif frontier.contains_state(child_node.state) and \
                    (frontier.get_node(frontier.index_state(child_node.state)).path_cost > child_node.path_cost):
                frontier.remove(frontier.index_state(child_node.state))
                frontier.insert(child_node)

    return None  # in questo caso ritorna con fallimento


def depth_first_search_tree(problem):
    """Ricerca-albero in profondità """
    node = sd.Node(problem.initial_state)  # il costo del cammino è inizializzato nel costruttore del nodo
    # controlla se lo stato iniziale e' uno stato obiettivo
    if problem.goal_test(node.state):
        return node.solution()

    frontier = sd.LIFOQueue() # la frontiera e' una coda LIFO
    frontier.insert(node)

    while not frontier.is_empty():
        node = frontier.pop()  # estrae il nodo dalla frontiera
        # print('Node: %s' % node)

        # controlla se lo stato del nodo è uno stato obiettivo
        if problem.goal_test(node.state):
            return node.solution()
        # espandi la frontiera
        for action in problem.actions(node.state):
            child_node = node.child_node(problem,action)
            frontier.insert(child_node)
    return None  # in questo caso ritorna con fallimento


def depth_first_search_graph(problem):
    """ Ricerca-grafo in profondità """

    explored = []  # insieme (implementato come una lista) degli stati gia' visitati
    node = sd.Node(problem.initial_state)  # il costo del cammino è inizializzato

    # nel costruttore del nodo
    # controlla se lo stato iniziale è uno stato obiettivo
    if problem.goal_test(node.state):
        return node.solution()

    frontier = sd.LIFOQueue()  # la frontiera e' una coda LIFO
    frontier.insert(node)

    while not frontier.is_empty():
        node = frontier.pop()  # estrae il nodo dalla frontiera
        # controlla se lo stato del nodo è uno stato obiettivo
        if problem.goal_test(node.state):
            return node.solution(explored_set=explored)
        else:
            # se lo stato non è uno stato obiettivo aggiungilo all'insieme degli esplorati
            explored.append(node.state)
        # espandi la frontiera
        for action in problem.actions(node.state):
            child_node = node.child_node(problem, action)
            if (child_node.state not in explored) and (not frontier.contains_state(child_node.state)):
                frontier.insert(child_node)
    return None  # in questo caso ritorna con fallimento


def limited_depth_first_search_tree(problem, depth_limit):
    """ Ricerca-albero in profondità con depth_limit """

    node = sd.Node(problem.initial_state)  # il costo del cammino è inizializzato nel costruttore del nodo

    # controlla se lo stato iniziale è uno stato obiettivo
    if problem.goal_test(node.state):
        return node.solution()

    frontier = sd.LIFOQueue()  # la frontiera e' una coda LIFO
    frontier.insert(node)

    while not frontier.is_empty():
        node = frontier.pop()  # estrae il nodo dalla frontiera

        if node.depth > depth_limit:
            continue

        # controlla se lo stato del nodo è uno stato obiettivo
        if problem.goal_test(node.state):
            return node.solution()

        # espandi la frontiera
        for action in problem.actions(node.state):
            child_node = node.child_node(problem, action)
            frontier.insert(child_node)

    return None  # in questo caso ritorna con fallimento


def limited_recursive_depth_first_search(problem, node, depth_limit):
    """Ricerca in profondità ricorsiva con depth_limit"""
    # controlla se lo stato del nodo è uno stato obiettivo

    if depth_limit < 0:
        return None

    if problem.goal_test(node.state):
        return node.solution()
    # in caso contrario continua con la ricerca
    for action in problem.actions(node.state):
        child_node = node.child_node(problem, action)
        result = limited_recursive_depth_first_search(problem, child_node, depth_limit - 1)
        if result is not None:
            return result
    return None


def astar_search(problem):
    """ Ricerca A* """

    explored = []  # insieme (implementato come una lista) degli stati già visitati
    node = sd.Node(problem.initial_state)  # il costo del cammino è inizializzato

    # nel costruttore del nodo
    # lambda serve a definire una funzione anonima a runtime
    frontier = sd.PriorityQueue(f=lambda x: x.path_cost + problem.h(x))
    frontier.insert(node)

    while not frontier.is_empty():
        # seleziona un nodo per l'espansione
        node = frontier.pop()  # seleziona il nodo con costo del cammino piu' basso
        # controlla se lo stato del nodo è uno stato obiettivo
        if problem.goal_test(node.state):
            return node.solution(explored_set=explored)
        else:
            # se lo stato non è uno stato obiettivo aggiungilo all'insieme degli esplorati
            explored.append(node.state)

        for action in problem.actions(node.state):
            child_node = node.child_node(problem, action)
            # controlla se lo stato del nodo figlio non è nell'insieme dei nodi esplorati
            # e non è nella frontiera
            if (child_node.state not in explored) and (not frontier.contains_state(child_node.state)):
                frontier.insert(child_node)
            # se lo stato del nodo figlio è gia' nella frontiera, ma con un costo piu' alto
            # allora sostituisci il nodo nella frontiera con il nodo figlio
            elif frontier.contains_state(child_node.state) and (frontier.get_node(frontier.index_state(child_node.state)).path_cost > child_node.path_cost):
                frontier.remove(frontier.index_state(child_node.state))
                frontier.insert(child_node)
    return None  # in questo caso ritorna con fallimento


def hill_climbing(problem):
    """ Ricerca locale - Hill-climbing."""
    current = sd.Node(problem.initial_state)

    while True:
        neighbors = [current.child_node(problem, action) for action in problem.actions(current.state)]
        # se current non ha successori esci e restituisci current
        if not neighbors:
            break
        # scegli il vicino con valore piu' alto
        neighbor = (sorted(neighbors, key=lambda x: problem.value(x), reverse=True))[0]
        if problem.value(neighbor) <= problem.value(current):
            break
        else:
            current = neighbor
    return current


def schedule_ex(k=100, lam=0.5, limit=100):
    """ Una possibile funzione di scheduling per
        l'algoritmo di simulated annealing."""
    # La temperatura è fatta decrescere seguendo una legge esponenziale
    # Nota: k è il valore della temperatura iniziale
    return lambda t: (k * math.exp(-lam * t) if t < limit else 0)


def simulated_annealing(problem, schedule=schedule_ex()):
    """Ricerca locale - Simulated Annealing"""
    current = sd.Node(problem.initial_state)

    for t in range(sys.maxsize):  # sys.maxsize e' il massimo numero intero
        T = schedule(t)
        if T == 0:
            return current
        neighbors = [current.child_node(problem, action) for action in problem.actions(current.state)]
        # se current non ha successori esci e restituisci current
        if not neighbors:
            return current

        next = random.choice(neighbors)
        delta_e = problem.value(next) - problem.value(current)
        if delta_e > 0 or (random.random() < (math.exp(delta_e / T))):
            current = next


class ToyGame1(sd.Game):
    """ Rappresentazione di un gioco di esempio."""

    risultato = {'B1': 3, 'B2': 12, 'B3': 8, 'C1': 2, 'C2': 4, 'C3': 6, 'D1': 14, 'D2': 5, 'D3': 2}

    def actions(self, state):
        if state == 'A':
            return ['a1', 'a2', 'a3']
        if state == 'B':
            return ['b1', 'b2', 'b3']
        if state == 'C':
            return ['c1', 'c2', 'c3']
        if state == 'D':
            return ['d1', 'd2', 'd3']
        return []

    def result(self, state, move):
        if move == 'a1':
            return 'B'
        if move == 'a2':
            return 'C'
        if move == 'a3':
            return 'D'
        #
        if move == 'b1':
            return 'B1'
        if move == 'b2':
            return 'B2'
        if move == 'b3':
            return 'B3'
        #
        if move == 'c1':
            return 'C1'
        if move == 'c2':
            return 'C2'
        if move == 'c3':
            return 'C3'
        #
        if move == 'd1':
            return 'D1'
        if move == 'd2':
            return 'D2'
        if move == 'd3':
            return 'D3'

    def utility(self, state):
        return self.risultato[state]

    def terminal_test(self, state):
        return state not in ('A', 'B', 'C', 'D')


class ToyGameEx1(sd.Game):
    """Rappresentazione di un gioco di esempio
       per l'esercitazione."""

    utilita = {'Z1': 10, 'Z2': 4, 'Z3': 5, 'Z4': 12, 'Z5': 3, 'Z6': 4, 'Z7': 3, 'Z8': 2, 'Z9': 6, 'Z10': 7, 'Z11': 10,
               'Z12': 8}
    risultato_mossa = {'a1': 'B', 'a2': 'C', 'b1': 'D', 'b2': 'E', 'b3': 'F', 'c1': 'G', 'c2': 'H', 'c3': 'K',
                       'd1': 'Z1', 'd2': 'Z2', 'e1': 'Z3', 'e2': 'Z4', 'f1': 'Z5', 'f2': 'Z6',
                       'g1': 'Z7', 'g2': 'Z8', 'h1': 'Z9', 'h2': 'Z10', 'k1': 'Z11', 'k2': 'Z12'}

    def actions(self, state):
        if state == 'A':
            return ['a1', 'a2']
        if state == 'B':
            return ['b1', 'b2', 'b3']
        if state == 'C':
            return ['c1', 'c2', 'c3']
        if state == 'D':
            return ['d1', 'd2']
        if state == 'E':
            return ['e1', 'e2']
        if state == 'F':
            return ['f1', 'f2']
        if state == 'G':
            return ['g1', 'g2']
        if state == 'H':
            return ['h1', 'h2']
        if state == 'K':
            return ['k1', 'k2']
        return []

    def result(self, state, move):
        return self.risultato_mossa[move]

    def utility(self, state):
        # print('sono in stato {}'.format(state))
        return self.utilita[state]

    def terminal_test(self, state):
        return state[0] in 'Z'


class ToyGameEx2(sd.Game):
    """ Rappresentazione di un gioco di esempio
       per l'esercitazione.
       -- Variante con ordinamento ottimale delle mosse."""

    utilita = {'Z1': 10, 'Z2': 4, 'Z3': 5, 'Z4': 12, 'Z5': 3, 'Z6': 4, 'Z7': 3, 'Z8': 2, 'Z9': 6, 'Z10': 7, 'Z11': 10,
               'Z12': 8}

    risultato_mossa = {'a1': 'B', 'a2': 'C', 'b1': 'D', 'b2': 'E', 'b3': 'F', 'c1': 'G', 'c2': 'H', 'c3': 'K',
                       'd1': 'Z1', 'd2': 'Z2', 'e1': 'Z3', 'e2': 'Z4', 'f1': 'Z5', 'f2': 'Z6',
                       'g1': 'Z7', 'g2': 'Z8', 'h1': 'Z9', 'h2': 'Z10', 'k1': 'Z11', 'k2': 'Z12'}

    def actions(self, state):
        if state == 'A':
            return ['a1', 'a2']
        if state == 'B':
            return ['b3', 'b1', 'b2']
        if state == 'C':
            return ['c1', 'c2', 'c3']
        if state == 'D':
            return ['d1', 'd2']
        if state == 'E':
            return ['e2', 'e1']
        if state == 'F':
            return ['f2', 'f1']
        if state == 'G':
            return ['g1', 'g2']
        if state == 'H':
            return ['h1', 'h2']
        if state == 'K':
            return ['k1', 'k2']
        return []

    def result(self, state, move):
        return self.risultato_mossa[move]

    def utility(self, state):
        # print('sono in stato {}'.format(state))
        return self.utilita[state]

    def terminal_test(self, state):
        return state[0] in 'Z'


# ------ minimax search ------
def minimax_decision(game, state):
    def max_value(state):
        if game.terminal_test(state):
            # se in state il gioco e' concluso restituisci il risultato
            return game.utility(state)
        v = - float('inf')  # v e' inizializzato a - infinito
        for a in game.actions(state):
            v = max(v, min_value(game.result(state, a)))
        print('MAX: stato %s - utilita\' %s' % (state, v))
        return v

    def min_value(state):
        if game.terminal_test(state):
            # se in state il gioco e' concluso restituisci il risultato
            return game.utility(state)
        v = float('inf')  # v e' inizializzato a + infinito
        for a in game.actions(state):
            v = min(v, max_value(game.result(state, a)))
        print('MIN: stato %s - utilita\' %s' % (state, v))
        return v

    best_action = max(game.actions(state), key=lambda x: min_value(game.result(state, x)))
    # best_action e' l'argomento (l'azione) che massimizza l'output di min_value
    print("L\'azione selezionata e\' %s " % (best_action))
    return best_action


# ------ alpha-beta search ------
def alpha_beta(game, state):
    def max_value(state, alpha, beta):
        if game.terminal_test(state):
            # se in state il gioco e' concluso restituisci il risutato
            return game.utility(state)
        v = - float('inf')  # v e' inizializzato a - infinito
        for a in game.actions(state):
            v = max(v, min_value(game.result(state, a), alpha, beta))
            if v >= beta:  # taglio beta
                print('MAX: stato %s - utilita\' %s - TAGLIO BETA (alpha = %s, beta = %s)' % (state, v, alpha, beta))
                return v
            alpha = max(alpha, v)  # aggiorna il migliore per MAX
        print('MAX: stato %s - utilita\' %s - (alpha = %s, beta = %s)' % (state, v, alpha, beta))
        return v

    def min_value(state, alpha, beta):
        if game.terminal_test(state):
            # se in state il gioco e' concluso restituisci il risultato
            return game.utility(state)
        v = float('inf')  # v e' inizializzato a + infinito
        for a in game.actions(state):
            v = min(v, max_value(game.result(state, a), alpha, beta))
            if v <= alpha:  # taglio alpha
                print('MIN: stato %s - utilita\' %s - TAGLIO ALPHA (alpha = %s, beta = %s)' % (state, v, alpha, beta))
                return v
            beta = min(beta, v)  # aggiorna il migliore per MIN
        print('MIN: stato %s - utilita\' %s - (alpha = %s, beta = %s)' % (state, v, alpha, beta))
        return v

    # inizializza alpha e beta
    alpha = - float('inf')
    beta = float('inf')

    best_action = None
    # esegue un ciclo esterno di max_value "controllato"
    # memorizzando la mossa migliore
    for a in game.actions(state):
        v = min_value(game.result(state, a), alpha, beta)
        if v > alpha:
            # se lo score della mossa a e' il migliore fino a qui
            # allora memorizza la mossa a
            best_action = a
            alpha = v
    print("L\'azione selezionata e\' %s" % (best_action))
    return best_action


# main()
if __name__ == '__main__':
    p = ToyProblem1('A', 'G')
    print('BF: ' + str(breadth_first_search(p)))
    print('DF: ' + str(depth_first_search_graph(p)))
    print('UC: ' + str(uniform_cost_search(p)))
    print('DL: ' + str(limited_depth_first_search_tree(p, 4)))
    # print('A*: ' + str(astar_search(p)))

    # gioco con ordinamento mosse standard
    game1 = ToyGame1()
    print("alpha_beta")
    alpha_beta(game1, 'A')
    print("minmax")
    minimax_decision(game1, 'A')

