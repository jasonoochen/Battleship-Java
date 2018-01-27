package model;

import java.util.ArrayList;
import java.util.List;

import view.BattleFieldListener;

public class BattleField {

	private CheckerBoard myBoard, pcBoard;
	public final static int carrierSize = 5, battleshipSize = 4, submarineSize = 3, seawolfSize = 3, patrolSize = 2;
	private int shipSize[] = { carrierSize, battleshipSize, submarineSize, seawolfSize, patrolSize };
	private Ship selectedShip;
	private Location selectedLocation;
	private int selectedSize;
	private Position selectedPosition;
	private boolean started;
	private List<BattleFieldListener> listeners = new ArrayList<>();

	public static BattleField createBattleField() {
		return new BattleField();
	}

	private BattleField() {
		myBoard = CheckerBoard.createCheckerBoard();
		pcBoard = CheckerBoard.createCheckerBoard();
	}

	public void defaultSetting() {
		selectedSize = 0;
		selectedShip = null;
		selectedPosition = Position.HORIZONTAL;
		selectedLocation = null;
		started = false;
	}

	public boolean allShipPlaced() {
		for (Ship myShip : myBoard.Ships) {
			if (myShip.placed == false) {
				return false;
			}
		}
		return true;
	}

	public boolean hasStarted() {
		return started;
	}

	public void setVerticalPosition() {
		selectedPosition = Position.VERTICAL;
	}

	public void setHorizontalPosition() {
		selectedPosition = Position.HORIZONTAL;
	}

	public CheckerCell getMyCheckerCell(Location loc) {
		return myBoard.checkerCells[loc.getRow()][loc.getCol()];
	}

	public CheckerCell getPcCheckerCell(Location loc) {
		return pcBoard.checkerCells[loc.getRow()][loc.getCol()];
	}

	public void placeMyShipAt(Location loc) {
		selectedLocation = loc;
		if (selectedShip != null) {
			if (selectedShip.placed == false && placableForMe()) {
				selectedShip.setOwner(Owner.ME);
				selectedShip.placed = true;
				for (BattleFieldListener listener : listeners) {
					listener.disableSelectedButton();
				}
				placeMyShip();
				
			}
		}
	}

	private boolean placableForMe() {
		try {
			for (int i = 0; i < selectedSize; i++) {
				Location loc = goThroughCells(i);
				if (getMyCheckerCell(loc).isOccupied()) {
					for (BattleFieldListener listener : listeners) {
						listener.shipOverlap();
					}
					return false;
				}
			}
			return true;
		} catch (ArrayIndexOutOfBoundsException e) {
			for (BattleFieldListener listener : listeners) {
				listener.outOfBoundry();
			}

			return false;
		}

	}

	private void placeMyShip() {
		for (int i = 0; i < selectedSize; i++) {
			Location loc = goThroughCells(i);
			getMyCheckerCell(loc).occupyBy(selectedShip);
			for (BattleFieldListener listener : listeners) {
				if(selectedPosition == Position.HORIZONTAL){
					if(listener.getSelectedShipName().equals("Patrol"))
						listener.myPatrolPlacedAt(loc, i);
					else if(listener.getSelectedShipName().equals("Seawolf"))
						listener.mySeawolfPlaceAt(loc, i);
					else if(listener.getSelectedShipName().equals("Submarine"))
						listener.mySubmarinePlaceAt(loc, i);
					else if(listener.getSelectedShipName().equals("Battleship"))
						listener.myBattleshipPlaceAt(loc, i);
					else if(listener.getSelectedShipName().equals("Carrier"))
						listener.myCarrierPlaceAt(loc, i);
					else
						break;
				}
				else if(selectedPosition == Position.VERTICAL){
					if(listener.getSelectedShipName().equals("Patrol"))
						listener.VmyPatrolPlaceAt(loc, i);
					else if(listener.getSelectedShipName().equals("Seawolf"))
						listener.VmySeawolfPlaceAt(loc, i);
					else if(listener.getSelectedShipName().equals("Submarine"))
						listener.VmySubmarinePlaceAt(loc, i);
					else if(listener.getSelectedShipName().equals("Battleship"))
						listener.VmyBattleshipPlaceAt(loc, i);
					else if(listener.getSelectedShipName().equals("Carrier"))
						listener.VmyCarrierPlaceAt(loc, i);
					else
						break;
				}
			}
		}
	}

