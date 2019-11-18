from cv2 import cv2 as cv2
from PIL import Image
import pytesseract


def recognize_text():
    # # 灰度图片
    # gray = cv2.cvtColor(src, cv2.COLOR_BGR2GRAY)
    # # 二值化
    # binary = cv2.adaptiveThreshold(
    #     ~gray, 255, cv2.ADAPTIVE_THRESH_GAUSSIAN_C, cv2.THRESH_BINARY, 35, -5)
    gray = cv2.cvtColor(src, cv2.COLOR_BGR2GRAY)
    ret, binary = cv2.threshold(gray, 0, 255, cv2.THRESH_BINARY_INV | cv2.THRESH_OTSU)
    cv2.imshow("threshold", binary)
    kernel = cv2.getStructuringElement(cv2.MORPH_RECT, (1, 8))
    binl = cv2.morphologyEx(binary, cv2.MORPH_OPEN, kernel)
    kernel = cv2.getStructuringElement(cv2.MORPH_RECT, (8, 1))
    open_out = cv2.morphologyEx(binl, cv2.MORPH_OPEN, kernel)
    cv2.bitwise_not(open_out, open_out)
    cv2.imshow("dstImage", open_out)

    # textImage = Image.fromarray(open_out)
    # text = pytesseract.image_to_string(textImage)
    # print("Result:%s"%text)
src = cv2.imread('c:/HJL/sawca/plugin/qrcode.png', 1)
# src = cv2.imread("target.jpg")
cv2.imshow("srcImage", src)
recognize_text()
cv2.waitKey(0)
cv2.destroyAllWindows()
