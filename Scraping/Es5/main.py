from pycoingecko import CoinGeckoAPI
import json
import os

cg = CoinGeckoAPI()
exchanges_list = cg.get_exchanges_list()

volumes_list = list()
keys = ['name', 'trade_volume_24h_btc']

request = 1
total = len(exchanges_list)

for exchange_dict in exchanges_list:
    tickers_dist = cg.get_exchanges_tickers_by_id(exchange_dict['id'])
    values_dict = {x: exchange_dict[x] for x in keys}
    values_dict.update({'converted_volume_usd_btc': tickers_dist['tickers'][0]['converted_volume']['usd']})
    volumes_list.append(values_dict)
    os.system('clear')
    print(f'Progress {request}/{total}')
    request += 1

with open("exchanges.json", "w") as fp:
    json.dump(volumes_list, fp, indent=2)
