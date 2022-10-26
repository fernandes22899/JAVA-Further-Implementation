/**
 * OneWayList class -- extends LinkedList by implementing remove. 
 * 
 * @author rdb
 * March 2013
 * 
 * @param  <T> type of object that can be added to the list
 */

class OneWayList<T extends Comparable<String>> extends LinkedList<T> 
{   
    //---------------- constructor ------------------------------------
    // OneWayList constructor -- not needed
    
    
    //------------------- remove( Node<Data> ) ------------------------
    /** 
     * Remove the node from the list.
     * @param bye Node<T>
     */   
    void remove( Node<T> bye )
    {
        if ( bye == null )
            return;
        
        // Need to find the predecessor to the node being deleted.
        Node<T> prev = null;
        Node<T> walk = _head;
        while ( walk != null && walk != bye )
        {
            prev = walk;
            walk = walk.next;
        }
        if ( prev == null ) // deleting head
            _head = _head.next;
        else
            prev.next = bye.next;
        if ( _head == null )
            _tail = null;
        else if ( bye == _tail )
            _tail = prev;
        _size--;
    }
    //--------------------- main unit test ---------------------------   
    /**
     * A unit test. It calls LinkedList.testListOps to make and modify
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
        
        System.out.println( dashes + "1-way linked list tests" + dashes );
        for ( int i = 0; i < opStrings.length; i++ )
        {
            System.out.println( "+++++>  " + opStrings[ i ] );
            OneWayList<Data> list = new OneWayList<Data>();       
            LinkedList.testListOps( list, opStrings[ i ] );
            System.out.println();
        }
    }
}
