import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

/**
 * FractalGUI -- GUI framework.
 *      Builds GUI components for the assignment:
 *         sliders for: 
 *            -ratio of child size to parent's size
 *            -offset of child position along the edge of parent
 *            -height of the initial triangle
 *            -projection point of the bottom point of triangle
 *             onto the top edge (as a % of distance from left 
 *             to right) the % can range from -20% to 120%
 *         radio buttons for:
 *            -choosing between filled hollow triangles
 *            -choosing between drawing child triangles on the
 *             outside or inside of the parent triangle
 *         spinner for determining recursive depth limit
 *       
 * @author rdb
 * 091910
 */
public class FractalGUI extends JPanel 
{
    //---------------- class variables ------------------------------
    private static int defaultHeight = 173;
    // this is sqrt(3); it makes first triangle equilateral
   
   
    //--------------- instance variables ----------------------------   
    private FractalTriangle _baseTri;
   
    // baseWidth and height are for outside generation
    //    twice these values are used for inside generation
    private  int baseWidth = 200;
    private  int height = 173; // makes an equilateral triangle; default
   
    //---- sliders ---
    private  JSlider _sizeSlider;      // These variables reference the JSlider
    private  JSlider _offsetSlider;    //  members in the LabeledSlider objects
    private  JSlider _projectionSlider;
    private  JSlider _heightSlider;
      
    //------------------ constructor ----------------------------
    /**
     * Constructor with parent object.
     *
     * @param parent Parent container.
     *
     */
    public FractalGUI( Container parent )     
    {
        this.setLayout( new BorderLayout() );
        buildGUI();
        FractalTriangle.maxDepth = 1;
      
        makeScene();
    }
    //--------------------- reset() ----------------------------
    /**
     * reset all generation parameters to their initial values 
     * (except for the spinner).
     */
    private void reset()
    {
        ///////////////////////////////////////////////////////
        // handle the reset button: restore 4 slider parameters
        // to their initial default values.
        ///////////////////////////////////////////////////////
        System.out.println( "Reset Pressed" );
        
        _sizeSlider.setValue( ( int ) Math.round( 0.5 * 100 ) );
        _offsetSlider.setValue( ( int ) Math.round( 0.5 * 100 ) );
        _projectionSlider.setValue( ( int ) Math.round( 0.5 * 100 ) );
        _heightSlider.setValue( ( int ) Math.round( 0.5 * 100 ) + 130 );
        
        height = defaultHeight;
        
        makeScene();      
    }
 
    //--------------- makeScene() --------------------------------
    /**
     * generate the FractalTriangle image.
     * 
     * Create an initial Triangle that is an appropriate 
     * size and location depending on whether the
     * recursion will be on the outside or the inside.
     */
    private void makeScene()
    {
        Point p0, p1;   // endpoints of the top of the triangle
        Line  top;      // the line representing the triangle top
        int   h;        // height of initial triangle
        int   w;        // width of initial triangle
        int   x, y;     // location of initial triangle
      
        if ( FractalTriangle.outside )  // start smaller so have room outside
        {
            w = baseWidth;
            x = 250;
            y = 200;
            h =  height;         
        }
        else   // start bigger, so have room inside
        {
            w = baseWidth * 2;
            x = 150;
            y = 100;
            h  = height * 2;
        }

        p0 = new Point( x, y );
        p1 = new Point( x + w, y );
        top = new Line( p0, p1 );  
         
        if( FractalTriangle.maxDepth > 0 )
        {
            double proj = FractalTriangle.p2projection;
            Point p2 = top.getPointOnNormalAt( proj, h );
            ATriangle tri = new ATriangle( p0, p1, p2 );
            _baseTri = new FractalTriangle( 1, tri, h );
        }
      
        repaint();
    } 
 
    //--------------------- buildGUI() ------------------------
    /**
     * create all the GUI components.
     */
    private void buildGUI( )
    {
        JPanel sliders = new JPanel();
        sliders.setBorder( new LineBorder( Color.BLACK, 2 ) );
        sliders.setLayout( new GridLayout( 0, 2 ) );
      
        buildSliders( sliders );
        add( sliders, BorderLayout.SOUTH );
      
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder( new LineBorder( Color.BLACK, 2 ) );
        buildButtons( buttonPanel );
            
        add( buttonPanel, BorderLayout.NORTH );
    }
   
