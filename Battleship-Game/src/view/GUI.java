package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import sounds.Fire;
import sounds.PressButton;
import sounds.ShipExplode;
import sounds.ShipHit;
import sounds.ExplodeInWater;
import sounds.FailedToPlaceShip;
import sounds.Lose;
import sounds.ShipPlacing;
import sounds.Start;
import sounds.UnitLost;
import sounds.Victory;
import sounds.Warning;
import sounds.Welcome;
import model.AI;
import model.BattleField;
import model.Location;
//import model.Position;
import model.Ship;

public class GUI {

	public BattleField battleField;
	public AI ai;
	public BattlePane pcPane, myPane;
	public JButton startButton, resetButton, selectedButton, helpButton;
	public JFrame frame;
	private ArrayList<JButton> Buttons;
	private JComboBox<String> positionComb, difficultyComb;
	private JPanel upPanel, vsPanel, downPanel;
	private JLabel labelForVsPanel;
	public JButton carrierB, battleshipB, submarineB, seawolfB, patrolB;
	private String[] difficulty = { "Easy", "Hard" };
	private String[] position = { "Horizontal", "Vertical" };
	private Ship[] myShips;
	private Ship[] pcShips;
	public JButton shipsButtons[];
//	private int XPosition[] = { 4, 9, 4, 1, 7 };
//	private int YPosition[] = { 9, 3, 1, 3, 8 };
//	private Position direction[] = { Position.VERTICAL, Position.HORIZONTAL,
//			Position.VERTICAL, Position.HORIZONTAL, Position.VERTICAL };
	String shipName[] = { "Carrier", "Battleship", "Submarine", "Seawolf",
			"Patrol" };
	public String selectedShipName;

	public GUI() {
		run();
	}

	public void run() {
		battleField = BattleField.createBattleField();
		battleField.defaultSetting();
		createWindow();
		windowRun();
		ai = AI.createAI();
		addListenerToAI();
		addListenerToCellPane();
		addListenerToShips();
		addListenerToBattleField();
		WelcomeSound();
	}

	public void reset() {
		PressButtonSound();
		frame.dispose();
		run();
	}

	public void stop() {
		pcPane.disablePane();
	}

	public void addListenerToCellPane() {
		for (int row = 0; row < 10; row++) {
			for (int col = 0; col < 10; col++) {
				myPane.cellPanes[row][col].addListener(new CellPaneListener() {
					@Override
					public BattleField getBattleField() {
						return battleField;
					}
				});
			}
		}

	}

	public void addListenerToAI() {

		ai.addListener(new AIListener() {
			@Override
			public void AIFire(Location chosenLocation) {
				battleField.hitMeAt(chosenLocation);
			}

			@Override
			public boolean checkCellForAI(Location loc) {
				return battleField.getMyCheckerCell(loc).hasBeenHit();
			}
		});

	}

	public void addListenerToShips() {
		for (Ship ship : battleField.getMyBoard().Ships) {
			ship.addListener(new ShipListener() {
				@Override
				public void shipWasSunk(String name, int size) {

					ShipExplodeSound();
					UnitLostSound();
					JOptionPane.showMessageDialog(frame, "Oh! Your " + name
							+ " has been destroyed!");
					if (ai.smart) {
						ai.removeShipFromList(size);
						ai.reconsider = true;
					}
					battleField.checkMyShips();

				}
			});
		}
		for (Ship ship : battleField.getPcBoard().Ships) {
			ship.addListener(new ShipListener() {
				@Override
				public void shipWasSunk(String name, int size) {

					ShipExplodeSound();
					JOptionPane.showMessageDialog(frame,
							"Yeah! You just destroy their " + name);
					battleField.checkPcShips();

				}
			});
		}
	}

