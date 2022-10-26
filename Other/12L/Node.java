/**
 * Node class: implements the node of a linked list as an independent 
 *             package-accessible class. This is often an internal class 
 *             within a list class, but it is convenient here to make it 
 *             its own independent class.
 *
 * @author rdb
 * March 2013
 * @param  <T> type of object that can be added to the list
 */

class Node< T extends Comparable<String>>
{
    //--------------- instance variables -----------------------------
    Node<T> next;  
    Node<T> prev;    // added for two-way list
    T       data;
    
    //--------------- Constructors -----------------------------
    /**
     * Constructor with an object.
     * 
     * @param d  T   a data item to be stored in the Node.
     */
    Node( T d )
    {
        data = d;
        next = null;
        prev = null;   // added for two-way list
    }
    
    /**
     * Construct empty Node.
     */
    Node()
    {
        data = null;
        next = null;
        prev = null;
    }
}