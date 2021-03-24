from lab7.constants import *
from lab7.mail_client import *


def dec2bin(num):
    res = bin(num).replace("0b", "")
    if len(res) % 4 != 0:
        div = len(res) / 4
        div = int(div)
        counter = (4 * (div + 1)) - len(res)
        for i in range(0, counter):
            res = '0' + res
    return res


def str2bin(text):
    return ''.join(['{:0>16}'.format(str(bin(ord(sym)))[2:]) for sym in text])


def bin2str(bits):
    return ''.join([chr(int(bits[i - 16:i], 2)) for i in range(16, len(bits) + 16, 16)])


def permute(bits, const_table, n):
    permutation = ""
    for i in range(0, n):
        permutation = permutation + bits[const_table[i] - 1]
    return permutation


def shift_left(k, nth_shifts):
    s = ""
    for i in range(nth_shifts):
        for j in range(1, len(k)):
            s = s + k[j]
        s = s + k[0]
        k = s
        s = ""
    return k


def xor(bits1, bits2):
    return ''.join([str(int(bits1[i]) ^ int(bits2[i])) for i in range(len(bits1))])


def check_key_weakness(left, right):
    right_check = right.count("0") == len(right) or \
                  right.count("1") == len(right)
    return left.count("0") == len(left) and right_check or \
           left.count("1") == len(left) and right_check


def generate_keys(bin_key):
    bin_key = permute(bin_key, key_perm, 56)
    left = bin_key[0:28]
    right = bin_key[28:56]
    if check_key_weakness(left, right):
        raise Exception("Слабкий ключ!")
    round_keys = []
    for i in range(0, 16):
        left = shift_left(left, shift_table[i])
        right = shift_left(right, shift_table[i])
        combined_key = left + right
        round_key = permute(combined_key, key_perm_compr, 48)
        round_keys.append(round_key)
    return round_keys


def encrypt_one_block(text, round_keys):
    text = permute(text, init_perm, 64)
    left = text[0:32]
    right = text[32:64]
    for i in range(0, 16):
        extended_right = permute(right, extension_table, 48)
        xor_x = xor(extended_right, round_keys[i])
        permuted_sbox_str = ""
        for j in range(0, 8):
            row = int(xor_x[j * 6] + xor_x[j * 6 + 5], 2)
            col = int(xor_x[j * 6 + 1] + xor_x[j * 6 + 2] + xor_x[j * 6 + 3] + xor_x[j * 6 + 4], 2)
            val = s_box[j][row][col]
            permuted_sbox_str = permuted_sbox_str + dec2bin(val)

        permuted_sbox_str = permute(permuted_sbox_str, direct_perm, 32)
        result = xor(left, permuted_sbox_str)
        left = result
        if i != 15:
            left, right = right, left
    combine = left + right
    cipher_text = permute(combine, final_perm, 64)
    return cipher_text


def encrypt(text, round_keys):
    text = str2bin(text)
    return ''.join([bin2str(encrypt_one_block(text[i-64:i], round_keys)) for i in range(64, len(text) + 64, 64)])


mail_msg = get_last_msg("plain_text").strip()
bin_key = str2bin("ж)tЪ")
#bin_key = "0000100101011100010100100110010101100100110010011100101110001100"

r_keys = generate_keys(bin_key)
encoded_text = encrypt(mail_msg, r_keys)
reversed_round_keys = r_keys[::-1]
decoded_text = encrypt(encoded_text, reversed_round_keys)
send_msg("encoded_text", encoded_text)
print(f"Не заш. текст: {mail_msg}")
print(f"Зашифр. текст: {encoded_text}")
print(f"Розшиф. текст: {decoded_text}")


