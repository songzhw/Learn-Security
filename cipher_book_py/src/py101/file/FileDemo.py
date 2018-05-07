import os

isAtxtExist = os.path.exists('a.txt')
if(isAtxtExist):
    file = open('a.txt', 'r')  # "a.txt"等同于"./a.txt"
    content = file.read()  # read() : return all the text in the file
    print(content)
    file.close() # python会在程序结束时自动关闭所有打开的文件. 这是自己手动关.

f2 = open('b.txt', 'w') # 没b.txt文件时, "w"模式会自动创建文件
f2.write("line1\nline2")
f2.close()