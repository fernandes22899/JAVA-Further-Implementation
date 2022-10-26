
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * RoundTest.java -- a skeleton for a comprehensive test of
 *    ARoundRectangle. This should be expanded sufficiently that
 *    it is clear from looking at the output that you have tested
 *    the ARoundRectangle thoroughly and understand its parameters.
 * @author others and jeff
 */
public class RoundTest extends JPanel
{
    //-------------- instance variables ------------------------------
    private ArrayList<AShape> shapes = new ArrayList<AShape>();
    private Point p = new Point( 100, 00 );
    //------------------ constructor ---------------------------------
    /**
     * roundtest contructer calls methods.
     */
    public RoundTest( )
    {
        ARoundRectangle rr0 = 
                  new ARoundRectangle( 90, 90, 40, 40, 180, 180 );
        rr0.setColor( Color.RED );
        rr0.setLocation( p );
        shapes.add( rr0 );
        
        ARoundRectangle rr1 = new ARoundRectangle( Color.BLUE );
        rr1.setLocation( 10, 10 );
        rr1.setArcSize( 10, 15 );
        shapes.add( rr1 );
        
        ARoundRectangle rr2 = new ARoundRectangle();
        rr2.setFillColor( Color.GREEN );
        rr2.setFrameColor( Color.RED );
        rr2.setThickness( 3 );
        rr2.setLocation( rr1.getYLocation(), rr0.getXLocation() );
        shapes.add( rr2 );
        
        ARoundRectangle rr3 = new ARoundRectangle( 0, 0 );
        rr3.setFillColor( rr2.getFrameColor() );
        rr3.setFrameColor( rr2.getFillColor() );
        rr3.setLineWidth( 7 );
        rr3.moveBy( 200, 200 );
        shapes.add( rr3 );
        
        First f = new First();
        f.setColor( new Color( 167, 177, 255, 75 ) );
        shapes.add( f );
        
        First f2 = new First( 150, 40 );
        f2.setColor( f.getColor() );
        shapes.add( f2 );
        
        First f3 = new First( Color.CYAN );
        f3.setLocation( 50, 200 );
        shapes.add( f3 );
        
        Second s = new Second();
        s.setLocation( 150, 0 );
        shapes.add( s );
        
        Second s2 = new Second();
        s2.setLocation( 50, 50 );
        s2.setColor( Color.BLUE );
        shapes.add( s2 );
        
    }
    //------------- paintComponent( Graphics ) ----------------------
    /**
     * paintComponent paints objects.
     * @param g Graphics
     */
    public void paintComponent( Graphics g )
    {
        super.paintComponent( g );
        //////////////////////////////////////////////////////////////
        // invoke display method of all AShape objects
        //////////////////////////////////////////////////////////////
        Graphics2D g2D = (Graphics2D) g;
        
        for( AShape shape: shapes )
            shape.display( g2D );
        
    }
    
    //------------------------ main -----------------------------------
    /**
     * Invoke RegularPolyTest.main.
     * @param args String
     */
    public static void  main( String[] args )
    {
        JFrame f = new JFrame( "ARoundRectangle test" );
        f.setSize( 500, 400 );
        f.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        JPanel panel = new RoundTest();
        f.add( panel );
        f.setVisible( true ); 
    }            
}