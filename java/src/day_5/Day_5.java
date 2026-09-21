package day_5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Lösung für Tag 5.
 * 
 * @author Yanik Recke
 */
public class Day_5 {

	public static void main(String[] args) {
		String pathToInput = "src/day_5/input.txt";
		
		System.out.println(part1(pathToInput)  + " - " + part2(pathToInput));
	}
	
	
	/**
	 * Bestimmen der Anzahl der Kisten die verschoben werden
	 * soll, des Indexes des Ursprungsstapels und des Indexes
	 * des Zielstapels. Dann entsprechend verschieben. So Verschieben,
	 * dass die letzte verschobene Kiste immer oben ist.
	 * 
	 * @param path - Pfad zum Puzzle Input
	 * @return - Bezeichnungen der obersten Kisten
	 */
	private static String part1(String path) {
		// Wäre besser mit Liste von Queues
		List<Queue<Character>> stacks = fillQueue();
		
		int moveCount = 0;
		int source = 0;
		int destination = 0;
		int idx = 0;
		
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
		    String line = br.readLine();

		    while (line != null) {
		    	idx  = line.indexOf('e') + 2;
		    	moveCount = Integer.parseInt(line.substring(idx, line.indexOf(' ', idx + 1)));
		    	
		    	idx = line.lastIndexOf("m") + 2;
		    	source = Integer.parseInt(line.substring(idx, idx + 1));
		    	
		    	idx = line.lastIndexOf('o') + 2;
		    	destination = Integer.parseInt(line.substring(idx, line.length()));
		    	
		    	if (source != destination) {
		    		for (int i = 0; i < moveCount; i++) {
		    			stacks.get(destination - 1).add(stacks.get(source - 1).poll());
		    		}
		    	}

		        line = br.readLine();
		    }

		} catch (Exception e) {
			e.printStackTrace();
		} 

		StringBuilder temp = new StringBuilder();
		
		for (int i = 0; i < stacks.size(); i++) {
			if (stacks.get(i).size() > 0) {
				temp.append(stacks.get(i).poll());
			}
		}
		
		return temp.toString();
	}
	
	
	/**
	 * Bestimmen der Anzahl der Kisten die verschoben werden
	 * soll, des Indexes des Ursprungsstapels und des Indexes
	 * des Zielstapels. Dann entsprechend verschieben. So Verschieben,
	 * dass die Reihenfolge der verschobenen Kisten gleich bleibt.
	 * Das heißt, das oberste verschobene Element muss oben bleiben usw.
	 * 
	 * @param path - Pfad zum Puzzle Input
	 * @return - Bezeichnungen der obersten Kisten
	 */
	private static String part2(String path) {
		// Wäre besser mit Liste von Queues
		List<List<Character>> stacks = fillStacks();

		int moveCount = 0;
		int source = 0;
		int destination = 0;
		int idx = 0;
		
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
		    String line = br.readLine();

		    while (line != null) {
		    	idx  = line.indexOf('e') + 2;
		    	moveCount = Integer.parseInt(line.substring(idx, line.indexOf(' ', idx + 1)));
		    	
		    	idx = line.lastIndexOf("m") + 2;
		    	source = Integer.parseInt(line.substring(idx, idx + 1));
		    	
		    	idx = line.lastIndexOf('o') + 2;
		    	destination = Integer.parseInt(line.substring(idx, line.length()));
		    	
		    	// Einfügen so, dass die Reihenfolge der verschobenen unverändert bleibt
		    	if (source != destination) {
		    		for (int i = 0; i < moveCount; i++) {
		    			stacks.get(destination - 1).add(i, stacks.get(source - 1).remove(0));
		    		}
		    	}

		        line = br.readLine();
		    }

		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		StringBuilder temp = new StringBuilder();
		
		for (int i = 0; i < stacks.size(); i++) {
			if (stacks.get(i).size() > 0) {
				temp.append(stacks.get(i).get(0));
			}
		}
		
		return temp.toString();
	}
	
	
	/**
	 * Liefert eine Liste von Listen mit Charaktern,
	 * die die Namen der Kisten darstellen. Liest aus
	 * 'stacks.txt'.
	 * 
	 * @return - Stacks mit ihren Kisten
	 */
	private static List<List<Character>> fillStacks(){
		List<List<Character>> stack = new ArrayList<>();
		List<String> input = helpers.HelperMethods.getInputAsListOfString("src/Day_5/stacks.txt");
		
		for (int i = 0; i < 9; i++) {
			stack.add(new ArrayList<>());
		}
		
		int idx;
		
		for (String line : input) {
			idx = line.indexOf('[');
			while(idx != -1) {
				if (idx != -1) {
					stack.get(idx / 4).add(line.charAt(++idx));
				}
				idx = line.indexOf('[', idx);
			}
		}
		return stack;
	}
	
	
	/**
	 * Liefert eine Liste von Listen mit Charaktern,
	 * die die Namen der Kisten darstellen. Liest aus
	 * 'stacks.txt'.
	 * 
	 * @return - Stacks mit ihren Kisten
	 */
	private static List<Queue<Character>> fillQueue(){
		List<Queue<Character>> stack = new ArrayList<>();
		List<String> input = helpers.HelperMethods.getInputAsListOfString("src/Day_5/stacks.txt");
		
		for (int i = 0; i < 9; i++) {
			stack.add(new LinkedList<>());
		}
		
		int idx;
		
		for (String line : input) {
			idx = line.indexOf('[');
			while(idx != -1) {
				if (idx != -1) {
					stack.get(idx / 4).add(line.charAt(++idx));
				}
				idx = line.indexOf('[', idx);
			}
		}
		return stack;
	}
}
