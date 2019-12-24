def f(n, sum):
    if n == 0:
        return sum
    sum += n*n
    return f(n-1, sum)

res = f(3, 0)
print(res)
# res2 = f2(3, 4, 0)
# print(res2)
