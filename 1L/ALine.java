//++++++++++++++++++++++++ ALine.java ++++++++++++++++++++++++++++++++
import java.awt.geom.*;
import java.awt.*;

/**
 * ALine.java -- version 1
 *              a convenience class for simplifying access to awt ALine2D.
 *              the model for this class is the wheels ALine class.
 *          
 * This class is patterned after SmartEllipse and SmartRectangle classes
 * from Ch 7 of Sanders and van Dam, Object-Oriented Programming with Java
 * as revised into our AEllipse and ARectangle classes.
 *
 * This is a very simple implementation that uses the first coordinate
 * of the line as the lines position. It would be better to create a
 * bounds for the line and use the upper left corner of the bounds.
 * 
 * @author rdb
 * Last major modification: January 2010
 * 
 * 01/29/11 rdb: added getColor
 * 01/12/15 rdb: make style-checker compatible
 */

public class ALine extends java.awt.geom.Line2D.Double 
                   implements AShape
{
    //---------------- instance variables -------------------------------
    protected Color _lineColor; 
    protected int   _lineWidth = 1;
    
    protected Color defaultColor = Color.RED;
    
    //----------------- constructors ------------------------------------
    /**
     * No argument constructor. Color defaults to RED, line from 0,0 to 10,10
     */
    public ALine()
    {
        setColor( defaultColor );
        setPoints( 0, 0, 1, 1 );
    }
    
    /**
     *  Constructor based on constructors for SmartEllipse and SmartRectangle.
     *       Endpoints default to a line from 0,0 to 10,10
     * 
     * @param aColor Color color of the object
     */
    public ALine( Color aColor )
    { 
        this();
        setColor( aColor );
    }
    /**
     * Another wheels-like constructor. Color defaults to RED
     * 
     * @param x1 x pos of endpoint 1
     * @param y1 y pos of endpoint 1
     * @param x2 x pos of endpoint 2
     * @param y2 y pos of endpoint 2
     */
    public ALine( int x1, int y1, int x2, int y2 )
    {
        setColor( defaultColor );
        this.setPoints( x1, y1, x2, y2 );
    }   
    //--------------------- wheels-like convenience methods --------------
    //------------------- setColor( Color ) ------------------------------
    /**
     * setColor -- a wheels method.
     * 
     * @param aColor Color color of the object
     */
    public void setColor( Color aColor ) 
    {
        _lineColor = aColor;
    }
    //----------------------- getColor() ---------------------------------
    /**
     * getColor -- a wheels method -- returns fillColor.
     * 
     * @return Color
     */
    public Color getColor() 
    {
        return _lineColor;
    }
    
    //------------------- setThickness( int ) ----------------------------
    /**
     * setThickness -- a wheels method.
     * 
     * @param w int  width of line
     */
    public void setThickness( int w ) 
    {
        _lineWidth = w;
    }
    //------------------- setLineWidth( int ) ----------------------------
    /**
     * setLineWidth -- same as setThickness; awt uses the term "LineWidth".
     * 
     * @param w int  width of line
     */
    public void setLineWidth( int w ) 
    {
        _lineWidth = w;
    }
    
    //------------------- getXLocation() ---------------------------------
    /**
     * getXLocation() - a wheels-like method.
     * 
     * @return int value of x location
     */
    public int getXLocation()
    {
        return (int) this.getX1();
    }
    //------------------- getYLocation() ---------------------------------
    /**
     * getYLocation() - a wheels-like method.
     * 
     * @return int value of y location
     */
    public int getYLocation()
    {
        return (int) this.getY1();
    }
    //-------------- setPoints( int, int, int, int ) ---------------------
    /**
     * setPoints -- wheels-like method.
     * 
     * @param x1 x pos of endpoint 1
     * @param y1 y pos of endpoint 1
     * @param x2 x pos of endpoint 2
     * @param y2 y pos of endpoint 2
     */
    public void setPoints( int x1, int y1, int x2, int y2 ) 
    {
        this.setLine ( x1, y1, x2, y2 );
    }
    //----------------- setLocation( int, int ) ------------------------
    /**
     * setLocation( int, int ) -- wheels-like.
     *         location is the first endpoint
     *         changing the location assumes the other end point should
     *         move accordingly.
     * 
     * @param x int position
     * @param y int position
     */
    public void setLocation( int x, int y )
    {
        this.moveBy( (int)( x - this.getX1() ), (int)( y - this.getY1() ) );
    }
    //------------------------ setSize( int, int ) ------------------------
    /**
     * setSize( int, int ) -- wheels-like.
     *           this changes the 2nd end point, the parameters
     *           are assumed to represent the relative position of the 2nd
     *           end point to the first
     * 
     * @param aWidth int width
     * @param aHeight int height
     */
    public void setSize( int aWidth, int aHeight ) 
    {
        this.setLine( this.getX1(), this.getY1(), 
                     this.getX1() + aWidth, this.getY1() + aHeight );
    }
    //--------------------------- moveBy( int, int ) ----------------------
    /**
     * moveBy( dx, dy ) -- move the location by delta.
     * 
     * @param dX int delta move in x
     * @param dY int delta move in y
     */
    public void moveBy( int dX, int dY ) 
    {
        this.setLine( this.getX1() + dX, this.getY1() + dY,
                     this.getX2() + dX, this.getY2() + dY );
    }
    
    //--------------------------- display( Graphics2D ) ------------------
    /**
     * display - calls draw awt method for the line; there is no fill for
     *           a line.
     * 
     * @param brush2D java.awt.Graphics2D 
     */
    public void display( java.awt.Graphics2D brush2D )
    {
        draw( brush2D );
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
        brush2D.setColor( _lineColor );
        java.awt.Stroke savedStroke = brush2D.getStroke();
        brush2D.setStroke( new java.awt.BasicStroke( _lineWidth ) );
        
        brush2D.draw( this );  // "this" class, ALine, is subclass
                               // of java.awt.geom.Line2D.Double
                               // A Graphics2D object knows how to draw 
                               // this kind of object.
        brush2D.setStroke( savedStroke );
        brush2D.setColor( savedColor );
    }
}
