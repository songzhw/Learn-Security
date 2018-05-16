import random

# not a public method. You should use *isPrime(num)* instead
def rabinMiller(num):
    # Returns True if num is a prime number.
    tillEven = num - 1
    time = 0
    while tillEven % 2 == 0:
        # keep halving "tillEven" until it is even (and use "time" to count how many times we halve s)
        tillEven = tillEven // 2
        time += 1

    for trials in range(5): # try to falsify num's primality 5 times
        randomValue = random.randrange(2, num - 1)
        value = pow(randomValue, tillEven, num)
        if value != 1: # this test does not apply if v is 1.
            i = 0
            while value != (num - 1):
                if i == time - 1:
                    return False
                else:
                    i = i + 1
                    value = (value ** 2) % num
    return True


def isPrime(num):
    # Return True if num is a prime number. This function does a quicker
    # prime number check before calling rabinMiller().

    if (num < 2):
        return False # 0, 1, and negative numbers are not prime

    # About 1/3 of the time we can quickly determine if num is not prime
    # by dividing by the first few dozen prime numbers. This is quicker
    # than rabinMiller(), but unlike rabinMiller() is not guaranteed to
    # prove that a number is prime.
    lowPrimes = [2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199, 211, 223, 227, 229, 233, 239, 241, 251, 257, 263, 269, 271, 277, 281, 283, 293, 307, 311, 313, 317, 331, 337, 347, 349, 353, 359, 367, 373, 379, 383, 389, 397, 401, 409, 419, 421, 431, 433, 439, 443, 449, 457, 461, 463, 467, 479, 487, 491, 499, 503, 509, 521, 523, 541, 547, 557, 563, 569, 571, 577, 587, 593, 599, 601, 607, 613, 617, 619, 631, 641, 643, 647, 653, 659, 661, 673, 677, 683, 691, 701, 709, 719, 727, 733, 739, 743, 751, 757, 761, 769, 773, 787, 797, 809, 811, 821, 823, 827, 829, 839, 853, 857, 859, 863, 877, 881, 883, 887, 907, 911, 919, 929, 937, 941, 947, 953, 967, 971, 977, 983, 991, 997]

    if num in lowPrimes:
        return True

    # See if any of the low prime numbers can divide num
    for prime in lowPrimes:
        if (num % prime == 0):
            return False

    # If all else fails, call rabinMiller() to determine if num is a prime.
    return rabinMiller(num)


def generateLargePrime(keysize=1024):
    # Return a random prime number of keysize bits in size.
    while True:
        num = random.randrange(2**(keysize-1), 2**(keysize+12))
        if isPrime(num):
            return num


if __name__ == '__main__':
    print("prime = ", generateLargePrime(2048))  #=> prime =  58862034624800144987863236333235837136641666806102678851153219472456166576721504148392203159749885996443481262011861980804380640473513732020346703515149562382935794150115536089534302342409342031830922150056348953358098668830862675163576333060050644980572570459533810611085676555868662790607718339122951156323408250340696404210282135979746185332990638631655257404087891470569693033232629880827661624742503021888189086235875909753531634574568257876127750043045683817725291381284153782122387756071597113876164031030621469609963318980014069569518930999478757244690073816639812137161745370421874817344867733507087198970300551
    # 约两三秒, 结果就出来了. rabinMiller确实快