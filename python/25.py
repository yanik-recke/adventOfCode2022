from pathlib import Path
from z3 import Solver, Int, Or, sat

# from decimal to quinary
def g(s: str) -> int:
    n = int(s)
    remainders = []

    while n != 0:
        remainders.append(str(n % 5))
        n = n // 5

    return int("".join(remainders[::-1]))

safu = {
    "2": 2,
    "1": 1,
    "0": 0,
    "-": -1,
    "=": -2
}

rev_safu = {v: k for k, v in safu.items()}

def from_safu(s: str) -> int:
    return sum(safu[c] * pow(5, i) for i, c in enumerate(s[::-1]))

dec_n = sum(from_safu(l) for l in Path("in25.txt").read_text().splitlines())
qui_n = g(str(dec_n))

s = Solver()

x = [Int(f'{i}') for i, _ in enumerate(str(qui_n))]

# x can only be one of these
for i, _ in enumerate(str(qui_n)):
    s.add(Or(x[i] == 2, x[i] == 1, x[i] == 0, x[i] == -1, x[i] == -2))

s.add(sum(x[i] * pow(5, i) for i, _ in enumerate(str(qui_n))) == dec_n)

if s.check() == sat:
    model = s.model()
    result = "".join(rev_safu[model[x[i]].as_long()] for i in range(len(str(qui_n)) - 1, -1, -1))
    print(result)
else:
    print("No model found")