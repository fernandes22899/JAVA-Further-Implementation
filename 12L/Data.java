/**
 * Data - the object to be stored in the data structure. It has
 *    String _key       -- the key used in the StringComparable interface
 *    String _rest      -- a stub representing other data
 * @author rdb
 */

public class Data implements Comparable<String>
{
    //------------ instance variables ----------------------------
    String key;
    String rest;
    
    //------------------ constructor -----------------------------
    /**
     *  Construct a Data object from its components.
     * 
     * @param k String   key field
     * @param r String   representation for "rest" of the data
     */
    public Data( String k, String r )
    {
        key = k;
        rest = r;
    }
    //+++++++++++++++++++ Comparable<String> method ++++++++++++++++
    /**
     * Compare this object to a string -- using key field.
     * 
     * @param searchKey String   key being searched for
     * @return int               <0 if key < searchKey
     *                           =0 if key == searchKey
     *                           >0 if key > searchKey
     */
    public int compareTo( String searchKey )
    {
        return key.compareTo( searchKey );
    }
    //--------------------- toString() ----------------------------
    /**
     * Returns a key:rest as a string representation for this object.
     * 
     * @return String  key:rest
     */
    public String toString()
    {
        return key + ":" + rest;
    }
}
