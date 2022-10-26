//++++++++++++++++++++++++++++++ RegexInJava +++++++++++++++++++++++++++
import javax.swing.*;
import java.awt.*;

/**
 * RegexInJava: Framework for testing Regex.java.
 * 
 * @author rdb
 * Last edited: 04/10/14
 */

public class RegexInJava extends JFrame
{
    //---------------------- instance variables ----------------------   
    //--------------------------- constructor -----------------------
    public RegexInJava( String title, String[] args ) 
    {      
        super( title );
        GUI   gui = new GUI( this );
        
        add( gui );
        this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        
        this.pack();
        this.setSize(  new Dimension( gui.getWidth(), gui.getHeight() + 100 ));
        this.setVisible( true );
    }
    //--------------------- main -----------------------------------------
    public static void main( String[] args )
    {
        RegexInJava r = new RegexInJava( "Regex in Java", args );
    }
}