/**
 * @author Junzhe Tony Liang
 * @author Bryanna Valdivia
 * @author Victor Nguyen
 * @version 1.0 05/04/19
 */

package shapes;
import java.awt.Graphics2D;

/**
 * Interface that requires a draw method for shapes.
 */
public interface MyShape{
    /**
     * The method should draw shapes
     * @param g2 - graphics object used to paint
     */
    void draw(Graphics2D g2);
}