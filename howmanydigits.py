# How Many Digits?
# https://open.kattis.com/problems/howmanydigits


import math


halflnPi = math.log(math.pi) / 2
ln10 = math.log(10)
while True:
    try:
        n = eval(input())
        if n == 0 or n == 1:
            print(1)
        else:
            # Approx for factorial stated by S. Ramanujan; https://math.stackexchange.com/a/138326
            print((math.ceil((n * math.log(n) - n + math.log(
                n * (1 + 4 * n * (1 + 2 * n))) / 6 + halflnPi) / ln10)))
    except EOFError:
        break
