# Analisi delle transazioni di Bitcoin

import pandas as pd

dates = pd.read_csv('Bitcoin/dates.csv', parse_dates=['time'])
inputs = pd.read_csv('Bitcoin/inputs.csv', names=['in_id', 'tx_id', 'sig_id', 'output_id'])
outputs = pd.read_csv('Bitcoin/outputs.csv', names=['output_id', 'tx_id', 'pk_id', 'value'])
transactions = pd.read_csv('Bitcoin/transactions.csv', names=['tx_id', 'blk_id'])

# 1: controllare se esistono blocchi o transazioni invalide nel dataset ed eventualmente rimuoverli
# Controllo i collegamenti tra i dataframe
if inputs.merge(transactions, how='left', on='tx_id').isna().values.any():
    print('Errore collegamenti tra inputs e transactions')

if outputs.merge(transactions, how='left', on='tx_id').isna().values.any():
    print('Errore collegamenti tra outputs e transactions')

if dates.merge(transactions, how='left', left_on='block_id', right_on='blk_id').isna().values.any():
    print('Errore collegamenti tra dates e transactions')

df = dates.merge(transactions, how='left', left_on='block_id', right_on='blk_id')
print(df.loc[df['tx_id'].isna()])

# Rimuovo la riga da dates
print()
print('Rimuovo il blocco 91857 da dates:')
dates.set_index('block_id', inplace=True)
dates.drop(91857, inplace=True)
print(dates.iloc[91855:91860])
print()

# Ricerca corrispondenza errate tra pk_id e sig_id
print("Ricerca corrispondenza errate tra pk_id e sig_id")
print()

InputOutput = pd.merge(outputs, inputs, on="output_id")

print(InputOutput.head())
print()

Invalid_pk_sig = InputOutput.loc[InputOutput['sig_id'] != InputOutput['pk_id']]

print("Transazioni inconsistenti trovate =", len(Invalid_pk_sig.index))
print()
print(Invalid_pk_sig)

# 2: distribuzione dei blocchi: numero di transazioni per ogni blocco, considerando l'intero periodo
print()
print('Numero di transazioni per ogni blocco:')
print(transactions.groupby('blk_id').count())
# Numero di transazioni per ogni mese
print()
print('Numero di transazioni per ogni mese:')
merge_df = transactions.merge(dates, left_on='blk_id', right_on='block_id')
print(merge_df.groupby(pd.Grouper(key='time', freq='1M'))['tx_id'].count())

# 3: distribuzione delle fee spese in ogni transazione nell'intero periodo
print()
print('Fee per ogni transazione:')
input_output = inputs.merge(outputs, how='left', on='tx_id')
print(outputs)
print(input_output)
