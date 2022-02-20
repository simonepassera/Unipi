import strutture_dati


def breadth_first_search(problem):
    """Ricerca-grafo in ampiezza"""
    explored = []  # insieme (implementato come una lista) degli stati gia' visitati
    node = strutture_dati.Node(problem.initial_state)  # il costo del cammino è inizializzato
    # nel costruttore del nodo
    # controlla se lo stato iniziale e' uno stato obiettivo

    if problem.goal_test(node.state):
        return node.solution(explored_set=explored)

    frontier = strutture_dati.FIFOQueue()  # la frontiera e' una coda FIFO
    frontier.insert(node)

    while not frontier.is_empty():
        # seleziona il nodo per l'espansione
        node = frontier.pop()
        explored.append(node.state)  # inserisce il nodo nell'insieme dei nodi esplorati

        for action in problem.actions(node.state):
            child_node = node.child_node(problem, action)
            # controlla se lo stato del nodo figlio non è nell'insieme dei nodi esplorati
            # e non e' nella frontiera
            if (child_node.state not in explored) and (not frontier.contains(child_node.state)):
                # controlla se lo stato del nodo figlio è uno stato obiettivo
                if problem.goal_test(child_node.state):
                    return child_node.solution(explored_set=explored)
            # se lo stato non è uno stato obiettivo allora inserisci il nodo nella frontiera
            frontier.insert(child_node)
    return None  # in questo caso ritorna con fallimento


