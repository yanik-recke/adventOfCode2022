package day_15;


/**
 * Klasse, die einen Sensor repräsentiert.
 * 
 * @author Yanik Recke
 */
public class Sensor {
	/** Position des nähesten Beacons eines Sensors */
	public Position nearestBeacon;
	/** Die Position des Sensors */
	public Position position;
	/** Die Entfernung zum nähesten Beacon */
	public int distanceToNearest;
	
	
	/**
	 * Konstruktor, mit dem ein
	 * Sensor erstellt wird.
	 * 
	 * @param position - Position des Sensors
	 * @param nearest - Position des nähesten Beacons
	 */
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
