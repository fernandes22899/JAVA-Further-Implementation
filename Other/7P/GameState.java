import java.util.*;

/**
 * GameState class for Aces Up. 
 * 
 * This is a real skeleton containing only a couple of signatures needed
 * for producing consistent output for testing.
 * 
 * A major decision on your part is between:
 *   a. Actually copying state variables: the 6 card piles.
 *   b. Defining an incremental state definition:
 *        Given the starting piles, just save state change information
 *        -- the Move made.
 *
 * @author rdb sna4
 *
 */
public class GameState
{
    // rdb note: 
    //   If you implement by saving information about the piles, do NOT 
    //      blindly copy them. 
    //   We first copied the Pile objects. This turned out to be
    //    a bad idea; the Pile objects include CardStack objects, which
    //    also needed to be copied. CardStack objects extend Stack<Card>,
    //    but they add in lots of graphics behavior and overhead. We don't
    //    need any of the graphics as we build a play tree with 
    //    upto 5000+ nodes all of which have to copy all 6 of the Piles and
    //    all of which become Java graphics components.
    //    It is significantly faster to use Stack<Card>.
    //   Even though the Card objects are also graphics objects, there are
    //     never more than 52 of them no matter how many nodes there are int
    //     the tree.
    
    //------------------------- class variables ----------------------------
    
    //------------------------ instance variables --------------------------
    
    ////////////////////////////////////////////////////
    // You need not use these variables. They are here only
    // to insure that the code compiles.
    //
    private int _drawSize = 0;
    private int[] _pileSizes = { 0, 0, 0, 0 };
    private int _discardSize = 0;
    
    //---------- If you explicitly store card piles, you should declare them
    //---------- like this:
    private Stack<Card>    _drawPile;
    private ArrayList< Stack<Card> >  _piles;
    private Stack<Card>    _discardPile;
    
    
    //-------------------- constructor(s) --------------------------------
    /**
     * Constructor: GameState(drawPile, piles, discardPiles). 
     * @param drawPile Stack<Card>
     * @param piles ArrayList< Stack<Card> >
     * @param discardPile  Stack<Card>
     */
    public GameState( Stack<Card> drawPile, 
                     ArrayList< Stack<Card> > piles, 
                     Stack<Card> discardPile ) 
    {
        setDrawPile( drawPile );
        setPiles( piles );
        setDiscardPile( discardPile );
    }
    
    
    //--------------- other methods --------------------------------------
    /**
     * copyStack method. 
     * @param stack Stack<Card> 
     * @return newStack
     * */
    Stack<Card> copyStack( Stack<Card> stack )
    {
        Stack<Card> newStack = new Stack<Card>();
        for( int i = 0; i < stack.size(); i++ ) 
        {
            newStack.push( stack.get( i ) );
        }
        return newStack;
    }
    /**
     * set  draw pile with reference for @param drawPile.
     * @param drawPile Stack<Card>
     */
    public void setDrawPile( Stack<Card> drawPile ) 
    {
        _drawPile =  copyStack( drawPile );
        _drawSize = drawPile.size();
    }
    
    /**
     * return draw pile of this state.
     * @return drawPile
     */
    public Stack<Card> getDrawPile()
    {
        return copyStack( _drawPile );
    }
    
    /**
     * set piles araay of this GameState.
     * @param piles ArrayList< Stack<Card> >
     */
    public void setPiles( ArrayList< Stack<Card> > piles ) 
    {
        _piles = new ArrayList< Stack<Card> >();
        for( int i = 0; i < piles.size(); i++ ) 
        {
            _piles.add( copyStack( piles.get( i ) ) );
            _pileSizes[i] = _piles.get( i ).size();
        }
    }
    
    /**
     * arrayList. 
     * @return piles on desk
     */
    public ArrayList< Stack<Card> > getPiles() 
    {
        ArrayList< Stack<Card> > piles =  new ArrayList< Stack<Card> >();
        for( int i = 0; i < _piles.size(); i++ ) 
        {
            piles.add( i, copyStack( _piles.get( i ) ) );
        }
        return piles;
    }
    
    /**
     * sets pile of discarded cards.
     * @param discardPile Stack<Card>
     */
    public void setDiscardPile( Stack<Card> discardPile ) 
    {
        _discardPile = copyStack( discardPile );
        _discardSize = discardPile.size();
    }
    
    /**
     * gets pile of discarded cards.
     * @return piles of discarded cards
     */
    public Stack<Card> getDiscardPile() 
    {
        return  copyStack( _discardPile );
    }
    
    
    //----------------------- toString() -----------------------------------
    /**
     * Returns a count of cards in each pile separated by spaces. 
     * @return String
     */
    public String toString()
    {
        return "Piles["  
            + _drawSize + " " 
            + _pileSizes[ 0 ] + " " 
            + _pileSizes[ 1 ] + " " 
            + _pileSizes[ 2 ] + " " 
            + _pileSizes[ 3 ] + " "
            + _discardSize + "]";
    }
}
