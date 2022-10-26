/**
 * JMovableEllipse.java: Draggable version of AEllipse
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

public class JMovableEllipse extends JEllipse implements Animated
{
    //---------------- instance variables ------------------------
    //protected Container getParent() = null;  // Used for animation
    protected int       _moveX, _moveY;  // move steps for each frame
    
    //--------------------  constructors ---------------------------
    /**
     * Constructor from SmartEllipse
     */
    public JMovableEllipse( Color aColor )
    { 
        this( 0, 0 );
        setColor( aColor );
    }
    /**
     * Another wheels-like constructor
     */
    public JMovableEllipse( int x, int y )
    {
        super( x, y );
        _draggable = true;
        _animated  = false;
    }
    
    /**
     * A default constructor
     */
    public JMovableEllipse()
    { 
        this( 0, 0 );
        setColor( defaultColor );
    }
    
    //------------------------- setContainer ---------------------------
    
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
    
    //+++++++++++++++ copy methods used for animation ++++++++++++++++++
    ///////////////////////////////////////////////////////////////////
    // In the JMovableEllipse version of this code:
    // Step 3b. Copy code from here through the maxY method to replace
    //          the moveBy method of JPlayer.
    ///////////////////////////////////////////////////////////////////
    //           
    //----------------- setMove( int, int ) -------------------
    public void setMove( int dx, int dy )
    {
        _moveX = dx;
        _moveY = dy;
        _animated = true;
    }    
    //++++++++++++++++++++++ Animated interface ++++++++++++++++++++++++
    private boolean _animated = false; // animation enabled flag
    
    //---------------------- setAnimated( boolean ) --------------------
    public void setAnimated( boolean onOff )
    {
        _animated = onOff;
    }
    //---------------------- isAnimated() ------------------------------
    public boolean isAnimated()
    {
        return _animated;
    }
    //---------------------- newFrame() --------------------------------
    /**
     * If JMovableEllipse doesn't know its container, it doesn't know
     * its container's bounds and thus cannot bounce off it.
     * 
     * If setContainer has been called, this is not a problem.
     */
    public void newFrame() 
    {
        if ( _animated )
        {
            moveBy( _moveX, _moveY );
            if ( getParent() == null )
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
    //+++++++++++++++ end Animated interface +++++++++++++++++++++++++++
    
    //++++++++++++++ Animated - related methods ++++++++++++++++++++++++
    //------------- minX() ---------------------
    private int minX() 
    {
        return(int) getParent().getX();
    }
    //------------- minY() ---------------------
    private int minY() 
    {
        return(int) getParent().getY();
    }
    //------------- maxX() ---------------------
    private int maxX() 
    {
        return(int)( getParent().getX() + getParent().getWidth()
                        - this.getWidth() );
    }
    //-------------- maxY() ----------------------
    private int maxY() 
    {
        return(int)( getParent().getY() + getParent().getHeight()
                        - this.getHeight() );
    }
    ////////////////////////////////////////////////////////////////////
    // end of Step 3b copy
    ////////////////////////////////////////////////////////////////////    
}
