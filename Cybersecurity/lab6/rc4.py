import os
import time
from file_manager import *
from itertools import product, count
from math import ceil


def generate_key(text):
    return os.urandom(len(text)//2)


def crypt_logic(text, key=None):
    start_time = time.perf_counter_ns()
    return_key = False
    if key is None:
        key = generate_key(text)
        text = text.encode('utf-8', errors="replace")
        return_key = True

    res = bytearray()
    for i in range(len(text)):
        res.append(text[i] ^ key[i % len(key)])

    if return_key:
        print("Час шифрування: ", (time.perf_counter_ns() - start_time) / 1000)
        return res, key
    print("Час дешифрування: ", (time.perf_counter_ns() - start_time) / 1000)
    return res


def break_the_cipher(encrypted_text, needed_result, key_length=None):
    start_time = time.perf_counter_ns()
    if key_length is not None:
        possible_keys = product('01', repeat=key_length * 8)
        for entry in possible_keys:
            poss_key = int("".join(entry), 2).to_bytes(key_length, byteorder='big')
            result = crypt_logic(encrypted_text, poss_key)
            if result == needed_result:
                break
    else:
        for num in count(1):
            possible_keys = product('01', repeat=num)
            for entry in possible_keys:
                poss_key = int("".join(entry), 2).to_bytes(ceil(num / 8), byteorder='big')
                result = crypt_logic(encrypted_text, poss_key)
                if result == needed_result:
                    print("Час взлому: ", (time.perf_counter() - start_time))
                    return poss_key
    print("Час неуспішного взлому: ", (time.perf_counter() - start_time))
    return None


TEXT_UKR = read_txt_file('text_ukr.txt')
print(f"Не заш. текст укр: {TEXT_UKR}")
encrypted_text, key = crypt_logic(TEXT_UKR)
print(f"Зашифр. текст укр: {write_to_txt_file(str(encrypted_text), 'encoded_text_ukr.txt')}")
print(f"Розшиф. текст укр: {crypt_logic(encrypted_text, key).decode('utf-8')}")

TEXT_ENG = read_docx_file('text_eng.docx')
print(f"Не заш. текст англ: {TEXT_ENG}")
encrypted_text, key = crypt_logic(TEXT_ENG)
print(f"Зашифр. текст англ: {write_to_docx_file(str(encrypted_text), 'encoded_text_eng.docx')}")
print(f"Розшиф. текст англ: {crypt_logic(encrypted_text, key).decode('utf-8')}")
