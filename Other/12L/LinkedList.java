//------------------------- LinkedList.java ---------------------------
import java.util.*;

/**
 * The LinkedList class -- provides some very primitive Node-based 
 *    functionality that can be shared by different kinds of linked lists.
 *    This is framework is somewhat contrived, but it helps for this
 *    low level pedagogically-driven exploration of different kinds of 
 *    linked list implementations
 * 
 * @author rdb
 * March 2013
 * @param  <T> type of object that can be added to the list
 */

abstract class LinkedList< T extends Comparable<String> >
{
    //---------------- instance variables -----------------------------
    Node<T>    _head = null;
    Node<T>    _tail = null;
    int        _size = 0;
    
    //---------------- constructor ------------------------------------
    // No constructor needed
    //------------------- head() ---------------------------------------
    /**
     * return a reference to the head node, or null if the list is empty.
     * 
     * @return Node<T>  a Node
     */
    Node<T> head()       // return head node
    {
        return _head;
    }
    //------------------- tail() ---------------------------------------
    /**
     * return a reference to the tail node, or null if the list is empty.
     * 
     * @return Node<T>  a Node
     */
    Node<T> tail()   // return tail node
    {
        return _tail;
    }
    //------------------- addHead( T ) --------------------------------
    /** 
     * add an element to the head of the list.
     * 
     * @param elem T   An object to be added
     */   
    void addHead( T elem ) // add an element to the head of the list
    {
        Node<T> n = new Node<T>( elem );
        n.next = _head;
        _head = n;
        if ( _tail == null )
            _tail = n;
        _size++;
    }
    
    //------------------- addTail( T ) -------------------------------
    /** 
     * add an element to the tail of the list.
     * 
     * @param elem T   An object to be added
     */   
    void addTail( T elem )
    {
        Node<T> n = new Node<T>( elem );
        if ( _head == null )
            _head = _tail = n;
        else
        {
            _tail.next = n;
            _tail = n;
        }
        _size++;
    }
    
    //----------------- find( String ) -------------------------------
    /**
     * find and return a Node whose key field matches the parameter.
     * 
     * @param key String  key to be searched for
     * @return Node<T>    a Node
     */
    Node<T> find( String key )   // return node whose key matches "key"
    {
        Node<T> walk = _head;
        while ( walk != null && walk.data.compareTo( key ) != 0 )
            walk = walk.next;
        return walk;
    }
    //------------------- remove( Node<T> ) ---------------------------
    /** 
     * Remove the node from the list.
     * 
     * @param bye Node<T>   A node to be removed
     */   
    abstract void remove( Node<T> bye );
    
    //--------------------- size() ------------------------------------
    /**
     * return the current size of the list.
     * 
     * @return int    size of list
     */
    public int size()      // return number elements in collection
    {
        return _size;
    }
    
