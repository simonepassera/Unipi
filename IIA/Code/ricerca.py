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


# main()
if __name__ == '__main__':
    p = ToyProblem1('A', 'G')
    print('BF: ' + str(breadth_first_search(p)))
    print('DF: ' + str(depth_first_search_graph(p)))
    print('UC: ' + str(uniform_cost_search(p)))
    print('DL: ' + str(limited_depth_first_search_tree(p, 4)))
