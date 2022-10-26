//++++++++++++++++++++++++++ Pile ++++++++++++++++++++++++++++++++++
import java.util.Stack;
/**
 * Pile.java - Encapsulate notion of a card pile; including an identifier
 *             component
 *        As implemented, the identifier should be in the range specified by 
 *             Game.PileName enum (0-5). 
 *        The constructor can take either an int or a Game.PileName value
 *             and either version of the id can be accessed using
 *             getIndex() - returns the int version
 *             getId() - returns the enum value
 * 
 * @author rdb 
 * 10/15/10
 */

public class Pile
{
    //------------------- instance variables ----------------------------
    private CardStack     cards;    // the CardStack in the pile
    private int           pileNum;  // identity of Pile based on int
    private Game.PileName pileId;   // identity of Pile based on enum
    
    //------------------- constructor -----------------------------------
    /**
     * Constructor using int to identify the Pile.
     * @param stack CardStack    collection to hold cards in the Pile.
     * @param num  int
     */
    public Pile( CardStack stack, int num )
    {
        pileNum = num;
        cards   = stack;
        // values() returns an array of the valid values for the Game.PileName
        //    enum in the order of their specification.
        Game.PileName vals[] = Game.PileName.values();
        if ( num >= 0 && num < vals.length ) // the index is valid
            pileId = vals[ num ];
        else  // probably should throw an exception
        {
            System.err.println( "Pile constructor error: " + num 
                                   + " is not a valid id for a Pile" );
        }
    }
    /**
     * Constructor using enum PileName.
     * @param stack CardStack    collection to hold cards in the Pile.
     * @param id  Game.PileName
     */
    public Pile( CardStack stack, Game.PileName id )
    {
        pileNum = id.ordinal();
        pileId  = id;
        cards   = stack;
    }
    //+++++++++++++++++++++ accessors ++++++++++++++++++++++++++++++++++++++
    //---------------- getStack() -----------------------------------------
    /**
     * Return the CardStack holding the cards in the Pile.
     * @return CardStack
     */
    public CardStack getStack()
    {
        return cards;
    }
    //----------------- getIndex() ----------------------------------------
    /**
     * Return the pile index: 0 for draw, 1-4 for "work" piles, 5 for discard.
     * @return int
     */
    public int getIndex()
    {
        return pileNum;
    }
    //----------------- getId() -------------------------------------------
    /** 
     * Return the Pile identifier for this pile.
     * @return Game.PileName
     */
    public Game.PileName getId()
    {
        return pileId;
    }
    //----------------- size() -------------------------------------------
    /**
     * The size of the Pile is the number of cards in it.
     * @return int
     */
    public int size()
    {
        return cards.size();
    }
    //----------------- peek() --------------------------------------
    /**
     * Get reference to the Card at the top of the Pile; this is 
     *   almost exclusively for display purposes.
     * @return Card
     */
    public Card peek()
    {
        return cards.get( 0 );
    }
    //----------------- get( int ) --------------------------------------
    /**
     * Get reference to the Card at the given location in the Pile; this is 
     *   almost exclusively for display purposes.
     * @param pos int
     * @return Card
     */
    public Card get( int pos )
    {
        return cards.get( pos );
    }
    //----------------- copy( Stach<Card> ) ------------------------------
    /**
     * Copy the cards from another Stack<Card> to this one.
     * @param from Stack<Card>  
     */
    public void copy( Stack<Card> from )
    {
        cards.addAll( from );
    }
    //----------------- clear ------------------------------------------
    /**
     * Delete all cards from this pile, by deleting from the underlying 
     *    CardStack.
     */
    public void clear()
    {
        cards.clear();
    }
}