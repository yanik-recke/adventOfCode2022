package day_19;

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
		
		System.out.println(part1(pathToInput) + " - " + part2(pathToInput));
	}
	
	
	private static int part1(String path) {
		int highest = 0;
		
		List<String> input = helpers.HelperMethods.getInputAsListOfString(path);


		for (String line : input) {
			String[] temp = line.split(" ");
			
			Robot[] all_robots = new Robot[4];
			
			all_robots[0] = new Robot(RobotType.ORE);
			all_robots[0].cost.add(new Material(Integer.parseInt(temp[6]), MaterialType.ORE));
			
			all_robots[1] = new Robot(RobotType.CLAY);
			all_robots[1].cost.add(new Material(Integer.parseInt(temp[12]), MaterialType.ORE));

			all_robots[2] = new Robot(RobotType.OBSIDIAN);
			all_robots[2].cost.add(new Material(Integer.parseInt(temp[18]), MaterialType.ORE));
			all_robots[2].cost.add(new Material(Integer.parseInt(temp[21]), MaterialType.CLAY));

			all_robots[3] = new Robot(RobotType.GEODE);
			all_robots[3].cost.add(new Material(Integer.parseInt(temp[27]), MaterialType.ORE));
			all_robots[3].cost.add(new Material(Integer.parseInt(temp[30]), MaterialType.OBSIDIAN));
			
			// wie viel von jedem Material gerade vorhanden ist
			Map<MaterialType, Integer> storage = new HashMap<>();
			
			for (MaterialType material : MaterialType.values()) {
				storage.put(material, 0);
			}
			
			// wie viele Roboter vorhanden sind
			Map<RobotType, Integer> robots = new HashMap<>();
			
			for (RobotType type : RobotType.values()) {
				robots.put(type, 0);
			}
			
			// Beginn mit einem Ore Roboter
			robots.put(RobotType.ORE, 1);
			
			highest = calc(storage, robots, all_robots, calcPossible(storage, all_robots), 0);
		}
		
		
		return highest;
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
	private static List<RobotType> calcPossible(Map<MaterialType, Integer> storage, Robot[] robots) {
		List<RobotType> possibles = new ArrayList<>();
		possibles.add(RobotType.NONE);
		
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
		
		return possibles;
	}
	
	
	
	private static int calc(Map<MaterialType, Integer> storage, Map<RobotType, Integer> currRobots, Robot[] robots, List<RobotType> possible, int time) {
		time++;
		int currHighest = 0;
		
		if (time == 24) {
			System.out.println("hier");			
		}
		
		if (time < 24) {
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

					default -> {
						new_storage.put(MaterialType.GEODE, storage.get(MaterialType.GEODE) + currRobots.get(typeOfBuilt));
					}
				}
			}
			
			// Wenn man ein Geode Roboter bauen kann, diesen aufjedenfall bauen
			if (possible.contains(RobotType.GEODE)) {
				// Den, der gebaut wird, um eins erhöhen
				Map<RobotType, Integer> tempRobots = new HashMap<>(currRobots);
				tempRobots.put(RobotType.GEODE, tempRobots.get(RobotType.GEODE) + 1);

				// Abziehen der verwendeten Ressourcen
				for (Robot robot : robots) {
					if (robot.type == RobotType.GEODE) {
						for (Material cost : robot.cost) {
							new_storage.put(cost.type, new_storage.get(cost.type) - cost.amount);
						}
					}
				}

				List<RobotType> newPossible = calcPossible(new_storage, robots);

				int temp = calc(new_storage, tempRobots, robots, newPossible, time);

				if (temp > currHighest) {
					currHighest = temp;
				}
				
			} else {
				for (RobotType type : possible) {

					if (type != RobotType.NONE) {
						// Den, der gebaut wird, um eins erhöhen
						Map<RobotType, Integer> tempRobots = new HashMap<>(currRobots);
						tempRobots.put(type, tempRobots.get(type) + 1);

						// Abziehen der verwendeten Ressourcen
						for (Robot robot : robots) {
							if (robot.type == type) {
								for (Material cost : robot.cost) {
									new_storage.put(cost.type, new_storage.get(cost.type) - cost.amount);
								}
							}
						}

						List<RobotType> newPossible = calcPossible(new_storage, robots);

						int temp = calc(new_storage, tempRobots, robots, newPossible, time);

						if (temp > currHighest) {
							currHighest = temp;
						}
					} else {
						// falls NONE (kein Roboter wird gebaut):
						List<RobotType> newPossible = calcPossible(new_storage, robots);

						// currRobots hat sich nicht verändert, kann weiter verwendet werden
						int temp = calc(new_storage, currRobots, robots, newPossible, time);

						if (temp > currHighest) {
							currHighest = temp;
						}
					}
				}
			}
		}
		
		if (storage.get(MaterialType.GEODE) > 4) {
			System.out.println("ss");
		}
		
		return storage.get(MaterialType.GEODE);
	}
	
	
	private static int part2(String path) {
		
		return 0;
	}
}
