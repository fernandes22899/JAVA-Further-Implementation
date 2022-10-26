import javax.swing.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.*;

/**Second.java.
  * 1P
  * 2/2/18
  * Makes a Stop and Yield Sign
  * @author jeff
  */
public class Second implements AShape
{
    private ArrayList<AShape> crazy = new ArrayList<AShape>();
    private Color bodyColor = Color.RED;
    private ARegularPoly stop, yield;
    private ARoundRectangle pole1, pole2;
    
    /**
     * Constructor.
     * makes roundrectangles and regularpoly
     */
    public Second()
    {
        pole1 = new ARoundRectangle( 8, 5 );
        pole1.setSize( 2, 45 );
        pole1.setColor( Color.GRAY );
        crazy.add( pole1 );
        
        pole2 = new ARoundRectangle( 28, 5 );
        pole2.setSize( 2, 45 );
        pole2.setColor( Color.GRAY );
        crazy.add( pole2 );
        
        stop = new ARegularPoly( 0, 0, 8, 10 );
        stop.setColor( Color.RED );
        stop.setRotation( 30 );
        crazy.add( stop );
        
        yield = new ARegularPoly( 22, 0, 3, 10 );
        yield.setFillColor( Color.WHITE );
        yield.setFrameColor( Color.RED );
        yield.setThickness( 4 );
        yield.setRotation( 93 );
        crazy.add( yield );
    }
    
    //----------------------- setColor( Color ) --------------------------
    /**
     * setColor -- a wheels method. Sets both frame and fill to same value.
     * 
     * @param c Color
     */
    public void setColor( Color c )
    {
        bodyColor = c;
        
        stop.setColor( c );
        yield.setColor( c );
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
        stop.setLocation( x, y );
        yield.setLocation( x + 22, y + 0 );
        pole1.setLocation( x + 8, y + 5 );
        pole2.setLocation( x + 28, y + 5 );
    }
    
    /**
     * Display method allows objects to show.
     * @param brush Graphics2D
     */
    public void display( java.awt.Graphics2D brush )
    {
        pole1.display( brush );
        pole2.display( brush );
        stop.display( brush );
        yield.display( brush );
    }
    
    /**
     * Invoke RegularPolyTest.main.
     * @param args String
     */
    public static void main( String[] args )
    {
        JFrame frame = new JFrame( "Second Test" );
        frame.setSize( 400, 300 );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        
        final Second building = new Second();
        
        JPanel jp = new JPanel()
        {
            public void paintComponent( Graphics g )
            {
                building.display( (Graphics2D) g );
            }
        };
        frame.add( jp );        // add it to the frame
        frame.setVisible( true );  // make it visible.   
    }
}