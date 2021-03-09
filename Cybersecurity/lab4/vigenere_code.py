import time

from lab4.symbols import *


def encode(text: str, key='за замовчуванням'):
    start_time = time.perf_counter_ns()
    encrypted = []
    for i in range(len(text)):
        encrypted_letter = text[i]
        code = get_code(encrypted_letter)
        if code != -1:
            encrypted_letter = get_char((code + get_code(key[i % len(key)])) % len(ALL_SYMBOLS))
        encrypted.append(encrypted_letter)
    print("Час шифрування: ", (time.perf_counter_ns() - start_time)/1000)
    return ''.join(encrypted)


def decode(encoded_text: str, key='за замовчуванням'):
    start_time = time.perf_counter_ns()
    decrypted = []
    for i in range(len(encoded_text)):
        decrypted_letter = encoded_text[i]
        code = get_code(decrypted_letter)
        if code != -1:
            decrypted_letter = get_char((code - get_code(key[i % len(key)]))+len(ALL_SYMBOLS) % len(ALL_SYMBOLS))
        decrypted.append(decrypted_letter)
    print("Час розшифрування: ", (time.perf_counter_ns() - start_time)/1000)
    return ''.join(decrypted)


def read_file(filename: str):
    file = open(filename, 'r')
    data = file.read().strip()
    file.close()
    return data


def write_to_file(text: str, filename: str):
    file = open(filename, 'w')
    file.write(text)
    file.close()
    return text


def break_cipher(encoded_text: str, needed_result: str, key_length=None):
    key = ''
    start_time = time.perf_counter_ns()
    for i in range(len(encoded_text)):
        if key_length is not None and i >= key_length:
            print((time.perf_counter_ns() - start_time)/1000)
            return ''.join(key)

        for sym in ALL_SYMBOLS:
            code = get_code(encoded_text[i])
            prob_key = key + sym
            decrypted_letter = get_char(
                (code - get_code(prob_key[i % len(prob_key)])) + len(ALL_SYMBOLS) % len(ALL_SYMBOLS))
            if decrypted_letter == needed_result[i]:
                key = prob_key
                break

    print((time.perf_counter_ns() - start_time)/1000)
    return ''.join(key)


KEY = "дуже складний ключ"
print(f"Текст: {read_file('text.txt')}")
print(f"Зашифрований текст: {write_to_file(encode(read_file('text.txt'), KEY), 'encoded_text.txt')}")
print(f"Розшифрований текст: {decode(read_file('encoded_text.txt'), KEY)}")
#print(break_cipher(read_file("encoded_text.txt"), read_file("text.txt"), len(KEY)))


