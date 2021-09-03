/**
 * @author Junzhe Tony Liang
 * @author Bryanna Valdivia
 * @author Victor Nguyen
 * @version 1.0 05/04/19
 */

package change_listeners;
import javax.swing.JButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import data_model.DataModel;

/**
 * Models a button with a change listener to switch player turns for the UI
 *
 */
public class EndTurnButton extends JButton implements ChangeListener {
	private DataModel dataModel;

	/**
	 * Constructs a button the may be used when the current player's turn is over.
	 * Is a visual representation for the player's turn taking.
	 * @param model is the data model
	 */
	public EndTurnButton(DataModel model) {
		super("End Turn");
		dataModel = model;
		addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				boolean temp = dataModel.getPlayerFlag();
				dataModel.setPlayerFlag(!temp);
				dataModel.resetRedoCounter();
				dataModel.setStillMyTurn(true);
			}
		});

	}

	/**
	 * Controls when the button is active. Checks the changed event to check if the
	 * button can be used.
	 * @param arg0 is the change event
	 */
	@Override
	public void stateChanged(ChangeEvent arg0) {
		if (dataModel.getStillMyTurn()) { // when you still have turns, you cannot end the turn
			setEnabled(false);
		} else {
			setEnabled(true); // when you are out of turns you need to click the end turn button
		}
	}

}