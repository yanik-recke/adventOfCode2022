package day_20;

import java.util.List;

/**
 * Lösung für Tag 20.
 * 
 * @author Yanik Recke
 *
 */
public class Day_20 {

	public static void main(String[] args) {
		String pathToInput = "src/day_20/input.txt";
		long test = 811589153L;

		System.out.println(part1(pathToInput));
		System.out.println(part2(pathToInput));
	}

	
	/**
	 * Erstellen einer doppelt verketteten Listenstruktur.
	 * Somit kann jede Nummer vorwärts und rückwärts 
	 * bewegt werden. Jede Zahl bekommt eine ID, damit 
	 * die Reihenfolge erhalten werden kann. Dann wird
	 * jede Zahl durchgegangen und entsprechend verschoben.
	 * 
	 * @param path - Pfad zum Puzzle Input
	 * @return - die Summe der 1000., 2000. und 3000. Zahl nach 0
	 */
	private static long part1(String path) {
		ListNode head = new ListNode(0, null, null, true, 0);
		int id = 1;
		
		List<String> input = helpers.HelperMethods.getInputAsListOfString(path);
		
		ListNode temp = head;
		ListNode prev = null;
		ListNode next = null;
		int idOfZero = 0;
		
		for (int i = 0; i < input.size() - 1; i++) {
			temp.setValue(Long.parseLong(input.get(i)));
			
			next = new ListNode(0, null, null, false, id++);
			temp.setNext(next);
			next.setPrev(temp);
			prev = temp;
			temp = next;
		}
		
		temp.setValue(Long.parseLong(input.get(input.size() - 1)));
		temp.setPrev(prev);
		temp.setNext(head);
		head.setPrev(temp);
		
		boolean found = false;
		ListNode runner = head;
		
		
		// Id von 0 finden
		while (!found) {
			if (runner.getValue() == 0) {
				idOfZero = runner.getId();
				found = true;
			}
			
			runner = runner.getNext();
		}
		
		for (int i = 0; i < id; i++) {
			ListNode currNode = head.getNodeByIdForward(i);
			long move = currNode.getValue();
			long offset;

			ListNode insertAt = currNode;
			
			if (((offset = (move % (id - 1))) != 0) && (move != 0)) {
				currNode.removeNode();
			} else {
				move = 0;
			}
			
			if (move > 0) {
				for (int j = 0; j < offset; j++) {
					insertAt = insertAt.getNext();
				}
			} else if (move < 0) {
				for (int j = 0; j > offset; j--) {
					insertAt = insertAt.getPrev();
				}
				insertAt = insertAt.getPrev();
			} else {
				// wenn 0, nichts machen
				continue;
			}
			
			insertAt.insertNode(currNode);
			head = insertAt;
		}
		
		ListNode currNode = head.getNodeByIdForward(idOfZero);

		long sum = 0;
		for (int i = 0; i <= 3000; i++) {
			if (i == 1000) {
				sum += currNode.getValue();
			} else if (i == 2000) {
				sum += currNode.getValue();
			} else if (i == 3000) {
				sum += currNode.getValue();
			}
			
			currNode = currNode.getNext();
		}
		
		return sum;
	}


	/**
	 * Funktioniert wie Part 1, nur wird der Key angewandt und
	 * dann 10 mal gemixt.
	 *
	 * @param path - Pfad zum Input
	 * @return - die Summe der 1000., 2000. und 3000. Zahl nach 0
	 */
	private static long part2(String path) {
		long key = 811589153L;

		ListNode head = new ListNode(0, null, null, true, 0);
		int id = 1;

		List<String> input = helpers.HelperMethods.getInputAsListOfString(path);

		ListNode temp = head;
		ListNode prev = null;
		ListNode next = null;
		int idOfZero = 0;

		for (int i = 0; i < input.size() - 1; i++) {
			long val = Long.parseLong(input.get(i));

			temp.setValue(val);

			next = new ListNode(0, null, null, false, id++);
			temp.setNext(next);
			next.setPrev(temp);
			prev = temp;
			temp = next;
		}

		temp.setValue(Long.parseLong(input.get(input.size() - 1)) * key);
		temp.setPrev(prev);
		temp.setNext(head);
		head.setPrev(temp);

		boolean found = false;
		ListNode runner = head;


		// Id von 0 finden
		while (!found) {
			if (runner.getValue() == 0) {
				idOfZero = runner.getId();
				found = true;
			}

			runner = runner.getNext();
		}

		for (int k = 0; k < 10; k++) {
			for (int i = 0; i < id; i++) {
				ListNode currNode = head.getNodeByIdForward(i);
				long move = currNode.getValue();
				long offset;

				ListNode insertAt = currNode;

				if (((offset = (move % (id - 1))) != 0) && (move != 0)) {
					currNode.removeNode();
				} else {
					move = 0;
				}

				if (move > 0) {
					for (int j = 0; j < offset; j++) {
						insertAt = insertAt.getNext();
					}
				} else if (move < 0) {
					for (int j = 0; j > offset; j--) {
						insertAt = insertAt.getPrev();
					}
					insertAt = insertAt.getPrev();
				} else {
					// wenn 0, nichts machen
					continue;
				}

				insertAt.insertNode(currNode);
				head = insertAt;
			}
		}

		ListNode currNode = head.getNodeByIdForward(idOfZero);

		long sum = 0;
		for (int i = 0; i <= 3000; i++) {
			if (i == 1000) {
				sum += currNode.getValue();
			} else if (i == 2000) {
				sum += currNode.getValue();
			} else if (i == 3000) {
				sum += currNode.getValue();
			}

			currNode = currNode.getNext();
		}

		// too low: 447185623303
		return sum;
	}
}
