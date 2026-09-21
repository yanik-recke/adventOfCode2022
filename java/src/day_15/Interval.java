package day_15;

/**
 * Klasse die ein Intervall mit Ober- und
 * Untergrenze repräsentiert.
 * 
 * @author recke
 *
 */
public class Interval {
	/** Untergrenze */
	public int lower;
	/** Obergrenze */
	public int upper; 
	
	
	
	/**
	 * Konstruktor, der ein neues Intervall mit
	 * "falschen" Grenzen erstellt.
	 */
	public Interval() {
		this.lower = Integer.MAX_VALUE;
		this.upper = Integer.MIN_VALUE;
	}
	
	/**
	 * Konstruktor, der ein Intervall mit 
	 * übergebenen Grenzen erstellt.
	 * 
	 * @param lower - Untergrenze
	 * @param upper - Obergrenze
	 */
	public Interval(int lower, int upper) {
		this.lower = lower;
		this.upper = upper;
	}
	
	
	/**
	 * Prüft, ob sich zwei Intervalle berühren.
	 * 
	 * @param interval - das übergebene Intervall
	 * @return - true, wenn sie sich berühren, false, wenn nicht
	 */
	public boolean areTouching(Interval interval) {
		if (interval.lower <= this.lower && interval.upper >= this.lower) {
			return true;
		} else if (interval.lower >= this.lower && interval.lower <= this.upper){
			return true;
		} else if (interval.lower <= this.lower && interval.upper >= this.upper) {
			return true;
		} else if (interval.lower >= this.lower && interval.upper <= this.upper){
			return true;
		} else if (this.upper + 1 == interval.lower || this.lower - 1 == interval.upper){
			return true;
		} else {
			return false;
		}
	}
	
	
	/**
	 * Falls sich zwei Intervalle berühren, 
	 * können diese zusammengefasst werden.
	 * 
	 * @param interval - das übergebene Intervall
	 * @return - das neue, zusammengefasste Intervall
	 */
	public Interval makeItFitting(Interval interval) {
		if (areTouching(interval)) {
			if (this.lower >= interval.lower) {
				this.lower = interval.lower;
			} 
		
			if (this.upper <= interval.upper) {
				this.upper = interval.upper;
			}
			
			return this;
		} else {
			return null;
		}
	}
	
	
	/*
	 * Gibt die Größe des umschlossenen Bereichs wieder. 
	 */
	public int size() {
		return this.upper - this.lower + 1;
	}
	
	
	@Override
	public int hashCode() {
	    int result = upper;
	    result = 31 * result + lower;
	    return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		return (obj instanceof Interval && ((Interval) obj).upper == this.upper && ((Interval) obj).lower == this.lower);
	}
	
}
