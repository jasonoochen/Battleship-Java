package model;

import javax.swing.ImageIcon;


public class Seawolf extends Ship {

	public Seawolf() {
		super();
		super.size = BattleField.seawolfSize;
		super.hp=super.size;
		super.name="Seawolf";
		super.shipIcon[0] = new ImageIcon("res/pictures/seawolf.gif");
	}

}
