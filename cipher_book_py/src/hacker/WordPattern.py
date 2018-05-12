
import pprint  #打印出来更美观

def getWordPattern(word):
    word1 = word.upper()
    num = 0
    map = {} # 字典/映射
    ret = [] # 列表
    for char in word1:
        if char not in map:
            map[char] = str(num)  #不加str(), 那在'.'.join(ret)时会报错: TypeError: sequence item 0: expected str instance, int found
            num += 1
        ret.append(map[char])
    return '.'.join(ret)

if __name__ == '__main__':
    print(": ", getWordPattern("book"))
