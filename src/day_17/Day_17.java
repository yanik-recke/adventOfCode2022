package day_17;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
	
	
	/**
	 * In entsprechender Reihenfolge wird immer
	 * ein neuer Stein gespawnt und fällt solange, bis er
	 * entweder mit dem Boden oder mit einem schon
	 * festliegenden Stein kollidiert. Solange bis 2022
	 * Steine gefallen sind.
	 * 
	 * @param path - Pfad zum Puzzle Input
	 * @return - Höhe der gefallenen Steine
	 */
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
		int lastInputIdx = 0;
		Position currHighest = new Position(0, -1);
		
		while (counter < 2022) {
			boolean falling = true;
			Stone stone = new Stone(currHighest, types.get(idx));
			
			while (falling) {
				if (lastInputIdx == input.length()) {
					lastInputIdx = 0;
				}
				
				for (int i = lastInputIdx; i < input.length() && falling; i++) {
					lastInputIdx = i + 1;
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
						// prüfen, ob nach aktuellem Fallen, schon settled
					} else {
						// Stein fällt nicht mehr, also alle Einzelteile zu settled hinzufügen
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
		
		return currHighest.getY() + 1;
	}
	
	
	/**
	 * Berechnet die Höhe des Turms nach 1.000.000.000.000
	 * Steinen. Da sich sowohl die Steine, als auch der Input
	 * wiederholen, wird es irgendwann Zyklen geben. Die Suche nach einem
	 * Zyklus beginnt erst ab 20.000 Steinen, da es zu Beginn
	 * keinen Zyklus gibt. Sobald ein Zyklus gefunden wurde, wird berechnet
	 * wie viele Zyklen noch durchlaufen können bis 1.000.000.000.000 erreicht sind.
	 * Sollten noch Steine bis 1.000.000.000.000 fehlen, wird die Höhe durch diese
	 * danach hinzuaddiert.
	 * 
	 * @param path - Pfad zum Puzzle Input
	 * @return - die Höhe des Turms
	 */
	private static long part2(String path) {
		String input = helpers.HelperMethods.getInputAsListOfString(path).get(0);

		Set<Position> settled = new HashSet<>();
		List<String> moves = new ArrayList<>();
		List<StoneType> types = new ArrayList<>(Arrays.asList(
													StoneType.HORIZONTAL_LINE, 
													StoneType.CROSS, 
													StoneType.REVERSED_L, 
													StoneType.LINE, 
													StoneType.DICE));
		
		int idx = 0;
		long counter = 0;
		
		// Zum Finden eines Zyklus
		long cycleIdx = 0;
		String expected = "";
		int idxExpected = 0;
		int currSize = 0;
		
		// Ob evlt. ein Zyklus gefunden wurde
		boolean possible = false;
		
		// Endergebnis
		long result = 0;
		
		// Speichern, bei welchem Index des Inputs man sich befindet
		int lastInputIdx = 0;
		
		// Aktuell höchste Position
		Position currHighest = new Position(0, -1);
		
		// Höhe zu Beginn und Ende eines Zyklus
		int heightBeginningCycle = 0;
		int heightEndCycle = 0;
		boolean foundCycle = false;
		
		// Zum Speichern, wie viel Höhe durch einen Stein dazukommt
		Map<Integer, Integer> gainsPerStone= new HashMap<>();
		int idxGains = 1;
		
		//1.000.000.000.000
		while (counter < 1000000000000L && !foundCycle) {
			boolean falling = true;
			Stone stone = new Stone(currHighest, types.get(idx));
			StringBuilder sequence = new StringBuilder();
			
			while (falling && !foundCycle) {
				if (lastInputIdx == input.length()) {
					lastInputIdx = 0;
				}
				
				for (int i = lastInputIdx; i < input.length() && falling && !foundCycle; i++) {
					lastInputIdx = i + 1;
					// links, sonst rechts
					
					if (input.charAt(i) == '<') {
						sequence.append('<');
						if (stone.canMoveLeft(settled)) {
							stone.moveLeft();
						}
					} else {
						sequence.append('>');
						if (stone.canMoveRight(settled, 7)) {
							stone.moveRight();
						}
					}
					
					// wenn Weg nach unten frei ist, fallen, sonst...
					if (stone.canMoveDown(settled)) {
						stone.moveDown();
						// prüfen, ob nach aktuellem Fallen, schon settled
					} else {
						// Stein fällt nicht mehr, also alle Einzelteile zu settled hinzufügen
						falling = false;
						String move = stone.getType() + " - " + sequence.toString();
						
						if (counter >= 20000) {
							if (counter == 20000) {
								heightBeginningCycle = currHighest.getY() + 1;
							}
							if (!possible && moves.contains(move) && moves.get(idxExpected).equals(move)) {
								currSize = moves.size();
								expected = moves.get(++idxExpected);
								possible = true;
								cycleIdx = counter;
								heightEndCycle = currHighest.getY() + 1;
							} else if (possible) {
								if (move.equals(expected)) {
									// Zyklus gefunden:
									if ((idxExpected) == (currSize - 1)) {
										foundCycle = true;
										// Länge eines Zyklus:
										long cycleLen = cycleIdx - 20000;
										// Höhe, die durch einen Zyklus dazugekommen ist
										long gainedHeight = heightEndCycle - heightBeginningCycle;
										// Anzahl, die noch fallen muss
										long toDrop = 1000000000000L - 20000;
										// Durchläufe bis zum letzten vollständigen Zyklus
										long runs = toDrop / cycleLen;
										// Durchläufe die noch fehlen
										long runsLeft = toDrop - runs * cycleLen;
										// Berechnung der Höhe, die durch die noch fehlenden Steine dazukommt
										long missingHeight = 0;
										for (int j = 1; j <= runsLeft; j++) {
											missingHeight += gainsPerStone.get(j);
										}
										
										// Berechnung des Endergebnis
										long temp = heightBeginningCycle + runs * gainedHeight;
										result = temp + missingHeight;
									} else {
										// Zyklus noch nicht gefunden, aber noch möglich
										expected = moves.get(++idxExpected);
									}
								// Zyklus nicht mehr möglich, Suche beginnt von vorne
								} else {
									gainsPerStone = new HashMap<>();
									idxGains = 0;
									expected = "";
									idxExpected = 0;
									possible = false;
									currSize = 0;
								}
							}
							
							moves.add(move);
						}
							
						
						// nur, wenn keine Wiederholung gefunden wurde
						if (!foundCycle) {
							for (Position pos : stone.getAllPositions()) {
								settled.add(pos);
							}
					
							// Speicherung der dazugekommenen Höhe pro Stein erst, wenn nach Zyklus gesucht wird
							if (counter >= 20000) {
								if ((stone.getHighest().getY() + 1) - (currHighest.getY() + 1) >= 0) {
									gainsPerStone.put(idxGains, (stone.getHighest().getY() + 1) - (currHighest.getY() + 1));
								} else {
									gainsPerStone.put(idxGains, 0);
								}
								idxGains++;
							}
							
							counter++;

							// Neue höchste Position
							if (stone.getHighest().getY() > currHighest.getY()) {
								currHighest = stone.getHighest();
							}
						
							// Wieder beim ersten Stein beginnen
							if ((idx + 1) >= types.size()) {
								idx = 0;
							} else {
								idx++;
							}
						}
					}
				}
			}
		}
		
		return result;
	}
	
}
