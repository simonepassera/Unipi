from utils import (
    removeall, unique, first, argmax, probability,
    isnumber, issequence, Expr, expr, subexpressions
)
import random
import inspect

def psource (obj):
    print(inspect.getsource(obj))

# ______________________________________________________________________________


# Rappresentazione di una base di conoscenza ------------------------
# classe KB

class KB:

    """La classe KB implementa una base di conoscenza.
    Per creare una base di conoscenza occorre specializzare
    questa classe."""

    def __init__(self, sentence=None):
        """ Costruttore."""
        raise NotImplementedError

    def tell(self, sentence):
        """Aggiunge la sentence alla base di conoscenza."""
        raise NotImplementedError

    def ask(self, query):
        """Restituisce una sostituzione che renda la query True,
           se non e' possibile, restituisce False."""
        return first(self.ask_generator(query), default=False)

    def ask_generator(self, query):
        """Restituisce tutte le sostituzioni che rendono la query True."""
        raise NotImplementedError

    def retract(self, sentence):
        """Rimuove la sentence dalla base di conoscenza."""
        raise NotImplementedError


# classe PropKB

class PropKB(KB):
    """ Classe che implementa una base di conoscenza per la logica proposizionale."""

    def __init__(self, sentence=None):
        """ Costruttore."""
        self.clauses = []
        if sentence:
            self.tell(sentence)

    def tell(self, sentence):
        """Aggiunge le clausole nella sentence alla KB."""
        self.clauses.extend(conjuncts(to_cnf(sentence)))

    def ask_generator(self, query):
        """Restituisce la sostituzione vuota {} se la KB implica la query in input,
           altrimenti nessun risultato."""
        if tt_entails(Expr('&', *self.clauses), query):
            yield {}

    def ask_if_true(self, query):
        """Restituisce True se la KB implica la query, altrimenti restituisce False."""
        for _ in self.ask_generator(query):
            return True
        return False

    def retract(self, sentence):
        """Elimina le clausole della sentence dalla KB."""
        for c in conjuncts(to_cnf(sentence)):
            if c in self.clauses:
                self.clauses.remove(c)

# classe PropDefiniteKB
class PropDefiniteKB(PropKB):
    """ Una base di conoscenza di clausole definite."""

    def tell(self, sentence):
        """Aggiunge una clausola definita alla KB."""
        assert is_definite_clause(sentence), "Deve essere una clausola definita"
        self.clauses.append(sentence)

    def ask_generator(self, query):
        """Restituisce la sostituzione vuota {} se la KB implica la query in input,
           altrimenti nessun risultato."""
        if pl_fc_entails(self.clauses, query):
            yield {}

    def retract(self, sentence):
        """Elimina le clausole della sentence dalla KB."""
        self.clauses.remove(sentence)

    def clauses_with_premise(self, p):
        """Restituisce una lista delle clausole nella KB
           che abbiano p nella loro premessa."""

        return [c for c in self.clauses
                if c.op == '==>' and p in conjuncts(c.args[0])]
                


# Funzioni di utilita' di base ------------------------------------------
                
def is_symbol(s):
    """Restituisce True se l'argomento s inizia con un carattere alfabetico,
       False altrimenti."""
    return isinstance(s, str) and s[:1].isalpha()


def is_var_symbol(s):
    """Restituisce True se l'argomento s e' una variabile logica
       (una stringa il cui primo carattere e' in minuscolo)
       False altrimenti."""
    return is_symbol(s) and s[0].islower()


def is_prop_symbol(s):
    """Restituisce True se l'argomento s e' un simbolo proposizionale
       (una stringa il cui primo carattere e' maiuscolo),
       False altrimenti."""
    return is_symbol(s) and s[0].isupper()


def variables(s):
    """ Restituisce l'insieme delle variabili nell'espressione s.
    Es.
    >>> variables(expr('F(x, x) & G(x, y) & H(y, z) & R(A, z, 2)')) == {x, y, z}
    True
    """
    return {x for x in subexpressions(s) if is_variable(x)}


