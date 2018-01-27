package model;

import javax.swing.ImageIcon;


public class Carrier extends Ship {

	public Carrier() {
		super();
		super.size = BattleField.carrierSize;
		super.hp=super.size;
		super.name="Carrier";
		super.shipIcon[0] = new ImageIcon("res/pictures/carrier.gif");

	}
}
