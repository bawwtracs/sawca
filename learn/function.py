def bmi(weight, height):
    print(weight / height**2)
    return


bmi(65, 1.7)

# 可变类型、不可变类型与参数传递
# 不可变： 整数、字符串、元组。
# 可变： 列表、字典。


def ChangeInt(a):
    a = 10


b = 2
ChangeInt(b)
print(b)


def changeme(mylist):
    "修改传入的列表"
    mylist.append([1, 2, 3, 4])
    print("函数内取值: ", mylist)
    return


mylist = [10, 20, 30]
changeme(mylist)
print("函数外取值: ", mylist)


def printinfo(name, age):
    "打印任何传入的字符串"
    print("Name: ", name)
    print("Age ", age)
    return


printinfo(age=50, name="miki")


def printinfo1(arg1, *vartuple):
    "打印任何传入的参数"
    print("输出: ")
    print(arg1)
    for var in vartuple:
        print(var)
    return


printinfo1(70, 60, 50)

# 匿名函数


def sum(arg1, arg2): return arg1 + arg2


print("相加后的值为 : ", sum(10, 20))
print("相加后的值为 : ", sum(20, 20))
