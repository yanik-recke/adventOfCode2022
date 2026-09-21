package day_14;

import java.util.ArrayList;
import java.util.List;


/**
 * Lösung für Tag 14.
 * 
 * @author Yanik Recke
 *
 */
public class Day_14 {

	
	public static void main(String[] args) {
		String pathToInput = "src/day_14/input.txt";
		
		System.out.println(part1(pathToInput) + " - " + part2(pathToInput));
	}
	
	
	/**
	 * Spielfeld zeichnen und Stein Strukturen "einbauen".
	 * Startposition des Sands ermitteln und solange bis
	 * ein Sandkorn fest liegt Schritte durchgehen. Wenn es fest 
	 * liegt das nächste Sandkorn fallen lassen. 
	 * Solange durchführen bis eins aus dem Spielfeld fällt.
	 * 
	 * @param path - Pfad zum Puzzle Input
	 * @return - Anzahl an fest liegenden Sandkörnern
	 */
	private static int part1(String path) {
		
		List<String[]> input = new ArrayList<>();
		
		for (String line : helpers.HelperMethods.getInputAsListOfString(path)) {
			input.add(line.split(" -> "));
		}
		
		int minX = Integer.MAX_VALUE;
		int maxX = 0;
		int maxY = 0;
		int temp = 0;
		
		for (String[] line : input) {
			for (int i = 0; i < line.length; i++) {
				if ((temp = Integer.parseInt(line[i].substring(0, line[i].indexOf(',')))) > maxX) {
					maxX = temp;
				} else if (temp < minX) {
					minX = temp;
				}
				
				if ((temp = Integer.parseInt(line[i].substring(line[i].indexOf(',') + 1))) > maxY) {
					maxY = temp;
				}
			}
		}
		
		char[][] field = new char[maxX - minX + 1][maxY + 1];
		
		for (int x = 0; x < field.length; x++) {
			for (int y = 0; y < field[x].length; y++) {
				field[x][y] = '.';
			}
		}

		
		for (String[] line : input) {
			field = drawRocks(line, field, minX, 0);
		}
		
		
		Position spawn = new Position(500 - minX, 0);
		boolean done = false;
		int counter = 0;
		
		while (!done) {
			int x = spawn.getX();
			int y = spawn.getY();
			
			field[x][y] = '+';
			
			boolean settled = false;
			boolean in = true;
			while (!settled) {
				if (in && (in = isInbounds(field, x, y + 1)) && (field[x][y + 1] != '#' && field[x][y + 1] != '+')) {
					field[x][y] = '.';
					field[x][++y] = '+';
				} else if (in && (in = isInbounds(field, x - 1, y + 1)) && (field[x - 1][y + 1] != '#' && field[x - 1][y + 1] != '+')) {
					field[x][y] = '.';
					field[--x][++y] = '+';
				} else if (in && (in = isInbounds(field, x + 1, y + 1)) && (field[x + 1][y + 1] != '#' && field[x + 1][y + 1] != '+')) {
					field[x][y] = '.';
					field[++x][++y] = '+';
				} else {
					//draw(field);
					counter++;
					settled = true;
				}
				
				done = !in;
			}
		}
		
		draw(field);
		return --counter;
	}
	
	
	/**
	 * Es gibt keine unendliche Tiefe mehr, sondern einen
	 * unendlich langen Boden. Spielfeld "vergrößern" und so Boden
	 * simulieren. Solange Sand fallen lassen, bis der Spawnpunkt
	 * verdeckt ist.
	 * 
	 * @param path - Pfad zum Puzzle Input
	 * @return - Anzahl fest liegender Sandkörner
	 */
	private static int part2(String path) {

		List<String[]> input = new ArrayList<>();
		
		for (String line : helpers.HelperMethods.getInputAsListOfString(path)) {
			input.add(line.split(" -> "));
		}
		
		int minX = Integer.MAX_VALUE;
		int maxX = 0;
		int maxY = 0;
		int temp = 0;
		
		for (String[] line : input) {
			for (int i = 0; i < line.length; i++) {
				if ((temp = Integer.parseInt(line[i].substring(0, line[i].indexOf(',')))) > maxX) {
					maxX = temp;
				} else if (temp < minX) {
					minX = temp;
				}
				
				if ((temp = Integer.parseInt(line[i].substring(line[i].indexOf(',') + 1))) > maxY) {
					maxY = temp;
				}
			}
		}
		
		int plus = 5000;
		char[][] field = new char[maxX - minX + 1 + plus * 2][maxY + 3];
		
		for (int x = 0; x < field.length; x++) {
			for (int y = 0; y < field[x].length; y++) {
				field[x][y] = '.';
			}
		}

		
		for (String[] line : input) {
			field = drawRocks(line, field, minX, plus);
		}
		
		
		for (int x = 0; x < field.length; x++) {
			field[x][field[x].length - 1] = '#';
		}
		
		
		Position spawn = new Position(500 - minX + plus, 0);
		boolean done = false;
		int counter = 0;
		
		while (!done) {
			int x = spawn.getX();
			int y = spawn.getY();
			
			field[x][y] = '+';
			
			boolean settled = false;
			boolean in = true;
			while (!settled) {
				if (in && (in = isInbounds(field, x, y + 1)) && (field[x][y + 1] != '#' && field[x][y + 1] != '+')) {
					field[x][y] = '.';
					field[x][++y] = '+';
				} else if (in && (in = isInbounds(field, x - 1, y + 1)) && (field[x - 1][y + 1] != '#' && field[x - 1][y + 1] != '+')) {
					field[x][y] = '.';
					field[--x][++y] = '+';
				} else if (in && (in = isInbounds(field, x + 1, y + 1)) && (field[x + 1][y + 1] != '#' && field[x + 1][y + 1] != '+')) {
					field[x][y] = '.';
					field[++x][++y] = '+';
				} else {
					counter++;
					settled = true;
				}
				
				if (settled && x == (500 - minX + plus) && y == 0) {
					done = true;
				}
			}
		}

		return counter;
	}
	
	
	/**
	 * Prüft ob eine Position in den Grenzen eines 
	 * Arrays liegt.
	 * 
	 * @param arr - der Array
	 * @param x - x-Koordinate
	 * @param y - y-Koordinate
	 * @return - true, wenn Koordinaten innerhalb liegen, false wenn nicht
	 */
	public static boolean isInbounds(char[][] arr, int x, int y) {
		return x >= 0 && y >= 0 && x < arr.length && y < arr[x].length;
	}
	
	
	/**
	 * "Zeichnet" die Stein Strukturen in das Feld ein. 
	 * Linien von einer angegebenen Position bis zur nächsten.
	 * 
	 * @param line - String Array mit Positionsangaben, die verbunden werden sollen
	 * @param field - Spielfeld
	 * @param minX - kleinste x-Koordinate aus dem Input
	 * @param plus - Summand um den das Spielfeld vergrößert werden muss (part 2)
	 * @return - Spielfeld mit eingezeichneten Stein Strukturen
	 */
	private static char[][] drawRocks(String[] line, char[][] field, int minX, int plus) {
		for (int i = 0; i < line.length; i++) {
			int x = Integer.parseInt(line[i].substring(0, line[i].indexOf(','))) - minX + plus;
			int y = Integer.parseInt(line[i].substring(line[i].indexOf(',') + 1));
			field[x][y] = '#';
			int tempX = 0;
			int tempY = 0;
			
			if ((i + 1) == line.length) {
				field[x][y] = '#';
			} else {
				tempX = Integer.parseInt(line[i + 1].substring(0, line[i + 1].indexOf(','))) - minX + plus;
				tempY = Integer.parseInt(line[i + 1].substring(line[i + 1].indexOf(',') + 1));
				if (x == tempX) {
					// check y
					if (y  == tempY) {
						// nun
					} else if (y < tempY) {
						// go down
						while (y < tempY) {
							field[x][y] = '#';
							y++;
						}
					} else {
						while (y > tempY) {
							field[x][y] = '#';
							y--;
						}
					}
				} else if (x > tempX) {
					// go left
					while (x > tempX) {
						field[x][y] = '#';
						x--;
					}
				} else {
					// go right
					while (x < tempX) {
						field[x][y] = '#';
						x++;
					}
				}
			}
		}
		
		return field;
	}
	
	
	/**
	 * Malt das Spielfeld.
	 * 
	 * @param field - das zumalende Spielfeld
	 */
	private static void draw(char[][] field) {
		for (int x1 = 0; x1 < field[0].length; x1++) {
			for (int y1 = 0; y1 < field.length; y1++) {
				System.out.print(field[y1][x1]);
			}
			
			System.out.println();
		}
	}
	
}
