from itertools import product, compress
from math import *
from functools import reduce


def calc_rel(n: int, topology_file: str, probs: list):
    table = get_table(topology_file, n)
    results = get_bin_paths(table)
    all_working_states = []
    for path in results:
        all_states = product(range(2), repeat=len(table) - 2)
        for state in all_states:
            mask = int("".join(list(map(lambda x: str(x), state))), 2)
            if path & mask == path:
                all_working_states.append(mask)
    all_working_states = list(set(all_working_states))
    return calc_and_print(all_working_states, probs, results, table)


def calc_and_print(all_working_states, probs, results, table):
    sys_prob = 0
    elements = [f'E{i}' for i in range(1, len(table) - 1)]
    print("\nAll possible paths:")
    for path in results:
        filtered = list(map(lambda x: int(x), list(bin(path)[2:])))
        new_path = compress(elements, filtered)
        print(" -> ".join(new_path))
    title = "| " + " | ".join([f'E{i}' for i in range(1, len(table) - 1)] + [""]) + "P".center(10) + "|"
    print("\nAll working states and their probabilities:")
    print(title)
    for state in all_working_states:
        binary = bin(state)[2:]
        binary_state = list(binary.rjust(len(table) - 2, '0'))
        element_probs = [p if binary_state[i] == '1' else 1 - p for i, p in enumerate(probs)]
        state_prob = reduce(lambda x, y: x * y, element_probs)
        print('-' * len(title))
        print("| " + "  | ".join(binary_state + [""]) + f"{round(state_prob, 6)}".center(10) + "|")
        sys_prob += state_prob
    return sys_prob


def get_bin_paths(table):
    start_elem_indexes = [i for i in range(len(table[0])) if table[0][i] == 1]
    states = [int('1' + '0' * (elem - 1) + '1' + '0' * ((len(table) - 1) - elem), 2) for elem in start_elem_indexes]
    results = []
    while len(start_elem_indexes) != 0:
        new_neighbours = []
        new_states = []
        start = 0
        for index, i in enumerate(start_elem_indexes):
            new_neighbours += [j for j in range(len(table[i])) if table[i][j] == 1 and bin(states[index])[2:][j] == '0']
            new_states += [states[index] + int("0" * elem + "1" + "0" * ((len(table) - 1) - elem), 2)
                           for elem in new_neighbours[start:]]
            start = len(new_neighbours)
        states = new_states
        start_elem_indexes = new_neighbours
        results += [states[i] for i in range(len(start_elem_indexes)) if start_elem_indexes[i] == len(table) - 1]
        states = [state for state in states if state not in results]
        start_elem_indexes = [ix for ix in start_elem_indexes if ix != len(table) - 1]
    results = [int(bin(elem)[2:][1: -1], 2) for elem in results]
    return results


def get_table(topology_file: str, n: int):
    with open(topology_file, 'r') as data:
        table = data.readlines()[1:]
        table = [list(map(lambda x: int(x), elem.rstrip("\n").split(", "))) for elem in table]
    if len(table) - 2 != n:
        raise Exception("Incorrect topology!")

    return table


def get_probs(n, probs=None):
    if probs is not None:
        return probs
    while True:
        input_probs = []
        for prob in input("Input probabilities: ").strip().split(" "):
            try:
                x = float(prob)
            except ValueError:
                print("Incorrect input! Try again")
                continue
            if 1 > x > 0:
                input_probs.append(x)
        if len(input_probs) == n:
            probs = input_probs
            break
        else:
            print("Incorrect input! Try again")

    return probs


def calc_Q_and_T(prob):
    sys_Q = 1 - sys_prob
    sys_T = - T / log(prob)
    print(f"Q of system: {sys_Q}")
    print(f"T of system: {sys_T}")
    return sys_Q, sys_T


def calc_4_general_reserv():
    res_Q1 = 1 / factorial(K + 1) * sys_Q
    res_P1 = 1 - res_Q1
    ref_T1 = -T / log(res_P1)
    print(f"Prob of reserved system: {res_P1}")
    print(f"Q of reserved system: {res_Q1}")
    print(f"T of reserved system: {ref_T1}")
    G_p1 = res_P1 / sys_prob
    G_q1 = res_Q1 / sys_Q
    G_t1 = ref_T1 / sys_T
    print(f"G_p1: {G_p1}")
    print(f"G_q1: {G_q1}")
    print(f"G_t1: {G_t1}")


def calc_4_separate_reserv():
    new_probs = [1 - pow(1 - p, K + 1) for p in probs]
    res_P2 = calc_rel(N, "topology.txt", new_probs)
    res_Q2 = 1 - res_P2
    res_T2 = -T / log(res_P2)
    print(f"Prob of reserved system: {res_P2}")
    print(f"Q of reserved system: {res_Q2}")
    print(f"T of reserved system: {res_T2}")
    G_p2 = res_P2 / sys_prob
    G_q2 = res_Q2 / sys_Q
    G_t2 = res_T2 / sys_T
    print(f"G_p2 = {G_p2}")
    print(f"G_q2 = {G_q2}")
    print(f"G_t2 = {G_t2}")


N = int(input("Input number of elements: "))
probs = get_probs(8, [0.5, 0.6, 0.7, 0.8, 0.85, 0.9, 0.92, 0.94])
T = int(input("Input time: "))
K = int(input("Input K: "))

sys_prob = calc_rel(N, "topology.txt", probs)

sys_Q, sys_T = calc_Q_and_T(sys_prob)

calc_4_general_reserv()
print("\n")
calc_4_separate_reserv()
