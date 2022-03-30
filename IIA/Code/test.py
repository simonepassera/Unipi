from P_Logic import *

kb = expr('(Mitico ==> ~Mortale) & (~Mitico ==> (Mortale & Mammifero)) & ((~Mortale | Mammifero) ==> Corna) & (Corna ==> Magico) & (~Mitico)')

print (prop_symbols(kb))

dpll_satisfiable('(Mitico ==> ~Mortale) & (~Mitico ==> (Mortale & Mammifero)) & ((~Mortale | Mammifero) ==> Corna) & (Corna ==> Magico) & (~Mitico)')