    //--------------------- toString() -------------------------------
    /**
     * return a simple string representation of the list.
     * 
     * @return String 
     */
    public String  toString()      
    {
        StringBuffer s = new StringBuffer();
        
        Node<T> n = _head;
        while( n != null )
        {
            s.append( n.data + ", " );
            n = n.next;
        }
        if ( s.length() > 0 )
        {
            s.setCharAt( s.length() - 2, ' ' );
            s.setCharAt( s.length() - 1, ']' );
        }
        else
            s.append( " ]" );
        
        return  "<" + _size + ">[ " + s.toString();
    }
    //+++++++++++++++++++++ unit test methods ++++++++++++++++++++
    //--------------------  checkSize ----------------------------
    /**
     * Traverse a list counting the nodes on the list to verify that
     * this count matches what is returned from size() -- the _size field.
     * 
     * @param list LinkedList<Data>   the list to check.
     */
    static void checkSize( LinkedList<Data> list )
    {
        String msg = null;
        
        //////////////////////////////////////////////////////////////
        // Insert code here to traverse the list -- you may use either
        //   iteration (while loop) or recursion.
        // Use the variable, traversalSize, to count, after setting to 0
        //////////////////////////////////////////////////////////////
        
        int traversalSize = 0; // change this to assign 0
        Node<Data> current = list._head;
        while( current != null )
        {
            traversalSize++;
            current = current.next;
        }
        
        
        ////////////////////// end of your code //////////////////////
        
        if ( list instanceof SentinelList )
            traversalSize -= 2; // subtract 2 for sentinels
        if ( traversalSize != list.size() )
        {
            System.out.flush();
            System.err.println( 
                  "Size discrepancy: traversal = " + traversalSize 
                                + "  size() = " + list.size() );
        }
    }
    //--------------------- testListOps ---------------------------
    /**
     * Build a list to test implementation.
     * 
     * A unit test function for bulding lists for children of LinkedList.
     *    It takes two arguments:
     *       LinkedList<T>:  a list 
     *       String:         a operation string that describes a series
     *                       ops to be applied to the list. 
     *    The operation string consists of concatenations of a single 
     *        letter operation codes.
     *    The letters and the meaning of the number are:
     *       h    add a node to the head of the list
     *       t    add a node to the tail of the list
     *       f    search for a randomly selected node by id.
     *       F    search for a node by non-existent id.
     *       r    remove a randomly selected nodes from the list by id.
     *       s    change the size field to be incorrect, 
     *            then call checkSize again to see if 
     *            student implemented check size correctly.
     *       H    For sentinel list, try to force head sentinel delete
     *       T    For sentinel list, try to force tail sentinel delete
     * 
     * 
     *    Example:    hhthhhhttttfffFrrhrrtrrrrrtrhr
     * 
     *    Spaces are optional and are ignored.
     *
     * @param list LinkedList<Data>  reference to list.
     * @param opCodes String    h start by adding to head, t add to tail
     */
    static void testListOps( LinkedList<Data> list, String opCodes )
    {     
        Random rng = new Random( 0 );
        boolean linksOK;          // true if last op found no link error
        
        String[] keys = { "a", "b", "c", "d", "e" };
        String   key;
        int      keyIndex;
        String   keysInList = ""; 
        String   logMsg;
        
        for ( int c = 0; c < opCodes.length(); c++ )
        {
            char op = opCodes.charAt( c );
            logMsg = op + ": ";

            switch ( op ) 
            {
                case 'h': 
                    keyIndex = rng.nextInt( keys.length );
                    key = keys[ keyIndex ];
                    logMsg += key;
                    list.addHead( new Data( key, "r-" + key ) );
                    keysInList += key; 
                    break;
                case 't': 
                    keyIndex = rng.nextInt( keys.length );
                    key = keys[ keyIndex ];
                    logMsg += key;
                    list.addTail( new Data( key, "r-" + key ) );
                    keysInList += key;
                    break;
                case 'f': 
                case'F':
                    keyIndex = rng.nextInt( keysInList.length() );
                    key = "" + keysInList.charAt( keyIndex );
                    if ( op == 'F' )
                        key += "FFFF";  // this won't be found!
                    logMsg  += key + " --> ";
                    Node foundNode = list.find( key );
                    if ( foundNode == null )
                        logMsg += " -- NOT found"; 
                    else
                        logMsg += foundNode.data.toString();
                    break;
                case 'r':
                    if ( keysInList.length() == 0 )
                        logMsg += "List should be empty";
                    else 
                    {                        
                        keyIndex = rng.nextInt( keysInList.length() );
                        key = "" + keysInList.charAt( keyIndex );
                        logMsg  += key;
                        Node<Data> delNode = list.find( key );
                        if ( delNode == null )
                            logMsg += " -- NOT found"; 
                        else
                        {
                            list.remove( delNode );
                            keysInList = keysInList.substring( 0, keyIndex )
                                + keysInList.substring( keyIndex + 1 );
                            
                            //logMsg += delNode.data.toString();
                        }
                    }
                    break;
                case 's':
                    if ( keysInList.length() == 0 )
                        logMsg += "List should be empty";
                    else 
                        list._size++;
                    break;
                case 'H':
                    if ( keysInList.length() == 0 )
                        logMsg += "List should be empty";
                    else if ( list instanceof SentinelList )
                    {
                        Data headData = list._head.data;
                        if ( headData != null )
                        {
                            Node found = list.find( headData.key );
                            if ( found == list._head )
                                logMsg += "*** Found head sentinel ***";
                        }
                    }
                    break;
                case ' ':
                    break;
                default:
                    System.out.println( "ERROR: Invalid op code in "
                                           + "list test: " + op );
            }
            System.out.print( logMsg + " --> " );
            System.out.println( list );
            
            checkSize( list );
            
            if ( list instanceof TwoWayList )
            {
                TwoWayList.checkLinks( (TwoWayList<Data>) list );
            }
        }
    }      
}
