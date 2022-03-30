#playing with queue

#import:
#from Queue import *
#from Node import *
from ToyGames import *
from AdversarialSearch import *


# gioco con ordinamento mosse standard
game1 = ToyGameEx1()
# gioco con ordinamento mosse ottimale
game2 = ToyGameEx2()

(alpha_beta(game1, 'A'))
minimax_decision(game1,'A')

(alpha_beta(game2, 'A'))

