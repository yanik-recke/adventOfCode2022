package day_22;

public enum Direction {
	RIGHT, DOWN, LEFT, TOP;
	
	public Direction opposite;
	
	static {
		TOP.opposite = DOWN;
		RIGHT.opposite = LEFT;
		DOWN.opposite = TOP;
		LEFT.opposite = RIGHT;
	}
}
