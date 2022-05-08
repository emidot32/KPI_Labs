import lab2
from math import log, factorial

possibilities = [0.9, 0.69, 0.92, 0.57, 0.6, 0.51, 0.55, 0.69]
linkMatrix = [[0, 1, 1, 0, 0, 0, 0, 0, 0, 0],
              [0, 0, 0, 1, 0, 1, 0, 0, 0, 0],
              [0, 0, 0, 0, 1, 0, 0, 1, 0, 0],
              [0, 0, 0, 0, 1, 1, 0, 0, 0, 0],
              [0, 0, 0, 1, 0, 0, 0, 1, 0, 0],
              [0, 0, 0, 0, 0, 0, 1, 0, 0, 0],
              [0, 0, 0, 0, 0, 1, 0, 0, 0, 1],
              [0, 0, 0, 0, 0, 0, 0, 0, 1, 0],
              [0, 0, 0, 0, 0, 0, 0, 1, 0, 1],
              [0, 0, 0, 0, 0, 0, 0, 0, 0, 0]]

# possibilities = [0.81, 0.62, 0.53, 0.78, 0.88, 0.92]
# linkMatrix = [[0, 0, 1, 1, 1, 0],
#               [0, 0, 1, 1, 0, 1],
#               [0, 0, 0, 1, 1, 1],
#               [0, 0, 0, 0, 1, 1],
#               [0, 0, 0, 0, 0, 0],
#               [0, 0, 0, 0, 0, 0]]

'''possibilities = [0.91, 0.87, 0.66, 0.93, 0.91, 0.72, 0.67, 0.59]
linkMatrix = [[0, 1, 1, 0, 0, 0, 0, 0],
            [0, 0, 0, 1, 1, 1, 0, 0],
            [0, 0, 0, 1, 1, 0, 1, 0],
            [0, 0, 0, 0, 1, 1, 1, 0],
            [0, 0, 0, 0, 0, 1, 1, 0],
            [0, 0, 0, 0, 0, 0, 0, 1],
            [0, 0, 0, 0, 0, 0, 0, 1],
            [0, 0, 0, 0, 0, 0, 0, 0]]'''

'''possibilities = [0.74, 0.14, 0.56, 0.35, 0.20, 0.21]
linkMatrix = [[0, 0, 1, 1, 1, 0],
              [0, 0, 1, 1, 0, 1],
              [0, 0, 0, 1, 1, 1],
              [0, 0, 0, 0, 1, 1],
              [0, 0, 0, 0, 0, 0],
              [0, 0, 0, 0, 0, 0]]'''
t = 87
k1 = 3
k2 = 3

possibility = lab2.lab2(possibilities, linkMatrix)
qssibility = 1 - possibility
t_average = -t / log(possibility)
print("Базова імовірність безвідмовної роботи = {}\n"
      "Базова імовірність відмови = {}\n"
      "Базовий середній наробіток на відмову = {}\n".format(possibility, qssibility, t_average))


def loaded_joint(t, k, qssib, possib, t_aver):
    # q_1 = 1 / factorial(k + 1) * qssib
    q_1 = pow(qssib, (k + 1))
    p_1 = 1 - q_1
    t_aver1 = -t / log(p_1)
    g_q1 = q_1 / qssib
    g_p1 = p_1 / possib
    g_t1 = t_aver1 / t_aver
    print("Імовірність безвідмовної роботи системи з навантаженим загальним резервуванням = {}\n"
          "Імовірність відмови системи з навантаженим загальним резервуванням = {}\n"
          "Середній час роботи системи з навантаженим загальним резервуванням = {}".format(p_1, q_1, t_aver1))
    print("Виграш системи з навантаженим загальним резервуванням по імовірності безвідмовної роботи = {}\n"
          "Виграш системи з навантаженим загальним резервуванням по імовірності відмови = {}\n"
          "Виграш системи з навантаженим загальним резервуванням по середньому часу роботи = {}\n".format(g_p1, g_q1,
                                                                                                          g_t1))


def not_loaded_joint(t, k, qssib, possib, t_aver):
    q_1 = qssib / factorial(k + 1)
    p_1 = 1 - q_1
    t_aver1 = -t / log(p_1)
    g_q1 = q_1 / qssib
    g_p1 = p_1 / possib
    g_t1 = t_aver1 / t_aver
    print("Імовірність безвідмовної роботи системи з ненавантаженим загальним резервуванням = {}\n"
          "Імовірність відмови системи з ненавантаженим загальним резервуванням = {}\n"
          "Середній час роботи системи з ненавантаженим загальним резервуванням = {}".format(p_1, q_1, t_aver1))
    print("Виграш системи з ненавантаженим загальним резервуванням по імовірності безвідмовної роботи = {}\n"
          "Виграш системи з ненавантаженим загальним резервуванням по імовірності відмови = {}\n"
          "Виграш системи з ненавантаженим загальним резервуванням по середньому часу роботи = {}\n".format(g_p1, g_q1,
                                                                                                            g_t1))


def loaded_distributed(t, k, qssib, poss, possib, t_aver, link_mat):
    new_possibilities = list(map(lambda x: 1 - (1 - x) ** (k + 1), possib))
    p_2 = lab2.lab2(new_possibilities, link_mat)
    q_2 = 1 - p_2
    t_aver2 = -t / log(p_2)
    g_q2 = q_2 / qssib
    g_p2 = p_2 / poss
    g_t2 = t_aver2 / t_aver
    print("Імовірність безвідмовної роботи системи з навантаженим розподіленим резервуванням = {}\n"
          "Імовірність відмови системи з навантаженим розподіленим резервуванням = {}\n"
          "Середній час роботи системи з навантаженим розподіленим резервуванням = {}".format(p_2, q_2, t_aver2))
    print("Виграш системи з навантаженим розподіленим резервуванням по імовірності безвідмовної роботи = {}\n"
          "Виграш системи з навантаженим розподіленим резервуванням по імовірності відмови = {}\n"
          "Виграш системи з навантаженим розподіленим резервуванням по середньому часу роботи = {}\n".format(g_p2, g_q2,
                                                                                                             g_t2))


def not_loaded_distributed(t, k, qssib, poss, possib, t_aver, link_mat):
    new_possibilities = list(map(lambda x: 1 - (1 - x) / factorial(k + 1), possib))
    p_2 = lab2.lab2(new_possibilities, link_mat)
    q_2 = 1 - p_2
    t_aver2 = -t / log(p_2)
    g_q2 = q_2 / qssib
    g_p2 = p_2 / poss
    g_t2 = t_aver2 / t_aver
    print("Імовірність безвідмовної роботи системи з ненавантаженим розподіленим резервуванням = {}\n"
          "Імовірність відмови системи з ненавантаженим розподіленим резервуванням = {}\n"
          "Середній час роботи системи з ненавантаженим розподіленим резервуванням = {}".format(p_2, q_2, t_aver2))
    print("Виграш системи з ненавантаженим розподіленим резервуванням по імовірності безвідмовної роботи = {}\n"
          "Виграш системи з ненавантаженим розподіленим резервуванням по імовірності відмови = {}\n"
          "Виграш системи з ненавантаженим розподіленим резервуванням по середньому часу роботи = {}\n".format(g_p2,
                                                                                                               g_q2,
                                                                                                               g_t2))


loaded_joint(t, k1, qssibility, possibility, t_average)
not_loaded_joint(t, k1, qssibility, possibility, t_average)
loaded_distributed(t, k2, qssibility, possibility, possibilities, t_average, linkMatrix)
not_loaded_distributed(t, k2, qssibility, possibility, possibilities, t_average, linkMatrix)
