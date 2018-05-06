num = 23

def foo():
    # print("foo() num1 = ", num)
    num = 100
    print("foo() num2 = ", num)

def foo2():
    print("foo() num1 = ", num)
    print("foo() num2 = ", num)

def bar():
    print("bar() num4 = ", num)

foo()  # => 100
print("main num3 = ", num) #=> 23
bar()  #=> 23
