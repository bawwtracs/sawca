from cv2 import cv2 as cv2
from PIL import Image
import pytesseract
import re
import sys


def recognize_text():
    gray = cv2.cvtColor(src, cv2.COLOR_BGR2GRAY)

    # 二值化
    ret, binary = cv2.threshold(
        gray, 155, 255, cv2.THRESH_BINARY)

    cv2.imwrite('tmp223/0_binary.jpg', binary)

    textImage = Image.fromarray(binary)
    text = pytesseract.image_to_string(textImage, lang='chi_sim')
    print("%s" % re.sub(' ', '', text))


src = cv2.imread('pic0223.jpg', 1)
recognize_text()
