//+++++++++++++++++++++++++++ CardGroup +++++++++++++++++++++++++++

/**
 * CardGroup -- encapsulates shared features of card collections without
 *              imposing a particular semantics.
 *             mainly used to facilitate display.
 * @author rdb
 */
public interface CardGroup
{
    /** get i-th card. Meaning of i is impl dependent
      * @param i int 
      * @return Card   */      
    public Card getCard( int i );  
    
    /** return size of stack. 
      * @return int */
    public int  size(); 
    
    /** get x location of group. @return int */
    public int  getXLocation();
    /** get y location of group. @return int */    
    public int  getYLocation();       
    
    /** set x offset for card display in group.  
      * @param dx int  the x offset. */
    public void setXOffset( int dx ); 
    /** set y offset for card display in group.
      * @param dy int  the y offset. */
    public void setYOffset( int dy ); //    0,0 is on top of each other
    
    /** get x card offset from deck top.
      * @return int         get the x display offset */
    public int  getXOffset();         
    /** get y card offset from deck top.
      * @return int         get the y display offset */
    public int  getYOffset();  
    
    /** display top of deck.
      * @param count int  #cards on top to show 
      * @param face boolean  true implies show top cards face up */
    public void displayCards( int count, boolean face );
                             // redisplay the first "count" cards in
                             //   the group; face true means show
                             //   card face, else show the back   
}