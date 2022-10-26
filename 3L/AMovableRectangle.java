/**
 * AMovableRectangle.java: Draggable version of ARectangle
 *             implements AMovableShape
 * 
 * @author rdb
 * Created: 25 January 2014
 *          Previously, this had been an alternative implementation
 *          of the A-classes. Much cleaner to use inheritance.
 *
 */

import java.awt.geom.*;
import java.awt.*;
import java.awt.event.*;

public class AMovableRectangle extends ARectangle implements AMovableShape
{
    //---------------- instance variables ------------------------
    protected Container _parent = null;  // Used for animation
    protected int       _moveX, _moveY;  // move steps for each frame
    
    //--------------------  constructors ---------------------------
    /**
     * Constructor from SmartEllipse
     */
    public AMovableRectangle( Color aColor )
    { 
        this( 0, 0 );
        setColor( aColor );
    }
    /**
     * Another wheels-like constructor
     */
    public AMovableRectangle( int x, int y )
    {
        super( x, y );
        _draggable = true;
        _animated  = false;
    }
    
    /**
     * A default constructor
     */
    public AMovableRectangle()
    { 
        this( 0, 0 );
        setColor( defaultColor );
    }
    
    //------------------------- setContainer ---------------------------
    /**
     * Defines the Container that contains this object.
     *   This is useful especially for the Animated interface so the
     *   object knows the bounds of its container, so if it wants to, it
     *   can keep the object inside the container.
     * If it is not called, the animation will just go off screen.
     */
    public void setContainer( Container parent )
    {
        _parent = parent;
    }
    //++++++++++++++++++++++ Draggable interface methods +++++++++++++++
    private boolean   _draggable = false; // true if obj can be dragged
    public void setDraggable( boolean onOff )
    {
        _draggable = onOff;
    }
    public boolean isDraggable()
    {
        return _draggable;
    }
    //++++++++++++++++++++++ Animated interface ++++++++++++++++++++++++
    private boolean _animated = false; // animation enabled flag
    
    //---------------------- setAnimated( boolean ) --------------------
    public void setAnimated( boolean onOff )
    {
        _animated = onOff;
    }
    //---------------------- isAnimated() --------------------------------
    public boolean isAnimated()
    {
        return _animated;
    }
    //---------------------- newFrame() --------------------------------
    /**
     * If AMovableRectangle doesn't know its container, it doesn't know
     * its container's bounds and thus cannot bounce off it.
     * 
     * If setContainer has been called, this is not a problem.
     */
    public void newFrame() 
    {
        if ( _animated )
        {
            moveBy( _moveX, _moveY );
            if ( _parent == null )
                return;    // can't test bounds
            int nextX = (int)getX();
            int nextY = (int)getY();
            if ( nextX <= minX() ) 
            {
                _moveX = - _moveX;
                setLocation( minX(), nextY ); // force on screen 
            }
            else if ( nextX >= maxX() ) 
            {
                _moveX = - _moveX;
                setLocation( maxX(), nextY );
            }
            
            if ( nextY <= minY() ) 
            {
                _moveY = - _moveY;
                setLocation( nextX, minY() );
            }
            else if ( nextY >= maxY() ) 
            {
                _moveY = - _moveY;
                setLocation( nextX, maxY() );
            }
        }
    }
    //+++++++++++++++ end Animated interface +++++++++++++++++++++++++++++++
    //++++++++++++++ Animated - related methods ++++++++++++++++++++++++++++
    //----------------- setMove( int, int ) -------------------
    public void setMove( int dx, int dy )
    {
        _moveX = dx;
        _moveY = dy;
        _animated = true;
    }
    
    //------------- minX() ---------------------
    private int minX() 
    {
        return(int) _parent.getX();
    }
    //------------- minY() ---------------------
    private int minY() 
    {
        return(int) _parent.getY();
    }
    //------------- maxX() ---------------------
    private int maxX() 
    {
        return(int)( _parent.getX() + _parent.getWidth()
                        - this.getWidth() );
    }
    //-------------- maxY() ----------------------
    private int maxY() 
    {
        return(int)( _parent.getY() + _parent.getHeight()
                        - this.getHeight() );
    }
}
