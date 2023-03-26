package day_19_without_part2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import day_19.materials.Material;
import day_19.materials.MaterialType;
import day_19.robots.Robot;
import day_19.robots.RobotType;

/**
 * Lösung für Tag 19.
 * 
 * @author Yanik Recke
 */
public class Day_19 {

	public static void main(String[] args) {
		String pathToInput = "src/day_19/input.txt";
		
		//System.out.println(part1(pathToInput) + " - " + part2(pathToInput));
		System.out.println(part1(pathToInput));
		//System.out.println(part2(pathToInput));
	}
	
	
	private static long part1(String path) {
		int highest = 0;
		
		List<String> input = helpers.HelperMethods.getInputAsListOfString(path);

		List<Robot[]> robot_costs = new ArrayList<>();
		List<Map<RobotType, Robot>> costs = new ArrayList<>();
		
		int j = 0;
		
		for (String line : input) {
			String[] temp = line.split(" ");
			
			Robot[] all_robots = new Robot[4];
			
			all_robots[0] = new Robot(RobotType.ORE);
			all_robots[0].cost.add(new Material(Integer.parseInt(temp[6]), MaterialType.ORE));
			costs.add(new HashMap<>());
			costs.get(j).put(RobotType.ORE, new Robot(RobotType.ORE));
			costs.get(j).get(RobotType.ORE).cost.add(new Material(Integer.parseInt(temp[6]), MaterialType.ORE));
			
			all_robots[1] = new Robot(RobotType.CLAY);
			all_robots[1].cost.add(new Material(Integer.parseInt(temp[12]), MaterialType.ORE));
			costs.get(j).put(RobotType.CLAY, new Robot(RobotType.CLAY));
			costs.get(j).get(RobotType.CLAY).cost.add(new Material(Integer.parseInt(temp[12]), MaterialType.ORE));

			all_robots[2] = new Robot(RobotType.OBSIDIAN);
			all_robots[2].cost.add(new Material(Integer.parseInt(temp[18]), MaterialType.ORE));
			all_robots[2].cost.add(new Material(Integer.parseInt(temp[21]), MaterialType.CLAY));
			costs.get(j).put(RobotType.OBSIDIAN, new Robot(RobotType.OBSIDIAN));
			costs.get(j).get(RobotType.OBSIDIAN).cost.add(new Material(Integer.parseInt(temp[18]), MaterialType.ORE));
			costs.get(j).get(RobotType.OBSIDIAN).cost.add(new Material(Integer.parseInt(temp[21]), MaterialType.CLAY));

			all_robots[3] = new Robot(RobotType.GEODE);
			all_robots[3].cost.add(new Material(Integer.parseInt(temp[27]), MaterialType.ORE));
			all_robots[3].cost.add(new Material(Integer.parseInt(temp[30]), MaterialType.OBSIDIAN));
			costs.get(j).put(RobotType.GEODE, new Robot(RobotType.GEODE));
			costs.get(j).get(RobotType.GEODE).cost.add(new Material(Integer.parseInt(temp[27]), MaterialType.ORE));
			costs.get(j).get(RobotType.GEODE).cost.add(new Material(Integer.parseInt(temp[30]), MaterialType.OBSIDIAN));
			
			j++;
			robot_costs.add(all_robots);
		}
		
		System.out.println("Size of robots_ " + robot_costs.size());
		
		Thread[] threads = new Thread[29];
		Day_19_Run runs[] = new Day_19_Run[29];
		for (int i = 0; i < input.size() - 1; i++) {
			Map<MaterialType, Integer> storage = initStorage();
			
			runs[i] = new Day_19_Run(storage, initCurrRobots(), robot_costs.get(i), calcPossible(storage, robot_costs.get(i)), 24, costs.get(i)); 
			threads[i] = new Thread(runs[i]);
			threads[i].start();
			System.out.println(i);
		}
		
		Map<MaterialType, Integer> storage = initStorage();
		highest = calc(storage, initCurrRobots(), robot_costs.get(29), calcPossible(storage, robot_costs.get(29)), 0, 0, 24, costs.get(29));
		
		System.out.println(highest);
		for (Thread t : threads) {
			try {
				t.join();
			} catch (InterruptedException e) {
				// sollte nicht passieren
				e.printStackTrace();
			}
		}
		
		long temp = 0;
		for (int i = 0; i < runs.length; i++) {
			System.out.println("id: " + i + " - " + runs[i].result);
			temp += (i + 1) * runs[i].result;
		}
		
		temp += 30 * highest;
		
		
		return temp;
	}
	
	
	private static Map<RobotType, Integer> initCurrRobots(){
		// wie viele Roboter vorhanden sind
		Map<RobotType, Integer> robots = new HashMap<>();

		for (RobotType type : RobotType.values()) {
			robots.put(type, 0);
		}
		
		// Beginn mit einem Ore Roboter
		robots.put(RobotType.ORE, 1);
		
		return robots;
	}
	
	
	private static Map<MaterialType, Integer> initStorage(){
		
		// wie viel von jedem Material gerade vorhanden ist
		Map<MaterialType, Integer> storage = new HashMap<>();

		for (MaterialType material : MaterialType.values()) {
			storage.put(material, 0);
		}
		
		return storage;
	}
	
	
	/**
	 * Berechnet welche Robotertypen
	 * mit den aktuellen Ressourcen gebaut werden
	 * können.
	 * 
	 * @param storage - aktuelle Ressourcen
	 * @param robots - Roboter Baupläne
	 * @return - Liste mit allen Robotertypen die gebaut werden können, enthält immer
	 			 mind. NONE
	 */
	protected static Set<RobotType> calcPossible(Map<MaterialType, Integer> storage, Robot[] robots) {
		Set<RobotType> possibles = new HashSet<>();
		
		for (Robot robot : robots) {
			Iterator<Material> it = robot.cost.iterator();
			boolean possible = true;
			while (it.hasNext() && possible) {
				Material tempMat = it.next();
				
				if (tempMat.amount > storage.get(tempMat.type)) {
					possible = false;
				}
			}
			
			if (possible) {
				possibles.add(robot.type);
			}
		}
		
		possibles.add(RobotType.NONE);
		return possibles;
	}
	
