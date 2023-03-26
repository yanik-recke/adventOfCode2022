package day_19_improved;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import day_19.robots.RobotType;


/**
 * Lösung für Tag 19 verbessert.
 * 
 * @author Yanik Recke
 */
public class Day_19 {
	
	/** Costs of the robots for the current blueprint */
	private static Map<Material, Map<Material, Integer>> costs;
	/** Robots */
	private static Map<Material, Integer> robotCount;
	/** Materials without Geode */
	private static final Set<Material> MATS_NO_GEODE = new HashSet<>(Arrays.asList(Material.ORE, Material.CLAY, Material.OBSIDIAN));
	
	
	public static void main(String[] args) {
		System.out.println(part1());
	}
	
	
	/**
	 * Parsing and preparing for the recursive
	 * calls.
	 * 
	 * (also includes part 2)
	 * 
	 * @return - result
	 */
	private static long part1() {
		List<String> inputLines = helpers.HelperMethods.getInputAsListOfString("src/day_19_improved/input.txt");
		
		int id = 1;
		//long result = 0;
		long result = 1;
		
		for (String line : inputLines) {
			System.out.println(id);
			// 6, 12, 18, 21, 27, 30
			String temp[] = line.split(" ");
		
			Map<Material, Integer> storage = new HashMap<>();
			robotCount = new HashMap<>();

			for (Material mat : Material.values()) {
				storage.put(mat, 0);
				robotCount.put(mat, 0);
			}

			costs = new HashMap<>();

			// Kosten für Ore-Roboter
			costs.put(Material.ORE, new HashMap<>());
			costs.get(Material.ORE).put(Material.ORE, Integer.parseInt(temp[6]));

			// Kosten für Clay-Roboter
			costs.put(Material.CLAY, new HashMap<>());
			costs.get(Material.CLAY).put(Material.ORE, Integer.parseInt(temp[12]));

			// Kosten für Obsidian-Roboter
			costs.put(Material.OBSIDIAN, new HashMap<>());
			costs.get(Material.OBSIDIAN).put(Material.ORE, Integer.parseInt(temp[18]));
			costs.get(Material.OBSIDIAN).put(Material.CLAY, Integer.parseInt(temp[21]));

			// Kosten für Geode-Roboter
			costs.put(Material.GEODE, new HashMap<>());
			costs.get(Material.GEODE).put(Material.ORE, Integer.parseInt(temp[27]));
			costs.get(Material.GEODE).put(Material.OBSIDIAN, Integer.parseInt(temp[30]));

			// Ersten Roboter hinzufügen
			robotCount.put(Material.ORE, 1);

			//result += help(0, storage, robotCount, 0, null, Integer.MAX_VALUE, new HashSet<>(), new HashSet<>()) * id;
			
			result *= help(0, storage, robotCount, 0, null, Integer.MAX_VALUE, new HashSet<>(), new HashSet<>());
			id++;
		}
		
		
		//7936 too low, 19440 too low as well 19980
		return result;
	}
	
	
	//private static int MAX_TIME = 24;
	private static int MAX_TIME = 32;
	
	
	/*
	 * Recursive method that explores every possible combination 
	 * of robots. 
	 * 
	 * -> Stop exploring when in a past branch a geode roboter has been produced earlier
	 * -> Stop exploring when it is not possible to exceed the number of geodes of a past branch
	 * -> Stop producing kinds of robots where you already have robots to produce enough of
	 * 	  their cost every minute because more work without gain
	 * -> If no robot was built in a minute any robot that could not have been built must not be
	 * 	  built in the next minute
	 */
	private static long help(int time, Map<Material, Integer> storage, Map<Material, Integer> robotCount, long currMax, Material toBuild, int earliest, Set<Material> doNotBuild, Set<Material> neverBuild) {
		time++;
		
		if (time <= MAX_TIME) {
			Map<Material, Integer> tempRobCount = new HashMap<>(robotCount);
			Map<Material, Integer> tempStorage = new HashMap<>(storage);
			Set<Material> tempNeverBuild = new HashSet<>(neverBuild);
			
			// Sammeln
			for (Material mat : robotCount.keySet()) {
				tempStorage.put(mat, storage.get(mat) + robotCount.get(mat));
			}

			// Bauen
			if (toBuild != null) {
				doNotBuild.clear();
				
				for (Material mat : costs.get(toBuild).keySet()) {
					// Abziehen der verbrauchten Ressourcen
					tempStorage.put(mat, tempStorage.get(mat) - costs.get(toBuild).get(mat));
				}

				tempRobCount.put(toBuild, robotCount.get(toBuild) + 1);
			} 
			
			if (costs.get(Material.GEODE).get(Material.OBSIDIAN) <= tempStorage.get(Material.OBSIDIAN)
					&& costs.get(Material.GEODE).get(Material.ORE) <= tempStorage.get(Material.ORE)) {

				if (time < earliest) {
					earliest = time;
				}

				long temp = help(time, tempStorage, tempRobCount, currMax, Material.GEODE, earliest, new HashSet<>(), new HashSet<>());

				if (temp > currMax) {
					currMax = temp;
				}
			} else {
				int sum = (((MAX_TIME - (time - 1))  + 1) * (MAX_TIME - (time - 1))) / 2 - (MAX_TIME - (time - 1)) + (MAX_TIME - (time - 1)) * tempRobCount.get(Material.GEODE);
				
				if (sum + tempStorage.get(Material.GEODE) > currMax) {
					for (Material mat : MATS_NO_GEODE) {
						// only build if could not be built last round
						if (!neverBuild.contains(mat) && !doNotBuild.contains(mat)) {
							boolean futile = true;
							boolean buildable = true;
							int counter = 0;
							Iterator<Material> it = costs.get(mat).keySet().iterator();

							while (it.hasNext() && buildable) {
								Material cost = it.next();
								if (costs.get(mat).get(cost) > tempStorage.get(cost)) {
									buildable = false;
								}

								if (futile && (costs.get(mat).get(cost) + 1 < tempRobCount.get(cost))) {
									futile = true;
									counter++;
								} else {
									futile = false;
								}
							}

							if (futile) {
								if (mat == Material.OBSIDIAN || mat == Material.GEODE) {
									if (counter == 2) {
										tempNeverBuild.add(mat);
									}
								} else {
									tempNeverBuild.add(mat);
								}
							}

							if (buildable) {
								doNotBuild.add(mat);

								long temp = help(time, tempStorage, tempRobCount, currMax, mat, earliest,
										new HashSet<>(), new HashSet<>(tempNeverBuild));

								if (temp > currMax) {
									currMax = temp;
								}
							}
						}
					}

					long temp = help(time, tempStorage, tempRobCount, currMax, null, earliest, doNotBuild,
							new HashSet<>(tempNeverBuild));

					if (temp > currMax) {
						currMax = temp;
					}
				}
			} 
		}
		
		return currMax > storage.get(Material.GEODE) ? currMax : storage.get(Material.GEODE);
	}
	

}
