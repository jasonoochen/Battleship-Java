package model;

public class Location {
	private int row;
	private int col;

	public Location(int x, int y) {
		this.row = x;
		this.col = y;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public String toString() {
		return row + " " + col;
	}
}
