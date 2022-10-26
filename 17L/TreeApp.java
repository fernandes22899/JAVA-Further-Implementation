/**
 * TreeApp - the main class for controlling the application code. 
 * 
 * 
 * The application semantics including the graphics generated 
 * by the app are controlled from this class.
 */
import javax.swing.*;
import java.util.*;

public class TreeApp
{
    //--------------------- class variables ----------------------------
    static boolean interactive = true;
    
    //--------------------- instance variables -------------------------
    private BinTree    _binTree = null;
    private DrawPanel  _display;
    private boolean    _autoBalance = false;
    
    //------ "package" state variables; set by GUI ----------
    boolean printMode     = true;  // if set, print after each command
    
    //--- data generation constants with package access so GUI can change
    int     dataSize        = 12;
    int     minDataSize     = 0;
    int     maxDataSize     = 20;
    int     defaultDataSize = 12;
    int     minNodeWidth    = 20;
    int     maxNodeWidth    = 80;
    int     defaultNodeWidth = 50;
    int     maxValue         = 99;
    
    int     rngSeed          = 0;   // random seed
    int     maxSeed          = 16;   // arbitrary number
    
    //--- subset constants
    private int     subsetMin       = 30;   // all values are 0, 99
    private int     subsetMax       = 70;
    
    //---------------------- constructor ----------------------------------
    /**
     * The app needs the display reference only so it can update the 
     * DisplayListPanel with new Lists when needed and tell it to redraw
     * when things change.
     */
    public TreeApp( DrawPanel display )
    {
        _display = display;
        if ( display == null )
            interactive = false;
        else
            interactive = true;
        makeData();
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
            _display.setTree( _binTree );     
    }
    //----------------------  setNodeWidth( int ) -------------------
    public void setNodeWidth( int newW )
    {
        if ( newW > 0 && _display != null )
            _display.setNodeWidth( newW );
    }
    //+++++++++++++++++++++ methods invoked by GUI buttons +++++++++++++
    //---------------------- makeData() -----------------------------
    /**
     * generate a list of data values 
     */
    public void makeData( )
    {  
        ArrayList<Integer> dl = makeData( this.dataSize, this.rngSeed );
        update();
    }
    
    //---------------------- print() -----------------------------
    /**
     * Print the tree for debugging; maybe more. 
     */
    public void print( )
    {  
        _binTree.print();
    }
    
    //---------------------- split() -----------------------------
    /**
     * split the spatial region at a given value
     */
    public void split( )
    { 
        String outMsg;
        String input;
        input = readInput( "Enter value(s) for split(s)" );
        if ( input != null && input.length() > 0 )
        {  
            split( input );
        }
    }
    //---------------------- split( String) --------------------------
    /**
     * split the spatial region at a given value
     */
    public void split( String in )
    {
        int value = 0;
        
        Scanner scan = new Scanner( in );
        while( scan.hasNextInt() )
        {
            value = scan.nextInt();  
            _binTree.split( value );
            update();
            //print();
        }
    }
    
    //---------------------- merge() -----------------------------
    /**
     * Merge the spatial region contaiing a given value with its sibling.
     */
    public void merge( )
    { 
        String outMsg;
        String input;
        input = readInput( "Enter value for merge" );
        if ( input != null && input.length() > 0 )
        {  
            merge( input );
        }
    }
    //---------------------- merge( String ) --------------------------
    /**
     * Merge the spatial region at a given value.
     */
    public void merge( String in )
    {
        int value = 0;
        
        Scanner scan = new Scanner( in );
        while( scan.hasNextInt() )
        {
            value = scan.nextInt();            
            _binTree.merge( value );
            update();
            //print();
        }
    }
    
    //---------------------- pointsInRange() -----------------------------
    /**
     * Find the leaf with a specified value and print its data 
     */
    public void pointsInRange( )
    { 
        String outMsg;
        String input;
        input = readInput( "Enter 2 values for the range" );
        
        if ( input != null && input.length() > 0 )
        {  
            int min = 0;
            int max = maxValue;
            
            Scanner scan = new Scanner( input );
            if ( scan.hasNextInt() )
            {
                min = scan.nextInt();
                if ( scan.hasNextInt() )
                    max = scan.nextInt();
                if ( min > max )
                    outMsg = "min > max: " + min + " " + max;
                else
                {
                    ArrayList<Integer> data = _binTree.pointsInRange( min, max );
                    outMsg = BiNode.toString( data );
                }
            }
            else
                outMsg = "Bad input: need a number";
            
            showOutput( outMsg );
        }
    }
    //---------------------- pointsInRange( int, int ) ---------------
    /**
     * Find the all data values in all leaves that are in the range
     *   specified by the parameters. Only visit nodes that intersect
     *   with the range.
     */
    public String pointsInRange( int low, int high )
    { 
        ArrayList<Integer> data = _binTree.pointsInRange( low, high );
        return BiNode.toString( data );  
    }
    //----------------------- stringToInt( String ) ---------------------
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
            showOutput( "Invalid integer input: " + str + ". No change." );
            return defaultVal;
        }
    }   
    
    
    //------------------- printTree( String ) ---------------------
    void printTree( String title )
    {
        String dashes = " ----------------- ";
        System.out.println( "\n" + dashes + title + dashes );
        if ( _binTree == null || _binTree.size() == 0 )
            System.out.println( "---Empty---" );
        else
        {
            System.out.print( _binTree );
            System.out.println( "---Tree has " + _binTree.size() 
                                   + " data objects." );
        }
    }
    
    //------------------- makeData( int, int ) -----------------------
    /**
     * arguments are 
     *    numItems -- number items to generate
     *    seed     -- random number seed; -1 means let system pick it.
     */
    ArrayList<Integer> makeData( int numItems, int seed )
    {
        Random rng;
        ArrayList<Integer> dl = new ArrayList<Integer>();
        if ( seed < 0 )
            rng = new Random();
        else 
            rng = new Random( seed );
        
        while ( dl.size() < numItems )
        {
            dl.add( new Integer( rng.nextInt( maxValue + 1 )));
        }
        
        _binTree = new BinTree( new BiNode( 0, maxValue ));
        
        Iterator<Integer> iter = dl.iterator();
        while ( iter.hasNext() )
            _binTree.add( iter.next() );
        return dl;
    }
    //--------------------- readInput --------------------------------
    /**
     * Read a line of input, either from System.in or from user.
     */
    private String readInput( String prompt )
    {
        String result = "";
        
        if ( interactive )
            result = JOptionPane.showInputDialog( null, prompt );
        else
        {
            Scanner in = new Scanner( System.in );
            result = in.nextLine();
        }
        return result;
    }
    //--------------------- showOutput --------------------------------
    /**
     * Produce response output, either to System.out or in dialog box.
     */
    private void showOutput( String msg )
    {
        if ( interactive )
            JOptionPane.showMessageDialog( null, msg );
        else
            System.out.println( msg );
    }
    //----------------------- main ------------------------------------
    /**
     * A convenience method to invoke the main application: BinTreeLab
     */
    public static void main( String[] args )
    {
        BinTreeLab.main( args );
    }
}
