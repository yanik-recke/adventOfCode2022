package day_15;

public class Sensor {

	public Position nearestBeacon;
	public Position position;
	public int distanceToNearest;
	
	public Sensor(Position position, Position nearest) {
		assert (position != null);
		assert (nearest != null);
		
		this.position = position;
		this.nearestBeacon = nearest;
		
		this.distanceToNearest = Math.abs(position.getX() - nearest.getX()) + Math.abs(position.getY() - nearest.getY());
	}
	
	
	@Override
	public String toString() {
		return "Position: " + position.toString() + " Beacon: " + nearestBeacon.toString();
	}
}
