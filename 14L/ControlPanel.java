//---------------------- ControlPanel.java -------------------------
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

/** 
 * ControlPanel.java:
 * A panel of sliders and other controls for the application.
 *
 * @author Matthew Plumlee
 * modified by rdb 
 * 
 * CS416 Spring 2008
 * Last editted: 
 *    03/16/14 rdb: make checkstyle-compatible
 */

public class ControlPanel extends JPanel 
{
    //---------------- instance variables ---------------------------
    private TreeApp    _app;
    
    //------------------- constructor -------------------------------
    /**
     * Container parent of the control panel is passed as an argument
     * and a reference to the application class.
     * 
     * @param app TreeApp    app to get informed of interaction     
     */
    public ControlPanel( TreeApp app ) 
    {
        super( new GridLayout( 0, 1 ) );
        _app    = app;
        
        //create sliders
        add( new DataSizeSlider() ); 
        add( new NodeWidthSlider() );
        
        //create misc check boxes and quit button
        JPanel buttonRow = new JPanel();
        
        buttonRow.add( new SeedSpinner() );
        //buttonRow.add( new QuitButton(), BorderLayout.EAST );
        
        add( buttonRow );
    }
    //+++++++++++++++++++++++ SeedSpinner class ++++++++++++++++++++
    /**
     * Private inner class to update spinner for recursive depth.
     */
    private class SeedSpinner extends LabeledSpinner 
    {
        //---------------- constructor --------------------------------
        /**
         * No argument constructor.
         */
        public SeedSpinner() 
        {
            super( "Random seed", -1, _app.maxSeed, _app.rngSeed );
        }
        //------------------------- valueChanged( int ) ---------------
        /**
         * Event listener.
         * @param newValue int     new value for the spinner.
         */
        public void valueChanged( int newValue ) 
        {
            _app.rngSeed = newValue;
        }
    }
    
    //+++++++++++++++++++++++ DataSizeSlider class ++++++++++++++++++++
    /**
     * Private inner class to update child size ratio with a slider.
     */
    private class DataSizeSlider extends LabeledSlider 
        implements ChangeListener
    {
        //---------------- constructor --------------------------------
        /**
         * No arg constructor.
         */
        public DataSizeSlider() 
        {
            super( "Data size", _app.minDataSize, _app.maxDataSize, 
                  _app.defaultDataSize );
            JSlider slider = getJSlider();
            slider.setMajorTickSpacing( 5 );
            slider.setMinorTickSpacing( 1 );
            addChangeListener( this ); // class is its own listener
        }
        //------------------- stateChanged( ... )  --------------------
        /**
         * listener method.
         * @param e ChangeEvent     event object
         */
        public void stateChanged( ChangeEvent e ) 
        {
            _app.dataSize = ( (JSlider)e.getSource() ).getValue();
        }
    }
    //+++++++++++++++++++++++ NodeWidthSlider class ++++++++++++++++++++
    /**
     * Private inner class to update the child size ratio with a slider.
     */
    private class NodeWidthSlider extends LabeledSlider 
        implements ChangeListener
    {
        //---------------- constructor --------------------------------
        /**
         * No arg constructor.
         */
        public NodeWidthSlider() 
        {
            super( "Node width", _app.minNodeWidth, _app.maxNodeWidth, 
                  _app.defaultNodeWidth );
            JSlider slider = getJSlider();
            slider.setMajorTickSpacing( 5 );
            slider.setMinorTickSpacing( 1 );
            addChangeListener( this );   // it is its own listener
        }
        //------------------- stateChanged( ... )  --------------------
        /**
         * Event listener method.
         * @param e ChangeEvent
         */
        public void stateChanged( ChangeEvent e ) 
        {
            _app.setNodeWidth( ( (JSlider)e.getSource() ).getValue() );
        }
    }
    //--------------------- main --------------------------------------
    /**
     * Convenience main; invokes app main.
     * @param args String[]    Command line arguments.
     */
    public static void main( String[] args )
    {
        TreeRecursion.main( args );
    }
}
