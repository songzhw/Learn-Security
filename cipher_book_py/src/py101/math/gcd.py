def gcd(a, b):
    while a != 0:
        a, b = b % a, a
    return b


if __name__ == '__main__':
    gcdNum = gcd(21, 2)
    print("gcd = ", gcdNum)
