package day_10;

import java.util.List;

/**
 * Lösung für Tag 10. 
 * 
 * @author Yanik Recke
 */
public class Day_10 {

	public static void main(String[] args) {
		String pathToInput = "src/day_10/input.txt";
		
		System.out.println(part1(pathToInput) + " - \n" + part2(pathToInput));
	}
	
	
	/**
	 * Entsprechenden Operationen ausführen und
	 * dabei den aktuellen Cycle prüfen bzw. hochzählen.
	 * 
	 * @param path - Pfad zum Puzzle Input
	 * @return - Aufaddierte Signalstärke
	 */
	private static int part1(String path) {
		int cycle = 0;
		int x = 1;
		String currOp = "";
		
		int signalStrength = 0;
		
		List<String> input = helpers.HelperMethods.getInputAsListOfString(path);
		
		for (String line : input) {
			currOp = line.substring(0, 4);

			if (currOp.equals("noop")) {
				cycle++;
					
				if (cycle == 20 || cycle == 60 || cycle == 100 || cycle == 140 || cycle == 180 || cycle == 220) {
					signalStrength += cycle * x;
				}
				
			} else if (currOp.equals("addx")) {
				for (int i = 0; i < 2; i++) {
					++cycle;
					if (cycle == 20 || cycle == 60 || cycle == 100 || cycle == 140 || cycle == 180 || cycle == 220) {
						signalStrength += cycle * x;
					}
				}
				
				x+= Integer.parseInt(line.substring(line.indexOf(' ') + 1, line.length()));
			}
		}
		
		return signalStrength;
	}

	/**
	 * Nun Position hochzählen bis 40, dann Absatz anhängen.
	 * Nur malen, wenn x-1, x oder x+1 = currPos sind.
	 * 
	 * @param path - Pfad zum Puzzle Input
	 * @return - Gemaltes Bild
	 */
	private static String part2(String path) {
		String output = "";
		int currPos = 0;
		int x = 1;
		String currOp = "";
		
		List<String> input = helpers.HelperMethods.getInputAsListOfString(path);
		
		for (String line : input) {
			currOp = line.substring(0, 4);
			
			if (currOp.equals("noop")) {
				
				if (currPos == 40) {
					output = output.concat("\n");
					currPos = 0;
				}
				
				output = check(output, x, currPos);
				
				currPos++;
				
			} else if (currOp.equals("addx")) {
				for (int i = 0; i < 2; i++) {
					
					if (currPos == 40) {
						output = output.concat("\n");
						currPos = 0;
					}
					
					output = check(output, x, currPos);
					
					currPos++;
				}
				
				x+= Integer.parseInt(line.substring(line.indexOf(' ') + 1, line.length()));
			}
			
		}
		
		
		return output;
	}
	
	/**
	 * Prüfen ob sich x und pos berühren.
	 * 
	 * @param output - an den anzuhängenden Output
	 * @param x - x-Wert
	 * @param pos - horizontale Position
	 * @return - neuen Output mit entweder "." oder "#" angehängt
	 */
	private static String check(String output, int x, int pos) {
		String temp = new String(output);
		
		if (x - 1 == pos || x == pos || x + 1 == pos) {
			temp = temp.concat("#");
		} else {
			temp = temp.concat(".");
		}
		
		return temp;
	}
	
}
