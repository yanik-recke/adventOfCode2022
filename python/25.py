from pathlib import Path
from z3 import Solver, Int, Or, sat

# from decimal to quinary
def g(s: str) -> str:
    n = int(s)
    remainders = []

    while n != 0:
        remainders.append(str(n % 5))
        n = n // 5

    return "".join(remainders[::-1])

safu = {
    "2": 2,
    "1": 1,
    "0": 0,
    "-": -1,
    "=": -2
}

rev_safu = {v: k for k, v in safu.items()}

def from_safu(s: str) -> int:
    return sum(safu[c] * (5 ** i) for i, c in enumerate(s[::-1]))

dec_n = sum(from_safu(l) for l in Path("in25.txt").read_text().splitlines())
qui_n = g(str(dec_n))


# solver not neccessarily needed but cooler
# other solution: you can also map remainders with division by 5
# 0 -> -2, 1 -> -1, 2 -> 0, 3 -> 1, 4 -> 2
s = Solver()

x = [Int(f'x{i}') for i in range(len(qui_n))]

# x can only be one of these
for i in range(len(qui_n)):
    s.add(Or(x[i] == 2, x[i] == 1, x[i] == 0, x[i] == -1, x[i] == -2))

s.add(sum(x[i] * pow(5, i) for i in range(len(qui_n))) == dec_n)

if s.check() == sat:
    model = s.model()
    result = "".join(rev_safu[model[x[i]].as_long()] for i in range(len(qui_n) - 1, -1, -1)) # type: ignore
    print(result)
else:
    print("No model found")