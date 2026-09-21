package day_12;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Lösung für Tag 12.
 * 
 * @author Yanik Recke
 */
public class Day_12 {

	
	public static void main(String[] args) {
		String pathToInput = "src/day_12/input.txt";
		
		System.out.println(part1(pathToInput) + " - " + part2(pathToInput));
	}
	
	/**
	 * Einlesen des Inputs in eine Graph Struktur. Jedes
	 * Zeichen ist ein Knoten. Ein Knoten ist dann mit einem
	 * anderen verbunden, wenn der Wert des Zeichens kleiner ist, oder 
	 * nur eins höher ist. Dann für den Graphen Dijkstra Algorithmus
	 * anwenden, von Startposition 'S' aus.
	 * 
	 * @param path - Pfad zum Puzzle Input
	 * @return - Länge des kürzesten Wegs von 'S' zu 'E'
	 */
	private static int part1(String path) {
		int steps = 0;
		Graph graph = new Graph();
		
		char[][] field = helpers.HelperMethods.getInputAsTwoDimensionalCharArray(path);
		Node[][] fieldOfNodes = new Node[field.length][field[0].length];
		
		
		Position start = null;
		Character currChar;
		
		for (int x = 0; x < field.length; x++) {
			for (int y = 0; y < field[x].length; y++) {
				if (field[x][y] == 'E') {
					fieldOfNodes[x][y] = new Node("E", new Position(x,y));
				} else if (field[x][y] == 'S'){
					fieldOfNodes[x][y] = new Node("S", new Position(x,y));
					start = new Position(x,y);
				} else {
					fieldOfNodes[x][y] = new Node(Integer.toString(x) + " - " + Integer.toString(y), new Position(x,y));
				}
				
			}
		}

		for (int x = 0; x < field.length; x++) {
			for (int y = 0; y < field[x].length; y++) {
				currChar = field[x][y];
				
				Character temp;
				int tempX = x;
				int tempY = y - 1;
				
				if (isInbounds(field, tempX, tempY)) {
					temp = field[tempX][tempY];
					if (((temp != 'S' && temp != 'E') && currChar.compareTo(temp) >= -1) || currChar == 'z' || currChar == 'S') {
						fieldOfNodes[x][y].addDestination(fieldOfNodes[tempX][tempY], 1);
					}
				}
				
				tempX = x + 1;
				tempY = y;
				
				if (isInbounds(field, tempX, tempY)) {
					temp = field[tempX][tempY];
					if (((temp != 'S' && temp != 'E') && currChar.compareTo(temp) >= -1) || currChar == 'z' || currChar == 'S') {
						fieldOfNodes[x][y].addDestination(fieldOfNodes[tempX][tempY], 1);
					}
				}
				
				tempX = x;
				tempY = y + 1;
				
				if (isInbounds(field, tempX, tempY)) {
					temp = field[tempX][tempY];
					if (((temp != 'S' && temp != 'E') && currChar.compareTo(temp) >= -1) || currChar == 'z' || currChar == 'S') {
						fieldOfNodes[x][y].addDestination(fieldOfNodes[tempX][tempY], 1);
					}
				}
				
				tempX = x - 1;
				tempY = y;
				
				if (isInbounds(field, tempX, tempY)) {
					temp = field[tempX][tempY];
					if (((temp != 'S' && temp != 'E') && currChar.compareTo(temp) >= -1) || currChar == 'z' || currChar == 'S') {
						fieldOfNodes[x][y].addDestination(fieldOfNodes[tempX][tempY], 1);
					}
				}
				
			}
		}
		
		for (int x = 0; x < fieldOfNodes.length; x++) {
			for (int y = 0; y < fieldOfNodes[x].length; y++) {
				graph.addNode(fieldOfNodes[x][y]);
			}
		}
		
		Graph graph0 = calculateShortestPathFromSource(graph, fieldOfNodes[start.getX()][start.getY()]);
		
		for (Node node : graph0.getNodes()) {
			if (node.getName().equals("E")) {
				steps = node.getDistance();
			}
		}
		
		return steps;
	}
	
	
	/**
	 * Beim Einlesen des Inputs alle 'a' Positionen
	 * in eine Liste hinzufügen. Dann Dijkstra für alle 
	 * möglichen Startpositionen durchführen und den Wert wiedergeben,
	 * der am kleinsten ist.
	 * 
	 * @param path - Pfad zum Puzzle Input
	 * @return - Länge des kürzesten Weg von einem 'a' zu 'E'
	 */
	private static int part2(String path) {
		Graph graph = new Graph();
		
		char[][] field = helpers.HelperMethods.getInputAsTwoDimensionalCharArray(path);
		Node[][] fieldOfNodes = new Node[field.length][field[0].length];
		
		List<Position> listOfStarts = new ArrayList<>();
		
		Character currChar;
		
		for (int x = 0; x < field.length; x++) {
			for (int y = 0; y < field[x].length; y++) {
				if (field[x][y] == 'E') {
					fieldOfNodes[x][y] = new Node("E", new Position(x,y));
				} else if (field[x][y] == 'S'){
					fieldOfNodes[x][y] = new Node("S", new Position(x,y));
				} else {
					fieldOfNodes[x][y] = new Node(Integer.toString(x) + " - " + Integer.toString(y), new Position(x,y));
				}
				
				if (field[x][y] == 'a') {
					listOfStarts.add(new Position(x,y));
				}
				
			}
		}

		
		for (int x = 0; x < field.length; x++) {
			for (int y = 0; y < field[x].length; y++) {
				currChar = field[x][y];
				
				Character temp;
				int tempX = x;
				int tempY = y - 1;
				
				if (isInbounds(field, tempX, tempY)) {
					temp = field[tempX][tempY];
					if (((temp != 'S' && temp != 'E') && currChar.compareTo(temp) >= -1) || currChar == 'z' || currChar == 'S') {
						fieldOfNodes[x][y].addDestination(fieldOfNodes[tempX][tempY], 1);
					}
				}
				
				tempX = x + 1;
				tempY = y;
				
				if (isInbounds(field, tempX, tempY)) {
					temp = field[tempX][tempY];
					if (((temp != 'S' && temp != 'E') && currChar.compareTo(temp) >= -1) || currChar == 'z' || currChar == 'S') {
						fieldOfNodes[x][y].addDestination(fieldOfNodes[tempX][tempY], 1);
					}
				}
				
				tempX = x;
				tempY = y + 1;
				
				if (isInbounds(field, tempX, tempY)) {
					temp = field[tempX][tempY];
					if (((temp != 'S' && temp != 'E') && currChar.compareTo(temp) >= -1) || currChar == 'z' || currChar == 'S') {
						fieldOfNodes[x][y].addDestination(fieldOfNodes[tempX][tempY], 1);
					}
				}
				
				tempX = x - 1;
				tempY = y;
				
				if (isInbounds(field, tempX, tempY)) {
					temp = field[tempX][tempY];
					if (((temp != 'S' && temp != 'E') && currChar.compareTo(temp) >= -1) || currChar == 'z' || currChar == 'S') {
						fieldOfNodes[x][y].addDestination(fieldOfNodes[tempX][tempY], 1);
					}
				}
				
			}
		}
		
		// alle Nodes dem Graphen hinzufügen
		for (int x = 0; x < fieldOfNodes.length; x++) {
			for (int y = 0; y < fieldOfNodes[x].length; y++) {
				graph.addNode(fieldOfNodes[x][y]);
			}
		}
		
		
		int steps = Integer.MAX_VALUE;
		for (Position position : listOfStarts) {
			Graph graph0 = calculateShortestPathFromSource(graph, fieldOfNodes[position.getX()][position.getY()]);
			
			for (Node node : graph0.getNodes()) {
				if (node.getName().equals("E")) {
					if (steps > node.getDistance()) {
						steps = node.getDistance();
					}
				}
			}
		}
		
		return steps;
	}
	
	
	/**
	 * Prüft ob eine Position in den Grenzen eines 
	 * Arrays liegt.
	 * 
	 * @param arr - der Array
	 * @param x - x-Koordinate
	 * @param y - y-Koordinate
	 * @return - true, wenn Koordinaten innerhalb liegen, false wenn nicht
	 */
	public static boolean isInbounds(char[][] arr, int x, int y) {
		return x >= 0 && y >= 0 && x < arr.length && y < arr[x].length;
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
	public static Graph calculateShortestPathFromSource(Graph graph, Node source) {
	    source.setDistance(0);

	    Set<Node> settledNodes = new HashSet<>();
	    Set<Node> unsettledNodes = new HashSet<>();

	    unsettledNodes.add(source);

	    while (unsettledNodes.size() != 0) {
	        Node currentNode = getLowestDistanceNode(unsettledNodes);
	        unsettledNodes.remove(currentNode);
	        for (Entry < Node, Integer> adjacencyPair: 
	          currentNode.getAdjacentNodes().entrySet()) {
	            Node adjacentNode = adjacencyPair.getKey();
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
	private static Node getLowestDistanceNode(Set<Node> unsettledNodes) {
	    Node lowestDistanceNode = null;
	    int lowestDistance = Integer.MAX_VALUE;
	    
	    for (Node node: unsettledNodes) {
	        int nodeDistance = node.getDistance();
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
	private static void calculateMinimumDistance(Node evaluationNode, Integer edgeWeigh, Node sourceNode) {
		Integer sourceDistance = sourceNode.getDistance();
		
		if (sourceDistance + edgeWeigh < evaluationNode.getDistance()) {
			evaluationNode.setDistance(sourceDistance + edgeWeigh);
			LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
			shortestPath.add(sourceNode);
			evaluationNode.setShortestPath(shortestPath);
		}
	}
	
}
