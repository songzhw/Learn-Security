import Submodule                    # 类似java中的import. 但同包中也要导入
from sub2.SubSecond import world    # 类似java中的static import


if __name__ == '__main__':
    print("run app: ")
    Submodule.hello("[params]")
    world("[modules]")

    obj = Submodule.FooClass("szw")
    print("3 * 2 = ", obj.addMe2Me(3))