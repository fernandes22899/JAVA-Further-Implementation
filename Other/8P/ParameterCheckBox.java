import javax.swing.JCheckBox;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

/** 
 * ParameterCheckbox.java:
 * An abstract class representing a checkbox that displays a label 
 * and simplifies access to the checkbox.
 * 
 * Child classes must override the valueChanged method.
 * 
 * @author Matthew Plumlee
 * CS416 Spring 2008
 */

abstract public class ParameterCheckBox extends JCheckBox 
{
    //------------------- instance variables ------------------------
    //------------------- constructor --------------------------------
    /**
     * Accepts name for box and default state.
     *
     * @param name  Name for checkbox.
     * @param defaultVal  Default value for checkbox.
     */
    public ParameterCheckBox ( String name, boolean defaultVal ) 
    {
        super( name, defaultVal );
      
        addChangeListener( new ParameterListener() );
        updateUI();
    }

    //-------------------- valueChanged( boolean ) ---------------------
    /**
     * Invoked when value of checkbox is changed.
     *
     * @param newValue  New value for checkbox.
     */
    abstract protected void valueChanged( boolean newValue );
   
    //+++++++++++++++++++ ParameterListener inner class +++++++++++++++++
    /**
     * A CheckBox listener.
     */
    private class ParameterListener implements ChangeListener 
    {
        // no constructor needed -- super default works
        //------------------------ stateChanged ----------------------------
        /**
         * Invoked when state is changed.
         *
         * @param e  Event object.
         */
        public void stateChanged( ChangeEvent e )
        {
            valueChanged( ( (JCheckBox)e.getSource( ) ).isSelected( ) );
        }
    }
}
