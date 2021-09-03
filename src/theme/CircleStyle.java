/**
 * @author Junzhe Tony Liang
 * @author Bryanna Valdivia
 * @author Victor Nguyen
 * @version 1.0 05/04/19
 */

package theme;
import java.util.Random;

import shapes.CircleShape;
import shapes.MyShape;
/**
 * Defines a concrete class of interface StrategyInterface to format pits and stones.
 * Part of strategy pattern.
 */
public class CircleStyle implements StrategyInterface{
    private final int PIT_WIDTH = 110;
    private final int PIT_HEIGHT = 110;
    private final int MANCALA_WIDTH = 105;
    private final int MANCALA_HEIGHT = 220;
    private final int STONE_WIDTH = 10;
    private final int STONE_HEIGHT = 10;

    /**
     * Describes the look of the pit so that it will be a colored circle
     * @param pitIndex - the pit index the shape will represent
     * @param panelHeight - the height of the panel the shape will be in
     */
    @Override
    public MyShape formatPit(int pitIndex, int panelHeight) {
        CircleShape cs;
        if(pitIndex > 7) { // bottom pits (player 1)
            cs = new CircleShape(0,0,PIT_WIDTH, PIT_HEIGHT);
        }
        else if(pitIndex == 7 || pitIndex == 0) { // mancala
            cs = new CircleShape(0,panelHeight-50,MANCALA_WIDTH, MANCALA_HEIGHT);
        }
        else { // (pitIndex > 0 && pitIndex < 7) top pits (player 2)
            cs = new CircleShape(0, panelHeight-50, PIT_WIDTH, PIT_HEIGHT);
        }
        return cs;
    }

    /**
     * Describes the look of the stones so that they will be colored circles
     * @param pitIndex - the pit index the shape will represent
     * @param stoneAmount - the amount of stones to draw circles for
     */
    @Override
    public MyShape formatStones(int pitIndex, int stoneAmount) {
        Random random = new Random();

        if(pitIndex > 0 && pitIndex < 7) { // stones for top pits (player 2)
            return (new CircleShape(random.nextInt(60) + 20, random.nextInt(60) + 70, STONE_WIDTH, STONE_HEIGHT));
        } else if (pitIndex > 7) { // stones for bottom pits (player 1)
            return (new CircleShape(random.nextInt(60) + 20, random.nextInt(60) + 20, STONE_WIDTH, STONE_HEIGHT));
        } else { // (pitIndex == 0 || pitIndex == 7) mancalas
            return (new CircleShape(random.nextInt(60) + 20, random.nextInt(150) + 80, STONE_WIDTH, STONE_HEIGHT));
        }
    }

}