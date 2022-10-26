//++++++++++++++++++++++++ ListApp +++++++++++++++++++++++++++++++++++
import java.util.Random;
import java.util.Scanner;
/**
 * ListApp: CS 416 Lab exercise: Code needs to be written for the methods:
 *
 *             loopAverage   (iterative)
 *             nodeSum       (recursive), called from recurseAverage
 *             reverse       (iterative)
 *             cursedReverse (recursive).
 *             compressList  (recursive OR iterative, your choice)
 * @author rdb
 */

public class ListApp
{
    //------------------- loopAverage( list ) ----------------------------
    /**
     * Compute and return the average value of all the value fields of
     * the Data objects in the list. Traverse the list using the
     * head() and next() methods of LinkedList. This is an iterative
     * solution.
     * @param list DataList
     * @return double
     */
    public static double loopAverage( DataList list )
    {
        double sum = 0.0;
        int    count = 0;

        ////////////////////////////////////////////////////
        // 1. add loop here to traverse list computing sum of value fields
        //++++++++++++++++++++++
        DataNode nd = list.head();
        while( nd != null )
        {
          sum += nd.data().value;
          nd = nd.next();
          count++;
        }
        
        //////////////////////////////////////////////////
        if ( count == 0 )
            return 0;
        else
            return sum / count;
    }
    //------------------- recurseAverage( list ) ----------------------
    /**
     * Compute and return the average value of all the value fields of
     * the Data objects in the list. Traverse the list by getting the node
     * that is the head of the list (the head() method of LinkedList)
     * and then using the next field of Node to get the next Node.
     *
     * The recursion should be done in the nodeSum helper method.
     * @param list DataList
     * @return double
     */
    public static double recurseAverage( DataList list )
    {
        DataNode head = list.head();
        if ( list.size() == 0 )
            return 0;
        else
            return nodeSum( head ) / list.size();
    }
    //------------------- nodeSum( Node ) -----------------------------
    /**
     * Compute and return the sum of the values for this node and all of
     * the remaining nodes after it on the list.
     * @param cur DataNode
     * @return double
     */
    public static double nodeSum( DataNode cur )
    {
        ///////////////////////////////////////////////////////////////
        // 2.  add this node's value to sum of values from rest of list
        //      return the sum.
        //++++++++++++++++++++++++++++
        
        if( cur == null )
          return 0;
        
        return cur.data().value + nodeSum( cur.next() );  // replace this line

        //////////////////////////////////////////////////////////////
    }
    //------------------- reverse( list ) -----------------------------
    /**
     * Create a new LinkedList that is in the reverse order of the list
     * passed as the argument. The input list should not be modified.
     * Traverse the input list using the head() and next() methods
     * of LinkedList. Add the element to the new list with addHead.
     * @param list DataList
     * @return DataList
     */
    public static DataList reverse( DataList list )
    {
        DataList newList = new DataList();

        //////////////////////////////////////////////////////////////
        // 3. traverse "list" in a loop getting each data item from list
        //     and adding it to the head of newList
        //++++++++++++++++++++++++++
        DataNode nd = list.head();
        while( nd != null )
        {
           newList.addHead( nd.data() );
           nd = nd.next();
        }

        return newList;
        //////////////////////////////////////////////////////////////
    }
    //------------------- cursedReverse( list ) ----------------------
    /**
     * Use recursion to create a new LinkedList that is in the reverse
     * order of the list passed as the argument. The input list should
     * not be modified.
     * Traverse the input list using the head() and next() methods
     * of LinkedList. Add the element to the new list with addHead.
     *
     * @param list DataList
     * @return DataList
     */
    private static DataList cursedReverse( DataList list )
    {
        if ( list == null )
            return null;
        else
            return cursedReverse( list.head() );
    }

    //----------- helper function
    /**
     * cursedReverse( DataNode )
     *     recursively chase list until get to the end.
     *     create new list at the end of the recursion, return
     *     add nodes to the returned list at the end of the list
     *        since you're coming back up, the end will be reversed.
     * @param node DataNode
     * @return DataList
     */
    public static DataList cursedReverse( DataNode node )
    {
        //////////////////////////////////////////////////////////////
        // 4. traverse "list", recursively
        //++++++++++++++++++++++++++
        if( node == null )
            return new DataList();
        
        DataList newList = cursedReverse( node.next() );
        newList.addTail( node.data() );
        
        return newList;
        
        //////////////////////////////////////////////////////////////
    }

