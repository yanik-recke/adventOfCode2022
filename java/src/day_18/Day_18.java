package day_18;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * Lösung für Tag 18.
 * 
 * @author Yanik Recke
 */
public class Day_18 {

	public static void main(String[] args) {
		String pathToInput = "src/day_18/input.txt";
		
		System.out.println(part1(helpers.HelperMethods.getInputAsListOfString(pathToInput)) + " - " + part2(pathToInput));
	}
	
	
	/**
	 * Für jede Koordinate die Nachbarn prüfen,
	 * wenn ein Nachbar existiert, dann den Zähler nicht 
	 * aufaddieren. Wenn kein Nachbar existiert, dann
	 * den Zähler um Eins erhöhen.
	 * 
	 * @param path - Pfad zum Puzzle Input
	 * @return - Anzahl an freistehenden Flächen
	 */
	private static int part1(List<String> input) {
		
		// Maximum finden
		int max_x = Integer.MIN_VALUE;
		int max_y = Integer.MIN_VALUE;
		int max_z = Integer.MIN_VALUE;
		
		for (String line : input) {
			String[] temp = line.split(",");
			
			if (max_x < Integer.parseInt(temp[0])) {
				max_x = Integer.parseInt(temp[0]);
			}
			
			if (max_y < Integer.parseInt(temp[1])) {
				max_y = Integer.parseInt(temp[1]);
			}
			
			if (max_z < Integer.parseInt(temp[2])) {
				max_z = Integer.parseInt(temp[2]);
			}
		}
		
		// Koordinatensystem initialisieren und befüllen
		int[][][] field = new int[max_x + 2][max_y + 2][max_z + 2];
		
		for (String line : input) {
			String[] temp = line.split(",");
			int x = Integer.parseInt(temp[0]);
			int y = Integer.parseInt(temp[1]);
			int z = Integer.parseInt(temp[2]);
			
			// 1 -> droplet, 0 -> Kein droplet
			field[x][y][z] = 1;
		}
		
		int counter = 0;
		
		for (int x = 0; x < field.length; x++) {
			for (int y = 0; y < field[x].length; y++) {
				for (int z = 0; z < field[x][y].length; z++) {
					if (field[x][y][z] == 1) {
						// Nachbar in negativ x-Richtung
						if ((x - 1 < 0) || (field[x - 1][y][z] == 0)) {
							counter++;
						}
					
						// Nachbar in negativ y-Richtung
						if ((y - 1 < 0) || (field[x][y - 1][z] == 0)) {
							counter++;
						}
					
						// Nachbar in negativ z-Richtung
						if ((z - 1 < 0) || (field[x][y][z - 1] == 0)) {
							counter++;
						}
					
						// Nachbar in positiver x-Richtung
						if ((x + 1 >= field.length) || (field[x + 1][y][z] == 0)) {
							counter++;
						}
					
						// Nachbar in positiver y-Richtung
						if ((y + 1 >= field[x].length)|| (field[x][y + 1][z] == 0)) {
							counter++;
						}
					
						// Nachbar in positiver z-Richtung
						if ((z + 1 >= field[x][y].length) || (field[x][y][z + 1] == 0)) {
							counter++;
						}
					}
				}
			}
		}
		
		return counter;
	}
	
	
	/**
	 * Von jeder Position aus prüfen, ob diese einen Weg
	 * nach "draußen" hat, also von Luft berührt werden kann.
	 * Die Wege speichern, damit man so wenig wie 
	 * möglich doppelt prüft. Alle die keinen Weg nach "draußen" haben
	 * werden dem Input hinzugefügt und dann wird part1() mit dem
	 * neuen Input aufgerufen.
	 * 
	 * @param path - Pfad zum Puzzle Input
	 * @return - Anzahl an Oberflächen, die von Luft berührt werden
	 */
	private static int part2(String path) {
		List<String> input = helpers.HelperMethods.getInputAsListOfString(path);
		
		// Maximum finden
		int max_x = Integer.MIN_VALUE;
		int max_y = Integer.MIN_VALUE;
		int max_z = Integer.MIN_VALUE;
		
		for (String line : input) {
			String[] temp = line.split(",");
			
			if (max_x < Integer.parseInt(temp[0])) {
				max_x = Integer.parseInt(temp[0]);
			}
			
			if (max_y < Integer.parseInt(temp[1])) {
				max_y = Integer.parseInt(temp[1]);
			}
			
			if (max_z < Integer.parseInt(temp[2])) {
				max_z = Integer.parseInt(temp[2]);
			}
		}
		
		System.out.println(max_x);
		System.out.println(max_y);
		System.out.println(max_z);
		
		// Koordinatensystem initialisieren und befüllen
		int[][][] field = new int[max_x + 1][max_y + 1][max_z + 1];
		
		for (String line : input) {
			String[] temp = line.split(",");
			int x = Integer.parseInt(temp[0]);
			int y = Integer.parseInt(temp[1]);
			int z = Integer.parseInt(temp[2]);
			
			// 1 -> droplet, 0 -> Kein droplet
			field[x][y][z] = 1;
		}
		
		Set<Position> all = new HashSet<>();
		
		for (int x = 0; x < field.length; x++) {
			for (int y = 0; y < field[x].length; y++) {
				for (int z = 0; z < field[x][y].length; z++) {
					
					if (!all.contains(new Position(x, y, z))) {
						List<Position> temp = canBeTouchedByAir(new Position(x, y, z), field, all);
						
						if (!temp.isEmpty()) {
							for (Position pos : temp) {
								input.add(pos.getX() + "," + pos.getY() + "," + pos.getZ());
							} 
						
							all.addAll(temp);
						}
					}
				}
			}
		}

		
		return part1(input);
	}
	
	
	/**
	 * Ermittelt, ob eine Position einen Weg nach
	 * "draußen" hat.
	 * 
	 * @param pos - die Position
	 * @param field - das aktuelle Feld
	 * @param all - alle Positionen, die schon einen Weg nach draußen haben
	 * @return - wenn kein Weg nach draußen gefunden wurde, eine leere Liste, sonst eine Liste,
	 * 			 die einen Pfad nach draußen abbildet
	 */
	private static List<Position> canBeTouchedByAir(Position pos, int[][][] field, Set<Position> all) {
		Set<Position> visited = new HashSet<>();
		Queue<Position> toCheck = new LinkedList<>();
		
		toCheck.add(pos);
		visited.add(pos);
		
		while(!toCheck.isEmpty()) {
			Position currPos = toCheck.poll();
			if (all.contains(currPos)) {
				return new ArrayList<>(visited);
			}
			
			//System.out.println(currPos);
			int x = currPos.getX();
			int y = currPos.getY();
			int z = currPos.getZ();
			
			if (field[x][y][z] != 1) {
				// prüfen, ob außerhalb
				boolean found = false;
				boolean isInside = true;
				boolean[] foundDir = new boolean[6];
				
				for (int tempX = (x - 1); tempX >= 0 && !found; tempX--) {
					if (field[tempX][y][z] == 1) {
						found = true;
					}
				}
				
				foundDir[0] = found;
				
				
				found = false;
				for (int tempY = (y - 1); tempY >= 0 && !found; tempY--) {
					if (field[x][tempY][z] == 1) {
						found = true;
					}
				}
				
				foundDir[1] = found;
				

				found = false;
				for (int tempZ = (z - 1); tempZ >= 0 && !found; tempZ--) {
					if (field[x][y][tempZ] == 1) {
						found = true;
					}
				}

				foundDir[2] = found;
				

				found = false;
				for (int tempX = (x + 1); tempX < field.length && !found; tempX++) {
					if (field[tempX][y][z] == 1) {
						found = true;
					}
				}

				foundDir[3] = found;
				

				found = false;
				for (int tempY = (y + 1); tempY < field[x].length && !found; tempY++) {
					if (field[x][tempY][z] == 1) {
						found = true;
					}
				}

				foundDir[4] = found;
				

				found = false;
				for (int tempZ = (z + 1); tempZ < field[x][y].length && !found; tempZ++) {
					if (field[x][y][tempZ] == 1) {
						found = true;
					}
				}
				
				foundDir[5] = found;
				
				
				for (boolean val : foundDir) {
					if (!val) {
						isInside = false;
					}
				}
				
				if (isInside) {
					// Nachbar in negativ x-Richtung
					if ((x - 1 >= 0) && !visited.contains(new Position(x - 1, y, z))) {
						if (field[x - 1][y][z] == 0) {
							toCheck.add(new Position(x - 1, y, z));
							visited.add(currPos);
						}
					}
				
					// Nachbar in negativ y-Richtung
					if (y - 1 >= 0 && !visited.contains(new Position(x, y - 1, z))) {
						if (field[x][y - 1][z] == 0) {
							toCheck.add(new Position(x, y - 1, z));
							visited.add(currPos);
						}
					}
				
					// Nachbar in negativ z-Richtung
					if (z - 1 >= 0 && !visited.contains(new Position(x, y, z - 1))) {
						if (field[x][y][z - 1] == 0) {
							toCheck.add(new Position(x, y, z - 1));
							visited.add(currPos);
						}
					}
				
					// Nachbar in positiver x-Richtung
					if (x + 1 < field.length && !visited.contains(new Position(x + 1, y, z))) {
						if (field[x + 1][y][z] == 0) {
							toCheck.add(new Position(x + 1, y, z));
							visited.add(currPos);
						}
					}
				
					// Nachbar in positiver y-Richtung
					if (y + 1 < field[x].length && !visited.contains(new Position(x, y + 1, z))) {
						if (field[x][y + 1][z] == 0) {
							toCheck.add(new Position(x, y + 1, z));
							visited.add(currPos);
						}
					}
				
					// Nachbar in positiver z-Richtung
					if (z + 1 < field[x][y].length && !visited.contains(new Position(x, y, z + 1))) {
						if (field[x][y][z + 1] == 0) {
							toCheck.add(new Position(x, y, z + 1));
							visited.add(currPos);
						}
					}
				} else {
					return new ArrayList<>();
				}
			}
		}
		
		
		return new ArrayList<>(visited);
	}
	
}
