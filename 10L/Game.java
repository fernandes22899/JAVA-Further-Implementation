//------------------------- Game.java -------------------------
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.event.*;

/**
 * Game.java - skeleton implementation of a solitaire game.
 * 
 * @author rdb
 * March 2009
 * mlb 10/09: new cards , new shuffle, new baseDeck
 */

public class Game
{
    //----------------------- class variables --------------------------
    static int   seed = 0;

    //----------------------- instance variables -----------------------    
    private CardStack       _drawPile = null; 
    private CardStack       _discards = null;
    private JPanel          _parent;
    private ArrayList<Card> _baseDeck = null;
    private boolean         _showAll = false;
    
    Random random;
    //---------------------- constructor ------------------------------
    /**
     * Construct a game to go into a parent.
     * @param parent JPanel
     */
    public Game( JPanel parent )
    {      
        //----- positioning variables
        int discardX  = 60,  discardY  = 140;
        int drawPileX = 60,  drawPileY = 40;
        
        _parent = parent;    
        
        createDeck();  // make the actual cards in _baseDeck
        
        // create 2 stacks: one for the draw pile, one for discards
        _drawPile = new CardStack( _parent, drawPileX, drawPileY );
        _discards = new CardStack( _parent, discardX, discardY );       
        
        //////////////////////////////////////////////////////////////
        // Use setXOffset and setYOffset methods of CardGroup (and so
        //   also of CardStack) in order to display cards from _discards
        //   and _drawPile collections to clearly show which card each is,
        //   but also don't take up so much of the screen space that we
        //    can't see all the cards.
        // We have more width than height, key offset for visibility
        //   in this problem is the x offset. The y offset is mainly 
        //   for visual variation. 
        // Make the y-offset for _drawPile 0, and just for variety, 
        //   something else for _discards.
        ///////////////////////////////////////////////////////////////
        _drawPile.setYOffset( 0 );
        _drawPile.setXOffset( 12 );
        
        _discards.setYOffset( 3 );
        _discards.setXOffset( 12 );
        
        //////////////////////////////////////////////////////////////
        // create a Random variable for the shuffle method and assign it
        //    to an instance variable.
        //////////////////////////////////////////////////////////////
        random = new Random();
        
        // play the "game", which in this case is just drawing cards
        newGame();
    }
    
    //----------------------------- update() ----------------------
    /**
     * Update the display components as needed.
     */
    public void update()
    {
        // show all cards on the _discards stack 
        _discards.displayCards( -1, true );
        
        if ( _showAll )
            _drawPile.displayCards( -1, true );
        else
            // show one card on the draw pile
            _drawPile.displayCards( 1, true );
        
        _parent.repaint();    
    }
    
    //------------------ showDeck() ----------------------------------
    /**
     * all cards in deck should display face side, rather than back.
     */
    public void showDeck()
    {
        _drawPile.displayCards( -1, true );
        _parent.repaint();
        _showAll = true;
    }
    //------------------ hideDeck() ----------------------------------
    /**
     * all cards in the deck should display their back side.
     */
    public void hideDeck()
    {
        _drawPile.displayCards( -1, false );
        _parent.repaint();
        _showAll = false;
    }
    
    //------------------------- drawCard() ----------------------------
    /**
     * pop the top card from the _drawPile and push it onto the _discards
     *      stack and turn its face up.
     * @return String the name of the card.
     */
    public String drawCard()
    {
        String msg = null;
        ////////////////////////////////////////////////////////////////
        //
        // To draw a card, you need to pop it from the _drawPile 
        //   and push it onto the _discards pile.
        // Of course, check if it is empty.
        // If the draw pile stack is empty, this method should return
        //   a non-null message -- indicating the game is over.
        ///////////////////////////////////////////////////////////////
        if( _drawPile != null )
          _discards.push( _drawPile.pop() );
        else
          msg = "Game is Over";
        
        update();
        return msg;
    }
    //------------------------ newGame() -----------------------------
    /**
     * restore the current deck to its original state with all cards on
     * the _drawPile. This does not automatically re-shuffle.
     */
    public void newGame()
    {
        _discards.clear();
        deckToDrawPile( _baseDeck );
        update();
    }
       
    //------------------------ shuffle( ) -----------------------------
    /**
     * shuffle the cards and start a new game. 
     * Shuffle by creating a loop that generates 2 random integers from 
     * 0 to 51 and then swaps the two
     * cards at those positions in _baseDeck. Execute the loop 50 times.
     */
    public void shuffle()
    {
        ///////////////////////////////////////////////////////////////
        // Make sure you've created and initialized the Random variable
        //    in the constructor.
        // Here:
        // 1. make a loop that executes 50 times
        // 2. In the loop:
        //    -Use Random object to generate 2 ints in range 0 to 51.
        //     i.e., invoke nextInt( 52 ) twice. 
        //    -The 2 values identify 2 cards in _baseDeck to be swapped
        //    -swap the cards
        // 3. Invoke newGame()
        ///////////////////////////////////////////////////////////////
      int first;
      int second;
      
      for( int i = 0; i < 50; i++ )
      {
        first = random.nextInt( 52 );
        second = random.nextInt( 52 );
        
        Card swap = _baseDeck.get( first );
        Card swap2 = _baseDeck.get( second );
        _baseDeck.set( first, swap2 );
        _baseDeck.set( second, swap );
      }
      
      newGame();
    }
    
    //------------------------ createDeck() --------------------------
    /**
     * Create a "base" deck of cards. These will keep being re-used for
     * multiple game invocations.
     */
    private void createDeck()
    {
        _baseDeck = new ArrayList<Card>();
        int  cardIndex = 0;
        
        for ( Card.Suit suit: Card.Suit.values() )
        {
            //for( int i = 0; i < 13; i++ )
          for( Card.Rank rank: Card.Rank.values() )
            {
                Card card = new Card( rank, suit );
                _baseDeck.add( 0, card );
                _parent.add( card );
            }            
        }
    }
    
    //------------------------ deckToDrawPile( Card[] ) --------------
    /**
     * Copy an array of cards into the CardStack representing 
     * the draw pile.
     * @param deck ArrayList<Card>  
     */
    private void deckToDrawPile( ArrayList<Card> deck )
    {
        _drawPile.clear();   // make sure draw pile is empty

        //////////////////////////////////////////////////////////////
        // for each entry in arrayList, push it onto _drawPile
        //////////////////////////////////////////////////////////////
      for( int i = 0; i < deck.size(); i++ )
        _drawPile.push( deck.get( i ) );
        
        
    }
    
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //------------------- main: convenience test ---------------------
    /**
     * Appl invocation.
     * @param args String[]  command line arguments
     */
    public static void main( String [ ] args ) 
    {
        String imageSource = null;
        if ( args.length > 0 )
            imageSource = args[ 0 ];
        new CardLab( "CardLab from Game", imageSource );
    }
}
