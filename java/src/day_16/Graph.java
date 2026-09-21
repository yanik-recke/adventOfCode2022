package day_16;

import java.util.HashSet;
import java.util.Set;


/**
 * Repräsentiert einen Graphen.
 * 
 * (vgl. https://www.baeldung.com/java-dijkstra)
 * 
 * @author Yanik Recke
 */
public class Graph {
	/** Set mit den Knoten des Graphen */
    private Set<Valve> nodes = new HashSet<>();
    
    /**
     * Fügt einen Knoten dem Graphen hinzu.
     * 
     * @param nodeA - der Knoten der hinzugefügt werden soll
     */
    public void addNode(Valve nodeA) {
        nodes.add(nodeA);
    }
    
    /**
     * Getter für die Nodes, die in dem
     * Graphen enthalten sind.
     * 
     * @return - Set mit den Knoten dieses Graphen
     */
    public Set<Valve> getNodes(){
    	return this.nodes;
    }
}
