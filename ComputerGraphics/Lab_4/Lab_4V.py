import numpy as np
import matplotlib.pyplot as plt
import random as rnd

'''Это переменные которые можно изменять'''
a = 1
b = 1
x1 = y2 = rnd.randint(-10, 10)

"""Это переменные, которые меняют значения в срезе x = C"""
y1 = []
z1 = []
'''Это переменные, которые меняют значения в срезе y = C'''
x2 = []
z2 = []
for i in np.arange(-10, 10):  # i stands for Y
    y1.append(i)
    z1.append((x1 - a) ** 2 / a - (i - b) ** 2 / b)
for j in np.arange(-10, 10):  # j stands for X
    x2.append(j)
    z2.append((j - a) ** 2 / a - (y2 - b) ** 2 / b)

"""Делает графики функций z(x) и z(y)"""


def make_graphics():
    fig = plt.figure()
    ax1 = fig.add_subplot(211)
    ax1.plot(y1, z1, color="b")
    ax1.set_xlabel("Y")
    ax1.set_ylabel("N")

    ax2 = fig.add_subplot(212)
    ax2.plot(x2, z2, color="r")
    ax2.set_xlabel("X")
    ax2.set_ylabel("N")
    plt.tight_layout()
    plt.show()


"""Делает гистограмы функций z(x) и z(y)"""


def make_histograms():
    fig = plt.figure()
    ax1 = fig.add_subplot(211)
    ax1.bar(y1, z1, color="b")
    ax1.set_xlabel("Y")
    ax1.set_ylabel("N")

    ax2 = fig.add_subplot(212)
    ax2.bar(x2, z2, color="r")
    ax2.set_xlabel("X")
    ax2.set_ylabel("N")

    plt.tight_layout()
    plt.show()


"""Делает круговые диаграммы функций z(x) и z(y)"""


def make_piecharts():
    fig1 = plt.figure()
    ax1 = fig1.add_subplot(111)
    ax1.pie(list(map(abs, z1)), labels=y1, autopct="%1.2f%%", startangle=90)
    ax1.legend(z1)
    plt.title("Z(Y)")

    fig2 = plt.figure()
    ax2 = fig2.add_subplot(111)
    ax2.pie(list(map(abs, z2)), labels=x2, autopct="%1.2f%%", startangle=90)
    ax2.legend(z2)
    plt.title("Z(X)")

    plt.show()


"""Делает таблицы занчений срезов x=C и y=C"""


def make_tables():
    fig, (ax1, ax2) = plt.subplots(ncols=2, nrows=1)
    ax1.axis('off')
    x1_ = [x1 for _ in range(len(y1))]
    data1 = list(zip(x1, y1, z1))
    headers1 = ["X", "Y", "Z"]
    table1 = ax1.table(cellText=data1, colLabels=headers1, loc='center')
    ax1.set_title("Зріз x = %d" % x1)

    ax2.axis('off')
    y2_ = [y2 for _ in range(len(x2))]
    data2 = list(zip(y2, x2, z2))
    headers2 = ["Y", "X", "Z"]
    table2 = ax2.table(cellText=data2, colLabels=headers2, loc='center')
    ax2.set_title("Зріз y = %d" % y2)

    plt.tight_layout()
    plt.show()


make_graphics()
make_histograms()
make_piecharts()
make_tables()