from tkinter import *
import random


def sym_dif(a, b):
    a = list(a)
    b = list(b)
    c = []
    for i in range(len(a)):
        if a[i] not in b:
            c.append(a[i])
    for j in range(len(b)):
        if b[j] not in a:
            c.append(b[j])
    return set(c)


def union(a, b):
    a = list(a)
    b = list(b)
    c = b[:]
    for i in range(len(a)):
        q = 0
        for j in range(len(b)):
            if a[i] == b[j]:
                q = 1
        if q == 0:
            c.append(a[i])
    return set(c)


class Main:
    def __init__(self):
        Label(root, text="Комісаров Ілля Андрійович", font=("Arial", 12)).pack(side='top')
        Label(root, text="Група ІО-71, Номер у списку - 8").pack(side='top')
        Label(root, text="Обчислення варіанту (8+71%60)%30+1 = ").pack(side='top')
        self.my_var = Button(root,
                             text='Перевірка варіанту',
                             command=self.my_var)
        self.my_var.pack(side='top')
        self.ent_var = Text(root, width=5, height=1, bd=2)
        self.ent_var.place(x=520, y=45)
        Label(root, text="Ви можете перейти в меню 2,3,4,5 за наступними посиланнями:", font=("Arial", 10)).pack(
            side='top')
        self.btn_menu2 = Button(root,
                                text="Вікно №2",
                                font='Verdana 12',
                                bg='Green',
                                command=self.check2)
        self.btn_menu2.place(x=600, y=88)
        self.btn_menu3 = Button(root,
                                text="Вікно №3",
                                font='Verdana 12',
                                bg='Yellow',
                                command=self.check3)
        self.btn_menu3.place(x=600, y=128)
        self.btn_menu4 = Button(root,
                                text="Вікно №4",
                                font='Verdana 12',
                                bg='Blue',
                                command=self.check4)
        self.btn_menu4.place(x=600, y=168)
        self.btn_menu5 = Button(root,
                                text="Вікно №5",
                                font='Verdana 12',
                                bg='Red',
                                command=self.check5)
        self.btn_menu5.place(x=600, y=208)

        Label(root, text="1.", font=('Arial', 11)).place(x=5, y=120)
        Label(root, text="Введіть потужність множин: ", font=('Arial', 11)
              ).place(x=20, y=120)
        Label(root, text='|A| = ').place(x=50, y=145)
        Label(root, text='|B| = ').place(x=50, y=165)
        Label(root, text='|C| = ').place(x=50, y=185)

        self.power_A = Entry(root, width=7, bd=2)
        self.power_A.place(x=80, y=145)

        self.power_B = Entry(root, width=7, bd=2)
        self.power_B.place(x=80, y=165)

        self.power_C = Entry(root, width=7, bd=2)
        self.power_C.place(x=80, y=185)

        Label(root, text="2.", font=('Arial', 11)
              ).place(x=5, y=220)

        self.btn_gnrtr = Button(root,
                                text='Натиcніть на цю кнопку, щоб згенерувати множини випадковим чином',
                                activebackground='Red',
                                command=self.random_sets)
        self.btn_gnrtr.place(x=20, y=220)

        self.Info = Text(root, width=50, height=2, font="Verdana 10", wrap=WORD)
        self.Info.place(x=21, y=255)

        Label(root, text="3.", font=('Arial', 11)).place(x=5, y=300)
        Label(root, text="Ви також можете створити множини власноруч", font="Arial 11"
              ).place(x=20, y=300)
        Label(root, text="Введіть цілі числа через кому (числа не менше 0 і не більше 255)"
              ).place(x=20, y=320)
        Label(root, text='P.S. Числа менше 0 та більше 255 будуть автоматично видалені.').place(x=20, y=340)
        Label(root, text="А = ").place(x=50, y=365)
        Label(root, text="B = ").place(x=50, y=385)
        Label(root, text="C = ").place(x=50, y=405)

        self.ent_A = Entry(root, width=40, bd=2)
        self.ent_A.place(x=75, y=365)
        self.ent_B = Entry(root, width=40, bd=2)
        self.ent_B.place(x=75, y=385)
        self.ent_C = Entry(root, width=40, bd=2)
        self.ent_C.place(x=75, y=405)

        self.btn_crtr = Button(root,
                               text='Створити множини',
                               height=3,
                               activebackground='Red',
                               command=self.make_sets)
        self.btn_crtr.place(x=350, y=365)

        self.Info2 = Text(root, width=50, height=2, font="Verdana 10", wrap=WORD)
        self.Info2.place(x=21, y=435)

        Label(root, text="4.", font=('Arial', 11)).place(x=5, y=490)
        Label(root, text='Введіть діапазон цілих чисел, який буде універсальною множиною', font="Arial 11"
              ).place(x=20, y=490)
        self.ent_U = Entry(root, width=7, bd=2)
        self.ent_U.place(x=485, y=490)

        self.btn_U = Button(root,
                            text='Створити U',
                            activebackground='Red',
                            command=self.universal_set)
        self.btn_U.place(x=21, y=510)
        self.Info3 = Text(root, width=50, height=3, font="Verdana 10", wrap=WORD)
        self.Info3.place(x=21, y=540)
        self.btn_clicks1 = 0
        self.btn_clicks2 = 0

    def menu2(self):
        self.task2 = Toplevel(root)
        self.task2.title('Menu №2')
        self.task2.geometry('810x550')
        self.task2.resizable(False, False)
        Label(self.task2, text='Виберіть, які з множин будуть основними', font='Arial, 11').place(x=20, y=20)
        self.favorite = StringVar()
        self.favorite.set(None)
        Radiobutton(self.task2,
                    text="Множини створені випадковим чином",
                    variable=self.favorite,
                    value="Рандом",
                    command=self.update_text_long).place(x=30, y=50)
        Radiobutton(self.task2,
                    text="Множини створені вами",
                    variable=self.favorite,
                    value="Вручную",
                    command=self.update_text_long).place(x=30, y=70)

        self.btn_call_sets = Button(self.task2,
                                    text='Множини',
                                    command=self.show_sets_long)
        self.btn_call_sets.place(x=20, y=100)

        self.field_sets = Text(self.task2, width=75, height=5, font="Verdana 10", wrap=WORD)
        self.field_sets.place(x=21, y=140)

        Label(self.task2, text='D = (A∪B)∪C∪(B∪C)∪A', font='Arial, 11').place(x=20, y=230)

        self.show_oprtns = Button(self.task2,
                                  text='Показати операції',
                                  command=self.show_oprtns_long)
        self.show_oprtns.place(x=20, y=260)

        self.field_D = Text(self.task2, width=75, height=3, font="Verdana 10", wrap=WORD)
        self.field_D.place(x=21, y=290)

        self.field_oprtn = Text(self.task2, width=75, height=3, font="Verdana 10", wrap=WORD)
        self.field_oprtn.place(x=21, y=350)

        self.btn_save = Button(self.task2,
                               text='Зберегти',
                               command=self.save_long)
        self.btn_save.place(x=20, y=410)
        self.Info4 = Text(self.task2, width=75, height=2, font="Verdana 10", wrap=WORD)
        self.Info4.place(x=21, y=450)

    def menu3(self):
        self.task3 = Toplevel(root)
        self.task3.title('Menu №3')
        self.task3.geometry('805x500')
        self.task3.resizable(False, False)

        Label(self.task3, text='Виберіть, які з множин будуть основними', font='Arial, 11').place(x=20, y=20)
        self.favorite_short = StringVar()
        self.favorite_short.set(None)
        Radiobutton(self.task3,
                    text="Множини створені випадковим чином",
                    variable=self.favorite_short,
                    value="Рандом",
                    command=self.update_text_short).place(x=30, y=50)
        Radiobutton(self.task3,
                    text="Множини створені вами",
                    variable=self.favorite_short,
                    value="Вручную",
                    command=self.update_text_short).place(x=30, y=70)

        self.btn_call_sets_short = Button(self.task3,
                                          text='Множини',
                                          command=self.show_sets_short)
        self.btn_call_sets_short.place(x=20, y=100)

        self.field_sets_short = Text(self.task3, width=75, height=5, font="Verdana 10", wrap=WORD)
        self.field_sets_short.place(x=21, y=140)

        Label(self.task3, text='D = A∪B∪C', font='Arial, 11').place(x=20, y=230)

        self.show_oprtns = Button(self.task3,
                                  text='Показати операції',
                                  command=self.show_oprtns_short)
        self.show_oprtns.place(x=20, y=260)

        self.field_D_short = Text(self.task3, width=75, height=3, font="Verdana 10", wrap=WORD)
        self.field_D_short.place(x=21, y=290)

        self.field_oprtn_short = Text(self.task3, width=75, height=3, font="Verdana 10", wrap=WORD)
        self.field_oprtn_short.place(x=21, y=350)

        self.btn_save_short = Button(self.task3,
                                     text='Зберегти',
                                     command=self.save_short)
        self.btn_save_short.place(x=20, y=410)
        self.Info4_short = Text(self.task3, width=75, height=2, font="Verdana 10", wrap=WORD)
        self.Info4_short.place(x=21, y=450)

    def menu4(self):
        self.task4 = Toplevel(root)
        self.task4.title('Menu №4')
        self.task4.geometry('805x600')
        self.task4.resizable(False, False)

        Label(self.task4, text='Виберіть, які з множин будуть основними', font='Arial, 11').place(x=20, y=20)
        self.favorite_XY = StringVar()
        self.favorite_XY.set(None)
        Radiobutton(self.task4,
                    text="Множини створені випадковим чином",
                    variable=self.favorite_XY,
                    value="Рандом",
                    command=self.update_text_XY).place(x=30, y=50)
        Radiobutton(self.task4,
                    text="Множини створені вами",
                    variable=self.favorite_XY,
                    value="Вручную",
                    command=self.update_text_XY).place(x=30, y=70)
        Label(self.task4, text='Ви можете переглянути множини Х та Y.', font='Arial, 10').place(x=20, y=100)
        self.X_and_Y = Button(self.task4,
                              text='Множини',
                              command=self.show_X_and_Y)
        self.X_and_Y.place(x=20, y=125)
        self.field_X_and_Y = Text(self.task4, width=75, height=7, font="Verdana 10", wrap=WORD)
        self.field_X_and_Y.place(x=21, y=160)

        Label(self.task4, text='Z = X Δ Y', font='Arial, 12').place(x=20, y=280)
        self.btn_Z = Button(self.task4,
                            text='Результат',
                            command=self.X_sym_dif_Y)
        self.btn_Z.place(x=20, y=305)
        self.field_Z = Text(self.task4, width=75, height=7, font="Verdana 10", wrap=WORD)
        self.field_Z.place(x=21, y=340)

        self.btn_save_Z = Button(self.task4,
                                 text='Зберегти',
                                 command=self.save_Z)
        self.btn_save_Z.place(x=20, y=470)
        self.Info4_Z = Text(self.task4, width=75, height=3, font="Verdana 10", wrap=WORD)
        self.Info4_Z.place(x=21, y=500)

    def menu5(self):
        self.task5 = Toplevel(root)
        self.task5.title('Menu №5')
        self.task5.geometry('805x700')
        self.task5.resizable(False, False)

        Label(self.task5, text='Ви можете зчитати дані за файлів, в які записані множини.', font='Arial, 11').place(
            x=20, y=20)
        Label(self.task5,
              text='1. Кнопка для зчитування файла, в якому збережений результат неспрощеного виразу.').place(x=20,
                                                                                                              y=60)
        self.btn_load_long = Button(self.task5,
                                    text='Зчитати',
                                    command=self.load_long)
        self.btn_load_long.place(x=20, y=80)
        self.field_load_long = Text(self.task5, width=75, height=3, font="Verdana 10", wrap=WORD)
        self.field_load_long.place(x=21, y=110)

        Label(self.task5, text='2. Кнопка для зчитування файла, в якому збережений результат спрощеного виразу.').place(
            x=20, y=170)
        self.btn_load_short = Button(self.task5,
                                     text='Зчитати',
                                     command=self.load_short)
        self.btn_load_short.place(x=20, y=200)
        self.field_load_short = Text(self.task5, width=75, height=3, font="Verdana 10", wrap=WORD)
        self.field_load_short.place(x=21, y=230)

        Label(self.task5, text='3. Кнопка для зчитування файла, в якому збережена множина Z.').place(x=20, y=280)
        self.btn_load_Z = Button(self.task5,
                                 text='Зчитати',
                                 command=self.load_Z)
        self.btn_load_Z.place(x=20, y=300)
        self.field_load_Z = Text(self.task5, width=75, height=3, font="Verdana 10", wrap=WORD)
        self.field_load_Z.place(x=21, y=330)

        Label(self.task5, text='4. Результат виконання Z виразу за допомогою стандартної функції Python.').place(x=20,
                                                                                                                 y=380)
        self.btn_std_Z = Button(self.task5,
                                text='Результат',
                                command=self.X_stdsymdif_Y)
        self.btn_std_Z.place(x=20, y=400)
        self.field_std_Z = Text(self.task5, width=75, height=3, font="Verdana 10", wrap=WORD)
        self.field_std_Z.place(x=21, y=430)

        Label(self.task5, text='5. Ви можете порівняти результати з пунктів 1,2 та 3,4.').place(x=20, y=480)
        self.btn_comparison = Button(self.task5,
                                     text='Порівняти',
                                     command=self.comparison)
        self.btn_comparison.place(x=20, y=500)
        self.field_comparison1 = Text(self.task5, width=75, height=3, font="Verdana 10", wrap=WORD)
        self.field_comparison1.place(x=21, y=530)

        self.field_comparison2 = Text(self.task5, width=75, height=3, font="Verdana 10", wrap=WORD)
        self.field_comparison2.place(x=21, y=580)

    def my_var(self):
        my_var = (8 + 71 % 60) % 30 + 1
        self.ent_var.delete(0.0, END)
        self.ent_var.insert(0.0, str(my_var))

    def inserter(self, value, field):
        field.delete(0.0, END)
        field.insert('0.0', value)

    def random_sets(self):
        pow_A = self.power_A.get()
        pow_B = self.power_B.get()
        pow_C = self.power_C.get()
        if pow_A.isdecimal() and pow_B.isdecimal() and pow_C.isdecimal():
            global rand_A, rand_B, rand_C
            rand_A = {random.randint(0, 255) for i in range(int(pow_A))}
            rand_B = {random.randint(0, 255) for i in range(int(pow_B))}
            rand_C = {random.randint(0, 255) for i in range(int(pow_C))}
            self.inserter("Множини створені успішно!\nВи можете переглянути їх в Вікні №2.", self.Info)
        else:
            self.inserter("Ви неправильно ввели дані!\nСпробуйте знову.", self.Info)

    def make_sets(self):
        hand_ent_A = self.ent_A.get()
        hand_ent_B = self.ent_B.get()
        hand_ent_C = self.ent_C.get()
        try:
            list_hand_A = hand_ent_A.split(",")
            list_hand_B = hand_ent_B.split(",")
            list_hand_C = hand_ent_C.split(",")

            def int_elem(arg):
                lst = []
                for i in arg:
                    i = int(i)
                    if i < 0 or i > 255:
                        continue
                    lst.append(i)
                return set(lst)

            global hand_A, hand_B, hand_C
            hand_A = int_elem(list_hand_A)
            hand_B = int_elem(list_hand_B)
            hand_C = int_elem(list_hand_C)
            self.inserter("Множини успішно створені!\nВи можете переглянути їх в Вікні №2.\n", self.Info2)
        except ValueError:
            self.inserter("Ви ввели неправильно елементи або роздільник!\nСпробуйте знову.\n", self.Info2)

    def universal_set(self):
        power_U = self.ent_U.get()
        if power_U.isdecimal():
            global U
            if max(max(hand_A), max(hand_B), max(hand_C)) < int(power_U):
                if int(power_U) <= 255:
                    U = set(range(int(power_U) + 1))
                    self.inserter("Універсальна множина успішно створена!", self.Info3)
                else:
                    self.inserter("Діапазон чисел має бути від 0 до 255!", self.Info3)
            else:
                self.inserter("В множинах А, В, С є елемент більше указаного значення!\nСпробуйте знову.", self.Info3)
        else:
            self.inserter("Ви неправильно ввели дані!\nСпробуйте знову.", self.Info3)

    def check2(self):
        if self.Info.get(0.0) == "М" and self.Info2.get(0.0) == "М" and self.Info3.get(0.0) == "У":
            self.menu2()
        else:
            self.inserter("Ви неправильно ввели дані!\nСпробуйте знову.", self.Info)
            self.inserter("Ви неправильно ввели дані!\nСпробуйте знову.", self.Info2)
            self.inserter("Ви неправильно ввели дані!\nСпробуйте знову.", self.Info3)

    def check3(self):
        if self.Info.get(0.0) == "М" and self.Info2.get(0.0) == "М" and self.Info3.get(0.0) == "У":
            self.menu3()
        else:
            self.inserter("Ви неправильно ввели дані!\nСпробуйте знову.", self.Info)
            self.inserter("Ви неправильно ввели дані!\nСпробуйте знову.", self.Info2)
            self.inserter("Ви неправильно ввели дані!\nСпробуйте знову.", self.Info3)

    def check4(self):
        if self.Info.get(0.0) == "М" and self.Info2.get(0.0) == "М" and self.Info3.get(0.0) == "У":
            self.menu4()
        else:
            self.inserter("Ви неправильно ввели дані!\nСпробуйте знову.", self.Info)
            self.inserter("Ви неправильно ввели дані!\nСпробуйте знову.", self.Info2)
            self.inserter("Ви неправильно ввели дані!\nСпробуйте знову.", self.Info3)

    def check5(self):
        if self.Info.get(0.0) == "М" and self.Info2.get(0.0) == "М" and self.Info3.get(0.0) == "У":
            self.menu5()
        else:
            self.inserter("Ви неправильно ввели дані!\nСпробуйте знову.", self.Info)
            self.inserter("Ви неправильно ввели дані!\nСпробуйте знову.", self.Info2)
            self.inserter("Ви неправильно ввели дані!\nСпробуйте знову.", self.Info3)

    def update_text_long(self):
        global A, B, C
        if self.favorite.get() == "Рандом":
            A, B, C = rand_A, rand_B, rand_C
        elif self.favorite.get() == "Вручную":
            A, B, C = hand_A, hand_B, hand_C

    def update_text_short(self):
        global A, B, C
        if self.favorite_short.get() == "Рандом":
            A, B, C = rand_A, rand_B, rand_C
        elif self.favorite_short.get() == "Вручную":
            A, B, C = hand_A, hand_B, hand_C

    def update_text_XY(self):
        global B, C
        if self.favorite_XY.get() == "Рандом":
            B, C = rand_B, rand_C
        elif self.favorite_XY.get() == "Вручную":
            B, C = hand_B, hand_C

    def show_sets_long(self):
        try:
            shown_A = ("A = %s" % (A))
            shown_B = ("B = %s" % (B))
            shown_C = ("C = %s" % (C))
            shown_sets = ("%s\n%s\n%s" % (shown_A, shown_B, shown_C))
            self.inserter(shown_sets, self.field_sets)
        except NameError:
            self.inserter("Ви не вибрали, котрі з множин будуть основними!", self.field_sets)

    def show_sets_short(self):
        try:
            shown_A = ("A = %s" % (A))
            shown_B = ("B = %s" % (B))
            shown_C = ("C = %s" % (C))
            shown_sets = ("%s\n%s\n%s" % (shown_A, shown_B, shown_C))
            self.inserter(shown_sets, self.field_sets_short)
        except NameError:
            self.inserter("Ви не вибрали, котрі з множин будуть основними!", self.field_sets)

    def show_oprtns_long(self):
        try:
            if self.btn_clicks1 == 0:
                self.D_long = "{0}∪{1}∪({2}∪{3})∪{4}\n".format((A | B), C, B, C, A)
                self.field_D.delete(0.0, END)
                self.field_D.insert(0.0, self.D_long)
                self.field_oprtn.delete(0.0, END)
                self.field_oprtn.insert(0.0, "(A ∪ B) = {0} ∪ {1} =\n= {2}".format(A, B, (A | B)))
                self.btn_clicks1 += 1

            elif self.btn_clicks1 == 1:
                self.D_long = "{0} ∪ {1} ∪ {2} ∪ {3}\n".format(((A | B) | C), B, C, A)
                self.field_D.delete(0.0, END)
                self.field_D.insert(0.0, self.D_long)
                self.field_oprtn.delete(0.0, END)
                self.field_oprtn.insert(0.0, "((A ∪ B) ∪ C) = {0} ∪ {1} =\n= {2}".format((A | B), C, ((A | B) | C)))
                self.btn_clicks1 += 1

            elif self.btn_clicks1 == 2:
                self.D_long = "{0}∪{1}∪{2}".format(((A | B) | C), (B | C), A)
                self.field_D.delete(0.0, END)
                self.field_D.insert(0.0, self.D_long)
                self.field_oprtn.delete(0.0, END)
                self.field_oprtn.insert(0.0, "(B∪C) = {0} ∪ {1} =\n= {2}".format(B, C, (C | B)))
                self.btn_clicks1 += 1

            elif self.btn_clicks1 == 3:
                self.D_long = "{0}∪{1}".format(((A | B) | C | (C | B)), A)
                self.field_D.delete(0.0, END)
                self.field_D.insert(0.0, self.D_long)
                self.field_oprtn.delete(0.0, END)
                self.field_oprtn.insert(0.0, "((A∪B)∪C∪(C∪B)) = {0} ∪ {1} =\n= {2}".format(((A | B) | C), (C | B),
                                                                                           ((A | B) | C | (C | B))))
                self.btn_clicks1 += 1

            elif self.btn_clicks1 == 4:
                self.D_long = "D = {0}".format((A | B) | C | (B | C) | A)
                self.field_D.delete(0.0, END)
                self.field_D.insert(0.0, self.D_long)
                self.field_oprtn.delete(0.0, END)
                self.field_oprtn.insert(0.0, "((A∪B)∪C∪(B∪C)∪A)= {0}∪{1} " \
                                             "=\n= {2}".format(((A | B) | C | (C | B)), A, ((A | B) | C | (C | B)) | A))
                self.btn_clicks1 += 1
            else:
                self.field_D.delete(0.0, END)
                self.field_oprtn.delete(0.0, END)
                self.btn_clicks1 = 0
        except NameError:
            self.inserter("Ви не вибрали, котрі з множин будуть основними!", self.field_sets)

    def show_oprtns_short(self):
        try:
            if self.btn_clicks2 == 0:
                self.D_short = "{0}∪{1}\n".format((A | B), C)
                self.field_D_short.delete(0.0, END)
                self.field_D_short.insert(0.0, self.D_short)
                self.field_oprtn_short.delete(0.0, END)
                self.field_oprtn_short.insert(0.0, "(A ∪ B) = {0} ∪ {1} =\n= {2}".format(A, B, (A | B)))
                self.btn_clicks2 += 1

            elif self.btn_clicks2 == 1:
                self.D_short = "{0} = {1}".format("D", ((A | B) | C))
                self.field_D_short.delete(0.0, END)
                self.field_D_short.insert(0.0, self.D_short)
                self.field_oprtn_short.delete(0.0, END)
                self.field_oprtn_short.insert(0.0, "(A ∪ B) ∪ C = {0} ∪ {1} =\n= {2}".format((A | B), C, ((A | B) | C)))
                self.btn_clicks2 += 1

            else:
                self.field_D_short.delete(0.0, END)
                self.field_oprtn_short.delete(0.0, END)
                self.btn_clicks2 = 0
        except NameError:
            self.inserter("Ви не вибрали, котрі з множин будуть основними!", self.field_sets_short)

    def save_long(self):
        try:
            if self.field_D.get(0.0) == "D":
                with open(r"C:\Komisarow_Dis_Math\file_long.txt", 'w') as f:
                    f.write(str(self.D_long) + "\n")
                self.inserter(
                    "Значення записано в файл file_long.txt!\nВи можете переглянути його в папці 'C:\Komisarow_Dis_Math'.",
                    self.Info4)
            else:
                self.inserter("Це не кінцевий результат!\nВи можете виконувати операції далі.", self.Info4)
        except NameError:
            self.inserter("Ви не вибрали, котрі з множин будуть основними!", self.field_sets)

    def save_short(self):
        try:
            if self.field_D_short.get(0.0) == "D":
                with open(r"C:\Komisarow_Dis_Math\file_short.txt", 'w') as f:
                    f.write(str(self.D_short))
                self.inserter(
                    "Значення записано в файл file_short.txt!\nВи можете переглянути його в папці 'C:\Komisarow_Dis_Math'.",
                    self.Info4_short)
            else:
                self.inserter("Це не кінцевий результат!\nВи можете виконувати операції далі.", self.Info4_short)
        except NameError:
            self.inserter("Ви не вибрали, котрі з множин будуть основними!", self.field_sets_short)

    def save_Z(self):
        try:
            if self.field_Z.get(0.0) == "Z":
                with open(r"C:\Komisarow_Dis_Math\file_Z.txt", 'w') as f:
                    f.write("Z = %s" % (self.Z))
                self.inserter(
                    "Значення записано в файл file_Z.txt!\nВи можете переглянути його в папці 'C:\Komisarow_Dis_Math'.",
                    self.Info4_Z)
            else:
                self.inserter("Це не кінцевий результат!\nВи можете виконувати операції далі.", self.Info4_Z)
        except NameError:
            self.inserter("Ви не вибрали, котрі з множин будуть основними!", self.field_Z)

    def show_X_and_Y(self):
        try:
            global X, Y
            X = B
            Y = U - C
            shown_X = ("X = %s" % (X))
            shown_Y = ("Y = %s" % (Y))
            self.inserter("{0}\n{1}".format(shown_X, shown_Y), self.field_X_and_Y)
        except NameError:
            self.inserter("Ви не вибрали, котрі з множин будуть основними!", self.field_X_and_Y)

    def X_sym_dif_Y(self):
        try:
            self.Z = sym_dif(X, Y)
            self.inserter("Z = {0}".format(self.Z), self.field_Z)
        except NameError:
            self.inserter("Ви не вибрали, котрі з множин будуть основними!", self.field_X_and_Y)

    def X_stdsymdif_Y(self):
        try:
            self.Z2 = X ^ Y
            self.inserter("Z = {0}".format(self.Z2), self.field_std_Z)
        except NameError:
            self.inserter("Ви не вибрали, котрі з множин будуть основними!", self.field_std_Z)
        except TypeError:
            self.inserter("Ви не вибрали, котрі з множин будуть основними!", self.field_std_Z)

    def load_long(self):
        try:
            with open(r"C:\Komisarow_Dis_Math\file_long.txt", 'r') as f:
                txt = f.read()
            self.inserter(txt, self.field_load_long)
        except NameError:
            self.inserter("Множини D не існує!\nПеревірте чи виконали ви операції та записали в файл. ",
                          self.field_load_long)

    def load_short(self):
        try:
            with open(r"C:\Komisarow_Dis_Math\file_short.txt", 'r') as f:
                txt = f.read()
            self.inserter(txt, self.field_load_short)
        except NameError:
            self.inserter("Множини D не існує!\nПеревірте чи виконали ви операції та зписали в файл. ",
                          self.field_load_short)

    def load_Z(self):
        try:
            with open(r"C:\Komisarow_Dis_Math\file_Z.txt", 'r') as f:
                txt = f.read()
            self.inserter(txt, self.field_load_Z)
        except NameError:
            self.inserter("Множини Z не існує!\nПеревірте чи виконали ви операції та зписали в файл. ",
                          self.field_load_Z)

    def comparison(self):
        try:
            if self.D_long == self.D_short:
                self.inserter("Результати 1,2 однакові", self.field_comparison1)
            else:
                self.inserter("Результати 1,2 різні.\nСкоріше за все ви вибрали в пункті 3 інші множини.",
                              self.field_comparison1)
            if self.Z == self.Z2:
                self.inserter("Результати 3,4 однакові", self.field_comparison2)
            else:
                self.inserter("Результати 3,4 різні.\nСкоріше за все ви вибрали в пункті 4 інші множини.",
                              self.field_comparison2)
        except AttributeError:
            self.inserter("Ви не виконали операції в Меню №2 або Меню№3!", self.field_comparison2)


root = Tk()
root.geometry('800x650')
root.title("Main task1")
root.resizable(False, False)
Main()
root.mainloop()
