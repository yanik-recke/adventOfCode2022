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
		System.out.println(part1(pathToInput) + " - Part 2: " + part2(pathToInput));
	}


	/**
	 * Erst Ermittlung von allen Monkeys, die direkt eine Nummer
	 * rufen, hinzufügen zu HashMap. Dann nach und nach Berechnung,
	 * der anderen Monkeys.
	 *
	 * @param path - Pfad zum Input
	 * @return - Ergebnis von root
	 */
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
			for (String key : unknown.keySet()) {
				String temp = unknown.get(key);
				String op1 = temp.substring(0, temp.indexOf(' '));
				String op2 = temp.substring(temp.lastIndexOf(' ') + 1);
			
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
			
			for (String key : known.keySet()) {
				unknown.remove(key);
			}
		}
		
		return known.get("root");
	}


	/**
	 * Beim Parsen die Zeile mit "humn: x" ignorieren.
	 * Anstatt monkeys auszurechnen eine große Gleichung erstellen
	 * und als String zurückgeben. Berechnung mit https://www.mathpapa.com/algebra-calculator.html
	 *
	 * @param path - Pfad zum Puzzle Input
	 * @return - Term
	 */
	private static String part2(String path) {
		Map<String, String> known = new HashMap<>();
		Map<String, String> unknown = new HashMap<>();
		List<String> input = helpers.HelperMethods.getInputAsListOfString(path);

		for (String line : input) {
			String[] temp = line.split(" ");
			if (temp.length == 2 && !temp[0].equals("humn:")) {
				known.put(temp[0].substring(0, temp[0].length() - 1), temp[1]);
			} else if (temp.length > 2){
				unknown.put(temp[0].substring(0, temp[0].length() - 1), temp[1] + " " + temp[2] + " " + temp[3]);
			}
		}

		String tempRoot = unknown.get("root");
		unknown.put("root", tempRoot.replace('+', '='));

		known.put("humn", "humn");

		while (unknown.size() > 0) {
			for (String key : unknown.keySet()) {
				String temp = unknown.get(key);
				String op1 = temp.substring(0, temp.indexOf(' '));
				String op2 = temp.substring(temp.lastIndexOf(' ') + 1);

				if (((known.containsKey(op1) || op1.equals("humn")) && (known.containsKey(op2) || op2.equals("humn")))) {
					switch (temp.split(" ")[1]) {
						case "+" -> {
							known.put(key, "(" + known.get(op1) + " + " +  known.get(op2) + ")");
						}

						case "-" -> {
							known.put(key, "(" + known.get(op1) + " - " + known.get(op2) + ")");
						}

						case "*" -> {
							known.put(key, "(" + known.get(op1) + " * " + known.get(op2) + ")");
						}

						case "=" -> {
							known.put(key, "(" + known.get(op1) + " = " + known.get(op2) + ")");
						}

						default -> {
							known.put(key, "(" + known.get(op1) + " / " + known.get(op2) + ")");
						}
					}

				}
			}

			for (String key : known.keySet()) {
				unknown.remove(key);
			}
		}

		return known.get("root");
	}
}
