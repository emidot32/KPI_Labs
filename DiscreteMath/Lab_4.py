from tkinter import *
import networkx as nx
from random import randint
import pylab as plt


def visit(i, some_matrix, color, colors):
    def nice_color():
        flag = True
        for j in range(len(some_matrix)):
            if some_matrix[j][i] == 1 and color[j] == appropriate_color:
                flag = False
                break
        return flag

    if i == len(some_matrix):
        return color
    else:
        for appropriate_color in colors:
            if nice_color():
                color[i] = appropriate_color
                return visit(i + 1, some_matrix, color, colors)


class Main:
    def __init__(self, menu):
        self.menu = menu
        self.menu.title('Main')
        self.menu.geometry("400x270")
        self.menu.resizable(0, 0)

        self.menubar_for_windows(self.menu)

        Label(self.menu, text="Комісаров Ілля Андрійович", font=("Arial", 12)).pack(side='top')
        Label(self.menu, text="Група ІО-71,  Номер у списку - 8").pack(side='top')
        Label(self.menu, text="Обчислення варіанту 7108 % 6 + 1 = ").pack(side='top')
        self.btn_my_var = Button(self.menu,
                                 text='Перевірка варіанту',
                                 command=self.my_var)
        self.btn_my_var.pack(side='top')
        self.ent_var = Text(self.menu, width=5, height=1, bd=2)
        self.ent_var.place(x=320, y=45)
        Label(self.menu, text="Ви можете вибрати такі пункти:", font=("Arial", 11)).pack(
            side='top')
        self.btn_menu2 = Button(root,
                                text="Створити матрицю суміжності",
                                font='Verdana 10',
                                bg='Red',
                                command=self.call_menu2)
        self.btn_menu2.place(x=95, y=125)
        self.btn_menu3 = Button(root,
                                text="Переглянути створений вами граф",
                                font='Verdana 10',
                                bg='Yellow',
                                command=self.call_menu3)
        self.btn_menu3.place(x=80, y=155)
        self.btn_menu4 = Button(root,
                                text="Переглянути заданий граф",
                                font='Verdana 10',
                                bg='Blue',
                                command=self.call_menu4)
        self.btn_menu4.place(x=105, y=185)

        self.menu.mainloop()

    def menubar_for_windows(self, menu):
        menubar = Menu(menu)
        filemenu = Menu(menubar, tearoff=0)
        filemenu.add_command(label='Вікно №2', command=self.call_menu2)
        filemenu.add_command(label='Вікно №3', command=self.call_menu3)
        filemenu.add_command(label='Вікно №4', command=self.call_menu4)
        menubar.add_cascade(label='Вікна', font='Arial 15', menu=filemenu)
        menu.config(menu=menubar)

    def my_var(self):
        my_var = 7108 % 6 + 1
        self.ent_var.delete(0.0, END)
        self.ent_var.insert(0.0, str(my_var))

    def call_menu2(self):
        Menu2(self.menu)

    def call_menu3(self):
        Menu3(self.menu)

    def call_menu4(self):
        Menu4(self.menu)


