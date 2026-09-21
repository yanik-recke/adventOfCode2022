package day_9;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Lösung für Tag 9.
 * 
 * @author Yanik Recke
 *
 */
public class Day_9 {

	public static void main(String[] args) {
		String pathToInput = "src/day_9/input.txt";

		System.out.println(part1(pathToInput) + " - " + part2(pathToInput));
	}
	
	
	/**
	 * Neue Position des Heads berechnen und dann, wenn
	 * nötig, neue Position des Tails berechnen. Nach jeder
	 * Berechnung die Position des Tails speichern, wenn sie noch
	 * nicht berührt wurde.
	 * 
	 * @param path - Pfad zum Puzzle Input
	 * @return - Anzahl der berührten Positionen
	 */
	private static int part1(String path) {
		List<String> input = helpers.HelperMethods.getInputAsListOfString(path);
		int moveCount = 0;
		char direction;
		Position posTail = new Position(0,0);
		Position posHead = new Position(0,0);
	
		Set<Position> touched = new HashSet<>();
		touched.add(posTail);
		
		
		for (String line : input) {
			direction = line.charAt(0);
			moveCount = Integer.parseInt(line.substring(2, line.length()));
			
			for (int i = 0; i < moveCount; i++) {
				switch (direction) {
					case 'U' -> {
						posHead = new Position(posHead.getX(), posHead.getY() - 1);
					}
					
					case 'R' -> {
						posHead = new Position(posHead.getX() + 1, posHead.getY());
					}
					
					case 'D' -> {
						posHead = new Position(posHead.getX(), posHead.getY() + 1);
					}
					
					case 'L' -> {
						posHead = new Position(posHead.getX() - 1, posHead.getY());
					}
				}
				
				if (!areTouching(posHead, posTail)) {
					posTail = calculateNewTailsPos(posHead, posTail);
					touched.add(posTail);
				}
			}
		}
		
		return touched.size();
	}
	
	
	/**
	 * Neue Position des Heads berechnen, 
	 * dann für das folgende Element, dann für das 
	 * nächste Element usw.
	 * Positionen, die vom letzten Element berührt werden,
	 * speichern.
	 * 
	 * @param path - Pfad zum Puzzle Input
	 * @return - Anzahl berührter Positionen
	 */
	private static int part2(String path) {
		List<String> input = helpers.HelperMethods.getInputAsListOfString(path);
		int moveCount = 0;
		char direction;
		Position[] posHead = new Position[10];
	
		for (int i = 0; i < posHead.length; i++) {
			posHead[i] = new Position(0,0);
		}
		
		Set<Position> touched = new HashSet<>();
		touched.add(posHead[9]);
		
		for (String line : input) {
			direction = line.charAt(0);
			moveCount = Integer.parseInt(line.substring(2, line.length()));
			
			for (int i = 0; i < moveCount; i++) {
				switch (direction) {
					case 'U' -> {
						posHead[0] = new Position(posHead[0].getX(), posHead[0].getY() - 1);
					}
					
					case 'R' -> {
						posHead[0] = new Position(posHead[0].getX() + 1, posHead[0].getY());
					}
					
					case 'D' -> {
						posHead[0] = new Position(posHead[0].getX(), posHead[0].getY() + 1);
					}
					
					case 'L' -> {
						posHead[0] = new Position(posHead[0].getX() - 1, posHead[0].getY());
					}
				}
				
				for (int j = 1; j < posHead.length; j++) {
					if (!areTouching(posHead[j - 1], posHead[j])) {
						posHead[j] = calculateNewTailsPos(posHead[j - 1], posHead[j]);
						
						// Letzter durchlauf = tail
						if (j == posHead.length - 1) {
							touched.add(posHead[j]);
						}
					}
				}
			}
		}
		
		return touched.size();
	}
	
	
	/**
	 * Prüft, ob sich zwei Positionen berühren.
	 * 
	 * @param head - Position 1
	 * @param tails - Position 2
	 * @return - true, wenn sie sich berühren, false wenn nicht
	 */
	private static boolean areTouching(Position head, Position tails) {
		return (head.getX() == tails.getX() || head.getX() - 1 == tails.getX() || head.getX() + 1 == tails.getX()) && (head.getY() == tails.getY() || head.getY() - 1 == tails.getY() || head.getY() + 1 == tails.getY());
	}
	
	
	/**
	 * Berechnet die Position, die eingenommen werden muss,
	 * damit die die Position tails, wieder head berührt.
	 * 
	 * @param head - Position die berührt werden soll
	 * @param tail - Position die an head anschließen soll
	 * @return - die neue Position
	 */
	private static Position calculateNewTailsPos(Position head, Position tail) {
		// Wenn in gleicher Spalte
		if (head.getX() == tail.getX()) {
			if (head.getY() > tail.getY()) {
				return new Position(tail.getX(), tail.getY() + 1);
			} else {
				return new Position(tail.getX(), tail.getY() - 1);
			}
		// Wenn in gleicher Reihe
		} else if (head.getY() == tail.getY()) {
			if (head.getX() > tail.getX()) {
				return new Position(tail.getX() + 1, tail.getY());
			} else {
				return new Position(tail.getX() - 1, tail.getY());
			}
		// Wenn weder in gleicher Spalte, noch Reihe
		} else if (head.getX() != tail.getX() && head.getY() != tail.getY()) {
			int tempX = 0;
			int tempY = 0;
			
			if (tail.getX() < head.getX()) {
				tempX = tail.getX() + 1;
			} else if (tail.getX() > head.getX()) {
				tempX = tail.getX() - 1;
			}
			
			if (tail.getY() > head.getY()) {
				tempY = tail.getY() - 1;
			} else if (tail.getY() < head.getY()) {
				tempY = tail.getY() + 1;
			}
		
			
			return new Position(tempX, tempY);
		}
		
		return null;
	}
	
}
