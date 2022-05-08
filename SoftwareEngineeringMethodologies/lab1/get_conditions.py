
import random
from dataclasses import dataclass


@dataclass
class Point:
    """Data class point"""
    p_x: int
    p_y: int


def get_sea_with_ships():
    ships = [4, 3, 3, 2, 2, 2, 1, 1, 1, 1]
    sea = [[0 for _ in range(0, 9)] for _ in range(0, 9)]
    for i, ship in enumerate(ships):
        is_settable = False
        s_p = Point(0, 0)
        orient = Point(0, 0)
        while not is_settable:
            orient = Point(1, 0) if random.random() < 0.5 else Point(0, 1)  # <0.5 - horizontal
            s_p.p_x = random.randint(0, 9 - ship * orient.p_x - int((len(ships) - i) // 3))
            s_p.p_y = random.randint(0, 9 - ship * orient.p_y - int((len(ships) - i) // 3))
            for j in range(ships[i]):
                try:
                    is_settable = sea[s_p.p_y + orient.p_y * j][s_p.p_x + orient.p_x * j] == 0
                except IndexError:
                    is_settable = False
                if not is_settable:
                    break
        if is_settable:
            set_ship(orient, sea, ship, s_p)
    return sea


def set_ship(orient, sea, ship, s_p):
    """Set ship to sea"""
    for size in range(ship):
        for j in range(-1, 2):
            for k in range(-1, 2):
                try:
                    set_y = s_p.p_y + orient.p_y * size + j
                    set_x = s_p.p_x + orient.p_x * size + k
                    r_1 = range(s_p.p_y, s_p.p_y + orient.p_y * size + 1)
                    r_2 = range(s_p.p_x, s_p.p_x + orient.p_x * size + 1)
                    if (set_y >= 0 and set_x >= 0) and (set_y not in r_1 or set_x not in r_2):
                        sea[set_y][set_x] = -1
                except IndexError:
                    pass
        sea[s_p.p_y + orient.p_y * size][s_p.p_x + orient.p_x * size] = 1


def print_sea(sea: list):
    """Print sea matrix"""
    for row in sea:
        for cell in row:
            if cell == 1:
                print(f"{'*':>3}", end="")
            else:
                print(f"{'_':>3}", end="")
        print()


def get_conditions():
    hor_spaces = {}
    ver_spaces = {}
    sea = []
    is_appropriate = False
    while not is_appropriate:
        hor_spaces = {}
        ver_spaces = {}
        sea = get_sea_with_ships()
        add_spaces(sea, 1, hor_spaces)
        add_spaces(sea, 4, hor_spaces)
        add_spaces(sea, 7, hor_spaces)
        transposed_sea = list(zip(*sea))
        add_spaces(transposed_sea, 2, ver_spaces)
        add_spaces(transposed_sea, 5, ver_spaces)
        add_spaces(transposed_sea, 7, ver_spaces)
        is_appropriate = list(hor_spaces.keys()) == [1, 4, 7] and list(ver_spaces.keys()) == [2, 5, 7]
    print_sea(sea)
    return hor_spaces, ver_spaces


def add_spaces(sea, row_number, spaces):
    row_str = "".join(["1" if k == 1 else "2" for k in sea[row_number]])
    count_space = [len(x) for x in row_str.split("1") if len(x) != 0]
    if len(count_space) in (2, 3) and sum(count_space) <= 7:
        spaces[row_number] = count_space


if __name__ == '__main__':
    print(get_conditions())
