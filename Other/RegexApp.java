import javax.swing.*;
import java.util.*;
import java.util.regex.*;
import java.io.*;
import java.text.*;

/**
 * RegexApp - the main class for controlling the application code
 *             for the Regular Expression DNA Exploration Tool.
 * CS416
 * Spring 2013
 * @author rdb 
 */

public class RegexApp
{
    //---------------------  class variables -------------------------------
    static  boolean          plainText = false;
    
    //--------------------- instance variables ----------------------------
    private RegexPanel       _display;          // edit pane display area
    private GUI              _gui;         // not much used
    private JFileChooser     _chooser   = null; // for opening files
    private Scanner          _dnaIn     = null; // for reading sequence data
    private String           _seqHeader = null; // dna sequence header info
    
    private Pattern          _pattern   = null; // pattern corr. to r.e.
    
    private String           _regex;          // the currently active reg expr
    private String           _input;          // the current input data
    private int              _seqLineLen;     // size of input sequence lines
                                              // used to make output the same
    private Vector<String>   _patterns;       // pre-defined patterns from file
    private int              _nxtPattern = 0; // next pre-defined pattern to use
    
    //---------------------- constructor ----------------------------------
    /**
     * The app needs the display reference only so it can update the 
     * data for the edit panes.
     * 
     * @param display  Display panel.
     * @param gui  GUI.
     * @param patFileName  File name for regex patterns.
     * @param dnaFileName  File name for DNA input.
     */
    public RegexApp( RegexPanel display, GUI gui, 
                    String patFileName, String dnaFileName )
    {
        _display   = display;
        _gui  = gui;
        _regex     = "";
        
        // Define a File chooser dialog box with its current directory at the
        // current directory of the user. 
        _chooser = new JFileChooser( "." );
                
        readPatternFile( patFileName );
        readDNAfile( dnaFileName );
    }

    //---------------------- readDNAfile() -----------------------------
    /**
     * opens a dna multi-fasta file; and calls nextDNAseq to read the first
     * sequence in the file to become the input test data.
     * Called from GUI.  Uses the JFileChooser class.
     */
    public void readDNAfile( )
    {
        //////////////////// 8P code ////////////////////////////////
        // This is similar to code for the pattern file
        /////////////////////////////////////////////////////////////
        
        
        
    }
    
    //---------------------- readDNAfile( String ) -------------------------
    /**
     * opens a dna multi-fasta file; and calls nextDNAseq to read the first
     * sequence in the file to become the input test data.
     * Maps a String filename to an File object.
     *
     * @param dnaFileName  File name for DNA input.
     */
    public void readDNAfile( String dnaFileName )
    {
        //////////////////// 8P code ////////////////////////////////
        // This is similar to code for the pattern file
        /////////////////////////////////////////////////////////////
        
        
        
        
    }
    //---------------------- readDNAfile( File ) -------------------------
    /**
     * opens a dna multi-fasta file; and calls nextDNAseq to read the first
     * sequence in the file to become the input test data.
     * This is the actual reading code.
     *
     * @param dnaFile  File for DNA sequences.
     */
    public void readDNAfile( File dnaFile )
    {
        //////////////////// 8P code //////////////////////////////// 
        // Be sure to break the task into multiple methods.
        /////////////////////////////////////////////////////////////
        
    }
 

   //---------------------- nextDNAseq() -----------------------------
    /**
     * Read the next dna sequence from the file -- if it is there. 
     * This method will make all the data lower case.
     *
     * It generates 2 versions of the input data:
     *    displayContents: line-preserved version for display in the
     *                     JEditorPane window
     *    contents: has all eol data removed so the sequence has no interior
     *              "white" space. Need this for proper testing of patterns;
     *              line feeds are not part of real DNA.
     * It also (must) read "ahead" 1 line too many, since it keeps reading
     *   lines of input that represent the dna data until it reads a
     *   line representing the header for the next sequence of dna.
     * So, it "saves" the next header in an instance variable, _seqHeader
     *   and stops reading.
     */
    public void nextDNAseq( )
    {  
        //////////////////// 8P code ////////////////////////////////
        
        
        
        
        
    }

