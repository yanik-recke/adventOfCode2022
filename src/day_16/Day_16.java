package day_16;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Lösung für Tag 16.
 * 
 * @author Yanik Recke
 */
public class Day_16 {
	
	public static void main(String[] args) {
		String pathToInput = "src/day_16/input.txt";
		
		System.out.println("Part 1: " + part1(pathToInput));
		System.out.print("Part 2: ");
		System.out.println(part2(pathToInput));
	}
	
	
	/**
	 * Rekursiv jede mögliche Permutation ermitteln und dann
	 * immer die wählen, die am meisten pressure released.
	 * Inline Kommentare für bessere Erklärung. Arbeitet mit calc().
	 * 
	 * @param path - Pfad zum Puzzle Input
	 * @return - Pressure released
	 */
	private static int part1(String path) {
		Map<String, Valve> valves = getValves(path);

		Valve currValve = valves.get("AA");	
		
		// von jeder Valve zu jeder anderen Valve kürzesten Weg berechnen
		for (Valve valve : valves.values()) {
			Graph graph = new Graph();
			for (Valve valve0 : valves.values()) {
				valve0.shortestPathList = new LinkedList<>();
				valve0.distance = Integer.MAX_VALUE;
				graph.addNode(valve0);
			}
			
			graph = calculateShortestPathFromSource(graph, valve);
		
			for (Valve tempValve : valves.values()) {
				valve.shortestPath.put(tempValve, tempValve.shortestPathList.size());
			}
		}
		
		List<Valve> possible = new ArrayList<>();
		
		// alle Valves mit flow rate > 0 zu den noch möglichen Valves hinzufügen
		for (Valve element : valves.values()) {
			if (element.flowRate > 0) {
				possible.add(element);
			}
		}
	
		
		int temp = 0;
		int currHighest = 0;
		
		// rekursive Berechnung des Pfads, durch den am meisten Pressure released wird
		for (Valve element : possible) {
			List<Valve> newPossible = new ArrayList<>(possible);
			newPossible.remove(element);
			
			temp = calc(element, currValve.shortestPath.get(element), newPossible, 0);
			
			// Wenn ein neues Maximum gefunden, dann neu setzen
			if (currHighest < temp) {
				currHighest = temp;
			}
		}
		
		return currHighest;
	}
	
	
	/**
	 * Rekursive Berechnung eines Pfads.
	 * 
	 * @param valve - aktuelle Valve
	 * @param distance - Entfernung, die zur aktuellen Valve zurückgelegt werden musste
	 * @param possible - die noch möglichen Valves
	 * @param time - aktuell vergangene Zeit
	 * @return - pressure die durch die eigene Valve releast wird + die pressure durch die darauffolgenden (Rekursion)
	 */
	private static int calc(Valve valve, int distance, List<Valve> possible, int time) {
		// Zeit um Distanz und Öffnen (1) erhöhen
		time += distance + 1;
		// Eigene Pressure auf 0 setzen
		int press = 0;
		int currHighest = 0;
		
		// Nur, wenn überhaupt noch Zeit ist
		if (time < 30) {
			// Berechnung der releasten pressure
			press = (30 - time) * valve.flowRate;
			
			int temp = 0;
		
			// Durchgehen jeder noch möglichen Valve
			for (Valve element : possible) {
				List<Valve> newPossible = new ArrayList<>(possible);
				newPossible.remove(element);
			
				// Berechnung der besten Kombination
				temp = calc(element, valve.shortestPath.get(element), newPossible, time);
			
				if (currHighest < temp) {
					currHighest = temp;
				}
			}
		}
		
		// Eigene releaste pressure + die höchst berechnete zurückgeben
		return currHighest + press;
	}
	
	
	/**
	 * Alle möglichen Pfade berechnen. Für jeden Pfad
	 * jede Komplementärmenge prüfen und die größte Summe, die
	 * sich aus zwei Pfaden ergibt, ist die maximal releasbare pressure.
	 * 
	 * @param path - Pfad zum Puzzle Input
	 * @return - maximal releasbare pressure
	 */
	private static int part2(String path) {
		Map<String, Valve> valves = getValves(path);
		
		Valve currValve = valves.get("AA");	
		
		// Kürzeste Distanz von jedem Valve zu jedem Valve berechnen
		for (Valve valve : valves.values()) {
			Graph graph = new Graph();
			for (Valve valve0 : valves.values()) {
				valve0.shortestPathList = new LinkedList<>();
				valve0.distance = Integer.MAX_VALUE;
				graph.addNode(valve0);
			}
			
			graph = calculateShortestPathFromSource(graph, valve);
		
			for (Valve tempValve : valves.values()) {
				valve.shortestPath.put(tempValve, tempValve.shortestPathList.size());
			}
		}
		
		List<Valve> possible = new ArrayList<>();
		
		for (Valve element : valves.values()) {
			if (element.flowRate > 0) {
				possible.add(element);
			}
		}
	
		
		List<List<Pair<Valve, Integer>>> paths = new ArrayList<>();
		
		List<Pair<Valve, Integer>> path0 = new ArrayList<>();
		
		// Berechnung jedes Pfades
		for (Valve element : possible) {
			List<Valve> newPossible = new ArrayList<>(possible);
			newPossible.remove(element);
			
			calc_2(element, currValve.shortestPath.get(element), newPossible, 0, path0, paths);
		}
		
		int currMax = 0;

		
		// Vergleichen jedes Pfades mit jedem anderen Pfad
		for (List<Pair<Valve, Integer>> combi : paths) {
			// Sinnvoller Ausschluss
			if (combi.size() > 4) {
				for (List<Pair<Valve, Integer>> combi0 : paths) {
					int sndTempSum = 0;
				
					boolean contains = false;
					Iterator<Pair<Valve, Integer>> it = combi0.iterator();
				
					while (it.hasNext() && !contains) {
						Pair<Valve, Integer> nxtPair = it.next();
					
						sndTempSum += nxtPair.r();
						if (combi.contains(nxtPair)) {
							contains = true;
						}
					}
				
					if (!contains) {
						int tempSum = 0;
						for (Pair<Valve, Integer> pair : combi) {
							tempSum += pair.r();
						}
					
						if (tempSum + sndTempSum > currMax) {
							currMax = tempSum + sndTempSum;
						}
					}
				}
			}
		}
		
		return currMax;
	}
	
	
	/**
	 * Rekursive Berechnung Pfade. Hinzufügen jedes Pfades in Liste von Listen
	 * 
	 * @param valve - aktuelle Valve
	 * @param distance - Entfernung die zur aktuellen Valve zurückgelegt werden musste
	 * @param possible - noch besuchbare Valves
	 * @param time - bis jetzt vergangene Zeit
	 * @param list - Liste mit Valves des aktuellen Pfads
	 * @param paths - Liste aller Pfade
	 * @return - maximal releasbare Pressure durch einen einzelnen Pfad
	 */
	private static int calc_2(Valve valve, int distance, List<Valve> possible, int time, List<Pair<Valve, Integer>> list, List<List<Pair<Valve, Integer>>> paths) {
		time += distance + 1;
		int press = 0;
		int currHighest = 0;
		List<Pair<Valve, Integer>> newList;

		if (time < 26) {
			press = (26 - time) * valve.flowRate;
			
			if (possible.size() == 0) {
				list.add(new Pair<Valve, Integer>(valve, press));
				paths.add(list);
			}
			
			int temp = 0;

			for (Valve element : possible) {
				List<Valve> newPossible = new ArrayList<>(possible);
				newPossible.remove(element);
				newList = new ArrayList<>(list);
				newList.add(new Pair<Valve, Integer>(valve, press));
				
				paths.add(newList);
				
				temp = calc_2(element, valve.shortestPath.get(element), newPossible, time, newList, paths);
			
				if (currHighest < temp) {
					currHighest = temp;
				}
			}
		}
		
		return currHighest + press;
	}
	
	
	
	
	/**
	 * Parsen des Inputs
	 * 
	 * @param path - Pfad zum Puzzle Input
	 * @return - Map mit ID auf Valve
	 */
	private static Map<String, Valve> getValves(String path){
		/*
		 * Wäre auch ohne Map machbar, habe es aber 
		 * so gelassen, da dies mein erster Ansatz war und
		 * es auch ganz angenehem ist, per ID / Name auf die
		 * Valves zugreifen zu können.
		 */
		List<String> input = helpers.HelperMethods.getInputAsListOfString(path);
		Map<String, Valve> valves = new LinkedHashMap<>();
		
		int counter = 0;
		for (String line : input) {
			String[] temp = line.split(" ");
			
			valves.put(temp[1], new Valve(temp[1], Integer.parseInt(temp[4].substring(temp[4].indexOf('=') + 1, temp[4].indexOf(';'))), counter));
			counter++;
		}
		
		for (String line : input) {
			String[] temp = line.split(" ");
			
			for (int i = 9; i < temp.length; i++) {
				if (temp[i].length() == 2) {
					valves.get(temp[1]).leadsTo.put(valves.get(temp[i]), 1);
				} else {
					valves.get(temp[1]).leadsTo.put(valves.get(temp[i].substring(0, temp[i].length() - 1)), 1);
				}
			}
		}
		
		return valves;
	}
	
	
    
