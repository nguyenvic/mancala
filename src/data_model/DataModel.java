/**
 * @author Junzhe Tony Liang
 * @author Bryanna Valdivia
 * @author Victor Nguyen
 * @version 1.0 05/04/19
 */

package data_model;
import java.util.ArrayList;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Models the Model in MVC - stores the data for the program and provides
 * accessors and mutators
 *
 */
public class DataModel {
	private ArrayList<Integer> pits; // an array of integers that represent stones where index=pits
	private ArrayList<ChangeListener> listeners;
	private ArrayList<Integer> oldState;
	private boolean player1Flag; // true for P1
	private boolean stillMyTurn;
	private int redoCounter;
	private int initialStoneAmt;
	private int winner;
	boolean gameOn = false;

	/**
	 * Constructs the data structures to store necessary program data
	 */
	public DataModel() {
		pits = new ArrayList<Integer>();
		initialStoneAmt = 0;
		listeners = new ArrayList<ChangeListener>();
		oldState = new ArrayList<Integer>();
		player1Flag = true;
		stillMyTurn = true;
		redoCounter = 0;
		winner = -1;
	}

	/**
	 * Connects the listeners to the Model
	 * @param c is the change listener
	 */
	public void attach(ChangeListener c) {
		listeners.add(c);
	}

	/**
	 * Updates the data appropriately depending on the pit and its data.
	 * @param pitIndex is the index of the pit - the pits are in an ArrayList
	 *                 structure
	 */
	public void update(int pitIndex) {
		int currentPit = pitIndex + 1;

		if (currentPit > 13) {
			currentPit = 0;
		}

		if (pitIndex > 7) { // Player 1
			int stoneAmount = pits.get(pitIndex);
			int i = currentPit;
			pits.set(pitIndex, 0);
			while (stoneAmount > 0) {
				pits.set(i, pits.get(i) + 1);
				if (i == 13) {
					i = (i % 13);
				} else if (i == 6) {
					i = i + 2;
				} else {
					i = (i % 13) + 1;
				}
				stoneAmount--;
			}

			int landingPit = i - 1;

			if (landingPit == 0) { // for if player1's last marble goes into their own mancala,
				// notify controller for P1 to take another turn
				stillMyTurn = true;
				player1Flag = true;
			} else if (landingPit > 7) { // if player 1's last marble land in their own pit
				if (pits.get(landingPit) == 1) { // end in player1's own empty pit
					int steal = landingPit - ((landingPit - 7) * 2);
					int stolen = pits.get(steal);
					pits.set(steal, 0);
					int landingPitValue = pits.get(landingPit);
					pits.set(landingPit, 0);
					pits.set(0, pits.get(0) + stolen + landingPitValue);
					stillMyTurn = false;
					player1Flag = true;
				} else { // end in player 1's pit with stone(s)
					stillMyTurn = false;
					player1Flag = true;
				}
			} else {
				stillMyTurn = false;
				player1Flag = true;
			}

		} else if (pitIndex < 7) {  // Player 2
			int stoneAmount = pits.get(pitIndex);
			int i = currentPit;
			pits.set(pitIndex, 0);

			while (stoneAmount > 0) {
				pits.set(i, pits.get(i) + 1);
				if (i == 13) {
					i = 1;
				} else {
					i++;
				}
				stoneAmount--;
			}

			int landingPit = i - 1;

			if (landingPit == 7) { // for if player2's last marble goes into their own mancala
				// notify controller for P1 to take another turn
				stillMyTurn = true;
				player1Flag = false;
			} else if (landingPit < 7) { // if player2's last marble land in their own pits
				if (pits.get(landingPit) == 1) { // if the marble lands in an empty pit
					// end in own empty pit
					int steal = landingPit + ((7 - landingPit) * 2);
					int stolen = pits.get(steal);
					pits.set(steal, 0);
					int landingPitValue = pits.get(landingPit);
					pits.set(landingPit, 0);
					pits.set(7, pits.get(7) + stolen + landingPitValue);
					stillMyTurn = false;
					player1Flag = false;
				} else { // if marble lands in a pit with marble(s)
					stillMyTurn = false;
					player1Flag = false;
				}
			} else {
				stillMyTurn = false;
				player1Flag = false;
			}

		}

		// Checks if game needs to end
		int grabber = checkGameEnd();
		if (grabber != 0) {
			winner = determineWinner(grabber);
		}
		notifyListeners();
	}

	/**
	 * Determines if the game is over or not
	 * @return int is the code for whether the game is over and who gets to add the
	 *         stones to their mancala if it is over
	 */
	public int checkGameEnd() {
		boolean P2isEmpty = true;
		for (int j = 1; j < 7; j++) {
			if (pits.get(j) != 0) {
				P2isEmpty = false;
			}
		}
		boolean P1isEmpty = true;
		for (int k = 8; k < 14; k++) {
			if (pits.get(k) != 0) {
				P1isEmpty = false;
			}
		}
		if (P1isEmpty == false && P2isEmpty == false) {
			return 0; // game not over
		} else if (P2isEmpty) {
			return 1; // P1 gets to add remaining stones to mancala
		} else if (P1isEmpty) {
			return 2; // P2 gets to add remaining stones to its mancala
		}
		return 3; // error
	}

