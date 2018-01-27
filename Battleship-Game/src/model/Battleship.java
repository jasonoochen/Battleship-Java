package model;

import javax.swing.ImageIcon;


public class Battleship extends Ship {

	public Battleship() {
		super();
		super.size = BattleField.battleshipSize;
		super.hp=super.size;
		super.name="Battleship";
		super.shipIcon[0] = new ImageIcon("res/pictures/battleship.gif");
	}

}
