/**
 * The BiNode class represents a node in a spatial binary tree
 * Each node corresponds to a range of a one-dimensional space
 * defined by bounds passed to the constructor. If the node has
 * children, they split this range into two parts -- not necessarily 
 * of equal size.
 * 
 * @author rdb 4/8/09
 * Last edited: 4/25/2014 rdb: formatted, added more useful comments
 */
import java.util.*;

public class BiNode
{
    //-------------- instance variables ---------------------------
    public Data data;
    
    private ArrayList<Integer> _values;
    private int                _floor;
    private int                _ceil;
    
    private int   _splitVal;   // if have children, this is split value
    private int   _size;       // number points in this region
    
    public BiNode _left;
    public BiNode _right;
    public BiNode _parent;
    
    //--------------- constructor --------------------------------
    /**
     * This node stores all 1D points, p in the range:
     * [ floor, ceiling ), which means that floor <= p < ceiling.
     */
    public BiNode( int floor, int ceil )
    {
        _floor = floor;
        _ceil = ceil;
        _left = _right = null;
        _values = new ArrayList<Integer>();
        data = new Data( "[" + floor + "," + ceil + "]", 0 );
        
        //////////////////////////////////////////////////////
        // 4a. initialize parent field
        //////////////////////////////////////////////////////
        
        _parent = null;
        
        
        
    }
    //-------------------- updateData() ------------------------------------
    /**
     * This node has just been split or merged. Its values have been 
     * distributed to its 2 children (there will always be exactly 0 
     * or 2 children) OR some values have been added to it.
     *
     * Update the data.count field, which should be the count of all
     * the descendants of this node. The data is stored only in the
     * leaves, but the counts need to cover the entire subtree reachable
     * from this node.      
     */
    private void updateData()
    {
        /////////////////////////////////////////////////////////////
        // 2. Add code here to fill in the data.count field with the
        //   sum of the counts of the children of the node if this
        //   is not a leaf node. You can use the size method of BiNode
        //   for the left and right children.
        // If this is a leaf node, the numbers in the node are stored
        //   in the _values ArrayList object, so you can get the 
        //   this count by calling its size() method.
        ///////////////////////////////////////////////////////////
        if( _left == null && _right == null )
          data.count = _values.size();
        else
          data.count = _left.size() + _right.size();
        
        
        
    }
    //------------------ findPoints( int, int, ArrayList ) ----------------
    /**
     * If there are any points in this node that fall into the specified range,
     * add them to the ArrayList.
     */
    public void findPoints( int min, int max, ArrayList<Integer> found )
    {        
        ///////////////////////////////////////////////////////////////
        // 3. Add code here to check the  values in this node's _values 
        //    to see if they fall within the min/max parameters passed. 
        //    If they are in specified range, they should be copied into
        //    the ArrayList that ispassed in.
        //
        // If the node has children their findPoints methods should be 
        //    called to add their data to found array. Note that if a 
        //    node has children, it shouldn't have any values in the 
        //   _values array.
        //
        // There is a handy utility method you might want to use:
        //    intersects( min, max ) returns true if the range (min, max) 
        //    overlaps with this node's minimum and maximum values.
        //
        ///////////////////////////////////////////////////////////////
        if( intersects( min, max ) )
        {
          if( _left != null )
          {
            _left.findPoints( min, max, found );
            _right.findPoints( min, max, found );
          }
          else
          {
            for( int i = 0; i < _values.size(); i++ )
            {
              if( _values.get( i ) >= min && _values.get( i ) <= max )
                found.add( _values.get( i ) );
            }
                          
          }
        }
        
    }   
    
    //--------------------- split ------------------------------
    /**
     * Split the values in this node into two children:
     *     val < splitValue go into left child
     *     val >= split value go into right child
     */
    public void split( int splitValue )
    {
        if ( splitValue <= _floor || splitValue > _ceil )
            return;   // do nothing if split value is not valid
        
        _splitVal = splitValue;
        _left = new BiNode( _floor, splitValue );
        _right = new BiNode( splitValue, _ceil );
        ////////////////////////////////////////////////////
        // 4a. set _parent fields of the 2 new BiNodes to reference
        //    this node.
        ////////////////////////////////////////////////////
        _left._parent = this;
        _right._parent = this;
        
        
        // copy values into left or right nodes based on split value
        for ( Integer val: _values )
        {
            if ( val < splitValue )
                _left.add( val );
            else
                _right.add( val );
        }
        _values.clear();
        updateData();
    }
    //------------------------ add( int ) ------------------------
    /**
     * add an int location to the tree
     */
    public void add( int val )
    {
        _size++;
        if ( val < _floor || val >= _ceil )
        {
            System.err.println( "Can't add: " + val 
                                   + " to node range: [ " 
                                   + _floor + ", " + _ceil + " )" );
            _size--;
        }
        else if ( _left == null )
            _values.add( new Integer( val ));
        else if ( val < _splitVal )
            _left.add( val );
        else 
            _right.add( val );
        updateData();
    }
    
