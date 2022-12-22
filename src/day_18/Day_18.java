package day_18;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Lösung für Tag 18.
 * 
 * @author Yanik Recke
 */
public class Day_18 {

	public static void main(String[] args) {
		String pathToInput = "src/day_18/input.txt";
		parse(pathToInput);
		System.out.println(part1(pathToInput) + " - " + part2(pathToInput));
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
	private static int part1(String path) {
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
	 * Zähler nur noch hochzählen, wenn in die gesamte Richtung
	 * kein droplet gefunden wurde.
	 * 
	 * @param path - Pfad zum Puzzle Input
	 * @return - Anzahl von nach außen zeigender Flächen
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
		
		int counter = 0;
		
		boolean[] dirs = new boolean[6];
		
		
		
		for (int x = 0; x < field.length; x++) {
			for (int y = 0; y < field[x].length; y++) {
				for (int z = 0; z < field[x][y].length; z++) {
					if (field[x][y][z] == 1) {
						boolean isFacingOutwards = false;
						// Prüfen in negative x-Richtung
						boolean found = false;
						for (int tempX = (x - 1); tempX >= 0 && !found; tempX--) {
							if (field[tempX][y][z] == 1) {
								found = true;
							}
						}
						
						if (!found || (x - 1) < 0) {
							isFacingOutwards = true;
							dirs[0] = true;
						} else {
							dirs[0] = false;
						}
		
					
						// Prüfen in negative y-Richtung
						found = false;
						for (int tempY = (y - 1); tempY >= 0 && !found; tempY--) {
							if (field[x][tempY][z] == 1) {
								found = true;
							}
						}
						
						if (!found || (y - 1) < 0) {
							isFacingOutwards = true;
							dirs[1] = true;
						} else {
							dirs[1] = false;
						}
					
						// Prüfen in negative z-Richtung
						found = false;
						for (int tempZ = (z - 1); tempZ >= 0 && !found; tempZ--) {
							if (field[x][y][tempZ] == 1) {
								found = true;
							}
						}
						
						if (!found || (z - 1) < 0) {
							isFacingOutwards = true;
							dirs[2] = true;
						} else {
							dirs[2] = false;
						}
					
						// Prüfen in positive x-Richtung
						found = false;
						for (int tempX = (x + 1); tempX < field.length && !found; tempX++) {
							if (field[tempX][y][z] == 1) {
								found = true;
							}
						}
						
						if (!found || (x + 1) >= field.length) {
							isFacingOutwards = true;
							dirs[3] = true;
						} else {
							dirs[3] = false;
						}
					
						// Prüfen in positive y-Richtung
						found = false;
						for (int tempY = (y + 1); tempY < field[x].length && !found; tempY++) {
							if (field[x][tempY][z] == 1) {
								found = true;
							}
						}
						
						if (!found || (y + 1) >= field[x].length) {
							isFacingOutwards = true;
							dirs[4] = true;
						} else {
							dirs[4] = false;
						}
					
						// Prüfen in positive z-Richtung
						found = false;
						for (int tempZ = (z + 1); tempZ < field[x][y].length && !found; tempZ++) {
							if (field[x][y][tempZ] == 1) {
								found = true;
							}
						}
						
						if (!found || (z + 1) >= field[x][y].length) {
							isFacingOutwards = true;
							dirs[5] = true;
						} else {
							dirs[5] = false;
						}
						
						if (isFacingOutwards) {
							
						}
					}
				}
			}
		}
		
		
		// 4092 too high, 2038 too low, 2507
		return counter;
	}
	
	
	private static void parse(String path) {
		List<String> input = helpers.HelperMethods.getInputAsListOfString(path);
		int count = 0;
		
		String output = "{";
		
		for (String line : input) {
			output = output.concat("(" + line.replace(',', '|') + ")");
			count++;
		}
		
		output = output.concat("}");
		
		System.out.println(output);
	}
	
}
