package day_21;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Lösung für Tag 21.
 * 
 * @author Yanik Recke
 */
public class Day_21 {

	
	public static void main(String[] args) {
		String pathToInput = "src/day_21/input.txt";

		System.out.println(part1(pathToInput) + " - " + part2(pathToInput));
	}
	
	
	private static long part1(String path) {
		Map<String, Long> known = new HashMap<>();
		Map<String, String> unknown = new HashMap<>();
		List<String> input = helpers.HelperMethods.getInputAsListOfString(path);
		
		for (String line : input) {
			String[] temp = line.split(" ");
			if (temp.length == 2) {
				known.put(temp[0].substring(0, temp[0].length() - 1), Long.parseLong(temp[1]));
			} else {
				unknown.put(temp[0].substring(0, temp[0].length() - 1), temp[1] + " " + temp[2] + " " + temp[3]);
			}
		}
		
		while (known.get("root") == null) {
			System.out.println(known.size());
			for (String key : unknown.keySet()) {
				String temp = unknown.get(key);
				String op1 = temp.substring(0, temp.indexOf(' '));
				String op2 = temp.substring(temp.lastIndexOf(' ') + 1, temp.length());
			
				if (known.containsKey(op1) && known.containsKey(op2)) {
					switch (temp.split(" ")[1]) {
						case "+" -> {
							known.put(key, known.get(op1) + known.get(op2));
						}
					
						case "-" -> {
							known.put(key, known.get(op1) - known.get(op2));
						}
					
						case "*" -> {
							known.put(key, known.get(op1) * known.get(op2));
						}
					
						default -> {
							known.put(key, known.get(op1) / known.get(op2));
						}
					}
				
				}
			}
		}
		
		return known.get("root");
	}
	
	private static int part2(String path) {
		
		return 0;
	}
}
