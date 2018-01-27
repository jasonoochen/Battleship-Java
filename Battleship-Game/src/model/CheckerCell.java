package model;

public class CheckerCell {

	public static CheckerCell createCheckerCell() {
		return new CheckerCell();
	}

	private boolean hit;
	private Ship placedShip;

	private CheckerCell() {
		hit = false;
		placedShip = null;
	}

	public boolean isOccupied() {
		if (placedShip == null) {
			return false;
		} else {
			return true;
		}
	}

	public void occupyBy(Ship selectedShip) {
		placedShip = selectedShip;
	}

	public void hit() {
		hit = true;
		if (placedShip != null) {
			placedShip.hit();
		}
	}

	public boolean hasBeenHit() {
		return hit;
	}

}
