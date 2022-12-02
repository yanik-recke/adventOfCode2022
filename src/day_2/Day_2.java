package day_2;

import java.io.BufferedReader;
import java.io.FileReader;


/**
 * Lösung für Tag 2.
 * 
 * Stein gibt 1 Punkt, Papier 2 und Schere 3.
 * Lose gibt 0, Draw 3 und Win 6.
 * A -> Stein
 * B -> Papier
 * C -> Schere
 * 
 * Part1:
 * X -> Stein
 * Y -> Papier
 * Z -> Schere
 * 
 * Part2:
 * X -> Verlieren
 * Y -> Unentschieden
 * Z -> Gewinnen
 * 
 * @author Yanik Recke
 */
public class Day_2 {
	
	public static void main(String[] args) {
		String pathToInput = "src/day_2/input.txt";
		
		System.out.println(part1(pathToInput) + " - " + part2(pathToInput));
	}
	
	
	/**
	 * Iterieren über den Input jeweils Fallunterscheidung 
	 * was der Gegner spielt mit switch(), dann eigenen Zug
	 * prüfen und entsprechend addieren.
	 * 
	 * @param path - Pfad zum Input
	 * @return - der Score
	 */
	private static int part1(String path) {
		int score = 0;
		
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
		    String line = br.readLine();
		    /* A rock B paper C scissor
		     * X rock Y paper Z scissor */
		    while (line != null) {
		    	switch (line.charAt(0)) {
		    		// Stone
		    		case 'A' -> {
		    			if (line.charAt(2) == 'Y') {
		    				score += 8;
		    			} else if (line.charAt(2) == 'Z'){
		    				score += 3;
		    			}
		    			else {
		    				score += 4;
		    			}
		    		}
		    	
		    		// Paper
		    		case 'B' -> {
		    			if (line.charAt(2) == 'Y') {
		    				score += 5;
		    			} else if (line.charAt(2) == 'Z'){
		    				score += 9;
		    			}
		    			else {
		    				score++;
		    			}
		    		}
		    	
		    		// Scissor
		    		default -> {
		    			if (line.charAt(2) == 'Y') {
		    				score += 2;
		    			} else if (line.charAt(2) == 'Z'){
		    				score += 6;
		    			}
		    			else {
		    				score += 7;
		    			}
		    		}
		    	}
		    	
		        line = br.readLine();
		    }

		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return score;
	}
	
	
	/**
	 * Iterieren über Input und Fallunterscheidung
	 * was der Gegner spielt mit switch(), dann
	 * prüfen, ob man verlieren, unentschieden oder
	 * gewinnen sollen und Score entsprechend berechnen.
	 * 
	 * @param path - Pfad zum Input
	 * @return - Berechneter score
	 */
	private static int part2(String path) {
		int score = 0;
		
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
		    String line = br.readLine();
		    
		    while (line != null) {
		    	switch (line.charAt(0)) {
		    		// Stone
		    		case 'A' -> {
		    			if (line.charAt(2) == 'Y') {
		    				score += 4;
		    			} else if (line.charAt(2) == 'Z'){
		    				score += 8;
		    			}
		    			else {
		    				score += 3;
		    			}
		    		}
		    	
		    		// Paper
		    		case 'B' -> {
		    			if (line.charAt(2) == 'Y') {
		    				score += 5;
		    			} else if (line.charAt(2) == 'Z'){
		    				score += 9;
		    			}
		    			else {
		    				score++;
		    			}
		    		}
		    	
		    		// Scissor
		    		default -> {
		    			if (line.charAt(2) == 'Y') {
		    				score += 6;
		    			} else if (line.charAt(2) == 'Z'){
		    				score += 7;
		    			}
		    			else {
		    				score += 2;
		    			}
		    		}
		    	}
		    	
		        line = br.readLine();
		    }

		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return score;
	}
	
	
}
