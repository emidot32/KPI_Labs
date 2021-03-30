from file_manager import *


def encrypt(text, number):
    key = (len(text) - 1) // number + 1
    res_str = [" ", ] * number * key
    for i in range(len(text)):
        index = abs(i // key) + abs(number * (i % key))
        res_str[index] = text[i]
    return "".join(res_str)


def decrypt(encrypted_text, number):
    key = (len(encrypted_text) - 1) // number + 1
    return encrypt(encrypted_text, key)


def break_cipher(msg):
    msg_size = len(msg)
    count_ops = 0
    results = []
    for m in range(1, msg_size):
        num = (msg_size - 1) // m + 1
        res = [" ", ] * m * num
        count_ops += 2
        for j in range(msg_size):
            index = abs(m * (j % num)) + abs(j // num)
            count_ops += 2
            res[index] = msg[j]
        results.append("".join(res))
    return results


TEXT = read_txt_file('text.txt')
print(f"Не заш. текст: {TEXT}")
print(f"Зашифр. текст: {write_to_txt_file(encrypt(TEXT, 10), 'encrypted_text.txt')}")
ENCRYPTED_TEXT = read_txt_file('encrypted_text.txt')
print(f"Розшиф. текст: {decrypt(ENCRYPTED_TEXT, 10)}")