def is_definite_clause(s):
    """ Restituisce True se l'argomento s e' una clausola definita
        (se contiene esattamente un letterale positivo),
        False altrimenti."""
    if is_symbol(s.op):
        return True
    elif s.op == '==>':
        antecedent, consequent = s.args
        return (is_symbol(consequent.op) and
                all(is_symbol(arg.op) for arg in conjuncts(antecedent)))
    else:
        return False


def parse_definite_clause(s):
    """Restituisce gli antecedenti e il conseguente di una clausola definita."""
    
    assert is_definite_clause(s)
    if is_symbol(s.op):
        return [], s
    else:
        antecedent, consequent = s.args
        return conjuncts(antecedent), consequent


# Useful constant Exprs used in examples and code:
A, B, C, D, E, F, G, P, Q, x, y, z, v = map(Expr, 'ABCDEFGPQxyzv')

def is_variable(x):
    """ Restituisce True se l'argomento e' una variabile
        (una variabile e' una espressione senza argomenti e un simbolo in minuscolo
         come operatore)."""
    return isinstance(x, Expr) and not x.args and x.op[0].islower()

# ---------------------------------------------------------------------------


# Inferenza logica mediante tabella di verita' -------------------------------

# funzione tt_entails
def tt_entails(kb, alpha):
    """Implementa l'algoritmo di inferenza mediante truth table
       (TV-consegue?).
       Data la base di conoscenza in kb, restituisce
       True se kb implica alpha
       False altrimenti."""    

    assert not variables(alpha)
    return tt_check_all(kb, alpha, prop_symbols(kb & alpha), {})

# funzione ausiliaria
def tt_check_all(kb, alpha, symbols, model):
    """Funzione ausiliaria per l'implementazione di tt_entails."""
    print ("tt_check_all: " + repr(model))
    if not symbols:
        if pl_true(kb, model):
            result = pl_true(alpha, model) # valuta la proposizione nel modello
            assert result in (True, False)
            return result
        else:
            return True
    else:
        P, rest = symbols[0], symbols[1:]
        # extend estende il modello con P = True o P = False
        return (tt_check_all(kb, alpha, rest, extend(model, P, True)) and
                tt_check_all(kb, alpha, rest, extend(model, P, False)))



# Funzioni di utilita' per tt_entails ---------------------------------------

def pl_true(exp, model={}):
    """ Data una espressione in logica proposizionale ed un modello,
        restituisce True se l'espressione e' vera nel modello,
        e False altrimenti."""
    
    if exp in (True, False):
        return exp
    op, args = exp.op, exp.args
    if is_prop_symbol(op):
        return model.get(exp)
    elif op == '~':
        p = pl_true(args[0], model)
        if p is None:
            return None
        else:
            return not p
    elif op == '|':
        result = False
        for arg in args:
            p = pl_true(arg, model)
            if p is True:
                return True
            if p is None:
                result = None
        return result
    elif op == '&':
        result = True
        for arg in args:
            p = pl_true(arg, model)
            if p is False:
                return False
            if p is None:
                result = None
        return result
    p, q = args
    if op == '==>':
        return pl_true(~p | q, model)
    elif op == '<==':
        return pl_true(p | ~q, model)
    pt = pl_true(p, model)
    if pt is None:
        return None
    qt = pl_true(q, model)
    if qt is None:
        return None
    if op == '<=>':
        return pt == qt
    elif op == '^':  # xor or 'not equivalent'
        return pt != qt
    else:
        raise ValueError("Operatore non legale nell'espressione logica " + str(exp))

def extend(s, var, val):
    """ Restituisce una copia della sostituzione s, estesa assegnando var a val."""
    s2 = s.copy()
    s2[var] = val
    return s2

def prop_symbols(x):
    """ Restituisce una lista di tutti i simboli proposizionali nell'argomento x."""
    if not isinstance(x, Expr):
        return []
    elif is_prop_symbol(x.op):
        return [x]
    else:
        return list(set(symbol for arg in x.args for symbol in prop_symbols(arg)))




# Risoluzione -----------------------------------------------------------------------


