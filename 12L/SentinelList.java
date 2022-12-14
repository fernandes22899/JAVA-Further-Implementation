/**
 * The SentinelList class -- extends TwoWayList to be a 2-way list with 
 *          sentinels on both ends. It overrides 
 *             addHead
 *             addTail
 *             find
 *          and defines 
 *             remove
 *             Sentinel constructor.
 * 
 * @author rdb
 * March 2013
 * 
 * @param  <T> type of object that can be added to the list
 */

class SentinelList<T extends Comparable<String>> extends TwoWayList<T> 
{   
    //---------------- instance variables ----------------------------
    
    //---------------- constructor ------------------------------------
    /**
     * Constructor.
     */
    SentinelList()
    {
        ////////////////////////////////////////////////////////////
        // Add code to create the 2 sentinels and link them together to
        //    with both next and prev fields. Use the Node's no-argument
        //    constructor and realize that you need to specify the Generic 
        //    type for the Node since THIS class is a generic class
        //        i.e.   ...new Node<T>();  
        /////////////////////////////////////////////////////////////
        Node<T> start = new Node<T>();
        Node<T> end = new Node<T>();
        _head = start;
        _tail = end;
        _head.next = _tail;
        _tail.prev = _head;
        _size = 0;
        
    }
    
    //------------------- addHead( T ) --------------------------------
    /** 
     * Add an element to the head of the list.
     * 
     * @param elem T   An object to be added
     */   
    void addHead( T elem ) // add an element to the head of the list
    {
        Node<T> n = new Node<T>( elem ); 
        
        //////////////////////////////////////////////////////////////
        // Need to insert this new node AFTER the head (sentinel) node, so
        // that it will be the first "real" node. Need to make sure both
        // the next and prev fields get updated appropriately
        /////////////////////////////////////////////////////////////
        
        _head.next.prev = n;
        n.next = _head.next;
        n.prev = _head;
        _head.next = n;
        
        ///////////////////////////////////////
        // be sure to update the _size field
        //////////////////////////////////////
        _size++;
    }    
    //------------------- addTail( T ) --------------------------------
    /** 
     * Add an element to the tail of the list.
     * 
     * @param elem T   An object to be added
     */   
    void addTail( T elem )
    {
        Node<T> n = new Node<T>( elem );
        
        //////////////////////////////////////////////////////////////
        // Need to insert this new node BEFORE the tail (sentinel) node, 
        // so that it will be the last "real" node. Need to make sure 
        // both the next and prev fields get updated appropriately
        /////////////////////////////////////////////////////////////
        
        _tail.prev.next = n;
        n.prev = _tail.prev;
        n.next = _tail;
        _tail.prev = n;
        
        ///////////////////////////////////////
        // be sure to update the _size field
        //////////////////////////////////////
        _size++;
    }
    
    //----------------- find( String ) --------------------------------
    /**
     * Find and return a Node whose key field matches the parameter.
     * 
     * @param key String    Desired key match.
     * @return Node<T>      Found Node reference or null if not found
     */
    Node<T> find( String key )   // return node whose key matches "key"
    {
        ///////////////////////////////////////////////////////////////
        // search for the key, but start AFTER the sentinel node stored
        //    in the "head" and stop when you get to the tail node 
        //    (another sentinel)
        ///////////////////////////////////////////////////////////////
        Node<T> now = _head.next;
        while( now.data == key )
        {
            if( now.data == key )
                return now;
            else
                now = now.next;
        }
        
        return now;  // need to change this.
    }

    //------------------- remove( Node<Data> ) -----------------------
    /** 
     * Remove the node from the list.
     * 
     * @override
     * @param bye Node<T>    Node to be removed from the list.
     */   
    void remove( Node<T> bye )
    {
        if ( bye == null )
            return;
        
        ///////////////////////////////////////////////////////////////
        //  To "unlink" this node, you only need to adjust the prev  
        //  field of its next field and the next field of its prev field.
        ///////////////////////////////////////////////////////////////
        
        Node<T> now2 = _head;
        while( now2 != null )
        {
            if( now2 == bye )
            {
                if( now2.prev != null )
                    now2.prev.next = now2.next;
                if( now2.next != null )
                    now2.next.prev = now2.prev;
                now2 = null;
                _size--;
            }
            else
                now2 = now2.next;
        }
        
        //////////////////////////////////////
        // be sure to update the _size field
        //////////////////////////////////////
        _size++;
    }
    //--------------------- main unit test ---------------------------   
    /**
     * A unit test. It calls LinkedList.testList to make and manipulate
     *    the list.
     * 
     * @param args String[]  command line arguments
     */
    public static void main( String[] args )
    {
        String dashes = " -------------------------- ";
        String[] opStrings =
        {
            "hrtr",
            "trhtr",
            "hthftrrfrhrr",
            //"hhhhhrrrrtrhrrr"
        };
        
        if ( args.length > 0 )
            opStrings = args;
        
        System.out.println( dashes + "2-way sentinel list tests" + dashes );
        for ( int i = 0; i < opStrings.length; i++ )
        {
            System.out.println( "+++++>  " + opStrings[ i ] );
            SentinelList<Data> list = new SentinelList<Data>();       
            LinkedList.testListOps( list, opStrings[ i ] );
            System.out.println();
        }
    }
}
