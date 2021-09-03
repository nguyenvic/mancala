/**
 * @author Junzhe Tony Liang
 * @author Bryanna Valdivia
 * @author Victor Nguyen
 * @version 1.0 05/04/19
 */

package change_listeners;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import data_model.DataModel;
import shapes.MyShape;
import theme.StrategyInterface;

/**
 * Defines a panel that represents a pit that holds stones. Part of strategy
 * pattern.
 */

public class PitPanel extends JPanel implements ChangeListener {
	private ArrayList<MyShape> stones = new ArrayList<MyShape>();
	private MyShape pit;
	private int pitIndex; // the index of the pit this object will represent
	private DataModel dataModel;
	private int stoneAmount;
	private StrategyInterface style;

	/**
	 * Constructs a PitPanel that adds a mouse listener, sets style to be used,
	 * index of pit to represent, and the datamodel to refer to
	 * @param dm       - DataModel for data to be read from
	 * @param pitIndex - the index of the pit this pitpanel will represent
	 * @param si       - the style to use
	 */
	public PitPanel(DataModel dm, int pitIndex, StrategyInterface si) {
		setSize(100, 100);
		dataModel = dm;
		style = si;
		this.pitIndex = pitIndex;
		pit = style.formatPit(pitIndex, getHeight());
		addMouseListener(new MouseListeners());
	}

	private class MouseListeners extends MouseAdapter {

		public void mousePressed(MouseEvent event) {
			if (dataModel.isPlayer1Flag() && dataModel.getStillMyTurn() && pitIndex >= 8
					&& (pitIndex <= 13) && dataModel.getPits().get(pitIndex) != 0) {
				dataModel.updateOldState();
				dataModel.update(pitIndex);
			} else if ((dataModel.isPlayer1Flag() == false) && (dataModel.getStillMyTurn() == true) && (pitIndex >= 1)
					&& (pitIndex <= 6) && dataModel.getPits().get(pitIndex) != 0) {
				dataModel.updateOldState();
				dataModel.update(pitIndex);
			}
		}
	}

	/**
	 * Draws the correct amount of stones.
	 * @param g - graphics object that paints the shapes
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		stoneAmount = dataModel.stoneAmtInPit(pitIndex);

		// For the labels
		if (pitIndex > 0 && pitIndex < 7) { // top row pit labels (player 2)
			String label = "B" + Integer.toString(pitIndex);
			g2.drawString(label, 49, 30);
		} else if (pitIndex > 7) { // bottom row pit labels (player 1)
			String label = "A" + Integer.toString(pitIndex - 7);
			g2.drawString(label, 49, 140);
		}

		// For the stones
		for (int i = 0; i < stoneAmount; i++) {
			stones.add(style.formatStones(pitIndex, stoneAmount));
		}

		pit.draw(g2);

		if (stones.size() != 0) {
			for (int i = 0; i < stoneAmount; i++) {
				stones.get(i).draw(g2);
			}
		}
	}

	/**
	 * Repaints when data model's data is changed.
	 * @param e - changeevent object to call state change
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		stoneAmount = dataModel.stoneAmtInPit(pitIndex);
		repaint();
	}

}