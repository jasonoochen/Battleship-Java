package view;

import model.Location;

public interface BattleFieldListener {
	void shipOverlap();
	void outOfBoundry();
	void myPatrolPlacedAt(Location loc, int i);//horizontal
	void mySeawolfPlaceAt(Location loc, int i);
	void mySubmarinePlaceAt(Location loc, int i);
	void myBattleshipPlaceAt(Location loc, int i);
	void myCarrierPlaceAt(Location loc, int i);
	void VmyPatrolPlaceAt(Location loc, int i);//Vertical
	void VmySeawolfPlaceAt(Location loc, int i);
	void VmySubmarinePlaceAt(Location loc, int i);
	void VmyBattleshipPlaceAt(Location loc, int i);
	void VmyCarrierPlaceAt(Location loc, int i);
	void gameStart();
	void failedToStart();
	void gameStop();
	void iWon();
	void pcWon();
	void pcShotHitAt(Location loc);
	void pcShotMissAt(Location loc);
	void myShotHitAt(Location loc);
	void myShotMissAt(Location loc);
	void disableSelectedButton();
	String getSelectedShipName();
}
