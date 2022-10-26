/**
 * Move class for Aces Up.
 * 
 * Encapsulate the information for a Move.
 *
 * @author rdb sna4
 */
public class Move
{    
    //------------------------ instance variables --------------------------
    ////////////////////////////////////////////////////
    // You need not use this variables. It is here so start code compiles.
    private Card card = null;
    
 
    





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