	public boolean placePcShipAt(Location loc) {
		selectedLocation = loc;
		if (placableForPc()) {
			selectedShip.setOwner(Owner.PC);
			selectedShip.placed = true;
			placePcShip();
			return true;
		}
		return false;
	}

	private boolean placableForPc() {
		try {
			for (int i = 0; i < selectedSize; i++) {
				Location loc = goThroughCells(i);
				if (getPcCheckerCell(loc).isOccupied()) {
					return false;
				}
			}
			return true;
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}

	}

	private void placePcShip() {
		for (int i = 0; i < selectedSize; i++) {
			Location loc = goThroughCells(i);
			getPcCheckerCell(loc).occupyBy(selectedShip);
		}
	}

	private Location goThroughCells(int i) {
		Location loc = null;
		int indexAdjust = selectedSize / 2;
		if (selectedPosition == Position.HORIZONTAL) {
			loc = new Location(selectedLocation.getRow(), selectedLocation.getCol() - i + indexAdjust);
		}
		if (selectedPosition == Position.VERTICAL) {
			loc = new Location(selectedLocation.getRow() - i + indexAdjust, selectedLocation.getCol());
		}
		return loc;
	}

	public void start() {
		if (allShipPlaced()) {
			for (BattleFieldListener listener : listeners) {
				listener.gameStart();
			}
			started = true;
		} else {
			for (BattleFieldListener listener : listeners) {
				listener.failedToStart();
			}
		}
	}

	public void hitMeAt(Location loc) {
		if (getMyCheckerCell(loc).isOccupied() == true) {
			for (BattleFieldListener listener : listeners) {
				listener.pcShotHitAt(loc);
			}

		} else {
			for (BattleFieldListener listener : listeners) {
				listener.pcShotMissAt(loc);
			}
		}
	}

	public void hitPcAt(Location loc) {
		if (getPcCheckerCell(loc).isOccupied() == true) {
			for (BattleFieldListener listener : listeners) {
				listener.myShotHitAt(loc);
			}
		} else {
			for (BattleFieldListener listener : listeners) {
				listener.myShotMissAt(loc);
			}

		}
	}

	public void checkMyShips() {
		if (myBoard.allSunk()) {
			for (BattleFieldListener listener : listeners) {
				listener.pcWon();
				listener.gameStop();
			}
			started = false;
		}
	}

	public void checkPcShips() {
		if (pcBoard.allSunk()) {
			for (BattleFieldListener listener : listeners) {
				listener.iWon();
				listener.gameStop();
			}
			started = false;
		}
	}

	public void addListener(BattleFieldListener listener) {
		listeners.add(listener);
	}

	public CheckerBoard getMyBoard() {
		return myBoard;
	}

	public CheckerBoard getPcBoard() {
		return pcBoard;
	}

	public CheckerCell getMyCellByLocation(Location location) {
		return myBoard.getCheckerCell(location);
	}

	public CheckerCell getPcCellByLocation(Location location) {
		return pcBoard.getCheckerCell(location);
	}

	public void selectShipSizeByIndex(int index) {
		selectedSize = shipSize[index];
	}

	public void selectPosition(Position selectedPosition) {
		this.selectedPosition = selectedPosition;
	}

	public void selectShip(Ship selectedShip) {
		this.selectedShip = selectedShip;
	}
	
	public Ship getSelectedShip(){
		return selectedShip;
	}

}
