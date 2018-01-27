package TDD;

import static org.junit.Assert.*;

import org.junit.Test;

import model.AI;
import model.BattleField;
import model.Location;
import model.Ship;

public class JUnitTest {
	
	@Test
	public void locationMethodTestForRow(){
		Location loc= new Location(3,5);
		assertEquals(3,loc.getRow());
	}
	@Test
	public void locationMethodTestForColumn(){
		Location loc= new Location(3,5);
		assertEquals(5,loc.getCol());
	}
	@Test
	public void myShipPlacingTest() {
		BattleField battleField=BattleField.createBattleField();
		battleField.setVerticalPosition();
		Location loc = new Location(4, 9);
		Ship patrol = new Ship();
		battleField.getMyBoard().checkerCells[4][9].occupyBy(patrol);
		assertTrue(battleField.getMyCheckerCell(loc).isOccupied());
	}

	@Test
	public void pcShipPlacingTest() {
		BattleField battleField = BattleField.createBattleField();
		battleField.selectShip(battleField.getPcBoard().getCarrier());
		battleField.selectShipSizeByIndex(1);
		battleField.setVerticalPosition();
		Location loc = new Location(4, 9);
		battleField.placePcShipAt(loc);
		assertTrue(battleField.getPcCheckerCell(loc).isOccupied());
	}
	
	@Test
	public void focusListTestOnSize(){
		AI ai = AI.createAI();
		for (int i=0;i<6;i++){
			ai.addLocationToList(new Location(7,2+i));
		}
		assertEquals(6,ai.focusLocations.size());
	}
	
	@Test
	public void focusListTestForTheFirstCell(){
		AI ai = AI.createAI();
		for (int i=0;i<6;i++){
			ai.addLocationToList(new Location(7,2+i));
		}
		assertEquals(0,ai.focusLocations.indexOf(72));
	}
	@Test
	public void focusListTestForTheLastCell(){
		AI ai = AI.createAI();
		for (int i=0;i<6;i++){
			ai.addLocationToList(new Location(7,2+i));
		}
		assertEquals(5,ai.focusLocations.indexOf(77));
	}

}
