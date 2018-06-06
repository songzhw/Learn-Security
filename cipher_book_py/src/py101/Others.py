ary = [1, 2, 3, 4, 5]
print(ary[:2])  #=> [1, 2]
print(ary[2:])  #=> [3, 4, 5]

str1 = "result = {1},{0},{1}".format("key", "value")
print(str1)     #=> result = value, key, value

eval("2 + 3")   #=> 5