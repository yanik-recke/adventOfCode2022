package day_12;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Klasse die einen Knoten eines Graphen
 * repräsentiert.
 * 
 * (vgl. https://www.baeldung.com/java-dijkstra)
 *
 * @author Yanik Recke
 */
public class Node {
	/** Position des Knoten im Array */
    private Position position;
    /** Name des Knoten */
    private String name;
    /** Liste mit Knoten, die den kürzesten Pfad zu diesem Knoten bilden */
    private List<Node> shortestPath = new LinkedList<>();
    /** Distanz */
    private Integer distance = Integer.MAX_VALUE;
    /** Map mit Knoten, zu denen dieser Knoten eine Kante hat */
    Map<Node, Integer> adjacentNodes = new HashMap<>();
    
    
    /**
     * Konstruktor, setzt Name und Position.
     * 
     * @param name - Name des Knoten
     * @param position - Position des Knoten
     */
    public Node(String name, Position position) {
        this.name = name;
        this.position = position;
    }
    
    
    /**
     * Getter für den Namen.
     * 
     * @return - der Name
     */
	public String getName() {
		return name;
	}

	/**
	 * Getter für den kürzesten Pfad.
	 * 
	 * @return - Liste mit Knoten, die den kürzesten Pfad bilden
	 */
	public List<Node> getShortestPath() {
		return shortestPath;
	}
	
	/**
	 * Setter, um den kürzesten Pfad zu setzen.
	 * 
	 * @param shortestPath - Liste mit Knoten, die den kürzesten Pfad abbilden
	 */
	public void setShortestPath(List<Node> shortestPath) {
		this.shortestPath = shortestPath;
	}

	/**
	 * Getter für die Distanz.
	 * 
	 * @return - die Distanz
	 */
	public Integer getDistance() {
		return distance;
	}

	/**
	 * Setter für die Distanz.
	 * 
	 * @param distance - die Distanz die gesetzt werden soll
	 */
	public void setDistance(Integer distance) {
		this.distance = distance;
	}
	
	/**
	 * Getter für die verbundenen Knoten.
	 * 
	 * @return - Map mit den Knoten, zu denen der eigene Knoten eine Verbindung hat
	 */
	public Map<Node, Integer> getAdjacentNodes() {
		return this.adjacentNodes;
	}
	
	
	/**
	 * Fügt einen Knoten zu den verbundenen Knoten dieses Knotens hinzu.
	 * Erstellt also eine Kante zu einem anderen Knoten.
	 * 
	 * @param destination - Zielknoten
	 * @param distance - Entfernung
	 */
    public void addDestination(Node destination, int distance) {
        adjacentNodes.put(destination, distance);
    }
	
	@Override
	public String toString() {
		return this.position.toString();
	}
    
    
}
