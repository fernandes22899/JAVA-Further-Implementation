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
 * 
 * @author rdb
 * 
 */
public class GUI extends JPanel //implements Animated
{
    //---------------- class variables ------------------------------
    static boolean showUpdates = true;  // true => show updates as recursing
    static boolean showSome = false;    // true => show 1 of a 1,000
    static int maxDelay = 500;
    
    
    //--------------- instance variables ----------------------------
    int             _pauseDelay = 250;  // delay after each move
    int[][]         _puzzle = new int[9][9];
    int[][]         _solution = new int[9][9];
    JButton[][]     _puzzleDisplay = new JButton[9][9];
    JPanel          _displayPanel = new JPanel();
    Color           _defaultButtonColor;
    int             _totalRecursions = 0;
    int             _totalBackups = 0;
    
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
        System.out.println(fileName );
        try {
            Scanner fileScanner = new Scanner(new File(fileName));
            for(int i=0; i < 9; i++){
                String line = fileScanner.nextLine();
                for(int j=0; j < 9; j++){
                    char c = line.charAt(j);
                    if(c == '-'){
                        _puzzle[i][j] = 0;
                        _puzzleDisplay[i][j].setText("");
                        if(_defaultButtonColor != null)
                        {
                            _puzzleDisplay[i][j].setBackground(_defaultButtonColor);
                        }
                    }
                    else {
                        _puzzle[i][j] = Integer.parseInt(c+"");
                        _puzzleDisplay[i][j].setText(c + "");
                        _puzzleDisplay[i][j].setBackground(Color.GREEN);
                    }     
                }
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println( "ERROR: " + e.getMessage() );
        }
        validate();  // In my solution a simple repaint() didn't work
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
        for(int i=0; i < 9; i++)
        {
            for(int j=0; j < 9; j++)
            {
                _solution[i][j] = _puzzle[i][j];
            }
        }
        _totalRecursions = 0;
        _totalBackups = 0;
        
        int startCellNum = 0;
        boolean solved = solve(startCellNum);
        validate();
        if(!solved)
        {
            System.out.println("No solution");
        }
        else
        {
            System.out.print(array2DtoString(_solution));
        }
        System.out.println(_totalRecursions + " " + _totalBackups);
    }
    
    //----------------------- solve (cellNum) ------------------------
    private boolean solve(int cellNum)
    {     
        _totalRecursions++;
        int row = cellNum / 9;
        int col = cellNum - (row*9);
        //System.out.println(row+","+col);
        if( (row >= 9) || (col >= 9) )
            return true;
        //updateDisplay( _displayPanel, 100);
        if(_solution[row][col] != 0)
        {
            return solve(cellNum+1);
        }
        else
        {
            //System.out.println(cellNum);
            //_puzzleDisplay[row][col].setBackground(Color.ORANGE);
            for(int num=1; num <= 9; num++)
            {
                if( (countNumberAtColumn(col, num) == 0) &&
                   (countNumberAtRow(row, num) == 0) && (countNumberAtSubBlock(row, col, num) == 0))
                {           
                    _solution[row][col] = num;
                    //Color c = _puzzleDisplay[row][col].getBackground();
                    //_puzzleDisplay[row][col].setBackground(Color.ORANGE);
                    _puzzleDisplay[row][col].setText(num+"");
                    _puzzleDisplay[row][col].setBackground(Color.ORANGE);
                    updateDisplay( _displayPanel, _pauseDelay);
                    validate();
                    if(solve(cellNum+1))
                        return true;
                    _solution[row][col] = 0;
                    _totalBackups++;
                    //_puzzleDisplay[row][col].setBackground(c);
                    _puzzleDisplay[row][col].setText("");
                    _puzzleDisplay[row][col].setBackground(_defaultButtonColor);
                    updateDisplay( _displayPanel, _pauseDelay);
                    validate();
                }                    
            }
            return false;
        }
        
    }
    
