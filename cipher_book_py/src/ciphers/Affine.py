from ciphers.math.cmath import gcd
from ciphers.math.cmath import mi

if __name__ == '__main__':
    gcdNum = gcd(1071, 462)
    print("gcd2 = ", gcdNum)

    print("ModInverse2 = ", mi(7, 26))