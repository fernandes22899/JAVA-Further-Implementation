//++++++++++++++++++++++++++++++ GUI  +++++++++++++++++++++++++++
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

/**
 * GUI.java -- Handles user interaction except card picking.
 *      Creates the panel for showing cards in a card game and associated
 *      buttons, spinners, and check boxes (if any).
 *
 * @author rdb
 *          Revised by jb, rdb
 *          
 * Last edit 03/27/15 Made checkstyle-compatible
 *                    Added logPath, saveTree,and nodeStats buttons.
 */
public class GUI extends JPanel 
{
    //------------------------- instance variables ------------------------
    private Game    _game;       // the game object
    private int     _seed = 0;         // seed for random num generator
    private JLabel  _gameNumberLabel;  // reports game # for current seed.
    private int     _gameNumForSeed = -1; // -1 since 1st doesn't use seed.
    private JCheckBox _userMode = null;
    
    //------------------------- constructor -------------------------------
    /**
     * GUI constructor creates the game and waits for interaction.
     */
    public GUI() 
    {
        super();                 
        setLayout( new BorderLayout() );
        
        JPanel gamePanel = new JPanel();
        gamePanel.setLayout( null );
        gamePanel.setBackground( Color.WHITE );
        
        _game = new Game();
        _game.setLayout( null );
        _game.setBackground( Color.WHITE );
        this.add( _game );
        
        setSize( 830, 400 );
        
        ///////////////////////////////////////////////////////
        // build the gui
        ///////////////////////////////////////////////////////
        String[] buttonLabels = { "Replay", "New Game", "Build Tree", 
            "Move", "AutoMove", "Undo", "LogPath", "NodeStats", "SaveTree" };
        Component buttonMenu = makeButtonMenu( buttonLabels );
        this.add( buttonMenu, BorderLayout.NORTH );
        
        Component controlMenu = makeControlMenu();
        this.add( controlMenu, BorderLayout.SOUTH );
        
        _game.replay();
    }
    //------------------- makeControlMenu ---------------------------------
    /**
     * Create a control menu that includes only a Spinner for the seed.
     * @return Component
     */
    private Component makeControlMenu( )
    {
        JPanel sMenu = new JPanel(); 
        LabeledSpinner seedSpinner = new LabeledSpinner( "Seed", 0, 30, _seed );
        
        //listener class for the spinner
        ChangeListener  cl = new ChangeListener()
        {  
            public void stateChanged( ChangeEvent ev )
            {  
                //System.out.println( "spinner changed" );
                JSpinner spinner = (JSpinner) ev.getSource();
                _seed = ( (Number) spinner.getValue() ).intValue(); 
                
                _gameNumForSeed = 0;
                _gameNumberLabel.setText( "Game: " + _gameNumForSeed );
                Game.newSeed( _seed );
            }
        };
        seedSpinner.addChangeListener( cl );
        sMenu.add( seedSpinner );
        //--- Add AutoPlay pause spinner ----
        LabeledSpinner pauseSpinner 
            = new LabeledSpinner( "Pause", 0, 8, Game.pause );
                
        //--- JLabel for reporting game number
        _gameNumberLabel = new JLabel( "Game: ?" );
        sMenu.add( _gameNumberLabel );
        
        //listener class for the spinner
        ChangeListener  pauseListen = new ChangeListener()
        {  
            public void stateChanged( ChangeEvent ev )
            {  
                //System.out.println( "spinner changed" );
                JSpinner spinner = (JSpinner) ev.getSource();
                Number value = (Number) spinner.getValue();
                Game.pause = value.intValue();
            }
        };
        pauseSpinner.addChangeListener( pauseListen );
        sMenu.add( pauseSpinner );
        
        //------ JCheckBox for user/tree play
        _userMode = new JCheckBox( "User", Game.userMode );
        _userMode.addItemListener( new ItemListener()
        {
            public void itemStateChanged( ItemEvent ev )
            {
                Game.userMode = ev.getStateChange() == 1;
                if( Game.userMode )
                    _game.replay();
                else
                {
                    _game.buildTree();
                    System.out.println( "node count: " + Game.nodeCount 
                                      + " tree depth: " + Game.treeDepth );
                }
            }  
        } );
        
        sMenu.add( _userMode );
        return sMenu;
    }
    //------------------- makeButtonMenu ----------------------------------
    /**
     * Create the button menu for this application.
     * @param labels String[]
     * @return Component
     */
    private Component makeButtonMenu( String[] labels )
    {
        JPanel bMenu = new JPanel( new GridLayout( 1, 0 ) ); 
        JButton button;
        for ( int i = 0; i < labels.length; i++ )
        {
            button = new JButton( labels[ i ] );
            bMenu.add( button );
            button.addActionListener( new ButtonListener( i ) );
        }
        bMenu.setSize( 40, 400 );
        
        return bMenu;
    }
    //+++++++++++++++++ ButtonListener inner class +++++++++++++++++++++++++
    /**
     * ButtonListener -- distributes button events to appropriate methods
     *                   of the GUI class.
     */
    public class ButtonListener implements ActionListener
    {
        int _buttonId;
        /** Constructor takes id. @param buttonId int */
        public ButtonListener( int buttonId )
        {
            _buttonId = buttonId;
        }
        /** Event handler. @param ev ActionEvent */
        public void actionPerformed( ActionEvent ev )
        {
            String msg = null;
            switch ( _buttonId )
            {
                case 0:   // Replay
                    _game.replay();
                    break;
                case 1:   // NewGame
                    _gameNumForSeed++;
                    _gameNumberLabel.setText( "Game: " + _gameNumForSeed );
                    _userMode.setSelected( true );
                    Game.userMode = true;
                    _game.newGame();
                    break;
                case 2:   // buildTree
                    _game.buildTree();
                    _userMode.setSelected( false );
                    System.out.println( "node count: " + Game.nodeCount 
                                      + " tree depth: " + Game.treeDepth );
                    break;
                case 3:   // Move
                    msg = _game.oneMove();
                    if ( msg != null )
                        endGame( msg );
                    break;
                case 4:   // autoMove
                    msg = _game.autoMove();
                    if ( msg != null )
                        JOptionPane.showMessageDialog( null, msg );
                    break;
                case 5:  // Undo
                    msg = _game.undo();
                    if ( msg != null )
                        JOptionPane.showMessageDialog( null, msg );
                    break;
                case 6:  // LogPath
                    msg = _game.logPath( "path: " );
                    if ( msg != null )
                        JOptionPane.showMessageDialog( null, msg );
                    break;
                case 7:   // NodeStats
                    msg = _game.nodeStats( "Stats: " );
                    if ( msg != null )
                        JOptionPane.showMessageDialog( null, msg );
                    break;
                case 8:   // SaveTree
                    // Add popup to get a file name?
                    String fileName = "s" + _seed + "g" + _gameNumForSeed
                               + "-tree.txt";
                    msg = _game.saveTree( fileName );
                    if ( msg != null )
                        JOptionPane.showMessageDialog( null, msg );
                    break;
            }               
        }
    }
    //------------------- endGame( String ) -------------------------------
    /**
     * If a play can't be performed, this is called showing why a play
     *    can't be performed and asks for a new deck.
     * @param msg String
     */
    private void endGame( String msg )
    {
        msg = msg + "\nWant to play again?";
        int choice = JOptionPane.showConfirmDialog( null, msg );
        if ( choice == 0 )
            _game.replay();
        else if ( choice == 1 )
            System.exit( 0 );
    }
    
    //------------------ main ---------------------------------------------
    /**
     * Convenience application start from DrJava.
     * @param args String[]
     */
    public static void main( String [ ] args ) 
    {
        AcesUp.main( args );
    }
}   