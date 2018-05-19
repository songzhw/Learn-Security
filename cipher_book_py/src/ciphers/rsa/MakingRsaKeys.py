import random, os, sys

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


def saveKeysAsFiles(fileNamePrefix, keySize):
    pubFile = "%s_public.txt" % (fileNamePrefix)
    priFile = "%s_private.txt" % (fileNamePrefix)
    if os.path.exists(pubFile) or os.path.exists(priFile):
        sys.exit("The secret key files already exists! Please be careful!!")

    pub, pri = generateRsaKeys(keySize)

    fout = open(pubFile, 'w')
    fout.write("%s,%s,%s" % (keySize, pub[0], pub[1]))
    fout.close()

    fout = open(priFile, 'w')
    fout.write("%s,%s,%s" % (keySize, pri[0], pri[1]))
    fout.close()



if __name__ == '__main__':
    saveKeysAsFiles("rsa01", 1025)
