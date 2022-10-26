//----------------------- Sudoku.java ----------------------------
import java.io.*;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

/**
 * Sudoku puzzle solver.
 * 
 * @author rdb
 * Created on Oct 10, 2005
 *
 */
public class Sudoku extends JFrame
{
    //----------------- class variables --------------------------
    static int frameWidth = 700;
    static int frameHeight = 700;
    //----------------- instance variables -----------------------
    
    //-------------------- constructors -------------------------------
    /**
     * Constructor takes a file name as argument.
     * 
     * @param title  String   Window title
     * @param inFile String   file name for board description.
     * @throws IOException if no file
     */
    public Sudoku( String title, String inFile ) throws IOException
    {
        super( title );
        this.setSize( frameWidth, frameHeight );
        this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
       
        GUI appGUI = new GUI( inFile );
        this.add( appGUI );
        
        this.setVisible( true );
    }
    
    //---------- main: app invocation  --------------------------
    /**
     * app test.
     * 
     * @throws IOException if file doesn't exist or can't be opened
     * @param argv String[]  command line arguments
     */
    public static void main( String[] argv ) throws IOException
    {
        if ( argv.length == 0 )
            new Sudoku( "Sudoku", "test0-nobackup.txt" );
        else
            new Sudoku( "Sudoku", argv[ 0 ] );
    }
}