def pl_resolution(KB, alpha):
    """Risoluzione per logica proposizionale.
       Restituisce True se alpha segue dalla base di conoscenza KB."""
    clauses = KB.clauses + conjuncts(to_cnf(~alpha))
    new = set()
    while True:
        n = len(clauses)
        pairs = [(clauses[i], clauses[j])
                 for i in range(n) for j in range(i+1, n)]
        for (ci, cj) in pairs:
            resolvents = pl_resolve(ci, cj)
            if False in resolvents:
                return True
            new = new.union(set(resolvents))
        if new.issubset(set(clauses)):
            return False
        for c in new:
            if c not in clauses:
                clauses.append(c)


def pl_resolve(ci, cj):
    """Restituisce tutte le clausole che possono essere
       ottenute risolvendo le clausole ci e cj."""
    clauses = []
    for di in disjuncts(ci):
        for dj in disjuncts(cj):
            if di == ~dj or ~di == dj:
                dnew = unique(removeall(di, disjuncts(ci)) +
                              removeall(dj, disjuncts(cj)))
                clauses.append(associate('|', dnew))
    return clauses

# ----------------------------------------------------------------------
# Funzioni di utilita' per la risoluzione ------------------------------

def to_cnf(s):
    """ Conversione in Forma Normale Congiuntiva
        (Conjunctive Normal Form).
        Converte la formula s in forma normale congiuntiva.
        Ad esempio:
        to_cnf('~(B | C)') restituisce come output:
        (~B & ~C)
    """
    s = expr(s)
    if isinstance(s, str):
        s = expr(s)
    s = eliminate_implications(s)  # Steps 1, 2 
    s = move_not_inwards(s)  # Step 3
    return distribute_and_over_or(s)  # Step 4


def eliminate_implications(s):
    """ Cambia le implicazioni in forma equivalente usando solo AND, OR e NOT come
        operatori logici."""
    s = expr(s)
    if not s.args or is_symbol(s.op):
        return s  # Atoms are unchanged.
    args = list(map(eliminate_implications, s.args))
    a, b = args[0], args[-1]
    if s.op == '==>':
        return b | ~a
    elif s.op == '<==':
        return a | ~b
    elif s.op == '<=>':
        return (a | ~b) & (b | ~a)
    elif s.op == '^':
        assert len(args) == 2  # 
        return (a & ~b) | (~a & b)
    else:
        assert s.op in ('&', '|', '~')
        return Expr(s.op, *args)


def move_not_inwards(s):
    """ Riscrive la formula s, spostando all'interno
        l'operatore di negazione.
        Es.:
        >>> move_not_inwards(~(A | B))
        (~A & ~B)"""
    s = expr(s)
    if s.op == '~':
        def NOT(b):
            return move_not_inwards(~b)
        a = s.args[0]
        if a.op == '~':
            return move_not_inwards(a.args[0])  # ~~A ==> A
        if a.op == '&':
            return associate('|', list(map(NOT, a.args)))
        if a.op == '|':
            return associate('&', list(map(NOT, a.args)))
        return s
    elif is_symbol(s.op) or not s.args:
        return s
    else:
        return Expr(s.op, *list(map(move_not_inwards, s.args)))


def distribute_and_over_or(s):
    """ Data uan formula s che consista in congiunzioni e disgiunzioni
        di letterali, restituisce una formula equivalente in
        forma normale congiuntiva.
        Es.
        >>> distribute_and_over_or((A & B) | C)
        ((A | C) & (B | C))
    """
    s = expr(s)
    if s.op == '|':
        s = associate('|', s.args)
        if s.op != '|':
            return distribute_and_over_or(s)
        if len(s.args) == 0:
            return False
        if len(s.args) == 1:
            return distribute_and_over_or(s.args[0])
        conj = first(arg for arg in s.args if arg.op == '&')
        if not conj:
            return s
        others = [a for a in s.args if a is not conj]
        rest = associate('|', others)
        return associate('&', [distribute_and_over_or(c | rest)
                               for c in conj.args])
    elif s.op == '&':
        return associate('&', list(map(distribute_and_over_or, s.args)))
    else:
        return s


