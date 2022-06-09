"""Placement of ships in a sea battle, taking into account the gaps between them.
The algorithm is parallel."""
import copy
import itertools
import multiprocessing as mp
import time

PROCESSES = 3
flotilla = [4, 3, 3, 2, 2, 2, 1, 1, 1, 1]


def get_all_appropriate_ships(horizontal_space_numbers: dict, vertical_space_numbers: dict):
    """Gets part of appropriate placements of ships on a sea
    from all processes and returns all ones"""
    start = time.time()
    pipes, processes = create_processes_and_pipes(horizontal_space_numbers, vertical_space_numbers)
    hor_rows = []
    ver_rows = []
    for i in range(PROCESSES):
        rows = pipes[i].recv()
        hor_rows.append(rows[0])
        ver_rows.append(rows[1])
    product = list(itertools.product(*hor_rows, *ver_rows))
    for i in range(PROCESSES):
        pipes[i].send(product[i * len(product) // PROCESSES: (i + 1) * len(product) // PROCESSES])
    all_appropriate_ships = []
    for i in range(PROCESSES):
        appr_ships = pipes[i].recv()
        if appr_ships:
            all_appropriate_ships += appr_ships
    for process in processes:
        process.join()
    print(f"Time spent: {time.time()-start}")
    return all_appropriate_ships


def create_processes_and_pipes(horizontal_space_numbers, vertical_space_numbers):
    """Creation of processes and pipes. Runs processes and returns pipes"""
    pipes = []
    processes = []
    for j in range(PROCESSES):
        pipe = mp.Pipe()
        process = mp.Process(target=common_process, name=f"{j}",
                             args=(horizontal_space_numbers, vertical_space_numbers, pipe[0],))
        pipes.append(pipe[1])
        processes.append(process)
    for process in processes:
        process.start()
    return pipes, processes


def common_process(hor_space_numbers: dict, ver_space_numbers: dict, pipe_to_main):
    """Common logic for 3 processes"""
    process_num = int(mp.current_process().name)
    pipe_to_main.send([get_variants(list(hor_space_numbers.items())[process_num][1], flotilla),
                       get_variants(list(ver_space_numbers.items())[process_num][1], flotilla)])
    product_part = pipe_to_main.recv()
    appropriate_seas = get_appropriate_ships_part(
        product_part, hor_space_numbers, ver_space_numbers)
    pipe_to_main.send(appropriate_seas)
    pipe_to_main.close()


def get_appropriate_ships_part(product_part: list, hor_space_nums: dict, ver_space_nums: dict):
    """Gets part of appropriate placements of ships on a sea"""
    seas = []
    all_absent_ships = []
    all_free_1 = []
    for cross in product_part:
        if is_appropriate(
                cross, list(hor_space_nums.keys()), list(ver_space_nums.keys())):
            sea = create_sea(cross, list(hor_space_nums.keys()), list(ver_space_nums.keys()))
            check, absent_ships, free_1 = check_sea(
                sea, list(hor_space_nums.keys()), list(ver_space_nums.keys()), flotilla)
            if sea not in seas and check:
                seas.append(sea)
                all_absent_ships.append(absent_ships)
                all_free_1.append(free_1)
    all_appropriate_seas = []
    for index, sea_ in enumerate(seas):
        for extended_sea in \
                add_absent_ships(sea_, all_absent_ships[index], all_free_1[index]):
            all_appropriate_seas.append(extended_sea)
    return all_appropriate_seas


def add_absent_ships(sea, absent_ships_dict: dict, free_1):
    """Add ships that absent in conforming sea."""
    absent_ships = get_absent_ships_list(absent_ships_dict, False)

    product = itertools.product(absent_ships, free_1)
    seas = []
    for ship_to_point1 in product:
        sea_copy = copy.deepcopy(sea)
        absent_ships_copy = absent_ships_dict.copy()
        free_1_copy = copy.deepcopy(free_1)
        if append_if_possible(sea_copy, ship_to_point1, absent_ships_copy, free_1_copy):
            for ship_to_point2 in product:
                if ship_to_point1[1] != ship_to_point2[1] \
                        and absent_ships_copy[ship_to_point2[0]] > 0:
                    append_if_possible(sea_copy, ship_to_point2, absent_ships_copy, free_1_copy)
            for _ in range(len(free_1_copy)):
                absent_ships_copy[1] -= 1
            append_instead_zero(sea_copy, absent_ships_copy)
            is_all_ships_appended = True
            for ship in absent_ships_copy:
                if absent_ships_copy[ship] > 0:
                    is_all_ships_appended = False
                    break
            if is_all_ships_appended:
                seas.append(sea_copy)
    return seas


def is_appropriate(cross, hor_space_keys, ver_space_keys):
    """Checks cross of rows and columns"""
    hor_space_len, ver_space_len = len(hor_space_keys), len(ver_space_keys)
    appropriate = True
    for i, hor in enumerate(cross[0:hor_space_len]):
        for j, ver in enumerate(cross[hor_space_len:hor_space_len + ver_space_len]):
            if hor[ver_space_keys[j]] != ver[hor_space_keys[i]]:
                appropriate = False
                break
    return appropriate


def create_sea(cross, hor_space_keys, ver_space_keys):
    """Create appropriate sea"""
    hor_space_len, ver_space_len = len(hor_space_keys), len(ver_space_keys)
    sea = [[0 for _ in range(0, 9)] for _ in range(0, 9)]
    for i, hor in enumerate(cross[0:hor_space_len]):
        sea[hor_space_keys[i]] = hor
    for i in range(9):
        for j, ver in enumerate(cross[hor_space_len:hor_space_len + ver_space_len]):
            sea[i][ver_space_keys[j]] = ver[i]
    return sea


def get_variants(space_list, ships):
    """Get all variants for 1 spaces"""
    variants = []
    appr_ships = [s for s in ships if s <= 9 - sum(space_list)]
    get_variant(appr_ships, 0, [], variants, (space_list, True))
    get_variant(appr_ships, 0, [], variants, (space_list, False))
    return variants


def check_sea(sea, hor_space_keys, ver_space_keys, ships):
    """Check all sea that it satisfies the conditions of a sea battle"""
    check_hor, found_ships_hor, free_1_hor = check_rows(sea, hor_space_keys, True)
    if not check_hor:
        return False, [], []
    check_ver, found_ships_ver, free_1_ver = check_rows(list(zip(*sea)), ver_space_keys, False)
    if not check_ver:
        return False, [], []
    found_ships = found_ships_hor + found_ships_ver
    count_found_ships = {i: found_ships.count(i) for i in found_ships}
    if 1 in count_found_ships.keys():
        count_found_ships[1] //= 2
    count_all_ships = {i: ships.count(i) for i in ships}
    absent_ships = {}
    for k in count_all_ships.keys():
        if k in count_found_ships.keys():
            if count_all_ships[k] > count_found_ships[k]:
                absent_ships[k] = count_all_ships[k] - count_found_ships[k]
            elif count_all_ships[k] < count_found_ships[k]:
                return False, [], []
        else:
            absent_ships[k] = count_all_ships[k]
    return True, absent_ships, free_1_hor + free_1_ver


def check_rows(sea, space_keys, is_hor):
    """Check all horizontal rows or columns that it satisfies the conditions of a sea battle"""
    empty_list = []
    found_ships = []
    free_1 = []
    for i in space_keys:
        j = 0
        while j < len(sea):
            count = 1
            if sea[i][j] == 1:
                k = j + 1
                while k < 9 and sea[i][k] == 1:
                    count += 1
                    k += 1
                for ship_len in range(count):
                    if not check_ship(ship_len, sea, i, j):
                        return False, empty_list, empty_list
                if count == 1:
                    free_1 += check_1_ship(sea, i, j, found_ships, is_hor)
                elif count > 1:
                    found_ships.append(count)
            j += count
    return True, found_ships, free_1


def check_ship(ship_len, sea, i, j):
    """Checks is ship set correctly"""
    for n_p in range(-1, 2, 2):
        for m_p in range(-1, 2, 2):
            if 0 <= i + n_p < 9 and 0 <= j + ship_len + m_p < 9 \
                    and sea[i + n_p][j + ship_len + m_p] == 1:
                return False
    return True


def check_1_ship(sea, i, j, found_ships, is_hor):
    """Check is 1 ship set correctly"""
    free_1 = []
    try:
        if sea[i - 1][j] == -1 and sea[i + 1][j] == -1 \
                and sea[i][j - 1] == -1 and sea[i][j + 1] == -1:
            found_ships.append(1)
        elif sea[i - 1][j] != 1 and sea[i + 1][j] != 1 \
                and sea[i][j - 1] != 1 and sea[i][j + 1] != 1:
            if is_hor:
                free_1.append((i, j))
            else:
                free_1.append((j, i))
    except IndexError:
        if is_hor:
            free_1.append((i, j))
        else:
            free_1.append((j, i))
    return free_1


def check_row_on_spaces(row: list, space_list):
    """Checking if a string satisfies spaces"""
    row_str = "".join(["2" if k == -1 else "1" for k in row])
    count_space = [len(x) for x in row_str.split("1") if len(x) != 0]
    if count_space != space_list:
        return False
    return True


def get_variant(ships, space_num, variant, variants, params):
    """Recursively form variant of ship arrangement"""
    space_list, ship_first = params
    if len(variant) >= 9 or space_num >= len(space_list):
        return variant
    new = []
    for ship in ships:
        old = variant[::]
        if ship_first:
            old += [1 for _ in range(ship)]
            if len(old) < 9:
                old += [-1 for _ in range(space_list[space_num])]
        else:
            old += [-1 for _ in range(space_list[space_num])]
            if len(old) < 9:
                old += [1 for _ in range(ship)]
        new_ships = ships[::]
        new_ships.remove(ship)
        new = get_variant(new_ships, space_num + 1, old, variants, params)
        if len(new) == 9 and check_row_on_spaces(new, space_list) and new not in variants:
            variants.append(new)
    return new


def count_free_cell(sea, i, j, iter_value, is_hor):
    """Count cell that free for ship setting."""
    k = (j if is_hor else i) + iter_value
    count = 0
    while 9 > k >= 0 == (sea[i][k] if is_hor else sea[k][j]):
        is_free = check_neighbours_with_direct_considering(sea, i, j, k, is_hor)
        if is_free:
            count += 1
            k += iter_value
        else:
            break
    return count, k - iter_value


def append_if_possible(sea, ship_to_point, absent_ships_dict, free_1: list):
    """Append absent ships if it is possible"""
    ship = ship_to_point[0]
    i, j = ship_to_point[1]
    down = count_free_cell(sea, i, j, 1, False)
    upp = count_free_cell(sea, i, j, -1, False)
    right = count_free_cell(sea, i, j, 1, True)
    left = count_free_cell(sea, i, j, -1, True)
    if down[0] + upp[0] + 1 >= ship:
        is_inserted = insert_ship_for_point(sea, down, upp, ship_to_point, False)
        if is_inserted:
            absent_ships_dict[ship] -= 1
            for _, one_point in enumerate(free_1):
                if ship_to_point[1] == one_point:
                    free_1.remove(one_point)
        return is_inserted
    if right[0] + left[0] + 1 >= ship:
        is_inserted = insert_ship_for_point(sea, right, left, ship_to_point, True)
        if is_inserted:
            absent_ships_dict[ship] -= 1
            for _, one_point in enumerate(free_1):
                if ship_to_point[1] == one_point:
                    free_1.remove(one_point)
        return is_inserted
    return False


def append_instead_zero(sea, absent_ships_dict):
    """Append absent ships instead zero cells"""
    absent_ships = get_absent_ships_list(absent_ships_dict, True)
    for ship in absent_ships:
        is_inserted = False
        for i, row in enumerate(sea):
            for j, field in enumerate(row):
                if field == 0:
                    is_inserted = insert_ship_instead_zero(sea, ship, absent_ships_dict, i, j)
                    if is_inserted:
                        break
            if is_inserted:
                break


def insert_ship_for_point(sea, down_right, up_left, ship_to_point, is_hor):
    """Insert ship around the point"""
    ship = ship_to_point[0]
    i, j = ship_to_point[1]
    x_p = j if is_hor else i
    if up_left[0] >= ship - 1 and x_p - ship >= -1:
        for indx in range(x_p, x_p - ship, -1):
            if is_hor:
                sea[i][indx] = 1
            else:
                sea[indx][j] = 1
        return True
    if down_right[0] >= ship - 1 and x_p + ship <= 9:
        for indx in range(x_p, x_p + ship):
            if is_hor:
                sea[i][indx] = 1
            else:
                sea[indx][j] = 1
        return True
    if up_left[1] + ship <= 9:
        for indx in range(up_left[1], up_left[1] + ship):
            if is_hor:
                sea[i][indx] = 1
            else:
                sea[indx][j] = 1
        return True
    return False


def insert_ship_instead_zero(sea, ship, absent_ships_dict, i, j):
    """Insert ship to free cells"""
    count_down = count_free_cell(sea, i, j, 1, False)[0]
    count_right = count_free_cell(sea, i, j, 1, True)[0]
    if count_down + 1 >= ship and i + ship <= 9 and check_neighbours(sea, i, j):
        for x_p in range(i, i + ship):
            sea[x_p][j] = 1
        absent_ships_dict[ship] -= 1
        return True
    if count_right + 1 >= ship and j + ship <= 9 and check_neighbours(sea, i, j):
        for x_p in range(j, j + ship):
            sea[i][x_p] = 1
        absent_ships_dict[ship] -= 1
        return True
    return False


def check_neighbours(sea, i, j):
    """Checks if there are free cells around the point without according the direction"""
    for iter_1 in range(-1, 2):
        for iter_2 in range(-1, 2):
            y_p = i + iter_1
            x_p = j + iter_2
            if 0 <= x_p < 9 and 0 <= y_p < 9 and sea[y_p][x_p] == 1:
                return False
    return True


def check_neighbours_with_direct_considering(sea, i, j, k, is_hor):
    """Checks if there are free cells around the point, taking into account the direction"""
    for iter_1 in range(-1, 2):
        for iter_2 in range(-1, 2):
            y_p = i + iter_1 if is_hor else k + iter_2
            x_p = k + iter_2 if is_hor else j + iter_1
            if 0 <= x_p < 9 and 0 <= y_p < 9 and sea[y_p][x_p] == 1 and y_p != i and x_p != j:
                return False
    return True


def get_absent_ships_list(absent_ships_dict, include_one):
    """Convert dict with absent ships to list"""
    absent_ships = []
    for ship in absent_ships_dict.keys():
        if include_one or ship != 1:
            for _ in range(absent_ships_dict[ship]):
                absent_ships.append(ship)
    return absent_ships
