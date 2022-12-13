package day_13;

import java.util.List;

/**
 * Klasse die ein Paket repräsentiert.
 * 
 * @author Yanik Recke
 */
public class Packet {
	/** Liste mit den Inhalten */
	public List<Object> list;
	/** Parent Liste (Paket) */
	public Packet parent;
	
	/**
	 * Konstruktor, setzt Liste und Parent.
	 * 
	 * @param list - Liste
	 * @param parent - Parent
	 */
	public Packet(List<Object> list, Packet parent) {
		this.list = list;
		this.parent = parent;
	}
}
