package model;

import javax.swing.ImageIcon;

public class Patrol extends Ship {

	public Patrol() {
		super();
		super.size = BattleField.patrolSize;
		super.hp=super.size;
		super.name="Patrol";
		super.shipIcon[0] = new ImageIcon("res/pictures/patrol.gif");
	}

}