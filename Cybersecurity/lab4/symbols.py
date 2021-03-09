import random
UKRAINIAN_ALPHABET_LETTERS = ['ь', 'і', 'ю', 'я', 'є', 'ї']
SYMBOLS = [' ', '!', '%', '(', ')', '-', '+', '=', '*', ':', "'", '"', '`', '/', '?', '.', ',']


def all_symbols():
    common_letters = [chr(i) for i in range(ord('а'), ord('ъ'))]
    digits = [str(i) for i in range(10)]
    letters = common_letters + UKRAINIAN_ALPHABET_LETTERS
    upper_letters = [letter.upper() for letter in letters]
    symbols = letters + upper_letters + digits + SYMBOLS
    random.shuffle(symbols)
    return symbols


ALL_SYMBOLS = all_symbols()


def get_char(code: int):
    return ALL_SYMBOLS[code]


def get_code(char: str):
    return ''.join(ALL_SYMBOLS).find(char)