def associate(op, args):
    """ Dato un operatore associativo, restituisce una espressione
        con lo stesso significato, ma appiattita, cioe' con istanze innestate
        dello stesso operatore promosse al livello piu' alto.
        Es.
        >>> associate('&', [(A&B),(B|C),(B&C)])
        (A & B & (B | C) & B & C)
        >>> associate('|', [A|(B|(C|(A&B)))])
        (A | B | C | (A & B))
    """
    args = dissociate(op, args)
    if len(args) == 0:
        return _op_identity[op]
    elif len(args) == 1:
        return args[0]
    else:
        return Expr(op, *args)


_op_identity = {'&': True, '|': False, '+': 0, '*': 1}


def dissociate(op, args):
    """ Dato un operatore associativo, restituisce come risultato
        una lista appiattita tale che
        Expr(op, *result) abbia lo stesso significato di Expr(op, *args)."""

    result = []

    def collect(subargs):
        for arg in subargs:
            if arg.op == op:
                collect(arg.args)
            else:
                result.append(arg)
    collect(args)
    return result


def conjuncts(s):
    """ Restituisce una lista dei termini congiuntivi
        presenti nella formula s.
        Es.
        >>> conjuncts(A & B)
        [A, B]
        >>> conjuncts(A | B)
        [(A | B)]
    """
    return dissociate('&', [s])


def disjuncts(s):
    """ Restituisce una lista dei termini disgiuntivi
        presenti nella formula s.
        Es.
        >>> disjuncts(A | B)
        [A, B]
        >>> disjuncts(A & B)
        [(A & B)]
    """
    return dissociate('|', [s])

# -------------------------------------------------------------

# Concatenazione in avanti per Logica Proposizionale
def pl_fc_entails(KB, q):
    """ Implementa l'algoritmo di concatenazione in avanti
        per verificare se la base di conoscenza KB di clausole definite
        implica q."""
    
    count = {c: len(conjuncts(c.args[0]))
             for c in KB.clauses
             if c.op == '==>'}
    inferred = defaultdict(bool)
    agenda = [s for s in KB.clauses if is_prop_symbol(s.op)]
    while agenda:
        p = agenda.pop()
        if p == q:
            return True
        if not inferred[p]:
            inferred[p] = True
            for c in KB.clauses_with_premise(p):
                count[c] -= 1
                if count[c] == 0:
                    agenda.append(c.args[1])
    return False

# Algoritmi di soddisfacibilita' ------------------------------------

# Algoritmo DPLL ----------------------------------------------------

def dpll_satisfiable(s):
    """ Verifica la soddisfacibilita' di una formula s in logica proposizionale.
        Restituisce un modello in cui s e' soddisfatta,
        oppure False
    """
    clauses = conjuncts(to_cnf(s))
    symbols = prop_symbols(s) #restituisce la lista dei simboli
    # proposizionali in s
    print (symbols)
    return dpll(clauses, symbols, {})


def dpll(clauses, symbols, model):
    """Verifica se le clausole sono vere nel modello parziale."""
    unknown_clauses = []  # clausole con valore di verita' sconosciuto
    for c in clauses:
        val = pl_true(c, model) #valuta c nel modello
        if val is False:
            return False
        if val is not True:
            unknown_clauses.append(c)
    if not unknown_clauses:
        return model
    P, value = find_pure_symbol(symbols, unknown_clauses)
    if P:
        print ('Pure symbol: ' + repr(P) + ' has value ' + repr(value))
        return dpll(clauses, removeall(P, symbols), extend(model, P, value))
    P, value = find_unit_clause(clauses, model)
    if P:
        print ('Unit clause: ' + repr(P) + ' has value ' + repr(value))
        return dpll(clauses, removeall(P, symbols), extend(model, P, value))
    if not symbols:
        raise TypeError("L'argomento deve essere di tipo Expr.")    
    P, symbols = symbols[0], symbols[1:]
    return (dpll(clauses, symbols, extend(model, P, True)) or
            dpll(clauses, symbols, extend(model, P, False)))

