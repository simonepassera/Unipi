import pandas as pd

# Indicare due modi diversi per importare il dataset dei giocatori della National Football League
# in un DataFrame PANDAS
# 1: df_nfl = pd.read_csv("nfl.csv")
#    df_nfl['Birthday'] = pd.to_datetime(df_nfl['Birthday'])
# 2:
df_nfl = pd.read_csv('nfl.csv', parse_dates=['Birthday'])

# Costruire un indice delle righe del DataFrame, utilizzando il Nome Del Giocatore:
# indicare due modi diversi per costruire l'indice
# 1 (~):
df_nfl_sort = df_nfl.sort_values(by='Name')
df_nfl_sort.reset_index(inplace=True, drop=True)
print(df_nfl_sort)
# 2:
df_nfl_name = df_nfl.set_index("Name")
print(df_nfl_name)
# 3:
# df_nfl = pd.read_csv('Dataset/nfl.csv', index_col = 'Name', parse_dates = ['Birthday'])

# Contare il numero di giocatori per ogni team: indicare tre modi diversi per rispondere alla precedente query
# 1:
print(df_nfl_sort.groupby('Team').size())
# 2:
print(df_nfl_sort.Team.value_counts())
# 3:
print(df_nfl_sort.groupby('Team')['Name'].count())
# get_group:
teams = df_nfl.groupby('Team')
group = teams.get_group('New England Patriots')

# Stampare i 5 giocatori più pagati, tra tutte le squadre
print(df_nfl.sort_values('Salary', ascending=False).head())

# Stampare il DataFrame ordinandolo prima in base ai Nomi dei team,
# quindi, all'interno dello stesso team, ordinando i giocatori in base al salario, in senso discendente
print(df_nfl.sort_values(['Team', 'Salary'], ascending=[True, False]))

# Stampare il nome del giocatore più vecchio dei New York Jets,
# con la data del suo compleanno: indicare due modi diversi per risolvere l'esercizio
# 1:
df_nfl_old = df_nfl.loc[df_nfl['Team'] == 'New York Jets']
print(df_nfl_old.loc[df_nfl_old['Birthday'] == df_nfl_old['Birthday'].min(), ['Name', 'Birthday']])
# 2:
print(df_nfl.sort_values('Birthday').loc[df_nfl['Team'] == 'New York Jets'].iloc[0])
# 3:
df_nfl = df_nfl.reset_index().set_index(keys="Team")
print(df_nfl.head())
print(type(df_nfl.loc["New York Jets"]))
print((df_nfl.loc["New York Jets"]).head())
df_nfl.loc["New York Jets"].sort_values("Birthday").head(1)
# 4:
Teams = df_nfl.groupby('Team')
Teams.get_group('New York Jets')[['Name', 'Birthday']].sort_values("Birthday").head(1)
