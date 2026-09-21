package day_1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Lösung für Tag 1.
 * 
 * @author Yanik Recke
 */
public class Day_1 {

	public static void main(String[] args) {
		String pathToInput = "src/day_1/input.txt";
		
		System.out.println(part1(pathToInput));
		System.out.println(part2(pathToInput) + " - " + part2_secondSolution(pathToInput));
	}
	
	
	/**
	 * Addiert die Werte bis zur nächsten Leerzeile auf
	 * und vergleicht mit dem vorherigen größten Wert.
	 * 
	 * @param path - Pfad zum Puzzle Input
	 * @return - Die größte Summe
	 */
	private static int part1(String path) {
		int biggestSum = 0;
		int sum = 0;
		
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
		    String line = br.readLine();

		    while (line != null) {
		        if ("".equals(line)) {
		        	if (biggestSum < sum) {
		        		biggestSum = sum;
		        	}
		        	
		        	sum = 0;
		        } else {
		        	sum += Integer.parseInt(line);
		        }
		        
		        line = br.readLine();
		    }

		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return biggestSum;
	}
	
	
	/**
	 * Läuft den Input drei mal ab und findet jeweils
	 * die größte Summe nach den vorherigen Durchläufen.
	 * 
	 * @param path - Pfad zum Puzzle Input
	 * @return - Die Summe der drei größten Summen
	 */
	private static int part2(String path) {
		List<Integer> biggestSums = new ArrayList<>();
		int biggestSum;
		int sum;
		
		while (biggestSums.size() < 3) {
			sum = 0;
			biggestSum = 0;
			
			try (BufferedReader br = new BufferedReader(new FileReader(path))) {
				String line = br.readLine();

				while (line != null) {
					if ("".equals(line)) {
						if (biggestSum < sum && !biggestSums.contains(sum)) {
							biggestSum = sum;
						}
		        	
						sum = 0;
					} else {
						sum += Integer.parseInt(line);
					}
		        
					line = br.readLine();
				}
		    

			} catch (Exception e) {
				e.printStackTrace();
			} 
			
			biggestSums.add(biggestSum);
		}

		
		return biggestSums.get(0) + biggestSums.get(1) + biggestSums.get(2);
	}
	
	
	/**
	 * Fügt jede Summe in eine Liste ein,
	 * sortiert diese Liste absteigend und
	 * addiert die ersten 3 Einträge auf.
	 * 
	 * @param path - Pfad zum Puzzle Input
	 * @return - Die Summe der größten drei Summen
	 */
	private static int part2_secondSolution(String path) {
		int sum = 0;
		
		List<Integer> list = new ArrayList<>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
		    String line = br.readLine();

		    while (line != null) {
		        if ("".equals(line)) {
		        	list.add(sum);
		        	sum = 0;
		        } else {
		        	sum += Integer.parseInt(line);
		        }
		        
		        line = br.readLine();
		    }

		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		list.sort(Comparator.reverseOrder());
		
		return list.get(0) + list.get(1) + list.get(2);
	}
	
}