    //------------------ findAll() ------------------------
    /**
     * Given the current r.e. pattern, try to find that pattern in the
     * input data; if successful, try again and again until it fails.
     * 
     * Each region of match should be capitalized in the display.
     * For each match, you should print all the matches to all the groups 
     * of the pattern to System.out.
     */
    public void findAll( )
    {      
        //////////////////// 9P code //////////////////////////////// 
        // Be sure to break the task into multiple methods.
        /////////////////////////////////////////////////////////////
        
        
        
    }


    ////////////////////////////////////////////////////////////////////////
    // Code below here is all related to getting and managing the Pattern 
    //    file and its contents. 
    // You may change it if you want and/or use it to help develop the
    //    sequence file handling code.
    ///////////////////////////////////////////////////////////////////////
    

    //-------------------- readPatternFile( String ) -----------------------
    /**
     * Open and read the pattern file specified by the parameter.
     * 
     * @param patFileName  File name for regex patterns.
     */
    private void readPatternFile( String patFileName )
    {
        if ( patFileName == null )
            return;
        File patternFile = new File( patFileName );
        if ( patternFile != null && patternFile.exists() )
            readPatternFile( patternFile );
        else
            System.out.println( "Pattern file does not exist: " + patFileName );
    }
    //-------------------- readPatternFile( File ) --------------------------
    /**
     * Open and read the pattern file specified by the parameter.
     *
     * @param patternFile  File for regex patterns.
     */
    private void readPatternFile( File patternFile )
    {
        /////////////////////////////////////////////////////////////////////
        // 8P code needed
        ////////////////////////////////////////////////////////////////////
        _patterns  = new Vector<String>();
        //-------------- read the predefined patterns from the file -- if there
        if ( patternFile != null )
        {
            try 
            {
                Scanner patScan = new Scanner( patternFile );
                while ( patScan.hasNextLine() )
                    _patterns.add( patScan.nextLine() );
            }
            catch ( IOException ioe )
            {
                System.out.println( "IO Exception trying to read pattern file: "
                                       + ioe.getMessage() );
            }
        }
        nextRegex();
    }
    //+++++++++++++++++++++ methods invoked by GUI buttons +++++++++++++++++++
    //-------------------- readPatternFile() -----------------------------
    /**
     * Get pattern file name from user, open it and read it.
     */
    public void readPatternFile( )
    {
        int returnval = _chooser.showOpenDialog( null );
        if ( returnval == JFileChooser.APPROVE_OPTION )
        {
            File newIn = _chooser.getSelectedFile();
            readPatternFile( newIn );
        }
    }
    //---------------------- nextRegex() -----------------------------
    /**
     * Access the next regex in the pattern array; re-cycles when gets to end.
     */
    public void nextRegex( )
    { 
        if ( _patterns != null && _patterns.size() > 0 )   // are there any?
        {
            String newRegex = _patterns.get( _nxtPattern++ );
            if ( _nxtPattern >= _patterns.size() )
            {
                _nxtPattern = 0;
                System.out.println( "*** Regex file finished, " +
                                    "starting over ***" );
            }
            setNewRegex( newRegex );
        }
    }
    //------------------ setNewRegex( String ) --------------------------------
    /**
     * Define a new regular expression -- a private utility function.
     *
     * @param newRE  New regular expression.
     */
    public void setNewRegex( String newRE )
    {
        _regex = newRE;
        _pattern = Pattern.compile( _regex );
        _gui.setRegexLabel( _regex );
        _display.setMatch( " " ); 
    }
    //---------------------- newRegex() -----------------------------
    /**
     * User enters a new regular expression via a popup dialog.
     */
    public void newRegex( )
    {  
        String newRegex = JOptionPane.showInputDialog( "Regular expression",
                                                       _regex ); 
        
        if ( newRegex != null )
            setNewRegex( newRegex );
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
