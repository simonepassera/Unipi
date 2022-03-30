#import:
from Problem import *
from Node import *
from Queue import *

import math
import random
import sys

def breadth_first_search(problem):
    """Ricerca-grafo in ampiezza"""
    explored = [] # insieme (implementato come una lista) degli stati gia' visitati
    node = Node(problem.initial_state) # il costo del cammino e' inizializzato nel costruttore del nodo    
    # controlla se lo stato iniziale e' uno stato obiettivo
    if problem.goal_test(node.state):
        return node.solution(explored_set = explored)
    frontier = FIFOQueue() # la frontiera e' una coda FIFO
    frontier.insert(node)
    while not frontier.isempty():
        # seleziona il nodo per l'espansione
        node = frontier.pop()
        explored.append(node.state) # inserisce il nodo nell'insieme dei nodi esplorati
        for action in problem.actions(node.state):
            child_node = node.child_node(problem,action)
            # controlla se lo stato del nodo figlio non e' nell'insieme dei nodi esplorati
            # e non e' nella frontiera
            if (child_node.state not in explored) and (not frontier.contains_state(child_node.state)):
                # controlla se lo stato del nodo figlio e' uno stato obiettivo
                if problem.goal_test(child_node.state):
                    return child_node.solution(explored_set = explored)
                # se lo stato non e' uno stato obiettivo allora inserisci il nodo nella frontiera
                frontier.insert(child_node)
    return None # in questo caso ritorna con fallimento

def uniform_cost_search(problem):
    """Ricerca-grafo UC"""
    explored = [] # insieme (implementato come una lista) degli stati gia' visitati
    node = Node(problem.initial_state) # il costo del cammino e' inizializzato nel costruttore del nodo
    # la frontiera e' una coda coda con priorita'
    frontier = PriorityQueue(f = lambda x:x.path_cost) #lambda serve a definire una funzione anonima a runtime
    frontier.insert(node)
    while not frontier.isempty():
        # seleziona il nodo per l'espansione
        node = frontier.pop() # estrae il nodo con costo minore
        # controlla se lo stato del nodo e' uno stato obiettivo
        if problem.goal_test(node.state):
            return node.solution(explored_set = explored)
        else:
            # se non lo e' inserisci lo stato nell'insieme degli esplorati
            explored.append(node.state)
            print('UC esploro lo stato '+node.state+' con costo {}'.format(node.path_cost))
        for action in problem.actions(node.state):
            child_node = node.child_node(problem, action)
            # controlla se lo stato del nodo figlio non e' nell'insieme dei nodi esplorati
            # e non e' nella frontiera
            if (child_node.state not in explored) and (not frontier.contains_state(child_node.state)):
                frontier.insert(child_node)
            # se lo stato del nodo figlio e' gia' nella frontiera, ma con un costo piu' alto
            # allora sostituisci il nodo nella frontiera con il nodo figlio
            elif frontier.contains_state(child_node.state) and (frontier.get_node(frontier.index_state(child_node.state)).path_cost >
                                                                child_node.path_cost):
                frontier.remove(frontier.index_state(child_node.state))
                frontier.insert(child_node)

    return None # in questo caso ritorna con fallimento

def astar_search(problem):
    """ Ricerca A*. """
    explored = [] # insieme (implementato come una lista) degli stati gia' visitati
    node = Node(problem.initial_state) # il costo del cammino e' inizializzato nel costruttore del nodo
    #lambda serve a definire una funzione anonima a runtime
    frontier = PriorityQueue(f = lambda x: (x.path_cost) + problem.h(x))
    frontier.insert(node)
    while not frontier.isempty():
        # seleziona un nodo per l'espansione
        node = frontier.pop() # seleziona il nodo con costo del cammino piu' basso
        # controlla se lo stato del nodo e' uno stato obiettivo
        if problem.goal_test(node.state):
            return node.solution(explored_set = explored)
        else:
            # se lo stato non e' uno stato obiettivo aggiungilo all'insieme degli esplorati
            explored.append(node.state)

            print('A* esploro lo stato '+node.state+' con costo {}'.format(node.path_cost+problem.h(node)))
            
        for action in problem.actions(node.state):
            child_node = node.child_node(problem, action)
            # controlla se lo stato del nodo figlio non e' nell'insieme dei nodi esplorati
            # e non e' nella frontiera
            if (child_node.state not in explored) and (not frontier.contains_state(child_node.state)):
                frontier.insert(child_node)
            # se lo stato del nodo figlio e' gia' nella frontiera, ma con un costo piu' alto
            # allora sostituisci il nodo nella frontiera con il nodo figlio
            elif frontier.contains_state(child_node.state) and \
                 (frontier.get_node(frontier.index_state(child_node.state)).path_cost > child_node.path_cost):
                frontier.remove(frontier.index_state(child_node.state))
                frontier.insert(child_node)
    return None #in questo caso ritorna con fallimento

def depth_first_search_tree(problem):
    """Ricerca-albero in profondita' """
    node = Node(problem.initial_state) # il costo del cammino e' inizializzato nel costruttore del nodo 
    # controlla se lo stato iniziale e' uno stato obiettivo
    if problem.goal_test(node.state):
        return node.solution()
    frontier = LIFOQueue() #la frontiera e' una coda LIFO
    frontier.insert(node)
    while not frontier.isempty():
        node = frontier.pop() #estrae il nodo dalla frontiera
