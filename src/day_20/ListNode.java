package day_20;


/**
 * Implementierung einer doppelt
 * verketten Liste.
 * 
 * @author Yanik Recke
 */
public class ListNode {
	/** Zahl des Elements */
	private long value;
	/** Id des Elements */
	private final int id;
	/** Nächstes Element der Liste */
	private ListNode next;
	/** Vorheriges Element der Liste */
	private ListNode prev;
	/** Ob das Element der ursprüngliche Head ist */
	public final boolean isHead;
	
	
	/**
	 * Konstruktor.
	 * 
	 * @param value - Zahl
	 * @param next - nächstes Element
	 * @param prev - vorheriges Element
	 * @param isHead - ob das Element der Kopf ist
	 * @param id - die Id des Elements
	 */
	public ListNode(long value, ListNode next, ListNode prev, boolean isHead, int id) {
		this.value = value;
		this.next = next;
		this.prev = prev;
		this.isHead = isHead;
		this.id = id;
	}
	
	
	/**
	 * Findet das Element der Liste
	 * mit der entsprechenden Id.
	 * 
	 * @param id - die Id des zu findenden Elements
	 * @return - das entsprechende Element
	 */
	public ListNode getNodeByIdForward(int id) {
		if (this.id == id) {
			return this;
		} else {
			return this.next.getNodeByIdForward(id);
		}
	}
	
	
	/**
	 * Entfernt ein Element
	 * aus der Liste.
	 * 
	 * @return - das entfernte Element
	 */
	public ListNode removeNode() {
		this.prev.setNext(this.next);
		this.next.setPrev(this.prev);
		
		return this;
	}
	
	
	/**
	 * Fügt ein Element in die
	 * Liste ein.
	 * 
	 * @param node - das einzufügende Element
	 */
	public void insertNode(ListNode node) {
		/*
		 * A, B, C, D
		 * -> remove D
		 * A, B, C
		 * -> insert D after B
		 * A, B, D, C
		 * -> B.next = D, D.prev = B, D.next = C, C.prev = D
		 * B = this, D = node, C = this.prev
		 */
		this.next.prev = node;
		node.next = this.next;
		node.prev = this;
		
		this.next = node;
	}
	
	
	/**
	 * Getter für die Zahl
	 * des Elements.
	 * 
	 * @return - die Zahl
	 */
	public long getValue() {
		return this.value;
	}
	
	
	/**
	 * Getter für das nächste Element
	 * der Liste.
	 * 
	 * @return - das nächste Element
	 */
	public ListNode getNext() {
		return this.next;
	}
	
	
	/**
	 * Getter für das vorherige
	 * Element der Liste.
	 * 
	 * @return - das vorherige Element
	 */
	public ListNode getPrev() {
		return this.prev;
	}
	
	
	/**
	 * Getter für die Id des
	 * Elements.
	 * 
	 * @return - die Id
	 */
	public int getId() {
		return this.id;
	}
	
	
	/**
	 * Setter für die Zahl
	 * des Elements.
	 * 
	 * @param value - die Zahl
	 */
	public void setValue(long value) {
		this.value = value;
	}
	
	
	/**
	 * Setter für das nächste
	 * Element der Liste.
	 * 
	 * @param next - das neue nächste Element der Liste
	 */
	public void setNext(ListNode next) {
		this.next = next;
	}
	
	
	/**
	 * Setter für das vorherige Element
	 * der Liste.
	 * 
	 * @param prev - das neue vorherige Element der Liste
	 */
	public void setPrev(ListNode prev) {
		this.prev = prev;
	}
	
	@Override
	public String toString() {
		return "value: " + this.value;
	}
	
}
