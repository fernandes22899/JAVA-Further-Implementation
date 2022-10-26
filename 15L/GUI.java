//-------------------------------- GUI.java ---------------------------
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
/**
 * GUI - Data structure manipulation 
 * 
 * This class allows a user to add and remove objects to a data collection
 * organized as a Stack, Queue, alphabetically sorted list, or binary
 * search tree.
 * 
 * The state of the collection is shown in a simple graphical form.
 * 
 * This is not a complete implementation. It needs the following
 * methods completed:
 *     removeItem
 *     removeItemByKey
 * and the links in the graphical display should be replaced with something 
 * that shows which end of the link as some kind of arrow head.
 * 
 * In addition the QueueList and StackList and SortedList classes are
 * not complete.
 * 
 * @author Matt Plumlee/Dan Bergeron
 * 
 * Last edited: 
 * 03/17/14  rdb: made checkstyle-compatible
 * 03/11/15  rdb: hid GUI components not needed for the bstTraversal lab.
 * 
 */

public class GUI extends JPanel
{
    //---------------------- instance variables ----------------------
    private StringDictionary _stringDict = null;   
    private DrawPanel     _displayPanel;
    private JLabel           _feedback;
    
    private boolean   _print = true; // if set, print after each command
    //--------------------------- constructor -----------------------
    /**
     * Constructor needs no args.
     */
    public GUI()     
    {   
        this.setLayout( new BorderLayout() );
        
        buildGUI();
        _stringDict = new StringDictionary();
        _displayPanel.setTree( _stringDict.getTree() );
        
        JScrollPane sPane = new JScrollPane( _displayPanel,
                      ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                      ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS );
        //sPane.getVerticalScrollBar().setUnitIncrement( 10 );
        //sPane.getHorizontalScrollBar().setUnitIncrement( 10 );
        
        sPane.setPreferredSize( new Dimension( 500, 400 ) );     
        
        this.add( sPane, BorderLayout.CENTER );
        _displayPanel.update();
    }
    //---------------------------- buildGUI ---------------------------
    /**
     * Build the gui widgets.
     */
    private void buildGUI()     
    {
        JPanel northPanel = new JPanel();
        String[] buttonLabels = { "Print",  "Add", "Clear", "Find",  
            "First", "Next" };
        JPanel buttonMenu = makeButtonMenu( buttonLabels );
        
        JPanel radioPanel = new JPanel( new GridLayout( 1, 3 ) );
        /********** Removed 3/11/15
        String[] dataTypeLabels = { "*String", "DataIntDict", "DataStrDict",
            "Dict<DataStr>", "Dict<DataInt>" };
        JPanel dataTypeMenu = makeRadioButtonMenu( dataTypeLabels, 
                                                  new DataTypeListener() );
        radioPanel.add( dataTypeMenu );
        ***********************************/
        String[] printOptions = { "*Print On", "Print Off" };
        JPanel printMenu = makeRadioButtonMenu( printOptions, 
                                               new PrintOptionListener() );
        radioPanel.add( printMenu );
        
        _feedback = new JLabel( " " );
        _feedback.setMinimumSize( new Dimension( 150, 50 ) );
        _feedback.setMaximumSize( new Dimension( 150, 50 ) );
        _feedback.setPreferredSize( new Dimension( 150, 50 ) );
        
        _feedback.setBorder( new LineBorder( Color.BLACK, 1 ) );
        radioPanel.add( _feedback );
        
        northPanel.add( buttonMenu );
        northPanel.add( radioPanel );
        this.add( northPanel, BorderLayout.NORTH );
        
        _displayPanel = new DrawPanel();
        this.add( _displayPanel, BorderLayout.CENTER );
    }
    
