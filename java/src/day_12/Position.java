package day_12;

/**
 * Positionsklasse
 * 
 * @author Yanik Recke
 */
class Position {
	private int x = 0;
	private int y = 0;
	
	/**
	 * Konstruktor, setzt x und y.
	 * 
	 * @param x
	 * @param y
	 */
	public Position(int x, int y) {
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
	public int getY() {
		return this.y;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		return obj != null && obj instanceof Position && ((Position) obj).getX() == this.x && ((Position) obj).getY() == this.y;
	}
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.x + " - " + this.y;
	}
}