    //------------------ findLeaf( int ) -----------------------
    /**
     * Find the node in the tree where the point should be located
     */
    public BiNode findLeaf( int value )
    {
        if ( !valueInRange( value ))
            return null;
        if ( _left == null )
            return this;
        else 
        {
            BiNode node = _left.findLeaf( value );
            if ( node != null )
                return node;
            else
                return _right.findLeaf( value );
        }         
    }
    
    //------------- intersects( int, int ) ---------------------------
    /**
     * Does this min,max range specified in the arguments overlap with 
     *    the min (_floor) and max (_ceil) of this node.
     */
    private boolean intersects( int min, int max )
    {
        return ( min <= _ceil && max >= _floor );
    }
    //------------- valueInRange( int ) -----------------------------
    /**
     * Is the value passed as the parameter contained in the range 
     *    specified for this node?
     */
    private boolean valueInRange( int val )
    {
        return ( val >= _floor && val < _ceil );
    }
    
    //-------------------------- getValues() -----------------------
    /**
     * Return an ArrayList of all values in the subtree rooted at this
     *    node.
     */
    public ArrayList<Integer> getValues()
    {
        if ( _left == null )
            return _values;
        else
        {
            ArrayList<Integer> all = new ArrayList<Integer>();
            findPoints( _floor, _ceil, all );
            return all;
        }
    }
    //-------------------------- toString() -------------------------
    /**
     * Generate a string representation of the tree.
     */
    public String toString()
    {
        return toString( this, "  ", "  " ) ;        
    }
    //------------------- toString( BiNode, String, String ) ---------
    /**
     * recursively generate a string representation for a Node of a tree.
     * indent is increased for increasing depth.
     * branch is a short character string that prefixes each node indicating
     *        whether this node is a left (L) or right (R) child of its parent.
     */
    private String toString( BiNode n, String indent, String branch )
    {
        String s = "";
        if ( n != null )
        {
            String prefix = indent.substring( 0, indent.length() - 2 ) + branch;
            s += prefix + n.dataToString() + "\n";
            if ( n._left != null )
                s += toString( n._left, indent + "  ", "L " );
            if ( n._right != null )
                s += toString( n._right, indent + "  ", "R " );
        }
        return s;
    }
    public String dataToString()
    {      
        return "{" + _floor + "," + _ceil + "}:" + data.count + " "
                                   + toString( _values );
    }
    //------------------------ height() ---------------------------
    /**
     * compute height of subtree.
     */
    public int height()
    {
        int lh = -1;
        int rh = -1;
        if ( _left != null )
            lh = _left.height();
        if ( _right != null )
            rh = _right.height();
        return 1 + Math.max( lh, rh );
    }
    //------------------------ size() ---------------------------
    /**
     * return # nodes in subtree
     */
    public int size()
    {
        return _size;
    }
    /****/   
    //++++++++++++++++++++++ class methods ++++++++++++++++++++++++
    //----------------------- toString --------------------------
    public static String toString( ArrayList<Integer> fa )
    {
        if ( fa.size() == 0 )
            return "[]";
        StringBuffer sb = new StringBuffer( "[" );;     
        for ( Integer f: fa )
            sb.append( f.intValue() + "," );
        sb.setCharAt( sb.length() - 1, ']' );
        return new String( sb );
    }
    //----------------------- printArray --------------------------
    private static void printArray( String title, ArrayList<Integer> fa )
    {
        String dash = " ----------------- ";
        System.out.println( dash + title + dash );
        System.out.println( toString( fa ));  
        System.out.println( "----------------------------------------" );
    }
    //----------------------- main ---------------------------
    public static void main( String[] args )
    {
        BinTreeLab app = new BinTreeLab( "BiNode", args );
        /****
          int max = 99;
          BiNode root = new BiNode( 0, max );
          for ( int f = 0; f < max; f += 10.0 )
          root.add( f );
          System.out.println( root );
          
          root.split( 35 );
          System.out.println( root );
          
          root._right.split( 70 );
          System.out.println( root );
          
          ArrayList<Integer> search = new ArrayList<Integer>();
          root.findPoints( 30, 70, search );
          printArray( "find 30,70", search );
          /*******************************/
        
    }
}
