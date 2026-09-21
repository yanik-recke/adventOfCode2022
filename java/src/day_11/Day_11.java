package day_11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Lösung für Tag 11. 
 * 
 * @author Yanik Recke
 */
public class Day_11 {

	public static void main(String[] args) {
		String pathToInput = "src/day_11/input.txt";
		
		System.out.println(part1(pathToInput) + " - " + part2(pathToInput));
	}
	
	
	/**
	 * Alles parsen und eine Liste von Listen für die 
	 * verschiedenen "Eigenschaften" eines Affen führen.
	 * Dann Berechnungen durchführen.
	 * 
	 * @param path - Pfad zum Puzzle Input
	 * @return - Produkt der beiden meisten Inspektionen
	 */
	private static long part1(String path) {
		List<List<Long>> list = new ArrayList<>();
		
		for (int i = 0; i < 8; i++) {
			list.add(new ArrayList<>());
		}
		
		List<String> input = helpers.HelperMethods.getInputAsListOfString(path);
		int[] count= new int[] {0,0,0,0,0,0,0,0};
		
		int monkey = 0;
		int round = 0;
		String op = "";
		long worry = 0;
		
		List<List<String>> curr = new ArrayList<>();
		int idx = 0;
		
		/* von 0 bis 3
		 * Operation
		 * Test
		 * true
		 * false */
		for (int i = 0; i < input.size(); i++) {
			// neuer Monkey
			if (input.get(i).contains("Monkey " + monkey)) {
				curr.add(new ArrayList<>());
				
				boolean done = false;
				String temp = input.get(i + 1);
				int tempIdx = temp.indexOf(":") + 1;
				
				while (!done) {
					if ((temp.indexOf(",", tempIdx + 1)) != -1) {
						list.get(monkey).add(Long.parseLong(temp.substring(tempIdx + 1, tempIdx = temp.indexOf(",", tempIdx + 1))));
						tempIdx++;
					} else {
						if (temp.lastIndexOf(",") != -1) {
							list.get(monkey).add(Long.parseLong(temp.substring(temp.lastIndexOf(",") + 2)));
						} else {
							list.get(monkey).add(Long.parseLong(temp.substring(temp.indexOf(":") + 2)));
						}
						done = true;
					}
				}
				
				curr.get(idx).addAll(Arrays.asList(input.get(i + 2), input.get(i + 3), input.get(i + 4), input.get(i + 5)));
				idx++;
				monkey++;
				
			}
		}
		
		int monkeyCount = monkey;
		monkey = 0;
		
		while (round < 20) {
			op = curr.get(monkey).get(0).substring(curr.get(monkey).get(0).indexOf("old") + 4);
			
			for(long item : list.get(monkey)) {
				if (op.charAt(0) == '*') {
					String temp = "";
					if ((temp = op.substring(2)).equals("old")) {
						worry = item * item;
					} else {
						worry = item * Integer.parseInt(temp);
					}
				} else if (op.charAt(0) == '+') {
					worry = item + Integer.parseInt(op.substring(2));
				}
				
				worry = worry / 3;
				
				String temp = curr.get(monkey).get(1).substring(curr.get(monkey).get(1).indexOf('y') + 2);

				if (worry % Integer.parseInt(temp) == 0) {
					list.get(Integer.parseInt(curr.get(monkey).get(2).substring(curr.get(monkey).get(2).length() - 1))).add(worry);
				} else {
					list.get(Integer.parseInt(curr.get(monkey).get(3).substring(curr.get(monkey).get(3).length() - 1))).add(worry);
				}
				
				count[monkey] = count[monkey] + 1;
			}
			
			list.get(monkey).clear();
			
			if (monkey + 1 == monkeyCount) {
				monkey = 0;
				round++;
			} else {
				monkey++;
			}
		}
		
		
		int tempMax = 0;
		int tempSndMax = 0;
		
		for (int i = 0; i < count.length; i++) {
			if (count[i] > tempMax) {
				tempSndMax = tempMax;
				tempMax = count[i];
			}
		}
		
		return tempMax * tempSndMax;
	}
	
	
	
