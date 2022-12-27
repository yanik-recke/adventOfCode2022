package day_20;

public class ListNode {
	private long value;
	private ListNode next;
	private ListNode prev;
	
	public final boolean isHead;
	
	
	public ListNode(long value, ListNode next, ListNode prev, boolean isHead) {
		this.value = value;
		this.next = next;
		this.prev = prev;
		this.isHead = isHead;
	}
	
	
	public long getValue() {
		return this.value;
	}
	
	public ListNode getNext() {
		return this.next;
	}
	
	public ListNode getPrev() {
		return this.prev;
	}
	
	public void setValue(long value) {
		this.value = value;
	}
	
	public void setNext(ListNode next) {
		this.next = next;
	}
	
	public void setPrev(ListNode prev) {
		this.prev = prev;
	}
	
}
