import random
import sys

SYMBOLS = "abcdefghijklmnopqrstuvwxyz ,.!?"


def getRandomKey():
    tmp = list(SYMBOLS)
    random.shuffle(tmp)
    return ''.join(tmp)


def checkKeyIsValid(key):
    keyList = list(key)
    symbolList = list(SYMBOLS)
    keyList.sort()
    symbolList.sort()
    if keyList != symbolList:  #Python中比较list, 可以用==, !=. 这点和java不一样
        sys.exit("The key is not a valid one. Please choose a differnt key")


def encrypt(key, msg):
    ret = ''
    for char in msg:
        if char in SYMBOLS:
            index = SYMBOLS.find(char)
            ret += key[index]
        else:
            ret += char
    return ret


if __name__ == '__main__':
    key = getRandomKey()
    checkKeyIsValid(key)

    plainText = "what a lovely day! Have a good day."
    encrypted = encrypt(key, plainText)

    print("encrypted = ", encrypted)


    s1 = "abcd"
    s2 = s1
    s1 = "1234"
    print(s1)  #=> 1234
    print(s2)  #=> abcd
