import pandas as pd

# Indicare due modi diversi per importare il dataset dei giocatori della National Football League
# in un DataFrame PANDAS
# 1: df_nfl = pd.read_csv("nfl.csv")
# 2:
df_nfl = pd.read_csv('nfl.csv', parse_dates=['Birthday'])

# Costruire un indice delle righe del DataFrame, utilizzando il Nome Del Giocatore:
# indicare due modi diversi per costruire l'indice
# 1:
df_nfl_sort = df_nfl.sort_values(by='Name')
df_nfl_sort.reset_index(inplace=True, drop=True)
print(df_nfl_sort)
# 2:
df_nfl_name = df_nfl.set_index("Name")
print(df_nfl_name)

# Contare il numero di giocatori per ogni team: indicare tre modi diversi per rispondere alla precedente query
# 1:
print(df_nfl_sort.groupby('Team').size())
# 2:
print(df_nfl_sort[['Team']].value_counts())
# 3
print(df_nfl_sort.groupby('Team')['Name'].count())

# Stampare i 5 giocatori più pagati, tra tutte le squadre
print(df_nfl.sort_values('Salary', ascending=False).head())

# Stampare il DataFrame ordinandolo prima in base ai Nomi dei team,
# quindi, all'interno dello stesso team, ordinando i giocatori in base al salario, in senso discendente
print(df_nfl.sort_values(['Team', 'Salary'], ascending=[True, False]).groupby('Team').head())

# Stampare il nome del giocatore più vecchio dei New York Jets,
# con la data del suo compleanno: indicare due modi diversi per risolvere l'esercizio
# 1:
df_nfl_old = df_nfl.loc[df_nfl['Team'] == 'New York Jets']
print(df_nfl_old.loc[df_nfl_old['Birthday'] == df_nfl_old['Birthday'].min(), ['Name', 'Birthday']])
# 2:
print(df_nfl.sort_values('Birthday').loc[df_nfl['Team'] == 'New York Jets'].iloc[0])
