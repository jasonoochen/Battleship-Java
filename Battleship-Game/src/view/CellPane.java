package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import sounds.MouseMoving;
import model.BattleField;
import model.CheckerCell;
import model.Location;

public class CellPane extends JPanel {
	private static final long serialVersionUID = 4277684250745058659L;
	
	public boolean belongToPc;
	public Color defaultBackground;
	public Location cellLoc;
	public boolean available, wasShot;
	private List<CellPaneListener> listeners = new ArrayList<>();

	public CellPane(Location loc) {
		cellLoc = loc;
		available = true;
		wasShot=false;
		addMouseListener(new MouseAdapter() {

			public void mouseEntered(MouseEvent e) {
				if (available&&!wasShot) {
					defaultBackground = getBackground();
					if (!gameHasStarted()) {
						if (getCheckerCell().isOccupied() == false&&getBattleField().getSelectedShip()!=null) {
							setBackground(Color.yellow);
							MouseMovingSound();
						}
					} else {
						setBackground(Color.yellow);
						MouseMovingSound();
					}
				}
			}

			public void mouseExited(MouseEvent e) {
				if (available&&!wasShot) {
					if (!gameHasStarted()) {
						if (getCheckerCell().isOccupied() == false) {
							setBackground(defaultBackground);
						}
					} else {
						setBackground(defaultBackground);
					}
				}
			}

			public void mousePressed(MouseEvent e) {
				if (available&&!wasShot) {
					if (!gameHasStarted()) {
						place();
						setBackground(defaultBackground);
					}
					else{
						hit();
						wasShot=true;
					}
				}
			}

//			public void mouseReleased(MouseEvent e) {
//				if (available&&!wasShot) {
//					if (gameHasStarted()) {
//						hit();
//						wasShot=true;
//					}
//				}
//
//			}

		});
	}
	
	public BattleField getBattleField(){
		return Launcher.gui.battleField;
	}

	public CheckerCell getCheckerCell() {
		CheckerCell checkerCell;
		if (belongToPc){
			checkerCell=getBattleField().getPcCellByLocation(cellLoc);
		}
		else{
			checkerCell=getBattleField().getMyCellByLocation(cellLoc);
		}
		return checkerCell;
	}

	public void setDefaultBackground(Color color) {
		defaultBackground = color;
	}

	public Dimension getPreferredSize() {
		return new Dimension(40, 40);
	}

	public void place() {
		if (getCheckerCell().isOccupied() == false) {
			getBattleField().placeMyShipAt(cellLoc);
		}
	}

	public void hit() {
		getBattleField().hitPcAt(cellLoc);
	}

	public void MouseMovingSound() {
		MouseMoving mouse = new MouseMoving();
		mouse.playMouseMovingSound();
	}
	
	public boolean gameHasStarted(){
		return getBattleField().hasStarted();
	}
	
	public void addListener(CellPaneListener listener) {
		listeners.add(listener);
	}
	

}
