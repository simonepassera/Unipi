import pandas as pd
import numpy as np

dataframe = pd.read_csv("IrisDataSet.csv")
print(dataframe)
print("---")
print(dataframe.shape)
print("---")
print(dataframe.head())
print("---")
print(dataframe.tail())
print("---")
print(dataframe.info())
print("---")
print(dataframe.describe())
print("---")
print(dataframe["species"].value_counts())
print("---")
np.random.seed(0)
small_iris_ds = dataframe.iloc[np.random.permutation(dataframe.shape[0])].head()
print(small_iris_ds.columns.to_numpy())