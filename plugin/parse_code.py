from urllib import request
from cv2 import cv2 as cv2
from PIL import Image
import pytesseract

url = 'http://202.205.191.56/zfp/com/verifycodeutil/sendcode'

f = open('sendcode.jfif', 'wb')
f.write(request.urlopen(url).read())
f.close()

src = cv2.imread('sendcode.jfif', 1)
gray = cv2.cvtColor(src, cv2.COLOR_BGR2GRAY)
ret, binary = cv2.threshold(
    gray, 0, 255, cv2.THRESH_BINARY_INV | cv2.THRESH_OTSU)
cv2.imshow("threshold", binary)
cv2.waitKey(0)
cv2.destroyAllWindows()
textImage = Image.fromarray(binary)
text = pytesseract.image_to_string(textImage)
print("Result:%s" % text)
