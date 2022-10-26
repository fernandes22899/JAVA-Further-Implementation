import java.util.*;

/**
 * SymbolTable - this class  maintains a symbol table for variables.
 * 
 * This functionality lends itself to a class that uses the Singleton pattern.
 * That is, it enforces a restriction that only 1 instance can ever be created.
 * 
 *@author rdb
 */
public class SymbolTable
{
    //--------------------- class variables -------------------------------
    private static SymbolTable theTable = null;
    
    //--------------------- instance variables ----------------------------
    // Use a Hashtable or a HashMap to store information (value) using the id 
    // as the key
    ///////////////////////////////////////////////////////////
    private Hashtable<String, Number> ht;
    
    //------------- constructor --------------------------------------------
    /**
     * Note the constructor is private!
     */
    private SymbolTable()
    {
        //////// Create the hashtable or hashht ///////////////////
        ht = new Hashtable<String, Number>();
    }
    //------------- instance -----------------------------------------------
    /**
     * user code must call a static method to get the reference to the 
     * only allowed instance -- first call creates the instance.
     *
     * @return the current SymbolTable instance.
     */
    public static SymbolTable instance()
    {
        if ( theTable == null )
            theTable = new SymbolTable();
        return theTable;
    }
    
    //------------------------ setValue( String, float  ) ---------------------
    /**
     * Set an identifier's value to the specified value.
     *
     * @param var Key string.
     * @param num Value.
     */
    public void setValue( String var, float num )
    {
        /////////////////////////////////////////////////////// 
        //    Need to save information into the hash table
        //
        // You are allowed to change the signatures; for example, you
        //    can use Float or Number as the parameter type, both
        //    here and in getValue
        //////////////////////////////////////////////////////////
        Number value = new Float( num );
        ht.put( var, value );
    }
    
    //------------------------ getValue( String ) ---------------------------
    /**
     * Look up an identifier in the hash table and return its value.
     * If the identifier is not in the table, add it with a value of 0
     * and return 0.
     *
     * @param var Key string.
     *
     * @return Value.
     */
    public float getValue( String var )
    {
        /////////////////////////////////////////////////////////////
        //  Use var as hash table key get value; return it as a float
        //    or a Number (Float)
        ////////////////////////////////////////////////////////////
        if( !ht.containsKey( var ) )
            return new Float( 0 );
        else
            return ht.get( var ).floatValue();
    }
    
    //------------------------- toString() -------------------------------
    /**
     * toString -- converts object to String.
     * @return String representation.
     */
    public String toString() 
    {
        String str = "";
        for( String ing: ht.keySet() )
        {
            str += ing + " - " + ht.get( ing ) + "\n";
        }
        return str;
    }
    
    //--------------------------- main -----------------------------------
    /**
     * Main method.
     *
     * @param args Array of arguments.
     *
     */
    public static void main( String[] args )
    {
        SymbolTable st = SymbolTable.instance();
        st.setValue( "a", 4.0f );
        float val = st.getValue( "a" );
        System.out.println( "Test: should print 4: " + val );
      
        val = st.getValue( "b" );
        System.out.println( "Test: should print 0: " + val );
      
        st.setValue( "a", 6.0f );
        val = st.getValue( "a" );
        System.out.println( "Test: should print 6: " + val );
        
        System.out.println();
        System.out.println( "Test for toString: \n" + st.toString() );
    }
}
