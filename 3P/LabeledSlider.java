import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Dimension;

/** 
 * LabeledSlider.java:
 * 
 * A utility class that combines a label and slider and simplifies
 * the code needed to modify a value. 
 * 
 * CS416 Spring 2009
 * @author rdb
 */
public class LabeledSlider extends JPanel 
{
    //-------------------- instance variables -----------------
    JSlider _slider;   // references to the 2 components
    JLabel  _label; 
   
    //------------------------ constructors -------------------
    /**
     * int argument constructor defines a "value" slider
     *       (as opposed to a % slider).
     *
     * @param name Name for slider.
     * @param min Minimum value.
     * @param max Maximum value.
     * @param val Default value.
     * @param direction Direction for the slider.
     */
    public LabeledSlider ( String name, int min, int max,
                           int val, int direction ) 
    {
        super();
        // Create the label and slider and initialize their parameters.
        _label = new JLabel( name );
        this.add( _label );
        _slider = new JSlider( direction, min, max, val );
        _slider.setBorder( new LineBorder( Color.BLACK, 2 ) );

        _slider.addChangeListener( new ParameterListener() );
        _slider.setPaintTicks( true );
        _slider.setPaintLabels( true );
      
        // a little heuristic to guess at reasonable tick marks
        int majorTicks;
        int range = max - min;
        int numScores = range / 20 + 1;
        int numCs = range / 100 + 1;
        if ( numCs >= 3 )
            majorTicks = 100;
        else if ( numScores >= 2 )
            majorTicks = 20;
        else
            majorTicks = 1;
        int minorTicks = majorTicks / 10;
        if ( minorTicks == 0 )
            minorTicks = 1;
      
        _slider.setMajorTickSpacing( majorTicks );
        _slider.setMinorTickSpacing( minorTicks );
        this.add( _slider );
    }
 
    /**
     * Constructor for default Horizontal layout.
     *
     * @param name Name for slider.
     * @param min Minimum value for slider.
     * @param max Maximum value for slider.
     * @param val Default value.
     */
    public LabeledSlider( String name, int min, int max, int val ) 
    {
        this( name, min, max, val, JSlider.HORIZONTAL );
    }

    //--------------------- getJSlider() ----------------------
    /**
     * Get the JSlider.
     * 
     * @return the underlying JSlider for more parameter 
     * modification options.
     */
    public JSlider getJSlider()
    {
        return _slider;
    }
    //--------------------- valueChanged ----------------------
    /** If extending this class, need to override the 
     * valueChanged method.
     *
     * @param newValue New value selected.
     */
    protected void valueChanged( int newValue )
    {
    } // override for value
   
    //---------------- addChangeListener( ChangeListener ) -----
    /** 
     * The app can call this method to specify the change listener.
     * 
     * @param listener Change listener to add.
     */
    public void addChangeListener( ChangeListener listener )
    {
        _slider.addChangeListener( listener );
    }
  
    //+++++++++++++++++++++++++++ ParameterListener class +++++++
    /**
     * Inner class to serve as slider listener.
     */
    private class ParameterListener implements ChangeListener 
    {
        // no constructor needed -- parent works fine
        //-------------- stateChanged( ChangeEvent ) -----------
        /**
         * Called when state is changed.
         * 
         * @param e Event object for state change.
         */
        public void stateChanged( ChangeEvent e ) 
        {
            valueChanged( ( (JSlider)e.getSource() ).getValue() );
        }
    }
}
