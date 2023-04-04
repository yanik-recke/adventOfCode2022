package day_23;

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


	/**
	 * Berechnet die Nachbarskoordinaten in Abhängigkeit
	 * einer übergebenen Richtung.
	 *
	 * @param dir - die Richtung
	 * @return - die Nachbarsposition
	 */
	public Position getNeighbour(Direction dir) {
		assert (dir != null);

		return switch (dir) {
			case N ->  new Position(this.x, this.y - 1);

			case NE -> new Position(this.x + 1, this.y - 1);

			case E -> new Position(this.x + 1, this.y);

			case SE -> new Position(this.x + 1, this.y + 1);

			case S -> new Position(this.x , this.y + 1);

			case SW -> new Position(this.x - 1, this.y + 1);

			case W -> new Position(this.x - 1, this.y);

			case NW -> new Position(this.x - 1, this.y - 1);
		};
	}
	
	
	@Override
	public boolean equals(Object obj) {
		return obj != null && obj instanceof Position && ((Position) obj).getX() == this.x && ((Position) obj).getY() == this.y;
	}
	
	@Override
	public int hashCode() {
	    int result = x;
	    result = 31 * result + y;
	    return result;
	}
	
	@Override
	public String toString() {
		return this.x + "|" + this.y;
	}
}
