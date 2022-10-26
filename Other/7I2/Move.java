/**
 * Move class for Aces Up.
 * 
 * Encapsulate the information for a Move.
 * This class represents the possible moves.
 *
 * @author rdb sna4
 */
public class Move
{    
    //------------------------ instance variables --------------------------
    ////////////////////////////////////////////////////
    // You need not use this variables. It is here so start code compiles.
    private Card card = null;
    
    /**Constructor.
     */ 
    public Move()
    {
    }
    
    /**Discard method skips cards by popping.
     * @param p Pile
     * @param trash Pile
     */ 
    public void discard( Pile p, Pile trash )
    {
        trash.getStack().push( p.getStack().pop() );
    }
    
    /**Draw Method pops cards out of first stack.
     * @param cards Pile
     * @param p Pile
     */
    public void draw( Pile cards, Pile p )
    {
        p.getStack().push( cards.getStack().pop() );
    }
    
    /**toEmpty method moves cards to trash.
     * @param p Pile
     * @param empty Pile
     */ 
    public void toEmpty( Pile p, Pile empty )
    {
        empty.getStack().push( p.getStack().pop() );
    }
    
    /**Method gets results.
     * @param ps Pile[]
     * @param trashPile Pile[]
     */
    public void result( Pile[] ps, Pile trashPile )
    {
        boolean boo = false;
        for( int i = 0; i < 4; i++ )
        {
            for( int y = 0; y < 4; y++ )
            {
                if( ps[i].peek().compareTo( ps[y].peek() ) < 0 )
                {
                    Reporter.report( "Loser" );
                }
                else
                {
                    if( 
                        ps[i].peek().compareTo( ps[y].peek() ) == 0 )
                    {
                        continue;
                    }
                    else if( ps[i].peek().compareTo( ps[y].peek() ) 
                                == 0 )
                    {
                        boo = true;   
                    }
                    if( boo == true )
                    {
                        Reporter.report( "WINNER!!!!!!" );
                    }
                }
            }
        }
    }
    
    //-------------------- toString() ---------------------------------
    /**
     * Return string representation for a Pile; needs to be compact.
     * @return String 
     */
    public String toString()
    {
        if ( card != null )
            return card.toString(); 
        else
            return "Draw";
    }  
}
