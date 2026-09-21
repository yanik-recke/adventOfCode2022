package day_23;

import helpers.HelperMethods;

import java.util.*;

/**
 * Lösungen für Tag 23.
 *
 * @author Yanik Recke
 */
public class Day_23 {

    public static void main(String[] args) {
        String pathToInput = "src/day_23/input.txt";

        System.out.println("part 1: " + part1(pathToInput) + "\npart 2: " + part2(pathToInput));
    }


    /**
     * Elfen werden als Positionen in einer Menge realisiert. Über
     * HashMap wird gespeichert, welche Position von welchem Elfen vorgeschlagen wurde.
     * Wenn eine Position das zweite Mal vorgeschlagen wurde, dann wird diese
     * entfernt und gespeichert, damit sie, sollte sie ein drittes Mal vorgeschlagen werden,
     * nicht wieder hinzugefügt wird. Am Ende jeder Runde die Positionen der Elfen durch die
     * vorgeschlagenen aus der HashMap ersetzen.
     *
     * @param pathToInput - Pfad zum Puzzle Input
     * @return - Anzahl leerer Felde nach 10 Runden
     */
    private static long part1(String pathToInput) {
        List<String> input = HelperMethods.getInputAsListOfString(pathToInput);

        Queue<Pair<Direction, Set<Direction>>> checks = new LinkedList<>();

        checks.add(new Pair<>(Direction.N, new HashSet<>(Arrays.asList(Direction.N, Direction.NE, Direction.NW))));
        checks.add(new Pair<>(Direction.S, new HashSet<>(Arrays.asList(Direction.S, Direction.SE, Direction.SW))));
        checks.add(new Pair<>(Direction.W, new HashSet<>(Arrays.asList(Direction.W, Direction.NW, Direction.SW))));
        checks.add(new Pair<>(Direction.E, new HashSet<>(Arrays.asList(Direction.E, Direction.NE, Direction.SE))));

        Set<Position> elves = new HashSet<>();

        for (int x = 0; x < input.size(); x++) {
            for (int y = 0; y < input.get(x).length(); y++) {
                if (input.get(x).charAt(y) == '#') {
                    elves.add(new Position(y, x));
                }
            }
        }

        // 10 rounds
        for (int round = 0; round < 10; round++) {
            Map<Position, Position> proposed = new HashMap<>();
            Set<Position> rejected = new HashSet<>();
            for (Position elve : elves) {
                boolean needsToMove = false;

                for (Direction dir : Direction.values()) {
                    if (elves.contains(elve.getNeighbour(dir))) {
                        needsToMove = true;
                    }
                }

                if (needsToMove) {
                    Iterator<Pair<Direction, Set<Direction>>> it = checks.iterator();
                    boolean done = false;

                    while (!done && it.hasNext()) {
                        Pair<Direction, Set<Direction>> tempPair = it.next();
                        Set<Direction> curr = tempPair.r();
                        Direction move = tempPair.l();

                        Iterator<Direction> it2 = curr.iterator();
                        boolean found = false;

                        while (!found && it2.hasNext()) {
                            Direction dir = it2.next();
                            Position pos = elve.getNeighbour(dir);

                            if (elves.contains(pos)) {
                                found = true;
                            }
                        }

                        if (!found) {
                            Position temp = elve.getNeighbour(move);
                            done = true;
                            if (proposed.containsKey(temp) && !rejected.contains(temp)) {
                                rejected.add(temp);
                                proposed.remove(temp);
                            } else if (!rejected.contains(temp)) {
                                proposed.put(temp, elve);
                            }
                        }
                    }
                }
            }

            for (Position pos : proposed.keySet()) {
                elves.remove(proposed.get(pos));
                elves.add(pos);
            }

            checks.add(checks.poll());
        }

        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;

        for (Position pos : elves) {
            if (pos.getX() < minX) {
                minX = pos.getX();
            }

            if (pos.getX() > maxX) {
                maxX = pos.getX();
            }

            if (pos.getY() < minY) {
                minY = pos.getY();
            }

            if (pos.getY() > maxY) {
                maxY = pos.getY();
            }
        }

        Set<Position> field = new HashSet<>();

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                field.add(new Position(x, y));
            }
        }

        field.removeAll(elves);

        return field.size();
    }

    /**
     * Siehe Part 1. Läuft diesmal aber nicht 10 Runden, sondern solange bis
     * sich kein Elf mehr bewegen muss.
     *
     * @param pathToInput - Pfad zum Puzzle Input
     * @return - Anzahl der durchlaufenen Runden
     */
    private static long part2(String pathToInput) {
        List<String> input = HelperMethods.getInputAsListOfString(pathToInput);

        Queue<Pair<Direction, Set<Direction>>> checks = new LinkedList<>();

        checks.add(new Pair<>(Direction.N, new HashSet<>(Arrays.asList(Direction.N, Direction.NE, Direction.NW))));
        checks.add(new Pair<>(Direction.S, new HashSet<>(Arrays.asList(Direction.S, Direction.SE, Direction.SW))));
        checks.add(new Pair<>(Direction.W, new HashSet<>(Arrays.asList(Direction.W, Direction.NW, Direction.SW))));
        checks.add(new Pair<>(Direction.E, new HashSet<>(Arrays.asList(Direction.E, Direction.NE, Direction.SE))));

        Set<Position> elves = new HashSet<>();

        for (int x = 0; x < input.size(); x++) {
            for (int y = 0; y < input.get(x).length(); y++) {
                if (input.get(x).charAt(y) == '#') {
                    elves.add(new Position(y, x));
                }
            }
        }

        boolean noneMoved = false;
        int counter = 0;
        // 10 rounds
        for (int round = 0; !noneMoved; round++) {
            noneMoved = true;
            counter++;
            Map<Position, Position> proposed = new HashMap<>();
            Set<Position> rejected = new HashSet<>();
            for (Position elve : elves) {
                boolean needsToMove = false;

                for (Direction dir : Direction.values()) {
                    if (elves.contains(elve.getNeighbour(dir))) {
                        needsToMove = true;
                        noneMoved = false;
                    }
                }

                if (needsToMove) {
                    Iterator<Pair<Direction, Set<Direction>>> it = checks.iterator();
                    boolean done = false;

                    while (!done && it.hasNext()) {
                        Pair<Direction, Set<Direction>> tempPair = it.next();
                        Set<Direction> curr = tempPair.r();
                        Direction move = tempPair.l();

                        Iterator<Direction> it2 = curr.iterator();
                        boolean found = false;

                        while (!found && it2.hasNext()) {
                            Direction dir = it2.next();
                            Position pos = elve.getNeighbour(dir);

                            if (elves.contains(pos)) {
                                found = true;
                            }
                        }

                        if (!found) {
                            Position temp = elve.getNeighbour(move);
                            done = true;
                            if (proposed.containsKey(temp) && !rejected.contains(temp)) {
                                rejected.add(temp);
                                proposed.remove(temp);
                            } else if (!rejected.contains(temp)) {
                                proposed.put(temp, elve);
                            }
                        }
                    }
                }
            }

            for (Position pos : proposed.keySet()) {
                elves.remove(proposed.get(pos));
                elves.add(pos);
            }

            checks.add(checks.poll());
        }

        return counter;
    }

}
