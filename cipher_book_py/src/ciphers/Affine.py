from ciphers.math.cmath import gcd
from ciphers.math.cmath import mi
import random

SYMBOLS = """ !"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\]^_`abcdefghijklmnopqrstuvwxyz{|}~""" # note the space at the front
size = len(SYMBOLS)  #=> 95


def getRandomKey():
    while True:
        key1 = random.randint(2, size)
        key2 = random.randint(2, size)
        if gcd(key1, size) == 1:
            return (key1, key2)

def encrypt(key1, key2, msg):
    ret = ''
    for char in msg:
        pos = SYMBOLS.find(char)
        newPos = (pos * key1 + key2) % size
        ret += SYMBOLS[newPos]
    return ret



if __name__ == '__main__':
    plainText = "Yes, Prime Minister"
    key1, key2 = getRandomKey()
    print("key1, key2 = ", key1, key2)

    encrypted = encrypt(key1, key2, plainText)
    print("encrypted = ", encrypted)



# 若只想传一个key, 那就可以传 key1 * size + key2, 即KEY
# 解析时就用key1 = KEY // size, key2 = KEY % size, 就解析出来了