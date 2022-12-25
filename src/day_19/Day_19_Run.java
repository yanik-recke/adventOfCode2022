package day_19;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import day_19.materials.MaterialType;
import day_19.robots.Robot;
import day_19.robots.RobotType;

public class Day_19_Run implements Runnable{
	public long result;
	private Map<MaterialType, Integer> storage;
	private Map<RobotType, Integer> currRobots;
	private Robot[] robots;
	private Set<RobotType> possible;
	
	public Day_19_Run(Map<MaterialType, Integer> storage,
			Map<RobotType, Integer> currRobots ,
			Robot[] robots,
			Set<RobotType> possible) {
		this.storage = new HashMap<MaterialType, Integer>(storage);
		this.currRobots = new HashMap<RobotType, Integer>(currRobots);
		this.robots = robots;
		this.possible = new HashSet<RobotType>(possible);
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		this.result = Day_19.calc(this.storage, this.currRobots, this.robots, this.possible, 0);
	}

	
}
