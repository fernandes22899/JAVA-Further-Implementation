/** 
 * AWTPanel.java: Swing panel for AWT draw assignment.
 * 
 * This is a framework for the main display window for an 
 * awt application. 
 *      
 * @author rdb
 * 01/10/08 
 * 01/22/11 rdb modified for start code for P1, Spring 2011
 */

import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;

/**
 * AWTPanel.java.
 * Gets objects to come together.
 * @author jeff
 * 1P
 * 2/2/18
 */
public class AWTPanel extends JPanel
{ 
    //-------------------- instance variables ----------------------
    //
    // declare here (among other things) the variables that reference
    //   AWT display objects (ellipses, rectangles, lines, etc.)
    //private House     robot1, robot2;
    
    private ArrayList<AShape> shapes = new ArrayList<AShape>();
    private AEllipse ell;
    private ARectangle rect;
    private ARoundRectangle rrect;
    private ALine lin;
    
    //------------- Constructor ---------------------------------
    /**
     * Create awt objects to be displayed.
     * @param args String
     */
    public AWTPanel( String [] args ) 
    {
        // Create objects you want to display using awt graphical objects.
        // Use wheels-like "wrapper" classes, ARectangle, AEllipse, ALine
        // The ARectangle and AEllipse classes are minor variations of
        // SmartRectangle and SmartEllipse from the Sanders and van Dam.
        //
        // References to the objects you create need to be saved in an
        //   ArrayList or Vector of AShape objects.
        //
        
        rect = new ARectangle( Color.BLUE );
        rect.setLocation( 200, 200 );
        rect.setSize( 30, 40 );
        shapes.add( rect );
        
        ell = new AEllipse( Color.RED );
        ell.setLocation( 180, 170 );
        ell.setSize( 20, 20 );
        shapes.add( ell );
        
        rrect = new ARoundRectangle( Color.GREEN );
        rrect.setLocation( 240, 250 );
        rrect.setSize( 30, 30 );
        shapes.add( rrect );
        
        lin = new ALine( 200, 180, 250, 200 );
        shapes.add( lin );
        
        First robot = new First( Color.YELLOW );
        shapes.add( robot );
        
        First robot2 = new First();
        robot2.setLocation( 100, 0 );
        shapes.add( robot2 );
        
        First robot3 = new First( 0, 100 );
        shapes.add( robot3 );
        
        Second test = new Second();
        shapes.add( test );
        
    }  
    
    //------------- paintComponent ---------------------------------------
    /**
     * This method is called from the Java environment when it determines.
     * that the JPanel display should be updated; you need to 
     * make appropriate calls here to re-draw the graphical images on
     * the display. Each object created in the constructor above should 
     * have its "fill" and/or "draw" methods invoked with a Graphics2D 
     * object. The Graphics object passed to paintComponent will be a 
     * a Graphics2D object, so it can be coerced to that type and
     * passed along to the "display" method of the objects you created.
     * 
     * Note that the "display" method is not an awt method; it is a
     * convenience method defined by the "wrapper" classes. 
     * The display method usually passes the graphical objects to both 
     * the Graphics2D.fill and Graphics2D.draw methods, except in the 
     * case of ALine graphical objects which cannot be "filled".
     * @param aBrush Graphics
     */
    public void paintComponent( Graphics aBrush )
    {
        super.paintComponent( aBrush );
        Graphics2D newBrush = (Graphics2D) aBrush; // coerce to Graphics2D
        
        //////////////////////////////////////////////////////////////
        // invoke display( newBrush ) for all A-objects in scene
        //////////////////////////////////////////////////////////////
        for( AShape i:shapes )
            i.display( newBrush );
    }
    //-------------------- main ------------------------------------------
    /**
     * Invoke AWTApp.main.
     * @param args String
     */
    public static void main( String[] args )
    {
        AWTApp.main( args );
    }
}
