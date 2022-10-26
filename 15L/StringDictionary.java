//--------------------- StringDictionary.java --------------------------
import java.util.*;

/**
 * StringDictionary: a Dictionary of string objects.
 * @author rdb
 *
 * Last edited:
 * 03/17/14  rdb: made checkstyle-compatible
 */

public class StringDictionary
{
    //---------------- class variables ---------------------------------

    //---------------- instance variables ------------------------------
    private BSTNode   _root;
    private BSTNode   _next;
    private int       _size;

    //--------------------- constructor --------------------------------
    /**
     * Constructor.
     */
    public StringDictionary()
    {
        _root = null;
        _next = null;
        _size = 0;
    }
    //----------------- getTree() --------------------------------
    /**
     * Return the root of the tree as a BSTNode.
     *
     * @return BSTNode      root of tree
     */
    public BSTNode getTree()
    {
        return _root;
    }
    //----------------- clear() --------------------------------
    /**
     * Remove all entries from the tree.
     *
     */
    public void clear()
    {
        _root = null;
        _next = null;
        _size = 0;
    }

    //--------------------- reset() ------------------------------------
    /**
     * Start the iteration at the beginning.
     */
    public void reset( )
    {
        if ( _root == null )
            _next = null;
        else
            _next = getLeftMost( _root );
    }
    //--------------------- first() ------------------------------------
    /**
     * Return first element in the tree (the lowest in sort order).
     *
     * @return String     first data object in the tree
     */
    public String first( )
    {
        BSTNode value;
      
        if ( _root == null )
            return null;
        ////////////////////////////////////////////////////////////////
        // a. The first node to be returned while traversing a BST is the
        //    left most descendant of the root of the tree.
        //
        //    Implement getLeftMost( _root ), assign its result to a
        //    local BSTNode variable
        ////////////////////////////////////////////////////////////////
        value = getLeftMost( _root );

        ////////////////////////////////////////////////////////////////
        // b. Before returning the data from the first node, need to
        //   set the _next instance variable to reference the "next" node
        //   to return. Implement the method "setNext" and call that method,
        //   using the node stored in your local variable.
        ////////////////////////////////////////////////////////////////
        setNext( value );

        ////////////////////////////////////////////////////////////////
        // c. return the data field from the first node (saved above in
        //    a local variable
        ////////////////////////////////////////////////////////////////

        return value.data;
    }
    //--------------------- getLeftMost( BSTNode ) ---------------------
    /**
     * Get the left most descendant of the node, which is itself if it
     *     has no descendants.
     * @param node BSTNode    root of subtree
     * @return BSTNode        leftMost node in subtree
     */
    private BSTNode getLeftMost( BSTNode node )
    {
        ////////////////////////////////////////////////////////////////
        // Return the left most descendant of this node. That is, follow
        //   left links until a node has a null left subtree.
        // Return that node.
        // Do this RECURSIVELY.
        ////////////////////////////////////////////////////////////////
      if( node == null )
        return null;
      
      if( node.left == null )
        return node;
        
        return getLeftMost( node.left );
    }

    //--------------------- setNext( BSTNode ) -------------------------
    /**
     * get the next node in sorted order after the current one
     *    set _next instance variable to this value.
     * This will be the right child's leftmost -- if we have a right child
     * or we have to look for an ancestor.
     *
     * @param node BSTNode    root of subtree
     */
    private void setNext( BSTNode node )
    {
        ////////////////////////////////////////////////////////////////
        // If there is a right child, its leftmost descendant should
        //     become _next
        // else need to find the closest ancestor node that is not yet
        //     finished. Do that by calling "getUnfinishedAncester( node )
        ////////////////////////////////////////////////////////////////
      //if( node.right == null && node.left == null )
        //return null;
      
      if( node.right != null )
        _next = getLeftMost( node.right );
      else
        _next = getUnfinishedAncestor( node );

      
        //_next = null;
    }
    //----------------- getUnfinishedAncestor( BSTNode ) ---------------
    /**
     * Find the nearest ancestor that has not yet been completely
     *     processed -- an ancestor that reached this node along a left
     *     branch (or we get to the root).
     *
     * @param node BSTNode    root of subtree
     * @return BSTNode        unfinished ancestor node in subtree
     */
    private BSTNode getUnfinishedAncestor( BSTNode node )
    {
        // If this node is a right child, its parent is done and we need
        //   to get an unfinished ancestor of the parent (sounds like
        //   RECURSION!)
        // Otherwise this node is a left child of its parent and the
        //   parent still needs to be "processed", so parent becomes the
        //   next node.
        // Of course, you also need to check whether you've reached the
        //   root, i.e., that's when node.parent is null, and the node
        //   is done, so the traversal is done and we return null.
      
      if( node.parent != null && node.parent.right == node )
        return getUnfinishedAncestor( node.parent );
      else
         return node.parent;


        //return node;
    }
    //--------------------- next() -------------------------------------
    /**
     * Return next element in tree.
     * @return String
     */
    public String next()
    {
        ///////////////////////////////////////////////////////////////
        // If there is a defined _next node, return its string field, but
        // not before re-setting the _next variable to the node after it.
        // -- by calling setNext
        ///////////////////////////////////////////////////////////////
       
       
        //setNext( _next );
        BSTNode var = null;
        if( _next != null )
        {
          var = _next;
          setNext( _next );
          return var.data;
        }
        return null;
          
        
    }


