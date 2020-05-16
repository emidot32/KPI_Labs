import numpy as np
import sys

"""Функция для составления нормированого плана эксперимента"""


def make_norm_plan_matrix(plan_matrix, matrix_of_min_and_max_x):
    X0 = np.array(
        [((matrix_of_min_and_max_x[i, 0] + matrix_of_min_and_max_x[i, 1]) / 2) for i in range(len(plan_matrix[0]))])
    interval_of_change = np.array([(matrix_of_min_and_max_x[i, 1] - X0[i]) for i in range(len(plan_matrix[0]))])
    X_norm = np.array(
        [[round((plan_matrix[i, j] - X0[j]) / interval_of_change[j], 3) for j in range(len(plan_matrix[i]))]
         for i in range(len(plan_matrix))])
    return X_norm


"""Подготовка матриц факторов и откликов с случайными числами"""
matrix_with_min_max_x = np.array([[-20, 15], [10, 60]])
m = 6
N = 3
plan_matr = np.array([np.random.randint(-20, 15, size=N), np.random.randint(10, 60, size=N)]).T
norm_matrix = make_norm_plan_matrix(plan_matr, matrix_with_min_max_x)
Y_matrix = np.random.randint(10 * (20 - 9), 10 * (30 - 9), size=(N, m))
print("Матриця плану експерименту: \n", plan_matr)
print("Нормована матриця: \n", norm_matrix)
print("Матриця відгуків: \n", Y_matrix)

"""Проверка на критерий Романовского"""
mean_Y: np.array = [np.mean(Y_matrix[i]) for i in range(len(Y_matrix))]
dispersion_Y = [np.sum([(Y_matrix[i, j] - mean_Y[i]) ** 2 for j in range(m)]) / np.size(Y_matrix[i])
                for i in range(len(Y_matrix))]
sigma = np.sqrt((2 * (2 * m - 2)) / (m * (m - 4)))
R: list = []
index = -1
for i in range(len(dispersion_Y)):
    for j in range(len(dispersion_Y)):
        if i > j:
            if dispersion_Y[i] >= dispersion_Y[j]:
                R.append(abs((m - 2) * dispersion_Y[i] / (m * dispersion_Y[j]) - 1) / sigma)
            else:
                R.append(abs((m - 2) * dispersion_Y[j] / (m * dispersion_Y[i]) - 1) / sigma)
            index += 1
            if R[index] > 2.16:
                print("Дисперсія неоднорідна, спробуйте знову!")
                sys.exit()
print("Середні значення У: ", mean_Y)

"""Нахождение коэффициентов"""
mx1 = np.sum(norm_matrix[:, 0]) / N
mx2 = np.sum(norm_matrix[:, 1]) / N
my = np.sum(mean_Y) / N
a1 = np.sum(list(map(lambda x: x ** 2, norm_matrix[:, 0]))) / N
a2 = np.sum(norm_matrix[:, 0] * norm_matrix[:, 1]) / N
a3 = np.sum(list(map(lambda x: x ** 2, norm_matrix[:, 1]))) / N
a11 = np.sum(norm_matrix[:, 0] * mean_Y) / N
a22 = np.sum(norm_matrix[:, 1] * mean_Y) / N
norm_matrix = np.insert(norm_matrix, 0, 1, axis=1)
print(norm_matrix)
b = np.linalg.lstsq(norm_matrix, mean_Y, rcond=None)[0]
print(b)
check1 = [(np.sum(b * norm_matrix[i])) for i in range(len(norm_matrix))]
print("Нормовані коефіціенти: ", b)
print("Перевірка 1: ", check1)

"""Натурализация коэффициентов"""
deltaX = [abs(matrix_with_min_max_x[i, 1] - matrix_with_min_max_x[i, 0]) / 2 for i in range(2)]
X0 = [(matrix_with_min_max_x[i, 1] + matrix_with_min_max_x[i, 0]) / 2 for i in range(2)]
a = np.array([b[0] - b[1] * X0[0] / deltaX[0] - b[2] * X0[1] / deltaX[1], b[1] / deltaX[0], b[2] / deltaX[1]])
check2 = [(a[0] + np.sum(a[1:3] * plan_matr[i])) for i in range(len(plan_matr))]
print("Натуралізовані коефіціенти: ", a)
print("Перевірка 2: ", check2)
