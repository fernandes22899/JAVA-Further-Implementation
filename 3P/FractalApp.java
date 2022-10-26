import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

/**
 * FractalApp.java -- Based on the canonical "main" program, SwingApp,
 *                  for a Swing application, SwingApp with GUI widgets
 *                  and a draw panel for graphics output.
 * 
 * @author rdb
 *
 * 1/31/09 rdb: 
 *      renamed MovePanel to FractalGUI: it has expanded responsibilities
 */
public class FractalApp extends JFrame 
{
    static int frameWidth = 750;
    static int frameHeight = 750;

    /**
     * Constructor with title and args.
     *
     * @param title Window title.
     * @param args Array of arguments.
     *
     */
    public FractalApp( String title, String[] args ) 
    {
        super( title );
        this.setSize( frameWidth, frameHeight );
        this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        FractalGUI appGUI = new FractalGUI( this );
      
        this.add( appGUI );
        this.setVisible( true );
    }

    //------------------------------- main -------------------------------
    /**
     * Main method.
     *
     * @param args Array of arguments.
     *
     */
    public static void main( String [] args ) 
    {
        FractalApp app = new FractalApp( "FractalApp demo", args );
    }
}
