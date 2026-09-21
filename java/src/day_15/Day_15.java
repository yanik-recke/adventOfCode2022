package day_15;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;


/**
 * Lösung für Tag 15.
 * 
 * @author Yanik Recke
 */
public class Day_15 {
	public final static int ROW = 20000;
	
	
	public static void main(String[] args) {
		String pathToInput = "src/day_15/input.txt";
		
		System.out.println("Part 1: " + part1(pathToInput) + " - Part 2: " + part2(pathToInput));
	}
	
	
	/**
	 * Berechnet, ob die entsprechende Reihe von einem
	 * Sensor aus mit der Entfernung zu seinem nähesten Beacon
	 * erreichbar ist. Wenn ja, dann wird berechnet wie viel von dieser
	 * Reihe, durch den Sensor "blockiert" wird und alle blockierten
	 * Positionen einem Set hinzugefügt.
	 * 
	 * @param path - Pfad zum Puzzle Input
	 * @return - Anzahl an "blockierter" Positionen
	 */
	private static int part1(String path) {
		List<String> input = helpers.HelperMethods.getInputAsListOfString(path);
		List<Sensor> sensors = new ArrayList<>();
		List<Position> listOfSensorPos = new ArrayList<>();
		List<Position> beacons = new ArrayList<>();
		
		for (String line : input) {
			int x = Integer.parseInt(line.substring(line.indexOf("x=") + 2, line.indexOf(',')));
			int y  = Integer.parseInt(line.substring(line.indexOf("y=") + 2, line.indexOf(':')));
			
			Position sensor = new Position(x,y);
			listOfSensorPos.add(sensor);
			
			x = Integer.parseInt(line.substring(line.lastIndexOf("x=") + 2, line.lastIndexOf(',')));
			y  = Integer.parseInt(line.substring(line.lastIndexOf("y=") + 2));
			
			Position beacon = new Position(x,y);
			beacons.add(beacon);
			sensors.add(new Sensor(sensor, beacon));
		}
		
		Set<Position> impossible = new HashSet<>();
		
		for (Sensor sensor : sensors) {
			
			int currX = sensor.position.getX();
			int currY = sensor.position.getY();
			int number = sensor.distanceToNearest;
			int offset = 0;
			
			boolean yes = false;
			
			if ((currY < ROW) && (currY + sensor.distanceToNearest >= ROW)) {
				offset = ROW - currY;
				yes = true;
				number = currX - sensor.distanceToNearest + offset;
			} else if ((currY > ROW) && currY - sensor.distanceToNearest <= ROW) {
				offset = currY - ROW;
				yes = true;
				number = currX - sensor.distanceToNearest + offset;
			}
			
			
			if (yes) {
				for (int x = number; x <= (currX + sensor.distanceToNearest - offset); x++) {			
					if (!beacons.contains(new Position(x, ROW)) && 
						!listOfSensorPos.contains(new Position(x, ROW))) {
							impossible.add(new Position(x, ROW));
					}
				}
			}

		}

		return impossible.size();
	}
	
	
	/**
	 * Berechnet für jede Reihe, startend bei 4.000.000
	 * ob die entsprechende Reihe von einem Sensor mit der Länge
	 * bis zu seinem nähesten Beacon erreicht werden kann. Wenn ja,
	 * dann wird ein neues Intervall mit der entsprechenden Länge an
	 * Positionen erstellt, die von dem Sensor "blockiert" werden. Wenn man
	 * jeden Sensor für eine Reihe abgearbeitet hat, wird geguckt, ob sich der
	 * gesuchte Beacon in der Reihe befinden kann.
	 * 
	 * @param path - Pfad zum Puzzle Input
	 * @return - Angabe, was berechnet werden muss als String
	 */
	private static String part2(String path) {
		List<String> input = helpers.HelperMethods.getInputAsListOfString(path);
		List<Sensor> sensors = new ArrayList<>();
		List<Position> listOfSensorPos = new ArrayList<>();
		List<Position> beacons = new ArrayList<>();
		
		for (String line : input) {
			int x = Integer.parseInt(line.substring(line.indexOf("x=") + 2, line.indexOf(',')));
			int y  = Integer.parseInt(line.substring(line.indexOf("y=") + 2, line.indexOf(':')));
			
			Position sensor = new Position(x,y);
			listOfSensorPos.add(sensor);
			
			x = Integer.parseInt(line.substring(line.lastIndexOf("x=") + 2, line.lastIndexOf(',')));
			y  = Integer.parseInt(line.substring(line.lastIndexOf("y=") + 2));
			
			Position beacon = new Position(x,y);
			beacons.add(beacon);
			sensors.add(new Sensor(sensor, beacon));
		}
		
		Position pos = null;
		boolean found = false;
		
		List<Interval> intervals = new ArrayList<>();
		
		Interval interval = null;
		int row0 = -1;
		for (int row = 4000000; row >= 0 && !found; row--) {
			intervals = new ArrayList<>();
			
			for (Sensor sensor : sensors) {
				int currX = sensor.position.getX();
				int currY = sensor.position.getY();
				
				int number = sensor.distanceToNearest;
				int offset = 0;
			
				boolean yes = false;
			
				if ((currY < row) && (currY + sensor.distanceToNearest >= row)) {
					offset = row - currY;
					yes = true;
					number = currX - sensor.distanceToNearest + offset;
				} else if ((currY > row) && currY - sensor.distanceToNearest <= row) {
					offset = currY - row;
					yes = true;
					number = currX - sensor.distanceToNearest + offset;
				} else if (sensor.position.getY() == row) {
					offset = 0;
					yes = true;
					number = currX - sensor.distanceToNearest;
				}
			
				if (yes) {
					interval = new Interval(number, (currX + sensor.distanceToNearest - offset));
					
					if (intervals.size() == 0) {
						intervals.add(interval);
					} else {
						boolean done = false;
						
						ListIterator<Interval> it = intervals.listIterator();
						Interval temp;
						
						while (it.hasNext()) {
							temp = it.next();
							
							if (interval.areTouching(temp)) {
								it.remove();
								interval = interval.makeItFitting(temp);
								
								if (!intervals.contains(interval)) {
									it.add(interval);
								}
									
								done = true;
							}
						}
						
						if (!done) {
							intervals.add(interval);
						}
					}
				}
				

			}

			if (intervals.size() > 1) {
				row0 = row;
				found = true;
			} else if (intervals.get(0).size() <= 4000000) {
				row0 = row;
				found = true;
			} else if (intervals.get(0).lower <= 0 && intervals.get(0).upper <= 4000000) {
				row0 = row;
				found = true;
			} else if (intervals.get(0).upper >= 4000000 && intervals.get(0).lower > 0) {
				row0 = row;
				found = true;
			}
		}
		
		
		int lowestLow = Integer.MAX_VALUE;
		Interval lower = null;
		
		for (Interval inter : intervals) {
			if (lowestLow > inter.lower) {
				lowestLow = inter.lower;
				lower = inter;
			}
		}

		pos = new Position(lower.upper + 1, row0);
		return pos.getX() + " * 4000000 + " + pos.getY();
	}
	
}
