/**
 * Chapter 7: EventsPanel.java
 * Creates the panel to be placed inside the Lag3 window.
 * Used( with modifications ) in all programs later in this book.
 * Version 3 of 3
 *
 * 1/30/08: rdb
 *    Renamed (old name was BallApp) and added JFrame parameter to constructor
 *    Pass JFrame to BouncingBall
 */
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class EventsPanel extends JPanel implements Animated, MouseListener, MouseMotionListener
{
    //------------------------ instance variables ---------------------
    private final int FRAME_INTERVAL = 100;  // 100 msec per frame
    
    private Container _parent;
    
    // animation/gui variables
    ArrayList<Animated>  _movers = null;    // objects that can move
    ArrayList<AShape>    _ashapes = null;   // objects that need to be displayed
    ArrayList<Draggable> _draggable = null; // Objects that can be dragged
    ArrayList<Draggable> _dragging = null;  // Objects currently being dragged
    
    //--------------------- EventsPanel -----------------------------
    public EventsPanel( Container parent ) 
    {
        super();
        setLayout( null );   //Very important method call!!
        
        _parent = parent;
        
        this.setBackground( Color.white );
        
        // create an ArrayList containing all objects that need updating
        //  on each frame.
        _movers   = new ArrayList<Animated>();
        
        // create an ArrayList for all objects that need to have their
        //  display methods executed in paintComponent
        _ashapes  = new ArrayList<AShape>();
        
        //////////////////////////////////////////////////////////////////////
        // Step 6a: initialize the _draggables and _dragging instance variables
        //////////////////////////////////////////////////////////////////////
        
        _draggable = new ArrayList<Draggable>();
        _dragging = new ArrayList<Draggable>();
        
        
        // Create a movable and draggable ball using AMovableEllipse
        AMovableEllipse bball = new AMovableEllipse( Color.RED );
        bball.setContainer( this ); // tell AMovableEllipse its move bnds
        bball.setLocation( 75, 75 );
        bball.setSize( 60, 60 );
        bball.setMove( 5, 9 );
        _movers.add( bball );
        _ashapes.add( bball );
        
        ////////////////////////////////////////////////////////////////
        // Step 1: Create another bouncing ball. Make it BLUE. Put it 
        //         somewhere new, make it a different size,  
        //         and give it different setMove parameters
        ////////////////////////////////////////////////////////////////
     
        AMovableEllipse bball2 = new AMovableEllipse( Color.BLUE );
        bball2.setContainer( this ); // tell AMovableEllipse its move bnds
        bball2.setLocation( 200, 200 );
        bball2.setSize( 40, 40 );
        bball2.setMove( 5, 3 );
        _movers.add( bball2 );
        _ashapes.add( bball2 );
        
        
        ////////////////////////////////////////////////////////////////
        // Step 2g: Add JMovableEllipse. Make it BLACK. Put it somewhere 
        //         new, make it a different size than any thing else, 
        //         and give it different setMove parameters
        ////////////////////////////////////////////////////////////////
        
        AMovableEllipse bball3 = new AMovableEllipse( Color.BLACK );
        bball3.setContainer( this ); // tell AMovableEllipse its move bnds
        bball3.setLocation( 0, 100 );
        bball3.setSize( 10, 10 );
        bball3.setMove( 9, 7 );
        _movers.add( bball3 );
        _ashapes.add( bball3 );
        
        
        ////////////////////////////////////////////////////////////////
        // Step 2h: Add another JMovableEllipse. Make it GREEN. Put it 
        //         somewhere new, make it a different size than anything
        //          else, and give it different setMove parameters
        ////////////////////////////////////////////////////////////////
        
        AMovableEllipse bball4 = new AMovableEllipse( Color.GREEN );
        bball4.setContainer( this ); // tell AMovableEllipse its move bnds
        bball4.setLocation( 300, 150 );
        bball4.setSize( 35, 35 );
        bball4.setMove( 2, 6 );
        _movers.add( bball4 );
        _ashapes.add( bball4 );
        
        ////////////////////////////////////////////////////////////////
        // Step 3d: Add a JPlayer. Make it CYAN. Put it somewhere new,
        //         and give it different setMove parameters
        ////////////////////////////////////////////////////////////////
         
        AMovableEllipse bball5 = new AMovableEllipse( Color.CYAN );
        bball5.setContainer( this ); // tell AMovableEllipse its move bnds
        bball5.setLocation( 10, 10 );
        bball5.setSize( 16, 16 );
        bball5.setMove( 8, 2 );
        _movers.add( bball5 );
        _ashapes.add( bball5 );
        
        ////////////////////////////////////////////////////////////////
        // Step 5e: Add a non-moving JPlayer. Make it GRAY. Put it 
        //         somewhere new, and give it different setMove parms
        ////////////////////////////////////////////////////////////////
        
        JPlayer jplay = new JPlayer( Color.GRAY , 80, 90 );
        //jplay.setContainer( this ); // tell AMovableEllipse its move bnds
        //jplay.setLocation( 80, 90 );
        jplay.setSize( 50, 50 );
        jplay.setMove( 5, 12 );
        //_ashapes.add( jplay );
        this.add( jplay );
        
        //------------------- 2 more starter objects -------------------
        AMovableEllipse ame = new AMovableEllipse( 100, 100 );
        ame.setColor( Color.MAGENTA );
        _ashapes.add( ame );
        //_draggable.add( ame );

        AMovableRectangle amr = new AMovableRectangle( 120, 110 );
        amr.setSize( 30, 40 );
        amr.setColor( Color.ORANGE );
        _ashapes.add( amr );
        //_draggable.add( amr );
        
        ///////////////////////////////////////////////////////////////
        // Step 5b. Add the above 2 objects to the _draggable array
        ///////////////////////////////////////////////////////////////
        
        _draggable.add( amr );
        _draggable.add( ame );
        
        
        // create and start up the FrameTimer
        FrameTimer timer = new FrameTimer( FRAME_INTERVAL, this );
        addMouseListener( this );
        addMouseMotionListener( this );
        timer.start();
    }
    
    //++++++++++++++++++++++ Animated interface ++++++++++++++++++++++++
    private boolean      _animated; // not really used for the panel
    //---------------------- newFrame() --------------------------------
    public boolean isAnimated()
    {
        return _animated;
    }
    //---------------------- setAnimated( boolean ) --------------------
    public void setAnimated( boolean onOff )
    {
        _animated = onOff;
    }
    //---------------------- newFrame() -------------------------------
    public void newFrame() 
    {
        for ( int i = 0; i < _movers.size(); i++ )
            _movers.get( i ).newFrame();
        
        this.repaint();
    }
    
    //++++++++++++++++++ MouseListener methods +++++++++++++++++++++++++
    // You need to implement mousePressed and mouseDragged; 
    // the others must be there but will remain "empty".
    //
    private Point   _saveLoc;   // instance variable needed for dragging
    //------------------- mousePressed( MouseEvent ) -------------------
    /**
     * When completed, this method will take one of 2 actions:
     *   if mouse event location is inside one or more draggable objects,
     *      initiate dragging of those objects
     *   else
     *      generate a new BouncingBall object at the mouse location
     */
    public void mousePressed( MouseEvent me )
    {  
        Point loc = me.getPoint();  // get location of mouse press event
        
        ////////////////////////////////////////////////////////////////
        // Step 4c. is in the "else" clause of the "if" test below. Fill
        //       in the else before doing the "if" and "then" clauses
        //       -- that will happen in step 6
        ////////////////////////////////////////////////////////////////
        
        ////////////////////////////////////////////////////////////////
        // Step 6e. Add a test of the mouse position:
        //
        //    Invoke the findSelectedDraggers method; it takes ArrayList
        //       of Draggable objects and a Point that is mouse position.
        //       It returns list of Draggable objects that contain point 
        //    Both lists need to be instance, not local, variables.
        //    If returned list is not empty (its size > 0)
        //       _saveLoc = mousePosition (used in mouseDragged method)
        //    else
        //       execute the code you wrote in step 4c to add new balls.
        ////////////////////////////////////////////////////////////////
        
        _dragging = findSelectedDraggers( _draggable, loc);
        
        if ( _dragging.size() > 0 ) // step 6e, replace with the test of 
                     //       findSelectedDraggers 
        {
            _saveLoc = loc;
        }
        else // create new a new bouncing box (AMovableRectangle)
        {
            ///////////////////////////////////////////////////////////
            // Step 4c.
            //  Add code to create a new YELLOW AMovableRectangle at  
            //     mouse event position, which is returned as a Point 
            //     by me.getPoint() and stored in the "loc" variable.
            //  The step increments for the ball's motion should be
            //            loc.x % 21 - 10, loc.y % 21 - 10
            //  The size (both width and height) should be determined 
            //    by the formula:
            //            10 + ( loc.x + loc.y ) % 26
            //  The color should be set to YELLOW.
            ///////////////////////////////////////////////////////////
            
          loc = me.getPoint();
          AMovableRectangle rect = new AMovableRectangle( Color.YELLOW );
          rect.setContainer( this ); // tell AMovableEllipse its move bnds
          rect.setLocation( 50, 50 );
          rect.setSize( 10 + ( loc.x + loc.y ) % 26, 10 + ( loc.x + loc.y ) % 26 );
          rect.setMove( loc.x % 21 - 10, loc.y % 21 - 10 );
          _movers.add( rect );
          //_ashapes.add( rect );
            
            
        }
    }
    
    //------------- unused MouseListener methods -----------------
    public void mouseEntered( MouseEvent me ) {}
    public void mouseExited( MouseEvent me ) {}
    public void mouseMoved( MouseEvent me ) {}
    public void mouseReleased( MouseEvent me ){}
    //+++++++++++++++++++++ end MouseListener methods ++++++++++++++++++
    
    //-------------- findSelectedDraggers( ArrayList, Point ) ----------
    /**
     * First argument: ArrayList<Draggable>  objects that can be dragged
     * Second argument: Point mousePosition
     * 
     * this routine tests the mouse position against draggable W-objects
     * to see if the point is inside the bounds of the each object.
     * It returns an ArrayList of all the objects that enclose the
     * mouse position.
     */
    private ArrayList<Draggable> 
        findSelectedDraggers( ArrayList<Draggable> draggers, Point p )
    {
        ArrayList<Draggable> selected = new ArrayList<Draggable>();
        Iterator<Draggable> iter = draggers.iterator();
        while( iter.hasNext() )
        {
            Draggable d = iter.next();
            if ( d.isDraggable() && d.contains( p ))
                selected.add( d );
        }
        return selected;
    }
    
    //+++++++++++++++ MouseMotionListener methods ++++++++++++++++++++++
    //------------------- mouseDragged( MouseEvent ) -----------------  
    public void mouseDragged( MouseEvent me )
    { 
        ////////////////////////////////////////////////////////////////
        // Step 6f.
        // if _saveLoc is NOT null  
        //    1. get the mouse location; use me.getPoint()
        //    2. compute dx, dy as differences between this location 
        //       and _saveLoc
        //    3. set _saveLoc to the new location
        //    4. foreach entry in _dragging array
        //          call its moveBy method with dx, dy as arguments
        /////e///////////////////////////////////////////////////////////
 
 int dx = me.getPoint().x - _saveLoc.x;
 int dy = me.getPoint().y - _saveLoc.y;

        for (int i = 0; i < _dragging.size(); i++)
     {
  Draggable obj = _dragging.get(i);
  obj.moveBy(dx, dy);
     }

 _saveLoc = me.getPoint();
    }
    //------------------- mouseClicked( MouseEvent ) -----------------  
    public void mouseClicked( MouseEvent me ){} 
    //+++++++++++++++++++ end MouseMotionListener methods ++++++++++++++++++++
    
    //--------------------- paintComponent( Graphics ) -----------------
    /**
     * Need this if we intermix A-objects with the J-objects
     *   need to explicitly draw the A-class objects
     */
    public void paintComponent( Graphics aBrush ) 
    {
        super.paintComponent( aBrush );
        Graphics2D brush2D = ( Graphics2D ) aBrush;
        
        Iterator<AShape> iter = _ashapes.iterator();
        while ( iter.hasNext() )
            iter.next().display( brush2D ); 
    }
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
