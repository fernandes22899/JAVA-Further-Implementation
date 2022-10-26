/**
 * RegexApp - this class manages the regular expression processing 
 * 
 * 
 * The application semantics including the graphics generated 
 * by the app are controlled from this class.
 */
import javax.swing.*;
import javax.swing.filechooser.*;
import java.util.*;
import java.util.regex.*;
import java.io.*;
import java.text.*;

public class RegexApp
{
    //--------------------- instance variables ----------------------------
    private JFileChooser     _chooser;
    
    private Pattern          _pattern = null;
    private String           _regexString;    // the currently active reg expr
    private String           _testString;
    
    private PrintWriter      _log;   
    private Regex            _regex;
    
    private boolean          _interactive = true;
    
    public boolean           dotFlag = false;
    public boolean           caseInsensitiveFlag = false;
    public boolean           multiLineFlag = true;
    public boolean           commentsFlag = false;
    
    //---------------------- constructors ----------------------------------
    /**
     * The app needs the display reference only so it can update the 
     * DisplayListPanel with new Lists when needed and tell it to redraw
     * when things change. It accesses the GUI.display class variable for this
     */
    public RegexApp( Scanner cmdIn ) throws IOException
    {
        _interactive = false;
        _regex = new Regex();     // create student's class
        _log = new PrintWriter( System.out, true );
        //openLogFile();
        processBatch( cmdIn );
        _log.close();
    }
    /**
     * The app needs the display reference only so it can update the 
     * DisplayListPanel with new Lists when needed and tell it to redraw
     * when things change. It accesses the GUI.display class variable for this
     */
    public RegexApp( )
    {
        _regex = new Regex();     // create student's class
        
        File f = new File( "." );
        System.out.println( "File( . ): " + f.getAbsolutePath() );
        
        _chooser = new JFileChooser( new File( "." ));
        _chooser.setCurrentDirectory( new File( "." ));
        FileNameExtensionFilter filter = 
            new FileNameExtensionFilter( "Text files", "txt" );
        _chooser.setFileFilter( filter );
        
        //_chooser.setAcceptAllFileFilterUsed( false );
        openLogFile( "RESET.log" );
    }
    //---------------------- processBatch( Scanner ) -------------------------
    /**
     * read batch input file: commands to simulate interaction 
     */
    private void processBatch( Scanner cmdIn ) throws IOException
    {
        while ( cmdIn.hasNextLine() )
        {
            String line = cmdIn.nextLine();
            if ( line.length() > 0 )
                processCommand( line );
        }
    }
    //------------------- processCommand( String ) --------------------
    /**
     * Execute one simulated interaction command
     */
    private void processCommand( String line ) 
    {
        String results = null;
        String[] tokens = line.split( " " );
        char cmd = tokens[0].charAt( 0 );
        String arg = "";
        if ( tokens.length > 1 )
            arg = tokens[ 1 ];
        switch ( cmd ) 
        {
            case 'i':          // read new input file
                readInputFile( new File( arg ));
                break;
            case 'r':          // new regex
                _regexString = arg;
                _regex.setRegex( _regexString );
                log( "Regex --> " + _regexString + " <--" );
                break;
            case 'R':         // comment mode regex
                _regexString = line.substring( 1 ); // leave out command
                _regex.setRegex( _regexString );
                log( "Regex --> " + _regexString + " <--" );
                break;
            case 'f':          // find 
                results = _regex.find();
                log( "Find    -----------------------------------------" );
                log( results );
                log( "-------------------------------------------------" );
                break;
            case 'a':          // find all
                log( "FindAll @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" );
                results = _regex.findAll();
                log( results );
                log( "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" );
                break;
            case 's':          //split
                results = _regex.split();
                log( "FindAll =========================================" );
                log( results );
                log( "=================================================" );
                break;
            case 'g':          //groups
                results = getGroupString();
                log( "Groups ++++++++++++++++++++++++++++++++++++++++++" );
                log( results );
                log( "+++++++++++++++++++++++++++++++++++++++++++++++++" );                   
                break;
            case 'x':          //reset
                _regex.reset();
                log( "%%%%%%%%%%%%%%%%%%%% reset %%%%%%%%%%%%%%%%%%%%%%" );
                break;
            case 'o':  // option flags: 
                // d: dotall          m: multiline
                // i: caseInsensitive c: comments.
                batchFlags( arg );
                log( "Options: " + arg );
                break; 
            case '#':
                log( line );
                break;
            case 'q':
                System.exit( 0 );
                break;
            default:
                System.err.println( "***Error: invalid command: " + line );
        }
    }
    //---------------------- batchFlags( String ) ---------------------
    /**
     * change ALL flags based on argument string. Anything not specified is
     *     turned OFF.
     */
    private void batchFlags( String flags )
    {
        dotFlag = false;
        caseInsensitiveFlag = false;
        multiLineFlag = false;
        commentsFlag = false;
        for ( int i = 0; i < flags.length(); i++ )
        {
            switch ( flags.charAt( i ) )
            {
                case 'd':  dotFlag = true; break;
                case 'i':  caseInsensitiveFlag = true; break;
                case 'm':  multiLineFlag = true; break;
                case 'c':  commentsFlag = true; break;
            }
        }
        _regex.setFlags( regexFlags() );
    }
    //----------------------- openLogFile( String ) --------------------
    /** 
     *  open the log file.
     */
    private void openLogFile( String fileName )
    {
        try 
        {
            File logFile = new File( fileName );
            if ( !logFile.exists() )
                logFile.createNewFile();
            
            FileWriter fw = new FileWriter( logFile, true );  // append
            _log = new PrintWriter( fw, true );
            
            log( "-------------------------------", "" );
        }
        catch ( IOException ioe ) 
        {
            System.out.println( "RegexApp catch: " + ioe.getMessage() );
        }
    }
    /**
     * log information in the log file: shorter version.
     */
    private void log( String data )
    {
        _log.println( data );
        //_log.println( "**** " + data );
    }
    /**
     * log information in the log file: this is the "long form" 
     * it has been replaced by the 1 argument version for most things.
     */
    private void log( String separator, String data )
    {
        _log.println( separator + dateString() + separator );
        _log.println( data );
        //_log.println( separator + separator + separator );
    }
    /**
     * return the current date/time as a string
     */
    private String dateString()
    {
        Date d = new Date();
        DateFormat fmt = new SimpleDateFormat();
        return fmt.format( d );
    }
    
