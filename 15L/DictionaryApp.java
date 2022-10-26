//--------------------------- DictionaryApp ---------------------------
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.border.*;
/**
 * DictionaryApp - basis for experimenting with different kinds of 
 *                 dictionaries.
 * 
 * This class creates a GUI .
 * 
 * @author rdb
 * 03/03/08
 * Last edited: 
 * 03/17/14  rdb: made checkstyle-compatible
 */

public class DictionaryApp extends JFrame
{
    //---------------------- instance variables ----------------------
    private GUI _appPanel;
    
    //--------------------------- constructor -----------------------
    /**
     * Constructor needs frame title.
     * @param title String
     * @param args String[]            command line args not used
     */
    public DictionaryApp( String title, String[] args )     
    {
        super( title );
        
        this.setBackground( Color.WHITE );
        this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        
        _appPanel = new GUI();
        this.add( _appPanel );
        
        this.setSize( new Dimension( 800, 700 ) );
        this.setVisible( true );
    }
    //------------------ main ------------------------------------------   
    /**
     * Convenience invocation of the app.
     * @param args String[]    passed on.
     */
    public static void main( String [ ] args ) 
    {
        new DictionaryApp( "DictionaryApp", args );
    }
}