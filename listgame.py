# A List Game
# https://open.kattis.com/problems/listgame


def prime_factors(n):
    i = 2
    count = 0
    while i * i <= n:
        if n % i:
            i += 1
        else:
            n //= i
            count = count + 1
    if n > 1:
        return count + 1
    return count


print(prime_factors(eval(input())))
