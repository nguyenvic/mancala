/**
 * @author Junzhe Tony Liang
 * @author Bryanna Valdivia
 * @author Victor Nguyen
 * @version 1.0 05/04/19
 */

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.*;

import change_listeners.CurrentPlayerText;
import change_listeners.EndTurnButton;
import change_listeners.PitPanel;
import change_listeners.PopUpWindow;
import data_model.DataModel;
import theme.CircleStyle;
import theme.SquareStyle;
import theme.StrategyInterface;
import utility.ErrorPopUp;

public class MancalaTest {
	private static final int FRAME_WIDTH = 915;
	private static final int FRAME_HEIGHT = 450;
	public static StrategyInterface style = null;

	public static void main(String[] args) {
		// GUI to get user preferences
		JFrame prompt = new JFrame();
		prompt.setResizable(false);

		JLabel quest = new JLabel("Would you like 3 or 4 stones per pit? :");
		TextField question = new TextField();
		question.setColumns(5);
		JRadioButton style1 = new JRadioButton("Party");
		JRadioButton style2 = new JRadioButton("Unicorn Square");
		prompt.setLayout(new FlowLayout());
		DataModel dataModel = new DataModel();

		PopUpWindow myPopUp = new PopUpWindow(dataModel);

		myPopUp.setVisible(false);

		dataModel.attach(myPopUp);

		JButton submit = new JButton("Ready to go!");
		prompt.add(quest);
		prompt.add(question);
		prompt.add(style1);
		prompt.add(style2);
		prompt.add(submit);
		prompt.pack();
		prompt.setLocationRelativeTo(null);
		prompt.setVisible(true);

		style1.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				style2.setSelected(false);
			}
		});
		style2.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				style1.setSelected(false);
			}
		});

		submit.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (Integer.parseInt(question.getText()) >= 5) {
					ErrorPopUp errorPop = new ErrorPopUp();
					errorPop.setVisible(true);
				} else {
					if (style2.isSelected()) { // squares
						style = new SquareStyle();
					} else if (style1.isSelected()) { // circles
						style = new CircleStyle();
					}

					dataModel.setStones(Integer.parseInt(question.getText()));
					dataModel.setGameOn(true);
					prompt.setVisible(false);

					JFrame frame = new JFrame();
					frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
					frame.setResizable(false);
					frame.setLayout(new BorderLayout());

					JPanel allPits;
					allPits = new JPanel();
					allPits.setLayout(new GridLayout(2, 6, 0, 0));

					JPanel board = new JPanel();
					board.setLayout(new BorderLayout());
					PitPanel mancalaA = new PitPanel(dataModel, 0, style);
					PitPanel mancalaB = new PitPanel(dataModel, 7, style);
					mancalaB.setPreferredSize(new Dimension(110, 220));
					mancalaA.setPreferredSize(new Dimension(110, 220));

					board.add(mancalaB, BorderLayout.WEST);
					board.add(allPits, BorderLayout.CENTER);
					board.add(mancalaA, BorderLayout.EAST);
					JPanel biggerPanel = new JPanel();
					biggerPanel.setLayout(new BorderLayout());
					JLabel manLabelA = new JLabel("Mancala A", JLabel.CENTER);
					JLabel manLabelB = new JLabel("Mancala B", JLabel.CENTER);
					JLabel playerA = new JLabel("Player A", JLabel.CENTER);
					JLabel playerB = new JLabel("Player B", JLabel.CENTER);

					JPanel mancalaAPanel = new JPanel();
					mancalaAPanel.setLayout(new BorderLayout());
					mancalaAPanel.add(mancalaA, BorderLayout.CENTER);
					mancalaAPanel.add(manLabelA, BorderLayout.SOUTH);

					JPanel mancalaBPanel = new JPanel();
					mancalaBPanel.setLayout(new BorderLayout());

					mancalaBPanel.add(mancalaB, BorderLayout.CENTER);
					mancalaBPanel.add(manLabelB, BorderLayout.SOUTH);

					biggerPanel.add(playerB, BorderLayout.NORTH);
					biggerPanel.add(board, BorderLayout.CENTER);
					biggerPanel.add(playerA, BorderLayout.SOUTH);
					biggerPanel.add(mancalaAPanel, BorderLayout.EAST);
					biggerPanel.add(mancalaBPanel, BorderLayout.WEST);
					frame.add(biggerPanel, BorderLayout.CENTER);

					int[] pitOrder = { 6, 5, 4, 3, 2, 1, 8, 9, 10, 11, 12, 13 };
					int pitOrderNum = 0;

					ArrayList<PitPanel> pits = new ArrayList<PitPanel>();
					for (int i = 0; i < 12; i++) {
						pits.add(new PitPanel(dataModel, pitOrder[pitOrderNum], style));
						pitOrderNum++;
					}

					for (int i = 0; i < 12; i++) {
						allPits.add(pits.get(i));
					}
					
					for (int i = 0; i < 12; i++) {
						dataModel.attach(pits.get(i));
					}
					dataModel.attach(mancalaA);
					dataModel.attach(mancalaB);

					JPanel buttonPanel = new JPanel();
					buttonPanel.setLayout(new FlowLayout());
					JButton undoBtn = new JButton("Undo!");
					undoBtn.addActionListener(new java.awt.event.ActionListener() {
						@Override
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (dataModel.getRedoCounter() == 3) {
								undoBtn.setEnabled(false);
								dataModel.setStillMyTurn(false);
							} else {
								dataModel.redoPits();
								dataModel.setStillMyTurn(true);
								dataModel.incrementRedoCounter();
							}
						}
					});
					EndTurnButton button2 = new EndTurnButton(dataModel);
					CurrentPlayerText currentPlayerText = new CurrentPlayerText(dataModel);

					dataModel.attach(button2);
					dataModel.attach(currentPlayerText);

					buttonPanel.add(button2);
					buttonPanel.add(currentPlayerText);
					buttonPanel.add(undoBtn);

					frame.add(buttonPanel, BorderLayout.NORTH);
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				}
			}
		});
	}
}
