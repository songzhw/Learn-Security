def encrypt(key, msg):
    table = [''] * key  # => 若key为3, 那结果就是['', '', '']
    idx = 0
    idxInTable = 0
    for char in msg:
        table[idxInTable] += char
        idx += 1  # python中没有idx++这样的操作
        idxInTable = idx % key

    print("==============")
    for c in table:
        print(c)
    print("==============")
    return ''.join(table)


if __name__ == '__main__':
    plainText = "common sense"
    encrypted = encrypt(3, plainText)
    print("encrypted =", encrypted)


    # s1 = [''] * 4
    # print("value = ", s1)  #=> ['', '', '', '']
    # s2 = ['a', 'd', 'b', '32']
    # print("joined = ", ''.join(s2))  # => adb32 (最开头有个空格?!)
    #
    # for c in "what a day":
    #     print(c)  #=> 每一个char一行

'''
plain text = common sense

加密后的table是:
==============
cm n
ooss
mnee
==============

encrpyted = cm noossmnee


'''