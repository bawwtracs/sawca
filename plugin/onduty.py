import requests
import re
from bs4 import BeautifulSoup
import json

f = open("../config/setting.json", encoding='utf-8')
setting = json.load(f)
username = setting['com']['username']
password = setting['com']['password']
host = "http://qk.quickcreate.cn:8888/"
auth_url = '/mess/logindo.jsp?prename=oa&username=' + \
    username+'&password=' + password
index_url = "/skins/blue/main.jsp"
duty_url = "/biz/duty/dutyhealth/dutyInfo.jsp"

headers = {
    'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.92 Safari/537.36'
}

s = requests.session()
response = s.get(host + auth_url)
response = s.get(host + duty_url)
soup = BeautifulSoup(response.text, features="html5lib")
duty_one = soup.find(id='dutyUser')
print(duty_one.text)
