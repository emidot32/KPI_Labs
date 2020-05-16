import numpy as np

a0 = 7
a1 = 1
a2 = 0
a3 = 8
plan_matrix = np.floor(20 * np.random.random((8, 3)))
Y = np.array([a0 + a1 * plan_matrix[i, 0] + a2 * plan_matrix[i, 1] + a3 * plan_matrix[i, 2] for i in range(len(plan_matrix))])
X0 = np.array([((np.max(plan_matrix[:, i]) + np.min(plan_matrix[:, i])) / 2) for i in range(len(plan_matrix[0]))])
Y_reference = a0 + a1 * X0[0] + a2 * X0[1] + a3 * X0[2]
interval_of_change = np.array([(np.max(plan_matrix[:, i]) - X0[i]) for i in range(len(plan_matrix[0]))])
X_norm = np.array([[round((plan_matrix[i, j] - X0[j]) / interval_of_change[j], 3) for j in range(len(plan_matrix[i]))] for i in
                   range(len(plan_matrix))])
diffs = sorted([i for i in sorted(Y) if i>Y_reference])[0]
evaluation_criterion = list(Y).index(diffs)
optionY = Y[evaluation_criterion]
optionX = plan_matrix[evaluation_criterion]
print("Матриця плану експерименту: \n", plan_matrix)
print("Значення функції відгуку: ", Y)
print("Значення нульових рівнів факторів: ", X0)
print("Эталонне значення Y: ", Y_reference)
print("Нормовані значення Х: \n", X_norm)
print("Середнє значення Y: ", np.mean(Y))
print("Значення функції відгуку, що задовольняє критерію оціновання: ", optionY)
print("Точка експерименту, що задавольняє критерію оцінювання: ", optionX)