# funzioni di utilita' per l'algoritmo DPLL 
def find_pure_symbol(symbols, clauses):
    """ Trova un simbolo e il suo valore se compare solo come
        letterale positivo (o solo come negativo) nelle clausole in input.
        Es.
        >>> find_pure_symbol([A, B, C], [A|~B,~B|~C,C|A])
        (A, True)
    """
    for s in symbols:
        found_pos, found_neg = False, False
        for c in clauses:
            if not found_pos and s in disjuncts(c):
                found_pos = True
            if not found_neg and ~s in disjuncts(c):
                found_neg = True
        if found_pos != found_neg:
            return s, found_pos
    return None, None


def find_unit_clause(clauses, model):
    """Cerca un assegnazione forzata, se possibile, da una clausola con una
    sola variabile non vincolata nel modello in ingresso.
    Es.
    >>> find_unit_clause([A|B|C, B|~C, ~A|~B], {A:True})
    (B, False)
    """
    for clause in clauses:
        P, value = unit_clause_assign(clause, model)
        if P:
            return P, value
    return None, None


def unit_clause_assign(clause, model):
    """Restituisce una singola coppia variabile/valore che renda la clausola vera
       nel modello, se possibile.
       Es.
        >>> unit_clause_assign(A|B|C, {A:True})
        (None, None)
        >>> unit_clause_assign(B|~C, {A:True})
        (None, None)
        >>> unit_clause_assign(~A|~B, {A:True})
        (B, False)
    """
    P, value = None, None
    for literal in disjuncts(clause):
        sym, positive = inspect_literal(literal)
        if sym in model:
            if model[sym] == positive:
                return None, None  # clausola gia' vera 
        elif P:
            return None, None      # piu' di una variabile non vincolatamore than 1 unbound variable
        else:
            P, value = sym, positive
    return P, value


def inspect_literal(literal):
    """Restituisce il simbolo in questo letterale e il valore che dovrebbere
       assumere per rendere il letterale vero.
       Es.
        >>> inspect_literal(P)
        (P, True)
        >>> inspect_literal(~P)
        (P, False)
    """
    if literal.op == '~':
        return literal.args[0], False
    else:
        return literal, True

# Algoritmo WalkSat ---------------------------------------------------------

def WalkSAT(clauses, p=0.5, max_flips=10000):
    """Verifica la soddisfacibilita' di tutte le clausole in input
    invertendo casualmente i valori delle variabili.
    L'argomento p rappresenta la probabilita' con cui invertire il valore di una variabile.
    L'argomento max_flips indica il numero massimo di inversioni.
    """
    # insieme di tutti i simboli in tutte le clausole
    symbols = set(sym for clause in clauses for sym in prop_symbols(clause))
    # il modello in model e' ottenuto trmite un'assegnazione casuale
    # di True/False ai simboli nelle clausole
    model = {s: random.choice([True, False]) for s in symbols}
    print ('Initial random assignement ' + repr(model))
    for i in range(max_flips):
        satisfied, unsatisfied = [], []
        for clause in clauses:
            (satisfied if pl_true(clause, model) else unsatisfied).append(clause)
        if not unsatisfied:  # se il modello soddisfa tutte la clausole
            return model     # restituiscilo in output
        print ('Still unsatisfied: ' + repr(unsatisfied))
        clause = random.choice(unsatisfied)
        print ('Working on ' + repr(clause))
        if probability(p): # con probabilita' p inverti il valore nel modello
                           # di un simbolo scelto a caso
            sym = random.choice(prop_symbols(clause))
            print ('Random step on ' + repr(clause))
        else: # altrimenti inverti il valore di verita' del simbolo in clause
              # che massimizza il numero di clausole soddisfatte
            print ('Optimization step on ' + repr(clause))
            def sat_count(sym):
                # Restituisce il numero di clausole soddisfatte
                # dopo aver invertito il valore del simbolo
                model[sym] = not model[sym]
                count = len([clause for clause in clauses if pl_true(clause, model)])
                model[sym] = not model[sym]
                return count
            sym = argmax(prop_symbols(clause), key=sat_count)
        model[sym] = not model[sym]
    # se non si trova alcuna soluzione entro il numero massimo di inversioni max_flips
    # restituisce None (fallimento)
    print ("Giving up after " + str(max_flips) + " flips")
    return None
    

