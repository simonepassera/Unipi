import pandas as pd
from matplotlib import pyplot as plt

# Con riferimento al dataset del noleggio delle biciclette di Seoul,
# calcolare per ogni anno (2017 e 2018) il numero medio di biciclette prese a nolo,
# la temperatura media, l'intensità del vento media e la quantità media di pioggia
# e visualizzare le 4 medie, raggruppate per anno, in un "grouped bar plot"

df_bike = pd.read_csv('SeoulBikeData.csv', encoding='unicode_escape', parse_dates=['Date'], dayfirst=True)
df_plot = df_bike.groupby(df_bike.Date.dt.year)[['Rented Bike Count', 'Temperature(°C)', 'Wind speed (m/s)', 'Rainfall(mm)']].mean().reset_index()
print(df_plot)
# Grouped bar chart:
df_plot.plot(kind='bar', x='Date', y=['Rented Bike Count', 'Temperature(°C)', 'Wind speed (m/s)', 'Rainfall(mm)'], title='Valori medi 2017/2018')
plt.ylabel('Values')
plt.show()

# Subplots:
fig, axs = plt.subplots(2, 2)
fig.suptitle('Valori medi 2017/2018', fontsize=16)
width = 0.7

axs[0, 0].bar(df_plot['Date'], df_plot['Rented Bike Count'], width, color='coral')
axs[0, 0].set_title("Rented Bike")
axs[0, 0].set_ylim(0, 800)

axs[0, 1].bar(df_plot['Date'], df_plot['Temperature(°C)'], width, color='khaki')
axs[0, 1].set_title('Temperature')
axs[0, 1].set_ylabel('°C')

axs[1, 0].bar(df_plot['Date'], df_plot['Wind speed (m/s)'], width, color='mediumspringgreen')
axs[1, 0].set_title('Wind speed')
axs[1, 0].set_ylabel('m/s')

axs[1, 1].bar(df_plot['Date'], df_plot['Rainfall(mm)'], width, color='deepskyblue')
axs[1, 1].set_title('Rainfall')
axs[1, 1].set_ylabel('mm')

for ax in axs.flat:
    ax.set_xticks([2017, 2018])

plt.show()