class Menu2(Main):
    def __init__(self, menu):
        super(Main, self).__init__()
        self.task2 = Toplevel(menu)
        self.task2.title("Menu #2")
        self.task2.geometry('450x450')
        self.task2.resizable(0, 0)

        self.menubar_for_windows(self.task2)

        Label(self.task2, text="Виберіть кількість вершин:", font="Arial 12").place(x=30, y=23)
        self.scale = Scale(self.task2, orient=HORIZONTAL, length=360, from_=1, to_=10, tickinterval=1, resolution=1)
        self.scale.place(x=40, y=45)

        self.btn_to_create_matrix = Button(self.task2, text="Створити матрицю суміжності", command=self.gen_matrix)
        self.btn_to_create_matrix.place(x=30, y=110)

        self.btn_to_create_random_matrix = Button(self.task2, text="Заповнити випадковим чином",
                                                  command=self.random_matrix)
        self.btn_to_create_random_matrix.place(x=30, y=140)

        self.task2.mainloop()

    def call_menu2(self):
        Menu2(self.task2)

    def call_menu3(self):
        Menu3(self.task2)

    def call_menu4(self):
        Menu4(self.task2)

    def gen_matrix(self):
        global matrix_of_adjacency
        matrix_of_adjacency = [[] for j in range(self.scale.get())]
        for i in range(1, self.scale.get() + 1):
            Label(self.task2, text="%3s" % (i), font="Arial 11").place(x=20, y=180 + i * 22 + 10)
        for i in range(1, self.scale.get() + 1):
            Label(self.task2, text="%3s" % (i), font="Arial 11").place(x=17 + i * 22 + 10, y=180)
        for i in range(self.scale.get()):
            for j in range(self.scale.get()):
                matrix_of_adjacency[i].append(Text(self.task2, font='Arial 10', width=2, height=1))
                matrix_of_adjacency[i][j].place(x=40 + j * 22 + 13, y=198 + i * 22 + 13)

    def random_matrix(self):
        random_matr = []
        for i in range(self.scale.get()):
            random_matr.append([])
            for j in range(self.scale.get()):
                random_matr[i].append(randint(0, 1))
        for i in range(self.scale.get()):
            for j in range(self.scale.get()):
                if i == j:
                    random_matr[i][j] = 0
                else:
                    random_matr[i][j] = random_matr[j][i]
        for i in range(self.scale.get()):
            for j in range(self.scale.get()):
                matrix_of_adjacency[i][j].delete(0.0, END)
                matrix_of_adjacency[i][j].insert(END, random_matr[i][j])


class Menu3(Menu2):
    def __init__(self, menu):
        super(Main, self).__init__()
        self.task3 = Toplevel(menu)
        self.task3.title("Menu #3")
        self.task3.geometry('600x250')
        self.task3.resizable(0, 0)
        self.menubar_for_windows(self.task3)

        Label(self.task3, text="В цьому вікні ви можете переглянути звичайний та\n"
                               "розфарбованиий графи, створені на основі матриці суміжності", font="Arial 12") \
            .place(x=80, y=20)
        self.btn_create_graph1 = Button(self.task3, text="Створити граф", height=3, width=30, relief=GROOVE,
                                        command=self.create_graph)
        self.btn_create_graph1.place(x=50, y=80)
        self.btn_create_graph2 = Button(self.task3, text="Показати розфарбований граф", height=3, width=30,
                                        relief=GROOVE, command=self.dye_graph)
        self.btn_create_graph2.place(x=330, y=80)
        self.info = Text(self.task3, width=40, height=3, font="Verdana 10", wrap=WORD)
        self.info.place(x=130, y=160)

        self.task3.mainloop()

    def create_graph(self):
        global matrix
        matrix = [['' for i in range(len(matrix_of_adjacency))] for i in range(len(matrix_of_adjacency))]
        for i in range(len(matrix_of_adjacency)):
            for j in range(len(matrix_of_adjacency)):
                if matrix_of_adjacency[i][j].get(0.0) == '1' or matrix_of_adjacency[i][j].get(0.0) == '0':
                    matrix[i][j] = int(matrix_of_adjacency[i][j].get(0.0))
                else:
                    self.info.delete(0.0, END)
                    self.info.insert(0.0, "Ви неправильно ввели дані в матрицю!")

        try:
            g = nx.DiGraph()
            for i in range(len(matrix)):
                for j in range(len(matrix)):
                    if matrix[i][j] == 1:
                        g.add_edge(i + 1, j + 1)
            nx.draw_networkx(g, pos=nx.circular_layout(g), arrows=False, with_labels=True, node_color='gray')
            self.info.delete(0.0, END)
            self.info.insert(0.0, "Граф успішно створений!")
            plt.show()
        except IndexError:
            self.info.delete(0.0, END)
            self.info.insert(0.0, "Ви неправильно ввели дані в матрицю!")

    def dye_graph(self):
        g = nx.DiGraph()
        our_colors = ['red', 'blue', 'green', 'yellow', 'brown', 'orange']
        for i in range(len(matrix)):
            for j in range(len(matrix)):
                if matrix[i][j] == 1:
                    g.add_edge(str(i+1), str(j+1))
        nx.draw_networkx(g, pos=nx.circular_layout(g), arrows=False, with_labels=True,
                         node_color=visit(0, matrix, ['' for i in range(len(matrix))], our_colors),
                         nodelist=[str(i) for i in range(1, len(matrix)+1)])
        plt.show()