    //---------------------- compress( list ) ----------------------------
    /**
     * Create a new list from the old. The new list will have just one
     * entry for each key; the value of that entry will be the sum of the
     * values of all the original entries with that key.
     *
     * @param list DataList
     * @return DataList
     */
    public static DataList compressList( DataList list )
    {
        DataList newList = new DataList();

        ////////////////////////////////////////////////////////////////////
        // 5. for each entry in the list
        //       if key is found in the  new list
        //          update the new list's value field for this key
        //       else
        //          add a node to the new list for this key/value
        //++++++++++++++++++++++++++++++++++
        DataNode comp = list.head();
        
        while( comp != null )
        {
            DataNode dn = newList.find( comp.data().key );
            if( dn != null )
                dn.data().value += comp.data().value;
            else
                newList.add( comp.data() );
            
            comp = comp.next();
        }
        
        return newList;
        ///////////////////////////////////////////////////////////////////
    }

    //***************** do not change anything below ******************

    //------------------- printList( list, String ) --------------------
    /**
     * Print a simple version of the list with a title.
     * @param list DataList
     * @param title String
     */
    public static void printList( DataList list, String title )
    {
        String dashes = "------------------------";
        System.out.println( "\n" + dashes + " " + title + " " + dashes );
        System.out.println( list );
        System.out.println( dashes + dashes + dashes );
    }
    //------------------- generateData --------------------------------
    /**
     * Generate some random list data.
     * @param numKeys int
     * @param numItems int
     * @param seed int
     * @return DataList
     */
    private static DataList generateData( int numKeys, int numItems, int seed )
    {
        DataList l = new DataList();
        Random rng;
        if ( seed < 0 )
            rng = new Random();
        else
            rng = new Random( seed );

        String letters = "abcdefghijkloaeipr";
        String[] keys = new String[ numKeys ];

        String uniqueKeys = "";
        for ( int i = 0; i < numKeys; i++ )
        {
            int letter1 = rng.nextInt( letters.length() );
            int letter2 = rng.nextInt( letters.length() );
            keys[ i ] =   letters.substring( letter1, letter1 + 1 )
                + letters.substring( letter2, letter2 + 1 );
            uniqueKeys += keys[ i ]  + " ";
        }
        System.err.println( keys.length + "  unique keys: " + uniqueKeys );

        for ( int i = 0; i < numItems; i++ )
        {
            String key = keys[ rng.nextInt( keys.length )];
            int    val = rng.nextInt( 100 );
            Data d = new Data( key, val );
            l.add( d );
        }
        return l;
    }
    //+++++++++++++++++++++++++ static methods +++++++++++++++++++++++++
    //--------------------- main ---------------------------------------
    /**
     * Main application invocation.
     *
     * @param args String[]   command line args
     */
    public static void main( String[] args )
    {
        int numKeys  = ReadArgs.getArg( args, 0, 6 );  // number unique keys
        int numItems = ReadArgs.getArg( args, 1, 8 );  // # items to generate
        int seed     = ReadArgs.getArg( args, 2, 0 );  // seed for data gen


        DataList list = ListApp.generateData( numKeys, numItems, seed );

        // 1. Test loopAverage method
        printList( list, "Initial list" );
        if ( list != null  && list.size() > 0 )
        {
            double loopAve = loopAverage( list );
            int total = (int) ( loopAve * list.size() );
            System.out. println( list.size()
                                    + " nodes: (loop) avg " + loopAve );
        }
        System.out.println( "---------------------------------" );


        // 2. Test recurseAverage method
        double recurseAve = ListApp.recurseAverage( list );
        System.out.println( "Recursive average " + recurseAve );
        System.out.println( "---------------------------------" );

        // 3. test reverse method
        DataList revList = ListApp.reverse( list );
        printList( revList, "Reversed list" );

        // 4. test recursive reverse
        list = ListApp.generateData( numKeys, numItems, seed );
        printList( list, "Initial list" );

        DataList recRevList = ListApp.cursedReverse( list );
        printList( recRevList, "cursedReverse" );
        DataList revRevList = ListApp.cursedReverse( recRevList );
        printList( revRevList, "Reverse cursedReverse" );

        // 5. test mergeEntries method
        // make new data, so effects of previous errors are minimized
        list = ListApp.generateData( numKeys, numItems, seed );
        ListApp.printList( list, "New List" );

        DataList clist = ListApp.compressList( list );
        ListApp.printList( clist, "After compression" );
    }
}
