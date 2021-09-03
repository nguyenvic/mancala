/**
 * @author Junzhe Tony Liang
 * @author Bryanna Valdivia
 * @author Victor Nguyen
 * @version 1.0 05/04/19
 */

package shapes;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Random;

/**
 * Define a concrete class of interface MyShape to draw stones, pits, and mancalas.
 */
public class SquareShape implements MyShape{

    private int x;
    private int y;
    private int width;
    private int height;
    private Random random;
    private final int STONE_WIDTH = 10;
    private final int MANCALA_WIDTH = 105;
    private final int PIT_WIDTH = 110;

    /**
     * Constructs a circle shape.
     * @param x - x location of shape
     * @param y - y location of shape
     * @param width - width of the square
     * @param height - height of the square
     */
    public SquareShape(int x, int y, int width, int height) {
        random = new Random();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Describes how to draw and color the shape.
     * @param g2 - Graphics object used to paint
     */
    public void draw(Graphics2D g2) {
		Color color;
		float r = random.nextFloat();
		float g = random.nextFloat();
		float b = random.nextFloat();

		Rectangle2D.Double pit = new Rectangle2D.Double(x, y, width, height);
		if (width == STONE_WIDTH) {
			color = new Color(r, g, b);
			g2.setColor(color);
			g2.fillRect(x, y, width, height);
		} else if (width == PIT_WIDTH) {
			g2.setColor(Color.BLUE);
			g2.fillRect(x, y, width, height);
		} else if (width == MANCALA_WIDTH) {
			g2.setColor(Color.WHITE);
			g2.fillRect(x, y, width, height);
		}

		g2.setColor(Color.BLACK);
		g2.draw(pit);
    }

}