/**
 * Operator this class represents an operator nodethat isa subclass ofTreeNode.
 *
 * @author abdulla
 */
public class OperatorNode extends TreeNode 
{
    
    private char op;
    /**
     * Operator node.
     * @param op char
     * 
     * */
    public OperatorNode( char op )
    {
        super( null, null );
        this.op = op;
    }
    /**
     * OperatorNode.
     * @param op char
     * @param left TreeNode
     * @param right TreeNode
     * 
     * */
    public OperatorNode( char op, TreeNode left, TreeNode right )
    {
        super( left, right );
        this.op = op;
    }
    
    
    @Override
    public String toString() 
    {
        return "<" + op + ">";
    }
    /**
     * getOp.
     * @return op
     * 
     * */
    public char getOp() 
    {
        return op;
    }
    
    @Override
    float evaluate( SymbolTable st ) 
    {
        float flo = 0;
        TreeNode left = getLeft();
        TreeNode right = getRight();
        switch( op ) 
        {
            case '+':
                flo = left.evaluate( st ) + right.evaluate( st );
                break;
            case '-':
                flo = left.evaluate( st ) - right.evaluate( st );
                break;
            case '*':
                flo = left.evaluate( st ) * right.evaluate( st );
                break;
            case '/':
                flo = left.evaluate( st ) / right.evaluate( st );
                break;
            case '%':   
                flo = left.evaluate( st ) % right.evaluate( st );
                break;
            case '=':
                String var = ( ( VariableNode )left ).getVarName();
                float num = right.evaluate( st );
                st.setValue( var, num );
                flo = num;
                break;
        }
        return flo;
    }
    
    @Override
    String print( int d ) 
    {
        String str = "";
        TreeNode left = getLeft();
        TreeNode right = getRight();
        
        if( left != null )
            str = left.print( d + 1 );
        
        str += "\n";
        str += "\t";
        
        for( int i = 0; i < d * 2; i++ )
            str += " ";
        
        str += op + "\n";
        
        if( right != null )
            str += right.print( d + 1 );
        
        return str;
    }
}