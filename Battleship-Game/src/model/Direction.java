package model;

import java.util.ArrayList;
import java.util.Random;

public enum Direction {
	UP,DOWN,RIGHT,LEFT;
	
	public static Direction randomDirection(){
		ArrayList<Direction> directions=new ArrayList<Direction>(4);
		directions.add(UP);
		directions.add(DOWN);
		directions.add(RIGHT);
		directions.add(LEFT);
		Random random = new Random();
		int index = random.nextInt(3);
		return directions.get(index);
	}
}
