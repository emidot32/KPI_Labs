from tkinter import *
import networkx as nx
import random as rand
import matplotlib. pyplot as plt


class Main:
    def __init__(self, menu):
        self.menu = menu
        self.menu.title('Main task1')
        self.menu.geometry("400x180")
        self.menu.resizable(0, 0)

        menubar = Menu(self.menu)
        filemenu = Menu(menubar, tearoff=0)
        filemenu.add_command(label='Вікно №2', command=self.call_menu2)
        filemenu.add_command(label='Вікно №3', command=self.call_menu3)
        filemenu.add_command(label='Вікно №4', command=self.call_menu4)
        menubar.add_cascade(label='Вікна', font='Arial 15', menu=filemenu)
        self.menu.config(menu=menubar)

        Label(self.menu, text="Комісаров Ілля Андрійович", font=("Arial", 12)).pack(side='top')
        Label(self.menu, text="Група ІО-71,  Номер у списку - 8").pack(side='top')
        Label(self.menu, text="Обчислення варіанту (8+71%60)%30+1 = ").pack(side='top')
        self.btn_my_var = Button(self.menu,
                                 text='Перевірка варіанту',
                                 command=self.my_var)
        self.btn_my_var.pack(side='top')
        self.ent_var = Text(self.menu, width=5, height=1, bd=2)
        self.ent_var.place(x=320, y=45)
        Label(self.menu, text="Ви можете перейти в меню 2,3,4 за наступними посиланнями:", font=("Arial", 10)).pack(
            side='top')
        self.btn_menu2 = Button(root,
                                text="Вікно №2",
                                font='Verdana 10',
                                bg='Green',
                                command=self.call_menu2)
        self.btn_menu2.place(x=80, y=125)
        self.btn_menu3 = Button(root,
                                text="Вікно №3",
                                font='Verdana 10',
                                bg='Yellow',
                                command=self.call_menu3)
        self.btn_menu3.place(x=160, y=125)
        self.btn_menu4 = Button(root,
                                text="Вікно №4",
                                font='Verdana 10',
                                bg='Blue',
                                command=self.call_menu4)
        self.btn_menu4.place(x=240, y=125)

        self.menu.mainloop()

    def my_var(self):
        my_var = (8 + 71 % 60) % 30 + 1
        self.ent_var.delete(0.0, END)
        self.ent_var.insert(0.0, str(my_var))

    def call_menu2(self):
        Menu2(self.menu)

    def call_menu3(self):
        Menu3(self.menu)

    def call_menu4(self):
        Menu4(self.menu)


