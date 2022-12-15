package day_15;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day_15 implements Runnable{
	public final static int ROW = 10;
	public volatile static int split = 500000;
	public static void main(String[] args) {
		String pathToInput = "src/day_15/input.txt";
		
		// System.out.println(part1(pathToInput));
		//part2(pathToInput);
		
		Thread t1 = new Thread(new Day_15());
		t1.run();
		
		Thread t2 = new Thread(new Day_15());
		t2.run();
		
		Thread t3 = new Thread(new Day_15());
		t3.run();
		
		Thread t4 = new Thread(new Day_15());
		t4.run();
		
		Thread t5 = new Thread(new Day_15());
		t5.run();
		
		Thread t6 = new Thread(new Day_15());
		t6.run();
		
		Thread t7 = new Thread(new Day_15());
		t7.run();
		
		Thread t8 = new Thread(new Day_15());
		t8.run();
	}
	
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
	
	private static int part2(String path, int splitThread) {
		int splitThreadLow = splitThread - 500000;
		
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
		Set<Position> possible = new HashSet<>();
		int max = 4000000;
		
		for (int row = splitThread; row >= splitThreadLow && !found; row--) {
			possible = new HashSet<>();
			
			for (int x = 0; x <= max; x++) {
				possible.add(new Position(x, row));
			}
			
			for (Sensor sensor : sensors) {
				int currX = sensor.position.getX();
				int currY = sensor.position.getY();
				
				if (currY == row) {
					possible.remove(sensor.position);
				}
				
				if (sensor.nearestBeacon.getY() == row) {
					possible.remove(sensor.nearestBeacon);
				}
				
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
					for (int x = number; x <= (currX + sensor.distanceToNearest - offset); x++) {	
						possible.remove(new Position(x, row));
					}
				}

			}
			
			if (possible.size() == 1) {
				found = true;
			}

		}
		
		for (Position position : possible) {
			pos = position;
		}
		
		System.out.println(pos);

		return 0;
	}
	


	@Override
	public void run() {
		int splitThread = split;
		split += split;
		
		part2("src/day_15/input.txt", splitThread);
	}
	
}
