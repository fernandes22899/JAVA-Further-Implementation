/**
 * Constant-thisclassrepresents anumberber nodewhich isa subclassofTreeNode.
 *
 * @author abdulla
 */
public class NumberNode extends TreeNode 
{
    
    private Number number;
    /**
     * NymberNode constructor.
     * @param number Number
     * 
     * 
     * */
    public NumberNode( Number number )
    {
        super( null, null );
        this.number = number;
    }
    
    @Override
    /**
     * floating.
     * @param symTable st
     * 
     * */
    float evaluate( SymbolTable st ) 
    {
        return number.floatValue();
    }
    
    @Override
    /**
     * print.
     * @param d int
     * @return str
     * */
    String print( int d ) 
    {
        String str = "\t";
        for( int i = 0; i < d * 2; i++ )
            str += " ";
        str += number.floatValue() + "";
        return str;
    }
    
}