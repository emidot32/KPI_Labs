import matplotlib.pyplot as plt  # Понятно зачем это нужно
import matplotlib.patches as ptc  # Нужен для использования обьекта класса Rectangle
import numpy as np  # Тут тоже всё ясно
from PyQt5 import QtWidgets
import uifile  # В этом модуле находится класс в котором отображается класс со всеми виджетами
import sys  # Нужен для выхода из приложения
import webbrowser  # Нужен для открытия моих картинок


class MyWindow(QtWidgets.QMainWindow):  # Дефолтное наследование от класса QtWidgets, который рисует окна (и нетолько)
    def __init__(self, parent=None):
        super().__init__()
        self.ui = uifile.Ui_MainWindow()
        self.ui.setupUi(self)  # Создание объекта класса Ui_MainWindow и вызов функции setupUi для отображения всего добра
        # Устанавливаю всплывающие подсказки для окна и кнопок
        self.setToolTip("Якщо ви введете некоректні дані, параметри будуть задані за замовчуванням.")
        self.ui.pushButton.setToolTip("P.S. Рекомендований параметр: 6. \nНе використовуйте параметр більше 10.")
        self.ui.pushButton_2.setToolTip("P.S. Рекомендовані параметри: 20; 2. \nНе вписуйте більше 3 квадратів.\nЦе займає багато часу.")
        self.ui.pushButton_3.setToolTip("P.S. Рекомендовані параметри: 15; 47. \nНе вказуйте великі числа, бо створений об'єкт буде занадто великим.")
        self.ui.pushButton.clicked.connect(self.show_square)
        self.ui.pushButton_2.clicked.connect(self.initialize_vars_for_ornam)
        self.ui.pushButton_3.clicked.connect(self.initialize_vars_for_effect)
        self.ui.pushButton_4.clicked.connect(lambda: self.show_unchangeable_pictures(r"file:///media/disk_d/КомпьютернаяГрафика/Орнамент.pdf"))
        self.ui.pushButton_5.clicked.connect(lambda: self.show_unchangeable_pictures(r"file:///media/disk_d/КомпьютернаяГрафика/Муар.pdf"))

    def initialize_vars_for_ornam(self):
        param1_for_ornam = self.check_true_input(self.ui.lineEdit_2.text(), 20)
        param2_for_ornam = self.check_true_input(self.ui.lineEdit_3.text(), 2)
        self.show_ornament(param1_for_ornam, param2_for_ornam)

    def initialize_vars_for_effect(self):
        param1_for_effect = self.check_true_input(self.ui.lineEdit_4.text(), 15)
        param2_for_effect = self.check_true_input(self.ui.lineEdit_5.text(), 47)
        self.show_effect(param1_for_effect, param2_for_effect)

    def show_square(self):
        fig = plt.figure(figsize=(7, 7))  # Этот кусок кода нужен для создания чистого квадрата.
        ax = fig.add_subplot(1, 1, 1)  # <--- Пресоздал по другому, тип теперь явно указано, что ax относится к fig
        size = self.check_true_input(self.ui.lineEdit.text(), 6)
        ax.set_xbound(1.5, 8.5)
        ax.set_ybound(1.5, 8.5)
        ax.axis('off')
        square = ptc.Rectangle((2, 2), size, size, angle=0, ec="r", fill=False)
        ax.add_patch(square)
        plt.show()

    def show_ornament(self, angle, times):  # <---------------- Новая функция для кнопки создания оранмента
        size = self.check_true_input(self.ui.lineEdit.text(), 6)
        square = ptc.Rectangle((2, 2), size, size, angle=0, ec="r", fill=False)
        fig2 = plt.figure(figsize=(7, 7))
        ax = fig2.add_subplot(1, 1, 1)  # <---------------------- Здесь тоже явно теперь явно указано
        ax.set_xbound(1.5, 8.5)
        ax.set_ybound(1.5, 8.5)
        ax.axis('off')
        ax.add_patch(square)
        self.make_ornament(square, angle, times, axes=ax)  # <--- Теперь рекурсия находится здесь
        plt.show()  # <--- Еще одна новая переменная, чтоб все рисовалось

    def make_ornament(self, element, angle, times, axes):  # Функция для создания орнамента/муара внутри 1-го обьекта класса Rectangle.
        if times == 0:  # С этого момента: данный квадрат - квадрат, в который вписываются другие; новый квадрат - тот, что вписывается в данный.
            return  # Координаты (х, у) квадрата - это координаты его левой нижней точки при угле поворота 0 градусов.
        hei = wid = np.sqrt(element.get_width() ** 2 / 8)  # Вычисляем высоту/ширину нового квадрата, вложеного в данный так, чтобы его диагональ/2 не превышала радиус вписаного в данный квадрат круга.
        dist = element.get_height() / np.sqrt(2)  # Радиус вписаного круга соотвественно.
        x = element.get_x() + dist * np.cos(np.deg2rad(
            45 + element.angle))  # Вычисляем координату х нового квадрата (сделано с учетом угла поворота ДАНОГО квадрата).
        y = element.get_y() + dist * np.sin(
            np.deg2rad(45 + element.angle))  # Вычисляем координату у нового квадрата (аналогично).
        for ang in range(0, 360, angle):
            current = ptc.Rectangle((x, y), hei, wid, angle=ang, ec="r", fill=False)  # Создаем обьект класса Rectangle
            axes.add_patch(current)  # Рисуем его
            self.make_ornament(current, angle, times - 1, axes)  # Рекурсивно переходим в эту же функцию, но для только что созданого квадрата, количество вложений уменьшаем на 1.

    def show_effect(self, elem_num, ornament_num):  # Функция для создания орнамента/муара при помощи создания нескольких обьектов класса Rectangle и последующего вызова функции make_ornament() для каждого из них.
        size_square = self.check_true_input(self.ui.lineEdit.text(), 6)
        fig3 = plt.figure(figsize=(7, 7))  # Это часть, где тестируется make_effect() с разными параметрами.
        ax = fig3.add_subplot(1, 1, 1)  # <-- Опять более явное указание
        ax.set_xbound(-7, 12)
        ax.set_ybound(-7, 12)
        ax.axis('off')
        ang = int(np.ceil(360 / elem_num))  # Угол поворота основных обьектов, распределённых равномерно на отрезке [0, 2PI].
        for num in range(elem_num):
            for i in range(ornament_num):
                elem = ptc.Rectangle((2, 2), size_square, size_square, angle=ang*i/np.pi+1, ec="r", fill=False)  # Аналогично предыдущей функции создаем обьект класса Rectangle.
                ax.add_patch(elem)  # Рисуем его.
            size_square -= 0.6
        plt.show()

    def check_true_input(self, widget, true_value):
        try:
            var = int(widget)
        except ValueError:
            var = true_value

        return var

    def show_unchangeable_pictures(self, path):
        webbrowser.open_new(path)


if __name__ == "__main__":
    app = QtWidgets.QApplication(sys.argv)  # Запуск всего приложения
    window = MyWindow()
    window.show()  # И только сейчас создание и отображение окна
    sys.exit(app.exec_())  # Выход из приложения
