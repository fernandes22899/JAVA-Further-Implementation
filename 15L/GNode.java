//-------------------------- GNode.java ------------------------------
import javax.swing.JLabel;
import javax.swing.border.*;
import java.awt.*;
/**
 * GNode - represents a d.s. node graphicially as a JLabel
 * @author rdb
 */

public class GNode extends JLabel
{
    //-------------------- instance variables -----------------------
    String nodeData;    // the data from the real node
    GNode  left;
    GNode  right;
    //-------------------- constructor ------------------------------
    /**
     * Set up default values, location gets specified later.
     * @param data String   data representing the Node
     * @param w    int      width for node label
     * @param h    int      height for node label
     */
    public GNode( String data, int w, int h )
    {
        super( data.toString() );
        nodeData = data;
        setFont( getFont().deriveFont( 11.0f ) ); // make font smaller
        
        setSize( w, h );
        setBorder( new LineBorder( Color.BLACK, 2 ) );
    }   
    //----------------- getNextStart() ----------------------------
    /**
     * Return the position that should be the start of a link.
     * @return Point    where a link should start
     */
    Point getStart()
    {
        return new Point( getX() + getWidth() / 2, 
                         getY() + getHeight() );
    }
    //----------------- getNextEnd() ----------------------------
    /**
     * Return the position that should be the end of a link.
     * @return Point    where a link should end
     */
    Point getEnd()
    {
        return new Point( getX() + getWidth() / 2, getY() );
    }
}