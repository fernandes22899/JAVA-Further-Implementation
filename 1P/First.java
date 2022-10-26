import javax.swing.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * First.java.
 * @author jeff
 * 1P
 * 2/2/18
 */
public class First implements AShape
{
    //----------------instance variables------------
    private AEllipse    wheel1, wheel2;
    private ARectangle  barUp;
    private ALine       barBack, barFront, barTop, barMiddle;
    private ARoundRectangle seat;
    private ARoundRectangle pedal;
    //private int xLoc, yLoc;
    private Color bodyColor = Color.BLACK;
    
    /**
     * Contructor calls methods.
     */
    public First()
    {
        wheel1 = new AEllipse();
        wheel1.setLocation( 50, 60 );
        wheel1.setSize( 30, 30 );
        wheel1.setThickness( 3 );
        wheel1.setFrameColor( Color.BLACK );
        wheel1.setFillColor( new Color( 0, 0, 0, 0 ) );
        
        wheel2 = new AEllipse();
        wheel2.setLocation( 0, 60 );
        wheel2.setSize( 30, 30 );
        wheel2.setThickness( 3 );
        wheel2.setFrameColor( Color.BLACK );
        wheel2.setFillColor( new Color( 0, 0, 0, 0 ) );
        
        barUp = new ARectangle();
        barUp.setLocation( 15, 74 );
        barUp.setSize( 30, 2 );
        barUp.setColor( Color.BLUE );
        
        barBack = new ALine( 15, 74, 30, 40 );
        barBack.setThickness( 3 );
        barBack.setColor( Color.BLUE );
        
        barTop = new ALine( 60, 40, 30, 40 );
        barTop.setThickness( 3 );
        barTop.setColor( Color.BLUE );
         
        barFront = new ALine( 65, 74, 60, 40 );
        barFront.setThickness( 3 );
        barFront.setColor( Color.BLUE );
        
        barMiddle = new ALine( 45, 72, 60, 40 );
        barMiddle.setThickness( 3 );
        barMiddle.setColor( Color.BLUE );
        
        seat = new ARoundRectangle();
        seat.setLocation( 30, 30 );
        seat.setSize( 18, 10 );
        seat.setArcSize( 14, 11 );
        seat.setColor( Color.BLACK );
        
        pedal = new ARoundRectangle();
        pedal.setLocation( 42, 72 );
        pedal.setSize( 15, 5 );
        pedal.setArcSize( 12, 8 );
        pedal.setColor( Color.BLACK );
        
        this.setLocation( 100, 100 );
    }
    
    /**
     * Constructer takes location.
     * @param xLoc int
     * @param yLoc int
     */
    public First( int xLoc, int yLoc )
    {
        wheel1 = new AEllipse();
        wheel1.setLocation( xLoc + 50, yLoc + 60 );
        wheel1.setSize( 30, 30 );
        wheel1.setThickness( 3 );
        wheel1.setFrameColor( Color.BLACK );
        wheel1.setFillColor( new Color( 0, 0, 0, 0 ) );
        
        wheel2 = new AEllipse();
        wheel2.setLocation( xLoc + 0, yLoc + 60 );
        wheel2.setSize( 30, 30 );
        wheel2.setThickness( 3 );
        wheel2.setFrameColor( Color.BLACK );
        wheel2.setFillColor( new Color( 0, 0, 0, 0 ) );
        
        barUp = new ARectangle();
        barUp.setLocation( xLoc + 15, yLoc + 74 );
        barUp.setSize( 30, 2 );
        barUp.setColor( Color.BLUE );
        
        barBack = new ALine( xLoc + 15, yLoc + 74, xLoc + 30, yLoc + 40 );
        barBack.setThickness( 3 );
        barBack.setColor( Color.BLUE );
        
        barTop = new ALine( xLoc + 60, yLoc + 40, xLoc + 30, yLoc + 40 );
        barTop.setThickness( 3 );
        barTop.setColor( Color.BLUE );
        
        barFront = new ALine( xLoc + 65, yLoc + 74, xLoc + 60, yLoc + 40 );
        barFront.setThickness( 3 );
        barFront.setColor( Color.BLUE );
        
        barMiddle = new ALine( xLoc + 45, yLoc + 72, xLoc + 60, yLoc + 40 );
        barMiddle.setThickness( 3 );
        barMiddle.setColor( Color.BLUE );
        
        seat = new ARoundRectangle();
        seat.setLocation( xLoc + 30, yLoc + 30 );
        seat.setSize( 18, 10 );
        seat.setArcSize( 14, 11 );
        seat.setColor( Color.BLACK );
        
        pedal = new ARoundRectangle();
        pedal.setLocation( xLoc + 42, yLoc + 72 );
        pedal.setSize( 15, 5 );
        pedal.setArcSize( 12, 8 );
        pedal.setColor( Color.BLACK );
        
        this.setLocation( xLoc, yLoc );
    }
    
