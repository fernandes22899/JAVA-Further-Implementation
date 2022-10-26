//------------------------ TreeRecursion.java -----------------------
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
/**
 * TreeRecursion -- Tree exercise lab.
 * 
 * @author rdb
 * Last edit
 * 03/16/14 rdb: made checkstyle compatible
 */

public class TreeRecursion extends JFrame
{
    //---------------------- instance variables ----------------------   
    //--------------------------- constructor -----------------------
    /**
     * Constructor.
     * @param title String       frame title
     */
    public TreeRecursion( String title ) 
    {      
        super( title );
        GUI gui = new GUI();
        
        add( gui );
        this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        
        this.pack();
        this.setSize(  new Dimension( gui.getWidth() + 300, 
                                     gui.getHeight() + 100 ) );
        this.setVisible( true );
    }
    //--------------------- main -----------------------------------------
    /**
     * main starts up the application.
     * @param args String[]   command line args -- not used.
     */
    public static void main( String[] args )
    {
        TreeRecursion app = new TreeRecursion( "Tree Recursion Lab" );
    }
}