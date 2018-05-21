import sys

DEFAULT_BLOCK_SIZE = 128
BYTE_SIZE = 256 # 即2的8次方

def readKeyFile(fileName):
    fout = open(fileName)
    content = fout.read()
    fout.close()
    keySize, n, eOrD = content.split(",")
    return (int(keySize), int(n), int(eOrD))  # string转为int

def getBlocksFromPlainText(msg, blockSize = DEFAULT_BLOCK_SIZE):
    '''
    Converts a string to a List<Block Integer>. Each integer represents "blockSize" string characters
    :param msg: plain text
    '''

    msgBytes = msg.encode('ascii')  # convert string to bytes. 如"hello"转成104, 101, 108, 108, 111
    msgBytesLen = len(msgBytes)

    blocks = []
    for blockStart in range(0, msgBytesLen, blockSize): # range(start, stop[, step])
        block = 0
        end = min(blockStart + blockSize, msgBytesLen)
        for i in range(blockStart, end):
            right = BYTE_SIZE ** (i % blockSize)
            block += msgBytes[i] * right
        blocks.append(block)
    return blocks

def encryptBlocks(msg, key, blockSize=DEFAULT_BLOCK_SIZE):
    blocks = []
    n, e = key      # key是元组
    for block in getBlocksFromPlainText(msg, blockSize):
        # cipherText = plainText ^ e mod n
        blocks.append( pow(block, e, n) )   # pow(x,y);  pow(x, y, z) = pow(x, y) % z
    return blocks


def encrypt(keyFileName, plainText, resultFileName, blockSize = DEFAULT_BLOCK_SIZE) :
    keySize, n, e = readKeyFile(keyFileName)

    # RSA要求blockSize >= keySize
    if keySize < blockSize * 8:
        sys.exit("error: block size need to >= key size")

    encryptedBlocks = encryptBlocks(plainText, (n, e), blockSize)
    return ""


if __name__ == '__main__':
    plainText = "WorkManager is designed for a task that is guarateened to execute"
    encrypt("rsa01_public.txt", plainText, "rsa01_encrypted.txt")