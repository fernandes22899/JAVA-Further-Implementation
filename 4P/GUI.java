//------------------------ GUI -------------------------------
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

/**
 * GUI for assignment 5P.
 * @author rdb
 */
public class GUI extends JPanel //implements Animated
{
    //---------------- class variables ------------------------------
    static boolean showUpdates = true;  // true => show updates as recursing
    static boolean showSome = false;    // true => show 1 of a 1,000
    static int maxDelay = 500;
    
    //--------------- instance variables ----------------------------
    int          _pauseDelay = 250;  // delay after each move
    int          _backupAmount = 0;
    int          _recursionAmount = 0;
    int          startNumbers;
    JPanel       _displayPanel = new JPanel();
    int[][]      _puzzle = new int[9][9];
    int[][]      _solution = new int[9][9];
    JButton[][]  _displaySudoku = new JButton[9][9];
    Integer      _integer;
    Boolean      test;
    
    //------------------ constructor ---------------------------------
    /**
     * The GUI constructor uses BorderLayout.
     * @param inFile String   initial puzzle file
     */
    public GUI( String inFile )     
    {
        this.setLayout( new BorderLayout() );
        buildGUI();
        makeDisplay( inFile );
    }
    
    //---------------------------- makeDisplay -------------------------
    /**
     * Make the puzzle display.
     * @param fileName String   the name of the puzzle file
     */
    private void makeDisplay( String fileName )
    { 
        ////////////////////////////////////////////////////////////
        // create the sudoku puzzle display and add it to this JPanel
        ////////////////////////////////////////////////////////////
        System.out.println( "Make puzzle from: " + fileName );
        
        try
        {
            Scanner fs = new Scanner( new File( fileName ) );
            
            for( int cols = 0; cols < 9; cols++ )
            {
                String l = fs.nextLine();
                for( int rows = 0; rows < 9; rows++ )
                {
                    char ch = l.charAt( rows );
                    if( ch == '-' )
                    {
                        _puzzle[cols][rows] = 0;
                        _displaySudoku[cols][rows].setText( "" );
                    }
                    else 
                    {
                        _puzzle[cols][rows] = _integer.parseInt( ch + "" );
                        _displaySudoku[cols][rows].setText( ch + "" );
                    }     
                }
            }
            fs.close();
        } 
        catch( Exception e )
        {
            System.out.println( "Error: " + e.getMessage() );
        }
        
        validate(); // In my solution a simple repaint() didn't work
        // here. validate() is a Container method that says
        // that the entire contents has changed and needs
        // to be recreated. This worked. You might need it.
    }
    
    //------------------ openPuzzleFile() --------------------
    /**
     * Open a puzzle input file using a JFileChooser to get the file name.
     */
    private void openPuzzleFile()
    {
        String prompt = "Choose a Sudoku board file";
        String fileName = Utilities.getFileName( prompt );
        if ( fileName != null && fileName.length() > 0 )
            makeDisplay( fileName );
    }
    
    //---------------------- solve ---------------------------------
    /**
     * Start the recursive solution of the puzzle. You will need
     * a helper function to do the solution recursively; feel free to
     * change this method name/parameters if you wish. It's here so the
     * Button handler has something to call. 
     */
    private void solve()
    {
        _recursionAmount = 0;
        _backupAmount = 0;
        startNumbers = 0;
        
        for( int cols = 0; cols < 9; cols++ )
            for( int rows = 0; rows < 9; rows++ )
            _solution[cols][rows] = _puzzle[cols][rows];
        
        test = solve( startNumbers );
        
        validate();
        
        if( !test )
            System.out.println( "No Answers :(" );
        else
            System.out.print( array2DtoString( _solution ) );
        
        System.out.println( "Recursion Amount: " + _recursionAmount );
        System.out.println( "Backups: " + _backupAmount );
    }
    
