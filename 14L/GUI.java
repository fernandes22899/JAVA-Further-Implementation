//------------------------------ GUI.java ---------------------------
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
/** 
 * GUI.java:
 * A JPanel to control the application's interface to the application.
 *
 * @author rdb
 * CS416 Spring 2008
 * Last edit: 
 * 03/16/14 rdb: made checkstyle-compatible
 */

public class GUI extends JPanel 
{
    //---------------- instance variables ---------------------------
    private DrawPanel _display;
    private TreeApp      _app;
    private ControlPanel _controls;
    
    //------------------- constructor -------------------------------
    /**
     * Container parent of the control panel is passed as an argument
     * along with the application object.
     */
    public GUI() 
    {
        super ( new BorderLayout() );
        
        _display = new DrawPanel();
        _app     = new TreeApp( _display );
        
        JScrollPane sPane = new JScrollPane( _display,
                  ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                   ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS );
        //sPane.getVerticalScrollBar().setUnitIncrement(10);
        //sPane.getHorizontalScrollBar().setUnitIncrement(10);
        
        sPane.setPreferredSize( new Dimension( 500, 400 ) );     
        
        this.add( sPane, BorderLayout.CENTER );
        
        //create ControlPanel in the South
        _controls = new ControlPanel( _app );
        this.add( _controls, BorderLayout.SOUTH );
        
        //create Buttons in the North
        Component buttonMenu = makeButtonMenu();
        this.add( buttonMenu, BorderLayout.NORTH );
    }
    //------------------- makeButtonMenu ------------------------------
    /**
     * Make a menu of buttons.
     * @return JPanel
     */
    private JPanel makeButtonMenu()
    {
        String[] labels = { "Data", "Print",  "Max Find", 
            "InOrder", "Height", "Find" };
        
        JPanel bMenu = new JPanel( new GridLayout( 1, 0 ) ); 
        JButton button;
        for ( int i = 0; i < labels.length; i++ )
        {
            button = new JButton( labels[ i ] );
            //button.setFont( getFont().deriveFont( 11.0f ) );
            bMenu.add( button );
            button.addActionListener( new ButtonListener( i ) );
        }      
        return bMenu;
    }
    //+++++++++++++++++ ButtonListener inner class ++++++++++++++++++++++++
    /**
     * ButtonListener handles all button events and passes them along
     * to methods of the ListPanel class.
     */
    public class ButtonListener implements ActionListener
    {
        int _buttonId;
        /** 
         * Constructor takes and id for the button.
         * @param buttonId int    just a code
         */
        public ButtonListener( int buttonId )
        {
            _buttonId = buttonId;
        }
        /**
         * listener method.
         * @param ev ActionEvent
         */
        public void actionPerformed( ActionEvent ev )
        {
            switch ( _buttonId )
            {
                case 0:
                    _app.makeData();
                    break;
                case 1:
                    _app.showTree();
                    break;
                case 2:
                    _app.maxFind();
                    break;
                case 3:
                    _app.inOrderString();
                    break;
                case 4:
                    _app.height();
                    break;
                case 5:
                    _app.find();
                    break;
            }
        }
    }         
}
