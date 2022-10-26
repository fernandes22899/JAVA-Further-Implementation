/**
 * Chapter 7: FrameTimer.java (originally named, MoveTimer)
 * A subclass of Timer that can be used for animation.
 * It also serves as an example of the code for an "event source" object.
 * Version 2 of 2
 * 
 * Last editted: 08/05/14: Changed parameter to NewFrame object instead of
 *                         Animated.
 */
import javax.swing.*;
import java.awt.event.*;

public class FrameTimer extends javax.swing.Timer 
{
    //---------------- instance variables --------------------------
    private NewFrame _mover; // peer object
    
    //------------------- constructor -------------------------------
    /**
     * Takes a time interval (in milliseconds) and an NewFrame object
     */
    public FrameTimer( int anInterval, NewFrame aFramer ) 
    {
        super( anInterval, null );
        _mover = aFramer;          // save reference to the object
        
        // create an instance of the inner class, MoveListener
        MoveListener myListener = new MoveListener();
        
        // tell the javax.swing.Timer object to tell myListener
        //   object whenever  the timer interval elapses.
        this.addActionListener( myListener );
    }
    
    //+++++++++++++++ MoveListener "inner" class +++++++++++++++++++++
    private class MoveListener implements ActionListener 
    {
        //---------------- actionPerformed( ActionEvent ) -------------
        /**
         * This method is called when the time interval elapses.
         */
        public void actionPerformed( ActionEvent e )
        {
            // invoke the "newFrame" method of the NewFrame object
            _mover.newFrame();
        }
    }
}