    //------------------- makeButtonMenu -------------------------------
    /**
     * Put the buttons in a panel.
     * 
     * @param labels String[]         label array.
     * @return JPanel
     */
    private JPanel makeButtonMenu( String[] labels )
    {
        // JPanel defaults to FlowLayout
        
        JPanel bMenu = new JPanel( new GridLayout( 3, 1 ) ); 
        JButton button;
        for ( int i = 0; i < labels.length; i++ )
        {
            button = new JButton( labels[ i ] );
            bMenu.add( button );
            button.setSize( new Dimension( 50, 20 ) );
            button.addActionListener( new ButtonListener( i ) );
        }      
        return bMenu;
    }
    //+++++++++++++++++ ButtonListener inner class +++++++++++++++++++++
    /**
     * ButtonListener handles all button events and passes them along
     * to methods of the GUI class.
     */
    public class ButtonListener implements ActionListener
    {
        int _buttonId;
        /**
         * Constructor takes an id.
         * @param buttonId int
         */
        public ButtonListener( int buttonId )
        {
            _buttonId = buttonId;
        }
        /**
         * Listener method.
         * @param ev  ActionEvent
         */
        public void actionPerformed( ActionEvent ev )
        {
            switch ( _buttonId )
            {
                case 0:   // Print
                    printDict( "StringDictionary" );
                    break;
                case 1:   // Add
                    addItems();
                    break;
                case 2:   // Clear
                    _stringDict.clear();
                    if  ( _print )
                        printDict( "After clear()" );
                    _displayPanel.setTree( _stringDict.getTree() ); 
                    break;
                case 3:   // Find
                    findItem();
                    break;
                case 4:    // first
                    showData( "First", _stringDict.first() );
                    break;
                case 5:    // next
                    showData( "Next", _stringDict.next() );
                    break;
            } 
        }
    }         
    //------------------- makeRadioButtonMenu --------------------------
    /**
     * Make a radio button panel.
     * 
     * @param labels String[]   the labels
     * @param listen ActionListener   listener object for all buttons
     * @return JPanel
     */
    private JPanel makeRadioButtonMenu( String[] labels, 
                                       ActionListener listen )
    {
        JPanel bMenu = new JPanel(); 
        bMenu.setLayout( new GridLayout( 2, 3 ) );
        ButtonGroup group = new ButtonGroup();
        bMenu.setBorder( new LineBorder( Color.BLACK, 2 ) );
        JRadioButton button;
        for ( int i = 0; i < labels.length; i++ )
        {
            if ( labels[i].charAt( 0 ) == '*' ) // this button selected
            {  
                button = new JRadioButton( labels[i].substring( 1 ) ); 
                button.setSelected( true );
            }
            else
                button = new JRadioButton( labels[ i ] );
            bMenu.add( button );
            group.add( button );
            button.addActionListener( listen );
        }      
        return bMenu;
    }
    //+++++++++++++++++ DataTypeListener inner class ++++++++++++++++++
    /**
     * RadioListener handles all button events and passes them along
     * to methods of the AnagramGUI class.
     */
    public class DataTypeListener implements ActionListener
    {
        /**
         * Listener method.
         * @param ev  ActionEvent
         */
        public void actionPerformed( ActionEvent ev )
        {
            String which = ( (JRadioButton) ev.getSource() ).getText();
            _stringDict = null;
            
            if ( which.equals( "String" ) )
                _stringDict = new StringDictionary();
            else
                System.err.println( "Unimplemented dictionary: " + which );
            System.out.println( "-------- New Empty Dictionary ---------" );
        }
    }         
    //+++++++++++++++++ PrintOptionListener inner class +++++++++++++++
    /**
     * RadioListener handles all button events and passes them along
     * to methods of the AnagramGUI class.
     */
    public class PrintOptionListener implements ActionListener
    {
         /**
         * Listener method.
         * @param ev  ActionEvent
         */
        public void actionPerformed( ActionEvent ev )
        {
            JRadioButton which = (JRadioButton) ev.getSource();
            if ( which.getText().equals( "Print On" ) )
                _print = true;
            else
                _print = false;
        }
    }         
    
    //---------------------- addItems -----------------------------
    /**
     * Execute dialog to add items to Dictionary.
     */
    public void addItems( )
    {  
        String inMessage = "Enter words to add to Dictionary";
        String input = JOptionPane.showInputDialog( null,  inMessage ); 
        String rest = "empty";
        
        if ( input != null && input.trim().length() > 0 )
        {
            String[] keys = input.split( " " );
            for ( int i = 0; i < keys.length; i++ )
            {
                String key = keys[ i ].trim();
                if ( key.length() > 0 )
                {
                    if ( _stringDict != null )
                        _stringDict.add( new String( key ) );
                    else
                        System.err.println( "addItem: No dictionary" );
                }
            }
        }
        if  ( _print )
            printDict( "After add()" );
        if ( _stringDict != null )
            _displayPanel.setTree( _stringDict.getTree() );
    }
    //---------------------- findItem -----------------------------
    /**
     * Execute dialog to find an item and show if successful.
     */
    public void findItem( )
    {  
        String inMessage = "Enter keys to find";
        String input = JOptionPane.showInputDialog( null,  inMessage ); 
        String key;
        String msg;
        
        while ( input != null && input.trim().length() > 0 )
        {
            String[] keys = input.split( " " );
            String result = null;
            msg = new String();
            for ( int k = 0; k < keys.length; k++ )
            {
                result = _stringDict.find( keys[ k ] );
                msg += keys[ k ];
                if ( result == null )
                    msg += ": NOT found.\n";
                else 
                    msg += ": found. \n";
            }
            msg += "Enter more words?";
            input = JOptionPane.showInputDialog( null,  msg );
        }
    }
    
    //+++++++++++++++++++++++++++++ Utility methods +++++++++++++++++++
    
    //---------------- showData( String, String  ) --------------
    /**
     * Show information in a JLabel.
     * @param label String         label for data
     * @param data  String         the data
     */
    private void showData( String label, String data )
    {
        String msg;
        
        if ( data == null )
            msg = label + ": Not found";
        else
            msg = label + ": " + data;
        //JOptionPane.showMessageDialog( null,  msg );
        _feedback.setText( msg );
        repaint();
    }
    
    //----------------------- printDict -----------------------------
    /**
     * Print entire dictionary.
     * @param title String
     */
    private void printDict( String title )
    {
        
        System.out.println( "----------------" + title + " ------------" );
        if ( _stringDict != null )
            System.out.print( _stringDict );
        else
            System.out.print( "No active Dictionary" );
        
        System.out.println( "\n---------------------------------------" );
    }
    //-----------------------------------------------------------------
    /**
     * Convenience invocation of the app.
     * @param args String[]    passed on.
     */
    public static void main( String[] args )
    {
        new DictionaryApp( "Dictionary", args );
    }
}