    //--------------------- buildSliders( Container ) -----------
    /**
     * create the sliders in the Container passed as a parameter.
     *
     * @param sliders Container for sliders.
     */
    private void buildSliders( Container sliders )
    {   
        LabeledSlider slider;
        //------------- Size Slider  ------------------------
        int initVal;
        initVal = (int)Math.round( FractalTriangle.sizeRatio * 100 );
        slider = new LabeledSlider( "Child size ratio", 0, 100, initVal );
        slider.addChangeListener( new SizeListener() );
        sliders.add( slider );
        _sizeSlider = slider.getJSlider();
 
        //------------- Offset Slider  ----------------------
        initVal = (int)Math.round( FractalTriangle.offset * 100 );
        slider = new LabeledSlider( "Child offset %", 0, 100, initVal );
        slider.addChangeListener( new OffsetListener() );
        sliders.add( slider );
        _offsetSlider = slider.getJSlider();
 
        //------------- Initial Height Slider  --------------
        slider = new LabeledSlider( "Initial height", 100, 400, height );
        slider.addChangeListener( new InitialHeightListener() );
        sliders.add( slider );
        _heightSlider = slider.getJSlider();
      
        //------------- projection Slider  ------------------
        initVal = (int)Math.round( FractalTriangle.p2projection * 100 );
        slider = new LabeledSlider( "Projection", -20, 120, initVal );
        slider.addChangeListener( new ProjectionListener() );
        sliders.add( slider );
        _projectionSlider = slider.getJSlider();
    }

    //--------------------- buildButtons( Container ) -------
    /**
     * create the buttons and spinner in the Container
     * passed as a parameter.
     * 
     * @param buttonPanel Panel for buttons.
     */
    private void buildButtons( Container buttonPanel )
    {
        String labelText = "Recursion depth";
        LabeledSpinner depthSpinner = new LabeledSpinner( labelText, 1, 6, 1 );
        depthSpinner.addChangeListener( new DepthListener() );
        buttonPanel.add( depthSpinner );
                 
        JRadioButton rButton = new JRadioButton( "Fill" ); 
        rButton.addActionListener( new FillListener() );
        rButton.setSelected( true );
        buttonPanel.add( rButton );
            
        rButton = new JRadioButton( "Outside" ); 
        rButton.addActionListener( new SideListener() );
        rButton.setSelected( true );
        buttonPanel.add( rButton );

        JButton button = new JButton( "Reset parameters" );
        button.addActionListener( new ResetListener() );
        button.setSelected( false );
        buttonPanel.add( button );
    }
      
    //++++++++++++++++++ inner classes +++++++++++++++++++++++
    //+++++++++++++++++++++ SizeListener class +++++++++++++++
    /**
     * SizeListener inner class - responds to slider that specifies
     * the size ratio of a child to its parent.
     * The integer percentage value from the slider must be converted to a
     * floating point (double) fraction. This value is assigned to the 
     * FractalTriangle class variable, sizeRatio, but only if the value is
     * greater than or equal to 5.
     */
    public class SizeListener implements ChangeListener
    {
        //------------------- stateChanged --------------------
        /**
         * Called when state is changed.
         * 
         * @param ev Event object for state change.
         */
        public void stateChanged( ChangeEvent ev )
        {
            ///////////////////////////////////////////////////////
            // handle event to change the relative size of children
            ///////////////////////////////////////////////////////
            //System.out.println( "size listener changed" );
            if( _sizeSlider.getValue() >= 5 )
                FractalTriangle.sizeRatio = 
                    ( double )_sizeSlider.getValue() / 100;
            
            makeScene();
        }
    }
    //+++++++++++++++++++++ OffsetListener class ++++++++++++++
    /**
     * OffsetListener inner class - responds to slider that specifies
     * the offset of the position of a child triangle on its parent's edge.
     * The integer percentage value from the slider must be converted to a
     * floating point (double) fraction. This value is assigned to the 
     * FractalTriangle class variable, offset. 
     */
    public class OffsetListener implements ChangeListener
    {
        //------------------- stateChanged -------------------
        /**
         * Called when state is changed.
         * 
         * @param ev Event object for state change.
         */
        public void stateChanged( ChangeEvent ev )
        {
            ///////////////////////////////////////////////////////
            // handle event to change the offset size of children
            ///////////////////////////////////////////////////////
            //System.out.println( "offset changed" );
            if( _offsetSlider.getValue() >= 50 )
                FractalTriangle.offset =
                    ( double ) _offsetSlider.getValue() / 100;
            else
                FractalTriangle.offset =
                    ( double ) _offsetSlider.getValue() / 100;
            
            makeScene();
        }
    }
    //+++++++++++++++++++++ ProjectionListener class +++++++++
    /**
     * ProjectionListener inner class - responds to slider 
     * that specifies the position of the projection of p2 
     * onto the line formed by p0 and p1.
     * The integer percentage value from the slider must be 
     * converted to a floating point (double) fraction. This 
     * value is assigned to the FractalTriangle class variable, 
     * p2projection. 
     */
    public class ProjectionListener implements ChangeListener
    {
        //------------------- stateChanged --------------------
        /**
         * Called when state is changed.
         * 
         * @param ev Event object for state change.
         */
        public void stateChanged( ChangeEvent ev )
        {
            ///////////////////////////////////////////////////////
            // handle event to change the projection of p2 
            ///////////////////////////////////////////////////////
            //System.out.println( "projection changed" );
            if( _projectionSlider.getValue() >= 20 )
                FractalTriangle.p2projection =
                    ( double ) _projectionSlider.getValue() / 100;
            else
                FractalTriangle.p2projection =
                    ( double ) _projectionSlider.getValue() / 100;
            
            makeScene();
        }
    }
   
