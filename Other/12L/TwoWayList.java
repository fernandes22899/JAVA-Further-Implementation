/**
 * The TwoWayList class -- extends LinkedList to implement a 2-way list
 *    by completing the final method, remove, and overriding addHead 
 *    and addTail in order to set the "prev" links.
 * 
 * @author rdb
 * March 2013
 * 03/02/14 Testing code added
 * 
 * @param  <T> type of object that can be added to the list
 */

class TwoWayList<T extends Comparable<String>> extends LinkedList<T> 
{   
    //---------------- instance variables ----------------------------
    
    //---------------- constructor ------------------------------------
    // TwoWayList constructor -- not needed
    
    //------------------- addHead( T ) -----------------------------
    /** 
     * Add an element to the head of the list.
     * 
     * @param elem T   node to be added 
     */   
    void addHead( T elem ) // add an element to the head of the list
    {
        //////////////////////////////////////////////////////////////
        // Look at the 'addHead' of LinkedList. You need to add code to
        //   handle the 'prev' references.
        //////////////////////////////////////////////////////////////
        
        
        Node<T> n = new Node<T>( elem );
        if( _head == null )
        {
            _head = n;
            _tail = n;
        }
        else
        {   
            n.next = _head;
            _head.prev = n;
            _head = n;
        }
        _size++;
        
        ///////////////////////////////////////
        // be sure to update the _size field
        //////////////////////////////////////
    }
    
    //------------------- addTail( T ) -------------------------------
    /** 
     * Add an element to the tail of the list.
     * 
     * @param elem T   node to be added 
     */   
    void addTail( T elem )
    {
        //////////////////////////////////////////////////////////////
        // Look at the 'addTail' of LinkedList. You need to add code to
        //   handle the 'prev' references.
        //////////////////////////////////////////////////////////////
        
        Node<T> n = new Node<T>( elem );
        if ( _head == null )
            _head = _tail = n;
        else
        {
            _tail.next = n;
            n.prev = _tail;
            _tail = n;
        }
        _size++;
        
        ///////////////////////////////////////
        // be sure to update the _size field
        //////////////////////////////////////
    }
    
    //------------------- remove( Node<Data> ) ----------------------
    /** 
     * Remove the node from the list.
     *     bye.prev.next = bye.next
     *     bye.next.prev = bye.prev
     * 
     * @param bye Node<T>   node to be deleted 
     */   
    void remove( Node<T> bye )
    {
        if ( bye == null )   // don't really need this, but it's safe to leave.
            return;
        
        ///////////////////////////////////////////////////////////////
        // 1. Need to find the predecessor to the node being deleted.
        //   but this is just the prev reference!
        //
        // 2. Make node prior to the one being deleted "skip" over the 
        //    deleted one by setting its "next" field to the "next" field
        //    of the node to be deleted.
        // 3. Make the node after the one being deleted "skip" over the 
        //    deleted node when going through the "prev" links. 
        // BUT WATCH OUT. You need to be sure to test for special cases, 
        //    like when the node being deleted is either the head or tail.
        //////////////////////////////////////////////////////////////
        
        if( bye == _head && bye == _tail )
        {
            _head = null;
            _tail = null;
            _size--;
        }
        else if( bye == _head )
        {
            _head = _head.next;
            _head.prev = null;
            _size--;
        }
        else if( bye == _tail)
        {
            _tail = _tail.prev;
            _tail.next = null;
            _size--;
        }
        else
        {
            Node<T> curr = _head;
            while( curr != null )
            {
                if( curr == bye )
                {
                    if( curr.prev != null )
                    {
                        curr.prev.next = curr.next;
                    }
                    if( curr.next != null )
                    {
                        curr.next.prev = curr.prev;
                    }
                    curr = null;
                    _size--;
                }
                else
                    curr = curr.next;
                
            }
        }        
        ///////////////////////////////////////
        // be sure to update the _size field
        //////////////////////////////////////
    }
    //+++++++++++++++++++++ unit test methods ++++++++++++++++++++
    //--------------------  checkLinks ----------------------------
    /**
     * Traverse a list in an attempt to find inconsistencies in the
     * list's next an prev links.
     * 
     * Tests:
     *  1. make sure that if head == null, tail == null and vice versa.
     *  2. make sure that head.prev == null and tail.next == null
     *  3. make sure that each node's "prev" link (except head) 
     *     references a Node whose "next" link references this one.
     *  4. make sure that each node's "next" link (except tail)
     *     reference a Node whose "prev" link references this one.
     *  5. make sure that the node whose "next" link is null is the
     *     same node referenced by tail.
     * 
     * @param list LinkedList<Data>   the list to check.
     */
    static void checkLinks( TwoWayList<Data> list )
    {
        if ( list.head() == null && list.tail() == null )
            return;
        if ( list.head() == null && list.tail() != null )
        {
            System.out.println( "null head, but tail is: " + list.tail() );
            System.out.println( "**** Ending test *****" );
            return;
        }
        if ( list.head().prev != null )
            System.out.println( "head.prev = " + list.head().prev.data );
        if ( list.tail().next != null )
            System.out.println( "tail.next = " + list.tail().next.data );
        
        Node<Data> cur = list.head();
        Node<Data> expectPrev = null; 
        Node<Data> back = null;
        while ( cur.next != null )
        {
            if ( cur.prev != expectPrev )
                printError( "cur.prev != expectPrev", cur.prev, expectPrev );
            if ( cur.next.prev != cur )
                printError( "cur.next.prev != cur", cur.next.prev, cur );
            expectPrev = cur;
            cur = cur.next;
        } 
        // when we get here, cur should be tail
        //   still need to check its prev
        if ( cur.prev != expectPrev )
            printError( "cur.prev != expectPrev", cur.prev, expectPrev );
        if ( list.tail() != cur )
            printError( "End of list != tail", cur, list.tail() );
    }
    //------------------- printError --------------------------------
    /**
     * Print an error when a link isn't right.
     * 
     * @param msg String    Informative text
     * @param node1 Node<Data> normally the earlier node in the list
     * @param node2 Node<data> normally the next node in the list, or tail
     */
    static void printError( String msg, Node<Data> node1, Node<Data> node2 )
    {
        System.out.flush();
        System.err.println( msg + ": " + node1.data + " != " + node2.data );
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
        
        System.out.println( dashes + "2-way linked list tests" + dashes );
        for ( int i = 0; i < opStrings.length; i++ )
        {
            System.out.println( "+++++>  " + opStrings[ i ] );
            TwoWayList<Data> list = new TwoWayList<Data>();       
            LinkedList.testListOps( list, opStrings[ i ] );
            System.out.println();
        }
    }
}
