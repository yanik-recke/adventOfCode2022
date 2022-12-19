package day_16;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;



public class Valve {
	
	public final String id;
	
	public final int index;
	
	public final int flowRate;
	
	public final Map<Valve, Integer> leadsTo;
	
	public final Map<Valve, Integer> shortestPath;
	
	public int distance = Integer.MAX_VALUE;
	
    /** Liste mit Knoten, die den kürzesten Pfad zu diesem Knoten bilden */
    public List<Valve> shortestPathList = new LinkedList<>();
	
	public Valve(String id, int flowRate, int index) {
		this.id = id;
		this.flowRate = flowRate;
		this.leadsTo = new HashMap<>();
		this.shortestPath = new HashMap<>();
		this.index = index;
	}
	
	
	public void addToShortstPath(Valve valve, int distance) {
		this.shortestPath.put(valve, distance);
	}
	
	
	public void setDistance(int distance) {
		this.distance = distance;
	}
	
	
	@Override
	public String toString() {
		String temp = "";
		for (Valve valve : this.leadsTo.keySet()) {
			temp = temp.concat(valve.id + ",");
		}
		
		temp = temp.substring(0, temp.length());
		
		return this.id;
	}
	
	@Override
	public int hashCode() {
		return this.index;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof Valve && ((Valve) obj).id == this.id;
	}
	
}
