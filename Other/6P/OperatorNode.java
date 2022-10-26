/**NumberNode represents a number.
 * Date: 4/4/18
 * @author jeff
 */
public class OperatorNode extends TreeNode 
{
    private char chop;
    
    /**OperatorNode.
     * @param oper char
     */
    public OperatorNode( char oper )
    {
        super( null, null );
        this.chop = oper;
    }
    
    /**OperatorNode.
     * @param op char
     * @param left TreeNode
     * @param right TreeNode
     */
    public OperatorNode( char op, TreeNode left, TreeNode right )
    {
        super( left, right );
        this.chop = op;
    }
    
    @Override
    public String toString() 
    {
        return "<" + chop + ">";
    }
    
    /**getOperator.
     * @return op
     */
    public char getOp() 
    {
        return chop;
    }
    
    @Override
    float evaluate( SymbolTable st ) 
    {
        float result = 0;
        TreeNode left = getLeft();
        TreeNode right = getRight();
        switch( chop ) 
        {
            case '+':
                result = left.evaluate( st ) + right.evaluate( st );
                break;
            case '-':
                result = left.evaluate( st ) - right.evaluate( st );
                break;
            case '*':
                result = left.evaluate( st ) * right.evaluate( st );
                break;
            case '/':
                result = left.evaluate( st ) / right.evaluate( st );
                break;
            case '%':   
                result = left.evaluate( st ) % right.evaluate( st );
                break;
            case '=':
                String var = ( ( VariableNode )left ).getVariableName();
                float num = right.evaluate( st );
                st.setValue( var, num );
                result = num;
                break;
        }
        return result;
    }
    
    @Override
    String print( int d ) 
    {
        String s = "";
        TreeNode left = getLeft();
        TreeNode right = getRight();
        
        if( left != null )
            s = left.print( d + 1 );
        s += "\n";
        s += "\t";
        for( int i = 0; i < d * 2; i++ )
            s += " ";
        s += chop + "\n";
        
        if( right != null )
            s += right.print( d + 1 );
        return s;
    }
}