    //+++++++++++++++++ methods invoked by GUI buttons ++++++++++++++++
    //---------------------- newRegex() -----------------------------
    /**
     * get a new regular expression from dialog box
     */
    public void newRegex( )
    {  
        _regexString = JOptionPane.showInputDialog( null, 
                                       "Enter regular expression" );
        setRegex( _regexString );
    }
    //---------------------- setRegex( String ) -----------------------
    /**
     * get a new regular expression from GUI
     */
    public void setRegex( String regexString )
    {  
        _regexString = regexString;
        if ( _regexString != null && _regexString.length() > 0 )
        {
            _regex.setRegex( _regexString );
            
            GUI.setRegexLabel( _regexString );
            log( "Regex --> " + _regexString + " <--" );
            GUI.display.setMatch( " " );
        }
    }
    //------------------ regexFlags() ------------------------
    /**
     * create the flag variable for regex compilation 
     */
    public int regexFlags()
    {
        int flags = 0;
        if ( multiLineFlag )
            flags |= Pattern.MULTILINE;
        if ( dotFlag )
            flags |= Pattern.DOTALL;
        if ( caseInsensitiveFlag )
            flags |= Pattern.CASE_INSENSITIVE;
        if ( commentsFlag )
            flags |= Pattern.COMMENTS;
        return flags;
    }   
    //------------------ flagsToString() ------------------------
    /**
     * create the flag variable for regex compilation 
     */
    public String flagsToString()
    {
        String flags = "";
        if ( multiLineFlag )
            flags += "MLine ";
        if ( dotFlag )
            flags += "DotAll ";
        if ( caseInsensitiveFlag )
            flags += "NoCase ";
        if ( commentsFlag )
            flags += "Comments";
        return flags;
    }
    //------------------ setFlags() ------------------------
    /**
     * The pattern or a flag has changed, compile the pattern.
     */
    public void setFlags()
    {
        try
        {
            log( "Regex --> Flags: " + flagsToString() );
            _regex.setFlags( regexFlags() );
        }
        catch ( PatternSyntaxException pse )
        {
            JOptionPane.showMessageDialog( null, pse.getMessage() );
        }
        reset();
    }
    
