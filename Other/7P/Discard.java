import java.util.*;

/**Child class to discard a move.
 * @author jjf
 * 4/25/18
 */
public class Discard implements Move
{
    private Card card = null;
    private GameState game;
    private int pileSize;
    
    /**Constructor.
     * @param g GameState
     * @param size int
     */
    public Discard( GameState g, int size ) 
    {
        this.game = g;
        this.pileSize = size;
    }
    
    /**Method that discards a move.
     * @return goBack 
     */
    public GameState moved() 
    {
        ArrayList< Stack< Card > > stack = game.getPiles();
        Stack<Card> dis = game.getDiscardPile();
        card = stack.get( pileSize ).peek();
        dis.push( stack.get( pileSize ).pop() );
        
        GameState goBack = new GameState( game.getDrawPile(), 
                                               stack, dis );
        
        return goBack;
    }
}