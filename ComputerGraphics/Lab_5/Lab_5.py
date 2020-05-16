import pygame as pg
from random import randint
from math import *
from PyQt5 import QtWidgets
import uifile
import sys


class MyWindow(QtWidgets.QMainWindow):
    def __init__(self, parent=None):
        super().__init__()
        self.ui = uifile.Ui_MainWindow()
        self.ui.setupUi(self)
        self.setToolTip("Якщо ви введете некоректні дані, параметри будуть задані за замовчуванням.")
        self.ui.pushButton.clicked.connect(self.situation0)
        self.ui.pushButton_2.clicked.connect(self.situation1)
        self.ui.pushButton_3.clicked.connect(self.situation2)
        self.ui.pushButton_4.clicked.connect(self.situation3)

    def check_right_input(self, widget, true_value):
        try:
            var = float(widget)
        except ValueError:
            var = true_value

        return var

    def situation1(self):
        speed1 = self.check_right_input(self.ui.lineEdit.text(), 1.5)
        speed_com = int(self.check_right_input(self.ui.lineEdit_2.text(), 10))
        size_x = 700
        size_y = 600
        pygame_window = pg.display.set_mode((size_x, size_y))
        pg.display.set_caption("Lab_5. Situation 1")
        GREEN = (0, 200, 0)
        RED = (255, 0, 0)
        screen = pg.Surface((size_x, size_y))
        screen.fill((55, 55, 55))

        dot1 = pg.Surface((6, 6))
        dot1.fill(RED)

        dot2 = pg.Surface((6, 6))
        dot2.fill(GREEN)

        dot3 = pg.Surface((8, 8))
        dot3.fill((255, 200, 0))

        picture = pg.image.load("KG.PNG")

        A = 3
        B = 2
        D = 1.5
        speed2 = 1

        phase = -400
        x1 = 348
        y1 = 301
        x2 = 332
        y2 = 190

        j = 0
        clock = pg.time.Clock()
        clock.tick()
        flag = False
        done = True
        while done:
            for i in pg.event.get():
                if i.type == pg.QUIT:
                    done = False

            screen.fill((55, 55, 55))
            screen.blit(picture, (168, 113))

            if (x2 <= x1 <= x2 + dot2.get_width() or x2 <= x1 + dot1.get_width() <= x2 + dot2.get_width()) and \
                    (y2 <= y1 <= y2 + dot2.get_height() or y2 <= y1 + dot1.get_height() <= y2 + dot2.get_height()):
                speed_com += ceil(10 * (1 / abs(speed1 - speed2 + 0.0001)))
                if speed_com > 100:
                    speed_com = 100
                print(clock.tick()/1000)
                flag = True
            elif not flag:
                x1 += ((A - B) * cos(speed1 * radians(j)) + D * cos(
                    A / B * speed1 * radians(j))) * (1.56 * speed1)
                y1 += (((A - B) * sin(speed1 * radians(j))) - D * sin(
                    A / B * speed1 * radians(j))) * (1.56 * speed1)
                x2 -= ((A - B) * cos(speed2 * radians(j + phase)) + D * cos(
                    A / B * speed2 * radians(j + phase))) * (1.56 * speed2)
                y2 += (((A - B) * sin(speed2 * radians(j + phase))) - D * sin(
                    A / B * speed2 * radians(j + phase))) * (1.56 * speed2)
                screen.blit(dot1, (x1, y1))
                screen.blit(dot2, (x2, y2))
            if flag:
                if speed1 - speed2 > 0:
                    x1 += ((A - B) * cos(speed1 * radians(j)) + D * cos(
                        A / B * speed1 * radians(j))) * (1.56 * speed1)
                    y1 += (((A - B) * sin(speed1 * radians(j))) - D * sin(
                        A / B * speed1 * radians(j))) * (1.56 * speed1)
                    x2 = 0
                    y2 = 0
                    screen.blit(dot3, (floor(x1), floor(y1)))
                elif speed1 - speed2 < 0:
                    x2 -= ((A - B) * cos(speed2 * radians(j + phase)) + D * cos(
                        A / B * speed2 * radians(j + phase))) * (1.57 * speed2)
                    y2 += (((A - B) * sin(speed2 * radians(j + phase))) - D * sin(
                        A / B * speed2 * radians(j + phase))) * (1.57 * speed2)
                    x1 = 0
                    y1 = 0
                    screen.blit(dot3, (floor(x2), floor(y2)))
                elif speed1 - speed2 == 0:
                    screen.blit(dot3, (floor(x1), floor(y1)))
            pygame_window.blit(screen, (0, 0))
            pg.display.flip()
            j += 1
            pg.time.delay(speed_com)
        pg.quit()

    def situation0(self):
        speed1 = self.check_right_input(self.ui.lineEdit.text(), 1.5)
        speed_com = int(self.check_right_input(self.ui.lineEdit_2.text(), 10))
        size_x = 700
        size_y = 600
        pygame_window = pg.display.set_mode((size_x, size_y))
        pg.display.set_caption("Lab_5. Situation 0")
        GREEN = (0, 200, 0)
        RED = (255, 0, 0)
        screen = pg.Surface((size_x, size_y))
        screen.fill((55, 55, 55))

        dot1 = pg.Surface((6, 6))
        dot1.fill(RED)

        dot2 = pg.Surface((6, 6))
        dot2.fill(GREEN)

        dot3 = pg.Surface((8, 8))
        dot3.fill((255, 200, 0))

        picture = pg.image.load("KG.PNG")

        A = 3
        B = 2
        D = 1.5
        speed2 = 1

        phase = +50
        x1 = 350
        y1 = 301
        x2 = 198
        y2 = 265

        j = 0
        clock = pg.time.Clock()
        clock2 = pg.time.Clock()
        clock3 = pg.time.Clock()
        clock.tick()
        clock2.tick()
        count = 0
        done = True
        while done:
            for i in pg.event.get():
                if i.type == pg.QUIT:
                    done = False

            screen.fill((55, 55, 55))
            screen.blit(picture, (168, 113))
            x1 += ((A - B) * cos(speed1 * radians(j)) + D * cos(
                A / B * speed1 * radians(j))) * (1.56 * speed1)
            y1 += (((A - B) * sin(speed1 * radians(j))) - D * sin(
                A / B * speed1 * radians(j))) * (1.56 * speed1)
            x2 -= ((A - B) * cos(speed2 * radians(j + phase)) + D * cos(
                A / B * speed2 * radians(j + phase))) * (1.56 * speed2)
            y2 += (((A - B) * sin(speed2 * radians(j + phase))) - D * sin(
                A / B * speed2 * radians(j + phase))) * (1.56 * speed2)
            screen.blit(dot1, (x1, y1))
            screen.blit(dot2, (x2, y2))

            if round(speed1*radians(j), 2)*100 % 1250 < 2 and j != 0:
                print("Червона точка робить повний оберт за %.3f секунд " % (clock.tick()/1000))
            if j % 730 == 0 and j != 0:
                print("Зелена точка робить повний оберт за %.3f секунд " % (clock2.tick() / 1000))
            if (x2 <= x1 <= x2 + dot2.get_width() or x2 <= x1 + dot1.get_width() <= x2 + dot2.get_width()) and \
                    (y2 <= y1 <= y2 + dot2.get_height() or y2 <= y1 + dot1.get_height() <= y2 + dot2.get_height()):
                count += 1
                print("Кількість зіткнень точок: ", ceil(count)-1)
                print("Зіткнення через %.3f секунд " % (clock3.tick() / 1000))

            pygame_window.blit(screen, (0, 0))
            pg.display.flip()
            j += 1
            pg.time.delay(speed_com)

        pg.quit()

    def situation2(self):
        size_x = 700
        size_y = 600
        window = pg.display.set_mode((size_x, size_y))
        pg.display.set_caption("Lab_5")
        GREEN = (0, 200, 0)
        RED = (255, 0, 0)
        WHITE = (255, 255, 255)
        screen = pg.Surface((size_x, size_y))
        screen.fill((55, 55, 55))

        dot1 = pg.Surface((6, 6))
        dot1.fill(RED)

        dot2 = pg.Surface((6, 6))
        dot2.fill(GREEN)

        dot3 = pg.Surface((8, 8))
        dot3.fill((255, 200, 0))
        picture = pg.image.load("KG.png")

        A = 3
        B = 2
        D = 1.5
        N = self.check_right_input(self.ui.lineEdit.text(), 1.5)
        speed_com = int(self.check_right_input(self.ui.lineEdit_2.text(), 10))
        N2 = 1.2

        x1 = 350
        y1 = 301
        x2 = 353
        y2 = 302
        j = 0
        some_time = 0
        clock = pg.time.Clock()
        clock.tick()
        done = True
        opposite = False

        while done:
            for i in pg.event.get():
                if i.type == pg.QUIT:
                    done = False

            if some_time >= 100:
                if x2 <= x1 + dot1.get_width() <= x2 + dot2.get_width() or x2 <= x1 <= x2 + dot2.get_width():
                    if y2 <= y1 + dot1.get_height() <= y2 + dot2.get_height() or y2 <= y1 <= y2 + dot2.get_height():
                        opposite = not opposite

            speed_dot1 = [((A - B) * cos(N * radians(j)) + D * cos(A / B * N * radians(j))) * (1.56 * N),
                          (((A - B) * sin(N * radians(j))) - D * sin(A / B * N * radians(j))) * (1.56 * N)]
            speed_dot2 = [-((A - B) * cos(N2 * radians(j)) + D * cos(A / B * N2 * radians(j))) * (1.56 * N2),
                          (((A - B) * sin(N2 * radians(j))) - D * sin(A / B * N2 * radians(j))) * (1.56 * N2)]

            screen.fill((55, 55, 55))
            screen.blit(picture, (168, 113))

            if opposite:
                x1 -= speed_dot1[0]
                y1 -= speed_dot1[1]

                x2 -= speed_dot2[0]
                y2 -= speed_dot2[1]
            if not opposite:
                x1 += speed_dot1[0]
                y1 += speed_dot1[1]

                x2 += speed_dot2[0]
                y2 += speed_dot2[1]

            screen.blit(dot1, (x1, y1))
            screen.blit(dot2, (x2, y2))
            if opposite:
                j -= 1
            else:
                j += 1
            some_time += 1
            window.blit(screen, (0, 0))
            pg.display.flip()
            pg.time.delay(speed_com)
        pg.quit()

    def situation3(self):
        speed1 = self.check_right_input(self.ui.lineEdit.text(), 1.5)
        speed_com = int(self.check_right_input(self.ui.lineEdit_2.text(), 10))
        size_x = 700
        size_y = 600
        pygame_window = pg.display.set_mode((size_x, size_y))
        pg.display.set_caption("Lab_5. Situation 4")
        GREEN = (0, 200, 0)
        RED = (255, 0, 0)
        screen = pg.Surface((size_x, size_y))
        screen.fill((55, 55, 55))

        dot1 = pg.Surface((6, 6))
        dot1.fill(RED)

        dot2 = pg.Surface((6, 6))
        dot2.fill(GREEN)

        dot3 = pg.Surface((8, 8))
        dot3.fill((255, 200, 0))

        picture = pg.image.load("KG.PNG")

        A = 3
        B = 2
        D = 1.5
        speed2 = 1

        phase = -400
        x1 = 348
        y1 = 301
        x2 = 332
        y2 = 190

        j = 0
        clock = pg.time.Clock()
        clock.tick()
        flag = False
        done = True
        while done:
            for i in pg.event.get():
                if i.type == pg.QUIT:
                    done = False

            screen.fill((55, 55, 55))
            screen.blit(picture, (168, 113))

            if (x2 <= x1 <= x2 + dot2.get_width() or x2 <= x1 + dot1.get_width() <= x2 + dot2.get_width()) and \
                    (y2 <= y1 <= y2 + dot2.get_height() or y2 <= y1 + dot1.get_height() <= y2 + dot2.get_height()):
                speed_com += ceil(10 * (1 / abs(speed1 - speed2 + 0.0001)))
                flag = True
            elif not flag:
                x1 += ((A - B) * cos(speed1 * radians(j)) + D * cos(
                    A / B * speed1 * radians(j))) * (1.56 * speed1)
                y1 += (((A - B) * sin(speed1 * radians(j))) - D * sin(
                    A / B * speed1 * radians(j))) * (1.56 * speed1)
                x2 -= ((A - B) * cos(speed2 * radians(j + phase)) + D * cos(
                    A / B * speed2 * radians(j + phase))) * (1.56 * speed2)
                y2 += (((A - B) * sin(speed2 * radians(j + phase))) - D * sin(
                    A / B * speed2 * radians(j + phase))) * (1.56 * speed2)
                screen.blit(dot1, (x1, y1))
                screen.blit(dot2, (x2, y2))
            if flag:
                pass
            pygame_window.blit(screen, (0, 0))
            pg.display.flip()
            j += 1
            pg.time.delay(speed_com)
        pg.quit()


if __name__ == "__main__":
    app = QtWidgets.QApplication(sys.argv)
    window = MyWindow()
    window.show()
    sys.exit(app.exec_())
