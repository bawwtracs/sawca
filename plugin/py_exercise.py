import requests
import re
from bs4 import BeautifulSoup

url = 'https://www.runoob.com/python/python-exercise-example'

headers = {
    'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.92 Safari/537.36'
}

for i in range(1, 101):
    _url = url + str(i) + '.html'
    response = requests.get(url=_url, headers=headers)
    soup = BeautifulSoup(response.content.decode('utf-8'))
    with open('py_exercise.txt', 'a', encoding='utf-8') as f:
        f.write('\n\n' + str(i) + '|>|>')
        for j in soup.find_all('p'):
            title = re.search('题目', j.text)
            desc = re.search('程序分析', j.text)
            if title != None or desc != None:
                f.write(j.text + '\n')
            if desc != None:
                break
