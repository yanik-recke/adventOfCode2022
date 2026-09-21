package day_13;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Lösung für Tag 13.
 * 
 * @author Yanik Recke
 */
public class Day_13 {

	public static void main(String[] args) {
		String pathToInput = "src/day_13/input.txt";
		
		System.out.println(part1(pathToInput) + " - " + part2(pathToInput));
	}
	
	
	/**
	 * Input einlesen und paarweise vergleichen.
	 * Vergleich erfolgt rekursiv.
	 * 
	 * @param path - Pfad zum Puzzle Input
	 * @return - Summe aller korrekt sortierten Packets
	 */
	private static int part1(String path) {
		List<String> input = helpers.HelperMethods.getInputAsListOfString(path);
		Packet left = new Packet(new ArrayList<Object>(), null);
		Packet right = new Packet(new ArrayList<Object>(), null);
		Packet curr = null;
		Packet temp;
		
		int sum = 0;
		
		int idx = 1;
		int count = 0;

		for (String line : input) {
			
			if (line.equals("")) {
				count = 0;
				curr = null;
			}
			else if (count == 0) {
				left = new Packet(new ArrayList<Object>(), null);
				count++;
				curr = left;
			} else if (count == 1) {
				right = new Packet(new ArrayList<Object>(), null);
				curr = right;
				count++;
			}
			
			for (int i = 1; i < line.length(); i++){
				switch (line.charAt(i)) {
					case ']' -> {
						curr = curr.parent;
					}
						
					case '[' -> {
						temp = new Packet(new ArrayList<Object>(), curr);
						curr.list.add(temp);
						curr = temp;
					}
						
					case ',' -> {
						;
					}
						
					default -> {
						if (line.indexOf(',', i) != -1) {
							if (line.indexOf(']', i) != -1 && line.indexOf(']', i) > line.indexOf(',', i))
								curr.list.add(Integer.parseInt(line.substring(i, line.indexOf(',', i))));
							else {
								curr.list.add(Integer.parseInt(line.substring(i, line.indexOf(']', i))));
							}
						} else if (line.indexOf(']') != -1) {
							curr.list.add(Integer.parseInt(line.substring(i, line.indexOf(']', i))));
						}
					}
				}
			}
			
			
			if (count == 2) {
				if (compare(left, right) == Status.YES) {
					sum += idx;
				} else if (compare(left, right) == Status.INCON) {
					throw new IllegalAccessError("eyo");
				}
				
				idx++;
			}
			
		}
		
		return sum;
	}
	
	
	/**
	 * Sortiert alle Packets und multipliziert
	 * die Indexe der Divider Packets miteinander.
	 * 
	 * @param path - Pfad zum Puzzle Input
	 * @return - Produkt der beiden Divider Packets
	 */
	private static int part2(String path) {
		List<String> input = helpers.HelperMethods.getInputAsListOfString(path);
		
		input.add("[[2]]");
		input.add("[[6]]");
		
		List<Packet> sorted = new ArrayList<>();
		
		Packet curr = null;
		Packet temp;
		
		Packet last = null;
		Packet sndLast = null;
		

		for (String line : input) {
			if (!line.equals("")) {
				curr = new Packet(new ArrayList<Object>(), null);
			
			
				for (int i = 1; i < line.length(); i++){
					switch (line.charAt(i)) {
						case ']' -> {
							if (curr.parent != null) {
								curr = curr.parent;
							}
						}
						
						case '[' -> {
							temp = new Packet(new ArrayList<Object>(), curr);
							curr.list.add(temp);
							curr = temp;
						}
						
						case ',' -> {
							;
						}
						
						default -> {
							if (line.indexOf(',', i) != -1) {
								if (line.indexOf(']', i) != -1 && line.indexOf(']', i) > line.indexOf(',', i))
								curr.list.add(Integer.parseInt(line.substring(i, line.indexOf(',', i))));
								else {
									curr.list.add(Integer.parseInt(line.substring(i, line.indexOf(']', i))));
								}
							} else if (line.indexOf(']') != -1) {
								curr.list.add(Integer.parseInt(line.substring(i, line.indexOf(']', i))));
							}
						}
					}
				}
				
				sndLast = last;
				last = curr;
				
				if (sorted.size() == 0) {
					sorted.add(curr);
				} else {
					boolean added = false;
					for (int i = 0; i < sorted.size() && !added; i++) {
						if (compare(curr, sorted.get(i)) == Status.YES) {
							sorted.add(i, curr);
							added = true;
						}
					}
					
					if (!added) {
						sorted.add(curr);
					}
				}
			}
		}
		
		int idx1 = 0;
		int idx2 = 0;
		
		for (int i = 0; i < sorted.size(); i++) {
			if (sorted.get(i).equals(last)) {
				idx2 = i + 1;
			}
			else if (sorted.get(i).equals(sndLast)) {
				idx1 = i + 1;
			}
		}
		
		return idx1 * idx2;
	}
	
	
	/**
	 * Führt den Vergleich zweier Packets durch.
	 * 
	 * @param left - erstes Packet
	 * @param right - zweites Packet
	 * @return - YES, wenn richtige Reihenfolge, NO, wenn nicht, INCON, wenn nicht sicher
	 */
	private static Status compare(Packet left, Packet right) {
		int iL = 0;
		int iR = 0;
		
		Status ordered = Status.INCON;
		
		boolean leftRanOut = false;
		boolean rightRanOut = false;
		
		while ((leftRanOut = iL < left.list.size()) & (rightRanOut = iR < right.list.size()) && ordered == Status.INCON) {
			// beides int
			if (left.list.get(iL) instanceof Integer && right.list.get(iR) instanceof Integer) {
				if ((int) left.list.get(iL) < (int) right.list.get(iR)) {
					return Status.YES;
				} else if ((int) left.list.get(iL) > (int) right.list.get(iR)) {
					return Status.NO;
				} else {
					ordered = Status.INCON;
				}
			}
			
			else if (left.list.get(iL) instanceof Packet && right.list.get(iR) instanceof Packet) {
				ordered = compare((Packet) left.list.get(iL), (Packet) right.list.get(iR));
			}
			
			else if (left.list.get(iL) instanceof Packet && right.list.get(iR) instanceof Integer) {
				ordered = compare((Packet) left.list.get(iL), new Packet(Arrays.asList(right.list.get(iR)), null));
			}
			
			else if (left.list.get(iL) instanceof Integer && right.list.get(iR) instanceof Packet) {
				ordered = compare(new Packet(Arrays.asList(left.list.get(iL)), null) , (Packet) right.list.get(iR));
			}
			
			iL++;
			iR++;
		}
		
		if (ordered == Status.INCON) {
			if (!leftRanOut && !rightRanOut) {
				return Status.INCON;
			} else if (!leftRanOut && rightRanOut) {
				return Status.YES;
			} else if (leftRanOut && !rightRanOut) {
				return Status.NO;
			}
		}
		
		return ordered;
	}
	
	
}
