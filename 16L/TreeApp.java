//++++++++++++++++++++++++ TreeApp +++++++++++++++++++++++++++++
import javax.swing.*;
import java.util.*;

/**
 * TreeApp - the main class for controlling the application code. 
 * 
 * The application semantics including the graphics generated 
 * by the app are controlled from this class.
 * 
 * @author rdb
 */

public class TreeApp
{
    //--------------------- class variables
    private static TreeApp   treeApp;   // the singleton TreeApp
    private static Random    rng = null;
    
    //--------------------- instance variables -------------------------
    private ArrayList<Data>  _list;
    private BinarySearchTree _bst = null;
    private DrawPanel        _display;
    
    //------ "package" state variables; set by GUI ----------
    boolean printMode     = true;  // if set, print after each command
    boolean mouseDelete   = true;  // if set mousePressed means delete node
    
    //--- data generation constants with package access so GUI can change
    int     dataSize        = 8;
    int     minDataSize     = 0;
    int     maxDataSize     = 20;
    int     defaultDataSize = 8;
    int     minNodeWidth    = 1;
    int     maxNodeWidth    = 70;
    int     defaultNodeWidth = 25;
    
    int     rngSeed          = 2;   // random seed
    int     maxSeed          = 16;   // arbitrary number
    
    //--- subset constants
    private int     subsetMin   = 30;   // arbitrary; all values are 0, 100
    private int     subsetMax   = 70;
    
    //---------------------- constructor --------------------------------
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
        treeApp = this;
        makeData();
    }
    //----------------------- treeApp() --------------------------
    /**
     * Static application accessor.
     * @return TreeApp
     */
    public static TreeApp treeApp()
    {
        return treeApp;
    }
    //---------------------- update() -----------------------------
    /**
     * Something in gui has change; update whatever needs to be updated
     * The GUI calls the app when a GUI update occurs; the app (in this
     * case) only needs to pass this along to the DisplayListPanel.
     */
    public void update()
    { 
        _display.setTree( _bst );     
    }
    //----------------------  setNodeWidth( int ) -------------------
    /**
     * Set the width of the node.
     * @param newW  int
     */
    public void setNodeWidth( int newW )
    {
        if ( newW > 0 )
            _display.setNodeWidth( newW );
    }
    //+++++++++++++++++++++ methods invoked by GUI buttons +++++++++++++
    //---------------------- makeData() -----------------------------
    /**
     * Generate a new DataList and tree.
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
    //---------------------- print() -----------------------------
    /**
     * Print the list for debugging; maybe more. 
     */
    public void print( )
    {  
        _bst.inOrderPrint();   
    }  
    //---------------------- delete() -----------------------------
    /**
     * Given a key, find the Data item in tree and delete it from tree.
     */
    public void delete( )
    { 
        String outMsg;
        String key;
        key = JOptionPane.showInputDialog( null, "Enter key to search for" );
        if ( key != null && key.length() > 0 )
        {         
            Data found = _bst.find( key );
            if ( found != null )
            {
                outMsg = "Deleting: " + found;
                _bst.delete( found );
            }
            else
                outMsg = "Entry not in tree " + key;           
            
            JOptionPane.showMessageDialog( null, outMsg );
            _display.update();
            print();
        }
    }    
    //------------------- mouseEvent( BinarySearchTree.Node ) ----------
    /**
     * Got a mouse event on a node in the tree.
     * @param node BinarySearchTree.Node
     */
    public void mouseEvent( BinarySearchTree.Node node )
    {
        if ( mouseDelete )
        {
            // Right now we just delete it.
            _bst.removeNode( node );
            _display.update();
            print();
        }
    }
    //----------------------- stringToInt( String ) --------------------
    /**
     * Convert string to integer.
     * @param str String
     * @param defaultVal int
     * @return int
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
    
    //---------------------- find() -----------------------------
    /**
     * Find and report the information associated  with a specified key.
     */
    public void find( )
    { 
        String outMsg;
        String key;
        key = JOptionPane.showInputDialog( null, "Enter key to search for" );
        if ( key != null && key.length() > 0 )
        {         
            Data found = _bst.find( key );
            if ( found != null )
                outMsg = "Found: " + found;
            else
                outMsg = key + " is not in the tree .";
            JOptionPane.showMessageDialog( null, outMsg );
        }
    }
    
    //------------------- printTree( _bst, String ) --------------------
    /**
     * Print the tree with given title.
     * @param bst BinarySearchTree
     * @param title String
     */
    private void printTree( BinarySearchTree bst, String title )
    {
        String dashes = " ------------------------ ";
        System.out.println( "\n" + dashes + title + dashes );
        if ( bst == null || bst.size() == 0 )
            System.out.println( "---Empty---" );
        else
        {
            System.out.println( _bst );
            System.out.println( "Tree has " + bst.size() + " nodes." );
        }
    }
    
    //------------------- generateData ---------------------------------
    /**
     * Generate data for a new tree. Arguments are 
     *    numItems -- number items to generate
     *    seed     -- random number seed; -1 means let system pick it.
     * @param numItems int
     * @param seed     int
     * @return ArrayList<Data>
     */
    static ArrayList<Data> generateData( int numItems, int seed )
    {
        String[] names = { "", "", "", "", "", "", "", 
            "", "", "", "", "" };
        ArrayList<Data>   dl = new ArrayList<Data>();
        ArrayList<String> keys = new ArrayList<String>();
        
        if ( rng == null )
            rng = new Random( seed );
        
        String        letters = "abcdefghijklmnopqrstuvwxyz";
        StringBuffer  keybuf  = new StringBuffer( "12" );
        
        while ( dl.size() < numItems )
        {
            // generate a key
            char letter1 = letters.charAt( rng.nextInt( letters.length() ) );
            char letter2 = letters.charAt( rng.nextInt( letters.length() ) );
            
            keybuf.setCharAt( 0, letter1 );
            keybuf.setCharAt( 1, letter2 );         
            String key = keybuf.toString();
            
            if ( ! keys.contains( key ) )
            {            
                // generate a name field; these are allowed to duplicate
                int    nameIndex = rng.nextInt( names.length );
                String name = names[ nameIndex ];
                
                // generate a value from 0 to 99
                int    val = rng.nextInt( 100 );
                dl.add( new Data( key, name, val ) );
                keys.add( key );
            }
        }
        return dl;
    }
    //----------------- main -------------------------------------------
    /**
     * Convenience method to execute BSTDeleteApp.main.
     * @param args String[]  
     */
    public static void main( String[] args )
    {
        BSTDeleteApp.main( args );
    }
}