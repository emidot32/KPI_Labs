from tkinter import *
import networkx as nx
import pylab as pl
from random import randint


class Main:
    A = ["a1", "a2", "a3", "а4", "a5"]
    B = ["b1", "b2", "b3", "b4", "b5"]
    relation = [["a1", "b2"], ["a3", "b1"], ["a2", "b5"], ["a1", "b4"], ["a5", "b3"]]

    def __init__(self, menu):
        self.menu = menu
        self.menu.title("Main task1")
        self.menu.geometry("400x500")
        self.menu.resizable(0, 0)

        self.btn_generator = Button(self.menu, text="Сгенерировать матрицу смежности", command=self.generator)
        self.btn_generator.place(x=20, y=10)
        self.btn_graph = Button(self.menu, text="Создать граф", command=self.graph)
        self.btn_graph.place(x=20, y=200)
        self.btn_gen_some_matrix = Button(self.menu, text="Создать матрицу", command=self.gen_matrix)
        self.btn_gen_some_matrix.place(x=20, y=230)
        self.menu.mainloop()

    def generator(self):
        C = []
        for i in range(len(self.A)):
            c = []
            for j in range(len(self.B)):
                flag = False
                for h in range(len(self.relation)):
                    if self.A[i] is self.relation[h][0] and self.B[j] is self.relation[h][1]:
                        flag = True
                if flag:
                    c.append(1)
                else:
                    c.append(0)
            C.append(c)

        for i in range(len(self.A)):
            Label(self.menu, text="%2s" % (self.A[i]), font="Arial 12").place(x=20, y=50+i*25+10)
        for i in range(len(self.B)):
            Label(self.menu, text="%2s" % (self.B[i]), font="Arial 12").place(x=40+i*25+10, y=40)
        for i in range(len(C)):
            for j in range(len(C[i])):
                Label(self.menu, text="%3s" % C[i][j], bg="white", font="Arial 11", height=0).place(x=40+j*25+10, y=50+i*25+10)

    def graph(self):
        g = nx.DiGraph()
        g.add_nodes_from(self.A + self.B)
        g.add_edges_from(self.relation)
        nx.draw_networkx(g, pos=nx.circular_layout(g), with_labels=True, edges=g.edges())
        pl.show()

    def gen_matrix(self):
        self.list_ent = [[] for j in range(8)]
        for i in range(8):
            for j in range(8):
                self.list_ent[i].append(Entry(self.menu, font='Arial 14', width=2))
                self.list_ent[i][j].place(x=30+j*30+10, y=250+i*30+10)
        tabl = [[randint(0, 1) for i in range(8)] for j in range(8)]
        for i in range(8):
            for j in range(8):
                self.list_ent[i][j].insert(END, tabl[i][j])


root = Tk()
Main(root)