	public void addListenerToBattleField() {
		battleField.addListener(new BattleFieldListener() {
			@Override
			public void shipOverlap() {
				FailedToPlaceShipSound();
				JOptionPane.showMessageDialog(frame,
						"Overlapped! Please select another place.");
			}

			@Override
			public void outOfBoundry() {
				FailedToPlaceShipSound();
				JOptionPane.showMessageDialog(frame,
						"Out of boundry! Please select another place.");
			}

			@Override
			public void myPatrolPlacedAt(Location loc, int i) {
				if(i==0){
					ImageIcon image= new ImageIcon("res/pictures/Hpatrol1.png");
					JLabel label = new JLabel(image);
					getMyCellPane(loc).add(label);
					frame.pack();
					getMyCellPane(loc).available = false;
					ShipPlacingSound();
				}else if(i==1){
					ImageIcon image= new ImageIcon("res/pictures/Hpatrol2.png");
					JLabel label = new JLabel(image);
					getMyCellPane(loc).add(label);
					frame.pack();
					getMyCellPane(loc).available = false;
					ShipPlacingSound();
				}
			}
			
			@Override
			public void mySeawolfPlaceAt(Location loc, int i) {
				if(i == 0){
					ImageIcon image= new ImageIcon("res/pictures/Hseawolf3.png");
					JLabel label = new JLabel(image);
					getMyCellPane(loc).add(label);
					frame.pack();
					getMyCellPane(loc).available = false;
					ShipPlacingSound();
				}else if(i == 1){
					ImageIcon image= new ImageIcon("res/pictures/Hseawolf2.png");
					JLabel label = new JLabel(image);
					getMyCellPane(loc).add(label);
					frame.pack();
					getMyCellPane(loc).available = false;
					ShipPlacingSound();
				}else if(i == 2){
					ImageIcon image= new ImageIcon("res/pictures/Hseawolf1.png");
					JLabel label = new JLabel(image);
					getMyCellPane(loc).add(label);
					frame.pack();
					getMyCellPane(loc).available = false;
					ShipPlacingSound();
				}
				
			}
			
			@Override
			public void mySubmarinePlaceAt(Location loc, int i) {
				if(i == 0){
					ImageIcon image= new ImageIcon("res/pictures/Hsubmarine1.png");
					JLabel label = new JLabel(image);
					getMyCellPane(loc).add(label);
					frame.pack();
					getMyCellPane(loc).available = false;
					ShipPlacingSound();
				}else if(i == 1){
					ImageIcon image= new ImageIcon("res/pictures/Hsubmarine2.png");
					JLabel label = new JLabel(image);
					getMyCellPane(loc).add(label);
					frame.pack();
					getMyCellPane(loc).available = false;
					ShipPlacingSound();
				}else if(i == 2){
					ImageIcon image= new ImageIcon("res/pictures/Hsubmarine3.png");
					JLabel label = new JLabel(image);
					getMyCellPane(loc).add(label);
					frame.pack();
					getMyCellPane(loc).available = false;
					ShipPlacingSound();
				}
				
			}
			
			@Override
			public void myBattleshipPlaceAt(Location loc, int i) {
				if(i == 0){
					ImageIcon image= new ImageIcon("res/pictures/Hbattleship1.png");
					JLabel label = new JLabel(image);
					getMyCellPane(loc).add(label);
					frame.pack();
					getMyCellPane(loc).available = false;
					ShipPlacingSound();
				}else if(i == 1){
					ImageIcon image= new ImageIcon("res/pictures/Hbattleship2.png");
					JLabel label = new JLabel(image);
					getMyCellPane(loc).add(label);
					frame.pack();
					getMyCellPane(loc).available = false;
					ShipPlacingSound();
				}else if(i == 2){
					ImageIcon image= new ImageIcon("res/pictures/Hbattleship3.png");
					JLabel label = new JLabel(image);
					getMyCellPane(loc).add(label);
					frame.pack();
					getMyCellPane(loc).available = false;
					ShipPlacingSound();
				}else if(i == 3){
					ImageIcon image= new ImageIcon("res/pictures/Hbattleship4.png");
					JLabel label = new JLabel(image);
					getMyCellPane(loc).add(label);
					frame.pack();
					getMyCellPane(loc).available = false;
					ShipPlacingSound();
				}
				
			}
			
			@Override
			public void myCarrierPlaceAt(Location loc, int i) {
				if(i == 0){
					ImageIcon image= new ImageIcon("res/pictures/HCarrier1.png");
					JLabel label = new JLabel(image);
					getMyCellPane(loc).add(label);
					frame.pack();
					getMyCellPane(loc).available = false;
					ShipPlacingSound();
				}else if(i == 1){
					ImageIcon image= new ImageIcon("res/pictures/HCarrier2.png");
					JLabel label = new JLabel(image);
					getMyCellPane(loc).add(label);
					frame.pack();
					getMyCellPane(loc).available = false;
					ShipPlacingSound();
				}else if(i == 2){
					ImageIcon image= new ImageIcon("res/pictures/HCarrier3.png");
					JLabel label = new JLabel(image);
					getMyCellPane(loc).add(label);
					frame.pack();
					getMyCellPane(loc).available = false;
					ShipPlacingSound();
				}else if(i == 3){
					ImageIcon image= new ImageIcon("res/pictures/HCarrier4.png");
					JLabel label = new JLabel(image);
					getMyCellPane(loc).add(label);
					frame.pack();
					getMyCellPane(loc).available = false;
					ShipPlacingSound();
				}else if(i == 4){
					ImageIcon image= new ImageIcon("res/pictures/HCarrier5.png");
					JLabel label = new JLabel(image);
					getMyCellPane(loc).add(label);
					frame.pack();
					getMyCellPane(loc).available = false;
					ShipPlacingSound();
				}
				
			}

			@Override
			public void gameStart() {
				prepareForStart();
				StartSound();
				JOptionPane.showMessageDialog(frame, "Game has started!!");
				placeShipsForAI();
			}

			@Override
			public void failedToStart() {
				WarningSound();
				JOptionPane.showMessageDialog(frame,
						"Please place all the ships before starting the game.");
			}

			@Override
			public void gameStop() {
				stop();
			}

			@Override
			public void pcShotHitAt(Location loc) {
				FireSound();
				getMyCellPane(loc).setBackground(Color.yellow);
				getMyCellPane(loc).defaultBackground = Color.yellow;
				new Timer().schedule(new TimerTask() {
					@Override
					public void run() {
						getMyCellPane(loc).setBackground(Color.red);
						ai.addLocationToList(loc);
						ai.reconsider = false;
						ShipHitSound();
						battleField.getMyCheckerCell(loc).hit();
						if (battleField.hasStarted()) {
							pcMove();
						}
					}
				}, 400);// 400 milliseconds is 0.4 second
			}

			@Override
			public void pcShotMissAt(Location loc) {
				FireSound();
				getMyCellPane(loc).setBackground(Color.yellow);
				getMyCellPane(loc).defaultBackground = Color.yellow;
				new Timer().schedule(new TimerTask() {
					@Override
					public void run() {
						getMyCellPane(loc).setBackground(Color.gray);
						ai.reconsider = true;
						explodeInWater();
						pcPane.enablePane();
						battleField.getMyCheckerCell(loc).hit();
					}
				}, 400);// 400 milliseconds is 0.4 second
			}

			@Override
			public void myShotHitAt(Location loc) {
				FireSound();
				getPcCellPane(loc).setBackground(Color.yellow);
				getPcCellPane(loc).defaultBackground = Color.yellow;
				new Timer().schedule(new TimerTask() {
					@Override
					public void run() {
						getPcCellPane(loc).setBackground(Color.red);
						getPcCellPane(loc).defaultBackground = Color.red;
						ShipHitSound();
						battleField.getPcCheckerCell(loc).hit();
					}
				}, 400);// 400 milliseconds is 0.4 second
			}

			@Override
			public void myShotMissAt(Location loc) {
				pcPane.disablePane();
				FireSound();
				getPcCellPane(loc).setBackground(Color.yellow);
				getPcCellPane(loc).defaultBackground = Color.yellow;
				new Timer().schedule(new TimerTask() {
					@Override
					public void run() {
						getPcCellPane(loc).setBackground(Color.gray);
						getPcCellPane(loc).defaultBackground = Color.gray;
						explodeInWater();
						battleField.getPcCheckerCell(loc).hit();
						pcMove();
					}
				}, 400);// 400 milliseconds is 0.4 second
			}

			@Override
			public void iWon() {
				VictorySound();
				JOptionPane.showMessageDialog(frame,
						"Congratulations! You win!");
			}

			@Override
			public void pcWon() {
				LoseSound();
				JOptionPane.showMessageDialog(frame, "Sorry, you lose.");
			}

			@Override
			public void disableSelectedButton() {
				selectedButton.setEnabled(false);

			}

			@Override
			public String getSelectedShipName() {
				return selectedShipName;
			}

			@Override
			public void VmyPatrolPlaceAt(Location loc, int i) {
				// TODO Auto-generated method stub
				if(i==0){
					ImageIcon image= new ImageIcon("res/pictures/Vpatrol1.png");
					JLabel label = new JLabel(image);
					getMyCellPane(loc).add(label);
					frame.pack();
					getMyCellPane(loc).available = false;
					ShipPlacingSound();
				}else if(i==1){
					ImageIcon image= new ImageIcon("res/pictures/Vpatrol2.png");
					JLabel label = new JLabel(image);
					getMyCellPane(loc).add(label);
					frame.pack();
					getMyCellPane(loc).available = false;
					ShipPlacingSound();
				}
			}

			@Override
			public void VmySeawolfPlaceAt(Location loc, int i) {
				// TODO Auto-generated method stub
				if(i == 0){
					ImageIcon image= new ImageIcon("res/pictures/Vseawolf3.png");
					JLabel label = new JLabel(image);
					getMyCellPane(loc).add(label);
					frame.pack();
					getMyCellPane(loc).available = false;
					ShipPlacingSound();
				}else if(i == 1){
					ImageIcon image= new ImageIcon("res/pictures/Vseawolf2.png");
					JLabel label = new JLabel(image);
					getMyCellPane(loc).add(label);
					frame.pack();
					getMyCellPane(loc).available = false;
					ShipPlacingSound();
				}else if(i == 2){
					ImageIcon image= new ImageIcon("res/pictures/Vseawolf1.png");
					JLabel label = new JLabel(image);
					getMyCellPane(loc).add(label);
					frame.pack();
					getMyCellPane(loc).available = false;
					ShipPlacingSound();
				}
			}

			@Override
			public void VmySubmarinePlaceAt(Location loc, int i) {
				// TODO Auto-generated method stub
				if(i == 0){
					ImageIcon image= new ImageIcon("res/pictures/Vsubmarine1.png");
					JLabel label = new JLabel(image);
					getMyCellPane(loc).add(label);
					frame.pack();
					getMyCellPane(loc).available = false;
					ShipPlacingSound();
				}else if(i == 1){
					ImageIcon image= new ImageIcon("res/pictures/Vsubmarine2.png");
					JLabel label = new JLabel(image);
					getMyCellPane(loc).add(label);
					frame.pack();
					getMyCellPane(loc).available = false;
					ShipPlacingSound();
				}else if(i == 2){
					ImageIcon image= new ImageIcon("res/pictures/Vsubmarine3.png");
					JLabel label = new JLabel(image);
					getMyCellPane(loc).add(label);
					frame.pack();
					getMyCellPane(loc).available = false;
					ShipPlacingSound();
				}
			}

			@Override
			public void VmyBattleshipPlaceAt(Location loc, int i) {
				// TODO Auto-generated method stub
				if(i == 0){
					ImageIcon image= new ImageIcon("res/pictures/Vbattleship1.png");
					JLabel label = new JLabel(image);
					getMyCellPane(loc).add(label);
					frame.pack();
					getMyCellPane(loc).available = false;
					ShipPlacingSound();
				}else if(i == 1){
					ImageIcon image= new ImageIcon("res/pictures/Vbattleship2.png");
					JLabel label = new JLabel(image);
					getMyCellPane(loc).add(label);
					frame.pack();
					getMyCellPane(loc).available = false;
					ShipPlacingSound();
				}else if(i == 2){
					ImageIcon image= new ImageIcon("res/pictures/Vbattleship3.png");
					JLabel label = new JLabel(image);
					getMyCellPane(loc).add(label);
					frame.pack();
					getMyCellPane(loc).available = false;
					ShipPlacingSound();
				}else if(i == 3){
					ImageIcon image= new ImageIcon("res/pictures/Vbattleship4.png");
					JLabel label = new JLabel(image);
					getMyCellPane(loc).add(label);
					frame.pack();
					getMyCellPane(loc).available = false;
					ShipPlacingSound();
				}
			}

			@Override
			public void VmyCarrierPlaceAt(Location loc, int i) {
				// TODO Auto-generated method stub
				if(i == 0){
					ImageIcon image= new ImageIcon("res/pictures/VCarrier1.png");
					JLabel label = new JLabel(image);
					getMyCellPane(loc).add(label);
					frame.pack();
					getMyCellPane(loc).available = false;
					ShipPlacingSound();
				}else if(i == 1){
					ImageIcon image= new ImageIcon("res/pictures/VCarrier2.png");
					JLabel label = new JLabel(image);
					getMyCellPane(loc).add(label);
					frame.pack();
					getMyCellPane(loc).available = false;
					ShipPlacingSound();
				}else if(i == 2){
					ImageIcon image= new ImageIcon("res/pictures/VCarrier3.png");
					JLabel label = new JLabel(image);
					getMyCellPane(loc).add(label);
					frame.pack();
					getMyCellPane(loc).available = false;
					ShipPlacingSound();
				}else if(i == 3){
					ImageIcon image= new ImageIcon("res/pictures/VCarrier4.png");
					JLabel label = new JLabel(image);
					getMyCellPane(loc).add(label);
					frame.pack();
					getMyCellPane(loc).available = false;
					ShipPlacingSound();
				}else if(i == 4){
					ImageIcon image= new ImageIcon("res/pictures/VCarrier5.png");
					JLabel label = new JLabel(image);
					getMyCellPane(loc).add(label);
					frame.pack();
					getMyCellPane(loc).available = false;
					ShipPlacingSound();
				}
			}
			
		});
	}

