def hello(params):
    print(" (from submodule) Hello : ", params)
    return


class FooClass(object):
    """my very first class: FooClass"""
    version = 0.1  # class (data) attribute

    def __init__(self, nm='John Doe'):
        """constructor"""
        self.name = nm  # class instance (data) attribute
        print('Created a class instance for', nm)

    def addMe2Me(self, x):  # does not use 'self'
        """apply + operation to argument"""
        return x + x

# 模块能定义函数，类和变量
