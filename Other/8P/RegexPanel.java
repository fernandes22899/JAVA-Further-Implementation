import javax.swing.JPanel;
import javax.swing.border.*;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.util.*;

/** 
 * RegexPanel.java: Main components are 2 JEditorPane windows, one to
 * show the input to the reg. ex. tool and one to show the output.
 * There are also labels for each. The panel uses a GridBagLayout --
 * one of the more complex ones.
 * 
 * @author rdb
 * CS416 Spring 2008
 */

public class RegexPanel extends JPanel 
{
    //------------------- instance variables ------------------------
    private JEditorPane _input;          // pane showing the test input data 
    private JLabel      _inputTitle;     // title for the input data
    private JEditorPane _match;          // pane showing match results
    private JLabel      _matchesTitle;   // title for match pane
    private boolean     _showColor = false;
    
    //------------- magic constants
    private int       _defaultW   = 1000;
    private int       _defaultH   = 900;
    private int       _colWidth   = 500;     
    
    //--------------------- constructor --------------------------
    /**
     * Default constructor.
     */
    public RegexPanel ( ) 
    {
        super( );
        GridBagLayout gridbag = new GridBagLayout( );
        setLayout( gridbag );
        
        GridBagConstraints gbc = new GridBagConstraints( );
        gbc.fill = GridBagConstraints.BOTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 0; gbc.gridy = 0;
        _inputTitle = makeTitle( "Input data", _colWidth );
        gridbag.setConstraints( _inputTitle, gbc );
        
        gbc.gridx = 1; gbc.gridy = 0;
        _matchesTitle = makeTitle( "Matches", _colWidth );
        gridbag.setConstraints( _matchesTitle, gbc );
        
        gbc.gridx = 0; gbc.gridy = 1;
        _input = makeEditorPane( _colWidth, "text/plain" );
        gridbag.setConstraints( _input, gbc );
        
        gbc.gridx = 1; gbc.gridy = 1;
        _match = makeEditorPane( _colWidth, "text/plain" );
        gridbag.setConstraints( _match, gbc );
    }
    //-------------------- makeTitle( String, int, int ) ---------
    /**
     * Make a JLabel to serve as EditorPane title.
     * 
     * @param text  Title text. 
     * @param width  Desired width for title.
     *
     * @return new JLabel.
     */
    private JLabel makeTitle( String text, int width )
    {
        Border border = new BevelBorder( BevelBorder.LOWERED, 
                                        Color.GRAY, Color.BLACK );
        JLabel title = new JLabel( text );
        
        title.setPreferredSize( new Dimension( width, 40 ) );
        title.setBorder( border );
        title.setHorizontalAlignment( SwingConstants.CENTER );
        add( title );
        return title;
    }
    //---------------------- showColor( boolean ) ---------------
    /**
     * Access the next regex in the pattern array; 
     * re-cycles when gets to end.
     *
     * @param show  Whether or not color should be shown.
     */
    public void showColor( boolean show )
    { 
        _showColor = show;
        if ( show )
            _match.setContentType( "text/html" );
        else
            _match.setContentType( "text/plain" );
    }
    //-------------------- setInputTitle( String ) --------------
    /**
     * Change the label on the input window.
     *
     * @param newTitle  New title text.
     */
    public void setInputTitle( String newTitle )
    {
        _inputTitle.setText( newTitle );
    }
    //--------------- makeEditorPane( String, int, int ) ---------
    /**
     * Make a JLabel to serve as EditorPane title.
     * 
     * @param width  Desired width for editor pane.
     * @param textStyle  Style for text.
     *
     * @return new JEditorPane
     */
    private JEditorPane makeEditorPane( int width, String textStyle )
    {
        Border border = new BevelBorder( BevelBorder.LOWERED, 
                                        Color.GRAY, Color.BLACK );
        JEditorPane p = new JEditorPane( textStyle, "  " );
        
        Font c = new Font( "Courier", Font.PLAIN, 14 );
        p.setFont( c );
        p.setBorder( border );
        p.setPreferredSize( new Dimension( width, 700 ) );
        add( p );
        return p;
    }
    
    //------------------------ setInput( String ) ----------------
    /**
     * Update the input text area.
     *
     * @param text  Text for input pane.
     */
    public void setInput( String text )
    {
        _input.setText( text );
        Font c = new Font( "Courier", Font.PLAIN, 14 );
        _match.setText( " " );
        //System.out.println( _match.getFont() );
    }
    //------------------------ setMatch( String ) ----------------
    /**
     * Update the match text area.
     *
     * @param text  Text for match pane.
     */
    public void setMatch( String text )
    {
        _match.setText( text );
    }
    //--------------------- main ---------------------------------
    /**
     * Application startup.
     * 
     * @param args String[]  May contain DNA input 
     * file name and pattern file name.
     */    
    public static void main( String[] args )
    {
        String patternFileName = "patternsDNA.txt";
        String dnaFileName = "inputDNA.txt";
        if ( args.length > 0 )
            patternFileName = args[ 0 ];
        if ( args.length > 1 )
            dnaFileName = args[ 1 ];
        
        new DNAregex( "DNA Regex", patternFileName, dnaFileName );
    }
}
