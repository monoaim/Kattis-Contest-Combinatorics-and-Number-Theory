# Goldbach's Conjecture
# https://open.kattis.com/problems/goldbach2


def sieve(n):
    size = n // 2
    sieve = [1] * size
    for i in range(1, int(n**0.5)):
        if sieve[i]:
            val = 2 * i + 1
            sieve[i + val::val] = [0] * (((size - 1) - i) // val)
    return [2] + [i * 2 + 1 for i, v in enumerate(sieve) if v and i > 0]


def isPrime(n):
    if n == 2 or n == 3: return True
    if n < 2 or n % 2 == 0: return False
    if n < 9: return True
    if n % 3 == 0: return False
    r = int(n**0.5)
    f = 5
    while f <= r:
        if n % f == 0 or n % (f + 2) == 0:
            return False
        f += 6
    return True


def toStr(n):
    x = []
    for i in sieve((n // 2) + 1):
        if isPrime(n - i):
            x += [str(i) + '+' + str(n - i)]
    print(n, 'has', len(x), 'representation(s)')
    print(*x, sep='\n')
    print()


def main():
    lst = []
    for _ in range(int(input())):
        lst += [int(input())]
    for i in lst:
        toStr(i)


if __name__ == "__main__":
    main()
