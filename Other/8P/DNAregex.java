import java.util.*;
import java.util.regex.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;

/**
 * DNAregex -- Regular expression exploration. Mainly in the context 
 *              of processing DNA data
 * 
 *  The program expects a single command line argument that is a file name
 * for a file that includes a number of regular expressions (one per line)
 * that are the initial tests for the program (and the user). 
 * This program checks that the filename represents a valid file, but it
 * does not open the file. It will give an error if the filename is not the
 * name of a file.
 * 
 * @author rdb
 * CS416 Spring 2013
 */

public class DNAregex extends JFrame
{
    //---------------------- instance variables ----------------------   
    //--------------------------- constructor -----------------------
    /**
     * Constructor to set up GUI elements.
     *
     * @param title  Title for window.
     * @param patternFileName  File name for regex patterns.
     * @param dnaFileName  File name for DNA input.
     */
    public DNAregex( String title, String patternFileName,
                     String dnaFileName  ) 
    {      
        super( title );
        GUI   gui = new GUI( patternFileName, dnaFileName );      
        add( gui );
        
        this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        
        this.pack();
        this.setSize(  new Dimension( gui.getWidth(), gui.getHeight() + 100 ) );
        this.setVisible( true );
    }
    
    //--------------------- main -----------------------------------------
    /**
     * Application startup.
     * 
     * @param args String[]  May contain DNA input 
     * file name and pattern file name.
     */    
    public static void main( String[] args )
    {
        String patternFileName = "patternsDNA.txt";
        String dnaFileName = "inputDNA.txt";
        if ( args.length > 0 )
            patternFileName = args[ 0 ];
        if ( args.length > 1 )
            dnaFileName = args[ 1 ];
        
        new DNAregex( "DNA Regex", patternFileName, dnaFileName );
    }
}