    /**
     * Constructor takes color.
     * @param c Color
     */
    public First( Color c )
    {
        wheel1 = new AEllipse();
        wheel1.setLocation( 50, 60 );
        wheel1.setSize( 30, 30 );
        wheel1.setThickness( 3 );
        wheel1.setFrameColor( Color.BLACK );
        wheel1.setFillColor( new Color( 0, 0, 0, 0 ) );
        
        wheel2 = new AEllipse();
        wheel2.setLocation( 0, 60 );
        wheel2.setSize( 30, 30 );
        wheel2.setThickness( 3 );
        wheel2.setFrameColor( Color.BLACK );
        wheel2.setFillColor( new Color( 0, 0, 0, 0 ) );
        
        barUp = new ARectangle();
        barUp.setLocation( 15, 74 );
        barUp.setSize( 30, 2 );
        barUp.setColor( c );
        
        barBack = new ALine( 15, 74, 30, 40 );
        barBack.setThickness( 3 );
        barBack.setColor( c );
        
        barTop = new ALine( 60, 40, 30, 40 );
        barTop.setThickness( 3 );
        barTop.setColor( c );
        
        barFront = new ALine( 65, 74, 60, 40 );
        barFront.setThickness( 3 );
        barFront.setColor( c );
        
        barMiddle = new ALine( 45, 72, 60, 40 );
        barMiddle.setThickness( 3 );
        barMiddle.setColor( c );
        
        seat = new ARoundRectangle();
        seat.setLocation( 30, 30 );
        seat.setSize( 18, 10 );
        seat.setArcSize( 14, 11 );
        seat.setColor( Color.BLACK );
        
        pedal = new ARoundRectangle();
        pedal.setLocation( 42, 72 );
        pedal.setSize( 15, 5 );
        pedal.setArcSize( 12, 8 );
        pedal.setColor( Color.BLACK );
        
        this.setLocation( 0, 0 );
    }
    
    //----------------------- setColor( Color ) --------------------------
    /**
     * setColor\.
     * @param c Color
     */
    public void setColor( Color c )
    {
        bodyColor = c;
        
        barUp.setColor( c );
        barTop.setColor( c );
        barBack.setColor( c );
        barFront.setColor( c );
        barMiddle.setColor( c );
        
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
        wheel1.setLocation( x + 50, y + 60 );
        wheel2.setLocation( x + 0, y + 60 );
        barUp.setLocation( x + 15, y + 74 );
        barBack.setPoints( x + 15, y + 74, x + 30, y + 40 );
        barTop.setPoints( x + 60, y + 40, x + 30, y + 40 );
        barFront.setPoints( x + 65, y + 74, x + 60, y + 40 );
        barMiddle.setPoints( x + 45, y + 72, x + 60, y + 40 );
        seat.setLocation( x + 30, y + 30 );
        pedal.setLocation( x + 42, y + 72 );
    }
    
    //----------------------- getColor() ---------------------------------
    /**
     * getColor -- a wheels method -- returns fillColor.
     *
     * @return Color
     */
    public Color getColor()
    {
        return bodyColor;
    }
    
    //----------------------- display( Graphics2D ) ----------------------
    /**
     * display - calls draw and fill awt methods (this is an rdb method).
     * @param brush Graphics2D
     */
    public void display( java.awt.Graphics2D brush )
    {
        wheel1.display( brush );
        wheel2.display( brush );
        barUp.display( brush );
        barBack.display( brush );
        barFront.display( brush );
        barTop.display( brush );
        barMiddle.display( brush );
        seat.display( brush );
        pedal.display( brush );
    }
    
    //-------------------- main ------------------------------------------
    /**
     * Invoke First.main.
     * @param args String
     */
    public static void main( String[] args )
    {
        JFrame frame = new JFrame( "First Test" );
        frame.setSize( 400, 300 );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        
        final First rob = new First( Color.BLUE );
        
        final First rob2 = new First( 50, 50 );
        rob2.setColor( Color.GREEN );
        
        
        JPanel jp = new JPanel()
        {
            public void paintComponent( Graphics g )
            {
                rob.display( ( Graphics2D ) g );
                rob2.display( ( Graphics2D ) g );
            }
        };
        frame.add( jp );        // add it to the frame
        frame.setVisible( true );  // make it visible.     
        
    }
}