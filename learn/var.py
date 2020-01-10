age = 25
height = 170
weight = 75
name = "bawwtracs"
exist = man = True
join_date, job, com, salary = 20190401, 'software engineer', 'mc', 998.98

print(age)
print(height)
print(weight)
print(name)
print(exist)
print(man)
print(job)
print(com)
print(join_date)
print(salary)

# 字符串切片
print(job[:8])
print(job[-8:])

# 重复操作
print('hello? '*2)

# List
domains = ['Latin', '911', 20040911]

# Touple 类似于不可变List
absolute = (True, False)

# Dictionary
bawwtracs = {}
bawwtracs['age'] = age
bawwtracs['height'] = height
bawwtracs['weight'] = weight
bawwtracs['job'] = job
bawwtracs['com'] = com

print(bawwtracs)
print(bawwtracs.keys())
print(bawwtracs.values())
print('my name is bawwtracs and weight is %d and job is %s' %
      (bawwtracs['weight'], bawwtracs['job']))

cmd = '''/data/jdk8/bin/java -Xms512m -Xmx512m -Xss512k -jar /data/websocket-cat/im-0.0.1-SNAPSHOT.jar > /data/websocket-cat/bootstrap.txt &'''
print(cmd)

# 全局变量、局部变量
# 函数内的变量为局部变量

# parse

# int(x [,base])
# 将x转换为一个整数

# long(x [,base] )
# 将x转换为一个长整数

# float(x)
# 将x转换到一个浮点数

# complex(real [,imag])
# 创建一个复数

# str(x)
# 将对象 x 转换为字符串

# repr(x)
# 将对象 x 转换为表达式字符串

# eval(str)
# 用来计算在字符串中的有效Python表达式,并返回一个对象

# tuple(s)
# 将序列 s 转换为一个元组

# list(s)
# 将序列 s 转换为一个列表

# set(s)
# 转换为可变集合

# dict(d)
# 创建一个字典。d 必须是一个序列 (key,value)元组。

# frozenset(s)
# 转换为不可变集合

# chr(x)
# 将一个整数转换为一个字符

# unichr(x)
# 将一个整数转换为Unicode字符

# ord(x)
# 将一个字符转换为它的整数值

# hex(x)
# 将一个整数转换为一个十六进制字符串

# oct(x)
# 将一个整数转换为一个八进制字符串
