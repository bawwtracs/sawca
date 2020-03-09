from cv2 import cv2

img = cv2.imread("1.jpg")

gray = cv2.cvtColor(img, cv2.COLOR_RGB2GRAY)

gauss = cv2.GaussianBlur(gray, (3, 3), 1)

maxvalue = 255


def onthreshold(x):
    value = cv2.getTrackbarPos("value", "Threshold")
    a, binary = cv2.threshold(gauss, value, maxvalue, cv2.THRESH_BINARY)
    b, binary_inv = cv2.threshold(
        gauss, value, maxvalue, cv2.THRESH_BINARY_INV)
    c, trunc = cv2.threshold(gauss, value, maxvalue, cv2.THRESH_TRUNC)
    d, to_zero = cv2.threshold(gauss, value, maxvalue, cv2.THRESH_TOZERO)
    e, to_zero_inv = cv2.threshold(
        gauss, value, maxvalue, cv2.THRESH_TOZERO_INV)
    if(a):
        cv2.imshow("Binary", binary)
    # if(b):
    #     cv2.imshow("Binary_INV", binary_inv)
    # if(c):
    #     cv2.imshow("TRUNC", trunc)
    # if(d):
    #     cv2.imshow("TO_ZERO", to_zero)
    # if(e):
    #     cv2.imshow("TO_ZERO_INV", to_zero_inv)


cv2.namedWindow("Threshold")

cv2.createTrackbar("value", "Threshold", 0, 255, onthreshold)

cv2.imshow("Threshold", img)

cv2.waitKey(0)
