from _pydecimal import Decimal, ROUND_UP, ROUND_FLOOR

# import numpy as np
from scipy.stats import f, t, ttest_ind, norm


class Criteries:
    @staticmethod
    def get_cohren_value(size_of_selections, qty_of_selections, significance):
        # qty_of_selections = 4
        # size_of_selections = 4
        size_of_selections += 1

        #  significance = 0.05

        partResult1 = significance / (size_of_selections - 1)
        params = [partResult1, qty_of_selections, (size_of_selections - 1 - 1) * qty_of_selections]
        fisher = f.isf(*params)
        # print(fisher)
        # fisher = 0
        result = fisher / (fisher + (size_of_selections - 1 - 1))
        return Decimal(result).quantize(Decimal('.0001')).__float__()

    @staticmethod
    def get_student_value(f3, significance):
        return Decimal(abs(t.ppf(significance / 2, f3))).quantize(Decimal('.0001')).__float__()

    @staticmethod
    def get_fisher_value(f3, f4, significance):
        return Decimal(abs(f.isf(significance, f4, f3))).quantize(Decimal('.0001')).__float__()
