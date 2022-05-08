valid_condition1 = {"horizontal_space_numbers": {1: [2, 1, 2], 4: [1, 4, 1], 7: [1, 1, 1]},
                    "vertical_space_numbers": {2: [1, 2, 4], 5: [1, 2, 3], 7: [3, 1, 1]}}

valid_condition2 = {"horizontal_space_numbers": {1: [3, 1, 1], 4: [5, 2], 7: [1, 2, 1]},
                    "vertical_space_numbers": {2: [5, 1, 1], 5: [1, 2, 4], 7: [4, 1]}}

invalid_condition1 = {"horizontal_space_numbers": {1: [2, 2, 2], 4: [1, 4, 1], 7: [1, 1, 1]},
                      "vertical_space_numbers": {2: [2, 2, 4], 5: [1, 2, 3], 7: [3, 1, 1]}}

invalid_condition2 = {"vertical_space_numbers": {2: [2, 2, 4], 5: [1, 2, 3], 7: [3, 1, 1]}}

solution_for_valid_condition = [[
    [0, 0, -1, 0, 0, -1, 0, -1, 0],
    [-1, -1, 1, 1, -1, 1, -1, -1, 1],
    [1, 0, -1, 0, 0, 1, 0, -1, 0],
    [1, 0, -1, 0, 0, -1, 0, 1, 0],
    [1, -1, 1, -1, -1, -1, -1, 1, -1],
    [1, 0, -1, 0, 0, 1, 0, 1, 0],
    [0, 0, -1, 0, 0, -1, 0, -1, 0],
    [-1, 1, -1, 1, 1, -1, 1, 1, 1],
    [0, 0, -1, 0, 0, -1, 0, -1, 0]]
]
