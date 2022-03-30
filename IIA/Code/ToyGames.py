#import
from Game import *

class ToyGame1(Game):
    """Rapresentazione di un gioco di esempio."""

    risultato = {'B1':3, 'B2':12, 'B3':8, 'C1':2, 'C2':4, 'C3':6, 'D1':14, 'D2':5, 'D3':2}
    


    def actions(self, state):
        if state == 'A':
            return ['a1','a2','a3']
        if state == 'B':
            return ['b1','b2','b3']
        if state == 'C':
            return ['c1','c2','c3']
        if state == 'D':
            return ['d1','d2','d3']
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


class ToyGameEx1(Game):
    """Rapresentazione di un gioco di esempio
       per l'esercitazione."""

    utilita = {'Z1':10, 'Z2':4, 'Z3':5, 'Z4':12, 'Z5':3, 'Z6':4, 'Z7':3, 'Z8':2, 'Z9':6, 'Z10':7 , 'Z11':10 , 'Z12':8}
    risultato_mossa = {'a1':'B','a2':'C','b1':'D','b2':'E','b3':'F','c1':'G','c2':'H','c3':'K','d1':'Z1','d2':'Z2','e1':'Z3','e2':'Z4','f1':'Z5','f2':'Z6',
                       'g1':'Z7','g2':'Z8','h1':'Z9','h2':'Z10','k1':'Z11','k2':'Z12'}

    def actions(self, state):
        if state == 'A':
            return ['a1','a2']
        if state == 'B':
            return ['b1','b2','b3']
        if state == 'C':
            return ['c1','c2','c3']
        if state == 'D':
            return ['d1','d2']
        if state == 'E':
            return ['e1','e2']
        if state == 'F':
            return ['f1','f2']
        if state == 'G':
            return ['g1','g2']
        if state == 'H':
            return ['h1','h2']
        if state == 'K':
            return ['k1','k2']
        return []

    def result(self, state, move):
        return self.risultato_mossa[move]
        

    def utility(self, state):
        #print('sono in stato {}'.format(state))
        return self.utilita[state]

    def terminal_test(self, state):
        return state[0] in ('Z')


class ToyGameEx2(Game):
    """Rapresentazione di un gioco di esempio
       per l'esercitazione.
       -- Variante con ordinamento ottimale delle mosse."""

    utilita = {'Z1':10, 'Z2':4, 'Z3':5, 'Z4':12, 'Z5':3, 'Z6':4, 'Z7':3, 'Z8':2, 'Z9':6, 'Z10':7 , 'Z11':10 , 'Z12':8}

    risultato_mossa = {'a1':'B','a2':'C','b1':'D','b2':'E','b3':'F','c1':'G','c2':'H','c3':'K','d1':'Z1','d2':'Z2','e1':'Z3','e2':'Z4','f1':'Z5','f2':'Z6',
                       'g1':'Z7','g2':'Z8','h1':'Z9','h2':'Z10','k1':'Z11','k2':'Z12'}

    def actions(self, state):
        if state == 'A':
            return ['a1','a2']
        if state == 'B':
            return ['b3','b1','b2']
        if state == 'C':
            return ['c1','c2','c3']
        if state == 'D':
            return ['d1','d2']
        if state == 'E':
            return ['e2','e1']
        if state == 'F':
            return ['f2','f1']
        if state == 'G':
            return ['g1','g2']
        if state == 'H':
            return ['h1','h2']
        if state == 'K':
            return ['k1','k2']
        return []

    def result(self, state, move):
        return self.risultato_mossa[move]
        

    def utility(self, state):
        #print('sono in stato {}'.format(state))
        return self.utilita[state]

    def terminal_test(self, state):
        return state[0] in ('Z')

