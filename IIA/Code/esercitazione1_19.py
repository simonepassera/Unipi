from ToyProblems import *
from SearchingAlgorithms import *

actions_order1 = ['UP','RIGHT','DOWN','LEFT'] 
actions_order2 = ['LEFT','DOWN','RIGHT','UP']


actions_order3 = ['UP','DOWN','RIGHT','LEFT']
actions_order4 = ['LEFT','RIGHT','DOWN','UP']

#### DEFINIZIONE DEI PROBLEMI NELLE DIVERSE VARIANTI ---------------------------------------------------------------------------
#
# teseo1 - lista delle possibili azioni ['UP','RIGHT','DOWN','LEFT']
teseo1 = Labirinto_Teseo(possible_actions = actions_order1)
# teseo2 - lista delle possibili azioni ['LEFT','DOWN','RIGHT','UP']
teseo2 = Labirinto_Teseo(possible_actions = actions_order2)




# teseo3 - lista delle possibili azioni ['UP','DOWN','RIGHT','LEFT']  --- non visto in classe
teseo3 = Labirinto_Teseo(possible_actions = actions_order3)
# teseo4 - lista delle possibili azioni ['LEFT','RIGHT','DOWN','UP']  --- non visto in classe
teseo4 = Labirinto_Teseo(possible_actions = actions_order4)
# teseo1_f - problema con definizione di euristica h = |row - row_goal| + |col - col_goal|
#            f = g+h
# lista delle possibili azioni ['UP','RIGHT','DOWN','LEFT']
teseo1_f = Labirinto_Teseo_F(possible_actions = actions_order1)
# teseo1_h - problema con definizione di euristica h = |row - row_goal| + |col - col_goal|
#            f = h
# lista delle possibili azioni ['UP','RIGHT','DOWN','LEFT']
teseo1_h = Labirinto_Teseo_H(possible_actions = actions_order1)
# teseo1_local - problema con definizione di funzione value per ricerca locale value = - (|row - row_goal| + |col - col_goal|)
# lista delle possibili azioni ['UP','RIGHT','DOWN','LEFT']
teseo1_local = Labirinto_Teseo_Local(possible_actions = actions_order1)
#
# --------------------------------------------------------------------------------------------------------------------------------


# ESERCIZIO 1
# DF - TESEO2
# ASTAR, REC_DF - TESEO1
# ASTAR CON EURISTICA - TESEO1_F, TESEO1_H

# 1 PAG 5
#depth first
soluzione_DF_ex1 = depth_first_search_tree(teseo1)
print("Soluzione EX.1 con depth_first_search_tree e actions = ['LEFT','DOWN','RIGHT','UP']")
print_solution(soluzione_DF_ex1); print()
input('>')

soluzione_DF_ex1 = depth_first_search_tree(teseo2)
print("Soluzione EX.1 con depth_first_search_tree e actions = ['LEFT','DOWN','RIGHT','UP']")
print_solution(soluzione_DF_ex1); print()
input('>')
##

####% 2 PAG 6
soluzione_RDF_ex1 = recursive_depth_first_search(teseo1, Node(state = teseo1.initial_state))
print("Soluzione EX.1 con recursive_depth_first_search e actions = ['UP','RIGHT','DOWN','LEFT']")
print_solution(soluzione_RDF_ex1); print()
input('>')
##
##% 3 PAG 6 - A*
soluzione_ASTAR_ex1 = astar_search(teseo1)
print("Soluzione EX.1 con astar_search e actions = ['UP','RIGHT','DOWN','LEFT'] -- f = -node.depth")
print_solution(soluzione_ASTAR_ex1); print()

# 4 PAG 7 - limited depth
max_depth = 5
soluzione_DF_ex1_limited = limited_depth_first_search_tree(teseo1,max_depth)
print("Soluzione EX.1 con limited depth_first_search_tree, actions = ['UP','RIGHT','DOWN','LEFT'], max_depth = 5")
print_solution(soluzione_DF_ex1_limited); print()
input('>')

# 5 - PAG 8 
# astar
soluzione_ASTAR_ex1_H = astar_search(teseo1_h)
print("Soluzione EX.1 con astar_search, actions = ['UP','RIGHT','DOWN','LEFT'], f = h")
print_solution(soluzione_ASTAR_ex1_H); print()

# 6 - PAG 9
soluzione_ASTAR_ex1_F = astar_search(teseo1_f)
print("Soluzione EX.1 con astar_search, actions = ['UP','RIGHT','DOWN','LEFT'], f = g+h")
print_solution(soluzione_ASTAR_ex1_F); print()


# uniform cost
soluzione_UC_ex1 = uniform_cost_search(teseo1)
print("Soluzione EX.1 con uniform_cost_search e actions = ['UP','RIGHT','DOWN','LEFT']")
print_solution(soluzione_UC_ex1); print()





# ricerca locale
# simulated annealing
soluzione_SIMULATED_ANNEALING = simulated_annealing(teseo1_local)
print("Soluzione EX.1 con simulated_annealing, actions = ['UP','RIGHT','DOWN','LEFT'], value = h")
print(soluzione_SIMULATED_ANNEALING); print()

# hill-climbing
soluzione_HILL_C = hill_climbing(teseo1_local)
print("Soluzione EX.1 con hill_climibing, actions = ['UP','RIGHT','DOWN','LEFT'], value = h")
print(soluzione_HILL_C); print()





# ESERCIZIO 2 ------- NON VISTO IN CLASSE ---------------------------------------------
# DF - TESEO4
# ASTAR, REC_DF - TESEO3

max_depth = 5
soluzione_DF_ex2 = limited_depth_first_search_tree(teseo4,max_depth)
print("Soluzione EX.2 con depth_first_search_tree, actions = ['LEFT','RIGHT','DOWN','UP'], max_depth = 5")
print_solution(soluzione_DF_ex2); print()

soluzione_ASTAR_ex2 = astar_search(teseo3)
print("Soluzione EX.2 con astar_search e actions = ['UP','DOWN','RIGHT','LEFT']")
print_solution(soluzione_ASTAR_ex2); print()

soluzione_ASTAR_ex2_v2 = astar_search(teseo4)
print("Soluzione EX.2 con astar_search e actions = ['LEFT','RIGHT','DOWN','UP']")
print_solution(soluzione_ASTAR_ex2_v2); print()

soluzione_UC_ex2 = uniform_cost_search(teseo3)
print("Soluzione EX.2 con uniform_cost_search e actions = ['UP','DOWN','RIGHT','LEFT']")
print_solution(soluzione_UC_ex2); print()

soluzione_RDF_ex2 = limited_recursive_depth_first_search(teseo3, Node(state = teseo3.initial_state),max_depth)
print("Soluzione EX.2 con recursive_depth_first_search, actions = ['UP','DOWN','RIGHT','LEFT'], max_depth = 5")
print_solution(soluzione_RDF_ex2); print()  



