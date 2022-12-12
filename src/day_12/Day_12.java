package day_12;

import java.util.HashSet;
import java.util.LinkedList;
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
	
	
	private static int part1(String path) {
		int steps = 0;
		Graph graph = new Graph();
		
		int count = 0;
		
		char[][] field = helpers.HelperMethods.getInputAsTwoDimensionalCharArray(path);
		Node[][] fieldOfNodes = new Node[field.length][field[0].length];
		
		
		Position start = null;
		char currChar;
		
		for (int x = 0; x < field.length; x++) {
			for (int y = 0; y < field[x].length; y++) {
				if (field[x][y] == 'E') {
					fieldOfNodes[x][y] = new Node("E");
				} else {
					fieldOfNodes[x][y] = new Node(Integer.toString(count));
				}
				
				count++;
			}
		}

		count = 0;
		
		// Start Position bestimmen
		for (int x = 0; x < field.length; x++) {
			for (int y = 0; y < field[x].length; y++) {
				currChar = field[x][y];
				Character temp;
				int tempX = x;
				int tempY = y - 1;
				
				if (isInbounds(field, tempX, tempY)) {
					temp = field[tempX][tempY];
					if (temp.compareTo(currChar) >= -1) {
						fieldOfNodes[x][y].addDestination(fieldOfNodes[tempX][tempY], 1);
					}
				}
				
				tempX = x + 1;
				tempY = y;
				
				if (isInbounds(field, tempX, tempY)) {
					temp = field[tempX][tempY];
					if (temp.compareTo(currChar) >= -1) {
						fieldOfNodes[x][y].addDestination(fieldOfNodes[tempX][tempY], 1);
					}
				}
				
				tempX = x;
				tempY = y + 1;
				
				if (isInbounds(field, tempX, tempY)) {
					temp = field[tempX][tempY];
					if (temp.compareTo(currChar) >= -1) {
						fieldOfNodes[x][y].addDestination(fieldOfNodes[tempX][tempY], 1);
					}
				}
				
				tempX = x - 1;
				tempY = y;
				
				if (isInbounds(field, tempX, tempY)) {
					temp = field[tempX][tempY];
					if (temp.compareTo(currChar) >= -1) {
						fieldOfNodes[x][y].addDestination(fieldOfNodes[tempX][tempY], 1);
					}
				}
				
				// Start Position festlegen
				if (field[x][y] == 'S') {
					start = new Position(x,y);
				}
			}
		}
		
		
		Position currPos = start;
		boolean done = false;
		
		for (int x = 0; x < fieldOfNodes.length; x++) {
			for (int y = 0; y < fieldOfNodes[x].length; y++) {
				graph.addNode(fieldOfNodes[x][y]);
			}
		}
		
		Graph graph0 = calculateShortestPathFromSource(graph, fieldOfNodes[start.getX()][start.getY()]);
		
		for (Node node : graph0.getNodes()) {
			if (node.getName().equals("E")) {
				steps = node.getDistance();
				System.out.println(node.getShortestPath());
			}
		}
		
		return steps;
	}
	
	
	private static int part2(String path) {
		
		return 0;
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
	
	
	private static Node getLowestDistanceNode(Set < Node > unsettledNodes) {
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
