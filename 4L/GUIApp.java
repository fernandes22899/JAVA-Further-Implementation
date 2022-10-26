/**
 * GUIApp.java -- Based on the canonical "main" program, SwingApp,
 *                  for a Swing application, SwingApp with GUI widgets
 *                  and a draw panel for graphics output.
 * 
 * This is a bouncing JSnowMan
 *
 * 1/31/09 rdb: 
 *      renamed MovePanel to MoverGUI: it has expanded responsibilities
 */
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

public class GUIApp extends JFrame 
{
    public GUIApp( String title ) 
    {
        super( title );
        int width = 700;
        int height = 500;
        
        this.setSize( width, height );
        this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        
        MoverGUI appGUI = new MoverGUI();    
        
        this.add( appGUI );
        this.setVisible( true );
    }
    
    //------------------------------- main -------------------------------
    /**
     * The application start.
     * @param args String[] command line arguments.
     */
    public static void main( String [] args ) 
    {
        // Command line args are all parameters to MoverGUI
        //    It's a cleaner design for the main app to "know" only that
        //    the arguments need to go to MoverGUI. The alternative is
        //    for this class to know that PLUS know what all the arguments
        //    are and how to set them in the MoverGUI class.
        // So, in this case we have a static method of MoverGUI that 
        //    knows how to get the arguments.
        MoverGUI.readArgs( args );
        GUIApp app = new GUIApp( "GUIApp demo" );
    }
}
