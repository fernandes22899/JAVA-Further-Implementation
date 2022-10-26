//---------------------------- Card.java ------------------------
import java.awt.geom.*;
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
 */


public class Card extends JLabel 
    implements Comparable<Card>, MouseListener
{
    //-------------------------- class variables --------------------
    //--- package access 
    static int width = 71, height = 96;   // card size; package access
    static String  imageSource = "cards_gif/";
    
    //--- private access
    private static BufferedImage backImage = null;
    private static JarFile  jarFile = null;  // rep jar file if used
    
    //---------------------- Suit enum definition --------------------
    /**
     * An "enum" is a special Java feature that allows you to provide
     * symbolic names for integer values without knowing exactly
     * what the integer value is. It is easier than a ton of declarations
     * such as:
     * 
     public static final CLUBS = 0;
     public static final DIAMONDS = 1;
     etc.
     */
    
    //  symbolic names for the 4 suits
    public static enum Suit  
    { 
        CLUBS, DIAMONDS, HEARTS, SPADES 
    }
    
    /* Enum functionality features:
     * 1. Java does an automatic conversion of the symbolic value to a 
     *    String on output. For Suit these would be:
     *      "CLUBS", "DIAMONDS", "HEARTS", "SPADES"
     * 
     * 2. Each identifier in the declaration of the enum has a unique 
     *    position in the declaration and that position is called its 
     *    "ordinal" value. You can access the ordinal value of an enum 
     *    element using the "ordinal()" method (see the "shortRankString" 
     *    method). In this case,
     *        ordinal( CLUBS )    --> 0
     *        ordinal( DIAMONDS ) --> 1
     *        ordinal( HEARTS )   --> 2 
     *        ordinal( SPADES )   --> 3
     * 
     * 3. Since these enums are defined as public inside the public
     *     class, Card, the enum can be accessed outside of this class  
     *     with 
     *         Card.Suit
     *     and the values with
     *         Card.Suit.CLUBS, Card.Suit.HEARTS, etc.
     */  
    
    //-------------------- Rank enum definition -----------------------
    ///////////////////////////////////////////////////////
    // Task 1b
    //////////
    // Using the Suit enum above as a model, define a list of ids 
    //   to represent the 13 cards: 
    // If you want Ace HIGH, you want the order to be:
    //      2, 3, 4, 5, 6, 7, 8, 9, 10, Jack, Queen, King, Ace
    // If you want Ace LOW, you want the order to be:
    //      Ace, 2, 3, 4, 5, 6, 7, 8, 9, 10, Jack, Queen, King
    //
    // You cannot use numbers in an enum, so the 13 identifiers
    // for the cards must all use words. By convention, we use
    // fully capitalized words
    ///////////////////////////////////////////////////////
    
    /**
     * enum Rank gives symbolic names for the 13 cards.
     */
    public static enum Rank 
    { 
        TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE
    }   
    
    //-------------------------- instance variables -----------------   
    private Rank rank = null;
    private Suit suit = null;
    private BufferedImage faceImage;
    private CardGroup sRef;
    private boolean _faceUp = false;
    
    
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
        if ( backImage == null ) // only read the backImage once
            readBackImage( imageSource );

        //---- Ace high
        String cardFileName = getAceHiFileName( s, r );
        
        //---- Ace low 
        //String cardFileName = getAceLoFileName( s, r );
        
        faceImage = readCardImage( imageSource, cardFileName );             
    }
    
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
        String[] rankSuffix = { "2", "3", "4", "5", "6", "7", "8",  
            "9", "10", "11", "12", "13", "1" };
        
        return suitPrefix[ s.ordinal() ] + rankSuffix[ r.ordinal() ]
               + ".gif";               
    }
    /**
     * This is a phony constructor, needed so program can compile
     * while the rank variable is being converted from int to Rank.
     * 
     * @param s Suit   the card's suit
     * @param r int    the card's rank as an int
     * @return String     filename
     */
    public String getAceHiFileName ( Suit s, int r )
    {
        return null; 
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
     * @return String    file name for the card
     */     
    public String getAceLoFileName( Suit s, Rank r )
    {
        String[] suitPrefix = { "c", "d", "h", "s" };        
        String[] rankSuffix = { "1", "2", "3", "4", "5", "6", "7", "8",  
            "9", "10", "11", "12", "13" };
        
        return suitPrefix[ s.ordinal() ] + rankSuffix[ r.ordinal() ]
               + ".gif";        
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
        if ( cardName == null )
            cardName = "rj.gif";  // Joker is default image
        if ( source.matches( ".*jar" ) )
            return readImageFromJar( source, cardName );
        else
            return readImageFromFile( source + "/" + cardName );
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
        // ------------------------------------------------------------
        // 0. Delete the System.out "unimplemented" statement at the end 
        //    of this long comment
        // -----------------------------------------------------------
        // 1. Create an instance of the JarFile class, which represents
        //    an abstraction of the jar file. This object is the same
        //    for all the cards, so it can be a class (static) variable
        //    and we only need to create it once. The start of the class
        //    includes the line:
        //       private static JarFile jarFile = null; 
        //    If the value is still null, create a new JarFile object 
        //    with a single parameter that identifies jar file (the 
        //    "source" parameter to this method); assign it to "jarFile".
        // ----------------------------------------------------------
        // 2. Now we need to identify the particular "entry" in the jar
        //    file that we want to read. The JarFile class is a child
        //    of the ZipFile class, which defines its entries as 
        //    instances of the ZipEntry class. This characteristic is
        //    inherited without change by JarFile, using the "getEntry"
        //    method that takes a String parameter that is the name of
        //    of the entry (file) desired (the "cardName" parameter to
        //    this method and returns an instance of the ZipEntry class.
        // -----------------------------------------------------------
        // 3. Create an InputStream object that can read the ZipEntry.
        //    The JarFile object has a method that can do that:
        //           getInputStream( ZipEntry )
        //    Invoke that method for "jarFile" passing the ZipEntry you
        //    created in the previous step. getInputStream returns
        //    an InputStream object.
        // -----------------------------------------------------------
        // 4. The Java ImageIO class has a static method, "read" that 
        //    takes an InputStream as a parameter and assumes it refers
        //    to an image file, reads it an returns a BufferedImage
        //    object. This step is already completed.
        /////////////////////////////////////////////////////////////
        BufferedImage cardImage = null;  // initialize return to null
 
        // Delete the output statement below once you get this working
        System.out.println( "****** readImageFromJar not implemented" );
        try 
        {
            // 1. step 5a. create JarFile if not already done
            if ( jarFile == null ) // create JarFile with source as arg
                new JarFile( source );  // step 5a. <-----------
            
            // 2. step 5b. get ZipEntry for "cardName" 
            ZipEntry cardEntry = jarFile.getEntry( cardName );  // step 5b. <------
            
            // 3. step 5c. get an InputStream object from the jarFile for the
            //    ZipEntry in step 2.
            InputStream jarIn = jarFile.getInputStream( cardEntry );  // step 5c. <------
            
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
    //---------------------- readImageFromFile -------------------------
    /**
     * Read an image of a card from a plain file.
     * @param cardFileName String  the file name of desired image
     * @return BufferedImage
     */
    private BufferedImage readImageFromFile( String cardFileName )
    {
        BufferedImage cardImage = null;
        try 
        {
            cardImage = ImageIO.read( new File( cardFileName ) );            
        } 
        catch ( IOException e ) 
        { 
            System.err.println( "Card image error: " + e.getMessage() ); 
        }  
        return cardImage;
    }
    //---------------------- readBackImage ----------------------------
    /**
     * Get back image for all cards; you can choose blue or red backs.
     * @param source String    the data source
     */ 
    public void readBackImage( String source )
    {
        String backImageFile = "b1fv.gif";  // blue back
        //String backImageFile = "b2fv.gif";  // red back
        
        backImage = readCardImage( source, backImageFile );
    }
    
    //--------------------- setFaceUp( boolean ) --------------------
    /**
     * set the face up status of the card.
     * @param b boolean true => set the face up.
     */ 
    public void setFaceUp( boolean b )
    {
        _faceUp = b;   
    }   
    //----------------------- getSuit() -------------------------
    /**
     * return the suit of this card.
     * @return Suit  the card's suit
     */ 
    public Suit getSuit() 
    {
        return this.suit;
    }  
    //--------------------- getRank ------------------------------
    /**
     * return the rank of this card.
     * @return Rank the rank of this card
     */ 
    public Rank getRank() 
    {
        return rank;
    }   
    //--------------------- setCurStack ------------------------
    /**
     * set the stack containing this card.
     * @param s CardGroup   the current stack of cards.
     */ 
    public void setCurStack( CardGroup s ) 
    {
        this.sRef = s;
    }
    
    //--------------------- getCurStack ------------------------
    /**
     * return a reference to the CardGroup containing this card.
     * @return CardGroup   the current stack.
     */ 
    public CardGroup getCurStack() 
    {
        return this.sRef;
    }
    
    //-------------------- paintComponent ------------------------
    /**
     * paintComponent draws the card as either face up or face down.
     * @param brush java.awt.Graphics  the graphics brush.
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
    
    //--------------------- mouseClicked --------------------------
    /**
     * mouseClicked -- unused.
     * @param e MouseEvent  the mouse event object
     */ 
    public void mouseClicked( MouseEvent e ) 
    {
        System.out.println( "Card Clicked - " + this );      
    }
    
    //----------------------------------------------------------
    /** Unimplemented. @param e MouseEvent */
    public void mousePressed( MouseEvent e ) 
    { }
    /** Unimplemented. @param e MouseEvent */
    public void mouseReleased( MouseEvent e ) 
    { }
    /** Unimplemented. @param e MouseEvent */
    public void mouseEntered( MouseEvent e ) 
    { }
    /** Unimplemented. @param e MouseEvent */
    public void mouseExited( MouseEvent e ) 
    { }
        
    //--------------------- compareTo ------------------------------
    /**
     * compareTo uses the Rank as the major comparison, the Suit
     * as a secondary one.
     * 
     * @param o Card  object being compared to
     * @return int   <0, 0, >0
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
     * equals uses the Rank and Suit components.
     * @param o Card   object doing the comparing. 
     * @return boolean true if rank and suits match
     */ 
    public boolean equals( Card o )
    {
        return compareTo( o ) == 0;
    }
    
    //-------------------------- toString -------------------------
    /**
     * convert the Card to a string representation.
     * @return String the string representation.
     */ 
    public String toString() 
    {
        //---- Ace as a HIGH card
        //String[] rankString = { "2", "3", "4", "5", "6", "7", "8", 
            //"9", "10", "J", "Q", "K", "A" }; 
        
        return rank + " of " + suit;
    }
    
    //------------------- main: convenience unit test ----------------
    /**
     * Unit test: this version tests the Card class.
     * 
     * @param args String[]  command line arguments
     */
    public static void main( String[] args )
    {
        //////////////////////////////////////////////////////
        // Edit test constructors to use Rank enum constants
        //////////////////////////////////////////////////////
        int rank;
        Suit suit;
        // Create Heart Card with rank, 8:
        //     8 is a 10 if Ace is High
        //     8 is a 9 if Ace is Low
        rank = 8;
        suit = Suit.HEARTS;
        
        Card c1 = new Card( Rank.TEN, suit );
        System.out.println( rank + ", " + suit +  " --- " + c1 );
        c1.setFaceUp( true );
        
        // Create a Space Card with rank 12,
        //     11 is either a Queen or King
        rank = 11;
        suit = Suit.SPADES;
        
        Card c2 = new Card( Rank.KING, suit ); 
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
