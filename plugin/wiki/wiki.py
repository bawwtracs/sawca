import requests
import re
# from bs4 import BeautifulSoup

host = 'https://wiki.tw.wjbk.site/wiki/'

search = '本拉登'

headers = {
    'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.92 Safari/537.36'
}

s = requests.session()
response = s.get(host + search)
# soup = BeautifulSoup(response.text, features="lxml")
# duty_one = soup.find(id='dutyUser')
# print(response.text)
with open('blt.txt', 'a', encoding='utf-8') as f:
    f.write(response.text)