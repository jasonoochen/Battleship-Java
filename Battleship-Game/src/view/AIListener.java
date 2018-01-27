package view;

import model.Location;

public interface AIListener {
	void AIFire(Location chosenLocation);
	boolean checkCellForAI(Location loc);
}
