/**
 * @author Junzhe Tony Liang
 * @author Bryanna Valdivia
 * @author Victor Nguyen
 * @version 1.0 05/04/19
 * 
 * Models a pop up frame for when the game ends - displays the winner
 */

package change_listeners;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import data_model.DataModel;
import java.awt.*;

/**
 * Constructs a frame connected to the model to display who won based on the
 * data
 */
public class PopUpWindow extends JFrame implements ChangeListener {
	private JLabel winnerLabel;
	private JButton exitButton;
	private DataModel dataModel;

	/**
	 * Constructs the frame shown when the game ends, displays the winner.
	 * @param d is a reference of the data model
	 */
	public PopUpWindow(DataModel d) {
		super("Winner winner chicken dinner!"); // invoke the JFrame constructor
		dataModel = d;
		setPreferredSize(new Dimension(600, 200));
		setResizable(false);
		setLayout(new BorderLayout());
		winnerLabel = new JLabel("", JLabel.CENTER);
		winnerLabel.setFont(new Font("Serif", Font.BOLD, 70));
		exitButton = new JButton("Exit Game"); // construct a JLabel

		exitButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				System.exit(0);
			}
		});

		add(winnerLabel, BorderLayout.CENTER);
		add(exitButton, BorderLayout.SOUTH);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
	}

	/**
	 * Determines if the end game pop up window is displayed or not Checks the
	 * winner after a change in the data to also display who won
	 * 
	 * @param e is the change listener event
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		int winner = dataModel.getWinner();
		switch (winner) {
			case 1: {
				winnerLabel.setText("Player A Wins!!!");
				setVisible(true);
				break;
			}
			case 2: {
				winnerLabel.setText("Player B Wins!!!");
				setVisible(true);
				break;
			}
			case 0: {
				winnerLabel.setText("It's a Tie!!!");
				setVisible(true);
				break;
			}
		}
	}
}