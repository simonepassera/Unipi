# esercitazione 2: Adversarial Search

#import:
from ToyGames import *
from AdversarialSearch import *


# gioco con ordinamento mosse standard
game1 = ToyGameEx1()
# gioco con ordinamento mosse ottimale
game2 = ToyGameEx2()

stato_iniziale = 'A' # inizia sempre da A

# --- GAME 1 ---
# applica minimax a game1
print('MINIMAX -- ordinamento mosse standard')
minimax_decision(game1,stato_iniziale)
print()
# applica alpha-beta a game1
print('ALPHA-BETA -- ordinamento mosse standard')
alpha_beta(game1, stato_iniziale)
print()

# --- GAME 2 ---
# applica minimax a game2
print('MINIMAX -- ordinamento mosse ottimale')
minimax_decision(game2,stato_iniziale)
print()
# applica alpha-beta a game2
print('ALPHA-BETA -- ordinamento mosse ottimale')
alpha_beta(game2, stato_iniziale)
print()