	/**
	 * Determines who won the game overall
	 * 
	 * @param result is who ended the game
	 * @return int is code for who won based on the total mancala count
	 */
	public int determineWinner(int result) { // 1 = P1 won, 2 = P2 won, 0 = tie
		if (result == 1) {
			int collectedStones = 0;
			for (int i = 8; i < 14; i++) {
				collectedStones += pits.get(i);
				pits.set(i, 0);
			}
			pits.set(0, pits.get(0) + collectedStones);
		}
		if (result == 2) {
			int collectedStones = 0;
			for (int i = 1; i < 7; i++) {
				collectedStones += pits.get(i);
				pits.set(i, 0);
			}
			pits.set(7, pits.get(7) + collectedStones);
		}
		int P1Mancala = pits.get(0);
		int P2Mancala = pits.get(7);

		if (P1Mancala > P2Mancala) {
			return 1;
		} else if (P1Mancala < P2Mancala) {
			return 2;
		} else {
			return 0;
		}

	}

	/**
	 * Resets the redo counter back to zero when it's the next person's turn because
	 * each turn can have up to 3 redos.
	 */
	public void resetRedoCounter() {
		redoCounter = 0;
	}

	/**
	 * Increases the redo counter because a player can only redo up to 3 times per
	 * turn.
	 */
	public void incrementRedoCounter() {
		redoCounter++;
	}

	/**
	 * Sets the pits to the previous state after a redo is called.
	 */
	public void redoPits() {
		for (int i = 0; i < pits.size(); i++) {
			pits.set(i, oldState.get(i));
		}
//        notifyListeners(); do we need this?
	}

	/**
	 * Updates the "previous" state in case a redo is called - it would have the
	 * most previous state saved.
	 */
	public void updateOldState() {
		for (int i = 0; i < pits.size(); i++) {
			oldState.set(i, pits.get(i));
		}
	}

	/**
	 * Sets whether it is still the current player's turn or not.
	 * @param b is either true or false
	 */
	public void setStillMyTurn(boolean b) {
		stillMyTurn = b;
		notifyListeners();
	}

	/**
	 * Gets the boolean to know if it is still the current player's turn.
	 * @return stillMyTurn is a boolean saying if it is still the current player's
	 *         turn or not
	 */
	public boolean getStillMyTurn() {
		return stillMyTurn;
	}

	/**
	 * Gets the most current previous state.
	 * @return oldState is an ArrayList of Integers of the most current previous
	 *         state
	 */
	public ArrayList<Integer> getOldState() {
		return oldState;
	}

	/**
	 * Gets the pits.
	 * @return pits is the ArrayList representing the pits
	 */
	public ArrayList<Integer> getPits() {
		return pits;
	}

	/**
	 * Gets the amount of stones from a specific pit.
	 * @param index is the index of the pit
	 * @return int is the amount of stones within the pit at the index
	 */
	public int stoneAmtInPit(int index) {
		return pits.get(index);
	}

	/**
	 * Gets if it is Player A's turn or not.
	 * @return player1Flag is a boolean to tell if it is player A's turn or not
	 */
	public boolean isPlayer1Flag() {
		return player1Flag;
	}

	/**
	 * Sets who's turn it is.
	 * @param player1Flag is a boolean for if it is Player A's or Player B's turn
	 */
	public void setPlayerFlag(boolean playerFlag) {
		this.player1Flag = playerFlag;
		notifyListeners();
	}

	/**
	 * Gets the redoCounter.
	 * @return redoCounter is the int counting how many redos have happened for the
	 *         current turn
	 */
	public int getRedoCounter() {
		return redoCounter;
	}

	/**
	 * Sets the redo counter.
	 * @param redoCounter is the int counting how many redos have happened for the
	 *                    current turn
	 */
	public void setRedoCounter(int redoCounter) {
		this.redoCounter = redoCounter;
	}

	/**
	 * Sets the number of stones in the pits.
	 * @param s is the amount of stones per pit
	 */
	public void setStones(int s) {
		initialStoneAmt = s;
		for (int i = 0; i < 14; i++) {
			if (i == 0 || i == 7) {
				pits.add(i, 0);
				oldState.add(i, 0);
			} else {
				pits.add(i, initialStoneAmt);
				oldState.add(i, initialStoneAmt);
			}
		}

	}

	/**
	 * Sets the gameOn boolean for if the game is still going.
	 * @param v is true or false
	 */
	public void setGameOn(boolean v) {
		gameOn = v;
	}

	/**
	 * Gets whether the game is continuing or not.
	 * @return gameOn is a boolean for if the game is still continuing
	 */
	public boolean getGameOn() {
		return gameOn;
	}

	/**
	 * Gets if it is Player A's turn.
	 * @return player1Flag is the boolean for if it is Player A's turn or not
	 */
	public boolean getPlayerFlag() {
		return player1Flag;
	}

	/**
	 * Notifies that the data has been changed.
	 */
	public void notifyListeners() {
		ChangeEvent event = new ChangeEvent(this);
		for (ChangeListener listener : listeners) {
			listener.stateChanged(event);
		}
	}

	/**
	 * Gets the winner.
	 * @return int represents the winner
	 */
	public int getWinner() {
		return winner;
	}
}