	// for part 1 = 24, part 2 = 32
	private static final int UB_TIME = 32;
	
	protected static int calc(Map<MaterialType, Integer> storage, Map<RobotType, Integer> currRobots, Robot[] robots, Set<RobotType> possible, int time, int alreadyReached, int currEarliest, Map<RobotType, Robot> costs) {
		time++;
		int currHighest = 0;
		
		int sum = (((UB_TIME - (time - 1))  + 1) * (UB_TIME - (time - 1))) / 2 - (UB_TIME - (time - 1)) + (UB_TIME - (time - 1)) * currRobots.get(RobotType.GEODE);
		
		if (time > currEarliest && !currRobots.containsKey(RobotType.GEODE)) {
			return currHighest;
		} else if (time == currEarliest && !possible.contains(RobotType.GEODE) && !currRobots.containsKey(RobotType.GEODE)) {
			return currHighest;
		}
		
		if ((time <= UB_TIME) && (sum + storage.get(MaterialType.GEODE)> alreadyReached)) {
			// Einsammeln:
			Map<MaterialType, Integer> new_storage = new HashMap<>(storage);

			// Hinzufügen der gesammelten Ressourcen der vorhandenen Roboter
			for (RobotType typeOfBuilt : currRobots.keySet()) {
				// Erhöhen um die Anzahl an vorhandener Roboter
				switch (typeOfBuilt) {
					case ORE -> {
						new_storage.put(MaterialType.ORE, storage.get(MaterialType.ORE) + currRobots.get(typeOfBuilt));
					}

					case CLAY -> {
						new_storage.put(MaterialType.CLAY, storage.get(MaterialType.CLAY) + currRobots.get(typeOfBuilt));
					}

					case OBSIDIAN -> {
						new_storage.put(MaterialType.OBSIDIAN,
								storage.get(MaterialType.OBSIDIAN) + currRobots.get(typeOfBuilt));
					}
					
					case NONE -> {
						;
					}

					// GEODE
					default -> {
						new_storage.put(MaterialType.GEODE, storage.get(MaterialType.GEODE) + currRobots.get(typeOfBuilt));
					}
				}
			}
			
			// Wenn man ein Geode Roboter bauen kann, diesen immer bauen
			if (time != UB_TIME && possible.contains(RobotType.GEODE)) {
				
				if (currRobots.keySet().contains(RobotType.GEODE) || (!currRobots.keySet().contains(RobotType.GEODE) && currEarliest >= time)) {
					// Den, der gebaut wird, um eins erhöhen
					Map<RobotType, Integer> tempRobots = new HashMap<>(currRobots);
					tempRobots.put(RobotType.GEODE, tempRobots.get(RobotType.GEODE) + 1);

					Map<MaterialType, Integer> tempStorage = new HashMap<>(new_storage);

					// Abziehen der Kosten
					for (Material cost : costs.get(RobotType.GEODE).cost) {
						tempStorage.put(cost.type, tempStorage.get(cost.type) - cost.amount);
					}

					Set<RobotType> newPossible = calcPossible(tempStorage, robots);

					int tempEarliest = currEarliest;

					if (time < currEarliest) {
						tempEarliest = time;
					}

					int temp = calc(tempStorage, tempRobots, robots, newPossible, time, currHighest, tempEarliest, costs);

					if (temp > currHighest) {
						currHighest = temp;
					}
				} else {
					return storage.get(MaterialType.GEODE);
				}
				
			// in letzter Minute, keinen mehr produzieren
			} else if (time != UB_TIME) {
				for (RobotType type : possible) {

					if (type != RobotType.NONE) {
						// Den, der gebaut wird, um eins erhöhen
						Map<RobotType, Integer> tempRobots = new HashMap<>(currRobots);
						tempRobots.put(type, tempRobots.get(type) + 1);

						Map<MaterialType, Integer> tempStorage = new HashMap<>(new_storage);
						
						// Abziehen der verwendeten Ressourcen
						for (Material cost : costs.get(RobotType.GEODE).cost) {
							tempStorage.put(cost.type, tempStorage.get(cost.type) - cost.amount);
						}

						Set<RobotType> newPossible = calcPossible(tempStorage, robots);
						
						int temp = calc(tempStorage, tempRobots, robots, newPossible, time, currHighest, currEarliest, costs);

						if (temp > currHighest) {
							currHighest = temp;
						}
					} else {
						// falls NONE (kein Roboter wird gebaut):
						Set<RobotType> newPossible = calcPossible(new_storage, robots);
						
						newPossible.removeAll(possible);
						
						if (!newPossible.contains(RobotType.NONE)) {
							newPossible.add(RobotType.NONE);
						}
						
						// currRobots hat sich nicht verändert, kann weiter verwendet werden
						int temp = calc(new_storage, currRobots, robots, newPossible, time, currHighest, currEarliest, costs);

						if (temp > currHighest) {
							currHighest = temp;
						}
					}
				}
			} 
		} else {
			currHighest = storage.get(MaterialType.GEODE);
		}
		
		return currHighest;
	}
	
	
	private static long part2(String path) {
		int highest = 0;
		
		List<String> input = helpers.HelperMethods.getInputAsListOfString(path);

		List<Robot[]> robot_costs = new ArrayList<>();

		
		List<Map<RobotType, Robot>> costs = new ArrayList<>();
		
		int j = 0;
		for (String line : input) {
			String[] temp = line.split(" ");
			
			Robot[] all_robots = new Robot[4];
			
			all_robots[0] = new Robot(RobotType.ORE);
			all_robots[0].cost.add(new Material(Integer.parseInt(temp[6]), MaterialType.ORE));
			costs.add(new HashMap<>());
			costs.get(j).put(RobotType.ORE, new Robot(RobotType.ORE));
			costs.get(j).get(RobotType.ORE).cost.add(new Material(Integer.parseInt(temp[6]), MaterialType.ORE));
			
			all_robots[1] = new Robot(RobotType.CLAY);
			all_robots[1].cost.add(new Material(Integer.parseInt(temp[12]), MaterialType.ORE));
			costs.get(j).put(RobotType.CLAY, new Robot(RobotType.CLAY));
			costs.get(j).get(RobotType.CLAY).cost.add(new Material(Integer.parseInt(temp[12]), MaterialType.ORE));

			all_robots[2] = new Robot(RobotType.OBSIDIAN);
			all_robots[2].cost.add(new Material(Integer.parseInt(temp[18]), MaterialType.ORE));
			all_robots[2].cost.add(new Material(Integer.parseInt(temp[21]), MaterialType.CLAY));
			costs.get(j).put(RobotType.OBSIDIAN, new Robot(RobotType.OBSIDIAN));
			costs.get(j).get(RobotType.OBSIDIAN).cost.add(new Material(Integer.parseInt(temp[18]), MaterialType.ORE));
			costs.get(j).get(RobotType.OBSIDIAN).cost.add(new Material(Integer.parseInt(temp[21]), MaterialType.CLAY));

			all_robots[3] = new Robot(RobotType.GEODE);
			all_robots[3].cost.add(new Material(Integer.parseInt(temp[27]), MaterialType.ORE));
			all_robots[3].cost.add(new Material(Integer.parseInt(temp[30]), MaterialType.OBSIDIAN));
			costs.get(j).put(RobotType.GEODE, new Robot(RobotType.GEODE));
			costs.get(j).get(RobotType.GEODE).cost.add(new Material(Integer.parseInt(temp[27]), MaterialType.ORE));
			costs.get(j).get(RobotType.GEODE).cost.add(new Material(Integer.parseInt(temp[30]), MaterialType.OBSIDIAN));
			
			j++;
			robot_costs.add(all_robots);
		}
		
		System.out.println("Size of robots_ " + robot_costs.size());
		
		Thread[] threads = new Thread[29];
		Day_19_Run runs[] = new Day_19_Run[29];
		for (int i = 0; i < input.size() - 1; i++) {
			Map<MaterialType, Integer> storage = initStorage();
			
			runs[i] = new Day_19_Run(storage, initCurrRobots(), robot_costs.get(i), calcPossible(storage, robot_costs.get(i)), 32, costs.get(i)); 
			threads[i] = new Thread(runs[i]);
			threads[i].start();
			System.out.println(i);
		}
		
		Map<MaterialType, Integer> storage = initStorage();
		highest = calc(storage, initCurrRobots(), robot_costs.get(2), calcPossible(storage, robot_costs.get(2)), 0, 0, 32, costs.get(2));
		
		for (Thread t : threads) {
			try {
				t.join();
			} catch (InterruptedException e) {
				// sollte nicht passieren
				e.printStackTrace();
			}
		}
 
		long temp = runs[0].result * runs[1].result * highest;
				
		return temp;
	}
}