##        print('Node: %s' % node) ########################################### <------- added print here
##        input(' ') #########################################################
        # controlla se lo stato del nodo e' uno stato obiettivo
        if problem.goal_test(node.state):
            return node.solution()
        # espandi la frontiera
        for action in problem.actions(node.state):
            child_node = node.child_node(problem,action)
            #if (child_node.state not in explored) and (not frontier.contains(child_node)):
            frontier.insert(child_node)
    return None # in questo caso ritorna con fallimento


def depth_first_search_graph(problem):
    """Ricerca-grafo in profondita' """
    explored = [] # insieme (implementato come una lista) degli stati gia' visitati
    node = Node(problem.initial_state) # il costo del cammino e' inizializzato nel costruttore del nodo 
    # controlla se lo stato iniziale e' uno stato obiettivo
    if problem.goal_test(node.state):
        return node.solution()
    frontier = LIFOQueue() #la frontiera e' una coda LIFO
    frontier.insert(node)
    while not frontier.isempty():
        node = frontier.pop() #estrae il nodo dalla frontiera
        # controlla se lo stato del nodo e' uno stato obiettivo
        if problem.goal_test(node.state):
            return node.solution(explored_set = explored)
        else:
            # se lo stato non e' uno stato obiettivo aggiungilo all'insieme degli esplorati
            explored.append(node.state)
        # espandi la frontiera
        for action in problem.actions(node.state):
            child_node = node.child_node(problem,action)
            if (child_node.state not in explored) and (not frontier.contains(child_node.state)):
                frontier.insert(child_node)
    return None # in questo caso ritorna con fallimento

def recursive_depth_first_search(problem, node):
    """Ricerca in profondita' ricorsiva """
    # controlla se lo stato del nodo e' uno stato obiettivo
    if problem.goal_test(node.state):
        return node.solution()
    # in caso contrario continua con la ricerca
    for action in problem.actions(node.state):
        child_node = node.child_node(problem, action)
        result = recursive_depth_first_search(problem, child_node)
        if result is not None:
            return result
    return None


def limited_depth_first_search_tree(problem, depth_limit):
    """Ricerca-albero in profondita' con depth_limit"""
    node = Node(problem.initial_state) # il costo del cammino e' inizializzato nel costruttore del nodo 
    # controlla se lo stato iniziale e' uno stato obiettivo
    if problem.goal_test(node.state):
        return node.solution()
    frontier = LIFOQueue() #la frontiera e' una coda LIFO
    frontier.insert(node)
    while not frontier.isempty():
        node = frontier.pop() #estrae il nodo dalla frontiera
        if node.depth > depth_limit:
            continue
		# controlla se lo stato del nodo e' uno stato obiettivo
        if problem.goal_test(node.state):
            return node.solution()
        # espandi la frontiera
        for action in problem.actions(node.state):
            child_node = node.child_node(problem,action)
            frontier.insert(child_node)
    return None # in questo caso ritorna con fallimento


def limited_recursive_depth_first_search(problem, node, depth_limit):
    """Ricerca in profondita' ricorsiva con depth_limit"""
    # controlla se lo stato del nodo e' uno stato obiettivo

    if depth_limit < 0:
        return None
    
    if problem.goal_test(node.state):
        return node.solution()
    # in caso contrario continua con la ricerca
    for action in problem.actions(node.state):
        child_node = node.child_node(problem, action)
        result = limited_recursive_depth_first_search(problem, child_node,depth_limit-1)
        if result is not None:
            return result
    return None


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


# ---------------------------------------------------------------------------------------
# LOCAL SEARCH

def hill_climbing(problem):
    """ Ricerca locale - Hill-climbing."""
    current = Node(problem.initial_state)
    while True:
        neighbors =  [current.child_node(problem, action) for action in problem.actions(current.state)]
        # se current non ha successori esci e restituisci current
        if not neighbors:
            break
        # scegli il vicino con valore piu' alto
        neighbor = (sorted(neighbors,key = lambda x:problem.value(x), reverse = True))[0]       
        if problem.value(neighbor) <= problem.value(current):
            break
        else:
            current = neighbor
    return current
           #[current.state, problem.value(current)]


def schedule_ex(k=100, lam=0.5, limit=100):
    """ Una possibile funzione di scheduling per
        l'algoritmo di simulated annealing."""
    # La temperatura e' fatta descrescere seguendo una legge esponenziale
    # Nota: k e' il valore della temperatura iniziale
    return lambda t: (k * math.exp(-lam * t) if t < limit else 0)

# con iper - parametri k=100, lam=0.1, limit=100
# original k=20, lam=0.005, limit=1000
# riesce a trovare la soluzione

def simulated_annealing(problem, schedule=schedule_ex()):
    """Ricerca locale - Simulated Annealing"""
    current = Node(problem.initial_state)
    for t in range(sys.maxsize): # sys.maxsize e' il massimo numero intero
        T = schedule(t)
        if T == 0:
            return current
        neighbors =  [current.child_node(problem, action) for action in problem.actions(current.state)]
        # se current non ha successori esci e restituisci current
        if not neighbors:
            return current
        next = random.choice(neighbors)
        delta_e = problem.value(next) - problem.value(current)
        if delta_e > 0 or (random.random()<(math.exp(delta_e / T))):
            current = next



