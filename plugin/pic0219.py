from cv2 import cv2 as cv2
from PIL import Image
import pytesseract
import numpy as np
import re
import sys


def noise_8(gray_img, k):
    """
    8邻域降噪
    Args:
        image_name: 图片文件命名
        k: 判断阈值

    Returns:
    """
    def calculate_noise_count(img_obj, w, h):
        """
        计算邻域非白色的个数
        Args:
            img_obj: img obj
            w: width
            h: height
        Returns:
            count (int)
        """
        count = 0
        width, height = img_obj.shape
        for _w_ in [w - 1, w, w + 1]:
            for _h_ in [h - 1, h, h + 1]:
                if _w_ > width - 1:
                    continue
                if _h_ > height - 1:
                    continue
                if _w_ == w and _h_ == h:
                    continue
                if img_obj[_w_, _h_] < 230:  # 二值化的图片设置为255
                    count += 1
        return count

    w, h = gray_img.shape
    for _w in range(w):
        for _h in range(h):
            if _w == 0 or _h == 0:
                gray_img[_w, _h] = 255
                continue
            # 计算邻域pixel值小于255的个数
            pixel = gray_img[_w, _h]
            if pixel == 255:
                continue

            if calculate_noise_count(gray_img, _w, _h) < k:
                gray_img[_w, _h] = 255
    return gray_img


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
    ret, binary = cv2.threshold(
        gray, 190, 255, cv2.THRESH_BINARY)

    # # 二值化
    # binary = cv2.adaptiveThreshold(
    #     ~gray, 255, cv2.ADAPTIVE_THRESH_GAUSSIAN_C, cv2.THRESH_BINARY_INV, 35, -5)

    cv2.imwrite('tmp219/0_binary.jpg', binary)

    # kernel = cv2.getStructuringElement(cv2.MORPH_RECT, (2, 2))
    # bin1 = cv2.erode(binary, kernel)

    # cv2.imwrite('tmp/1_erode22.jpg', bin1)

    noise_8(binary, 3)
    cv2.imwrite('tmp219/1_noise8.jpg', binary)

    textImage = Image.fromarray(binary)
    text = pytesseract.image_to_string(textImage, lang='chi_sim')
    print("%s" % re.sub(' ', '', text))


src = cv2.imread('pic0219.jpg', 1)
recognize_text()
