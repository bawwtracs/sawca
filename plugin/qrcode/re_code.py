import sys
import json
import cv2

from connected_area import connected_area
from hsv_format import hsv_format
from morph import morph
from neighbour_eight import neighbour_eight
from neighbour_four import neighbour_four
from thresh import thresh


def runProcess():
    with open("conf.json", encoding='UTF-8') as f:
        conf = json.load(f)
        for i in conf['process']:
            operation = i['operation']
            if operation == 'connected_area':
                connected_area(i['k'])
            if operation == 'hsv_format':
                hsv_format(i['lower'], i['upper'])
            if operation == 'morph':
                morph(i['structuring_element'], i['size'], i['morph'])
            if operation == 'neighbour_eight':
                neighbour_eight(i['k'])
            if operation == 'neighbour_four':
                neighbour_four(i['k'])
            if operation == 'thresh':
                thresh(i['thresh'])


if __name__ == '__main__':
    # imgPath = sys.argv[1]
    imgPath = 'qrcode.png'
    runProcess()
