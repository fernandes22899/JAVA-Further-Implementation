//+++++++++++++++++++++++++++++++ Node ++++++++++++++++++++++++++++++++++
import java.util.*;
/**
 * Node -- Represent a node in a directed graph
 *         Skeleton code for digraph lab
 * 
 * Features to be added (in addition to completing existing methods):
 *    1. a collection in which to store all e that start at this node
 *    2. public methods that return e; you might want to return one
 *       edge at a time and/or the entire collection
 *
 * @author rdb 
 * 10/31/10
 */

public class Node
{
    //----------------- instance variables ----------------------------
  private String iD;
  private ArrayList<Edge> e; 
    //----------------- Constructors ----------------------------------
    /**
     * Creates a node with the given id.
     * @param id String
     */
    public Node( String id )     
    {
      iD = id;
      e = new ArrayList<Edge>();
    }
    //----------------------------- getId() ---------------------------
    /**
     * Returns the name of this node.
     * @return String
     */
    public String getId()
    {
        return iD;
    }
    
    //----------------------------- addEdge( Edge ) -------------------
    /**
     * Add an edge that starts at this node to the edge list.
     * @param edge Edge   edge to be added
     */
    public void addEdge( Edge edge )
    {
      e.add ( edge );
    }
    
    //-------------------------- getEdges() ----------------------
    /**
     * Returns ArrayList<Edge> containing all e starting at this node.
     * @return ArrayList<Edge>     
     */
    public ArrayList<Edge> getEdges()
    {
        return e;
    }
    
    //----------------------- toString() ------------------------------
    /**
     * Returns the id of this node.
     * @return String
     */
    public String toString()
    {
        return iD;
    }
    
    //----------------------- longString() ---------------------------
    /**
     * Returns the extended information about the node:
     *    nodeId:  end1 end2 ... endk
     * where 
     *    endk are ids of nodes that are at ends of e from this node.
     * @return String
     */
    public String longString()
    {
        // "this" in the starting code line invokes Node.toString()
        //    so it will print the node id once step 1 is done.
      String str = "";
      for ( int i = 0; i < e.size(); i++ )
      {
        str += " " + e.get( i ).getEnd().toString();
      }
        return this + ": " + str;
    }
    
    //------------------------- main ----------------------------------
    /**
     * unit test code.
     * @param args String[]     // not used
     */
    public static void main( String[] args )
    {
        Node test = new Node( "A" );
        System.out.println( "getId() = " + test.getId() 
                               + "\ntoString= " 
                               + test 
                               + "\nlongString= " 
                               + test.longString() );
    }
}
