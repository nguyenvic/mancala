/**
 * @author Junzhe Tony Liang
 * @author Bryanna Valdivia
 * @author Victor Nguyen
 * @version 1.0 05/04/19
 */

package theme;
import shapes.MyShape;

/**
 * Interface for strategy pattern for the look of the game
 */
public interface StrategyInterface {
    /**
     * Formats the look of the pits.
     * @param pitIndex - the pit index the shape will represent
     * @param panelHeight - the height of the panel the pit will be in
     */
    MyShape formatPit(int pitIndex, int panelHeight) ;
    /**
     * Formats the look of the stones.
     * @param pitIndex - the pit index the shape will represent
     * @param stoneAmount - the amount of stones that needs to be formatted and drawn
     */
    MyShape formatStones(int pitIndex, int stoneAmount);
}