//+++++++++++++++++++++++++ RadioSliderGUI +++++++++++++++++++++++++
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
/**
 * RadioSliderGUI: Demo program/lab using JSlider and JRadioButton.
 * 
 * @author rdb
 * 02/04/09
 * 
 * 01/31/14 rdb
 *    Replaced "magic" constants used for rectangle with named 
 *         constants as instance vars
 *    Proper initialized sliders/colors from target characteristics
 *         instance vars
 *    Added Reset button and setSelected functionality
 *    Made target instance var of the class; simpler access
 * 02/04/15 rdb Made checkstyle compatible
 */

public class RadioSliderGUI extends JPanel
{
    //---------------- class variables ------------------------------  
    //--------------- instance variables ----------------------------   
    private JShape       _target;     // the object being changed
    
    //--- initial characteristics of target and slider values
    private int          _initSize = 50;
    private int          _initX = 250;
    private int          _initY = 250;
    private Color        _initColor = Color.BLUE;
    private JRadioButton _initColorButton;
    
    
    //------------------- constructor -------------------------------
    /**
     * Constructor builds and displays gui interface.
     */
    public RadioSliderGUI()     
    {
        this.setLayout( new BorderLayout() );
        JPanel drawPanel = new JPanel();
        drawPanel.setLayout( null );
        this.add( drawPanel );
        
        // create a single rectangle in the middle of the window.
        //    position, size and color will be controlled by the GUI
        _target = new JRectangle( _initColor );
        _target.setSize( _initSize, _initSize );
        _target.setLocation( _initX, _initY );
        drawPanel.add( _target );
        
         // build the sliders to control position and size
        buildSliders( this );
        
        // build the radio button panel to change the color
        buildButtonPanel( this );
    }
    
    //------------------- buildSliders -------------------------------
    /**
     * Create 3 sliders and add using border layout.
     *   South region has a horizontal slider controlling x position
     *   West region has a vertical slider controlling y position 
     *   East region has a slider controlling the size
     * 
     * @param parent Container  where the sliders are to go
     */
    private void buildSliders( Container parent )
    {
        JSlider xSlider;    // slider controls x location
        JSlider ySlider;    // slider controls y location
        JSlider szSlider;   // slider controls height and width
        //------------- X Slider  ------------------------------------
        // a horizontal slider in the south panel
        //
        xSlider = new JSlider( JSlider.HORIZONTAL, 0, 500, _initX );
        addLabels( xSlider, 100 );
        xSlider.setBorder( new LineBorder( Color.BLACK, 2 ) );
        
        SliderListener xListen = new SliderListener( xSlider, "x" );
        xSlider.addChangeListener( xListen );

        parent.add( xSlider, BorderLayout.SOUTH );
        
        //------------- Y Slider  ------------------------------------
        //////////////////////////////////////////////////////////////
        // 3. Copy and edit the above X slider code to make a Y slider
        //    in the WEST border region.
        //    Add code to SliderListener to process X-slider events
        // 3a. Change orientation to vertical
        // 3b. Invert ySlider
        //////////////////////////////////////////////////////////////
        
        ySlider = new JSlider( JSlider.VERTICAL, 0, 500, _initY );
        addLabels( ySlider, 100 );
        ySlider.setBorder( new LineBorder( Color.BLACK, 2 ) );
        ySlider.setInverted( true );
        
        xListen = new SliderListener( ySlider, "y" );
        ySlider.addChangeListener( xListen );

        parent.add( ySlider, BorderLayout.WEST );
        
        
        //------------- Sz (size) Slider  -------------------------------
        //////////////////////////////////////////////////////////////
        // 5. Copy and edit y slider code to create Size slider 
        //    for changing size of the target JShape.
        //    Add code to SliderListener to process S-slider events.
        //////////////////////////////////////////////////////////////
        szSlider = new JSlider( JSlider.VERTICAL, 0, 200, _initSize );
        addLabels( szSlider, 100 );
        szSlider.setBorder( new LineBorder( Color.BLACK, 2 ) );
        szSlider.setInverted( true );
        
        xListen = new SliderListener( szSlider, "s" );
        szSlider.addChangeListener( xListen );

        parent.add( szSlider, BorderLayout.EAST );
        
        
    }  
    //---------------- addLabels( JSlider, int ) ---------------------
    /**
     * a utility method to add tick marks.
     * First argument is the slider, the second represents the
     *   major tick mark interval
     *   minor tick mark interval will be 1/10 of that.
     * @param slider JSlider
     * @param majorTicks int
     */
    private void addLabels( JSlider slider, int majorTicks )
    {
        slider.setPaintTicks( true );
        slider.setPaintLabels( true );
        slider.setMajorTickSpacing( majorTicks );
        slider.setMinorTickSpacing( majorTicks / 10 );
    }
    //+++++++++++++++++++ SliderListener inner class +++++++++++++++++
    /**
     * SliderListener needs access to 
     *   -- slider it is associated with (to get that slider's value)
     *   -- JShape that is being controlled.
     *   -- a string that serves as an identifier for the slider
     * These are passed to the constructor.
     */
    public class SliderListener implements ChangeListener
    {
        private JSlider    _slider;
        private String     _id;
        