class Menu2:
    A = set()
    B = set()
    list_women = ['Антоніна', 'Оксана', 'Галина', 'Ольга', 'Світлана', 'Катерина',
                  'Тетяна', 'Юлія', 'Ірина', 'Домініка']
    list_men = ['Борис', 'Василь', 'Максим', 'Іван',
                'Аркадій', 'Артем', 'Петро', 'Ілля', 'Євген', 'Віталій']

    def __init__(self, menu):
        self.task2 = Toplevel(menu)
        self.task2.title("Menu #2")
        self.task2.geometry('600x570')
        self.task2.resizable(0, 0)

        Label(self.task2, text='Список жіночих імен:', font='Arial 11').place(x=50, y=5)
        self.lst_box_women = Listbox(self.task2, font='Arial 14', selectmode=EXTENDED)
        self.lst_box_women.place(x=50, y=30)
        for i in self.list_women:
            self.lst_box_women.insert(END, i)

        Label(self.task2, text='Виберіть множину.', font='Arial 11').place(x=50, y=270)

        self.women_set = StringVar()
        self.women_set.set(None)
        Radiobutton(self.task2,
                    text="Записати в множину А",
                    variable=self.women_set,
                    value="A",
                    command=self.update_text).place(x=60, y=290)
        Radiobutton(self.task2,
                    text="Записати в множину B",
                    variable=self.women_set,
                    value="B",
                    command=self.update_text).place(x=60, y=310)

        Label(self.task2, text='Список чоловічих імен:', font='Arial 11').place(x=320, y=5)
        self.lst_box_men = Listbox(self.task2, font='Arial 14', selectmode=EXTENDED)
        self.lst_box_men.place(x=320, y=30)
        for i in self.list_men:
            self.lst_box_men.insert(END, i)

        Label(self.task2, text='Виберіть множину.', font='Arial 11').place(x=320, y=270)

        self.men_set = StringVar()
        self.men_set.set(None)
        Radiobutton(self.task2,
                    text="Записати в множину А",
                    variable=self.men_set,
                    value="A",
                    command=self.update_text).place(x=330, y=290)
        Radiobutton(self.task2,
                    text="Записати в множину B",
                    variable=self.men_set,
                    value="B",
                    command=self.update_text).place(x=330, y=310)

        self.line = Canvas(self.task2, width=600, height=3)
        self.line.place(x=0, y=340)
        self.line.create_line(0, 2, 599, 2, width=2, fill='black')

        Label(self.task2, text='Кнопка для запису в файл множини А').place(x=40, y=350)
        self.btn_save_A = Button(self.task2,
                                 text="Записати А",
                                 command=self.save_A)
        self.btn_save_A.place(x=100, y=370)
        Label(self.task2, text='Кнопка для зчитування множини А').place(x=50, y=400)
        self.btn_read_A = Button(self.task2,
                                 text="Зчитати А",
                                 command=self.read_A)
        self.btn_read_A.place(x=102, y=420)
        Label(self.task2, text='Кнопка для очищення множини А').place(x=52, y=450)
        self.btn_clear_A = Button(self.task2,
                                  text="Очистити А",
                                  command=self.clear_A)
        self.btn_clear_A.place(x=98, y=470)

        Label(self.task2, text='Кнопка для запису в файл множини B').place(x=310, y=350)
        self.btn_save_B = Button(self.task2,
                                 text="Записати B",
                                 command=self.save_B)
        self.btn_save_B.place(x=370, y=370)
        Label(self.task2, text='Кнопка для зчитування множини B').place(x=320, y=400)
        self.btn_read_B = Button(self.task2,
                                 text="Зчитати B",
                                 command=self.read_B)
        self.btn_read_B.place(x=372, y=420)
        Label(self.task2, text='Кнопка для очищення множини B').place(x=322, y=450)
        self.btn_clear_B = Button(self.task2,
                                  text="Очистити B",
                                  command=self.clear_B)
        self.btn_clear_B.place(x=368, y=470)

        self.info_A = Text(self.task2, width=30, height=3, font="Verdana 10", wrap=WORD)
        self.info_A.place(x=25, y=500)

        self.info_B = Text(self.task2, width=30, height=3, font="Verdana 10", wrap=WORD)
        self.info_B.place(x=295, y=500)
        self.task2.mainloop()

    @staticmethod
    def inserter(value, field):
        field.delete(0.0, END)
        field.insert(0.0, value)

    def update_text(self):
        if self.women_set.get() == "A":
            for i in self.lst_box_women.curselection():
                self.A.add(self.list_women[i])
        elif self.women_set.get() == "B":
            for i in self.lst_box_women.curselection():
                self.B.add(self.list_women[i])
        if self.men_set.get() == "A":
            for i in self.lst_box_men.curselection():
                self.A.add(self.list_men[i])
        elif self.men_set.get() == "B":
            for i in self.lst_box_men.curselection():
                self.B.add(self.list_men[i])

    def save_A(self):
        with open(r"C:\Komisarow_Dis_Math\file_A.txt", 'w') as f:
            f.write(str(self.A))
        self.inserter(r"Множина А записана в файл C:\Komisarow_Dis_Math\file_A.txt", self.info_A)

    def save_B(self):
        with open(r"C:\Komisarow_Dis_Math\file_B.txt", 'w') as f:
            f.write(str(self.B))
        self.inserter(r"Множина B записана в файл C:\Komisarow_Dis_Math\file_A.txt", self.info_B)

    def read_A(self):
        with open(r"C:\Komisarow_Dis_Math\file_A.txt", 'r') as f:
            file = f.read()
        self.inserter("A = " + file, self.info_A)

    def read_B(self):
        with open(r"C:\Komisarow_Dis_Math\file_B.txt", 'r') as f:
            file = f.read()
        self.inserter("B = " + file, self.info_B)

    def clear_A(self):
        self.A.clear()
        self.inserter("Множина А очищена!", self.info_A)

    def clear_B(self):
        self.B.clear()
        self.inserter("Множина B очищена!", self.info_B)


