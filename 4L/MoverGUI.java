//++++++++++++++++++++++++++++ MoverGUI.java +++++++++++++++++++++++++++
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.event.*;
/**
 * Chapter 7: MoverGUI.java
 * Creates the panel to be placed inside the SwingApp window.
 * 
 * @author rdb
 *
 * 1/30/08: rdb
 *    Renamed (old name was BallApp)
 * 01/28/09: rdb
 *    Revised to use null Layout
 * 12/31/12: rdb
 *    Revision so that students need to create the drawing panel
 *       that goes into the CENTER of this panel once the layout
 *       is changed to BorderLayout
 *    Also removed the explicit w,h parameters
 * 1/29/13 rdb
 *      revised event handling code to separate "listener" code
 *      from "responder" code, which is now in handleButtonEvent.
 * 1/28/14 rdb 
 *      added interactive toggle of minimum step size
 *      re-formatted; removed some magic numbers
 */

public class MoverGUI extends JPanel implements NewFrame 
{
    //--------- package static variables ---------------------
    //---- command line arguments
    static int     dxDefault = 4;
    static int     dyDefault = 8;
    static int     dxInitialMove = dxDefault;
    static int     dyInitialMove = dyDefault;
    
    //---- other "constants" we might want to change
    static float   fasterRatio = 1.2f;
    static float   slowerRatio = 0.8f;    
    static int     minStepSize = 3;
    static boolean minStepFlag = false;
    
    //------------------------- instance variables -------------------
    private JSnowMan _snowman;
    
    private FrameTimer _timer;
    private final int INTERVAL = 100;
    
