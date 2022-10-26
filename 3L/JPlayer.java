/**
 * JPlayer.java-- a composite J object that is comprised of parts that
 *    are J-objects. Note that this implementation of a composite
 *    graphics object is different from what you probably did for
 *    the previous lab -- this one extends JComponent which changes how 
 *    several things need to be done. In particular, this object must
 *    compute its size from its components and invoke setSize
 *    explicitly. 
 *
 *    This is a very simple composite and the setSize could have been 
 *    pre-computed fairly easily. The solution here is more general in
 *    order to provide a template for more complex situations:
 *    We override the "add" method of JComponent, and update the
 *    composite size/location information as items are added.
 *
 * @author rdb
 * Last edited: January 2010
 */

import java.awt.geom.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class JPlayer extends JComponent implements Animated, MouseListener, MouseMotionListener
{
    //---------------- instance variables ------------------------
    private Rectangle _bounds;   // composite bounds for all components
    int       _moveX, _moveY;
    //--------------------  constructors ---------------------------
    /**
     * Constructor for JPlayer
     */
    public JPlayer( Color aColor, int x, int y )
    {       
        int    headX = 5,  headY = 0,  headW = 20, headH = 20;
        int    bodyX = 0,  bodyY = 20, bodyW = 30, bodyH = 50;
        
        // The parts are defined relative to 0,0
        JEllipse head = new JEllipse( headX, headY );
        head.setSize( headW, headH );
        head.setColor( aColor );
        this.add( head );
        
        JRectangle body = new JRectangle( bodyX, bodyY );
        body.setSize( bodyW, bodyH );
        body.setColor( aColor.darker().darker() );
        this.add( body );      
        
        setLocation( x, y );
        addMouseListener( this );
        addMouseMotionListener( this );
    }
    //---------------- add( JComponent ) ---------------------------
    /**
     * override add method to compute and set bounds information as 
     * components are added to the object.
     */
    public void add( JComponent comp )
    {
        super.add( comp );
        if ( _bounds == null )
            _bounds = new Rectangle( comp.getBounds() );
        else
            _bounds = _bounds.union( comp.getBounds() );
        super.setBounds( _bounds ); // update location/size     
    }
    //---------------- moveBy( int, int ) -----------------------------
    public void moveBy( int dx, int dy )
    {
        setLocation( (int)getX() + dx, (int)getY() + dy );
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



    //++++++++++++++++++++ mouse methods / instance variables ++++++++++
    private Point _saveMouse;   // instance variable for last mouse pos
    //   used for dragging
    
    //+++++++++++++++++++++++++ mouseListener methods ++++++++++++++++++
    public void mousePressed( MouseEvent me )
    {
        ////////////////////////////////////////////////////////////////
        // Step 5c. 
        //    Need to get and save the mouse position on a press.
        //         me.getPoint()
        //    which we've used before is the location of the 
        //    mouse "inside" the JComponent; this won't work.
        //
        //    We need the position of the mouse in the container that 
        //    holds the JComponent: getParent().getMousePosition()
        //
        // Assign it to the instance variable, _saveMouse, of type Point
        ////////////////////////////////////////////////////////////////

        _saveMouse = getParent().getMousePosition();
        
    }
    public void mouseClicked( MouseEvent me ){}
    public void mouseReleased( MouseEvent me ){}
    public void mouseEntered( MouseEvent me ){}
    public void mouseExited( MouseEvent me ){}
    
    //+++++++++++++++++++ mouseMotionListener methods ++++++++++++++++++
    public void mouseDragged( MouseEvent me )
    {
        ////////////////////////////////////////////////////////////////
        //  Step 5d. drag!
        //     Get new position of mouse:
        //         getParent().getMousePosition()
        //     Compute
        //        dX = newX - oldX (stored in _saveMouse.x)
        //        dY = newY - oldY (stored in _saveMouse.y)
        //     Invoke moveBy( dX, dY ) 
        //     Save new position in _saveMouse
        //
        // WARNING: If mouse is moved out of the frame, the position is
        //          returned as a null reference. You need to check that
        //          before using the saved position or the new position.
        //          If either is off screen, don't do anything!!!
        ////////////////////////////////////////////////////////////////

        int newX = getParent().getMousePosition().x;
        int newY = getParent().getMousePosition().y;
        
       int dX = newX - _saveMouse.x;
       int dY = newY - _saveMouse.y;
       moveBy( dX, dY );
       _saveMouse = getParent().getMousePosition();
        
        
        
    }
    public void mouseMoved( MouseEvent me ){}
    //+++++++++++++++++ end MouseMotionListeners +++++++++++++++++++++++
    
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //------------------ main -------------------------------
    /**
     * Convenience main for testing the lab code.
     */
    public static void main( String [ ] args ) 
    {
        SwingEventsApp app = new SwingEventsApp( "SwingEventsApp" );
    }
}
