class Game:
    """
    Questa classe impementa l'astrazione di un gioco.
    Per rappresentare un gioco specializzare questa classe implementandone i metodi.
    """

    def player(self, state):
        """Restituisce il giocatore la cui mossa e' in questo stato."""
        raise NotImplementedError

    def actions(self, state):
        """Restituisce la lista delle possibili mosse nello state state."""
        raise NotImplementedError

    def result(self, state, action):
        """Restituisce lo stato che risulta dalla mossa action nello stato state."""
        raise NotImplementedError

    def terminal_test(self, state):
        """Restituisce True se state e' uno stato finale (se il gioco e' terminato),
           False altrimenti."""
        return (len(self.actions)==0)

    def utility(self, state):
        """restituisce il valore di utilita' dello state state per il giocatore."""
        raise NotImplementedError

    