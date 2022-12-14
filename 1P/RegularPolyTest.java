
import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * RegularPolyTest.java -- this is a skeleton for a program to.
 *             thoroughly test the ARegularPoly class.
 * 
 *  It needs to be significantly expanded. 
 *       1. At the very least, every public method of ARegularPoly needs 
 *          to be invoked at least once -- even methods you didn't edit! 
 *          You must verify that you didn't break them. 
 *       2. And, every option of every method you did write needs to be
 *          tested thoroughly.
 *          
 * @author rdb
 * Last edited: 01/01/14
 */
public class RegularPolyTest extends JPanel
{
    //-------------- instance variables ------------------------------
    private ArrayList<AShape> shapes = new ArrayList<AShape>();
    private Point p = new Point( 100, 200 );
    //------------------ constructor ---------------------------------
    /**
     * RegularPolyTest calls methods.
     */
    public RegularPolyTest( )
    {
        ////////////////////////////////////////////////////////
        // Create enough ARegularPoly objects to show that you 
        //   have thoroughly tested this code. At the very least,
        //   your tests should cause the execution of every public method
        //   in the class (not just the ones you had to write).
        ////////////////////////////////////////////////////////
        ARegularPoly one = new ARegularPoly( 0, 0, 5, 5 );
        one.setLocation( 200, 70 );
        one.setColor( Color.RED );
        one.setRotation( 135 );
        one.setRadius( 50 );
        shapes.add( one );
        
        ARegularPoly two = new ARegularPoly( 0, 0, 3, 40 );
        two.setLocation( p );
        two.setFillColor( new Color( 0, 200, 100, 90 ) );
        two.setFrameColor( Color.RED );
        two.setThickness( 6 );
        two.setRotation( 30 );
        shapes.add( two );
        
        ARegularPoly three = new ARegularPoly( 10, 10, 7, 50 );
        three.moveBy( 0, 100 );
        three.setFillColor( Color.CYAN );
        three.setFrameColor( Color.BLUE );
        three.setLineWidth( 8 );
        shapes.add( three );
        
        ARegularPoly four = new ARegularPoly( 0, 0, 5, 30 );
        four.setLocation( three.getYLocation(), three.getXLocation() );
        four.setSize( 0, 30 );
        shapes.add( four );
    }
    //------------- paintComponent( Graphics ) ----------------------
    /**
     * paintComponent paints objects.
     * @param g Graphics
     */
    public void paintComponent( Graphics g )
    {
        super.paintComponent( g );
        //////////////////////////////////////////////////////////
        // draw the objects you created in the constructor
        //////////////////////////////////////////////////////////
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
        JFrame f = new JFrame( "ARegularPoly test" );
        f.setSize( 700, 600 );
        f.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        JPanel panel = new RegularPolyTest();
        f.add( panel );
        f.setVisible( true ); 
    }            
}