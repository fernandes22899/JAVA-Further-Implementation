/**NumberNode represents a number.
 * Date: 4/4/18
 * @author jeff
 */
public class NumberNode extends TreeNode 
{
    //------Instance Variables
    private Number num;
    
    /**constructor.
     * @param n Number
     */
    public NumberNode( Number n )
    {
        super( null, null );
        this.num = n;
    }
    
    /**evaluate method.
     * @param st SymblTable
     * @return float
     */
    @Override
    float evaluate( SymbolTable st ) 
    {
        return num.floatValue();
    }
    
    /**print method.
     * @param d int
     * @return s
     */
    @Override
    String print( int d ) 
    {
        String s = "\t";
        for( int i = 0; i < d * 2; i++ )
            s += " ";
        s += num.floatValue() + "";
        return s;
    }
}