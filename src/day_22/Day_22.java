package day_22;

import java.util.ArrayList;
import java.util.List;

public class Day_22 {

	public static void main(String[] args) {
		String pathToInput = "src/day_22/input.txt";
		
		System.out.println(part1(pathToInput));
	}
	
	
	private static long part1(String path) {
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

		System.out.println(pos.getX() + " - " + pos.getY() + " - " + facing);
		return 1000 * (pos.getY() + 1) + 4 * (pos.getX() + 1) + facing.ordinal();
	}

}
