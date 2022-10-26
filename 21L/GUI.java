/** 
 * GUI.java:
 * A JPanel to control the application's interface to the application
 *
 * @author rdb
 * CS416 Spring 2008
 */

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;

public class GUI extends JPanel 
{
    //---------------- class variables ---------------------------
    // These have package access and are referenced by RegexApp
    static DrawPanel      display;
    static GUI            gui;    // act like a Singleton
    
    //---------------- instance variables ---------------------------
    private Container    _parent;
    private RegexApp     _app;
    private JLabel       _regexLabel;
    
    //------------------- constructor -------------------------------
    /**
     * Container parent of the control panel is passed as an argument
     * along with the application object.
     */
    public GUI( Container parent ) 
    {
        super ( new BorderLayout() );
        gui      = this;     // This is a singleton, even though we don't
        // don't follow the Singleton pattern.
        _parent  = parent;  
        
        display = new DrawPanel();
        
        JScrollPane sPane = new JScrollPane( display,
                                            ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                                            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS
                                           );
        
        this.add( sPane, BorderLayout.CENTER );
        _app     = new RegexApp();
        
        //Display the regular expression and checkboxes in the South
        JPanel south = new JPanel();
        south.add( makeRegexTextArea() );
        south.add( makeRegexLabel() );
        south.add( makeCheckBoxes() );
        this.add( south, BorderLayout.SOUTH );
        
        //create Buttons in the North
        Component buttonMenu = makeButtonMenu();
        this.add( buttonMenu, BorderLayout.NORTH );
        
        int width = display.getPreferredSize().width;
        
        sPane.setPreferredSize( new Dimension( width, 400 ));           
    }
    //-------------- makeRegexTextArea() --------------------------
    private Component makeRegexTextArea()
    {
        JPanel labelPanel = new JPanel();
        
        labelPanel.add( new JLabel( "New regex: " ));
        JTextField regexText = new JTextField( "" );
        regexText.setFont( getFont().deriveFont( 12.0f )); // make the font big
        regexText.setColumns( 20 );
        
        regexText.setBorder( new LineBorder( Color.BLACK ));
        regexText.addActionListener( new ActionListener()
        { public void actionPerformed( ActionEvent ev ) 
            {
                JTextField field = (JTextField) ev.getSource();
                _app.setRegex( field.getText() );
             }
        } );
        
        
        labelPanel.add( regexText );
        
        return labelPanel;
    }
    //-------------- makeRegexLabel() --------------------------
    private Component makeRegexLabel()
    {
        JPanel labelPanel = new JPanel();
        
        labelPanel.add( new JLabel( "Regex: " ));
        _regexLabel = new JLabel( "      " );
        _regexLabel.setFont( getFont().deriveFont( 12.0f )); // make the font big
        
        _regexLabel.setBorder( new LineBorder( Color.BLACK ));
        labelPanel.add( _regexLabel );
        
        return labelPanel;
    }
    //--------------------- setRegexLabel( String ) ----------------------
    /**
     * update the label field showing the current regular expression
     */
    public static void setRegexLabel( String newText )
    {
        gui._regexLabel.setText( "  " + newText + "  " );
    }
    //------------------- makeButtonMenu --------------------------------
    private Component makeButtonMenu()
    {
        // JPanel defaults to FlowLayout
        String[] labels = { "Type In", "File", "Split", "FindAll",
            "Find", "Groups", "Reset" };
        
        JPanel bMenu = new JPanel( new GridLayout( 1, 0 )); 
        JButton button;
        for ( int i = 0; i < labels.length; i++ )
        {
            button = new JButton( labels[ i ] );
            //button.setFont( getFont().deriveFont( 11.0f ));
            bMenu.add( button );
            button.addActionListener( new ButtonListener( i ));
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
        public ButtonListener( int buttonId )
        {
            _buttonId = buttonId;
        }
        public void actionPerformed( ActionEvent ev )
        {
            switch ( _buttonId )
            {
                case 0:
                    _app.newInputString();
                    break;
                case 1:
                    _app.newInputFile();
                    break;
                case 2:
                    _app.split();
                    break;
                case 3:
                    _app.findAll();
                    break;
                case 4:
                    _app.find();
                    break;
                case 5:
                    _app.groups();
                    break;
                case 6:
                    _app.reset();
                    break;
            }               
        }
    }         
    //-------------- makeCheckBoxes() --------------------------
    private Component makeCheckBoxes()
    {
        JPanel checkPanel = new JPanel();
        //------ JCheckBox for Dot all
        JCheckBox dotCheck = new JCheckBox( "DOTALL", _app.dotFlag );
        dotCheck.addItemListener( new ItemListener()
        {
            public void itemStateChanged( ItemEvent ev )
            {
                _app.dotFlag = ev.getStateChange() == 1;
                _app.setFlags();
            }  
        } 
        );
        
        checkPanel.add( dotCheck );
        
        //------ JCheckBox for MultiLine
        JCheckBox multiLineCheck = new JCheckBox( "MULTILINE", _app.multiLineFlag );
        multiLineCheck.addItemListener( new ItemListener()
        {
            public void itemStateChanged( ItemEvent ev )
            {
                _app.multiLineFlag = ev.getStateChange() == 1;
                _app.setFlags();
            }  
        } 
        );
        checkPanel.add( multiLineCheck );
        
        //------ JCheckBox for MultiLine
        JCheckBox caseCheck = new JCheckBox( "CASE_INSENSITIVE", 
                                           _app.caseInsensitiveFlag );
        caseCheck.addItemListener( new ItemListener()
        {
            public void itemStateChanged( ItemEvent ev )
            {
                _app.caseInsensitiveFlag = ev.getStateChange() == 1;
                _app.setFlags();
            }  
        } 
        );
        checkPanel.add( caseCheck );

        //------ JCheckBox for Comments
        JCheckBox commentsCheck = new JCheckBox( "COMMENTS", _app.commentsFlag );
        commentsCheck.addItemListener( new ItemListener()
        {
            public void itemStateChanged( ItemEvent ev )
            {
                _app.commentsFlag = ev.getStateChange() == 1;
                _app.setFlags();
            }  
        } 
        );
        checkPanel.add( commentsCheck );
        
        return checkPanel;
    }
}
