//-------------------------- Line.java -----------------------------
import java.awt.geom.*;
import java.awt.*;
/**
 * Line.java -- a convenience class for simplifying access to awt Line2D.
 *              the model for this class is the wheels Line class.
 *          
 * This class is patterned after the SmartEllipse and SmartRectangle classes
 * from Ch 7 of Sanders and van Dam, Object-Oriented Programming with Java
 * as revised into our Ellipse and Rectangle classes.
 * 
 * @author rdb
 * January 2008
 * Last edit
 * 03/16/14 rdb: made checkstyle compatible
 */
public class Line extends java.awt.geom.Line2D.Double 
{
    //---------------- instance variables -------------------------------
    protected Color _lineColor; 
    protected int   _lineWidth = 1;
    
    //----------------- constructors ------------------------------------
    /**
     * No argument constructor.
     */
    public Line()
    {
        this( Color.BLACK );
    }
    
    /**
     * Constructor based on SmartEllipse and SmartRectangle constructors. 
     * @param aColor Color   specify line color
     */
    public Line( Color aColor )
    { 
        _lineColor = aColor;
    }
    /**
     * Another wheels-like constructor -- uses double rather than float.
     * @param x1 double      endpoints
     * @param y1 double
     * @param x2 double
     * @param y2 double
     */
    public Line( double x1, double y1, double x2, double y2 )
    {
        this( Color.BLACK );
        this.setPoints( x1, y1, x2, y2 );
    }   
    //--------------------- wheels-like convenience methods -----------------
    //------------------- setColor( Color ) ------------------------------
    /**
     * setColor -- a wheels method.
     * @param aColor Color       set line color
     */
    public void setColor( Color aColor ) 
    {
        _lineColor = aColor;
    }
    //------------------- setThickness( int ) ------------------------------
    /**
     * setThickness -- a wheels method.
     * @param w int       set line thickness
     */
    public void setThickness( int w ) 
    {
        _lineWidth = w;
    }
    //------------------- setLineWidth( int ) ------------------------------
    /**
     * setLineWidth -- same as setThickness, but I like the name better.
     * @param w int       set line thickness
     */
    public void setLineWidth( int w ) 
    {
        _lineWidth = w;
    }
    //------------------- getXLocation() ------------------------------
    /**
     * getXLocation() - a wheels-like method.
     * @return int      return int value of x location
     */
    public int getXLocation()
    {
        return (int) this.getX1();
    }
    //------------------- getYLocation() ------------------------------
    /**
     * getYLocation() - a wheels-like method.
     * @return int      return int value of y location
     */
    public int getYLocation()
    {
        return (int) this.getY1();
    }
    //-------------------- setPoints( int, int, int, int ) ------------
    /**
     * setPoints -- wheels-like method.
     *      define the endpoints of the line with double, rather than int
     * @param x1 double      endpoints
     * @param y1 double
     * @param x2 double
     * @param y2 double
     */
    public void setPoints( double x1, double y1, double x2, double y2 ) 
    {
        this.setLine ( x1, y1, x2, y2 );
    }
    //----------------- setLocation( double, double ) -----------------
    /**
     * setLocation( double, double ) -- wheels-like.
     *    location is the first endpoint; changing the location assumes 
     *    other end point should move accordingly.
     * @param x double      location of 1st endpoint
     * @param y double
     */
    public void setLocation( double x, double y )
    {
        this.move( (int)( x - this.getX1() ), (int)( y - this.getY1() ) );
    }
    //------------------------ setSize( int, int ) --------------------
    /**
     * setSize( int, int ) -- wheels-like.
     *     Changes the 2nd end point, the parameters are assumed to 
     *     represent relative position of 2nd end point to the first.
     * @param aWidth int     width
     * @param aHeight int    height
     */
    public void setSize( int aWidth, int aHeight ) 
    {
        this.setLine( this.getX1(), this.getY1(), 
                     this.getX1() + aWidth, this.getY1() + aHeight );
    }
    //--------------------------- move( int, int ) --------------------
    /**
     * move( dx, dy ) -- move the location by delta.
     * @param aChangeInX int     delta x
     * @param aChangeInY int     delta y
     */
    public void move( int aChangeInX, int aChangeInY ) 
    {
        this.setLine( this.getX1() + aChangeInX, this.getY1() + aChangeInY,
                     this.getX2() + aChangeInX, this.getY2() + aChangeInY );
    }
    
    //--------------------------- display( Graphics2D ) ---------------
    /**
     * Display - calls draw and fill awt methods (this is an rdb method).
     * @param aBetterBrush Graphics2D
     */
    public void display( java.awt.Graphics2D aBetterBrush )
    {
        draw( aBetterBrush );
    }
    //--------------------------- draw( Graphics2D ) -------------------
    /**
     * draw - overrides parent method.
     * @param aBrush Graphics2D
     */
    public void draw( java.awt.Graphics2D aBrush ) 
    {
        Color savedColor = aBrush.getColor();
        aBrush.setColor( _lineColor );
        java.awt.Stroke savedStroke = aBrush.getStroke();
        aBrush.setStroke( new java.awt.BasicStroke( _lineWidth ) );
        aBrush.draw( this );
        aBrush.setStroke( savedStroke );
        aBrush.setColor( savedColor );
    }
}
