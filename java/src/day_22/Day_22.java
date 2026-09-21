package day_22;

import helpers.HelperMethods;

import java.util.ArrayList;
import java.util.List;


/**
 * Lösung für Tag 22.
 * Part 2 funktioniert nur mit Input, der denselben
 * Aufbau hat wie meiner.
 *
 * @author Yanik Recke
 */
public class Day_22 {

	public static void main(String[] args) {
		String pathToInput = "src/day_22/input.txt";
		
		System.out.println("p1: " + part1(pathToInput));
		System.out.println("p2: " + part2());
	}


	/**
	 * Erst Aufbauen der Map. Alle Whitespaces aus dem Input werden
	 * zu '-' umgewandelt. Sobald man auf einen Bindestrich kommt
	 * weiß man, dass man "wrappen" muss.
	 *
	 * @param path - Pfad zum Puzzle Input
	 * @return - 1000 * Reihe Endposition + 4 * Spalte Endposition + Richtung-Ordinalwert
	 */
	private static long part1(String path) {
		/*
		 * This code was initially written very late at night (it's not pretty but it works)
		 * TODO clean up
		 */
		List<String> input = helpers.HelperMethods.getInputAsListOfString(path);
		
		int maxX = 0;
		int maxY = 0;
		int counter = 0;
		boolean done = false;
		
		
		// TODO ausstieg bei done = true
		for (String line : input) {
			if (!done && (line.length() - 1) > maxX) {
				maxX = line.length();
			}
			
			if ("".equals(line)) {
				maxY = counter;
				done = true;
			}
			
			counter++;
		}

		char[][] field = new char[maxX][maxY];
		
		for (int x = 0; x < field.length; x++) {
			for (int y = 0; y < field[x].length; y++) {
				field[x][y] = '-';
			}
		}

		for (int y = 0; y < input.size(); y++) {
			for (int x = 0; x < input.get(y).length(); x++) {
				
				if (!"".equals(input.get(y))) {
					String temp = input.get(y);

					if (temp.charAt(x) == '.') {
						field[x][y] = '.';
					} else if (temp.charAt(x) == '#') {
						field[x][y] = '#';
					}
				}
			}
		}
		
		String instructions = input.get(input.size() - 1);
		
		int idx = 0;
		int prevIdx = idx;
		
		
		List<String> instr = new ArrayList<>();
		
		while (idx != -1) {
			if (instructions.indexOf('L', idx) < instructions.indexOf('R', idx)) {
				if (instructions.indexOf('L', idx) != -1)
					idx = instructions.indexOf('L', idx);
				else 
					idx = instructions.indexOf('R', idx);
			} else {
				if (instructions.indexOf('R', idx) != -1)
					idx = instructions.indexOf('R', idx);
				else 
					idx = instructions.indexOf('L', idx);
			}
			
			if (idx != -1) {
				instr.add(instructions.substring(prevIdx, idx));
				instr.add(instructions.substring(idx, ++idx));
				prevIdx = idx;
			}
		}

		instr.add("22");
		Position pos = null;
		Direction facing = Direction.RIGHT;
		
		boolean found0 = false;
		for (int x = 0; x < field.length && !found0; x++) {
			if (field[x][0] != '-' && field[x][0] != '#') {
				pos = new Position(x, 0);
				found0 = true;
			}
		}
		
		for (String go : instr) {

			int y = pos.getY();
			int x = pos.getX();
			
			if ("R".equals(go)) {
				switch (facing) {
					case TOP -> {
						facing = Direction.RIGHT;
					}
				
					case RIGHT -> {
						facing = Direction.DOWN;
					}
				
					case DOWN -> {
						facing = Direction.LEFT;
					}
				
					default -> {
						facing = Direction.TOP;
					}
				}
			} else if ("L".equals(go)) {
				switch (facing) {
					case TOP -> {
						facing = Direction.LEFT;
					}
			
					case RIGHT -> {
						facing = Direction.TOP;
					}
			
					case DOWN -> {
						facing = Direction.RIGHT;
					}
			
					default -> {
						facing = Direction.DOWN;
					}
				}
			} else {
				int move = Integer.parseInt(go);
				
				switch (facing) {
					case TOP -> {
						for (int i = 0; i < move; i++) {
							x = pos.getX();
							y = pos.getY();
							// Auf der anderen Seite heraus kommen
							if (y - 1 < 0 || field[x][y - 1] == '-') {
								boolean found = false;
								int tempY = y;
								
								while (!found) {
									if (tempY >= field[x].length || field[x][tempY] == '-') {
										tempY--;
										if (field[x][tempY] != '#') {
											pos = new Position(x, tempY);
										}
										found = true;
									} else {
										tempY++;
									}
								}
								
							} else {
								if (field[x][y - 1] != '#') {
									pos = new Position(x, y - 1);
								}
							}
						}
					}
		
					case RIGHT -> {
						for (int i = 0; i < move; i++) {
							x = pos.getX();
							y = pos.getY();
							// Auf der anderen Seite heraus kommen
							if (x + 1 >= field.length || field[x + 1][y] == '-') {
								boolean found = false;
								int tempX = x;
								
								while (!found) {
									if (tempX < 0 || field[tempX][y] == '-') {
										tempX++;
										if (field[tempX][y] != '#') {
											pos = new Position(tempX, y);
										}
										found = true;
									} else {
										tempX--;
									}
								}
							} else {
								if (field[x + 1][y] != '#') {
									pos = new Position(x + 1, y);
								}
							}
						}
					}
		
					case DOWN -> {
						for (int i = 0; i < move; i++) {
							x = pos.getX();
							y = pos.getY();
							// Auf der anderen Seite heraus kommen
							if (y + 1 >= field[x].length || field[x][y + 1] == '-') {
								boolean found = false;
								int tempY = y;
								
								while (!found) {
									if (tempY < 0 || field[x][tempY] == '-') {
										tempY++;
										if (field[x][tempY] != '#') {
											pos = new Position(x, tempY);
										}
										found = true;
									} else {
										tempY--;
									}
								}
								
							} else {
								if (field[x][y + 1] != '#') {
									pos = new Position(x, y + 1);
								}
							}
						}
					}	
		
					default -> {
						for (int i = 0; i < move; i++) {
							x = pos.getX();
							y = pos.getY();
							// Auf der anderen Seite heraus kommen
							if (x - 1 < 0|| field[x - 1][y] == '-') {
								boolean found = false;
								int tempX = x;
								
								while (!found) {
									if (tempX >= field.length || field[tempX][y] == '-') {
										tempX--;
										if (field[tempX][y] != '#') {
											pos = new Position(tempX, y);
										}
										found = true;
									} else {
										tempX++;
									}
								}
							} else {
								if (field[x - 1][y] != '#') {
									pos = new Position(x - 1, y);
								}
							}
						}
					}
				}
			}
		}

		return 1000 * (pos.getY() + 1) + 4 * (pos.getX() + 1) + facing.ordinal();
	}


