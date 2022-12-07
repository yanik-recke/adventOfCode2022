package day_7;

import java.util.List;

import helpers.Node;
import helpers.Tree;

/**
 * Lösung für Tag 7.
 * 
 * @author Yanik Recke
 */
public class Day_7 {
	private static long wholeSum = 0;

	public static void main(String[] args) {
		String pathToInput = "src/day_7/input.txt";
		
		System.out.println(part1(pathToInput) + " - " + part2(pathToInput));
	}
	
	/**
	 * Einlesen des "Dateisystems" und Abbildung in eigens ersteller 
	 * Baum Struktur. Dann Größe der Directories ermitteln und aufaddieren,
	 * wenn Größe <= 100.000 ist.
	 * 
	 * @param path - Pfad zum Puzzle Input
	 * @return - Summe der Größe aller Directories mit Größe <= 100.000
	 */
	private static long part1(String path) {
		List<String> input = helpers.HelperMethods.getInputAsListOfString(path);
		Tree<String> data = new Tree<>("/");
		Node<String> currNode = data.root;
		
		int i = 0;
		
		while (i < input.size()) {
			String currName = input.get(i);
			
			if (currName.equals("$ ls")) {
				String temp = input.get(++i);
				
				while (!temp.contains("$") && i++ < input.size()) {
					currNode.addChild(temp);
					
					if (i < input.size()) {
						temp = input.get(i);	
					}
				}
				
			} else if (currName.equals("$ cd ..")) {
				currNode = currNode.parent;
				i++;
			} else  if (currName.contains("$ cd ") && !currName.contains("..")) {
				boolean found = false;
				int n = 0;
				String dirName = "dir" + currName.substring(currName.indexOf('d') + 1, currName.length());
				
				while (!found) {
					if (currNode.children.get(n).data.equals(dirName)) {
						found = true;
						currNode = currNode.children.get(n);
					}
					
					n++;
				}
				
				i++;
			}
		}
		
		// rekursiv
		output(data.root.children);

		return wholeSum;
	}
	
	
	/**
	 * Rekursive Methode mit der die Größe jedes Directories
	 * ermittelt werden kann. Es wird "von links nach rechts" gearbeitet,
	 * d.h. sie ruft sich so lange selbst auf, bis sie den letzten Knoten 
	 * erreicht hat und gibt dann dessen Größe wieder. 
	 * 
	 * @param runner - Liste mit Einstiegselementen
	 * @return - Größe eines einzelnen Knotens
	 */
	private static int output(List<Node<String>> runner) {
		int tempSum = 0;
		
		for (Node<String> node : runner) {
			if (node.children != null && node.children.size() > 0) {
				tempSum += output(node.children);
				
			} else {
				if (node.data.contains("dir")) {
					tempSum += 0;
				} else {
					tempSum += Integer.parseInt(node.data.substring(0, node.data.indexOf(' ')));
				}
			}
		}
		
		if (tempSum <= 100000) {
			wholeSum += tempSum;
		}
		
		return tempSum;
	}
	
	
	/**
	 * Ermitteln wie viel Speicher von "/" verwendet wird: 43.441.553
	 * 70.000.000 - 43.441.553 ergibt 26.558.447
	 * 30.000.000 - 26.558.447 ergibt 3.441.553
	 * Es muss ein Directory mit mind. 3.441.553 gelöscht werden.
	 * Mit rekursiver Methode die Größe jedes Directories prüfen und
	 * das kleinste, welches trotzdem größer als 3.441.553 ist, zurückgeben.
	 * 
	 * @param path - Pfad zum Puzzle Input
	 * @return - Größe des kleinsten Directories, welches trotzdem noch >= 3.441.553
	 */
	private static long part2(String path) {
		wholeSum = 0;
		
		List<String> input = helpers.HelperMethods.getInputAsListOfString(path);
		Tree<String> data = new Tree<>("/");
		Node<String> currNode = data.root;
		
		int i = 0;
		
		while (i < input.size()) {
			String currName = input.get(i);
			
			if (currName.equals("$ ls")) {
				String temp = input.get(++i);

				while (!temp.contains("$") && i++ < input.size()) {
					currNode.addChild(temp);
					
					if (i < input.size()) {
						temp = input.get(i);	
					}
				}
			} else if (currName.equals("$ cd ..")) {
				currNode = currNode.parent;
				i++;
			} else  if (currName.contains("$ cd ") && !currName.contains("..")) {
				boolean found = false;
				int n = 0;
				String dirName = "dir" + currName.substring(currName.indexOf('d') + 1, currName.length());
				
				while (!found) {
					if (currNode.children.get(n).data.equals(dirName)) {
						found = true;
						currNode = currNode.children.get(n);
					}
					
					n++;
				}
				
				i++;
			}
		}
		
		// Rekursion
		output_part2(data.root.children);
		
		return wholeSum;
	}
	
	
	/**
	 * Berechnet nach demselben Prinzip wie oben das Directory mit
	 * der kleinsten Größe, welches aber trotzdem noch >= 3441553.
	 * Schreibt den aktuellen Kandidaten in "wholeSum".
	 * 
	 * @param runner - Liste an Einstiegsknoten
	 * @return - Größe eines einzelnen Knotens
	 */
	private static long output_part2(List<Node<String>> runner) {
		long tempSum = 0L;
		for (Node<String> node : runner) {
			
			if (node.children != null && node.children.size() > 0) {
				tempSum += output_part2(node.children);
			} else {
				if (node.data.contains("dir")) {
					tempSum += 0;
				} else {
					tempSum += Integer.parseInt(node.data.substring(0, node.data.indexOf(' ')));
				}
			}
		}
		
		/* 70.000.000 - 43441553 = 26558447
		/* 30.000.000 - 26558447 = 3441553 
		 * -> mindestgröße des Directory muss 3441553 sein */
		if (tempSum >= 3441553 && (wholeSum == 0 || tempSum < wholeSum)) {
			wholeSum = tempSum;
		}
		
		return tempSum;
	}
	

}
