SYMBOLS = """ !"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\]^_`abcdefghijklmnopqrstuvwxyz{|}~"""


def encrypt(key, msg):
    ret = []
    keyIndex = 0

    for char in msg:
        pos = SYMBOLS.find(char)
        if pos == -1:  # 不识别的字符
            ret.append(char)
        else:
            keyChar = key[keyIndex]
            keyPosition = SYMBOLS.find(keyChar)
            pos += keyPosition
            pos %= len(SYMBOLS)
            ret.append(SYMBOLS[pos])
            keyIndex += 1
            keyIndex %= len(key)

    return "".join(ret)



def decrypt(key, msg):
    ret = []
    keyIndex = 0

    for char in msg:
        pos = SYMBOLS.find(char)
        if pos == -1:  # 不识别的字符
            ret.append(char)
        else:
            keyChar = key[keyIndex]
            keyPosition = SYMBOLS.find(keyChar)
            pos -= keyPosition
            pos %= len(SYMBOLS)
            ret.append(SYMBOLS[pos])
            keyIndex += 1
            keyIndex %= len(key)

    return "".join(ret)


if __name__ == '__main__':
    plainText = """Android Architecture Components: A collection of libraries that help you design robust, testable, and maintainable apps. Start with classes for managing your UI component lifecycle and handling data persistence."""
    key = "zhuofangcao_kongchengji"

    encrypted = encrypt(key, plainText)
    decrypted = decrypt(key, encrypted)

    print("encrypted = ", encrypted)
    print("decrypted = ", decrypted)
