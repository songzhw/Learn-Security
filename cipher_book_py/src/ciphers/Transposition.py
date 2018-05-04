import math


def encrypt(key, msg):
    table = [''] * key  # => 若key为3, 那结果就是['', '', '']
    idx = 0
    idxInTable = 0
    for char in msg:
        table[idxInTable] += char
        idx += 1  # python中没有idx++这样的操作
        idxInTable = idx % key

    return ''.join(table)


def decrypt(key, msg):
    length = len(msg)
    blanStartPos = length % key  # blankCount = key - (length%key)
    rowCount = math.ceil(length / key)
    ret = [''] * rowCount  # 其实key就是columnCount

    row = 0
    col = 0
    for char in msg:
        isLast1 = row == rowCount
        isLast2 = row == (rowCount - 1) and col >= blanStartPos

        if isLast1 or isLast2:
            row = 0
            col += 1

        ret[row] += char
        row += 1

    return ''.join(ret)
    # Python中没有&&, ||, 只有and, or. 可读性好些


if __name__ == '__main__':
    plainText = "common senses"
    key = 3
    encrypted = encrypt(key, plainText)
    decrypted = decrypt(key, encrypted)
    print("encrypted =", encrypted)
    print("decrypted = " + decrypted)

    # s1 = [''] * 4
    # print("value = ", s1)  #=> ['', '', '', '']
    # s2 = ['a', 'd', 'b', '32']
    # print("joined = ", ''.join(s2))  # => adb32 (最开头有个空格?!)
    #
    # for c in "what a day":
    #     print(c)  #=> 每一个char一行
    #
    # print("ceil = ", math.ceil(10 / 3)) #=> 4
    #
    # 格式化输出 (%d 数字, %s字符串, %r布尔)
    # print("col = %d, row = %d, char = %s, %r, %r" % (col, row, char, isLast1, isLast2))

'''
plain text = common senses, key = 3

加密后的table是:
==============
cm ns
ooss
mnee
==============

encrpyted = cm nsoossmnee


'''
