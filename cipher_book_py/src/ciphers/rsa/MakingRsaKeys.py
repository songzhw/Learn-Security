import random

from ciphers.math.rabinMiller import generateLargePrime
from ciphers.math.cmath import mi
from ciphers.math.cmath import gcd


def generateRsaKeys(keySize):
    p = generateLargePrime(keySize)
    q = generateLargePrime(keySize)
    n = p * q
    phi = (p - 1) * (q - 1)  # Φ : phi

    # e的选取, 得保证e与phi互质, 所以可能要多次挑选直到符合条件
    while True:
        e = random.randrange(2 ** (keySize - 1), 2 ** (keySize + 12))
        if (gcd(e, phi) == 1):
            break

    # 走到这, 就是已经拿到了e了. 现在来拿d
    d = mi(e, phi)

    publicKey = (n, e)
    privateKey = (n, d)

    return (publicKey, privateKey)


if __name__ == '__main__':
    pub, pri = generateRsaKeys(1024)
    print("public  = ", pub)
    print("private = ", pri)