    //------------------------ countNumberAtRow ------------------------------
    /**
     * Get count of given numbers in the puzzle row
     * @param row
     * @param number
     * @return
     */
    private int countNumberAtRow(int row, int number)
    {
        int count = 0;
        for(int col=0; col < 9; col++)
        {
            if(_solution[row][col] == number)
            {
                count++;
            }
            
        }
        return count;
    }
    
    //------------------------ countNumberAtColumn ------------------------------
    /**
     * Get count of given numbers in the puzzle column
     * @param col
     * @param number
     * @return
     */
    private int countNumberAtColumn(int col, int number)
    {
        int count = 0;
        for(int row=0; row < 9; row++)
        {
            if(_solution[row][col] == number)
            {
                count++;
            }
            
        }
        return count;
    }
    
    //------------------------ countNumberAtSubSquare ------------------------------
    /**
     * Get count of given numbers in the puzzle sub square
     * @param row
     * @param col
     * @param number
     * @return
     */
    private int countNumberAtSubBlock(int row, int col, int number)
    {
        int count = 0;
        int squareStartRow = row / 3;
        int squareStartCol = (col / 3);
        squareStartCol *= 3;
        squareStartRow *= 3;
        
        for(int i=0; i < 3; i++)
        {
            for(int j=0; j < 3; j++)
            {
                if(_solution[squareStartRow + i][squareStartCol + j] == number)
                {
                    count++;
                }
            }             
        }
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
    private JPanel makeDisplayPanel()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(9, 9));
        for(int i=0; i < 9; i++)
        {
            for(int j=0; j < 9; j++)
            {
                _puzzleDisplay[i][j] = new JButton("");
                _puzzleDisplay[i][j].setOpaque(true);
                _puzzleDisplay[i][j].setBorder( new LineBorder(Color.BLACK, 1) );       
                panel.add(_puzzleDisplay[i][j]);
            }
        }
        _defaultButtonColor = _puzzleDisplay[0][0].getBackground();
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
        fileButton.addActionListener( 
                                     new ActionListener()
                                         {   // all we want to do is override actionPerformed
            public void actionPerformed( ActionEvent ev )
            {  
                openPuzzleFile();
            }
        } );
        panel.add( fileButton );
        
        // create a button to solve the puzzle
        JButton solveButton = new JButton( "Solve" );
        solveButton.addActionListener( 
                                      new ActionListener()
                                          {   // all we want to do is override actionPerformed
            public void actionPerformed( ActionEvent ev )
            {  
                solve();
            }
        } );
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
        showButton.addActionListener( 
                                     new ActionListener()
                                         {   // all we want to do is override actionPerformed
            public void actionPerformed( ActionEvent ev )
            {  
                showUpdates = !showUpdates;
            }
        } );
        showButton.setSelected( true );
        panel.add( showButton );
        
        // create a toggle to show updates
        JToggleButton someButton = new JToggleButton( "Show a few" );
        someButton.addActionListener( 
                                     new ActionListener()
                                         {   // all we want to do is override actionPerformed
            public void actionPerformed( ActionEvent ev )
            {  
                showSome = !showSome;
            }
        } );
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
        delaySlider.addChangeListener( 
                                      new ChangeListener()
                                          {
            public void stateChanged( ChangeEvent ev )
            {
                //System.out.println( "Slider" );
                JSlider slider = (JSlider) ev.getSource();
                _pauseDelay = slider.getValue();
            }
        } );
        
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
     * to a String with 1 blank at the beginning of each line, 1 blank 
     * between the elements on the line and a line feed at the end 
     * of each row of the array.
     * 
     * The idea is for you to modify this method method to accept an
     * array of whatever class you build your game board and to access
     * the r,c element with the appropriate "getter" method to get the
     * value of that element in order to print it.
     * 
     * @param array int[][]   2D array to be converted to a String rep
     * @return String         String with 1 line per row of array
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
     * @param argv String[]  command line arguments
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