	/**
	 * Zuerst mit "prep.py" den Input vernünftig parsen. Heraus kommmen
	 * 6 Textdateien, die die entsprechenden 50x50 Felder enthalten. Was passiert,
	 * wenn von einem Feld ins nöchste überlaufen wird, wird in "calcNextField()"
	 * berechnet.
	 *
	 * @return - 1000 * (Reihe Endposition) + 4 * (Spalte Endposition) + Richtungs-Ordinalwert
	 */
	private static long part2() {
		String[] paths = new String[]{"src/day_22/1.txt", "src/day_22/2.txt", "src/day_22/3.txt", "src/day_22/4.txt",
				"src/day_22/5.txt", "src/day_22/6.txt"};

		List<char[][]> fields = new ArrayList<>();

		for (String path : paths) {
			fields.add(createField(path));
		}

		List<String> input = HelperMethods.getInputAsListOfString("src/day_22/input.txt");
		String instructions = input.get(input.size() - 1);

		int idx = 0;
		int prevIdx = idx;


		// scuffed
		List<String> instr = new ArrayList<>();

		while (idx != -1) {
			if (instructions.indexOf('L', idx) < instructions.indexOf('R', idx)) {
				if (instructions.indexOf('L', idx) != -1)
					idx = instructions.indexOf('L', idx);
				else
					idx = instructions.indexOf('R', idx);
			} else {
				if (instructions.indexOf('R', idx) != -1)
					idx = instructions.indexOf('R', idx);
				else
					idx = instructions.indexOf('L', idx);
			}

			if (idx != -1) {
				instr.add(instructions.substring(prevIdx, idx));
				instr.add(instructions.substring(idx, ++idx));
				prevIdx = idx;
			}
		}

		instr.add("22");

		Position pos = new Position(0,0);
		Direction facing = Direction.RIGHT;
		int currField = 1;

		for (String go : instr) {

			int y = pos.getY();
			int x = pos.getX();

			if ("R".equals(go)) {
				switch (facing) {
					case TOP -> {
						facing = Direction.RIGHT;
					}

					case RIGHT -> {
						facing = Direction.DOWN;
					}

					case DOWN -> {
						facing = Direction.LEFT;
					}

					default -> {
						facing = Direction.TOP;
					}
				}
			} else if ("L".equals(go)) {
				switch (facing) {
					case TOP -> {
						facing = Direction.LEFT;
					}

					case RIGHT -> {
						facing = Direction.TOP;
					}

					case DOWN -> {
						facing = Direction.RIGHT;
					}

					default -> {
						facing = Direction.DOWN;
					}
				}
			} else {
				int move = Integer.parseInt(go);

				for (int i = 0; i < move; i++) {

					switch (facing) {
						case TOP -> {
								x = pos.getX();
								y = pos.getY();
								// Auf neuem Feld herauskommen
								if (y - 1 < 0) {
									Pair<Integer, Pair<Position, Direction>> tempPair = calcNextField(currField, facing, pos);
									if (fields.get(tempPair.l() - 1)[tempPair.r().l().getX()][tempPair.r().l().getY()] != '#') {
										currField = tempPair.l();
										pos = tempPair.r().l();
										facing = tempPair.r().r();
									}
								} else {
									if (fields.get(currField - 1)[x][y - 1] != '#') {
										pos = new Position(x, y - 1);
									}
								}
						}

						case RIGHT -> {
								x = pos.getX();
								y = pos.getY();
								// Auf neuem Feld wieder herauskommen
								if (x + 1 >= 50) {
									Pair<Integer, Pair<Position, Direction>> tempPair = calcNextField(currField, facing, pos);
									if (fields.get(tempPair.l() - 1)[tempPair.r().l().getX()][tempPair.r().l().getY()] != '#') {
										currField = tempPair.l();
										pos = tempPair.r().l();
										facing = tempPair.r().r();
									}
								} else {
									if (fields.get(currField - 1)[x + 1][y] != '#') {
										pos = new Position(x + 1, y);
									}
								}
						}

						case DOWN -> {
								x = pos.getX();
								y = pos.getY();
								// Auf neuem Feld wieder herauskommen
								if (y + 1 >= 50) {
									Pair<Integer, Pair<Position, Direction>> tempPair = calcNextField(currField, facing, pos);
									if (fields.get(tempPair.l() - 1)[tempPair.r().l().getX()][tempPair.r().l().getY()] != '#') {
										currField = tempPair.l();
										pos = tempPair.r().l();
										facing = tempPair.r().r();
									}
								} else {
									if (fields.get(currField - 1)[x][y + 1] != '#') {
										pos = new Position(x, y + 1);
									}
								}
						}

						default -> {
								x = pos.getX();
								y = pos.getY();
								// Auf neuem Feld wieder herauskommen
								if (x - 1 < 0) {
									Pair<Integer, Pair<Position, Direction>> tempPair = calcNextField(currField, facing, pos);
									if (fields.get(tempPair.l() - 1)[tempPair.r().l().getX()][tempPair.r().l().getY()] != '#') {
										currField = tempPair.l();
										pos = tempPair.r().l();
										facing = tempPair.r().r();
									}
								} else {
									if (fields.get(currField - 1)[x - 1][y] != '#') {
										pos = new Position(x - 1, y);
									}
								}
						}
					}
				}
			}
		}

		int row;
		int col;

		switch (currField) {
			case 1-> {
				col = 50;
				row = 0;
			}

			case 2 -> {
				col = 100;
				row = 0;
			}

			case 3 -> {
				col = 50;
				row = 50;
			}

			case 4 -> {
				col = 50;
				row = 100;
			}

			case 5 -> {
				col = 0;
				row = 100;
			}

			default -> {
				col = 0;
				row = 150;
			}
		}

		return 1000 * (row + pos.getY() + 1) + 4 * (col + pos.getX() + 1) + facing.ordinal();
	}


