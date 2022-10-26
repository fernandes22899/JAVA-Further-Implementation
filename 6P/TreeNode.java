public abstract class TreeNode
{
    //variables
    protected TreeNode left;
    protected TreeNode right;
    
    public TreeNode( TreeNode left, TreeNode right ) 
    {
        this.left = left;
        this.right = right;
    }
    
    public abstract float evaluate();
    abstract String print( int depth );
}