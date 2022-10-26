/**
 * Variable - this class represents avariable nodethat is a subclass ofTreeNode.
 *
 * @author Abdulla 
 */
public class VariableNode extends TreeNode 
{
    
    private String str;
    private float flo;
    /**
     * VariableNode.
     * @param str String
     * */
    public VariableNode( String str )
    {
        super( null, null );
        this.str = str;
    }
    
    @Override
    public String toString() 
    {
        return str;
    }
    /**
     * getVarName.
     * @return str
     * */
    public String getVarName() 
    {
        return str;
    }
    
    @Override
    float evaluate( SymbolTable st ) 
    {
        flo = st.getValue( str );
        return flo;
    }
    
    @Override
    String print( int d ) 
    {
        String st = "\t";
        for( int i = 0; i < d * 2; i++ )
            st += " ";
        st += str;
        return st;
    }
    
}