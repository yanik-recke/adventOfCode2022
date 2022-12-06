package day_6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Lösung für Tag 6.
 * 
 * @author Yanik Recke
 */
public class Day_6 {

	public static void main(String[] args) {
		String pathToInput = "src/Day_6/input.txt";
		
		System.out.println(part1(pathToInput) + " - " + part2(pathToInput));
	}
	
	
	/**
	 * Wenn Queue 4 groß ist, dann prüfen
	 * ob ein Element doppelt vorkommt, wenn ja
	 * dann erstes Element entfernen, sonst fertig.
	 * 
	 * @param path - Pfad zum Puzzle Input
	 * @return - Die Anzahl der Zeichen bis die ersten 4 individuellen kamen
	 */
	private static int part1(String path) {
		Queue<Character> queue = new LinkedList<>();
		String input = "";
		
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
		    String line = br.readLine();

		    while (line != null) {
		    	input = line;
		        line = br.readLine();
		    }

		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		boolean found = false;

		int counter = 0;

		HashSet<Character> set = new HashSet<>();
		
		for (int i = 0; i < input.length() && !found; i++) {
			queue.add(input.charAt(i));
			set.add(input.charAt(i));
			counter++;
			
			if (set.size() == 4) {
				found = true;
			}
			
			if(queue.size() == 4) {
				set.remove(queue.poll());
			}
		}
		
		return counter - 1;
	}
	
	
	/**
	 * Wenn Queue 14 Elemente hat, prüfen ob
	 * doppelte vorkommen, wenn nicht, dann fertig, sonst
	 * erstes Element entfernen und nächstes hinzufügen.
	 * 
	 * @param path - Pfad zum Puzzle Input
	 * @return - Die Anzahl an Zeichen die verarbeitet wurden bis 14 individuelle kamen
	 */
	private static int part2(String path) {
		String input = "";
		Queue<Character> queue = new LinkedList<>();
		boolean found = false;
		boolean foundDouble = false;
		int counter = 0;
		HashSet<Character> set = new HashSet<>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
		    String line = br.readLine();

		    while (line != null) {
		    	input = line;
		        line = br.readLine();
		    }

		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		for (int i = 0; i < input.length() && !found; i++) {
			queue.add(input.charAt(i));
			counter++;
			
			if(queue.size() == 14) {
				
				set = new HashSet<>();
				
				for (Character character : queue) {
					if (!set.add(character)) {
						foundDouble = true;
						break;
					}
				}
				
				if (!foundDouble) {
					found = true;
				}
				
				foundDouble = false;
				
				queue.poll();
			}
		}
		
		return counter;
	}
	
}
