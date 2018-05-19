'''
Math for cipher
'''

def gcd(a, b):
    while a != 0:
        a, b = b % a, a
    return b

# modular inverse
def mi(a, m):
    if gcd(a, m) != 1:
        return None
    x1, x2, x3 = 1, 0, a
    y1, y2, y3 = 0, 1, m
    while y3 != 0:
        q = x3 // y3  # "//"是整数除法. (20, 21, 22, 23, 24)任一 //5都等于4. 19 // 5 == 3
        y1, y2, y3, x1, x2, x3 = (x1 - q * y1), (x2 - q * y2), (x3 - q * y3), y1, y2, y3
    return x1 % m