    //------------------------- constructor --------------------------
    /** Constructor takes no arguments. */
    public MoverGUI() 
    {
        super();
        /////////////////////////////////////////////////////////////
        // Although it isn't critical, you might find it interesting
        //   to compile and execute after each of the 4 steps below.
        //   Don't worry if you don't understand the behavior you see.
        // Need to modify the 2 lines below to:
        // 2a. change setLayout parameter to "new BorderLayout()".
        // 2b. create a new JPanel as the "drawPanel" with null layout 
        // 2c. Add it to "this" panel's CENTER region, which is the 
        //    default if you do not specify a region.
        // 2d. Pass the new drawPanel to buildDisplay instead of this.
        /////////////////////////////////////////////////////////////
        
        setLayout( new BorderLayout() ); // no layout manager
        
        JPanel drawPanel = new JPanel(null);
        this.add( drawPanel, BorderLayout.CENTER );
        
        
        buildDisplay( drawPanel );           
        
        /////////////////////////////////////////////////////
        buildGUI();    // add the GUI components for this app
        this.setBackground( Color.white );
        
        _timer = new FrameTimer( INTERVAL, this );
        _timer.start();
    }
    //-----------------------  buildDisplay() ------------------------
    /**
     * encapsulate code that builds display components for startup.
     * @param panel JPanel
     */
    private void buildDisplay( JPanel panel )
    {
        // for now just have 1 snow man
        _snowman = new JSnowMan( 50, 80 );
        _snowman.setMove( dxInitialMove, dyInitialMove ); 
        panel.add( _snowman );
    }
    //-----------------------  buildGUI() --------------------------
    /**
     * Encapsulate code that builds GUI components for the program.
     * 
     * Here we call 3 different methods: each shows a slightly
     * different way to implement the listener/responder interface.
     */
    private void buildGUI()
    {
        // Put several animation-related buttons in the South
        JPanel buttonPanel = buildButtonPanel();
        this.add( buttonPanel, BorderLayout.SOUTH );

        // create a toggle button for imposing a minimum step size
        //   add it to buttonPanel
        JToggleButton minStepButton = makeStepSizeToggle();        
        buttonPanel.add( minStepButton );
        
        // create unrelated buttons in the North/East/West regions.
        makeGenericButtons();
    }
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //---------------------  buildButtonPanel() ----------------------
    /**
     * Create a panel containing related buttons whose events are all
     * handled by different instances of the same event handler class.
     */
    private JPanel buildButtonPanel()
    {
        //////////////////////////////////////////////////////////////
        // Step 3a: Add 2 labels and make the labels meaningful:
        //               Start, Stop, Faster, Slower and Reset
        //////////////////////////////////////////////////////////////
        String labels[] = { "Start", "Stop", "Faster", "Slower", "Reset" };
        
        JPanel buttonPanel = new JPanel();
        for ( int i = 0; i < labels.length; i++ )
        {
            JButton button = new JButton( labels[ i ] );
            button.addActionListener( new MyButtonListener( i ));
            buttonPanel.add( button );
            
        }
        return buttonPanel;
    }
    //++++++++++++++++++++++ MyButtonListener ++++++++++++++++++++++++++
    /** public inner class for button event handler. */
    public class MyButtonListener implements ActionListener
    {
        int _btnId;  // which button is associated with this ActionListener
        /**
         * Constructor takes an integer id for the specific button.
         * @param btnId int
         */
        public MyButtonListener( int btnId )
        {
            _btnId = btnId;   // save the id for the actionPerfomed invocation
        }
        /** The listener event handler. @param ev ActionEvent */
        public void actionPerformed( ActionEvent ev )
        {
            //////////////////////////////////////////////////////////
            // 3b. This is a "listener" class event; when it gets told
            //    that an event has occurred, it just passes an id
            //    of the button to the event Responder, which is
            //    the handleButtonEvent method of the outer class.
            //    Make that call here.
            //////////////////////////////////////////////////////////
          handleButtonEvent( _btnId );
            
        }
    }    
    //------------------  handleButtonEvent( int ) ------------------
    /**
     * Event responder method. Carries out application specific 
     * processing. This responder handles all the buttons in the
     * south panel, distinguishing the button by an integer 
     * parameter associated with the function.
     * @param buttonId int
     */
    private void handleButtonEvent( int buttonId )
    {
        //////////////////////////////////////////////////////////////
        // 3c. add code here to test _btnId and print a message to 
        //     System.out, identifying which button was pressed.
        // 4.  Add code for the start and stop buttons.
        // 5.  Implement the "faster" button, using MoveGUI.fasterRatio
        //   a. Define int and float versions of dx, dy step sizes
        //      Use float for all calculations, assign final values
        //      to int versions using Math.round() method.
        //   b. Extend print statements of step 3 to include dx,dy
        // 6.  Implement "slower" button, using MoveGUI.slowerRatio
        //   a. Use same int/float variables.
        //   b. Extend print statement for slower option to include
        //      dx, dy.
        // 7. Add code to prevent dx and dy from getting less than
        //    MoverGUI.minStepSize.
        // 9a. Surround the minStepSize tests with an "if" that only
        //     makes the tests if MoverGUI.minStepFlag is true.
        // 10. Implement Reset button code. 
        //////////////////////////////////////////////////////////////
      float x1, y1;
      int x2, y2;
      
      
      switch( buttonId )
      {
        case 0: System.out.println( "Start" );
        _timer.start();
        break;
        case 1: System.out.println( "Stop" );
        _timer.stop();
        break;
        case 2: System.out.println( "Faster" );
        x1 = _snowman.getMoveX() * fasterRatio;
        y1 = _snowman.getMoveY() * fasterRatio;
          
        x2 = Math.round( x1 );
        y2 = Math.round( y1 );
          
        JSnowMan.setMove( x2, y2 );
        break;
        case 3: System.out.println( "Slower" );
        if( minStepFlag == true )
        {
          if( x1 > minStepSize )
          {
            x1 = _snowman.getMoveX() * slowerRatio;
            x2 = Math.round( x1 );
          }
          if( y1 > minStepSize )
          {
            y1 = _snowman.getMoveY() * slowerRatio;
            y2 = Math.round( y1 );
          } 
        }
        
        JSnowMan.setMove( x2, y2 );
        break;
        case 4: System.out.println( "Reset" );
        break;
      }

    
    }
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //------------------ makeStepSizeToggle() ------------------------
    /**
     * make a JToggleButton for turning stepsize limit on/off
     * 
     * This method makes use of an anonymous class that we create as
     * a child of the ActionListener class. The only feature we need
     * to add to the ActionListener implementation is to override 
     * the actionPerformed method definition. 
     */
    private JToggleButton makeStepSizeToggle()
    {
        ///////////////////////////////////////////////////////////
        // 9b. Change "Toggle" label to something indicating that
        //       this button used for enabling/disabling minStepSize
        //       enforcement.
        //     Fill in actionPerformed method
        //     Test that snowman stops with toggle off, but can start
        //        up again with enough "faster" clicks.
        ///////////////////////////////////////////////////////////
        JToggleButton toggle = new JToggleButton( "Toggle" );
        toggle.addActionListener( 
                 // create an unnamed subclass of ActionListener by
                 //   following the "new" statement with { .. } that
                 //   contain the definition of the class inside.
                 new ActionListener()
                 {   // all we want to do is override actionPerformed
                     public void actionPerformed( ActionEvent ev )
                     {  
                         // 8d. toggle value of minStepFlag
                         //      false <-> true
                         System.out.println( "Toggle it!" ); 
                     }
                 });
        return toggle;
    }
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++    
    //---------------------  makeGenericButtons() -----------------------
    /**
     *  Create some unrelated buttons in various places, but have
     *  them handled by a single instance of a general purpose button
     *  event handler.
     */
    private void makeGenericButtons()
    {
        GenericButtonListener listen = new GenericButtonListener();
        
        JButton msgButton;
        msgButton = new JButton( "North" );
        msgButton.addActionListener( listen );
        this.add( msgButton, BorderLayout.NORTH );
        
        msgButton = new JButton( "East" );
        msgButton.addActionListener( listen );
        this.add( msgButton, BorderLayout.EAST );
        
        msgButton = new JButton( "West" );
        msgButton.addActionListener( listen );
        this.add( msgButton, BorderLayout.WEST );
    } 
    //------------------ GenericButtonListener class -----------------
    /** 
     *  public inner class for a generic button event listener.
     *    It identifies the triggered button by its label.
     *    It performs "event responder" actions as well as "listener" 
     *       actions.
     */
    public class GenericButtonListener implements ActionListener
    {
        public void actionPerformed( ActionEvent ev )
        {
            // ActionEvent object getSource() method returns the source
            //   object for the event. We know in this case that it is
            //   a JButton object, so we can cast the return value to
            //   JButton and then get its text field:
            
            JButton button = (JButton)( ev.getSource() );
            String label = button.getText();
            
            // Because we don't expect common processing for thes
            //  "unrelated" buttons, we test for the button, by 
            //  getting the text label for it and do the appropriate
            //  processing. (Clearly in this demo, we don't need the
            //  nested if statements.)
            if ( label.equals( "North" ))
                System.out.println( "Do North button processing." );
            else if ( label.equals( "East" ))
                System.out.println( "Do East button processing." );
            else if ( label.equals( "West" ))
                System.out.println( "Do West button processing." );
         }
    }    //++++++++++++++++++ Animated interface methods ++++++++++++++++++
    private boolean _animated = true;  // instance variable
    //---------------------- isAnimated() ----------------------------
    public boolean isAnimated()
    {
        return _animated;
    }
    //---------------------- setAnimated( boolean ) ------------------
    public void setAnimated( boolean onOff )
    {
        _animated = onOff;
    }
    //-------------------------- newFrame() --------------------------
    /**
     * implements Animated interface. On each time event, 
     * move each component
     */
    public void newFrame() 
    {
        _snowman.newFrame();
        this.repaint();
    }   
    //---------------------------- readArgs ----------------------------
    /**
     * Set up the runtime static parameters than can be controlled by command
     *    line arguments.
     * @param args String[]   command line arguments
     */
    public static void readArgs( String[] args )
    {
        /////////////////////////////////////////////////////////
        // 1: Add processing of command line arguments here.
        //       args[ 0 ] should set to dxInitialMove
        //           if it is present and is a valid integer
        //           otherwise it should be set to dxDefault
        //       args[ 1 ] should set MoverGUI.dyInitialMove
        //           if it is present and is a valid integer
        //           otherwise it should be set to dyDefault
        //
        //  THESE TESTS ARE DONE FOR YOU BY THE ReadArgs CLASS!
        //
        //        Read comments in ReadArgs.java code.
        //
        /////////////////////////////////////////////////////////

      dxInitialMove = ReadArgs.getArg( args, 0, dxDefault );
      dyInitialMove = ReadArgs.getArg( args, 1, dyDefault );
      
    
    }    
    //+++++++++++++++++++ application starter ++++++++++++++++++++
    //-------------------------- main ----------------------------
    /**
     * A DrJava convenience method to start the application.
     * @param args String[] command line arguments
     */
    public static void main( String[] args )
    {
        GUIApp.main( args );
    }  
}
