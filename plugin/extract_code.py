from cv2 import cv2
from PIL import Image
import numpy as np
import pytesseract
import sys
import re


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


def get_code(imagePath, k):

    # 原图
    src = cv2.imread(imagePath)

    # 提取绿色验证码部分
    hsv = cv2.cvtColor(src, cv2.COLOR_BGR2HSV)
    low_hsv = np.array([36, 43, 46])
    high_hsv = np.array([77, 255, 245])
    mask = cv2.inRange(hsv, lowerb=low_hsv, upperb=high_hsv)

    ret, binary = cv2.threshold(
        mask, 0, 255, cv2.THRESH_BINARY_INV)

    # cv2.imshow('mask',mask)
    # cv2.imwrite('tmp/binary.jpg', mask)

    noise_4(binary, k)
    # cv2.waitKey(0)
    # cv2.destroyAllWindows()

    textImage = Image.fromarray(mask)
    text = pytesseract.image_to_string(textImage)
    print("Result:%s" % re.sub('\n', '', text))


if __name__ == '__main__':
    imagePath = sys.argv[1]
    k = int(sys.argv[2])
    if k == 0:
        k = 2
    get_code(imagePath, k)