	/**
	 * Dijkstra Algorithmus. 
	 * 
	 * Quelle: https://www.baeldung.com/java-dijkstra
	 * 
	 * @param graph - Der zu verwendende Graph
	 * @param source - Anfangsnode
	 * @return - Graph, die Nodes haben die entsprechende Entfernung zum Startpunkt
	 */
	public static Graph calculateShortestPathFromSource(Graph graph, Valve source) {
	    source.setDistance(0);

	    Set<Valve> settledNodes = new HashSet<>();
	    Set<Valve> unsettledNodes = new HashSet<>();

	    unsettledNodes.add(source);

	    while (unsettledNodes.size() != 0) {
	        Valve currentNode = getLowestDistanceNode(unsettledNodes);
	        unsettledNodes.remove(currentNode);
	        
	        for (Entry <Valve, Integer> adjacencyPair: currentNode.leadsTo.entrySet()) {
	        	
	        	Valve adjacentNode = adjacencyPair.getKey();
	            Integer edgeWeight = adjacencyPair.getValue();
	            
	            if (!settledNodes.contains(adjacentNode)) {
	                calculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
	                unsettledNodes.add(adjacentNode);
	            }
	        }
	        settledNodes.add(currentNode);
	    }
	    
	    return graph;
	}
	
	
	/**
	 * Holen der Node mit der kleinsten Entfernung aus
	 * einem Set.
	 * 
	 * Quelle: https://www.baeldung.com/java-dijkstra
	 * 
	 * @param unsettledNodes - Set mit Nodes
	 * @return - Node mit der kleinsten Entfernung aller Nodes aus dem Set
	 */
	private static Valve getLowestDistanceNode(Set<Valve> unsettledNodes) {
		Valve lowestDistanceNode = null;
	    int lowestDistance = Integer.MAX_VALUE;
	    
	    for (Valve node: unsettledNodes) {
	        int nodeDistance = node.distance;
	        if (nodeDistance < lowestDistance) {
	            lowestDistance = nodeDistance;
	            lowestDistanceNode = node;
	        }
	    }
	    
	    return lowestDistanceNode;
	}
	
	
	/**
	 * Vergleicht die wirkliche Distanz mit der neu
	 * berechneten.
	 * 
	 * Quelle: https://www.baeldung.com/java-dijkstra
	 * 
	 * @param evaluationNode - aktuelle Node
	 * @param edgeWeigh - Distanz
	 * @param sourceNode - Startnode
	 */
	private static void calculateMinimumDistance(Valve evaluationNode, Integer edgeWeigh, Valve sourceNode) {
		Integer sourceDistance = sourceNode.distance;
		
		if (sourceDistance + edgeWeigh < evaluationNode.distance) {
			evaluationNode.setDistance(sourceDistance + edgeWeigh);
			LinkedList<Valve> shortestPath = new LinkedList<>(sourceNode.shortestPathList);
			shortestPath.add(sourceNode);
			evaluationNode.shortestPathList = shortestPath;
		}
	}
	
	
}
