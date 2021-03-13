import time

from symbols import *
from file_manager import *


def encode(text: str, key1=(5, 15), key2=(11, 23)):
    start_time = time.perf_counter_ns()
    length = len(ALL_SYMBOLS_ENG)
    if lcd(key1[0], length) != 1 or lcd(key2[0], length) != 1:
        raise Exception("Мультиплікативний ключ та кількість символів не взаємно прості!")
    encoded_text = []
    for sym in text:
        encoded_code1 = (get_code(sym, ALL_SYMBOLS_ENG) * key1[0] + key1[1]) % length
        encoded_letter = get_char((encoded_code1 * key2[0] + key2[1]) % length, ALL_SYMBOLS_ENG)
        encoded_text.append(encoded_letter)
    print("Час шифрування: ", (time.perf_counter_ns() - start_time) / 1000)
    return ''.join(encoded_text)


def decode(encoded_text: str, key1=(5, 15), key2=(11, 23)):
    start_time = time.perf_counter_ns()
    decoded_text = []
    for sym in encoded_text:
        decoded_code1 = ((get_code(sym, ALL_SYMBOLS_ENG) - key2[1]) * key_inverse(key2[0])) % len(ALL_SYMBOLS_ENG)
        decoded_letter = get_char(((decoded_code1 - key1[1]) * key_inverse(key1[0])) % len(ALL_SYMBOLS_ENG),
                                  ALL_SYMBOLS_ENG)
        decoded_text.append(decoded_letter)
    print("Час розшифрування: ", (time.perf_counter_ns() - start_time) / 1000)
    return ''.join(decoded_text)


def key_inverse(key: int):
    l = len(ALL_SYMBOLS_ENG)
    if lcd(key, l) != 1:
        raise Exception("Мультиплікативний ключ та кількість символів не взаємно прості!")
    u1, u2, u3 = 1, 0, key
    v1, v2, v3 = 0, 1, l
    while v3 != 0:
        q = u3//v3
        v1, v2, v3, u1, u2, u3 = (u1 - q * v1), (u2 - q * v2), (u3 - q * v3), v1, v2, v3
    return u1 % l


def lcd(a: int, b: int):
    while a != 0:
        a, b = b % a, a
    return b


def break_cipher(encoded_text: str, needed_result: str):
    start_time = time.perf_counter()
    length = len(ALL_SYMBOLS_ENG)
    for k11 in range(1, length+1):
        if lcd(k11, length) != 1:
            continue
        for k12 in range(1, length+1):
            key1 = (k11, k12)
            for k21 in range(1, length + 1):
                if lcd(k21, length) != 1:
                    continue
                for k22 in range(1, length + 1):
                    key2 = (k21, k22)
                    decoded_text = []
                    for sym in encoded_text:
                        decoded_code1 = ((get_code(sym, ALL_SYMBOLS_ENG) - key2[1]) * key_inverse(key2[0])) % len(ALL_SYMBOLS_ENG)
                        decoded_letter = get_char(((decoded_code1 - key1[1]) * key_inverse(key1[0])) % len(ALL_SYMBOLS_ENG),
                                                  ALL_SYMBOLS_ENG)
                        decoded_text.append(decoded_letter)
                    if ''.join(decoded_text) == needed_result:
                        print("Час взлому: ", (time.perf_counter() - start_time))
                        return key1, key2
    return None


KEY1 = (7, 15)
KEY2 = (11, 23)
TEXT = read_txt_file('text.txt')
print(f"Не заш. текст: {TEXT}")
print(f"Зашифр. текст: {write_to_txt_file(encode(TEXT, KEY1, KEY2), 'encoded_text.txt')}")
ENCODED_TEXT = read_txt_file('encoded_text.txt')
print(f"Розшиф. текст: {decode(ENCODED_TEXT, KEY1, KEY2)}")
#print(break_cipher(ENCODED_TEXT, TEXT))
