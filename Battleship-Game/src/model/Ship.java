package model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import view.ShipListener;


public class Ship {
	public ImageIcon[] shipIcon;
	public boolean placed, sunk;
	public int size, hp;
	Owner owner;
	public String name;
	private List<ShipListener> listeners = new ArrayList<>();

	public Ship() {
		shipIcon = new ImageIcon[2];
		placed = false;
	}

	public ImageIcon getHorizontalIcon() {
		return shipIcon[0];
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	public int getSize() {
		return size;
	}

	public void setMyButton() {
	}

	public void hit() {
		hp--;
		if (hp == 0) {
			sunk();
		}
	}
	public void sunk(){
		sunk = true;
		for(ShipListener listener:listeners){
			listener.shipWasSunk(name,size);
		}
	}
	

	public void addListener(ShipListener listener) {
		listeners.add(listener);
	}
}
