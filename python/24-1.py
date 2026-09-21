from pathlib import Path
import math 

lines = Path("/Users/yanik/_repos/adventOfCode2022/python/in24.txt").read_text().splitlines()

# -1 for removing the # borders, add start and goal manually
W, H = len(lines[0]) - 1, len(lines) - 1

valley_types = {
    ".": 0,
    "^": 1,
    ">": 2,
    "v": 3,
    "<": 4
}

rev_types = {v: k for k, v in valley_types.items()}

offsets = {
    1: (0, -1),
    2: (1, 0),
    3: (0, 1),
    4: (-1, 0)
}

def to_list(g: dict[tuple[int, int], int]) -> list[tuple[int, int, int]]:
    return [(e[0], e[1], g[e]) for e in g]

def move_bliz(b: int, p: tuple[int, int], w: int, h: int) -> tuple[int, int]:
    nx, ny = p[0] + offsets[b][0], p[1] + offsets[b][1]
    if nx == w: nx = 1
    if ny == h: ny = 1
    if nx == 0: nx = w - 1
    if ny == 0: ny = h - 1
    
    return (nx, ny)

def print_valley(v: dict[tuple[int, int], set[int]], w: int, h: int):
    tmp = ""
    for y in range(0, h):
        for x in range(0, w):
            if (x, y) not in v:
                tmp += "#"
            elif len(v[(x, y)]) == 1:
                tmp += rev_types[list(v[(x, y)])[0]]
            elif len(v[(x, y)]) == 2:
                if 0 in v[(x, y)]:
                    for b in v[(x, y)]:
                        if b == 0:
                            continue

                        tmp += rev_types[b]
            else:
                tmp += str(len(v[(x, y)]) - 1)

        tmp += "\n"
    print(tmp)

assert (1, 2) == move_bliz(2, (5, 2), 6, 6)
assert (5, 2) == move_bliz(4, (1, 2), 6, 6)
assert (2, 1) == move_bliz(3, (2, 5), 6, 6)
assert (5, 5) == move_bliz(1, (5, 1), 6, 6)

valley: dict[tuple[int, int], set[int]] = {
    (c, r): {valley_types[s]} for r, l in enumerate(lines) for c, s in enumerate(l) if r > 0 and r < H and c > 0 and c < W
}

start = (1, 0)
valley[start] = {0}

goal = (W - 1, H)
valley[goal] = {0}
states = []

# simulate all the blizzards
# keep track of how many times each blizzard needs to return to it's starting position
# get lcm -> at lcm the grid is in its original state

ticks = math.lcm(H - 1, W - 1)

valleys: dict[int, dict[tuple[int, int], set[int]]] = {
    0: valley
}

for i in range(1, ticks):
    nvalley = {e: {0} for e in valley}

    for x, y in valley:
        for b in valley[x, y]:
            if b != 0:
                nvalley.setdefault(move_bliz(b, (x, y), W, H), set()).add(b)

    valleys[i] = {e: nvalley[e] for e in nvalley}
    valley = {e: nvalley[e] for e in nvalley}


# queue providing tick and position
q: list[tuple[int, tuple[int, int], int]] = []
visited: set[tuple[int, tuple[int, int], int]] = set()


dirs = [(0, -1), (1, 0), (0, 1), (-1, 0), (0, 0)]
q.append((0, start, 1))

while len(q) > 0:
    state = q.pop(0)

    if state in visited:
        continue

    t, (x, y), s = state
    visited.add(state)

    if (t + 1) % ticks == 0: t = -1

    cv = valleys[t + 1]

    for dx, dy in dirs:
        nx, ny = x + dx, y + dy

        if (nx, ny) == goal: 
            print(s)
            exit(0)

        if nx == 0 or nx == W: continue
        if ny == 0 or ny == H: continue
        if (nx, ny) in cv and len(cv[(nx, ny)]) == 1 and 0 in cv[(nx, ny)]:
            q.append((t + 1, (nx, ny), s + 1))


# too low 252