//+++++++++++++++++++++++ JSnowMan.java ++++++++++++++++++++++++++++++
import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;
/** 
 * JSnowMan.java:
 * 
 * Displays a simple snow man using multiple Wheels Shapes.
 * The entire snowman is built in the constructor.
 * 
 * @author rdb
 * Created 9/11/07; derived from cs415 demo program, Start.java 
 * 01/28/10 rdb: revised to put JComponents into JSnowMan.
 * 01/31/10 rdb: revised to use Animated interface, rather than Movable
 */

public class JSnowMan extends JComponent 
    implements Animated, MouseListener, MouseMotionListener
{
    //---------------- instance variables ------------------------------
    /////////////////////////////////////////////////////////////////////
    // 8. Lab 4 changes.
    // a. Change move parameters, _dX and _dY, to float
    // b-c. See newFrame method
    // d-e. Change setMove, getMoveX, getMoveY to use floats.
    ////////////////////////////////////////////////////////////////////
    private float   _dX;           // steps sizes for each move
    private float   _dY;
    private Point _lastMouse = null;
    
    private Vector<JShape>  _components; // components of this composite
    
    // -----------------------------------------------------------------
    /** 
     * Constructor for the JSnowMan class. 
     *    Container argument is needed by this Composite class: it defines
     *        the Container in which the snow man is allowed to move
     *    x,y are the position of the JSnowMan.
     * @param x int
     * @param y int
     */
    public JSnowMan( int x, int y )
    {
        _components = new Vector<JShape>();
        
        buildHat( );   // create the hat and its brim  
        buildBody( );   // create the body
        buildArms( );   // create some arms
        buildFace( );   // create the face      
        buildHead( );   // create the head
        
        setLocation( x, y );
        
        this.addMouseListener( this );
        this.addMouseMotionListener( this );   
    }
    //---------------- add( JComponent ) ---------------------------
    private Rectangle  _bounds = null;    // instance variable
    
    /**
     * override add method to compute and set bounds information as 
     * components are added to the object.
     * @param comp JComponent
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
    //++++++++++++ mouseListener/mouseMotionListeer methods ++++++++++
    /** mousePress event saves location. 
      * @param me MouseEvent 
      */
    public void mousePressed( MouseEvent me )
    { 
        _lastMouse = getParent().getMousePosition();
        //System.out.println( "JSnowMan pressed at " + _lastMouse ); 
    }   
    /** mouseDragged event used to move the Snowman. 
      * @param me MouseEvent 
      */
    public void mouseDragged( MouseEvent me )
    { 
        Point curMouse;
        curMouse = getParent().getMousePosition();
        
        if ( _lastMouse != null && curMouse != null
                && !curMouse.equals( _lastMouse ) ) 
        {
            //System.out.println( "JSnowMan dragged to " + curMouse ); 
            setLocation( this.getX() + curMouse.x - _lastMouse.x, 
                        this.getY() + curMouse.y - _lastMouse.y );
            _lastMouse = curMouse;
            getParent().repaint();
        }
    }  
    /** Unimplemented interface method. @param me MouseEvent */
    public void mouseClicked( MouseEvent me ) 
    { 
        //System.out.println( "JSnowMan mouse clicked" ); 
    }   
    /** Unimplemented interface method. @param me MouseEvent */
    public void mouseEntered( MouseEvent me ) 
    { 
        //System.out.println( "JSnowMan mouse entered" ); 
    }   
    /** Unimplemented interface method. @param me MouseEvent */
    public void mouseExited( MouseEvent me ) 
    { 
        //System.out.println( "JSnowMan mouse exited" ); 
    }   
    /** Unimplemented interface method. @param me MouseEvent */
    public void mouseMoved( MouseEvent me ) 
    { 
        //System.out.println( "JSnowMan mouse moved" ); 
    }   
    /** Unimplemented interface method. @param me MouseEvent */
    public void mouseReleased( MouseEvent me )
    { 
        //System.out.println( "JSnowMan mouse released" ); 
    }   
    
    //++++++++++++++++++ component building methods +++++++++++++++++++
    //----------------------- buildFace ------------------------------
    /**
     * build the components for the face.
     */
    private void buildFace()
    {
        int[]  smileX     = { 40, 45, 55, 60 };
        int[]  smileY     = { 43, 45, 45, 43 };
        
        int    leftEyeX  = 40,  leftEyeY  = 30;
        int    rightEyeX = 55,  rightEyeY = 30;
        int    eyeSize   = 4;
        
        // create the two eyes
        JEllipse leftEye = new JEllipse( leftEyeX, leftEyeY );
        leftEye.setColor( Color.BLACK );
        leftEye.setSize( eyeSize, eyeSize );
        _components.add( leftEye );
        this.add( leftEye );
        
        JEllipse rightEye = new JEllipse( rightEyeX, rightEyeY );
        rightEye.setColor( Color.BLACK );
        rightEye.setSize( eyeSize, eyeSize );
        _components.add( rightEye );
        this.add( rightEye );
        
        // create a smile
        JLine[] smile = new JLine[ smileX.length - 1 ];
        for ( int i = 0; i < smileX.length - 1; i++ )
        {
            smile[ i ] = new JLine( Color.BLACK );
            smile[ i ].setPoints(  smileX[ i ], smileY[ i ], 
                                 smileX[ i + 1 ], smileY[ i + 1 ] );
            smile[ i ].setLineWidth( 2 );
            _components.add( smile[ i ] );
            this.add( smile[ i ] );
        }
    }
    //----------------------- buildHat ------------------------------
    /**
     * build the components for the hat.
     */
    private void buildHat()
    {
        int    brimX  = 30,  brimY  = 15;
        int    brimW  = 40,  brimH = 4;
        int    hatX   = 38,  hatY   = 0;
        int    hatW   = 25,  hatH   = 13;
        
        JRectangle hatBody = new JRectangle( hatX, hatY );
        hatBody.setSize( hatW, hatH );
        hatBody.setColor( Color.BLACK );
        _components.add( hatBody );
        this.add( hatBody );
        
        JRectangle hatBrim = new JRectangle( brimX, brimY );
        hatBrim.setSize( brimW, brimH );
        hatBrim.setColor( Color.BLACK );
        _components.add( hatBrim );
        this.add( hatBrim );
    }
    //----------------------- buildBody ------------------------------
    /**
     * build the components for the body.
     */
    private void buildBody()
    {
        int    bodyX     = 10,  bodyY     = 55;
        int    bodySize  = 80; 
        
        JEllipse body = new JEllipse( bodyX, bodyY );
        body.setSize( bodySize, bodySize );
        body.setFillColor( Color.WHITE );
        body.setFrameColor( Color.RED );
        body.setLineWidth( 2 );
        _components.add( body );
        this.add( body );
    }
    //----------------------- buildArms ------------------------------
    /**
     * build the components for the arms.
     */
    private void buildArms()
    {
        int    lArmX1 = 0, lArmY1 = 35, lArmX2 = 15, lArmY2 = 75;
        int    rArmX1 = 85, rArmY1 = 75, rArmX2 = 120, rArmY2 = 55;
        
        JLine leftArm = new JLine( Color.BLACK );
        leftArm.setPoints( lArmX1, lArmY1, 
                          lArmX2, lArmY2 );
        leftArm.setLineWidth( 3 );
        _components.add( leftArm );
        this.add( leftArm );
        
        JLine rightArm = new JLine( Color.BLACK  );
        rightArm.setPoints( rArmX1, rArmY1, 
                           rArmX2, rArmY2 );
        rightArm.setLineWidth( 3 );
        _components.add( rightArm );
        this.add( rightArm );
    }
    //----------------------- buildHead ------------------------------
    /**
     * build the components for the head.
     */
    private void buildHead()
    {
        int    headX     = 25,  headY     = 15;
        int    headSize  = 50;
        
        JEllipse head = new JEllipse( headX, headY );
        head.setSize( headSize, headSize );
        head.setFillColor( java.awt.Color.WHITE );
        _components.add( head );
        this.add( head );
    } 
    
    //------------------------ setLocation( Point ) ------------------
    /**
     * display the snowman at the specified Point.
     * @param p Point
     */
    public void setLocation( Point p ) 
    {
        setLocation( p.x, p.y );
    }
    
    //+++++++++++++++++++++++ Animated interface +++++++++++++++++++++
    //++++++++++++++++++++ Animated interface methods ++++++++++++++++
    private boolean _animated = true;  // instance variable
    //---------------------- isAnimated() ----------------------------
    /**
     * Return animated state.
     * @return boolean
     */
    public boolean isAnimated()
    {
        return _animated;
    }
    //---------------------- setAnimated( boolean ) ------------------
    /**
     * Set animated state.
     * @param onOff boolean
     */
    public void setAnimated( boolean onOff )
    {
        _animated = onOff;
    }
    //---------------------- newFrame() ------------------------------
    /////////////////////////////////////////////////////////////////////
    // 8. Lab 4 changes.
    // a. Change move parameters, _dX and _dY, to float
    //---------------------------------------------------
    // b. Change the variables and computations in newFrame to use float.
    // c. Change the setLocation at the end of newFrame to convert nextX and
    //    nextY to int using Math.round
    //---------------------------------------------------
    // d. Change setMove to accept float parameters
    // e. Change getMoveX, and getMoveY to return floats.
    //////////////////////////////////////////////////////////////////////
    /**
     * move the JSnowMan by the specified incremental steps.
     */
    public void newFrame() 
    {
        // get the expected next location
        float nextX = this.getX() + _dX;
        float nextY = this.getY() + _dY;
        float maxX  = nextX + this.getWidth();
        float maxY  = nextY + this.getHeight();
        
        // but check if we have hit a boundary of the panel we're in
        // first check x bounds: left and right. Since the JSnowMan 
        // is quite large, this is the "safer" version, that 
        // prevents any of the JSnowMan from going outside the panel.
        if ( nextX < 0 )     // outside on the left?
        {
            _dX = - _dX;     // if so, reverse x increment
            nextX = 0; 
        }
        else if ( maxX > getParent().getWidth()  ) // out on right?
        {                                     
            _dX = - _dX;    // if so, reverse x increment
            nextX = getParent().getWidth() - this.getWidth();
        }
        if ( nextY < 0 )       // outside on the top?
        {
            _dY = - _dY;       // if so, reverse x increment
            nextY = 0; 
        }
        else if ( maxY > getParent().getHeight() ) // out on bottom?
        {                                     
            _dY = - _dY;          // if so, reverse x increment
            nextY = getParent().getHeight() - this.getHeight();
        }
        this.setLocation( nextX, nextY );   // update location
        this.repaint();
    }
    //----------------------- setMove( int, int ) -------------------
    /**
     * set the move increments.
     * @param dx int
     * @param dy int
     */
    public void setMove( float dx, float dy )
    {
        _dX = dx;
        _dY = dy;
    }
    //---------------------- int getMoveX() -------------------------
    /**
     * return the x move increment.
     * @return int
     */
    public float getMoveX()
    {
        return _dX;
    }
    //---------------------- int getMoveY() --------------------------
    /**
     * return the y move increment.
     * @return int
     */
    public float getMoveY()
    {
        return _dY;
    }
}
