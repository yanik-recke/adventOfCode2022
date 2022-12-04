package day_4;

import java.io.BufferedReader;
import java.io.FileReader;


/**
 * Lösung für Tag 4.
 * 
 * @author Yanik Recke
 */
public class Day_4 {

	public static void main(String args[]) {
		String pathToInput = "src/day_4/input.txt";
		
		System.out.println(part1(pathToInput) + " - " + part2(pathToInput));
	}
	
	
	/**
	 * Ober- und Untergrenze für jedes Paar ermittlen
	 * und wenn die Untergrenze der ersten Nummer kleiner ist als
	 * die des anderen Paars und die zweite Nummer größer ist als 
	 * die des anderen Paars, dann vollständige Überlappung.
	 * 
	 * @param path - Pfad zum Puzzle Input
	 * @return - Anzahl an vollständig umfassenden Paaren
	 */
	public static int part1(String path) {
		int counter = 0;
		int[] nums = new int[4];
		
		
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
		    String line = br.readLine();

		    while (line != null) {
		    	nums[0] = Integer.parseInt(line.substring(0, line.indexOf('-')));
		    	nums[1] = Integer.parseInt(line.substring(line.indexOf('-') + 1, line.indexOf(',')));
		    	nums[2] = Integer.parseInt(line.substring(line.indexOf(',') + 1, line.indexOf('-', line.indexOf(','))));
		    	nums[3] = Integer.parseInt(line.substring(line.indexOf('-', line.indexOf(',')) + 1, line.length()));
		    	
		    	if((nums[0] <= nums[2]) && (nums[1] >= nums[3])) {
		    		counter++;
		    	} else if(nums[2] <= nums[0] && nums[3] >= nums[1]) {
		    		counter++;
		    	}
		    	
		        line = br.readLine();
		        
		    }

		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		
		return counter;
	}
	
	
	/**
	 * Ober- und Untergrenze pro Paar ermitteln und 
	 * prüfen, ob es eine Überlappung gibt.
	 * Überlappung wenn:
	 * 		- Ober- oder Untergrenze gleich sind mit einer Grenze
	 *    	  des anderen Paars
	 * 		- Komplette Überlappung wie aus Part 1
	 * 		- Untergrenze ist kleiner als Untergrenze des anderen Paars
	 * 	  	  und Obergrenze ist größer als Untergrenze des anderen Paars
	 * 
	 * @param path - Pfad zum Puzzle Input
	 * @return - Anzahl an Überlappungen
	 */
	public static int part2(String path) {
		int counter = 0;
		int idx = 0;
		int[] nums = new int[4];
		
		
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
		    String line = br.readLine();

		    while (line != null) {
		    	idx = line.indexOf('-');
		    	nums[0] = Integer.parseInt(line.substring(0, idx));
		    	nums[1] = Integer.parseInt(line.substring(++idx, idx = line.indexOf(',')));
		    	nums[2] = Integer.parseInt(line.substring(idx + 1, idx = line.indexOf('-', idx)));
		    	nums[3] = Integer.parseInt(line.substring(++idx, line.length()));

		    	if((nums[0] <= nums[2]) && (nums[1] >= nums[3])) {
		    		counter++;
		    	} else if(nums[2] <= nums[0] && nums[3] >= nums[1]) {
		    		counter++;
		    	} else if(nums[0] <= nums[2] && nums[1] >= nums[2]) {
		    		counter++;
		    	} else if(nums[2] <= nums[0] && nums[3] >= nums[0]) {
		    		counter++;
		    	}
		    	
		        line = br.readLine();
		    }

		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		
		return counter;
		
	}
	
}