class Menu4(Menu3):
    def __init__(self, menu):
        super(Main, self).__init__()
        self.task4 = Toplevel(menu)
        self.task4.title("Menu #4")
        self.task4.geometry('600x180')
        self.task4.resizable(0, 0)
        self.menubar_for_windows(self.task4)

        Label(self.task4, text="В цьому вікні ви можете переглянути звичайний та\n"
                               "розфарбованиий графи, які задані у варіанті.", font="Arial 12").place(x=100, y=20)

        self.btn_create_graph1 = Button(self.task4, text="Створити граф", height=3, width=30, relief=GROOVE,
                                        command=self.create_graph)
        self.btn_create_graph1.place(x=50, y=80)
        self.btn_create_graph2 = Button(self.task4, text="Показати розфарбований граф", height=3, width=30,
                                        relief=GROOVE, command=self.dye_graph)
        self.btn_create_graph2.place(x=330, y=80)

        self.task4.mainloop()

    def create_graph(self):
        relation = [['1', '2'], ['1', '5'], ['2', '3'], ['2', '5'], ['2', '6'],
                    ['3', '4'], ['3', '6'], ['3', '7'], ['4', '7'], ['7', '6'],
                    ['7', '9'], ['6', '5'], ['6', '8'], ['6', '9'], ['5', '8']]
        g = nx.DiGraph()
        g.add_edges_from(relation)
        result = list(zip([str(i) for i in range(1, 10)],
                          [(6, 16), (12, 16), (18, 16), (21, 15), (6, 14), (12, 14), (18, 14), (9, 13), (15, 13)]))
        nx.draw_networkx(g, pos=dict(result), arrows=False, with_labels=True, node_color="gray")
        plt.show()

    def dye_graph(self):
        relation = [['1', '2'], ['1', '5'], ['2', '3'], ['2', '5'], ['2', '6'],
                    ['3', '4'], ['3', '6'], ['3', '7'], ['4', '7'], ['7', '6'],
                    ['7', '9'], ['6', '5'], ['6', '8'], ['6', '9'], ['5', '8']]
        matrix = [
                  [0, 1, 0, 0, 1, 0, 0, 0, 0],
                  [1, 0, 1, 0, 1, 1, 0, 0, 0],
                  [0, 1, 0, 1, 0, 1, 1, 0, 0],
                  [0, 0, 1, 0, 0, 0, 1, 0, 0],
                  [1, 1, 0, 0, 0, 1, 0, 1, 0],
                  [0, 1, 1, 0, 1, 0, 1, 1, 1],
                  [0, 0, 1, 1, 0, 1, 0, 0, 1],
                  [0, 0, 0, 0, 1, 1, 0, 0, 0],
                  [0, 0, 0, 0, 0, 1, 1, 0, 0]
                ]
        our_colors = ['red', 'blue', 'green', 'yellow', 'brown', 'orange']
        g = nx.DiGraph()
        g.add_edges_from(relation)
        result = list(zip([str(i) for i in range(1, 10)],
                          [(6, 16), (12, 16), (18, 16), (21, 15), (6, 14), (12, 14), (18, 14), (9, 13), (15, 13)]))
        nx.draw_networkx(g, pos=dict(result), arrows=False, with_labels=True,
                         node_color=visit(0, matrix, ['' for i in range(9)], our_colors),
                         nodelist=[str(i) for i in range(1, 10)])
        plt.show()


root = Tk()
Main(root)
