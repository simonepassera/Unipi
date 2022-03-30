from utils2 import (
    removeall, unique, first, argmax, probability, some,
    isnumber, issequence, Expr, expr, subexpressions
)

from P_Logic import *

def unify(x, y, s):
    """ Unifica le espressioni x, y con la sostituzione s.
        Restituisce una sostituzione che rende x e y uguali,
        oppure None se x e y non possono essere unificati.
        Gli argomenti x e y possono essere variabili, costanti,
        liste o oggetti di tipo Expr, anche in notazione infissa.
        Es.
        >>> ppsubst(unify(x + y, y + C, {}))
        {x: y, y: C}
    """
    print ('unify(' + repr(x) + ', ' + repr(y) + ', ' + repr(s) + ')')
    if s is None:
        print ('FAIL')
        return None
    elif x == y:
        return s
    elif is_variable(x):
        return unify_var(x, y, s)
    elif is_variable(y):
        return unify_var(y, x, s)
    elif isinstance(x, Expr) and isinstance(y, Expr):
        return unify(x.args, y.args, unify(x.op, y.op, s))
    elif isinstance(x, str) or isinstance(y, str):
        return None
    elif issequence(x) and issequence(y) and len(x) == len(y):
        if not x: return s
        return unify(x[1:], y[1:], unify(x[0], y[0], s))
    else:
        return None

def unify_var(var, x, s):
    if var in s:
        return unify(s[var], x, s)
    elif x in s:
        return unify(var, s[x], s)
    elif occur_check(var, x, s):
        return None
    else:
        return extendR(s, var, x)

def extendR (s, var, x):
    new_s = extend(s, var, x)
    # bug fix of AIMA-Python unification
    cascade_substitution(new_s)
    return new_s
    
def occur_check(var, x, s):
    """ Restituisce True se la variabile var
        occorre in x (o in subst(s, x));
        False altrimenti."""
    if var == x:
        return True
    elif is_variable(x) and x in s:
        return occur_check(var, s[x], s)
    elif isinstance(x, Expr):
        return (occur_check(var, x.op, s) or
                occur_check(var, x.args, s))
    elif isinstance(x, (list, tuple)):
        return some(lambda element: occur_check(var, element, s), x)
    else:
        return False

def cascade_substitution(s):
    """This method allows to return a correct unifier in normal form
    and perform a cascade substitution to s.
    For every mapping in s perform a cascade substitution on s.get(x)
    and if it is replaced with a function ensure that all the function 
    terms are correct updates by passing over them again.
    >>> s = {x: y, y: G(z)}
    >>> cascade_substitution(s)
    >>> s == {x: G(z), y: G(z)}
    True
    """

    for x in s:
        s[x] = subst(s, s.get(x))
        if isinstance(s.get(x), Expr) and not is_variable(s.get(x)):
            # Ensure Function Terms are correct updates by passing over them again
            s[x] = subst(s, s.get(x))

def subst(s, x):
    """Substitute the substitution s into the expression x.
    >>> subst({x: 42, y:0}, F(x) + y)
    (F(42) + 0)
    """
    if isinstance(x, list):
        return [subst(s, xi) for xi in x]
    elif isinstance(x, tuple):
        return tuple([subst(s, xi) for xi in x])
    elif not isinstance(x, Expr):
        return x
    elif is_var_symbol(x.op):
        return s.get(x, x)
    else:
        return Expr(x.op, *[subst(s, arg) for arg in x.args])

            