    //----------------------- solve( totalHelp ) ------------------------
    /**
     * helping solve method.
     * @param totalHelp int
     * @return boolean
     */
    private boolean solve( int totalHelp )
    {
        _recursionAmount++;
        int row = totalHelp / 9;
        int col = totalHelp - ( row * 9 );
        
        if( ( row >= 9 ) || ( col >= 9 ) )
            return true;
        
        if( _solution[row][col] != 0 )
            return solve( totalHelp + 1 );
        else
        {
            for( int i = 0; i < 9; i++ )
            {
                if( ( blockAmount( row, col, i ) == 0 ) &&
                    ( rowAmount( row, i ) == 0 ) 
                       && ( colAmount( col, i ) == 0 ) )
                {           
                    _solution[row][col] = i;
                    _displaySudoku[row][col].setText( i + "" );
                    _displaySudoku[row][col].setBackground( Color.GRAY );
                    updateDisplay( _displayPanel, _pauseDelay );
                    
                    validate();
                    
                    if( solve( totalHelp + 1 ) )
                        return true;
                    
                    _solution[row][col] = 0;
                    
                    _backupAmount++;
                    _displaySudoku[row][col].setText( "" );
                    
                    updateDisplay( _displayPanel, _pauseDelay );
                    validate();
                }                    
            }
            return false;
        }
    }
    
    //------------------------ colAmount ------------------------------
    /**
     * Column amount.
     * @param col int
     * @param number int
     * @return int
     */
    private int colAmount( int col, int number )
    {
        
        int count = 0;
        for( int row = 0; row < 9; row++ )
            if( _solution[row][col] == number )
                count++;
        
        return count;
    }
    
    //-------------------- rowAmount ------------------------
    /**
     * row amount.
     * @param row int
     * @param number int
     * @return int
     */
    private int rowAmount( int row, int number )
    {
        
        int count = 0;
        for( int col = 0; col < 9; col++ )
            if( _solution[row][col] == number )
                count++;
        
        return count;
    }
    
    //---------------------- blockAmount -------------------
    /**
     * Number amount.
     * @param row int
     * @param col int
     * @param number int
     * @return int
     */
    private int blockAmount( int row, int col, int number )
    {
        int count = 0;
        int rowStart = row / 3;
        int colStart = col / 3;
        colStart *= 3;
        rowStart *= 3;
        
        for( int cols = 0; cols < 3; cols++ )
            for( int rows = 0; rows < 3; rows++ )
                if( _solution[rowStart + cols][colStart + rows] == number )
                    count++;          
        
        return count;
    }
    
    //--------------------- buildGUI -------------------------------
    /**
     * generate the gui tools.
     *
     */
    private void buildGUI()
    {   
        JPanel northPanel = makeNorthPanel();
        add( northPanel, BorderLayout.NORTH );
        
        _displayPanel = makeDisplayPanel();
        add( _displayPanel, BorderLayout.CENTER );
    }
    
    //-------------------- displayPanel------------------------
    /**
     * Makes diplay panel.
     * @return JPanel
     */
    private JPanel makeDisplayPanel()
    {
        JPanel panel = new JPanel();
        panel.setLayout( new GridLayout( 9, 9 ) );
        for( int i = 0; i < 9; i++ )
        {
            for( int j = 0; j < 9; j++ )
            {
                _displaySudoku[i][j] = new JButton( "" );
                _displaySudoku[i][j].setOpaque( true );
                _displaySudoku[i][j]
                    .setBorder( new LineBorder( Color.BLACK, 1 ) );       
                panel.add( _displaySudoku[i][j] );
            }
        }
        return panel;
    }
    
    
    //------------------------- makeNorthPanel --------------------
    /**
     * Make a panel containing buttons and slider for the GUI.
     * 
     * @return JPanel  panel containing buttons and sliders
     */
    private JPanel makeNorthPanel()
    {
        JPanel panel = new JPanel();        
        panel.setBorder( new LineBorder( Color.BLACK, 2 ) );        
        addButtons( panel );
        addToggles( panel );
        addSlider( panel );
        
        return panel;
    }
    
    //--------------------- addButtons ---------------------------
    /**
     * Add the File and Solve buttons.
     * @param panel JPanel   Container to put buttons into.
     */
    private void addButtons( JPanel panel )
    {
        // create a button to open a new file
        JButton fileButton = new JButton( "File" );
        fileButton.addActionListener( new ActionListener()
        {   
            // all we want to do is override actionPerformed
            public void actionPerformed( ActionEvent ev )
            {  
                openPuzzleFile();
            }
        } 
        );
        panel.add( fileButton );
        
        // create a button to solve the puzzle
        JButton solveButton = new JButton( "Solve" );
        solveButton.addActionListener( new ActionListener()
        {   
            // all we want to do is override actionPerformed
            public void actionPerformed( ActionEvent ev )
            {  
                solve();
            }
        } 
        );
        panel.add( solveButton );       
    }
    
