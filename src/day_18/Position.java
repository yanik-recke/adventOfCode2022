package day_18;

/**
 * Positionsklasse für ein drei-dimensionales
 * Koordinatensystem.
 * 
 * @author Yanik Recke
 */
class Position {
	private int x = 0;
	private int y = 0;
	private int z = 0;
	
	/**
	 * Konstruktor, setzt x, y und z.
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public Position(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
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
	public int getY() {
		return this.y;
	}
	
	
	/**
	 * Getter für z.
	 * 
	 * @return - z-Koordinate
	 */
	public int getZ() {
		return this.z;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		return obj != null && obj instanceof Position && ((Position) obj).getX() == this.x && ((Position) obj).getY() == this.y &&
				((Position) obj).getZ() == this.z;
	}
	
	@Override
	public int hashCode() {
		/*
		 * Funktioniert in bestimmten Fällen nicht!
		 */
		int hash = 23;
		hash = hash * 31 + x;
		hash = hash * 31 + y;
		hash = hash * 31 + z;
		return hash;
	}
	
	@Override
	public String toString() {
		return this.x + "|" + this.y + "|" + this.z;
	}
}
