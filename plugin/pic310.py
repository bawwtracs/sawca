from cv2 import cv2 as cv2
from PIL import Image
import pytesseract
import numpy as np
import re
import sys


def noise_4(img_array, k):
    """
    4邻域降噪
    Args:
        image:图片文件
        k: 阈值 <= 4
    """
    def noise_4_count(img_array, w, h):
        """
        计算4邻域非白色的个数
        Args:
            img_obj: img obj
            w: width
            h: height
        Returns:
            count (int)
        """
        count = 0
        if img_array[w - 1, h] < 230:
            count += 1
        if img_array[w, h - 1] < 230:
            count += 1
        if img_array[w + 1, h] < 230:
            count += 1
        if img_array[w, h + 1] < 230:
            count += 1
        return count

    w, h = img_array.shape

    for _w in range(w):
        for _h in range(h):
            # 外圈直接白色，跳过计算
            if _w == 0 or _h == 0 or _w == w - 1 or _h == h - 1:
                img_array[_w, _h] = 255
                continue
            if img_array[_w, _h] == 255:
                continue
            if noise_4_count(img_array, _w, _h) < k:
                img_array[_w, _h] = 255


def recognize_text():
    gray = cv2.cvtColor(src, cv2.COLOR_BGR2GRAY)

    # 二值化
    binary = cv2.adaptiveThreshold(
        ~gray, 255, cv2.ADAPTIVE_THRESH_GAUSSIAN_C, cv2.THRESH_BINARY_INV, 35, -5)

    # # 二值化
    # ret, binary = cv2.threshold(
    #     gray, 190, 255, cv2.THRESH_BINARY)

    cv2.imwrite('tmp310/0_binary.jpg', binary)
    noise_4(binary, 2)
    cv2.imwrite("tmp310/1_noise4.jpg", binary)
    textImage = Image.fromarray(binary)
    text = pytesseract.image_to_string(textImage, lang='chi_sim')
    print("%s" % re.sub(' ', '', text))


src = cv2.imread('310.jpg', 1)
recognize_text()
