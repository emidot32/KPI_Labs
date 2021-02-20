import numpy as np


def get_data_from_file(file_name):
    with open(file_name, "r") as files:
        data = files.read()
    return np.fromstring(data.replace("\n", " "), dtype=int, sep=",")


gamma = float(input("Параметр гамма: "))
n = int(input("Кількість інтервалів: "))
T1 = int(input("Час на визначення ймовірності: "))
T2 = int(input("Час на визначення інтенсивності: "))
# gamma = 0.98
# n = 10
# T1 = 414
# T2 = 2077
data = get_data_from_file("lab1/data_lab1.txt")

part = data.max() / n
density = np.array([len(data[(data > part * i) * (data < part * (i + 1))]) / (len(data) * part) for i in range(n)])


def calculate_t_gamma():
    probabilities = np.array([1 - density[:i].sum() * part for i in range(n + 1)])
    ti = (probabilities < gamma).argmax()
    d = (probabilities[ti - 1] - gamma) / (probabilities[ti - 1] - probabilities[ti])
    return ti - 1 + part * d


def calc_rel_work_prob(time):
    index = (np.array([part * i for i in range(n + 1)]) > time).argmax() - 1
    return 1 - density[:index].sum() * part - (time - part * index) * density[index], index


def calc_intensity():
    intensity, index = calc_rel_work_prob(T2)
    return density[index] / intensity


print(f"Середній наробіток до відмови Tср: {data.mean()}")
print(f"γ-відсотковий наробіток на відмову Tγ при γ = {gamma}: {calculate_t_gamma()}")
print(f"Ймовірність безвідмовної роботи на час {T1}: {calc_rel_work_prob(T1)[0]}")
print(f"Інтенсивність відмов на час {T2}: {calc_intensity()}")