        /**
         * SliderListener "package" constructor.
         * 
         * @param slider JSlider the slider to which we are listening
         * @param sliderId     String   an id for the slider
         */
        SliderListener( JSlider slider, String sliderId )
        {
            _slider = slider;
            _id     = sliderId;
            
        }
        //------------------- stateChanged ---------------------------
        /**
         * Invoked whenever user changes the state of a slider.
         * @param ev ChangeEvent
         */
        public void stateChanged( ChangeEvent ev )
        {
            //////////////////////////////////////////////////////////
            // 1a. add code to respond to the x-slider. it needs to
            //   change the x-position of the target object.
            //   The slider reference is in _slider.
            //   The current value of the slider is
            //      _slider.getValue();
            //   This value should be the x-parameter to
            //      _target.setLocation( x, y ).
            //   and the y-parameter should be _target.getY()
            /////////////////////////////////////////////////////////
            // 4a. After adding the y-slider, test here which slider
            //    generated the event; can do that by testing the
            //    _id field that was set in the constructor. 
            //    Compare it (using String equals method) to String
            //    used to create this instance of SliderListener.
            // 4b. Figure out how to invert the y-slider
            //////////////////////////////////////////////////////////
            // 5. After adding size slider, need to augment this code
            //    to identify and handle events from that slider.
            //////////////////////////////////////////////////////////
          if( _id.equals( "x" ) )
          {
            int x = _slider.getValue();
            int y = _target.getY();
            _target.setLocation( x, y );
            _target.getParent().repaint();
          }
            else if( _id.equals( "y" ) )
            {
              int x2 = _target.getX();
              int y2 = _slider.getValue();
              _target.setLocation( x2, y2 );
              _target.getParent().repaint();
            }
            else if( _id.equals( "s" ) )
            {
              int wh = _slider.getValue();
              _target.setSize( wh, wh );
              _target.getParent().repaint();
            }
        }
        
    }
    
