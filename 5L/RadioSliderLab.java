//++++++++++++++++++++++++++++ RadioSliderLab +++++++++++++++++++++++++
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

/**
 * RadioSliderLab
 * Based on the canonical framework for a Swing application with a GUI.
 *
 * 2/4/09 
 * @author rdb 
 * 
 * Last edited: 02/04/15 made checkstyle-compatible
 */
public class RadioSliderLab extends JFrame 
{
    /**
     * Constructor takes window title.
     * @param title String
     */
    public RadioSliderLab( String title ) 
    {
        super( title );
        this.setSize( 700, 600 );
        this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        
        RadioSliderGUI appGUI = new RadioSliderGUI();
        
        this.add( appGUI );
        this.setVisible( true );
    }    

    //------------------------------- main -------------------------------
    /**
     * Application invocation.
     * @param args String[]  command line args -- not used
     */
    public static void main( String [] args ) 
    {
        RadioSliderLab app = new RadioSliderLab( "RadioSliderLab" );
    }
}
