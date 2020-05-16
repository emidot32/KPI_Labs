import numpy as np
import matplotlib.pyplot as plt
import random as rnd
from PyQt5 import QtWidgets
import uifile
import sys


class MyWindow(QtWidgets.QMainWindow):
    def __init__(self, parent=None):
        super().__init__()
        self.ui = uifile.Ui_MainWindow()
        self.ui.setupUi(self)
        self.setToolTip("Якщо ви введете некоректні дані, параметри будуть задані за замовчуванням.")
        self.ui.pushButton_3.clicked.connect(self.make_piecharts)
        self.ui.pushButton_4.clicked.connect(self.make_tables)
        self.ui.pushButton_5.clicked.connect(self.make_graphics)
        self.ui.pushButton_6.clicked.connect(self.make_histograms)

    def check_right_input(self, widget, true_value):
        try:
            var = int(widget)
        except ValueError:
            var = true_value

        return var

    def initialize_vars(self):
        global a, b, x1, y2
        a = self.check_right_input(self.ui.lineEdit_6.text(), 1)
        b = self.check_right_input(self.ui.lineEdit_7.text(), 1)
        x1 = self.check_right_input(self.ui.lineEdit_9.text(), rnd.randint(-10, 10))
        y2 = self.check_right_input(self.ui.lineEdit_8.text(), rnd.randint(-10, 10))
        self.y1 = []
        self.z1 = []
        '''Это переменные, которые меняют значения в срезе y = C'''
        self.x2 = []
        self.z2 = []
        for i in np.arange(-10, 10):  # i stands for Y
            self.y1.append(i)
            self.z1.append(round(((x1 - a) ** 2 / a - (i - b) ** 2 / b), 2))
        for j in np.arange(-10, 10):  # j stands for X
            self.x2.append(j)
            self.z2.append(round(((j - a) ** 2 / a - (y2 - b) ** 2 / b), 2))

    def make_graphics(self):
        self.initialize_vars()
        fig = plt.figure()
        ax1 = fig.add_subplot(211)
        ax1.plot(self.y1, self.z1, color="b")
        ax1.set_xlabel("Y")
        ax1.set_ylabel("N")

        ax2 = fig.add_subplot(212)
        ax2.plot(self.x2, self.z2, color="r")
        ax2.set_xlabel("X")
        ax2.set_ylabel("N")
        plt.tight_layout()
        plt.show()

    def make_histograms(self):
        self.initialize_vars()
        fig = plt.figure()
        ax1 = fig.add_subplot(211)
        ax1.bar(self.y1, self.z1, color="b")
        ax1.set_xlabel("Y")
        ax1.set_ylabel("N")

        ax2 = fig.add_subplot(212)
        ax2.bar(self.x2, self.z2, color="r")
        ax2.set_xlabel("X")
        ax2.set_ylabel("N")

        plt.tight_layout()
        plt.show()

    """Делает круговые диаграммы функций z(x) и z(y)"""

    def make_piecharts(self):
        self.initialize_vars()
        fig1 = plt.figure()
        ax1 = fig1.add_subplot(111)
        ax1.pie(list(map(abs, self.z1)), labels=self.y1, autopct="%1.2f%%", startangle=90)
        ax1.legend(self.z1)
        plt.title("Z(Y)")

        fig2 = plt.figure()
        ax2 = fig2.add_subplot(111)
        ax2.pie(list(map(abs, self.z2)), labels=self.x2, autopct="%1.2f%%", startangle=90)
        ax2.legend(self.z2)
        plt.title("Z(X)")

        plt.show()

    """Делает таблицы занчений срезов x=C и y=C"""

    def make_tables(self):
        self.initialize_vars()
        fig, (ax1, ax2) = plt.subplots(ncols=2, nrows=1)
        ax1.axis('off')
        x1_ = [x1 for _ in range(len(self.y1))]
        data1 = list(zip(x1_, self.y1, self.z1))
        headers1 = ["X", "Y", "Z"]
        table1 = ax1.table(cellText=data1, colLabels=headers1, loc='center')
        ax1.set_title("Зріз x = %d" % x1)

        ax2.axis('off')
        y2_ = [y2 for _ in range(len(self.x2))]
        data2 = list(zip(y2_, self.x2, self.z2))
        headers2 = ["Y", "X", "Z"]
        table2 = ax2.table(cellText=data2, colLabels=headers2, loc='center')
        ax2.set_title("Зріз y = %d" % y2)

        plt.tight_layout()
        plt.show()


if __name__ == "__main__":
    app = QtWidgets.QApplication(sys.argv)
    window = MyWindow()
    window.show()
    sys.exit(app.exec_())

