/**
 * AShape.java
 * 
 * Interface for objects in the A-classes. 
 *   Need display method to be invoked explicitly.
 * @author rdb
 */
public interface AShape 
{
    /** implement display semantics.
      * @param brush java.awt.Graphics2D Java Graphics context
      */
    public void display( java.awt.Graphics2D brush );
}
