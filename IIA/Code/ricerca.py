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


# main()
if __name__ == '__main__':
    p = ToyProblem1('A', 'G')
    print('BF: ' + str(breadth_first_search(p)))
    print('DF: ' + str(depth_first_search_graph(p)))
    print('UC: ' + str(uniform_cost_search(p)))
    print('DL: ' + str(limited_depth_first_search_tree(p, 4)))
    #print('A*: ' + str(astar_search(p)))

