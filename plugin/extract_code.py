from cv2 import cv2 as cv2
from PIL import Image
import numpy as np
import pytesseract
import sys

def get_code(imagePath):
    # 原图
    src = cv2.imread(imagePath)
    # cv2.imshow("src", src)
    # 提取绿色验证码部分
    hsv = cv2.cvtColor(src, cv2.COLOR_BGR2HSV)
    low_hsv = np.array([36, 43, 46])
    high_hsv = np.array([77, 255, 255])
    mask = cv2.inRange(hsv, lowerb=low_hsv, upperb=high_hsv)
    # cv2.imshow("src", mask)
    ret, binary = cv2.threshold(
        mask, 0, 255, cv2.THRESH_BINARY_INV)
    # cv2.imshow("threshold", binary)
    # cv2.waitKey(0)
    # cv2.destroyAllWindows()
    textImage = Image.fromarray(binary)
    cv2.imwrite('thr_test.jpg', binary)
    text = pytesseract.image_to_string(textImage, lang='eng')
    print("Result:%s" % text)

if __name__ == '__main__':
    imagePath = sys.argv[1]
    get_code(imagePath)
