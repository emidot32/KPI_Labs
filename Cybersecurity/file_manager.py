def read_txt_file(filename: str):
    file = open(filename, 'r')
    data = file.read().strip()
    file.close()
    return data


def write_to_txt_file(text: str, filename: str):
    file = open(filename, 'w')
    file.write(text)
    file.close()
    return text