    //--------------------- buildButtonPanel ------------------------
    /**
     * Build a button panel that contains:
     *   1. a group of "radio" buttons with exclusive behavior, exactly 
     *      one button can be pressed at a time; these buttons will
     *      select a color.
     *   2. a reset button that is not part of the radio behavior,
     *      but is used to restore the radio group to its default 
     *      setting.
     * 
     * @param parent Container   where the button panel is to go
     */
    private void buildButtonPanel( Container parent )
    {  
        JPanel      bPanel = new JPanel(); // defaults to FlowLayout        
        bPanel.setBorder( new LineBorder( Color.BLACK, 2 ) );
        
        buildRadioButtons( bPanel );
        buildResetButton( bPanel );
        
        parent.add( bPanel, BorderLayout.NORTH ); 
    }
    //---------------- buildRadioButtons( JPanel ) ----------------
    /**
     * Build a radio button group.
     * @param parent JPanel  where to put the radio buttons
     */
    private void buildRadioButtons( JPanel parent )
    {        
        String[]  labels = { "red", "green", "blue", "cyan", "magenta", "yellow" };
        Color[] colors = { Color.RED, Color.GREEN, Color.BLUE, Color.CYAN, Color.MAGENTA, Color.YELLOW };
        /////////////////////////////////////////////////////////////
        // 7. The ButtonGroup defines a set of RadioButtons that must be 
        //   "exclusive" -- only 1 can be "active" at a time. 
        //   Need to create ButtonGroup object (no arguments) and assign
        //      to a ButtonGroup variable..
        ///////////////////////////////////////////////////////////////
        ButtonGroup bg = new ButtonGroup();
        
        
        
        // for each entry in the labels array, create a JRadioButton
        for ( int i = 0; i < labels.length; i++ )
        {
            JRadioButton button = new JRadioButton( labels[ i ] );
            ButtonListener bListen =  new ButtonListener( colors[i] );
            button.addActionListener( bListen );
            parent.add( button );
            //////////////////////////////////////////////////////
            // 8. Test for initial color here, so first selected 
            //    button corresponds to initial color.
            //////////////////////////////////////////////////////
            if( colors[i] == _initColor )
            {
              button.setSelected( true );
              _initColorButton = button;
            }
            
            /////////////////////////////////////////////////////
            // 7. Add button to the ButtonGroup.
            /////////////////////////////////////////////////////
            bg.add( button );
            
        } 
    }
    //------------------ buildResetButton -----------------------
    /**
     * Build a reset button that resets the radiobutton group.
     * 
     * @param parent JPanel where to put the reset button
     */
    private void buildResetButton( JPanel parent )
    {
        JButton reset = new JButton( "Reset" );
        reset.addActionListener(
            new ActionListener() 
            { 
                public void actionPerformed( ActionEvent ev )
                {
                    System.out.println( "Reset invoked" );
                    _initColorButton.doClick();
                }
            }
        );
        parent.add( reset );
    }
    //+++++++++++++++++ ButtonListener inner class +++++++++++++++++++
    /**
     * This ButtonListener "knows" it is listening to an event from a
     * button and uses that knowledge in the actionPerformed method.
     */
    class ButtonListener implements ActionListener
    {
        //------ instance variables ---------------
         private Color        buttonColor;
        /**
         * Construct a ButtonListener object.
         */
        ButtonListener( Color c )
        {
          buttonColor = c;
        }
        //----------------------- actionPerformed --------------------
        /**
         * Get event and respond to it. We want to get the text of
         * the button label in order to know which button it is. 
         * We know that the button is a JRadioButton. However, it is
         * good practice to assume as little as you need to; it makes
         * the code more general-purpose. We need the getText() method,
         * which is defined by javax.swing.AbstractButton. So, we'll
         * get the Object from the ActionEvent using its getSource() 
         * and cast that to an AbstractButton and call getText().
         * 
         * @param ev ActionEvent  source event
         */
        public void actionPerformed( ActionEvent ev )
        { 
            // get a reference to the button that just got pressed, 
            //   get the text string, so we know which button it is.
            // 
            AbstractButton button = (AbstractButton) ev.getSource();
            
            String buttonLabel = button.getText(); // get text field.
            System.out.println( buttonLabel  + ": Action event.  " );
            
            // In this case, we only have 1 button using this listener
            //   so we don't have to test the buttonLabel, we just
            // update the target color and invoke its repaint()
            _target.setColor( buttonColor );
            _target.getParent().repaint();
        
        }
    }
    //+++++++++++++++++++++ main +++++++++++++++++++++++++++++++++++++
    /**
     * Convenience main to start main application;RadioSliderLab.main.
     * 
     * @param args String[]  command line arguments
     */
    public static void main( String[] args )
    {
        RadioSliderLab.main( args );
    }
}
