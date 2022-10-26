/**
 * BSTNode - a node in a binary search tree.
 * @author rdb
 */
public class BSTNode
{
    //--------- package visibility instance variables ------------
    BSTNode left   = null;
    BSTNode right  = null;
    BSTNode parent = null;
    String  data   = null;
    
    //------------------ BSTNode constructor ---------------------
    /**
     * Constructor needs a string as data.
     * @param str String
     */
    public BSTNode( String str )
    {
        this.data = str;
    }
}
