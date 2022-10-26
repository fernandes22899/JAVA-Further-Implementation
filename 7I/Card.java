//++++++++++++++++++++++++ Card.java +++++++++++++++++++++++++++++
import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.*;
import java.awt.event.*;
import java.util.*;
import java.util.jar.*;
import java.util.zip.*;

/** 
 * Card -- models playing cards
 *       It has 2 public enums: one for the suit, one for the rank
 * 
 *       It has a framework for handling mouse events.
 *       It can keep track of the CardGroup it belongs to.
 *        
 * @author    mlb
 * 
 * 10/01/10 rdb modified to support either Ace HIGH or ACE low
 * 02/17/13 rdb removed unused reference to parent
 * 02/22/14 rdb Replaced reading card images from a directory of files
 *              with reading the images from a jar file
 *              Simplified creation of filenames; removed it from 
 *                  student responsibility.
 * 03/04/15 rdb Made AceHi or AceLo a runtime option as well as card back
 */

public class Card extends JLabel 
                  implements Comparable<Card>, MouseListener
{
    //-------------------------- class variables --------------------
    //--- package access 
    static int width = 71, height = 96;   // card size; package access
    static String  imageSource = "cards_gif.jar";  
    static boolean aceHi = true;       // set false to get Ace low
    static boolean blueBack = true;    // set false to get red back
    
    //--- private access
    private static BufferedImage backImage = null;
    private static JarFile  jarFile = null;  // rep jar file if used
    private static String[] suitChar = { "C", "D", "H", "S" };
    private static String[] rankCharAceHi = 
    { "X", "2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K", "A" };
    private static String[] rankCharAceLo = 
    { "A", "2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K", "X" };
       
    
    //---------------------- Suit enum definition --------------------
    /**
     * enum Suit used for the suit of a card.
     */
    public static enum Suit 
    { 
        CLUBS, DIAMONDS, HEARTS, SPADES 
    }
    
    //-------------------- Rank enum definition -----------------------
    /**
     * enum Rank used for the "value" of a card. 
     */
    public  enum Rank     // symbolic names for the 14 cards
    { 
        // 14 names: but only one of A_LO or A_HI can be used.
        A_LO, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, 
                            JACK, QUEEN, KING, A_HI 
    };
    
    //-------------------------- instance variables -----------------
    private Rank rank = null;
    private Suit suit = null;
    private BufferedImage faceImage;
    private CardGroup groupRef;
    private boolean _faceUp = false;
    private ArrayList<MouseListener> _listeners = null;
     
    //------------------------ Constructor --------------------------
    /**
     * A card is identified by its rank and suit.
     * 
     * @param r Rank   2, 3, ... 10, J, Q, K, A
     * @param s Suit   Hearts, Clubs, etc.
     */
    public Card( Rank r, Suit s )
    {
        rank = r;
        suit = s;
        this.setSize( width, height );
        
        addMouseListener( this );
        _listeners = new ArrayList<MouseListener>();
        
        if ( backImage == null ) // only read the backImage once
            readBackImage( imageSource );
        
        
        String cardFileName;
        if ( aceHi )   //---- Ace high
            cardFileName = getAceHiFileName( s, r );
        else           //---- Ace low 
            cardFileName = getAceLoFileName( s, r );
        
        faceImage = readCardImage( imageSource, cardFileName );             
    }
    //---------------------- getAceHiFileName ------------------------
    /**
     * Get filename for the face image from the Rank when Ace is high.
     * The file names for clubs are:
     *     c1.gif == ace
     *     c2.gif == 2
     *      ...
     *     c10.gif == 10
     *     c11.gif == jack
     *     c12.gif == queen
     *     c13.gif == king
     * Other suits are the same except the first letter is d or h or s.
     * 
     * @param s Suit   the suit for the card
     * @param r Rank   the rank for the card
     * @return String    file name for the card
     */ 
    public String getAceHiFileName( Suit s, Rank r )
    {
        String[] suitPrefix = { "c", "d", "h", "s" };        
        String[] rankSuffix = { "1", "2", "3", "4", "5", "6", "7", "8",  
            "9", "10", "11", "12", "13", "1" }; // 1st entry not used for A HI
        
        return suitPrefix[ s.ordinal() ] + rankSuffix[ r.ordinal() ] + ".gif";
    } 
    //---------------------- getAceLoFileName ------------------------
    /**
     * Get filename for the face image from the Rank when Ace is high
     * The file names for clubs are:
     *     c1.gif == ace
     *     c2.gif == 2
     *      ...
     *     c10.gif == 10
     *     c11.gif == jack
     *     c12.gif == queen
     *     c13.gif == king
     * Other suits are the same except the first letter is d or h or s.
     * 
     * @param s Suit   the suit for the card
     * @param r Rank   the rank for the card
     * @return String     file name for the card
     */ 
    public String getAceLoFileName( Suit s, Rank r )
    {
        String[] suitPrefix = { "c", "d", "h", "s" };        
        String[] rankSuffix = { "1", "2", "3", "4", "5", "6", "7", "8",  
            "9", "10", "11", "12", "13", "1" };   // last index not used
        
        return suitPrefix[ s.ordinal() ] + rankSuffix[ r.ordinal() ] + ".gif";
    } 
    //---------------------- readCardImage ----------------------------
    /**
     * Read an image of a card from a jar file.
     * @param source String    the data source
     * @param cardName String  the file name of desired image
     * @return BufferedImage the image returned.
     */
    private BufferedImage readCardImage( String source, String cardName )
    {
        return readImageFromJar( source, cardName );
    }
    //---------------------- readImageFromJar -------------------------
    /**
     * Read an image of a card from a jar file.
     * @param source String    the data source
     * @param cardName String  the file name of desired image
     * @return BufferedImage
     */
    private BufferedImage readImageFromJar( String source, String cardName )
    {
        //////////////////////////////////////////////////////////////
        // A jar file is a single "archive" file that contains 
        // compressed versions of 1 or more separate files. It is very
        // useful (and pretty easy) to read files directly from the 
        // jar file, rather than requiring the user to "unjar" the files
        //
        // There are basically 4 steps in the process. 
        // All these steps need to be done inside a try-catch block
        //   that catches IOExceptions.

        /////////////////////////////////////////////////////////////
        BufferedImage cardImage = null;  // initialize return to null

        try 
        {
            // 1. create JarFile if not already done
            jarFile = new JarFile( source );
            
            // 2. get ZipEntry for "cardName" 
            ZipEntry cardEntry = jarFile.getEntry( cardName );
            
            // 3. get an InputStream object from the jarFile for the
            //    ZipEntry in step 2.
            InputStream jarIn = jarFile.getInputStream( cardEntry );
            
            // 4. Use the static read method of ImageIO to read the 
            //    input stream as a BufferedImage object:
            cardImage =  ImageIO.read( jarIn );
        }
        catch ( IOException ex )
        {
            System.err.println( "Card image error: " + ex.getMessage() );
        }
                  
        return cardImage;
    }

    //---------------------- readCardImage ----------------------------
    /**
     * Get back image for all cards; you can choose blue or red backs!
     * @param source String    the data source
     */ 
    public void readBackImage( String source )
    {
        String backImageFile;
        if ( blueBack )
            backImageFile = "b1fv.gif";  // blue back
        else
            backImageFile = "b2fv.gif";  // red back
        
        backImage = readCardImage( source, backImageFile );
    }
    //--------------------- setFaceUp( boolean ) --------------------
    /**
     * set the face up status of the card.
     * 
     * @param up boolean  if true set face of card facing up 
     */ 
    public void setFaceUp( boolean up )
    {
        _faceUp = up;   
    }   
    //--------------------- getFaceUp() --------------------
    /**
     * get the face up status of the card.
     * 
     * @return boolean  if true set face of card facing up 
     */ 
    public boolean getFaceUp()
    {
        return _faceUp;   
    }   
    //----------------------- getSuit() -------------------------
    /**
     * return the suit of this card.
     * 
     * @return Suit of the card
     */ 
    public Suit getSuit() 
    {
        return this.suit;
    }  
    //--------------------- getRank ------------------------------
    /**
     * return the rank of this card.
     * @return Rank  of the card
     */ 
    public Rank getRank() 
    {
        return rank;
    }   
    //--------------------- setGroup ------------------------
    /**
     * set the stack containing this card.
     * 
     * @param g CardGroup   
     */ 
    public void setGroup( CardGroup g ) 
    {
        this.groupRef = g;
    }
    
    //--------------------- getGroup ------------------------
    /**
     * return a reference to the CardGroup containing this card.
     * 
     * @return CardGroup    the current stack
     */ 
    public CardGroup getGroup() 
    {
        return this.groupRef;
    }
    //-------------------- paintComponent ------------------------
    /**
     * paintComponent draws the card as either face up or face down.
     * 
     * @param brush java.awt.Graphics 
     */ 
    public void paintComponent( java.awt.Graphics brush )
    {
        super.paintComponent( brush ); 
        Graphics2D brush2 = (Graphics2D) brush;
        if ( _faceUp )
            brush2.drawImage( faceImage, 0, 0, null );
        else
            brush2.drawImage( backImage, 0, 0, null );
    }
    
    //++++++++++++++++++++ mouse methods  ++++++++++++++++++++++++++++++
    /**
     * All the mouse methods just pass their event on to any listeners.
     * @param e MouseEvent
     */ 
    public void mouseClicked( MouseEvent e ) 
    { 
        for ( MouseListener ml: _listeners )
            ml.mouseClicked( e );
    }
    /**
     * All the mouse methods just pass their event on to any listeners.
     * @param e MouseEvent
     */ 
    public void mousePressed( MouseEvent e ) 
    { 
        for ( MouseListener ml: _listeners )
            ml.mousePressed( e );
    }
    
    /**
     * All the mouse methods just pass their event on to any listeners.
     * @param e MouseEvent
     */ 
    public void mouseReleased( MouseEvent e )
    { 
        for ( MouseListener ml: _listeners )
            ml.mouseReleased( e );
    }
    
    /**
     * All the mouse methods just pass their event on to any listeners.
     * @param e MouseEvent
     */ 
    public void mouseEntered( MouseEvent e ) 
    { 
        for ( MouseListener ml: _listeners )
            ml.mouseEntered( e );
    }
    
    /**
     * All the mouse methods just pass their event on to any listeners.
     * @param e MouseEvent
     */ 
    public void mouseExited( MouseEvent e ) 
    { 
        for ( MouseListener ml: _listeners )
            ml.mouseExited( e );
    }       
    
    //-------------------------- toString -------------------------
    /**
     * toString() -- a very short (2-character) string rep for the 
     *               card. This was inspired by code created by JB
     * @return String  representation of the object
     */ 
    public String toString() 
    {
        if ( aceHi )
            return rankCharAceHi[ rank.ordinal() ] + suitChar[ suit.ordinal() ];
        else
            return rankCharAceLo[ rank.ordinal() ] + suitChar[ suit.ordinal() ];
    }
    //---------------------- addListener( MouseListener ) -----------------
    /**
     * Add a MouseListener to this card.
     * @param ml MouseListener
     */
    public void addListener( MouseListener ml )
    {
        _listeners.add( ml );
    }
    //--------------------- removeListener( MouseListener ) -----------------
    /**
     * Remove a MouseListener from this card.
     * @param ml MouseListener
     */
    public void removeListener( MouseListener ml )
    {
        _listeners.remove( ml );
    }
    //--------------------- compareTo ------------------------------
    /**
     * compareTo uses the Rank as the major comparison, the Suit
     * as a secondary one.
     * @param o Card  Card to be compared with.
     * @return int   <0 , ==, >0
     */ 
    public int compareTo( Card o ) 
    {
        if ( this.rank == o.rank )
            return this.suit.ordinal() - o.suit.ordinal();
        else
            return this.rank.ordinal() - o.rank.ordinal();
    }
    
    //----------------------- equals ---------------------------
    /**
     * equals uses the Rank and Suit components..
     * @param o Card  object to be compared to this
     * @return boolean  true if equals
     */ 
    public boolean equals( Card o )
    {
        return compareTo( o ) == 0;
    }
    
    //------------------- main unit test ---------------------------
    /**
     * Unit test.
     * @param args String[]  command line arguments
     */
    public static void main( String[] args )
    {
        imageSource = "cards_gif.jar";
        //////////////////////////////////////////////////////
        // Edit test constructors to use Rank enum constants
        //////////////////////////////////////////////////////
        Rank rank;
        Suit suit;
        // Create Heart Card with rank, 8:
        //     8 is a 10 if Ace is High
        //     8 is a 9 if Ace is Low
        //rank = 8;
        rank = Rank.TEN;
        suit = Suit.HEARTS;
        
        Card c1 = new Card( rank, suit );
        System.out.println( rank + ", " + suit +  " --- " + c1 );
        c1.setFaceUp( true );
        
        // Create a Space Card with rank 12,
        //     11 is either a Queen or King
        //rank = 11;
        rank = Rank.KING;
        suit = Suit.SPADES;
        
        Card c2 = new Card( rank, suit ); 
        System.out.println( rank + ", " + suit +  " --- " + c2 );
        c2.setFaceUp( true );
        /****/
        
        //------ graphical test -------------------
        JFrame f = new JFrame();
        f.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        f.setLayout( null );
        
        c1.setLocation( 50, 50 );
        f.add( c1 );
        c2.setLocation( 200, 50 );
        f.add( c2 );
        
        f.setSize( 600, 200 );
        f.setVisible( true );      
    }   
}
