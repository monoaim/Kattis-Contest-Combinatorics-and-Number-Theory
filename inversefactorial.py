# Inverse Factorial
# https://open.kattis.com/problems/inversefactorial


import math


def InverseFactorial(number):
    assert isinstance(number,
                      int) and number > 0, "only accepts positive integer"
    special_case = {1: (0, 1), 2: 2, 6: 3, 24: 4, 120: 5, 720: 6}
    n = len(special_case) + 1
    try:
        if number <= max(special_case, key=int):
            return special_case[number]
        length = int(math.log10(number)) + 1

        halflnPi = math.log(math.pi) / 2
        ln10 = math.log(10)

        # Approx for factorial stated by S. Ramanujan; https://math.stackexchange.com/a/138326
        while (math.ceil(
            (n * math.log(n) - n + math.log(n * (1 + 4 * n * (1 + 2 * n))) / 6
             + halflnPi) / ln10)) != length:
            n = n + 1
        if math.factorial(n) == number:
            return n
        raise KeyError
    except KeyError:
        return "{} is not factorial number".format(number)


print(InverseFactorial(eval(input())))
