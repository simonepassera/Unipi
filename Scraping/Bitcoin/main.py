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