class Menu3(Menu2):
    def __init__(self, menu):
        super(Menu2, self).__init__()
        self.task3 = Toplevel(menu)
        self.task3.title("Menu #3")
        self.task3.geometry("580x550")
        self.task3.resizable(0, 0)

        Label(self.task3, text="Елементи множини А:", font="Arial 11").place(x=50, y=10)
        self.lst_box_A = Listbox(self.task3, selectmode=EXTENDED, font="Arial 14")
        self.lst_box_A.place(x=50, y=30)
        for i in self.A:
            self.lst_box_A.insert(END, i)

        Label(self.task3, text="Елементи множини B:", font="Arial 11").place(x=300, y=10)
        self.lst_box_B = Listbox(self.task3, selectmode=EXTENDED, font="Arial 14")
        self.lst_box_B.place(x=300, y=30)
        for i in self.B:
            self.lst_box_B.insert(END, i)

        Label(self.task3, text="Кнопка для виводу графічного представлення відношення S",
              font="Arial 11").place(x=50, y=270)
        self.btn_show_S = Button(self.task3,
                                 text="Показати S",
                                 height=2,
                                 width=40,
                                 command=self.relation_S)
        self.btn_show_S.place(x=70, y=300)

        Label(self.task3, text="Кнопка для виводу графічного представлення відношення R",
              font="Arial 11").place(x=50, y=340)
        self.btn_show_R = Button(self.task3,
                                 text="Показати R",
                                 height=2,
                                 width=40,
                                 command=self.relation_R)
        self.btn_show_R.place(x=70, y=370)

        self.line = Canvas(self.task3, width=580, height=3)
        self.line.place(x=0, y=430)
        self.line.create_line(0, 2, 579, 2, width=2, fill='black')

        self.btn_show_S_txt = Button(self.task3,
                                     text="Показати відношення S",
                                     command=self.relation_S_txt)
        self.btn_show_S_txt.place(x=90, y=445)
        self.field_S = Text(self.task3, width=30, height=3, font="Verdana 10", wrap=WORD)
        self.field_S.place(x=30, y=480)

        self.btn_show_R_txt = Button(self.task3,
                                     text="Показати відношення R",
                                     command=self.relation_R_txt)
        self.btn_show_R_txt.place(x=350, y=445)
        self.field_R = Text(self.task3, width=30, height=3, font="Verdana 10", wrap=WORD)
        self.field_R.place(x=300, y=480)

        self.task3.mainloop()

    def relation_S(self):
        global S
        list_S = []
        for a in rand.sample(self.A, int(len(self.A) / 1.2)):
            for b in rand.sample(self.B, int(len(self.B) / 1.2)):
                if a in self.list_men and b in self.list_women:
                    flag = True
                    for i in list_S:
                        if a in i or b in i:
                            flag = False
                    if flag:
                        list_S.append([a, b])
        S = list_S
        g1 = nx.DiGraph()
        g1.add_nodes_from(list(self.A | self.B))
        g1.add_edges_from(S)
        nx.draw_networkx(g1, pos=nx.circular_layout(g1), arrows=True, with_labels=True, edges=g1.edges(),
                         edge_color="blue", node_size=200)
        plt.show()

    def relation_R(self):
        global R
        list_S = S
        list_R = []
        for a in rand.sample(self.A, int(len(self.A))):
            for b in rand.sample(self.B, int(len(self.B))):
                if a in self.list_men:
                    for i in list_S:
                        if a != i[0] and [a, b] not in list_S and a != b:
                            list_R.append([a, b])
                    for j in list_R:
                        if a in j:
                            list_R.remove(j)
        test = []
        for i in list_R:
            for j in i:
                test.append(j)
                if test.count(j) > 0 and i in list_R:
                    list_R.remove(i)
            if list_R is None:
                list_R.append(i)
            if i in list_R:
                list_R.remove(i)
                list_R.append(i)
        R = list_R
        g2 = nx.DiGraph()
        g2.add_nodes_from(list(self.A | self.B))
        g2.add_edges_from(list_R)
        nx.draw_networkx(g2, pos=nx.circular_layout(g2), arrows=True, with_labels=True, edges=g2.edges(),
                         edge_color="green", node_size=200)
        plt.show()

    def relation_S_txt(self):
        try:
            S_txt = ("S = %s" % (S))
            self.inserter(S_txt, self.field_S)
        except NameError:
            self.inserter("Спочатку потрібно представити відношення графічно!", self.field_S)

    def relation_R_txt(self):
        try:
            R_txt = ("R = %s" % (R))
            self.inserter(R_txt, self.field_R)
        except NameError:
            self.inserter("Спочатку потрібно представити відношення графічно!", self.field_R)


