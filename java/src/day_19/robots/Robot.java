package day_19.robots;

import java.util.HashSet;
import java.util.Set;

import day_19.materials.Material;

public class Robot {

	public Set<Material> cost = new HashSet<>();
	
	public RobotType type;
	
	
	public Robot(RobotType type) {
		assert (type != RobotType.NONE);
		
		this.type = type;
	}
	
}
