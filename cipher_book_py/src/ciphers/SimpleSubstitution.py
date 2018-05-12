import random
import sys

SYMBOLS = "abcdefghijklmnopqrstuvwxyz ,.!?"


def getRandomKey():
    tmp = list(SYMBOLS)  # "abcd", 经过list()后, 变成['a','b','c','d']列表
    random.shuffle(tmp)  # 随机shuffle列表
    return ''.join(tmp)


def checkKeyIsValid(key):
    keyList = list(key)
    symbolList = list(SYMBOLS)
    keyList.sort()
    symbolList.sort()
    if keyList != symbolList:  #Python中比较list, 可以用==, !=. 这点和java不一样
        sys.exit("The key is not a valid one. Please choose a differnt key")


def encrypt(key, msg):
    return translate(key, msg, 'encrypt')

def decrypt(key, msg):
    return translate(key, msg, "decrypt")

def translate(key, msg, mode):
    ret = ''
    src = SYMBOLS
    dst = key
    if mode == 'decrypt':
        src, dst = dst, src

    for char in msg:
        if char in src:
            index = src.find(char)
            ret += dst[index]
        else:
            ret += char
    return ret



if __name__ == '__main__':
    key = getRandomKey()
    checkKeyIsValid(key)

    plainText = "what a lovely day! Have a good day."
    encrypted = encrypt(key, plainText)
    decrypted = decrypt(key, encrypted)

    print("encrypted = ", encrypted) #=> encrypted =  dnp,zpze.yterzuprczHpytzpzq..uzuprm
    print("decrypted = ", decrypted) #=> decrypted =  what a lovely day! Have a good day.

