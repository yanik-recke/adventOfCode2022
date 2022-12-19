package day_16;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
		
		System.out.println(part1(pathToInput) + " - " + part2(pathToInput));
	}
	
	
	private static int part1(String path) {
		Map<String, Valve> valves = getValves(path);
		
		boolean done = false;
		Valve currValve = null;
		Iterator<Valve> iterator = valves.values().iterator();
		
		// find first
		while (iterator.hasNext() && !done) {
			Valve temp = iterator.next();
			if (temp.id.equals("AA")) {
				done = true;
				currValve = temp;
			}
		}
		
		
		// Setting shortest path for every Valve 
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
	
		
		int temp = 0;
		int currHighest = 0;
		
		for (Valve element : possible) {
			List<Valve> newPossible = new ArrayList<>(possible);
			newPossible.remove(element);
			
			temp = calc(element, currValve.shortestPath.get(element), newPossible, 0);
			
			if (currHighest < temp) {
				currHighest = temp;
			}
		}
		
		return currHighest;
	}
	
	
	private static int calc(Valve valve, int distance, List<Valve> possible, int time) {
		time += distance + 1;
		int press = 0;
		int currHighest = 0;
		
		if (time < 30) {
			press = (30 - time) * valve.flowRate;
			
			int temp = 0;
		
			for (Valve element : possible) {
				List<Valve> newPossible = new ArrayList<>(possible);
				newPossible.remove(element);
			
				temp = calc(element, valve.shortestPath.get(element), newPossible, time);
			
				if (currHighest < temp) {
					currHighest = temp;
				}
			}
		}
		
		return currHighest + press;
	}
	
	
	private static int part2(String path) {
		Map<String, Valve> valves = getValves(path);
		
		boolean done = false;
		Valve currValve = null;
		Iterator<Valve> iterator = valves.values().iterator();
		
		// find first
		while (iterator.hasNext() && !done) {
			Valve temp = iterator.next();
			if (temp.id.equals("AA")) {
				done = true;
				currValve = temp;
			}
		}
		
		
		// Setting shortest path for every Valve 
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
	
		
		int temp = 0;
		int currHighest = 0;
		List<Valve> path0 = new ArrayList<>();
		
		for (Valve element : possible) {
			List<Valve> newPossible = new ArrayList<>(possible);
			newPossible.remove(element);
			
			temp = calc_2(element, currValve.shortestPath.get(element), newPossible, 0, path0);
			
			if (currHighest < temp) {
				currHighest = temp;
			}
			
		}
		
		System.out.println(paths);
		
		
		
		return currHighest;
	}
	
	// TODO
	public static List<List<Valve>> paths = new ArrayList<>();
	
	private static int calc_2(Valve valve, int distance, List<Valve> possible, int time, List<Valve> list) {
		time += distance + 1;
		int press = 0;
		int currHighest = 0;
		List<Valve> newList;

		if (time < 30) {
			press = (30 - time) * valve.flowRate;
			
			if (possible.size() == 0) {
				list.add(valve);
				paths.add(list);
			}
			
			int temp = 0;

			for (Valve element : possible) {
				List<Valve> newPossible = new ArrayList<>(possible);
				newPossible.remove(element);
				newList = new ArrayList<>(list);
				newList.add(valve);
				
				paths.add(newList);
				
				temp = calc_2(element, valve.shortestPath.get(element), newPossible, time, newList);
			
				if (currHighest < temp) {
					//list.add(element);
					currHighest = temp;
				}
			}
		}
		
		return currHighest + press;
	}
	
	
	

	
	private static Map<String, Valve> getValves(String path){
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