    //+++++++++++++++++++++ InitialHeightListener class ++++++++
    /**
     * responds to a slider that sets the height for the 
     * initial triangle. The value is assigned to an instance 
     * variable of this class, height.
     */
    public class InitialHeightListener implements ChangeListener
    {
        //------------------- stateChanged ----------------------
        /**
         * Called when state is changed.
         * 
         * @param ev Event object for state change.
         */
        public void stateChanged( ChangeEvent ev )
        {
            ///////////////////////////////////////////////////////
            // handle event to change the height root
            ///////////////////////////////////////////////////////
            //System.out.println( "height changed" );
            if( _heightSlider.getValue() >= FractalTriangle.minHeight )
                height = _heightSlider.getValue();
            
            makeScene();
        }
    }
   
    //+++++++++++++++++++++++++++ DepthListener class +++++++++++
    /**
     * responds to a Spinner that sets the max recursion depth.
     * The value is assigned to a FractalTriangle class variable, 
     * maxDepth.
     */
    public class DepthListener implements ChangeListener
    {
        //------------------- stateChanged ----------------------
        /**
         * Called when state is changed.
         * 
         * @param ev Event object for state change.
         */
        public void stateChanged( ChangeEvent ev )
        {
            JSpinner spinner = (JSpinner) ev.getSource();
            Number spinValue = (Number) spinner.getValue();
            FractalTriangle.maxDepth = spinValue.intValue();
            _baseTri = null;
            makeScene();
        }
    }
   
    //+++++++++++++++++++++++++++ FillListener class ++++++++++++
    /**
     * responds to an event from a JRadioButton that identifies
     * whether to fill the triangles or just draw outlines.
     */
    private class FillListener implements ActionListener
    {
        /**
         * Called when action is performed.
         * 
         * @param ev Event object for action.
         */
        public void actionPerformed( ActionEvent ev )
        {
            FractalTriangle.fillTriangles = !FractalTriangle.fillTriangles;
            makeScene();
        }
    }

    //+++++++++++++++++++++++++++ SideListener class +++++++++++
    /**
     * responds to an event from a JRadioButton that identifies
     * whether to draw chidren on the inside or the outside of 
     * their parent.
     */
    private class SideListener implements ActionListener
    {
        /**
         * Called when action is performed.
         * 
         * @param ev Event object for action.
         */
        public void actionPerformed( ActionEvent ev )
        {
            FractalTriangle.outside = !FractalTriangle.outside;
            makeScene();
        }
    }
   
    //+++++++++++++++++++++++++++ ResetListener class +++++++++++
    /**
     * responds to an event from a JButton that identifies that
     * the generation parameters should be reset to their initial
     * values.
     */
    private class ResetListener implements ActionListener
    {
        /**
         * Called when action is performed.
         * 
         * @param ev Event object for action.
         */
        public void actionPerformed( ActionEvent ev )
        {
            JButton button = ( JButton ) ev.getSource();
            button.setSelected( false );
            reset();
        }
    }
 
    //-------------- paintComponent ----------------------------
    /**
     * paintComponent - overrides parent method.
     * @param g Graphics object for drawing.
     */
    public void paintComponent( Graphics g )
    {
        //System.out.println( "paint" );
        super.paintComponent( g );
        Graphics2D g2 = (Graphics2D) g;
      
        if( _baseTri != null )
            _baseTri.display( g2 );      
    }
    //------------------------------- main ----------------------
    /**
     * Main method.
     *
     * @param args Array of arguments.
     *
     */
    public static void main( String [] args ) 
    {
        FractalApp app = new FractalApp( "FractalApp demo", args );
    }
}
