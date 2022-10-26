//++++++++++++++++++ DataNode class ++++++++++++++++++++++++++++++++
/**
 * The DataNode class is public. DataNode stores a Data object.
 * 
 * @author rdb
 */
public class DataNode 
{
    private DataNode _next = null;
    private Data _data = null;
    
    /** 
     * Construct a node with the passed in Data object, add it after
     * the passed in node.
     * @param newData Data   data to be added to list
     * @param n DataNode     add the new node where this node is in list
     */   
    public DataNode( Data newData, DataNode n )
    {
        _data = newData;  
        _next = n;
    }
    //---------------- next() ------------------
    /** 
     * getter method t move along the list.
     * 
     * @return DataNode   the next node on the list
     */
    public DataNode next()
    {
        return _next;
    }
    //---------------- data() ------------------
    /**
     * return the data field from the current node.
     * @return Data
     */
    public Data data()
    {
        return _data;
    }
    //---------------- setNext( DataNode ) ------------------
    /**
     *  set this point's next field to the parameter.
     * 
     * @param n DataNode new next in list
     */
    public void setNext( DataNode n )
    {
        _next = n;
    }
}