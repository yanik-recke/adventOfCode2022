package day_16;


/**
 * Klasse, die ein Paar mit zwei
 * verschiedenen Datentypen repräsentiert.
 * 
 * @author Yanik Recke
 *
 * @param <E> - Datentyp des linken Elements
 * @param <T> - Datentyp des rechten Elements
 */
public class Pair<E, T> {
	/** Linkes Element des Paars */
	private E left;
	/** Rechtes Element des Paars */
	private T right;

	
	/**
	 * Konstruktor, setzt linkes und rechtes
	 * Element.
	 * 
	 * @param left - linkes  Element
	 * @param right - rechtes Element
	 */
	public Pair(E left, T right) {
		this.left = left;
		this.right = right;
	}

	
	/**
	 * Gibt das linke Element wieder.
	 * 
	 * @return - das linke Element
	 */
	public E l() {
		return this.left;
	}

	
	/**
	 * Gibt das rechte Element wieder.
	 * 
	 * @return - das rechte Element
	 */
	public T r() {
		return this.right;
	}

	@Override
	public String toString() {
		return this.l() + ":" + this.r();
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof Pair && ((Pair<?, ?>) obj).l() instanceof Valve && ((Pair<?, ?>) obj).l().equals(this.left);
	}
}
