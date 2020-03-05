import os
import json

import connected_area
import hsv_format
import morph
import neighbour_eight
import neighbour_four


def runProcess():
    with open("conf.json", encoding='UTF-8') as f:
        conf = json.load(f)
        for i in conf['process']:
            print(i)


if __name__ == '__main__':
    runProcess()
