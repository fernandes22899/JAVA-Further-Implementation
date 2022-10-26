/**TreeNode father class. 
 * Date: 4/6/18
 * @author Jeff
 */
public abstract class TreeNode 
{
    private TreeNode left;
    private TreeNode right;
    
    /**TreeNode constuctor. 
     * @param leftNode TreeNode
     * @param rightNode TreeNode
     */
    public TreeNode( TreeNode leftNode, TreeNode rightNode ) 
    {
        this.left = leftNode;
        this.right = rightNode;
    }
    
    /**getLeft method. 
     * @return left
     */
    public TreeNode getLeft() 
    {
        return left;
    }
    
    /**getRight method. 
     * @return right
     */
    public TreeNode getRight() 
    {
        return right;
    }
    
    /**setLeft method. 
     * @param l TreeNode
     */
    public void setLeft( TreeNode l ) 
    {
        this.left = l;
    }
    
    /**setRight method. 
     * @param r TreeNode
     */
    public void setRight( TreeNode r ) 
    {
        this.right = r;
    }
    
    /**evaluation.
     * @param st symolTable
     * @return st
     */
    abstract float evaluate( SymbolTable st );
    
    /**print method.
     * @param d int
     * @return d
     */
    abstract String print( int d );
}
