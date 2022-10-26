import java.util.*;

/**Child class that draws a move.
  * @author jjf
  * 4/25/18
  */
public class Draw implements Move
{
    private Card card = null;
    private GameState game;
    
    /**Constructor.
     * @param g GameState
     */
    public Draw( GameState g ) 
    {
        this.game = g;
    }
    
    /**Method that draws card back.
     * @return goBack
     */
    public GameState moved() 
    {
        Stack<Card> stack = game.getDrawPile();
        ArrayList< Stack<Card> > arr = game.getPiles();
        
        for( int i = 0; i < arr.size(); i++ ) 
        {
            arr.get( i ).add( stack.pop() );
        }
        
        GameState goBack = new GameState( stack, arr, 
                                               game.getDiscardPile() );
        
        return goBack;
    }
}