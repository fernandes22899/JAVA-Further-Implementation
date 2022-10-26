/**
 * Data - the object to be stored in the data structure. It has
 *    String key    -- not a key, but string representing range of data 
 *    int    count  -- # values in this node; values are stored in 
 *                     the BiNode object; should be in this Data object
 */

public class Data 
{
    //------------ instance variables ----------------------------
    public   String  key;  
    public   int     count;   // # values in this node
    
    //------------------ constructor -----------------------------
    /**
     *  Construct a Data object from its components
     */
    public Data( String k, int c )
    {
        key   = k;
        count = c;
    }
    //-------------------- toString() ------------------------
    public String toString()
    {
        return  key  + ":" + count ;
    }
}
