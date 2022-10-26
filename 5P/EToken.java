/**
 * EToken - an abstract class representing a token in an expression.
 *             subclasses are Operator and Operand
 *
 * @author rdb
 */
public abstract class EToken
{
    /**
     * Non-standard toString.
     * 
     * @return String representation.
     */
    abstract public String printable();

    /**
     * toString -- converts object to String.
     * @return String representation.
     */
    public String toString()
    {
        return printable();
    }

    //------------------ main unit test ------------------------
    /**
     * Main method.
     *
     * @param args Array of arguments.
     *
     */
    public static void main( String[] args )
    {
        try
        {
            EToken plus  = TokenFactory.makeToken( "+" );
            EToken times = TokenFactory.makeToken( "*" );
            EToken a     = TokenFactory.makeToken( "a" );
            EToken one   = TokenFactory.makeToken( "1" );

            System.out.println( a + " " + plus + " " + one + " "
                               + times + " " + a );
        }
        catch ( Exception e )
        {
            System.out.println( "Bad token: " + e );
        }
    }
}
