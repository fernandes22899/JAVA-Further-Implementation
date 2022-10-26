import javax.swing.*;
import java.util.*;
import java.io.*;
/**
 * Lexer.java - parses string input representing an infix arithmetic
 *              expression into tokens.
 *              The expression can use the operators =, +, -, *, /, %.
 *              and can contain arbitrarily nested parentheses.
 *              The = operator is assignment and must be absolutely lowest
 *              precedence.
 * March 2009
 * @author rdb
 * March 2016
 * ah: Simplified to do just lexical analysis.
 */
public class Lexer //extends JFrame
{
    //----------------------  class variables  ------------------------
    //---------------------- instance variables ----------------------
    
    private SymbolTable  _symbolTable;
    
    //----------- constants
    private String   dashes = " ---------------------------- ";
    private String   endOutputBlock =
        "====================================================================";
    //--------------------------- constructor -----------------------
    /**
     * Create the Lexer.
     * If there is a command line argument use it as an input file.
     * otherwise invoke an interactive dialog.
     *
     * @param args Array of arguments.
     */
    public Lexer( String[] args )
    {
        _symbolTable = SymbolTable.instance();
        
        if ( args.length > 0 )
            resultFile( args[ 0 ] );
        else
            interactive();
    }
    //--------------------- resultFile -----------------------------
    /**
     * Given a String containing a file name, open the file and read it.
     * Each line should represent an expression to be parsed.
     * For each line, result it, and print the result to System.out.
     *
     * @param fileName File name.
     */
    public void resultFile( String fileName )
    {
        //variables
        Scanner _scanner = null;
        FileInputStream file;
        // A try and catch for an exception ( File not found ) case.
        try
        {
            //getting the File
            file = new FileInputStream( fileName );
            _scanner = new Scanner( file );
            // reading the File
            while( _scanner.hasNextLine() )
            {
                System.out.println( _scanner.nextLine() );
            }
        }
        catch( FileNotFoundException e )
        {
            e.printStackTrace();
            //error massage incase file was not found.
            System.out.println( "Error: File Not Found" );
        }
    }
    //--------------------- resultLine -----------------------------
    /**
     * Parse each input line and do the right thing; return the
     *    "results" which can be null.
     *
     * @param line Input line.
     *
     * @return Line with annotations.
     */
    public String resultLine( String line )
    {
        System.out.println(  "Input: " + line );
        String trimmed = line.trim();
        if ( trimmed.length() == 0 || trimmed.charAt( 0 ) == '#' )
            return line;
        else
            return resultExpr( trimmed );
    }
    //--------------------- interactive -----------------------------
    /**
     * Use a file chooser to get an file name interactively, then
     * go into a loop prompting for expressions to be entered one
     * at a time.
     */
    public void interactive()
    {
        JFileChooser fChooser = new JFileChooser( "." );
        fChooser.setFileFilter( new TextFilter() );
        int choice = fChooser.showDialog( null, "Pick a file of expressions" );
        if ( choice == JFileChooser.APPROVE_OPTION )
        {
            File file = fChooser.getSelectedFile();
            if ( file != null )
                resultFile( file.getName() );
        }
        
        String prompt = "Enter an arithmetic expression: ";
        String expr = JOptionPane.showInputDialog( null, prompt );
        while ( expr != null && expr.length() != 0 )
        {
            String result = resultLine( expr );
            JOptionPane.showMessageDialog( null, expr + "\n" + result );
            expr = JOptionPane.showInputDialog( null, prompt );
        }
    }
    //------------------ resultExpr( String ) -------------------------
    /**
     * Get all fields in the expression, and
     * generate tokens for all of them, and
     * return a String representation of all of them.
     *
     * @param line Expression string.
     *
     * @return Annotated expression.
     */
    public String resultExpr( String line )
    {
        //variables
        String result = "";
        int top = 0;
        //loop to go over and get all the fields
        for( int i = 0; i < line.length(); i++ )
        {
            top = ( int )line.charAt( i );   
            //conditions for the assaigment of @, $, +, -, *, /, (, and ).
            //it goes through the int of the characters.
            if( ( top >= 65 && top <= 90 ) || ( top >= 97 && top <= 122 ) )
            {
                //variables
                result += "@" + line.charAt( i );
            }
            else if( top >= 48 && top <= 57 )
            {
                //constants
                result += "$" + line.charAt( i );
            }
            else
            {
                //operators
                switch( top )
                {
                    case '+':
                        result += " < + >";
                        break;
                        
                    case '-':
                        result += " < - >";
                        break;
                        
                    case '*':
                        result += " < * >";
                        break;
                        
                    case '/':
                        result += " < / >";
                        break;
                        
                    case '(':
                        result += " < ( >";
                        break;
                        
                    case ')':
                        result += " < ) >";
                        break;
                }
            }
        }
        return result;
    }
    //---------------------------- TextFilter -----------------------------
    /**
     * This class is used with FileChooser to limit the choice of files
     * to those that end in *.txt.
     */
    public class TextFilter extends javax.swing.filechooser.FileFilter
    {
        /**
         * Test whether file is acceptable type.
         *
         * @param f File descriptor.
         *
         * @return indication of whether file is acceptable.
         */
        public boolean accept( File f )
        {
            if ( f.isDirectory() || f.getName().matches( ".*txt" ) )
                return true;
            return false;
        }
        /**
         * Describe which files can be selected.
         *
         * @return the description.
         */
        public String getDescription()
        {
            return "*.txt files";
        }
    }
    //--------------------- main -----------------------------------------
    /**
     * Main method.
     *
     * @param args Array of arguments.
     *
     */
    public static void main( String[] args )
    {
        Lexer app = new Lexer( args );
    }
}