    //--------------------- addToggles ---------------------------
    /**
     * Add the Show and ShowAFew toggle buttons.
     * @param panel JPanel   Container to put buttons into.
     */
    private void addToggles( JPanel panel )
    {
        // create a toggle to show updates
        JToggleButton showButton = new JToggleButton( "Show" );
        showButton.addActionListener( new ActionListener()
        {   
            // all we want to do is override actionPerformed
            public void actionPerformed( ActionEvent ev )
            {  
                showUpdates = !showUpdates;
            }
        } 
        );
        showButton.setSelected( true );
        panel.add( showButton );
        
        // create a toggle to show updates
        JToggleButton someButton = new JToggleButton( "Show a few" );
        someButton.addActionListener( new ActionListener()
        {   
            // all we want to do is override actionPerformed
            public void actionPerformed( ActionEvent ev )
            {  
                showSome = !showSome;
            }
        } 
        );
        panel.add( someButton );
    }
    
    //--------------------- addSlider ---------------------------
    /**
     * Add the delay time slider.
     * @param panel JPanel   Container to put buttons into.
     */
    private void addSlider( JPanel panel )
    {
        //------------- Delay time Slider  -----------------------------
        LabeledSlider delaySlider = 
            new LabeledSlider( "Delay", 0, maxDelay, maxDelay / 2 );
        delaySlider.addChangeListener( new ChangeListener()
        {
            public void stateChanged( ChangeEvent ev )
            {
                //System.out.println( "Slider" );
                JSlider slider = ( JSlider ) ev.getSource();
                _pauseDelay = slider.getValue();
            }
        } 
        );
        panel.add( delaySlider );
    } 
    
    //----------------------- updateDisplay ---------------------------
    private static int hideCount = 1000;
    private static int skipCount = 0;
    /**
     * This method invokes a special Swing method that immediately does
     * a repaint of the specified region of the specified component.
     * It's just what we need to update the display during recursion
     * We'll always do the entire GUI component. This will also sleep
     * for the specified msecs.
     * 
     * @param comp JComponent  the panel to update
     * @param msec int         the delay time
     */
    private void updateDisplay( JComponent comp, int msec )
    {
        // First check if we want to show the updates or just some
        //    some of them. 
        if ( !showUpdates )
            return;
        if ( showSome )
        {
            if ( ++skipCount < hideCount )
                return;
            skipCount = 0;
        }
        
        comp.paintImmediately( comp.getBounds() );
        try 
        { 
            Thread.sleep( msec ); 
        } 
        catch ( InterruptedException ie ) 
        {
            System.err.println( "updateDisplay InterruptedException: " +
                               ie.getMessage() );
        }
    }
    //--------------------- array2DtoString ------------------------
    /**
     * This method is a framework for converting a 2D array of anything
     * to a String with 1 blank at the beginning of each l, 1 blank 
     * between the elements on the l and a l feed at the end 
     * of each row of the array.
     * 
     * The idea is for you to modify this method method to accept an
     * array of whatever class you build your game board and to access
     * the r,c element with the appropriate "getter" method to get the
     * value of that element in order to print it.
     * 
     * @param array int[][]   2D array to be converted to a String rep
     * @return String         String with 1 l per row of array
     */
    public static String array2DtoString( int[][] array )
    {
        StringBuffer buf = new StringBuffer();
        for ( int r = 0; r < array[ 0 ].length; r++ )
        {
            for ( int c = 0; c < array[ 0 ].length; c++ )
            {
                buf.append( " " + array[ r ][ c ] );
            }
            buf.append( "\n" );
        }
        return buf.toString();
    }    
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //------------------------------- main ----------------------------
    /** 
     * Convenience main for starting program.
     * @param argv String[]  command l arguments
     * @throws IOException  on missing file
     */
    public static void main( String [] argv ) throws IOException
    {
        if ( argv.length == 0 )
            new Sudoku( "Sudoku from GUI", "test0-nobackup.txt" );
        else
            new Sudoku( "Sudoku from GUI", argv[ 0 ] );
    }
}
