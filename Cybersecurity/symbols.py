import random
UKRAINIAN_ALPHABET_LETTERS = ['ь', 'і', 'ю', 'я', 'є', 'ї']
SYMBOLS = [' ', '!', ':', "'", '"', '`', '/', '?', '.', ',']


def all_symbols(lang: str):
    letters = []
    if lang == 'ukr':
        common_cyrillic_letters = [chr(i) for i in range(ord('а'), ord('ъ'))]
        letters = common_cyrillic_letters + UKRAINIAN_ALPHABET_LETTERS
    elif lang == 'eng':
        letters = [chr(i) for i in range(ord('a'), ord('z')+1)]
    upper_letters = [letter.upper() for letter in letters]
    #digits = [str(i) for i in range(10)]
    symbols = letters + upper_letters + SYMBOLS
    random.shuffle(symbols)
    return symbols


def get_char(code: int, symbols: list):
    return symbols[code]


def get_code(char: str, symbols: list):
    return ''.join(symbols).find(char)


ALL_SYMBOLS_UKR = all_symbols('ukr')
ALL_SYMBOLS_ENG = all_symbols('eng')
