package day_3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

/**
 * Lösung für Tag 3.
 * 
 * @author Yanik Recke
 */
public class Day_3 {

	
	public static void main(String[] args) {
		String pathToInput = "src/day_3/input.txt";
		int test = 0;
		test += 'a';
		System.out.println(test);
		System.out.println(part1(pathToInput) + " - " + part2(pathToInput));
	}
	
	
	/**
	 * Das Zeichen finden, dass sowohl in der
	 * ersten, als auch in der letzten Hälfte
	 * des Strings vorkommt finden und Wert
	 * entsprechend aufaddieren. (a = 1, b = 2, usw.)
	 * 
	 * @param path - Pfad zum Puzzle Input
	 * @return - 
	 */
	private static int part1(String path) {
		String half1 = "";
		String half2 = "";
		
		boolean found = false;
		int sum = 0;
		
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
		    String line = br.readLine();

		    while (line != null) {
		    	half1 = line.substring(0, line.length() / 2);
		    	half2 = line.substring(line.length() / 2, line.length());
		    	
		    	for (int i = 0; i < half1.length() && !found; i++) {
		    		if (half2.contains("" + half1.charAt(i))) {
		    			found = true;
		    			sum += getPrio(half1.charAt(i));
		    		}
		    	}
		    	
		    	found = false;
		    
		        line = br.readLine();
		    }

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return sum;
	}
	
	
	/**
	 * Das gemeinsame Zeichen pro 3 Reihen finden
	 * und Werte aufaddieren. (a = 1, b = 2, usw.).
	 * 
	 * @param path - Pfad zum Puzzle Input
	 * @return - Summe aller gemeinsamer Zeichen pro drei Reihen
	 */
	private static int part2(String path) {
		List<String> inputList = helpers.HelperMethods.getInputAsListOfString(path);

		int sum = 0;
		int count;
		int everyThird = 0;
		char temp;
		boolean found = false;
		
		for (int k = 0; k < inputList.size(); k +=3) {
	    	for (int i = 0; i < inputList.get(k).length() && !found; i++) {
	    		temp = inputList.get(k).charAt(i);
	    		count = 0;
	    		for (int j = everyThird; j < everyThird + 3; j++) {
	    			if (inputList.size() > j && inputList.get(j).contains("" + temp)) {
	    				count++;
	    			}
	    		}
	    		
	    		if (count == 3) {
	    			sum += getPrio(temp);
	    			found = true;
	    		}
	    	}
	    	
    		everyThird += 3;
	    	found = false;
		}
		
		return sum;
	}
	
	
	/**
	 * Methode zum Bestimmen des Wertes eines
	 * Buchstaben entsprechend seiner Position
	 * im Alphabet.
	 * 
	 * @param c - der Buchstaben von dem der Wert bestimmt werden soll
	 * @return - der Wert des Buchstaben
	 */
	private static int getPrio(char c) {
		String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		
		return alphabet.indexOf(c) + 1;
	}
}
