import math


def isPrime(num):
    if num < 2:
        return False  # 0, 1不是质数

    # num为100时, 它是不可能有因子是大于50的. 比如说60 * ? = 100, 这是不可能的, 所以这里只要比较sqrt(), 平方根
    boundary = int(math.sqrt(num)) + 1
    for i in range(2, boundary):
        if num % i == 0:
            return False

    return True

def primeSieve(size):
    sieve = [True] * size # 某格一为乘积, 就置为False
    sieve[0] = False
    sieve[1] = True

    # num为100时, 它是不可能有因子是大于50的. 比如说60 * ? = 100, 这是不可能的, 所以这里只要比较sqrt(), 平方根
    boundary = int(math.sqrt(size)) + 1
    for i in range(2, boundary):
        pointer = i * 2  # startPosition. 以3为例, 3其实是质数, 但它的位数6,9, 12, ...都不是质数
        while pointer < size:
            sieve[pointer] = False
            pointer += i


    ret = [] # contains all the prime number within "size"
    for i in range(size):
        if sieve[i] == True:
            ret.append(str(i))
    return ret


if __name__ == '__main__':
    primes = primeSieve(100)
    primesString = ", ".join(primes)
    print("prime : ", primesString)

'''
prime :  1, 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97
'''