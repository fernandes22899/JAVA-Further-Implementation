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
        
        Node<T> set1 = new Node<T>();
        Node<T> set2 = new Node<T>();
        _head = set1;
        _tail = set2;
        _head.next = _tail;
        _tail.prev = _head;
        _size = 0;
        
        
    }
    
    //------------------- addHead( T ) --------------------------------
    /** 
     * Add an element to the _head of the list.
     * 
     * @param elem T   An object to be added
     */   
    void addHead( T elem ) // add an element to the _head of the list
    {
        Node<T> n = new Node<T>( elem ); 
        
        //////////////////////////////////////////////////////////////
        // Need to insert this new node AFTER the _head (sentinel) node, so
        // that it will be the first "real" node. Need to make sure both
        // the next and prev fields get updated appropriately
        /////////////////////////////////////////////////////////////
        
        _head.next.prev = n;
        n.next = _head.next;
        n.prev = _head;
        _head.next = n;
        _size++;
        
        
        ///////////////////////////////////////
        // be sure to update the _size field
        //////////////////////////////////////
        
    }    
    //------------------- addTail( T ) --------------------------------
    /** 
     * Add an element to the _tail of the list.
     * 
     * @param elem T   An object to be added
     */   
    void addTail( T elem )
    {
        Node<T> n = new Node<T>( elem );
        
        //////////////////////////////////////////////////////////////
        // Need to insert this new node BEFORE the _tail (sentinel) node, 
        // so that it will be the last "real" node. Need to make sure 
        // both the next and prev fields get updated appropriately
        /////////////////////////////////////////////////////////////
        
        _tail.prev.next = n;
        n.prev = _tail.prev;
        n.next = _tail;
        _tail.prev = n;
        _size++;
        
        ///////////////////////////////////////
        // be sure to update the _size field
        //////////////////////////////////////
        
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
        //    in the "_head" and stop when you get to the _tail node 
        //    (another sentinel)
        ///////////////////////////////////////////////////////////////
        Node<T> curr1 = _head.next;
        while( curr1 != null && curr1 != _tail )
        {
            if( curr1.data == key )
            {
                return curr1;
            }
            else
            {
                curr1 = curr1.next;
            }
        }
        
        return null;  // need to change this.
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
        Node<T> curr2 = _head; 
        while( curr2 != null )
        {
            if( curr2 == bye )
            {
                if( curr2.prev != null )
                {
                    curr2.prev.next = curr2.next;
                }
                if( curr2.next != null )
                {
                    curr2.next.prev = curr2.prev;
                }
                curr2 = null;
                _size--;
            }
            else
                curr2 = curr2.next;
        }
        //////////////////////////////////////
        // be sure to update the _size field
        //////////////////////////////////////
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
