import numpy as np
import sys

gt = {1: 0.9065, 2: 0.7679, 3: 0.6841, 4: 0.6287, 5: 0.5892, 6: 0.5598, 7: 0.5365, 8: 0.5175, 9: 0.5017, 10: 0.4884}
tt = {4: 2.776, 8: 2.306, 12: 2.179, 16: 2.120, 20: 2.086, 24: 2.064, 28: 2.048}
ft = {1: {4: 7.7, 8: 5.3, 12: 4.8, 16: 4.5, 20: 4.4, 24: 4.3, 28: 4.2},
      2: {4: 6.9, 8: 4.5, 12: 3.9, 16: 3.6, 20: 3.5, 24: 3.4, 28: 3.3},
      3: {4: 6.6, 8: 4.1, 12: 3.5, 16: 3.2, 20: 3.1, 24: 3.0, 28: 3.0},
      4: {4: 6.4, 8: 3.8, 12: 3.3, 16: 3.0, 20: 2.9, 24: 2.8, 28: 2.7},
      5: {4: 6.3, 8: 3.7, 12: 3.1, 16: 2.9, 20: 2.7, 24: 2.6, 28: 2.6},
      6: {4: 6.2, 8: 3.6, 12: 3.0, 16: 2.7, 20: 2.6, 24: 2.5, 28: 2.4}}


def make_norm_plan_matrix(plan_matrix, matrix_of_min_and_max_x):
    X0 = np.mean(matrix_with_min_max_x, axis=1)
    interval_of_change = np.array([(matrix_of_min_and_max_x[i, 1] - X0[i]) for i in range(len(plan_matrix[0]))])
    X_norm = np.array(
        [[round((plan_matrix[i, j] - X0[j]) / interval_of_change[j], 3) for j in range(len(plan_matrix[i]))]
         for i in range(len(plan_matrix))])
    return X_norm


def cochran_check(Y_matrix):
    mean_Y = np.mean(Y_matrix, axis=1)
    dispersion_Y = np.mean((Y_matrix.T - mean_Y) ** 2, axis=0)
    Gp = np.max(dispersion_Y) / (np.sum(dispersion_Y))
    return Gp < gt[m-1]


def students_t_test(norm_matrix, Y_matrix):
    mean_Y = np.mean(Y_matrix, axis=1)
    dispersion_Y = np.mean((Y_matrix.T - mean_Y) ** 2, axis=0)
    mean_dispersion = np.mean(dispersion_Y)
    sigma = np.sqrt(mean_dispersion / (N * m))
    betta = np.mean(norm_matrix.T * mean_Y, axis=1)
    t = np.abs(betta) / sigma
    return np.where(t > tt[(m-1)*N])


def phisher_criterion(Y_matrix, d):
    if d == N:
        return False
    Sad = m / (N - d) * np.mean(check1 - mean_Y)
    mean_dispersion = np.mean(np.mean((Y_matrix.T - mean_Y) ** 2, axis=0))
    Fp = Sad / mean_dispersion
    return Fp < ft[N - d][(m - 1) * N]


matrix_with_min_max_x = np.array([[-20, 15], [10, 60], [15, 35]])
m = 6
N = 4
plan_matr = np.array(
    [np.random.randint(-20, 15, size=N), np.random.randint(10, 60, size=N), np.random.randint(15, 35, size=N)]).T
norm_matrix = make_norm_plan_matrix(plan_matr, matrix_with_min_max_x)
plan_matr = np.insert(plan_matr, 0, 1, axis=1)
norm_matrix = np.insert(norm_matrix, 0, 1, axis=1)
Y_matrix = np.random.randint(20 + np.mean(matrix_with_min_max_x, axis=0)[0],
                             20 + np.mean(matrix_with_min_max_x, axis=0)[1], size=(N, m))
mean_Y = np.mean(Y_matrix, axis=1)
if cochran_check(Y_matrix):
    b_natura = np.linalg.lstsq(plan_matr, mean_Y, rcond=None)[0]
    b_norm = np.linalg.lstsq(norm_matrix, mean_Y, rcond=None)[0]
    check1 = np.sum(b_natura * plan_matr, axis=1)
    check2 = np.sum(b_norm * norm_matrix, axis=1)
    indexes = students_t_test(norm_matrix, Y_matrix)
    print("Матриця плану експерименту: \n", plan_matr)
    print("Нормована матриця: \n", norm_matrix)
    print("Матриця відгуків: \n", Y_matrix)
    print("Середні значення У: ", mean_Y)
    print("Натуралізовані коефіціенти: ", b_natura)
    print("Нормовані коефіціенти: ", b_norm)
    print("Перевірка 1: ", check1)
    print("Перевірка 2: ", check2)
    print("Індекси коефіціентів, які задовольняють критерію Стьюдента: ", np.array(indexes)[0])
    print("Критерій Стьюдента: ", np.sum(b_natura[indexes] * np.reshape(plan_matr[:, indexes], (N, np.size(indexes))), axis=1))
    if phisher_criterion(Y_matrix, np.size(indexes)):
        print("Рівняння регресії адекватно оригіналу.")
    else:
        print("Рівняння регресії неадекватно оригіналу.")
else:
    print("Дисперсія неоднорідна!")
