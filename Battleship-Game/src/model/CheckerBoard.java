package model;

import java.util.ArrayList;

public class CheckerBoard {
	public ArrayList<Ship> Ships;
	private Ship Carrier;
	private Ship Battleship;
	private Ship Submarine;
	private Ship Seawolf;
	private Ship Patrol;
	public CheckerCell checkerCells[][];

	public static CheckerBoard createCheckerBoard() {
		return new CheckerBoard();
	}

	private CheckerBoard() {
		Ships = new ArrayList<Ship>();
		checkerCells = new CheckerCell[10][10];
		for (int row = 0; row < 10; row++) {
			for (int col = 0; col < 10; col++) {
				CheckerCell checkerCell = CheckerCell.createCheckerCell();
				checkerCells[row][col] = checkerCell;
			}
		}
		Carrier = new Carrier();
		Ships.add(Carrier);
		Battleship = new Battleship();
		Ships.add(Battleship);
		Submarine = new Submarine();
		Ships.add(Submarine);
		Seawolf = new Seawolf();
		Ships.add(Seawolf);
		Patrol = new Patrol();
		Ships.add(Patrol);
	}

	public CheckerCell getCheckerCell(Location loc) {
		int row = loc.getRow();
		int col = loc.getCol();
		CheckerCell checkerCell = checkerCells[row][col];
		return checkerCell;
	}
	
	public Ship getCarrier(){
		return Carrier;
	}
	public Ship getBattleship(){
		return Battleship;
	}
	public Ship getSubmarine(){
		return Submarine;
	}
	public Ship getSeawolf(){
		return Seawolf;
	}
	public Ship getPatrol(){
		return Patrol;
	}

	public boolean allSunk() {
		for (Ship ship : Ships) {
			if (ship.sunk == false) {
				return false;
			}
		}
		return true;
	}
}