    //------------------ newInputString() ------------------------
    /**
     * Enter an input string using dialog box
     */
    public void newInputString()
    {
        String prompt  = "Enter input string";
        _testString = JOptionPane.showInputDialog( null, prompt );
        if ( _testString != null && _testString.length() > 0 )
        {
            GUI.display.setInput( _testString );   
            GUI.display.setMatch( " " );
        }
        _regex.setInput( _testString );
        log( "Input --> " + _testString + " <--" );
    }
    //------------------ findAll() ------------------------
    /**
     * find all occurrences of the pattern in the input
     */
    public void findAll()
    {
        String results = _regex.findAll();
        if ( results == null  )
            log( "FindAll no match" );
        else
        {
            GUI.display.setMatch( results );
            String[] lines = results.split( "\n" );
            log( "FindAll match count: " + lines.length ); 
        }
    }
    //------------------ split() ------------------------
    /**
     * split the input string according to the r.e.
     */
    public void split()
    {
        String results = _regex.split();
        GUI.display.setMatch( results );
        if ( results == null )
            log( "Split no match" );
        else
        {
            String[] lines = results.split( "\n" );
            log( "Split count" + lines.length );
        }
    }
    //---------------------- newInputFile() -----------------------------
    /**
     * read input strings from a file 
     */
    public void newInputFile( )
    {
        int returnval = _chooser.showOpenDialog( null );
        if ( returnval == JFileChooser.APPROVE_OPTION )
        {
            File newIn = _chooser.getSelectedFile();
            readInputFile( newIn );
        }
    }
    /**
     * open string input file
     */
    private boolean readInputFile( File file )
    {
        log( "File --> " + file.getName() + " <--" );
        StringBuffer contents = new StringBuffer();
        try
        {
            Scanner in = new Scanner( file );
            while ( in.hasNextLine() )
                contents.append( in.nextLine() + "\n" );
            _testString = new String( contents );
            if ( _interactive )
            {
                GUI.display.setInput( _testString );
                GUI.display.setMatch( " " );
            }
            _regex.setInput( _testString );
        }
        catch ( IOException ioe )
        {
            System.out.println( "inputFile::IOException " + ioe.getMessage() );
            return false;
        } 
        return true;
    }
    //------------------ find() ------------------------
    /**
     * Find next match
     */
    public void find()
    {
        log( "Find" );
        GUI.display.setMatch( _regex.find() );
    }
    //------------------ groups() ------------------------
    /**
     * Return the groups matched in previous find
     */
    public void groups()
    {
        String results = getGroupString();
        GUI.display.setMatch( results );
        if ( results == null )
            log( "getGroupString is null" );
        else
        {
            String[] lines = results.split( "\n" );
            log( "Groups count: " + lines.length  +"\n" + results );
        }
    }  
    //----------------- getGroupString() ---------------------
    private String getGroupString()
    {
        String results = null;
        String wholeMatch = _regex.group();
        if ( wholeMatch == null )
            results = "**** No match ****";
        else
        {
            results = "Entire match: " + wholeMatch + "\n";
            //Note: should use for loop based on Matcher groupCount method,
            //   but even if we had access to it, the student Regex should be 
            //   doing that test.
            int cnt = 1;
            String group = _regex.group( cnt );
            while ( group != null )   
            {
                results += cnt + ": " + group + "\n";
                group = _regex.group( ++cnt );
            }
        }
        return results;
    }
    //---------------------- reset() -----------------------------
    /**
     * reset the regular expression processing on the current input string.
     */
    public void reset( )
    {  
        _regex.reset();
        GUI.display.setMatch( " " );
    }
    
    //++++++++++++++++++++++ utility methods ++++++++++++++++++++++++++
    //----------------------- stringToInt( String ) -------------------
    /**
     * Convert string to integer
     */
    private int stringToInt( String str, int defaultVal )
    {
        try 
        {
            return Integer.parseInt( str );
        }
        catch ( NumberFormatException nfe )
        {
            String input = JOptionPane.showInputDialog( null, 
                     "Invalid integer input: " + str + ". No change." );
            return defaultVal;
        }
    }
    //--------------------- main -----------------------------------------
    public static void main( String[] args )
    {
        Scanner cmdIn = null;
        try
        {
            if ( args.length > 0 )
                cmdIn = new Scanner( new File( args[ 0 ] ));
            else
            {
                System.err.println( "Batch mode: no arg; using std in" );
                cmdIn = new Scanner( System.in );
            }
            new RegexApp( cmdIn );
        }
        catch ( FileNotFoundException fnf )
        {
            System.err.println( "*** Error: file not found: " + args[ 0 ] );
        }
        catch ( IOException ioe )
        {
            System.err.println( "***IO Error: " + ioe.getMessage() );
        }
    }
}