	/**
	 * Alles parsen und Liste von Liste mit den
	 * "Eigenschaften" des jeweiligen Affen führen. LCM aller
	 * Test Zahlen berechenn und "worry" Zahl Modulo des LCM nehmen.
	 * Somit bleibt Teilbarkeit erhalten und Zahlen werden nicht zu groß.
	 * 
	 * @param path - Pfad zum Puzzle Input
	 * @return - Produkt der beiden meisten Inspektionen
	 */
	private static long part2(String path) {
		List<List<Long>> list = new ArrayList<>();
		
		for (int i = 0; i < 8; i++) {
			list.add(new ArrayList<>());
		}
		
		List<String> input = helpers.HelperMethods.getInputAsListOfString(path);
		long[] count= new long[] {0,0,0,0,0,0,0,0};
		
		int monkey = 0;
		int round = 0;
		String op = "";
		long worry = 0;
		int monkeyCount = 0;
		
		List<List<String>> curr = new ArrayList<>();
		int idx = 0;
		
		/* von 0 bis 3
		 * Operation
		 * Test
		 * true
		 * false */
		for (int i = 0; i < input.size(); i++) {
			if (input.get(i).contains("Monkey " + monkey)) {
				curr.add(new ArrayList<>());
				boolean done = false;
				String temp = input.get(i + 1);
				int tempIdx = temp.indexOf(":") + 1;
				while (!done) {
					if ((temp.indexOf(",", tempIdx + 1)) != -1) {
						list.get(monkey).add(Long.parseLong(temp.substring(tempIdx + 1, tempIdx = temp.indexOf(",", tempIdx + 1))));
						tempIdx++;
					} else {
						if (temp.lastIndexOf(",") != -1) {
							list.get(monkey).add(Long.parseLong(temp.substring(temp.lastIndexOf(",") + 2)));
						} else {
							list.get(monkey).add(Long.parseLong(temp.substring(temp.indexOf(":") + 2)));
						}
						done = true;
					}
				}
				
				curr.get(idx).addAll(Arrays.asList(input.get(i + 2), input.get(i + 3), input.get(i + 4), input.get(i + 5)));
				idx++;
				monkey++;
				
			}
		}
		
		monkeyCount = monkey;
		monkey = 0;
		
		int[] tests = new int[monkeyCount];
		
		for (int i = 0; i < monkeyCount; i++) {
			tests[i] = Integer.parseInt(curr.get(i).get(1).substring(curr.get(i).get(1).indexOf('y') + 2));
		}
		
		int lcm = lcm(tests);
		
		while (round < 10000) {
			op = curr.get(monkey).get(0).substring(curr.get(monkey).get(0).indexOf("old") + 4);
			
			for(long item : list.get(monkey)) {
				if (op.charAt(0) == '*') {
					String temp = "";
					if ((temp = op.substring(2)).equals("old")) {
						worry = item * item;

					} else {
						worry = item * Long.parseLong(temp);
					}
				} else if (op.charAt(0) == '+') {
					worry = item + Long.parseLong(op.substring(2));
				}
				
				
				String temp = curr.get(monkey).get(1).substring(curr.get(monkey).get(1).indexOf('y') + 2);

				if (worry % Integer.parseInt(temp) == 0) {
					list.get(Integer.parseInt(curr.get(monkey).get(2).substring(curr.get(monkey).get(2).length() - 1))).add(worry % lcm);
				} else {
					list.get(Integer.parseInt(curr.get(monkey).get(3).substring(curr.get(monkey).get(3).length() - 1))).add(worry % lcm);
				}
				
				count[monkey] = count[monkey] + 1;
			}
			
			list.get(monkey).clear();
			
			if (monkey + 1 == monkeyCount) {
				monkey = 0;
				round++;
			} else {
				monkey++;
			}
		}
		

		long tempMax = 0;
		long tempSndMax = 0;
		
		for (int i = 0; i < count.length; i++) {
			if (count[i] > tempMax) {
				tempMax = count[i];
			} else if (count[i] > tempSndMax) {
				tempSndMax = count[i];
			}
		}

		return tempMax * tempSndMax;
	}
	
	
	/**
	 * Berechnung des GCD.
	 * 
	 * @param x - Zahl 1
	 * @param y - Zahl 2
	 * @return - GCD der beiden Zahlen
	 */
	private static int gcd(int x, int y) {
	    return (y == 0) ? x : gcd(y, x % y);
	}
	
	/**
	 * Berechnung des LCD für mehrere Zahlen.
	 * 
	 * @param numbers - Zahlen dessen LCD berechnet werden soll
	 * @return - LCD aller übergebenen Zahlen
	 */
	public static int lcm(int... numbers) {
	    return Arrays.stream(numbers).reduce(1, (x, y) -> x * (y / gcd(x, y)));
	}
	
	
}
