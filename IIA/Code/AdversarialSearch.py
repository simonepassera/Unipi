from Game import *

#------ minimax search ------
def minimax_decision(game, state):
    def max_value(state):
        if game.terminal_test(state):
            # se in state il gioco e' concluso restituisci il risultato
            return game.utility(state)
        v = - float('inf') # v e' inizializzato a - infinito
        for a in game.actions(state):
            v = max(v, min_value(game.result(state,a)))
        print('MAX: stato %s - utilita\' %s' % (state,v))
        return v

    def min_value(state):
        if game.terminal_test(state):
            # se in state il gioco e' concluso restituisci il risultato
            return game.utility(state)
        v = float('inf') # v e' inizializzato a + infinito
        for a in game.actions(state):
            v = min(v,max_value(game.result(state,a)))
        print('MIN: stato %s - utilita\' %s' % (state,v))
        return v
    
    best_action = max(game.actions(state), key = lambda x:min_value(game.result(state,x)))
    # best_action e' l'argomento (l'azione) che massimizza l'output di min_value
    print("L\'azione selezionata e\' %s " % (best_action))
    return best_action



#------ alpha-beta search ------
def alpha_beta(game,state):
    def max_value(state, alpha, beta):
        if game.terminal_test(state):
            # se in state il gioco e' concluso restituisci il risutato
            return game.utility(state)
        v = - float('inf') # v e' inizializzato a - infinito
        for a in game.actions(state):
            v = max(v, min_value(game.result(state,a), alpha, beta))
            if v >=beta: # taglio beta
                print('MAX: stato %s - utilita\' %s - TAGLIO BETA (alpha = %s, beta = %s)' % (state,v,alpha,beta))
                return v
            alpha = max(alpha,v) # aggiorna il migliore per MAX
        print('MAX: stato %s - utilita\' %s - (alpha = %s, beta = %s)' % (state,v,alpha,beta))
        return v

    def min_value(state, alpha, beta):
        if game.terminal_test(state):
            # se in state il gioco e' concluso restituisci il risultato
            return game.utility(state)
        v = float('inf') # v e' inizializzato a + infinito
        for a in game.actions(state):
            v = min(v, max_value(game.result(state,a), alpha, beta))
            if v <= alpha: # taglio alpha
                print('MIN: stato %s - utilita\' %s - TAGLIO ALPHA (alpha = %s, beta = %s)' % (state,v,alpha,beta))
                return v
            beta = min(beta,v) # aggiorna il migliore per MIN
        print('MIN: stato %s - utilita\' %s - (alpha = %s, beta = %s)' % (state,v,alpha,beta))
        return v
        
    #inizializza alpha e beta
    alpha = - float('inf')
    beta = float('inf')

    best_action = None
    # esegue un ciclo esterno di max_value "controllato"
    # memorizzando la mossa migliore
    for a in game.actions(state):
        v = min_value(game.result(state,a), alpha, beta)
        if v > alpha:
            # se lo score della mossa a e' il migliore fino a qui
            # allora memorizza la mossa a
            best_action = a
            alpha = v
    print("L\'azione selezionata e\' %s" % (best_action))
    return best_action