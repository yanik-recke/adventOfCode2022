package day_17;

/**
 * Positionsklasse mit Koordinaten als long.
 * 
 * @author Yanik Recke
 */
class PositionLong {
	private int x = 0;
	private long y = 0;
	
	/**
	 * Konstruktor, setzt x und y.
	 * 
	 * @param x
	 * @param y
	 */
	public PositionLong(int x, long y) {
		this.x = x;
		this.y = y;
	}
	
	
	/**
	 * Getter für x.
	 * 
	 * @return - x-Koordinate
	 */
	public int getX() {
		return this.x;
	}
	
	/**
	 * Getter für y.
	 * 
	 * @return - y-Koordinate
	 */
	public long getY() {
		return this.y;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		return obj != null && obj instanceof PositionLong && ((PositionLong) obj).getX() == this.x && ((PositionLong) obj).getY() == this.y;
	}
	
	@Override
	public int hashCode() {
	    int result = x;
	    result = 31 * result + (int) y;
	    return result;
	}
	
	@Override
	public String toString() {
		return this.x + "|" + this.y;
	}
}