class Menu4(Menu3):
    def __init__(self, menu):
        super(Menu2, self).__init__()
        self.task4 = Toplevel(menu)
        self.task4.title("Menu #4")
        self.task4.geometry("670x500")
        self.task4.resizable(0, 0)

        Label(self.task4, text="Графічне представлення відношення R∪S.",
              font="Arial 12").place(x=20, y=20)
        self.btn_show_S_union_R = Button(self.task4,
                                         text="Показати R∪S",
                                         height=2,
                                         width=40,
                                         command=self.union_graph)
        self.btn_show_S_union_R.place(x=40, y=50)

        Label(self.task4, text="Графічне представлення відношення R∩S.",
              font="Arial 12").place(x=20, y=100)
        self.btn_show_S_cros_R = Button(self.task4,
                                        text="Показати R∩S",
                                        height=2,
                                        width=40,
                                        command=self.crossing_graph)
        self.btn_show_S_cros_R.place(x=40, y=130)

        Label(self.task4, text="Графічне представлення відношення R\S.",
              font="Arial 12").place(x=20, y=170)
        self.btn_show_S_dif_R = Button(self.task4,
                                       text="Показати R\S",
                                       height=2,
                                       width=40,
                                       command=self.difference_graph)
        self.btn_show_S_dif_R.place(x=40, y=200)

        Label(self.task4, text="Графічне представлення відношення U\S.",
              font="Arial 12").place(x=20, y=250)
        self.btn_show_S_dif_R = Button(self.task4,
                                       text="Показати U\S",
                                       height=2,
                                       width=40,
                                       command=self.not_R_graph)
        self.btn_show_S_dif_R.place(x=40, y=280)

        Label(self.task4, text="Графічне представлення відношення S^-1.",
              font="Arial 12").place(x=20, y=330)
        self.btn_show_S_dif_R = Button(self.task4,
                                       text="Показати S^-1",
                                       height=2,
                                       width=40,
                                       command=self.inversion_S_graph)
        self.btn_show_S_dif_R.place(x=40, y=360)

        self.line = Canvas(self.task4, width=5, height=550)
        self.line.place(x=360, y=0)
        self.line.create_line(2, 0, 2, 549, width=2, fill='black')

        self.btn_show_S_union_R = Button(self.task4,
                                         text="Вивести R∪S",
                                         height=2,
                                         width=30,
                                         command=self.union_text)
        self.btn_show_S_union_R.place(x=400, y=20)

        self.btn_show_S_cros_R = Button(self.task4,
                                        text="Вивести R∩S",
                                        height=2,
                                        width=30,
                                        command=self.crossing_text)
        self.btn_show_S_cros_R.place(x=400, y=70)

        self.btn_show_S_dif_R = Button(self.task4,
                                       text="Вивести R\S",
                                       height=2,
                                       width=30,
                                       command=self.difference_text)
        self.btn_show_S_dif_R.place(x=400, y=120)

        self.btn_show_S_dif_R = Button(self.task4,
                                       text="Вивести U\S",
                                       height=2,
                                       width=30,
                                       command=self.not_R_text)
        self.btn_show_S_dif_R.place(x=400, y=170)

        self.btn_show_S_dif_R = Button(self.task4,
                                       text="Вивести S^-1",
                                       height=2,
                                       width=30,
                                       command=self.inversion_S_text)
        self.btn_show_S_dif_R.place(x=400, y=220)

        self.field_all_rltns = Text(self.task4, width=38, height=8, font="Verdana 10", wrap=WORD)
        self.field_all_rltns.place(x=363, y=270)

        self.task4.mainloop()

    def union_graph(self):
        g = nx.DiGraph()
        g.add_nodes_from(list(self.A | self.B))
        g.add_edges_from(list(S + R))
        nx.draw_networkx(g, pos=nx.circular_layout(g), arrows=True, with_labels=True, edges=g.edges())
        plt.show()

    def union_text(self):
        C1 = "R∪S = %s" % (list(S + R))
        self.inserter(C1, self.field_all_rltns)

    def crossing_graph(self):
        global C2
        C2 = []
        for i in S:
            for j in list(R):
                if i == j:
                    C2.append(i)
        g = nx.DiGraph()
        g.add_nodes_from(list(self.A | self.B))
        g.add_edges_from(C2)
        nx.draw_networkx(g, pos=nx.circular_layout(g), arrows=True, with_labels=True, edges=g.edges())
        plt.show()

    def crossing_text(self):
        try:
            C2_txt = "R∩S = %s" % (list(C2))
            self.inserter(C2_txt, self.field_all_rltns)
        except NameError:
            self.inserter("Спочатку задайте відношення графічно!", self.field_all_rltns)

    def difference_graph(self):
        global C3
        C3 = list(R)
        for i in S:
            for j in list(R):
                if i == j:
                    C3.remove(j)
        g = nx.DiGraph()
        g.add_nodes_from(list(self.A | self.B))
        g.add_edges_from(C3)
        nx.draw_networkx(g, pos=nx.circular_layout(g), arrows=True, with_labels=True, edges=g.edges())
        plt.show()

    def difference_text(self):
        try:
            C3_txt = "R\S = %s" % (list(C3))
            self.inserter(C3_txt, self.field_all_rltns)
        except NameError:
            self.inserter("Спочатку задайте відношення графічно!", self.field_all_rltns)

    def not_R_graph(self):
        global C4
        C4 = []
        for i in self.A:
            for j in self.B:
                if [i, j] not in R:
                    C4.append([i, j])
        g = nx.DiGraph()
        g.add_nodes_from(list(self.A | self.B))
        g.add_edges_from(C4)
        nx.draw_networkx(g, pos=nx.circular_layout(g), arrows=1, with_labels=True, edges=g.edges())
        plt.show()

    def not_R_text(self):
        try:
            C4_txt = "U\S = %s" % (list(C4))
            self.inserter(C4_txt, self.field_all_rltns)
        except NameError:
            self.inserter("Спочатку задайте відношення графічно!", self.field_all_rltns)

    def inversion_S_graph(self):
        global C5
        C5 = list(S)
        for i in range(len(C5)):
            C5[i][0], C5[i][1] = C5[i][1], C5[i][0]
        g = nx.DiGraph()
        g.add_nodes_from(list(self.A | self.B))
        g.add_edges_from(C5)
        nx.draw_networkx(g, pos=nx.circular_layout(g), arrows=True, with_labels=True, edges=g.edges())
        plt.show()

    def inversion_S_text(self):
        try:
            C5_txt = "S^-1 = %s" % (list(C5))
            self.inserter(C5_txt, self.field_all_rltns)
        except NameError:
            self.inserter("Спочатку задайте відношення графічно!", self.field_all_rltns)


root = Tk()
Main(root)
