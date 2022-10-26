//----------------------------- TreeApp.java --------------------------
import javax.swing.*;
import java.util.*;   
/**
 * TreeApp - the main class for controlling the application code. 
 * 
 * 
 * The application semantics including the graphics generated 
 * by the app are controlled from this class.
 * @author rdb
 * Last edit
 * 03/16/14 rdb: made checkstyle compatible
 * 08/13/14 rdb: Reintroduced a non null name field with 1-char names
 *               Added a "." as separator between key and name fields
 *               Changed max value field to 9 so only uses 1 char
 *               The 1 new char led to need to increase node width
 * 03/10/14 rdb: fixed batch execution to handle new name field.
 */
public class TreeApp
{
    //---- "package" state variables; set by GUI and/or BSTdriver -----
    static boolean printMode     = true; // if set, print after each cmd
    // false if running from BSTdriver
    static boolean interactive   = true;  // true if interactive user
    static Scanner terminal      = null;
    //--------------------- instance variables ------------------------
    private ArrayList<Data>  _list;
    private BinarySearchTree _bst = null;
    private DrawPanel     _display = null;
    private Random           _rng = null;
    
    //------ "package" state variables; set by GUI ----------
    //boolean printMode     = true;  // if set, print after each command
    
    //--- data generation constants with package access so GUI can change
    int     dataSize        = 8;
    int     minDataSize     = 0;
    int     maxDataSize     = 20;
    int     defaultDataSize = 8;
    int     minNodeWidth    = 1;
    int     maxNodeWidth    = 200;
    int     defaultNodeWidth = 52;
    int     maxVal           = 9;
    
    int     rngSeed          = 2;   // random seed
    int     maxSeed          = 16;   // arbitrary number
    
    //--- subset constants
    private int     subsetMin       = 30;   // arbitrary; all values are 0, 100
    private int     subsetMax       = 70;
    
    //---------------------- constructors ----------------------------------
    /**
     * The app needs the display reference only so it can update the 
     * DisplayListPanel with new Lists when needed and tell it to redraw
     * when things change.
     * @param display DrawPanel 
     */
    public TreeApp( DrawPanel display )
    {
        _display = display;
        _display.setNodeWidth( defaultNodeWidth );
        makeData();
    }
    /**
     * This is the constructor for batch execution.
     */
    public TreeApp()
    {
        interactive = false;
    }
    //---------------------- update() -----------------------------
    /**
     * Something in gui has change; update whatever needs to be updated
     * The GUI calls the app when a GUI update occurs; the app (in this
     * case) only needs to pass this along to the DisplayListPanel.
     */
    public void update()
    { 
        if ( _display != null )
            _display.setTree( _bst );     
    }
    //----------------------  setNodeWidth( int ) -------------------
    /**
     * Set width of node. 
     * @param newW  int    width
     */
    public void setNodeWidth( int newW )
    {
        if ( _display != null && newW > 0 )
            _display.setNodeWidth( newW );
    }
    //+++++++++++++++++++++ methods invoked by GUI buttons +++++++++++++++++++
    //---------------------- makeData() -----------------------------
    /**
     * Generate a new DataList.
     */
    public void makeData( )
    {  
        _list = generateData( this.dataSize, this.rngSeed );
        
        _bst = new BinarySearchTree();
        
        Iterator<Data> iter = _list.iterator();
        while ( iter.hasNext() )
            _bst.add( iter.next() );
        
        update();
    }
    //----------------------- makeData( int ) ------------------------
    /**
     * Update list size and generate a new list of that size.
     * @param sz int    size
     */
    public void makeData( int sz )
    {
        this.dataSize = sz;
        makeData();
    }
    
    //---------------------- inOrderString() --------------------------
    /**
     * Print the list for debugging; maybe more.
     */
    public void inOrderString( )
    {        
        showResults( "--------------in-Order print---------------\n"
                        + _bst.inOrderString() );   
    }
    
    //---------------------- height() -----------------------------
    /**
     * Compute and print the height of the tree.
     */
    public void height( )
    { 
        showResults( "Tree height: " + _bst.height() );
    }
    
