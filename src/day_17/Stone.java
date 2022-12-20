package day_17;

import java.util.HashSet;
import java.util.Set;

/**
 * Repräsentiert einen Stein, der aus 
 * mehreren Einzelteilen besteht. Es gibt:
 * 
 * ####
 * 
 *  #
 * ###
 *  #
 *  
 *   #
 *   #
 * ###
 * 
 * # 
 * # 
 * # 
 * # 
 * 
 * ## 
 * ## 
 * 
 * @author Yanik Recke
 */
public class Stone {
	/** Einzelteile eines Steins */
	private final Position[] parts;
	
	
	/**
	 * Konstruktor, erstellt einen Stein.
	 * 
	 * @param currHighestPos - bis jetzt höchste festgelegte Position
	 * @param type - der Typ des zu erstellenden Steins
	 */
	public Stone(Position currHighestPos, StoneType type) {
		int y = currHighestPos.getY() + 4;
		
		switch (type) {
			case CROSS -> {
				this.parts = new Position[]{
						new Position(3, y + 2), 
						new Position(4, y + 1), 
						new Position(3, y + 1),
						new Position(2, y + 1),
						new Position(3, y)
				};
			}
			
			case HORIZONTAL_LINE -> {
				this.parts = new Position[] {
						new Position(2, y),
						new Position(3, y),
						new Position(4, y),
						new Position(5, y)
				};
			}
			
			case REVERSED_L -> {
				this.parts = new Position[] {
						new Position(4, y + 2),
						new Position(4, y + 1),
						new Position(4, y),
						new Position(3, y),
						new Position(2, y)
				};
			}
			
			case LINE -> {
				this.parts = new Position[] {
					new Position(2, y),
					new Position(2, y + 1),
					new Position(2, y + 2),
					new Position(2, y + 3)
				};
			}
			
			// Würfel
			default -> {
				this.parts = new Position[] {
						new Position(2, y),
						new Position(2, y + 1),
						new Position(3, y),
						new Position(3, y + 1)
				};
			}
		}
		
	}
	
	
	/**
	 * Getter für die Einzelteile
	 * eines gesamten Steins.
	 * 
	 * @return - Array mit allen Positionen eines Steins
	 */
	public Position[] getParts() {
		return this.parts;
	}
	
	
	/**
	 * Getter für eine Liste aller Positionen 
	 * eines Steins.
	 * 
	 * @return - Set mit allen Positionen eines Steins
	 */
	public Set<Position> getAllPositions(){
		Set<Position> positions = new HashSet<>();
		
		for (Position pos : this.parts){
			positions.add(pos);
		}
		
		return positions;
	}
	
	
	/**
	 * Verschiebt alle Einzelteile eines Steins eine
	 * y-Koordinate nach unten.
	 */
	public void moveDown() {
		for (int i = 0; i < parts.length; i++) {
			parts[i] = new Position(parts[i].getX(), parts[i].getY() - 1);
		}
	}
	
	
	/**
	 * Verschiebt alle Einzelteile eines Steins eine
	 * x-Koordinate nach rechts.
	 */
	public void moveRight() {
		for (int i = 0; i < parts.length; i++) {
			parts[i] = new Position(parts[i].getX() + 1, parts[i].getY());
		}
	}
	
	
	/*
	 * Verschiebt alle Einzelteile eines Steins eine x-Koordinate nach
	 * links.
	 */
	public void moveLeft() {
		for (int i = 0; i < parts.length; i++) {
			parts[i] = new Position(parts[i].getX() - 1, parts[i].getY());
		}
	}
	
	
	/**
	 * Prüft, ob es möglich ist, den Stein um eine y-Koordinate
	 * nach unten zu verschieben.
	 * 
	 * @param settled - Menge an bereits festsitzenden Positionen
	 * @return - true, wenn der Weg nach unten frei ist, false wenn nicht
	 */
	public boolean canMoveDown(Set<Position> settled) {
		boolean collides = false;
		
		for (int i = 0; i < parts.length && !collides; i++) {
			Position nxtPos = new Position(parts[i].getX(), parts[i].getY() + 1);
			if (settled.contains(nxtPos) || nxtPos.getY() < 0) {
				collides = true;
			}
		}
		
		return collides;
	}
	
	
	/**
	 * Prüft, ob es möglich ist, den Stein um eine x-Koordinate
	 * nach rechts zu verschieben.
	 * 
	 * Wenn x dadurch >= upperX wird -> false!
	 * 
	 * @param settled - Menge an bereits festsitzenden Positionen
	 * @param upperX - Obere x-Grenze
	 * @return - true, wenn der Weg nach rechts frei ist, false, wenn nicht
	 */
	public boolean canMoveRight(Set<Position> settled, int upperX) {
		boolean collides = false;
		
		for (int i = 0; i < parts.length && !collides; i++) {
			Position nxtPos = new Position(parts[i].getX() + 1, parts[i].getY());
			if (settled.contains(nxtPos) || nxtPos.getX() >= upperX) {
				collides = true;
			}
		}
		
		return collides;
	}
	
	
	/**
	 * Prüft, ob es möglich ist, den Stein um eine x-Koordinate
	 * nach links zu verschieben.
	 * 
	 * @param settled - Menge an bereits festsitzenden Positionen
	 * @return - true, wenn der Weg nach links frei ist, false, wenn nicht
	 */
	public boolean canMoveLeft(Set<Position> settled) {
		boolean collides = false;
		
		for (int i = 0; i < parts.length && !collides; i++) {
			Position nxtPos = new Position(parts[i].getX() - 1, parts[i].getY());
			if (settled.contains(nxtPos) || nxtPos.getX() < 0) {
				collides = true;
			}
		}
		
		return collides;
	}
	
	
	/**
	 * Berechnet die höchste Position des 
	 * Steins.
	 * 
	 * @return - die höchste Position des Steins
	 */
	public Position getHighest() {
		Position temp = new Position(0, -1);
		
		for (int i = 0; i < parts.length; i++) {
			if (parts[i].getY() > temp.getY()) {
				temp = parts[i];
			}
		}
		
		return temp;
	}
	
	/**
	 * Berechnet die linkeste Position des 
	 * Steins.
	 * 
	 * @return - die linkeste Position des Steins
	 */
	public Position getMostLeft() {
		Position temp = new Position(Integer.MAX_VALUE, 0);
		
		for (int i = 0; i < parts.length; i++) {
			if (parts[i].getX() < temp.getX()) {
				temp = parts[i];
			}
		}
		
		return temp;
	}
}
