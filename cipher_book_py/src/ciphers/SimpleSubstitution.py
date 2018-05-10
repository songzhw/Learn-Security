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
    if keyList != symbolList:
        sys.exit("The key is not a valid one. Please choose a differnt key")


if __name__ == '__main__':
    key = getRandomKey()
    checkKeyIsValid(key)
