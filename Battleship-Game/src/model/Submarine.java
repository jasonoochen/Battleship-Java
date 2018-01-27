package model;

import javax.swing.ImageIcon;

public class Submarine extends Ship {

	public Submarine() {
		super();
		super.size = BattleField.submarineSize;
		super.hp=super.size;
		super.name="Submarine";
		super.shipIcon[0] = new ImageIcon("res/pictures/submarine.gif");
	}


}
