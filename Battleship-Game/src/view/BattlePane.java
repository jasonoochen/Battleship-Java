package view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;

import model.Location;

public class BattlePane extends JPanel {
	private static final long serialVersionUID = -8078470202707340571L;

	public CellPane cellPanes[][];

	public BattlePane() {
		cellPanes = new CellPane[10][10];
		setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();
		for (int row = 0; row < 10; row++) {
			for (int col = 0; col < 10; col++) {
				gbc.gridx = col;
				gbc.gridy = row;

				Location loc = new Location(row, col);
				CellPane cellPane = new CellPane(loc);
				cellPanes[row][col] = cellPane;

				if ((row + col) % 2 == 0) {
					cellPane.setBackground(new Color(0, 175, 250));
					cellPane.setDefaultBackground(new Color(0, 175, 250));
				} else {
					cellPane.setBackground(new Color(0, 200, 250));
					cellPane.setDefaultBackground(new Color(0, 200, 250));
				}

				Border border = null;
				Color seaColor = new Color(0, 200, 225);

				if (row < 9) {
					if (col < 9) {
						border = new MatteBorder(1, 1, 0, 0, seaColor);
					} else {
						border = new MatteBorder(1, 1, 0, 1, seaColor);
					}
				} else {
					if (col < 9) {
						border = new MatteBorder(1, 1, 1, 0, seaColor);
					} else {
						border = new MatteBorder(1, 1, 1, 1, seaColor);
					}
				}

				cellPane.setBorder(border);
				add(cellPane, gbc);
			}
		}
	}

	public void enablePane() {
		for (int row = 0; row < 10; row++) {
			for (int col = 0; col < 10; col++) {
				cellPanes[row][col].available = true;
			}
		}
	}

	public void disablePane() {
		for (int row = 0; row < 10; row++) {
			for (int col = 0; col < 10; col++) {
				cellPanes[row][col].available = false;
			}
		}
	}
	
	

}