//-------------------------- GNode.java ------------------------------
import javax.swing.JLabel;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
/**
 * GNode - represents a d.s. node graphicially as a JLabel
 *         this version gets mouse events.
 * @author rdb
 */

public class GNode extends JLabel  implements MouseListener
{
    //-------------------- instance variables -----------------------
    BinarySearchTree.Node bstNode; // reference to the bst node
    GNode left;
    GNode right;
    //-------------------- constructor ------------------------------
    /**
     * Set up default values, location gets specified later.
     * @param node BinarySearchTree.Node  BST Node from which to make a GNode
     * @param w    int      width for node label
     * @param h    int      height for node label
     */
    public GNode( BinarySearchTree.Node node, int w, int h )
    {
        super( node.data.key );  // Only use the key field in disply
        bstNode = node;
        setFont( getFont().deriveFont( 12.0f ) ); // make font smaller
        
        setSize( w, h );
        setBorder( new LineBorder( Color.BLACK, 2 ) );
        addMouseListener( this );
    }   
    //----------------- getStart() ----------------------------
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
     * @return Point    where a link should ejnd
     */
    Point getEnd()
    {
        return new Point( getX() + getWidth() / 2, getY() );
    }
    //--------------------- mousePressed --------------------------
    /**
     * mousePressed -- used to delete the node.
     * @param e MouseEvent
     */ 
    public void mousePressed( MouseEvent e ) 
    {
        System.out.println( "MousePressed: " + bstNode.data );
        TreeApp.treeApp().mouseEvent( this.bstNode );
    }
    
    //----------------------------------------------------------
    /** Unimplemented. @param e MouseEvent */
    public void mouseClicked( MouseEvent e ) 
    { }
    /** Unimplemented. @param e MouseEvent */
    public void mouseReleased( MouseEvent e ) 
    { }
    /** Unimplemented. @param e MouseEvent */
    public void mouseEntered( MouseEvent e ) 
    { }
    /** Unimplemented. @param e MouseEvent */
    public void mouseExited( MouseEvent e ) 
    { }
}