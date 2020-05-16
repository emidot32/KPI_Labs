import matplotlib.pyplot as plt
import numpy as np
from PyQt5 import QtWidgets
import uifile  # В этом модуле находится класс в котором отображается класс со всеми виджетами
import sys  # Нужен для выхода из приложения


class MyWindow(QtWidgets.QMainWindow):  # Дефолтное наследование от класса QtWidgets, который рисует окна (и нетолько)
    def __init__(self, parent=None):
        super().__init__()
        self.ui = uifile.Ui_MainWindow()
        self.ui.setupUi(self)  # Создание объекта класса Ui_MainWindow и вызов функции setupUi для отображения всего добра
        self.setToolTip("Якщо ви введете некоректні дані, параметри будуть задані за замовчуванням.")
        self.ui.pushButton.setToolTip("P.S. Рекомендовані параметри: \nА = 2\nB = 3\nD = 1\nN = 10.\nПри інших значеннях ви не отримаєте бажаної фігури.")
        self.ui.pushButton_2.setToolTip("P.S. Рекомендовані параметри: 0; 9. \nНе задавайте занадто великі значення та менше 0.")
        self.ui.pushButton_3.setToolTip("P.S. Рекомендовані параметри: 3; 3. \nНе задавайте занадто великі значення та менше 0.")
        self.ui.pushButton.clicked.connect(self.show_picture)  # Думаю шаришь, что это значит
        self.ui.pushButton_2.clicked.connect(self.show_ornament)
        self.ui.pushButton_3.clicked.connect(self.show_effect)

    def check_right_input(self, widget, true_value):
        try:
            var = int(widget)
        except ValueError:
            var = true_value

        return var

    def initialize_vars(self):
        global A, B, D, N
        A = self.check_right_input(self.ui.lineEdit.text(), 2)
        B = self.check_right_input(self.ui.lineEdit_2.text(), 3)
        D = self.check_right_input(self.ui.lineEdit_3.text(), 1)
        N = self.check_right_input(self.ui.lineEdit_4.text(), 10)

    def show_picture(self):
        self.initialize_vars()
        fig5 = plt.figure(figsize=(8, 7))
        plt.title("Шукана фігура")
        step = 0.1
        ax = plt.subplot()
        ax.set_xbound(-10, 11)
        ax.set_ybound(-10, 11)
        ax.axis("on")

        x = (A - B) * np.cos(np.arange(0, N * (np.pi) + step, step)) + D * np.cos(
            A / B * np.arange(0, N * (np.pi) + step, step))
        y = (A - B) * np.sin(np.arange(0, N * (np.pi) + step, step)) - D * np.sin(
            A / B * np.arange(0, N * (np.pi) + step, step))
        ax.plot(x, y, "r")

        plt.show()

    def show_ornament(self):  # Функция, создающая орнамент, вращая осн. рисунок по кругу с радиусом radius
        self.initialize_vars()
        period = self.check_right_input(self.ui.lineEdit_6.text(), 9)
        radius = self.check_right_input(self.ui.lineEdit_5.text(), 0)
        fig5 = plt.figure(figsize=(8, 7))  # и периодом period, при этом изменяя его форму.
        step = 0.1
        ax = fig5.add_subplot(1, 1, 1, label="test")
        ax.axis('off')
        for phi in range(0, 360, period):
            x = ((A - B) * np.cos(np.arange(0, N * np.pi + step, step)) + D * np.cos(
                A / B * np.arange(0, N * np.pi + step, step))) * np.sin(np.deg2rad(phi))+ radius * np.cos(np.deg2rad(phi))
            y = ((A - B) * np.sin(np.arange(0, N * np.pi + step, step)) - D * np.sin(
                A / B * np.arange(0, N * np.pi + step, step))) * np.sin(np.deg2rad(phi))+ radius * np.sin(np.deg2rad(phi))
            ax.plot(x, y, "r")
        plt.show()

    def show_effect(self):  # Функция, создающая муар, вращая осню рисунок по кругу с радиусом radius
        self.initialize_vars()
        period = self.check_right_input(self.ui.lineEdit_8.text(), 3)
        radius = self.check_right_input(self.ui.lineEdit_7.text(), 3)
        fig5 = plt.figure(figsize=(8, 7))  # и периодом period.
        step = 0.1
        ax = fig5.add_subplot(1, 1, 1)
        ax.axis('off')
        for i in range(0, 360, period):
            x = ((A - B) * np.cos(np.arange(0, N * np.pi + step, step)) + D * np.cos(
                A / B * np.arange(0, N * np.pi + step, step))) + radius * np.cos(np.deg2rad(i))
            y = ((A - B) * np.sin(np.arange(0, N * np.pi + step, step)) - D * np.sin(
                A / B * np.arange(0, N * np.pi + step, step))) + radius * np.sin(np.deg2rad(i))
            ax.plot(x, y, "r")
        plt.show()


if __name__ == "__main__":
    app = QtWidgets.QApplication(sys.argv)  # Запуск всего приложения
    window = MyWindow()
    window.show()  # И только сейчас создание и отображение окна
    sys.exit(app.exec_())  # Выход из приложения
