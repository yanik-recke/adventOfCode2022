package day_17;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Lösung für Tag 17.
 * 
 * @author Yanik Recke
 */
public class Day_17 {

	public static void main(String[] args) {
		String pathToInput = "src/day_17/input.txt";
		
		System.out.println(part1(pathToInput) + " - " + part2(pathToInput));
	}
	
	
	private static int part1(String path) {
		String input = helpers.HelperMethods.getInputAsListOfString(path).get(0);

		Set<Position> settled = new HashSet<>();
		List<StoneType> types = new ArrayList<>(Arrays.asList(
													StoneType.HORIZONTAL_LINE, 
													StoneType.CROSS, 
													StoneType.REVERSED_L, 
													StoneType.LINE, 
													StoneType.DICE));
		
		int idx = 0;
		int counter = 0;
		
		Position currHighest = new Position(0, 0);
		
		while (counter < 2022) {
			boolean falling = true;
			Stone stone = new Stone(currHighest, types.get(idx));
			
			while (falling) {
				for (int i = 0; i < input.length(); i++) {
					// links, sonst rechts
					if (input.charAt(i) == '<') {
						if (stone.canMoveLeft(settled)) {
							stone.moveLeft();
						}
					} else {
						if (stone.canMoveRight(settled, 7)) {
							stone.moveRight();
						}
					}
					
					// wenn Weg nach unten frei ist, fallen, sonst...
					if (stone.canMoveDown(settled)) {
						stone.moveDown();
					} else {
						// Stein fällt nicht mehr und alle Einzelteile zu settled hinzufügen
						falling = false;
						settled.addAll(stone.getAllPositions());
						counter++;

						if (stone.getHighest().getY() > currHighest.getY()) {
							currHighest = stone.getHighest();
						}
						
						if ((idx + 1) >= types.size()) {
							idx = 0;
						} else {
							idx++;
						}
					}
				}
			}
		}
		
		return currHighest.getY();
	}
	
	
	private static int part2(String path) {
		
		return 0;
	}
	
}
