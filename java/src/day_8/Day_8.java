package day_8;

/**
 * Lösung für Tag 8.
 * 
 * @author Yanik Recke
 */
public class Day_8 {

	public static void main(String[] args) {
		String pathToInput = "src/day_8/input.txt";
		
		System.out.println(part1(pathToInput) + " - " + part2(pathToInput));
	}
	
	
	/**
	 * Prüfen jedes Baums, wenn von oben keiner größer
	 * ist, ist er sichtbar und kann mitgezählt werden.
	 * Wenn er von oben nicht sichtbar ist, müssen auch die anderen
	 * Richtungen geprüft werden. Wenn er aus keiner der anderen
	 * Richtungen sichtbar ist, darf er nicht gezählt werden.
	 * 
	 * @param path - Pfad zum Puzzle Input
	 * @return - Anzahl an sichtbaren Bäumen
	 */
	private static int part1(String path) {
		int[][] trees = helpers.HelperMethods.getInputAsTwoDimensionalArray(path);
		int sum = 0;
		
		int currX = 0;
		int currY = 0;
		
		int currHeight = 0;
		
		boolean visible = true;
		
		for (int x = 0; x < trees.length; x++) {
			for (int y = 0; y < trees[x].length; y++) {
				if (x == 0 || y == 0 || x == (trees.length - 1) || y == (trees[x].length - 1)) {
					sum++;
				} else {
					currX = x;
					currY = y;
					currHeight = trees[x][y];
					
					visible = true;
					for (int x1 = 0; x1 < currX && visible; x1++) {
						if (trees[x1][y] >= currHeight) {
							visible = false;
						}
					}

					if (!visible) {
						visible = true;
						for (int y1 = 0; y1 < currY && visible; y1++) {
							if (trees[x][y1] >= currHeight) {
								visible = false;
							}
						}
						
						if (!visible) {
							visible = true;
							for (int x1 = trees.length - 1; x1 > currX && visible; x1--) {
								if (trees[x1][y] >= currHeight) {
									visible = false;
								}
							}
							
							if (!visible) {
								visible = true;
								for (int y1 = trees[x].length - 1; y1 > currY && visible; y1--) {
									if (trees[x][y1] >= currHeight) {
										visible = false;
									}
								}
							}
						}
					}
					
					if (visible) {
						sum++;
					}
				}
			}
		}
		
		return sum;
	}
	
	
	/**
	 * Von jedem Baum aus den "Viewing Score" berechnen, indem
	 * man erst nach oben, dann nach rechts, dann nach unten und
	 * dann nach links geht. Es wird immer mit dem aktuellen
	 * höchsten Score verglichen und wenn der neue größer ist,
	 * wird der neue zum höchsten.
	 * 
	 * @param path - Pfad zum Puzzle Input
	 * @return - Höchster Viewing Score der Bäume
	 */
	private static int part2(String path) {
		int[][] trees = helpers.HelperMethods.getInputAsTwoDimensionalArray(path);
		int highestScore = 0;
		int[] tempScore = new int[4];
		boolean done = false;
		
		for (int x = 0; x < trees.length; x++) {
			for (int y = 0; y < trees[x].length; y++) {
				int currHeight = trees[x][y];
				
				for (int i = 0; i < tempScore.length; i++) {
					tempScore[i] = 0;
				}
				done = false;
				for (int y1 = (y - 1); y1 >= 0 && !done; y1--) {
					if (trees[x][y1] < currHeight) {
						tempScore[0]++;
					} else {
						tempScore[0]++;
						done = true;
					}
				}
				
				done = false;
				for (int x1 = (x + 1); x1 < trees.length && !done; x1++) {
					if (trees[x1][y] < currHeight) {
						tempScore[1]++;
					} else {
						tempScore[1]++;
						done = true;
					}
				}
				
				done = false;
				for (int y1 = y + 1; y1 < trees[x].length && !done; y1++) {
					if (trees[x][y1] < currHeight) {
						tempScore[2]++;
					} else {
						tempScore[2]++;
						done = true;
					}
				}
				
				done = false;
				for (int x1 = (x - 1); x1 >= 0 && !done; x1--) {
					if (trees[x1][y] < currHeight) {
						tempScore[3]++;
					} else {
						tempScore[3]++;
						done = true;
					}
				}
				
				if (tempScore[0] * tempScore[1] * tempScore[2] * tempScore[3] > highestScore) {
					highestScore = tempScore[0] * tempScore[1] * tempScore[2] * tempScore[3];
				}
			}
		}
		
		return highestScore;
	}
	
	
}
