/**VariableNode subclass. 
 * Date: 4/6/18
 * @author Jeff
 */
public class VariableNode extends TreeNode 
{
    private String var;
    private float value;
    
    /**VariableNode constructor.
     * @param str String
     */
    public VariableNode( String str )
    {
        super( null, null );
        this.var = str;
    }
    
    @Override
    public String toString() 
    {
        return var;
    }
    
    /**getVariableName.
     * @return var
     */
    public String getVariableName() 
    {
        return var;
    }
    
    @Override
    float evaluate( SymbolTable symTable ) 
    {
        value = symTable.getValue( var );
        return value;
    }
    
    @Override
    String print( int depth ) 
    {
        String s = "\t";
        for( int i = 0; i < depth * 2; i++ )
            s += " ";
        s += var;
        return s;
    }
}