	/**
	 * Erzeugt ein Spielfeld aus einer
	 * Textdatei.
	 *
	 * @param path - Pfad zur Textdatei
	 * @return - Char Array
	 */
	private static char[][] createField(String path) {
		List<String> input = HelperMethods.getInputAsListOfString(path);

		// jedes Feld ist 50x50
		char[][] field = new char[50][50];

		int j = 0;
		for (String line : input) {
			for (int i = 0; i < line.length(); i++) {
				field[i][j] = line.charAt(i);
			}
			j++;
		}

		return field;
	}


	/**
	 * Berechnet, was passiert, wenn über den Rand eines Spielfeldes gelaufen wird.
	 * So wird z.B. beim Überlaufen des rechten Randes des Feldes 1 wiedergegeben, dass
	 * man auf Feld 2 landet und nach rechts guckt.
	 *
	 * @param currField - das aktuelle Feld auf dem man sich befindet
	 * @param facing - die Richtung in die man guckt
	 * @param pos - die aktuelle Position auf dem Feld
	 * @return - Pair, welches das neue Feld und ein weiteres Paar, welches die neue Richtung und Position enthält, enthält
	 */
	private static Pair<Integer, Pair<Position, Direction>> calcNextField(int currField, Direction facing, Position pos) {
		switch (currField) {
			case 1 -> {
				switch (facing) {
					case TOP -> {
						return new Pair<>(6, new Pair<>(new Position(0, pos.getX()), Direction.RIGHT));
					}

					case RIGHT -> {
						return new Pair<>(2, new Pair<>(new Position(0, pos.getY()), Direction.RIGHT));
					}

					case DOWN -> {
						return new Pair<>(3, new Pair<>(new Position(pos.getX(), 0), Direction.DOWN));
					}

					default -> {
						return new Pair<>(5, new Pair<>(new Position(0, 49 - pos.getY()), Direction.RIGHT));
					}
				}
			}

			case 2 -> {
				switch (facing) {
					case TOP -> {
						return new Pair<>(6, new Pair<>(new Position(pos.getX(), 49), Direction.TOP));
					}

					case RIGHT -> {
						return new Pair<>(4, new Pair<>(new Position(49, 49 - pos.getY()), Direction.LEFT));
					}

					case DOWN -> {
						return new Pair<>(3, new Pair<>(new Position(49, pos.getX()), Direction.LEFT));
					}

					default -> {
						return new Pair<>(1, new Pair<>(new Position(49, pos.getY()), Direction.LEFT));
					}
				}
			}

			case 3 -> {
				switch (facing) {
					case TOP -> {
						return new Pair<>(1, new Pair<>(new Position(pos.getX(), 49), Direction.TOP));
					}

					case RIGHT -> {
						return new Pair<>(2, new Pair<>(new Position(pos.getY(), 49), Direction.TOP));
					}

					case DOWN -> {
						return new Pair<>(4, new Pair<>(new Position(pos.getX(), 0), Direction.DOWN));
					}

					default -> {
						return new Pair<>(5, new Pair<>(new Position(pos.getY(), 0), Direction.DOWN));
					}
				}
			}

			case 4 -> {
				switch (facing) {
					case TOP -> {
						return new Pair<>(3, new Pair<>(new Position(pos.getX(), 49), Direction.TOP));
					}

					case RIGHT -> {
						return new Pair<>(2, new Pair<>(new Position(49, 49 - pos.getY()), Direction.LEFT));
					}

					case DOWN -> {
						return new Pair<>(6, new Pair<>(new Position(49, pos.getX()), Direction.LEFT));
					}

					default -> {
						return new Pair<>(5, new Pair<>(new Position(49, pos.getY()), Direction.LEFT));
					}
				}
			}

			case 5 -> {
				switch (facing) {
					case TOP -> {
						return new Pair<>(3, new Pair<>(new Position(0, pos.getX()), Direction.RIGHT));
					}

					case RIGHT -> {
						return new Pair<>(4, new Pair<>(new Position(0, pos.getY()), Direction.RIGHT));
					}

					case DOWN -> {
						return new Pair<>(6, new Pair<>(new Position(pos.getX(), 0), Direction.DOWN));
					}

					default -> {
						return new Pair<>(1, new Pair<>(new Position(0, 49 - pos.getY()), Direction.RIGHT));
					}
				}
			}

			case 6 -> {
				switch (facing) {
					case TOP -> {
						return new Pair<>(5, new Pair<>(new Position(pos.getX(), 49), Direction.TOP));
					}

					case RIGHT -> {
						return new Pair<>(4, new Pair<>(new Position(pos.getY(), 49), Direction.TOP));
					}

					case DOWN -> {
						return new Pair<>(2, new Pair<>(new Position(pos.getX(), 0), Direction.DOWN));
					}

					default -> {
						return new Pair<>(1, new Pair<>(new Position(pos.getY(), 0), Direction.DOWN));
					}
				}
			}
		}

		throw new IllegalArgumentException("no god! pls no! nooooooooo");
	}

}
