import matplotlib.pyplot as plt
import numpy as np
from mpl_toolkits.mplot3d import Axes3D
from PyQt5 import QtWidgets
import uifile
import sys
#Комент с винды

class MyWindow(QtWidgets.QMainWindow):
    def __init__(self, parent=None):
        super().__init__()
        self.ui = uifile.Ui_MainWindow()
        self.ui.setupUi(self)
        self.setToolTip("Якщо ви введете некоректні дані, параметри будуть задані за замовчуванням.")
        self.ui.pushButton.setToolTip("P.S. Рекомендовані параметри: \na = -0.4\nb = -0.25.")
        self.ui.pushButton.clicked.connect(self.show_picture)
        self.ui.pushButton_2.clicked.connect(self.show_cut)


    def check_right_input(self, widget, true_value):
        try:
            var = float(widget)
        except ValueError:
            var = true_value

        return var

    def initialize_vars(self):
        global a, b
        a = self.check_right_input(self.ui.lineEdit.text(), -0.4)
        b = self.check_right_input(self.ui.lineEdit_2.text(), -0.25)

    def show_picture(self):
        self.initialize_vars()
        fig = plt.figure()
        ax = fig.gca(projection='3d')
        y = np.arange(-15, 15)
        x = np.arange(-15, 15)
        x, y = np.meshgrid(x, y)
        z = 0.5*(y**2 + y - 2)
        surf = ax.plot_surface(z, y, x, linewidth=0, antialiased=True)
        plt.show()

    def show_cut(self):
        self.initialize_vars()
        fig = plt.figure()
        ax = fig.gca(projection='3d')
        x = np.arange(-20, 20)
        y = np.arange(-15, 15)
        x, y = np.meshgrid(x, y)
        z = (x - a) ** 2 / a - (y - b) ** 2 / b
        surf = ax.plot_surface(x, y, z, linewidth=0, antialiased=True)
        x2 = 0
        y2 = x*np.cos(x)
        x2, y2 = np.meshgrid(x2, y2)
        for i in range(-750, 1000, 25):
            ax.plot3D(xs=x2, ys=y2, zs=i, antialiased=True, color="b")
        plt.show()


if __name__ == "__main__":
    app = QtWidgets.QApplication(sys.argv)
    window = MyWindow()
    window.show()
    sys.exit(app.exec_())