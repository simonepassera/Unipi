# Effettuare lo scraping della pagina (https://www.imdb.com/title/tt0944947/episodes)
# per reperire il rating di ogni episodio di ogni stagione.
# Plottare un barplot in cui si mettono a confronto le valutazione date ai vari episodi della stagione

import os.path
import csv
import requests
from bs4 import BeautifulSoup
import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns

if not os.path.isfile("imdb.csv"):
    base_url = "https://www.imdb.com/title/tt0944947/episodes"
    url = base_url
    headers = {"User-Agent": "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/111.0.0.0 Safari/537.36"}

    imdb = open("imdb.csv", "w")
    writer = csv.writer(imdb)
    writer.writerow(["Season", "Episode", "Rating"])

    while True:
        season_html = requests.get(url, headers=headers)
        soup = BeautifulSoup(season_html.content, 'html.parser')

        season = int(soup.head.title.string.split()[6])
        print(f"Scraping Season {season}")

        for div in soup.find_all("div", attrs={"class": ["list_item odd", "list_item even"]}):
            info = div.find("div", attrs={"class": "info"})
            episode = info.find("strong").get_text()
            rating = float(info.find("span", attrs={"class": "ipl-rating-star__rating"}).get_text())
            writer.writerow([season, episode, rating])

        if season == 1:
            break
        else:
            url = base_url + soup.find("a", attrs={"id": "load_previous_episodes"}).attrs["href"]

    imdb.close()

df = pd.read_csv("imdb.csv")
season_8 = df[df["Season"] == 8]

print(season_8)

sns.barplot(x="Episode", y="Rating", data=season_8)
plt.show()
