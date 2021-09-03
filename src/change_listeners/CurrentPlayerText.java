/**
 * @author Junzhe Tony Liang
 * @author Bryanna Valdivia
 * @author Victor Nguyen
 * @version 1.0 05/04/19
 */

package change_listeners;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import data_model.DataModel;

/**
 * Defines a graphical text to represent the current player's turn.
 */
public class CurrentPlayerText extends JPanel implements ChangeListener {
	private DataModel dataModel;
	private String text;

	/**
	 * Constructs a CurrentPlayer text given DataModel.
	 * @param model - the DataModel the text will get information from
	 */
	public CurrentPlayerText(DataModel model) {
		dataModel = model;
		setPreferredSize(new Dimension(155, 19));
	}

	/**
	 * Displays and paints the label of current player on the window
	 * @param g - graphics object to be used to draw/paint
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		if (dataModel.getPlayerFlag()) { // true = player1
			text = "Current turn: Player A";
		} else {
			text = "Current turn: Player B";
		}

		g2.setFont(new Font("SansSeruif", Font.BOLD, 14));
		g2.setColor(Color.RED);
		g2.drawString(text, 0, 15);
	}

	/**
	 * Repaints when data model's data is changed.
	 * @param e - changeevent object to call state change
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		revalidate();
		repaint();
	}

}