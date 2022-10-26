import java.awt.geom.*;
//import java.awt.*;
import java.awt.Color;
import java.awt.Point;
import javax.swing.*;

/**
 * ATriangle.java-- a wheels-like convenience class using AWT. 
 *    
 * @author  rdb
 * Last edited: January 2010
 */
public class ATriangle
{
    //---------------- instance variables ------------------------
    private Color _borderColor;
    private Color _fillColor;
    private int   _lineWidth;
   
    private double[] _xd;
    private double[] _yd;
    private int[]    _x;
    private int[]    _y;
    private int      _n;
   
    private java.awt.Polygon   _poly;
    private java.awt.Rectangle _bnds;
   
    //--------------------  constructors ---------------------------
    /**
     * Constructor with 3 Point objects.
     *
     * @param p0 First vertex.
     * @param p1 Second vertex.
     * @param p2 Third vertex.
     *
     */
    public ATriangle( Point p0, Point p1, Point p2 )
    {
        _n = 3;
        _x = new int[ _n ];
        _y = new int[ _n ];
        _x[ 0 ] = p0.x;
        _y[ 0 ] = p0.y;
        _x[ 1 ] = p1.x;
        _y[ 1 ] = p1.y;
        _x[ 2 ] = p2.x;
        _y[ 2 ] = p2.y;
        _poly = new java.awt.Polygon( _x, _y, _n );
        _bnds = _poly.getBounds();
      
        _borderColor = Color.RED;
        _fillColor   = Color.RED;
        _lineWidth   = 2;
      
        double xLoc = _bnds.getX();
        double yLoc = _bnds.getY();
      
        // save int and double versions of the vertices relative to the location
        _xd = new double[ _n ];
        _yd = new double[ _n ];
        for ( int i = 0; i < _n; i++ )
        {
            _xd[ i ] = _x[ i ] - xLoc;
            _yd[ i ] = _y[ i ] - yLoc;
        }
    }
    //----------------------- getPoints() ----------------------------------
    /**
     * getPoints() -- Get the vertices of the triangle.
     * @return the 3 vertices of the triangle as an array of Point objects.
     */
    public Point[] getPoints()
    {
        Point[] vertices = new Point[ _x.length ];
        for ( int i = 0; i < _x.length; i++ )
            vertices[ i ] = new Point( _x[ i ], _y[ i ] );
        return vertices;         
    }
    //++++++++++++++++++ wheels-like convenience methods +++++++++++++++++++
    //----------------------- getXLocation() -------------------------------
    /**
     * getXLocation() - a wheels method.
     *                  @return int value of x location
     */
    public int getXLocation()
    {
        return (int) _bnds.getX();
    }
    //----------------------- getYLocation() -------------------------------
    /**
     * getYLocation() - a wheels method.
     *                  @return int value of y location
     */
    public int getYLocation()
    {
        return (int) _bnds.getY();
    }
    //----------------------- getWidth() -------------------------------
    /**
     * getWidth() - a wheels method.
     *                  @return int value of width
     */
    public int getWidth()
    {
        return (int) _bnds.getWidth();
    }
    //----------------------- getHeight() -------------------------------
    /**
     * getHeight() - a wheels method.
     *                  @return int value of height
     */
    public int getHeight()
    {
        return (int) _bnds.getHeight();
    }
    //------------------- setLocation( Point ) -----------------------
    /**
     * setLocation( Point ) -- a wheels method.
     * @param p New location value.
     */
    public void setLocation( Point p ) 
    {
        setLocation( p.x, p.y );
    }
    //------------------- setLocation( double, double ) -----------------------
    /**
     * setLocation( double, double ) -- wheels-like, but
     *                                  use double rather than int.
     * @param x New x value.
     * @param y New y value.
     */
    public void setLocation( double x, double y ) 
    {
        moveBy( x - getXLocation(), y - getYLocation() );
    }
    //-------------------- moveBy( double, double ) --------------------------
    /**
     * moveBy( dx, dy ) -- move the location by delta.
     * @param changeInX Amount to move in x.
     * @param changeInY Amount to move in y.
     */
    public void moveBy( double changeInX, double changeInY ) 
    {
        _poly.translate( (int) changeInX, (int) changeInY );
        _bnds = _poly.getBounds();
    }
    //--------------------- setSize( double, double ) ------------------------
    /**
     * setSize( double, double )  - a wheels method.
     * @param aWidth New width.
     * @param aHeight New height.
     */
    public void setSize ( double aWidth, double aHeight ) 
    {
        // need to actually change the coordinate positions in order to
        // change the size. Need to scale all coordinates by the ratio
        // of the new size to the old size
        double oldW = _bnds.getWidth();
        double oldH = _bnds.getHeight();
      
        double scaleW = aWidth / oldW;
        double scaleH = aHeight / oldH;
       
        double xLoc = _bnds.getX();
        double yLoc = _bnds.getY();

        for ( int i = 0; i < _n; i++ )
        {
            // if store as ints, assign to float and translate to origin
            //_xd[ i ] = _x[ i ] - xLoc;  
            //_yd[ i ] = _y[ i ] - yLoc;
            _xd[ i ] = scaleW * _xd[ i ];
            _x[ i ]  = (int) Math.round( _xd[ i ] + xLoc );
            _yd[ i ] = scaleH * _yd[ i ];
            _y[ i ]  = (int) Math.round( _yd[ i ] + yLoc );
        }
        _poly = new java.awt.Polygon( _x, _y, _n );
        _bnds = _poly.getBounds();
    }
    //----------------------- display( Graphics2D ) -------------------------
    /**
     * display - calls draw and fill awt methods (this is an rdb method).
     * @param brush2 Brush object for drawing.
     */
    public void display( java.awt.Graphics2D brush2 )
    {
        fill( brush2 );
        draw( brush2 );
    }
    //-------------------------- fill( Graphics2D ) ------------------------
    /**
     * fill - overrides parent method.
     * @param brush2 Brush object for drawing.
     */
    public void fill( java.awt.Graphics2D brush2 )
    {
        Color savedColor = brush2.getColor();
        brush2.setColor( _fillColor );
      
        brush2.fill( _poly );
      
        brush2.setColor( savedColor );
    }
    //--------------------------- draw( Graphics2D ) ------------------------
    /**
     * draw - overrides parent method.
     * @param aBrush Brush object for drawing.
     */
    public void draw( java.awt.Graphics2D aBrush ) 
    {
        Color savedColor = aBrush.getColor();
        aBrush.setColor( _borderColor );
        java.awt.Stroke savedStroke = aBrush.getStroke();
        aBrush.setStroke( new java.awt.BasicStroke( _lineWidth ) );

        aBrush.draw( _poly );

        aBrush.setStroke( savedStroke );
        aBrush.setColor( savedColor );
    }
    //++++++++++++++++++ more wheels-like convenience methods ++++++++++++++
    //++++++++++++++ These are the same as AEllipse and ARectangle ++++++
    //----------------------- setFrameColor( Color ) --------------------
    /**
     * setFrameColor -- a wheels method.
     * @param aColor New frame color.
     */
    public void setFrameColor( Color aColor ) 
    {
        _borderColor = aColor;
    }
    //----------------------- setFillColor( Color ) --------------------
    /**
     * setFillColor -- a wheels method.
     * @param aColor New fill color.
     */
    public void setFillColor( Color aColor ) 
    {
        _fillColor = aColor;
    }
    //----------------------- setColor( Color ) -----------------------
    /**
     * setColor -- a wheels method.
     * @param aColor New fill and border color.
     */
    public void setColor( Color aColor ) 
    {
        _fillColor   = aColor;
        _borderColor = aColor;
    }
    //----------------------- setThickness( int ) -------------------------
    /**
     * setThickness -- a wheels method.
     * @param w Line width.
     */
    public void setThickness( int w ) 
    {
        _lineWidth = w;
    }
    //----------------------- setLineWidth( int ) --------------------------
    /**
     * setLineWidth -- same as setThickness, but awt uses the term "LineWidth".
     * @param w Line width.
     */
    public void setLineWidth( int w ) 
    {
        _lineWidth = w;
    }
    //-------------------------- toString() --------------------------------
    /**
     * toString -- converts object to String.
     * @return String representation.
     */
    public String toString()
    {
        return "Triangle: " + new Point( _x[0], _y[0] ) + ", " +
            new Point( _x[1], _y[1] ) + ", " +
            new Point( _x[2], _y[2] ) + "]";
    }
}
