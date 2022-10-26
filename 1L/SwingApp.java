//++++++++++++++++++++++++++ SwingApp.java ++++++++++++++++++++++++++
import javax.swing.*;

/**
 * SwingApp.java -- CS416 Lab 1 -- Spring 2010
 * 
 * This is a Swing application based on the pattern described in Chapter 7
 * of Sanders and van Dam, Object-Oriented Programming in Java.
 * 
 * All the application-specific code is in the DrawPanel class
 * 
 * @author rdb
 * Last modified: 01/13/15: make checkstyle-compatible
 */

public class SwingApp extends JFrame
{
    //------------------ constructor ---------------------------------
    /**
     * Construct a JFrame for the application and put a JPanel in it.
     * 
     * @param title String  Frame title
     * @param args String[] Command line arguments
     */
    public SwingApp( String title, String[] args )
    {
        super( title );            // specify window title
        this.setSize( 700, 500 );  // define window size
        
        this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        
        // Application graphics specific code is in DrawPanel constructor.
        // DrawPanel extends JPanel. 
        
        DrawPanel panel = new DrawPanel( args );  // graphics in JPanel
        this.add( panel );                        // add to the frame
        
        this.setVisible( true );  // make frame visible
    }
    
    //------------------------ main -----------------------------------
    /**
     * Application start.
     * 
     * @param args String[] Command line arguments
     */
    public static void main( String[] args )
    {
        SwingApp app = new SwingApp( "Drawing in AWT/Swing", args );
    }
}