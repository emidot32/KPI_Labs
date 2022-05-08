"""Client for task10_2 API"""
import requests

URL = "http://127.0.0.1:5000"


def get_conditions():
    """Gets space numbers"""
    return requests.get(f"{URL}/conditions").json()


def print_seas(spaces: dict):
    """Gets solution and prints sea matrices"""
    response = requests.post(f"{URL}/ship-placements", json=spaces)
    if response.status_code != 200:
        print("Wrong request")
    seas = response.json()
    if not seas:
        print("Solution is not found")
    for sea in seas:
        for row in sea:
            for cell in row:
                if cell == 1:
                    print(f"{'*':>3}", end="")
                else:
                    print(f"{'_':>3}", end="")
            print()
        print()


if __name__ == '__main__':
    conditions = get_conditions()
    print(conditions)
    print_seas(conditions)
