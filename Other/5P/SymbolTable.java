import java.util.*;

/**
 * SymbolTable - this class  maintains a symbol table for variables.
 * 
 * This functionality lends itself to a class that uses the Singleton pattern.
 * That is, it enforces a restriction that only 1 instance can ever be created.
 * 
 * @author rdb
 */
public class SymbolTable
{
    //--------------------- class variables -------------------------------
    private static SymbolTable theTable = null;
   
    //--------------------- instance variables ----------------------------
    // Use a Hashtable or a HashMap to store information (value) using the id 
    // as the key
    ///////////////////////////////////////////////////////////
    //instance variable
    private Hashtable<String, String> key;
    
   
    //------------- constructor --------------------------------------------
    /**
     * Note the constructor is private!
     */
    private SymbolTable()
    {
        //////// Create the hashtable or hashmap ///////////////////
        //initialization of the Hash Table
        key = new Hashtable<String, String>();
    }
    //------------- instance -----------------------------------------------
    /**
     * user code must call a static method to get the reference to the 
     * only allowed instance -- first call creates the instance.
     *
     * @return New symbol table.
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
     * @param num Number to store.
     */
    public void setValue( String var, Float num )
    {
        /////////////////////////////////////////////////////// 
        //    Need to save information into the hash table
        //
        // You are allowed to change the signatures; for example, you
        //    can use Float or Number as the parameter type, both
        //    here and in getValue
        //////////////////////////////////////////////////////////
        //places var, string "", and num in the key( Hash Table )
        key.put( var, "" + num );
    }
    //------------------------ getValue( String ) ---------------------------
    /**
     * Look up an identifier in the hash table and return its value.
     * If the identifier is not in the table, add it with a value of 0
     * and return 0.
     *
     * @param var Key string.
     *
     * @return value for key.
     */
    public float getValue( String var )
    {
        /////////////////////////////////////////////////////////////
        //  Use var as hash table key get value; return it as a float
        //    or a Number (Float)
        ////////////////////////////////////////////////////////////
        //variable instance
        float variable;
        //try and catch for a case of NullPointerException
        try
        {
            variable = Float.parseFloat( key.get( var ) );
        }
        catch( NullPointerException e )
        {
            variable = 0;
            setValue( "" + var, variable );
            //error massage
            System.out.println( "Error: Variable Does Not Exist" );
        }
        return variable;
    }
    //------------------------- toString() -------------------------------
    /**
     * toString -- converts object to String.
     * @return String representation.
     */
    public String toString()
    {
        return "{ }";
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
    }
}
