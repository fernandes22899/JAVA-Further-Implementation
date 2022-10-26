//++++++++++++++++++ AEllipse.java ++++++++++++++++++++++++++++++++++
import java.awt.geom.*;
import java.awt.*;

/**
 * ARectangle.java: version 1a 
 * 
 * Extends Java's ARectangle2D.Double class, adding the capabilities to
 * set color, location, and size, to move to a specified
 * location, and to display itself on a panel.
 * 
 * This class is a modification of the SmartRectangle class from
 * Sanders and van Dam, Object-Oriented Programming in Java, Chapter 7.
 * 
 * The modifications include:
 * 1. reformatting to match CS415/416 style
 * 2. more wheels-like functions: 
 *     getXLocation(), getYLocation(), 
 *     setColor, setLineWidth, setThickness
 * 3. added display method that then calls the awt fill and draw methods.
 * 4. this version's interface is entirely "int"-based, whereas the
 *    Smart classes from the book have some int and partial rotation;
 *    the awt classes have fully "int" interfaces.
 * 
 * @author rdb, Brown Univ
 * Last modified: January 2015
 * 01/29/11 rdb - Added getColor, getFrameColor, getFillColor
 * 01/12/15 rdb: make style-checker compatible
 */

public class ARectangle extends java.awt.geom.Rectangle2D.Double 
                        implements AShape
{
    //---------------- instance variables ------------------------
    protected Color _borderColor;
    protected Color _fillColor;
    protected int   _lineWidth = 2;
    
    final protected int   defaultW = 30;
    final protected int   defaultH = 30;
    final protected Color defaultColor = Color.RED;
    
    //--------------------  constructor ---------------------------
    /**
     * Constructor from SmartRectangle.
     * 
     * @param aColor Color color of the object
     */
    public ARectangle( Color aColor )
    { 
        this( 0, 0 );
        setColor( aColor );   
    }
    /**
     * Another wheels-like constructor.
     * 
     * @param x int position
     * @param y int position
     */
    public ARectangle( int x, int y )
    {
        this.setLocation( x, y );
        this.setSize( defaultW, defaultH );
        this.setColor( defaultColor );
    }   
    /**
     * A default constructor.
     */
    public ARectangle()
    { 
        this( 0, 0 );
        setColor( defaultColor );   
    }
    
    //++++++++++++++++ wheels-like convenience methods +++++++++++++++++++
    //----------------------- setFrameColor( Color ) ---------------------
    /**
     * setFrameColor -- a wheels method.
     * 
     * @param aColor Color color of the object
     */
    public void setFrameColor( Color aColor ) 
    {
        _borderColor = aColor;
    }
    //----------------------- setFillColor( Color ) ----------------------
    /**
     * setFillColor -- a wheels method.
     * 
     * @param aColor Color color of the object
     */
    public void setFillColor( Color aColor ) 
    {
        _fillColor = aColor;
    }
    //----------------------- setColor( Color ) --------------------------
    /**
     * setColor -- a wheels method. Sets both frame and fill to same value.
     * 
     * @param aColor Color color of the object
     */
    public void setColor( Color aColor ) 
    {
        _fillColor   = aColor;
        _borderColor = aColor;
    }
    //----------------------- getColor() ---------------------------------
    /**
     * getColor -- a wheels method -- returns fillColor.
     *
     * @return Color
     */
    public Color getColor() 
    {
        return _fillColor;
    }
    //----------------------- getFillColor() -----------------------------
    /**
     * getFillColor -- a wheels method -- returns fillColor.
     *
     * @return Color
     */
    public Color getFillColor() 
    {
        return _fillColor;
    }
    //----------------------- getFrameColor() ----------------------------
    /**
     * getFrameColor -- a wheels method -- returns frameColor.
     *
     * @return Color
     */
    public Color getFrameColor() 
    {
        return _borderColor;
    }
    
    //----------------------- setThickness( int ) ------------------------
    /**
     * setThickness -- a wheels method.
     * 
     * @param w int width of line
     */
    public void setThickness( int w ) 
    {
        _lineWidth = w;
    }
    //----------------------- setLineWidth( int ) ------------------------
    /**
     * setLineWidth -- same as setThickness; awt uses the term "LineWidth".
     * 
     * @param w int width of line
     */
    public void setLineWidth( int w ) 
    {
        _lineWidth = w;
    }
    
    //----------------------- getXLocation() -----------------------------
    /**
     * getXLocation() - a wheels method.
     * 
     * @return int value of x location
     */
    public int getXLocation()
    {
        return (int) this.getX();
    }
    //----------------------- getYLocation() -----------------------------
    /**
     * getYLocation() - a wheels method.
     * 
     * @return int value of y location
     */
    public int getYLocation()
    {
        return (int) this.getY();
    }
    //------------------- setLocation( Point ) --------------------------
    /**
     * setLocation( Point ) -- a wheels method.
     *
     * @param p Point position
     */
    public void setLocation( Point p ) 
    {
        setLocation( p.x, p.y );
    }
    //------------------- setLocation( int, int ) ------------------------
    /**
     * setLocation( int, int ) -- wheels-like.
     * 
     * @param x int position
     * @param y int position
     */
    public void setLocation( int x, int y ) 
    {
        super.setFrame( x, y, this.getWidth(), this.getHeight() );
    }
    //----------------------- setSize( int, int ) ------------------------
    /**
     * setSize( int, int )  - a wheels method.
     * 
     * @param aWidth int width
     * @param aHeight int height
     */
    public void setSize ( int aWidth, int aHeight ) 
    {
        super.setFrame( this.getX(), this.getY(), aWidth, aHeight );
    }
    //----------------------- moveBy( int, int ) -------------------------
    /**
     * moveBy( dx, dy ) -- move the location by delta.
     * 
     * @param dX int delta move in x
     * @param dY int delta move in y
     */
    public void moveBy( int dX, int dY ) 
    {
        super.setFrame( this.getX() + dX,
                       this.getY() + dY,
                       this.getWidth(),
                       this.getHeight() );
    }
    //----------------------- display( Graphics2D ) ----------------------
    /**
     * display - calls draw and fill awt methods (this is an rdb method).
     * 
     * @param brush2D java.awt.Graphics2D 
     */
    public void display( java.awt.Graphics2D brush2D )
    {
        fill( brush2D );
        draw( brush2D );
    }
    //-------------------------- fill( Graphics2D ) ----------------------
    /**
     * fill - overrides parent method.
     * 
     * See implementation comments in the AEllipse class.
     * 
     * @param brush2D java.awt.Graphics2D 
     */
    public void fill( java.awt.Graphics2D brush2D )
    {
        Color savedColor = brush2D.getColor();
        brush2D.setColor( _fillColor );
        brush2D.fill( this );  // "this", ARectangle, is subclass
                               // of java.awt.geom.Rectangle2D.Double
                               // Graphics2D object knows how to fill it
        brush2D.setColor( savedColor );
    }
    //--------------------------- draw( Graphics2D ) ---------------------
    /**
     * draw - overrides parent method.
     * 
     * See implementation comments in the AEllipse class.
     * 
     * @param brush2D java.awt.Graphics2D 
     */
    public void draw( java.awt.Graphics2D brush2D ) 
    {
        Color savedColor = brush2D.getColor();
        brush2D.setColor( _borderColor );
        java.awt.Stroke savedStroke = brush2D.getStroke();
        brush2D.setStroke( new java.awt.BasicStroke( _lineWidth ) );
        brush2D.draw( this );     // "this" class, ARectangle, is subclass
        // of java.awt.geom.Rectangle2D.Double
        // A Graphics2D object knows how to draw the
        // the border of this kind of object.
        brush2D.setStroke( savedStroke );
        brush2D.setColor( savedColor );
    }
}