	public void pcMove() {
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				ai.fire();
			}
		}, 1000);// 1000 milliseconds is 1 second

	}

	public CellPane getMyCellPane(Location loc) {
		return myPane.cellPanes[loc.getRow()][loc.getCol()];
	}

	public CellPane getPcCellPane(Location loc) {
		return pcPane.cellPanes[loc.getRow()][loc.getCol()];
	}

	private void WelcomeSound() {
		Welcome welcome = new Welcome();
		welcome.playWelcomeSound();
	}

	private void ShipHitSound() {
		ShipHit shipHit = new ShipHit();
		shipHit.playShipHitSound();
	}

	private void ShipExplodeSound() {
		ShipExplode shipExplode = new ShipExplode();
		shipExplode.playShipExplodeSound();
	}

	private void explodeInWater() {
		ExplodeInWater explode = new ExplodeInWater();
		explode.playExplodeInWaterSound();
	}

	private void StartSound() {
		Start start = new Start();
		start.playStartSound();
	}

	private void ShipPlacingSound() {
		ShipPlacing ship = new ShipPlacing();
		ship.playShipPlacingSound();
	}

	private void LoseSound() {
		Lose lose = new Lose();
		lose.playLoseSound();
	}

	private void VictorySound() {
		Victory victory = new Victory();
		victory.playVictorySound();
	}

	private void WarningSound() {
		Warning warning = new Warning();
		warning.playWarningSound();
	}

	public void FireSound() {
		Fire fire = new Fire();
		fire.playFireSound();
	}

	public void UnitLostSound() {
		UnitLost unitLost = new UnitLost();
		unitLost.playUnitLostSound();
	}

	public void FailedToPlaceShipSound() {
		FailedToPlaceShip failedToPlaceShip = new FailedToPlaceShip();
		failedToPlaceShip.playFailedToPlaceShipSound();
	}

	public void PressButtonSound() {
		PressButton pressButton = new PressButton();
		pressButton.playPressButtonSound();
	}

	public void createWindow() {
		myPane = new BattlePane();
		pcPane = new BattlePane();
		for (CellPane[] cellArray : myPane.cellPanes) {
			for (CellPane myCell : cellArray) {
				myCell.belongToPc = false;
			}
		}
		for (CellPane[] cellArray : pcPane.cellPanes) {
			for (CellPane pcCell : cellArray) {
				pcCell.belongToPc = true;
			}
		}
		Buttons = new ArrayList<JButton>();
		myShips = new Ship[] { battleField.getMyBoard().getCarrier(),
				battleField.getMyBoard().getBattleship(), battleField.getMyBoard().getSubmarine(),
				battleField.getMyBoard().getSeawolf(), battleField.getMyBoard().getPatrol() };
		pcShips = new Ship[] { battleField.getPcBoard().getCarrier(),
			battleField.getPcBoard().getBattleship(),
			battleField.getPcBoard().getSubmarine(),
			battleField.getPcBoard().getSeawolf(), battleField.getPcBoard().getPatrol() };

		shipsButtons = new JButton[]{ carrierB, battleshipB, submarineB, seawolfB,
				patrolB };
	}

	public void CreateStartButton() {
		startButton = new JButton("Start !!!");
		startButton.setFont(new Font("Verdana", Font.PLAIN, 18));
		startButton.setPreferredSize(new Dimension(120, 32));
	}

	public void StartListener() {
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				battleField.start();
			}
		});
	}

	public void CreateResetButton() {
		resetButton = new JButton("Reset ???");
		resetButton.setFont(new Font("Verdana", Font.PLAIN, 18));
		resetButton.setPreferredSize(new Dimension(120, 32));
	}

	public void ResetListener() {
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				reset();
				;
			}
		});
	}

	public void CreateDifficultyCombButton() {
		difficultyComb = new JComboBox<String>(difficulty);
		difficultyComb.setFont(new Font("Verdana", Font.PLAIN, 18));
		difficultyComb.setPreferredSize(new Dimension(120, 32));
	}

	public void DifficultyCombListener() {
		difficultyComb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				chooseDifficulty();
			}
		});
	}

	public void chooseDifficulty() {
		Object selectedItem = difficultyComb.getSelectedItem();
		if (selectedItem.equals("Easy")) {
			PressButtonSound();
			ai.smart = false;
		}
		if (selectedItem.equals("Hard")) {
			PressButtonSound();
			ai.smart = true;
		}
	}

	public void CreatePositionCombButton() {
		positionComb = new JComboBox<String>(position);
	}

	public void PositionCombListener() {
		positionComb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				shipsDirection();
			}
		});
	}

	public void shipsDirection() {
		Object selectedItem = positionComb.getSelectedItem();
		if (selectedItem.equals("Vertical")) {
			PressButtonSound();
			battleField.setVerticalPosition();
		}
		if (selectedItem.equals("Horizontal")) {
			PressButtonSound();
			battleField.setHorizontalPosition();
		}
	}

	public void CreateUpPanel() {
		upPanel = new JPanel();
		upPanel.add(difficultyComb);
		upPanel.add(startButton);
		upPanel.add(resetButton);
		upPanel.add(helpButton);
		upPanel.setBackground(Color.darkGray);
	}

	public void CreateLabelForVsPanel() {
		labelForVsPanel = new JLabel("VS");
		labelForVsPanel.setForeground(Color.white);
		labelForVsPanel.setFont(new Font("Verdana", Font.BOLD, 50));
		vsPanel.setLayout(new GridBagLayout());
	}

	public void CreateVsPanel() {
		vsPanel = new JPanel();
		CreateLabelForVsPanel();
		vsPanel.add(labelForVsPanel);
		vsPanel.setBackground(Color.darkGray);
	}

	public void CreateDownPanel() {
		downPanel = new JPanel();
		downPanel.add(positionComb);
		for (JButton shipButton : Buttons) {
			downPanel.add(shipButton);
		}
		downPanel.setBackground(Color.darkGray);
	}

	public void CreateShipsButton() {
		ButtonHandler itemHandler = new ButtonHandler();
		for (int shipNum = 0; shipNum < 5; shipNum++) {
			(shipsButtons[shipNum] = new JButton(shipName[shipNum], myShips
					[shipNum].getHorizontalIcon()))
					.addActionListener(itemHandler);
			Buttons.add(shipsButtons[shipNum]);
		}
	}

	public void placeShipsButton() {
		for (Ship myShip : battleField.getMyBoard().Ships) {
			myShip.setMyButton();
		}
	}

	public void CreateFrame() {
		frame = new JFrame("BattleShip");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		designPanelPosition();
		adjustFrame();
	}

	public void designPanelPosition() {
		frame.getContentPane().add(upPanel, "North");
		frame.getContentPane().add(downPanel, "South");
		frame.getContentPane().add(pcPane, "East");
		frame.getContentPane().add(vsPanel, "Center");
		frame.getContentPane().add(myPane, "West");
	}

	public void adjustFrame() {
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public void disablePcPane() {
		pcPane.disablePane();
	}

	public void CreateButtonsForUpPanel() {
		CreateStartButton();
		StartListener();
		CreateResetButton();
		ResetListener();
		CreateDifficultyCombButton();
		DifficultyCombListener();
		CreateHelpButton();
		HelpListener();
	}

	private void HelpListener() {
		helpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(frame, "双方各有五艘战舰，所占长度分别为5、4、3、3、2. 玩家可以在横竖方向上随机放置战舰位置。\n"
						+ "其中当鼠标移到放船位置时，黄色标记代表瞄准，按下鼠标灰色标记代表未击中战舰，红色标记\n"
						+ "代表击中战舰。玩家可以按个人意愿选择游戏难度。电脑放置区域的战舰不会显示给玩家，当然\n"
						+ "电脑也不会知道玩家的战舰的具体位置。接下来请享受此款游戏。");
			}
		});
	}

	private void CreateHelpButton() {
		helpButton = new JButton("help");
		helpButton.setFont(new Font("Verdana", Font.PLAIN, 18));
		helpButton.setPreferredSize(new Dimension(120, 32));
	}

	public void CreateButtonsForDownPanel() {
		CreatePositionCombButton();
		PositionCombListener();
		CreateShipsButton();
		placeShipsButton();
	}

	public void CreateMainWindow() {
		CreateButtonsForUpPanel();
		CreateUpPanel();
		CreateVsPanel();
		CreateButtonsForDownPanel();
		CreateDownPanel();
		CreateFrame();
	}

	public void windowRun() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException ex) {
		}
		disablePcPane();
		CreateMainWindow();
	}

	public void prepareForStart() {
		pcPane.enablePane();
		myPane.disablePane();
		startButton.setEnabled(false);
		positionComb.setEnabled(false);
		difficultyComb.setEnabled(false);
	}

	private class ButtonHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			chooseShip(e);
			PressButtonSound();
		}
	}

	public void chooseShip(ActionEvent e) {
		for (int shipNum = 0; shipNum < 5; shipNum++) {
			if (e.getSource() == shipsButtons[shipNum]) {
				Object source = e.getSource();
				if (source instanceof JButton) {
					selectedButton = (JButton) source;
					selectedShipName = shipName[shipNum];
				}
				battleField.selectShip(myShips[shipNum]);
				battleField.selectShipSizeByIndex(shipNum);
				break;
			}
		}
	}

	private void placeShipsForAI() {
		for (int shipNum = 0; shipNum < 5; shipNum++) {
			battleField.selectShip(pcShips[shipNum]);
			battleField.selectShipSizeByIndex(shipNum);
			tryToPlacePcShip();
		}
	}
	
	private void tryToPlacePcShip(){
//		battleField.selectPosition(direction[shipNum]);
//		Location loc = new Location(XPosition[shipNum], YPosition[shipNum]);
		
		battleField.selectPosition(ai.randomPosition());
		Location loc = ai.randomLocation();
		if(!battleField.placePcShipAt(loc)){
			tryToPlacePcShip();
		}
	}

}