    //////////////////////////////////////////////////////////////////
    // MAKE NO CHANGES BELOW HERE
    //////////////////////////////////////////////////////////////////


    //--------------------- add( Data ) -------------------------------
    /**
     * Add an object before next; that's what add( int, String) does.
     *
     * @param item String     represents data to be added to tree
     */
    public void add( String item )
    {
        BSTNode node = new BSTNode( item );
        if ( _root == null )
            _root = node;
        else
            add( _root, node );
    }

    //--------------------- add( BSTNode, BSTNode ) -------------------
    /**
     * Add a new node to the subtree rooted at cur.
     *
     * @param cur BSTNode      where to add new node
     * @param newNode BSTNode  node to be added
     */
    public void add( BSTNode cur, BSTNode newNode )
    {
        int cmp = cur.data.compareTo( newNode.data );
        if ( cmp > 0 )
        {
            if ( cur.left != null )
                add( cur.left, newNode );
            else
            {
                cur.left = newNode;
                newNode.parent = cur;
                _size++;
            }
        }
        else if ( cmp < 0 )
        {
            if ( cur.right != null )
                add( cur.right, newNode );
            else
            {
                cur.right = newNode;
                newNode.parent = cur;
                _size++;
            }
        }
        else
            System.err.println( "Duplicate nodes not allowed; not adding " +
                               newNode.data );
    }
    //--------------------- find( String ) -----------------------------
    /**
     * Return Data if key is found in dictionary.
     *
     * @param key String      key to search in dictionary
     * @return  String        data associated with the key
     */
    public String find( String key )
    {
        return find( _root, key );
    }
    //--------------------- find( BSTNode, String ) ------------------
    /**
     * Return Data if key is found in dictionary.
     *
     * @param root BSTNode       root of subtree
     * @param key String         search key
     * @return String            representing data in found node
     */
    public String find( BSTNode root, String key )
    {
        if ( root == null )
            return null;
        int cmp = root.data.compareTo( key );
        if ( cmp == 0 )
            return root.data;
        else if ( cmp > 0 )
            return find( root.left, key );
        else
            return find( root.right, key );
    }

    //--------------------- size() --------------------------------------
    /**
     * return size.
     * @return int
     */
    public int size()
    {
        return _size;
    }
    //--------------------- isEmpty() --------------------------------------
    /**
     * Return true if StringDictionary is empty.
     *
     * @return boolean
     */
    public boolean isEmpty()
    {
        return _root == null;
    }

    //---------------------- toString() --------------------------------
    /**
     * Make a string from dictionary, but don't print it all!
     *    limit the number of entries printed to the number
     *    defined by maxPrint.
     *
     * @return String
     */
    public String toString()
    {
        return toString( _root );
    }
    //---------------------- toString( BSTNode) ------------------------
    /**
     * Make a string from dictionary.
     *
     * @param root BSTNode
     * @return String
     */
    private String toString( BSTNode root )
    {
        if ( root == null )
            return "";

        return toString( root.left )
            + root.data + ", "
            + toString( root.right );
    }

    //--------------------- main ----------------------------------
    /**
     * A batch unit test for StringDictionary.
     *
     * @param args String[]      command line arguments (not used)
     */
    public static void main( String[] args )
    {
        batchTest();
    }
    //-------------   batchTest() ------------------------------------
    /**
     * Test in batch mode.
     */
    static void batchTest()
    {
        StringDictionary dict = new StringDictionary();
        String           ans = null;
        String           keys = "mhbaeczyrgjqsdft";

        for ( int k = 0; k < keys.length(); k++ )
            dict.add( keys.substring( k, k + 1 ) );

        /********** if have first */
        System.out.print( "first,next*:       " );
        ans = dict.first();
        while ( ans != null )
        {
            System.out.print( ans + ", " );
            ans = dict.next();
        }
        System.out.println();

        /***************************************/
        /******* if use reset() **/

        System.out.print( "reset,first,next*: " );
        dict.reset();
        ans = dict.next();
        while ( ans != null )
        {
            System.out.print( ans + ", " );
            ans = dict.next();
        }
        System.out.println();
        System.out.println( "Dictionary:        " + dict );
    }
}
