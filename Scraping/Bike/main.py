import pandas as pd
from matplotlib import pyplot as plt

data = pd.read_csv("SeoulBikeData.csv", encoding="unicode_escape", parse_dates=["Date"], dayfirst=True)
print("Dataset:")
print(data)
print("\nInfo:")
print(data.info())
print("\nDescribe:")
print(data.describe())
print("\nData, ora e numero totale di biciclette in data 2017-12-08:")
bike_date = data[data["Date"] == "2017-12-08"]
print(bike_date[["Date", "Hour", "Rented Bike Count"]])
print("\nData 2017-12-08, numero totale di biciclette tra le 8 e le 20:")
print(bike_date[bike_date["Hour"].between(8, 20)])
print("\nGrafico x=data y=numero di biciclette")
data.plot(x="Date", y="Rented Bike Count")
plt.show()