    //---------------------- find() -----------------------------
    /**
     * Find an item in tree with a given id. For interactive mode.
     */
    public void find( )
    { 
        String outMsg;
        String key;
        key = readLine( "Enter key to search for" );
        if ( key != null && key.length() > 0 )
        {         
            Data found = _bst.find( key );
            if ( found != null )
                outMsg = "Found: " + found;
            else
                outMsg = key + " is not in the tree.";
            showResults( outMsg );
        }
    }
    //----------------------- find( String ) -------------------------
    /**
     * Batch version specifies id on command input line.
     * @param key String       key to be search for
     */
    public void find( String key )
    { 
        String outMsg = key + "?: ";
        if ( key != null && key.length() > 0 )
        {         
            Data found = _bst.find( key );
            if ( found != null )
                outMsg = "Found! " + found;
            else
                outMsg = key + " ---Not in the tree---";
            showResults( outMsg );
        }
    }
    //---------------------- maxFind() -----------------------------
    /**
     * Find and report the max value Data item in the list.
     */
    public void maxFind( )
    { 
        String msg;
        Data max = _bst.maxFind();
        if ( max == null )
            msg = "----------No max; tree must be empty";
        else
            msg = "----------Max entry in tree is " + max;
        showResults( msg );
    }
    //---------------------- showTree() -----------------------------
    /**
     * Print the list for debugging; maybe more.
     */
    public void showTree( )
    {  
        showResults( "-------------Print tree ------------ \n" + _bst );
    }
    //----------------------- stringToInt( String ) ------------------
    /**
     * Convert string to integer.
     * @param str String      to be converted
     * @param defaultVal int  default value if str is invalid
     * @return int            value
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
    
    //------------------- printTree( String ) ------------------
    /**
     * Print the current tree.
     * @param title String             title
     */
    private void printTree( String title )
    {
        String dashes = " ------------------------ ";
        System.out.println( "\n" + dashes + title + dashes );
        if ( _bst == null || _bst.size() == 0 )
            System.out.println( "---Empty---" );
        else
        {
            System.out.println( _bst );
            System.out.println( "Tree has " + _bst.size() + " nodes." );
        }
    }
    
    //------------------- generateData -----------------------------------
    /**
     * Generate data for the tree.
     * @param numItems int      number items to generate
     * @param seed     int      random number seed; -1: system picks
     * @return ArrayList<Data>  the data
     */
    private ArrayList<Data> generateData( int numItems, int seed )
    {
        _bst = new BinarySearchTree();
        String[] names = { "a", "b", "c", "d", "e", "f", "g", 
            "h", "i", "j", "k", "l" };
        ArrayList<Data> dl = new ArrayList<Data>();
        ArrayList<String> keys = new ArrayList<String>();
        
        if ( _rng == null )
            _rng = new Random( seed );
        
        String        letters = "abcdefghijklmnopqrstuvwxyz";
        StringBuffer  keybuf  = new StringBuffer( "12" );
        
        while ( dl.size() < numItems )
        {
            // generate a key
            char letter1 = letters.charAt( _rng.nextInt( letters.length() ) );
            char letter2 = letters.charAt( _rng.nextInt( letters.length() ) );
            
            keybuf.setCharAt( 0, letter1 );
            keybuf.setCharAt( 1, letter2 );         
            String key = keybuf.toString();
            
            int found = keys.indexOf( key ); 
            if ( found < 0 )   // keys must be unique so only add if not there
            {            
                keys.add( key );   // add key to key list
                // generate a name field; these are allowed to be duplicate
                int    nameIndex = _rng.nextInt( names.length );
                String name = names[ nameIndex ];
                // generate a value from 0 to maxVal
                int    val = _rng.nextInt( maxVal );
                dl.add( new Data( key, name, val ) );
                
            }
        }
        return dl;
    }
    
    //--------------------- readLine --------------------------------
    /**
     * Get input line from user. If guiMode is true, use JOptionPanes,
     *     else return empty string.
     * @param msg String      prompt
     * @return String         user input
     */
    private String readLine( String msg )
    {
        String line;
        if ( interactive )
            line = JOptionPane.showInputDialog( null, msg );
        else
            line = "";
        return line;
    }
    
    //--------------------- showResults --------------------------------
    /**
     * Show results to "user". If guiMode is true, use JOptionPanes,
     *     else print to standard output.
     * @param msg String      string to print
     */
    private void showResults( String msg )
    {
        if ( interactive )
            JOptionPane.showMessageDialog( null, msg );
        else
            System.out.println( msg );
    }
    //-------------------- batchFind -------------------------------------
    /**
     * In batch mode do some searches for things that are there and 
     *    some that aren't.
     * @param num int     number of searches to perform.
     */
    public void batchFind( int num )
    {
        String tree = _bst.toString();  // get the whole tree
        
        String[] parts = tree.split( ":" ); // : separates key and value
        // in Data.toString
        Random rng = new Random( 0 );
        while ( num-- >= 0 )
        {
            // Data.toString output is of the form: key.name:value
            String part = parts[ rng.nextInt( parts.length ) ];
            int lastSpace = part.lastIndexOf( ' ' );
            if ( lastSpace >= 0 && lastSpace < part.length() - 1 )
            {
                String keyName = part.substring( lastSpace + 1 );
                int dot = keyName.indexOf( '.' );
                String key = keyName.substring( 0, dot );
                find( key );
            }
        }
        // Now lookup nodes not in tree
        find( "---" );   // likely to be < all in tree
        find( "ZZZZ" );  // likely to be > all in tree
        find( "ee" );    // somewhere inside
    }
    //--------------------- main -------------------------------------
    /**
     * A convenience main that runs the app for this problem.
     * @param args String[]   command line args.
     */
    public static void main( String[] args )
    {
        TreeRecursion.main( args );
    }
}
