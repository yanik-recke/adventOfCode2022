package day_20;

import java.util.List;

public class Day_20 {

	public static void main(String[] args) {
		String pathToInput = "src/day_20/input.txt";
		
		System.out.println(part1(pathToInput));
	}
	
	
	private static int part1(String path) {
		ListNode head = new ListNode(0, null, null, true);
		
		List<String> input = helpers.HelperMethods.getInputAsListOfString(path);
		
		ListNode temp = head;
		ListNode prev = null;
		ListNode next = null;
		
		for (int i = 0; i < input.size() - 1; i++) {
			temp.setValue(Long.parseLong(input.get(i)));
			next = new ListNode(0, null, null, false);
			temp.setNext(next);
			next.setPrev(temp);
			prev = temp;
			temp = next;
		}
		
		temp.setValue(Long.parseLong(input.get(input.size() - 1)));
		temp.setPrev(prev);
		temp.setNext(head);
		head.setPrev(temp);
		
		
		
		return 0;
	}
	
	
	private static int part2(String path) {
		
		
		return 0;
	}
}
