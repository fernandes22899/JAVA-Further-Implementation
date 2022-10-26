/**
 * Data - the object to be stored in the data structure. It has
 *    String key        -- the key used in the StringComparable interface
 *    int    value      -- an int
 * 
 * @author rdb
 */

public class Data 
{
    //------------ instance variables ----------------------------
    // package visibility for simplicity
    String key;
    int    value;
    
    //------------------ constructor -----------------------------
    /**
     *  Construct a Data object from its components.
     *
     * @param k String  key
     * @param v int     value
     */
    public Data( String k, int v )
    {
        key = k;
        value = v;
    }
    //--------------------- toString() -------------------------------
    /**
     * toString created as a tuple. 
     * 
     * @return String    string representation of the Data object
     */
    public String toString()
    {
        return key + ":" + value;
    }
}