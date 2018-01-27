package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import view.AIListener;
import view.Launcher;

public class AI {
	private Location chosenLocation;
	public ArrayList<Integer> unSelectedLocations, focusLocations;
	public int focusIndex;
	public Direction checkDirection;
	public boolean smart, reconsider;
	private int row, col, thisRow, thisCol;
	private List<AIListener> listeners = new ArrayList<>();

	public static AI createAI(){
		return new AI();
	}
	private AI() {
		setBasicVariable();
		unSelectedLocations = new ArrayList<Integer>();
		setList();
		setDirection();
		focusLocations = new ArrayList<Integer>();
	}

	public void setBasicVariable() {
		smart = false;
		reconsider = true;
		chosenLocation = null;
		focusIndex = 0;
	}
	
	public Position randomPosition(){
		int index=getRandomIndex(2);
		if (index==0)
			return Position.HORIZONTAL;
		else
			return Position.VERTICAL;
	}
	
	public Location randomLocation(){
		int index = getRandomIndex(unSelectedLocations.size());
		int randomNum = unSelectedLocations.get(index);
		return changeNumberToLocation(randomNum);
	}

	private void setList() {
		for (int i = 0; i < 100; i++) {
			unSelectedLocations.add(i);
		}
	}

	private void setDirection() {
		checkDirection = Direction.randomDirection();
	}

	public void fire() {
		if (smart == false || focusLocations.isEmpty()) {
			randomChoose();
		} else {
			wiselyChoose();
		}
		for(AIListener listener:listeners){
			listener.AIFire(chosenLocation) ;
		}
	}

	private int getRandomIndex(int size) {
		Random random = new Random();
		return random.nextInt(size);
	}

	private void randomChoose() {
		int index = getRandomIndex(unSelectedLocations.size());
		int randomNum = unSelectedLocations.get(index);
		removeFromUnselectedList(randomNum);
		chosenLocation = changeNumberToLocation(randomNum);
	}

	private void hitSurround() {
		while (hitableSurrounding(chosenLocation) == false) {
			switchLocation();
			chosenLocation = changeNumberToLocation(focusLocations
					.get(focusIndex));
		}
	}

	private void chooseLocation() {
		chosenLocation = getNextLocation(chosenLocation);
		int chosenNum = changeLocationToNumber(chosenLocation);
		removeFromUnselectedList(chosenNum);
	}

	private void wiselyChoose() {
		if (reconsider == true
				|| hitable(getNextLocation(chosenLocation)) == false) {
			chosenLocation = changeNumberToLocation(focusLocations
					.get(focusIndex));
			hitSurround();
			decideDirection();
		}
		chooseLocation();
	}

	private void getLocation(Location loc) {
		row = loc.getRow();
		col = loc.getCol();
	}

	private boolean chooseHitableDirection() {
		if (hitable(new Location(row - 1, col)))
			return true; // up
		if (hitable(new Location(row + 1, col)))
			return true; // down
		if (hitable(new Location(row, col - 1)))
			return true; // left
		if (hitable(new Location(row, col + 1)))
			return true; // right
		return false;
	}

	private boolean hitableSurrounding(Location loc) {
		getLocation(loc);
		return chooseHitableDirection();
	}

	private void identifyDirection() {
		Location loc = getNextLocation(chosenLocation);
		if (hitable(loc) == false) {
			switchDirection();
			loc = getNextLocation(chosenLocation);
		}
		if (hitable(loc) == false) {
			oppositeDirection();
		}
	}

	private void decideDirection() {
		oppositeDirection();
		identifyDirection();
	}

	private void getThisLocation(Location thisLocation) {
		thisRow = thisLocation.getRow();
		thisCol = thisLocation.getCol();
	}

	private Location getNextLocation() {
		Location nextLocation;
		if (checkDirection == Direction.UP)
			nextLocation = new Location(thisRow - 1, thisCol);
		else if (checkDirection == Direction.DOWN)
			nextLocation = new Location(thisRow + 1, thisCol);
		else if (checkDirection == Direction.LEFT)
			nextLocation = new Location(thisRow, thisCol - 1);
		else
			// (checkDirection == Launcher.RIGHT) the only condition left
			nextLocation = new Location(thisRow, thisCol + 1);
		return nextLocation;
	}

	private Location getNextLocation(Location thisLocation) {
		getThisLocation(thisLocation);
		return getNextLocation();
	}

	private int getLastLocation(int locationNum, int distance) {
		int lastLocation;
		if (checkDirection == Direction.UP)
			lastLocation = locationNum + 10 * distance;
		else if (checkDirection == Direction.DOWN)
			lastLocation = locationNum - 10 * distance;
		else if (checkDirection == Direction.LEFT)
			lastLocation = locationNum + 1 * distance;
		else
			// (checkDirection == Launcher.RIGHT) the only condition left
			lastLocation = locationNum - 1 * distance;
		return lastLocation;
	}

	private boolean hitable(Location loc) {
		if (reachable(loc) && !hasBeenHit(loc))
			return true;
		else
			return false;
	}

	private boolean reachable(Location loc) {
		if (loc.getRow() < 0 || loc.getRow() > 9)
			return false;
		if (loc.getCol() < 0 || loc.getCol() > 9)
			return false;
		return true;
	}

	private boolean hasBeenHit(Location loc) {
		return Launcher.gui.battleField.getMyCheckerCell(loc).hasBeenHit();
	}

	private void oppositeDirection() {
		if (checkDirection == Direction.RIGHT)
			checkDirection = Direction.LEFT;
		else if (checkDirection == Direction.UP)
			checkDirection = Direction.DOWN;
		else if (checkDirection == Direction.DOWN)
			checkDirection = Direction.UP;
		else
			// (checkDirection == Launcher.LEFT) the only condition left
			checkDirection = Direction.RIGHT;
	}

	private void switchDirection() {
		if (checkDirection == Direction.UP)
			checkDirection = Direction.RIGHT;
		else if (checkDirection == Direction.RIGHT)
			checkDirection = Direction.DOWN;
		else if (checkDirection == Direction.DOWN)
			checkDirection = Direction.LEFT;
		else
			// (checkDirection == Launcher.DOWN) the only condition left
			checkDirection = Direction.UP;
	}

	private void switchLocation() {
		focusIndex++;
		decideDirection();
	}

	private void removeFromUnselectedList(int number) {
		unSelectedLocations.remove(unSelectedLocations.indexOf(number));
	}

	public void addLocationToList(Location loc) {
		focusLocations.add(changeLocationToNumber(loc));
	}

	public void removeShipFromList(int size) {
		int thisLocationNum = changeLocationToNumber(chosenLocation);
		for (int i = 0; i < size; i++) {
			focusLocations.remove(focusLocations.indexOf(getLastLocation(
					thisLocationNum, i)));
		}
		focusIndex = 0;
	}

	private int changeLocationToNumber(Location loc) {
		int number = (loc.getRow() * 10) + loc.getCol();
		return number;
	}

	private Location changeNumberToLocation(int number) {
		Location loc = new Location(number / 10, number % 10);
		return loc;
	}
	
	public void addListener(AIListener listener) {
		listeners.add(listener);
	}
}
