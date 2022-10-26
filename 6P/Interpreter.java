import javax.swing.*;
import java.util.*;
import java.io.*;

/**
 * Interpreter.java - parses string input representing an infix arithmetic
 *                 expression into tokens, and builds an expression tree.
 *                 The expression can use the operators =, +, -, *, /, %.
 *                 and can contain arbitrarily nested parentheses.
 *                 The = operator is assignment and must be absolutely lowest
 *                 precedence.
 * March 2013
 * @author rdb
 */
public class Interpreter //extends JFrame
{
    //----------------------  class variables  ------------------------
    
    
    //---------------------- instance variables ----------------------
    private boolean      _printTree = true;  // if true print tree after each
    //    expression tree is built 
    
    private SymbolTable _symTable;
    private Stack<TreeNode> _operatorsStack = new Stack<TreeNode>();
    private Stack<TreeNode> _operandsStack = new Stack<TreeNode>();
    private TreeNode _lastTreeNode = null;
    
    //----------- constants
    private String operators = "=()+-*/%";
    
    //--------------------------- constructor -----------------------
    /**
     * If there is a command line argument use it as an input file.
     * otherwise invoke an interactive dialog.
     *
     * @param args Array of arguments.
     */
    public Interpreter( String[] args ) 
    {      
        _symTable = SymbolTable.instance();
        
        if ( args.length > 0 )
            processFile( args[ 0 ] );
        else
            interactive();      
    }
    //--------------------- processFile -----------------------------
    /**
     * Given a String containing a file name, open the file and read it.
     * Each line should represent an expression to be parsed.
     *
     * @param fileName File name.
     */
    public void processFile( String fileName ) 
    {
        try 
        {
            Scanner fileScanner = new Scanner( new File( fileName ) );
            String line;
            while ( fileScanner.hasNextLine() ) 
            {
                line = fileScanner.nextLine();
                String results = processLine( line );
                System.out.println( results );
            }
            fileScanner.close();
        } 
        catch ( FileNotFoundException e ) 
        {
            System.out.println( "ERROR: " + e.getMessage() );
        }
    }
    //--------------------- processLine -----------------------------
    /**
     * Parse each input line -- it shouldn't matter whether it comes from
     * the file or the popup dialog box. It might be convenient to return
     * return something to the caller in the form of a String that can
     * be printed or displayed.
     *
     * @param line Input line.
     *
     * @return Processed line.
     */
    public String processLine( String line ) 
    {
        System.out.println( "Input: " + line );
        String trimmed = line.trim();
        if ( trimmed.length() == 0 || trimmed.charAt( 0 ) == '#' )
            return line;
        else
            return processExpr( trimmed );
    }
    //--------------------- interactive -----------------------------
    /**
     * Use a file chooser to get a file name interactively, then 
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
                processFile( file.getName() );
        }
        
        String prompt = "Enter an arithmetic expression: ";
        String expr = JOptionPane.showInputDialog( null, prompt );
        while ( expr != null && expr.length() != 0 )
        {
            String result = processLine( expr );
            JOptionPane.showMessageDialog( null, expr + "\n" + result );
            expr = JOptionPane.showInputDialog( null, prompt );
        }
    }
    
    //------------------ processExpr( String ) -------------------------
    /**
     * Get all fields in the expression, and
     * generate tokens for all of them, and
     * return a String representation of all of them.
     *
     * @param line Expression string.
     *
     * @return Annotated expression.
     */
    public String processExpr( String line )
    {      
        String results = "";
        //results = "Input:\t" + line + "\n";
        
        if( line.equals( "@print" ) ) 
        {
            results += " ---------    " + line + " -----------\n";
            results += _lastTreeNode.print( 0 );
        }
        else if( line.equals( "@print off" ) ) 
        {
            results += " ---------    " + line + " ----------\n";
            _printTree = false;
        }
        else if( line.equals( "@print on" ) ) 
        {
            results += " ----------    " + line + " ---------\n";
            _printTree =  true;
        }
        else
        {
            try
            {
                TreeNode root = parseTree( line );
                if( _printTree ) 
                {
                    results += "Tree:\n";
                    results += root.print( 0 ) + "\n";      
                }     
                results += " -----------    " + line + " -------\n";
                results += root.evaluate( _symTable );
                _lastTreeNode = root;
            } 
            catch ( Exception e ) 
            {
                results += e.getMessage() + ": invalid expression" + "\n";
                if( _printTree ) 
                {
                    results += "Empty result tree\n";
                    results += "Tree:" + "\n" + "null\n";      
                }     
                results += " ----------    " + line + " --------\n";
                results += "Not a valid expression" + ":\n" + line;
                _lastTreeNode = null;
            }       
        }                
        results += "\n================================";
        return results;
    }
    /**
     * compare precedence. 
     * @param op1 char
     * @param op2 char
     * @return precedence
     * */
    private int comparePrecedence( char op1, char op2 ) 
    {
        int precedence1;
        int precedence2;
        if( op1 == op2 )
            return 0;
        if( op1 == '=' )
            return -1;
        if( op2 == '=' )
            return 1;
        
        if( op1 == '+' || op1 == '-' )
            precedence1 = 1;
        else
            precedence1 = 2;
        if( op2 == '+' || op2 == '-' )
            precedence2 = 1;
        else
            precedence2 = 2;
        return ( precedence1 - precedence2 );
    }
    
    /**
     * Parse an expression line to TreeNode.
     * 
     * @param line input expression line
     * @return A TreeNode if parsed successfully, else return null
     * @throws Exception
     */
    private TreeNode parseTree( String line ) throws Exception 
    {
        Scanner lineScanner = new Scanner( line );
        _operandsStack.clear();
        _operatorsStack.clear();
        
        while ( lineScanner.hasNext() ) 
        {
            String tokenStr = lineScanner.next();
            if( tokenStr.equals( ")" ) )
            {
                while( !_operatorsStack.isEmpty() ) 
                {
                    OperatorNode opNode = ( OperatorNode )_operatorsStack.pop();
                    if( opNode.getOp() == '(' )
                        break;
                    if( _operandsStack.isEmpty() ) 
                    {
                        throw new Exception( "Operator: " 
                                                + opNode.getOp() +
                                            " missing 1st operand" );
                    }
                    TreeNode right = _operandsStack.pop();
                    if( _operandsStack.isEmpty() ) 
                    {
                        throw new Exception( "Operator: " +
                                            opNode.getOp() +
                                            " missing 2nd operand" );
                    }
                    TreeNode left = _operandsStack.pop();
                    opNode.setLeft( left );
                    opNode.setRight( right );
                    _operandsStack.push( opNode );
                }
            }
            else 
            {
                TreeNode node = parseToken( tokenStr );
                if ( node instanceof OperatorNode ) 
                {
                    OperatorNode opNode = ( OperatorNode )node;
                    if( opNode.getOp() != '(' && opNode.getOp() != '=' ) 
                    {
                        while( !_operatorsStack.isEmpty() ) 
                        {
                            OperatorNode topOpNode = 
                                ( OperatorNode )_operatorsStack.peek();
                            if( topOpNode.getOp() == '(' )
                                break;
                            if( comparePrecedence( opNode.getOp(), 
                                                  topOpNode.getOp() ) <= 0 ) 
                            {
                                if( _operandsStack.isEmpty() ) 
                                {
                                    throw new Exception( 
                                                        "Operator: " 
                                                            + 
                                                        topOpNode.getOp() 
                                                            + 
                                                       " missing 1st operand" );
                                }
                                TreeNode right = _operandsStack.pop();
                                if( _operandsStack.isEmpty() ) 
                                {
                                    throw new Exception( 
                                                        "Operator: " 
                                                            + 
                                                        topOpNode.getOp() 
                                                            + 
                                                       " missing 2nd operand" );
                                }
                                TreeNode left = _operandsStack.pop();
                                topOpNode.setLeft( left );
                                topOpNode.setRight( right );
                                _operandsStack.push( topOpNode );
                                _operatorsStack.pop();
                            }
                            else break;
                        }
                    }     
                    _operatorsStack.push( opNode );
                }
                else
                    _operandsStack.push( node );
            }   
        }
        while ( !_operatorsStack.isEmpty() ) 
        {
            OperatorNode opNode = 
                ( OperatorNode )_operatorsStack.pop();
            if( _operandsStack.isEmpty() ) 
            {
                throw new Exception( "Operator: " 
                                        + opNode.getOp() 
                                        + " missing 1st operand" );
            }
            TreeNode right = _operandsStack.pop();
            if( _operandsStack.isEmpty() ) 
            {
                throw new Exception( "Operator: " 
                                        + opNode.getOp() 
                                        + " missing 2nd operand" );
            }
            TreeNode left = _operandsStack.pop();
            opNode.setLeft( left );
            opNode.setRight( right );
            _operandsStack.push( opNode );
        }
        TreeNode root = _operandsStack.pop();
        if( !_operandsStack.isEmpty() )
            throw new Exception( "'Extra' operands: " 
                                    + _operandsStack.pop().toString() );
        return root;
    }
    
    //------------------ parseToken ( String ) -------------------------
    /**
     * Parse the token string to a TreeNode. 
     *
     * @param token a token string in an expression
     *
     * @return a TreeNode from the string
     */
    private TreeNode parseToken( String token ) 
    {
        if ( isValidJavaIdentifier( token ) ) 
        {
            VariableNode var = new VariableNode( token );
            return var;
        } else if ( operators.contains( token ) && token.length() == 1 ) 
        {
            OperatorNode op = new OperatorNode( token.charAt( 0 ) );
            return op;
        } else 
        {
            try 
            {
                NumberNode num = new NumberNode( Double.parseDouble( token ) );
                return num;
            } 
            catch ( NumberFormatException e ) 
            {
                return null;
            }
        }
    }
    
    
    //---------------- isValidJavaIdentifier ( String ) --------------
    /**
     * Check whether given string is a valid Java identifier.
     * @param s input string
     * @return true if s is a valid java identifier, else return false
     */
    private boolean isValidJavaIdentifier( String s ) 
    {
        if( !Character.isJavaIdentifierStart( s.charAt( 0 ) ) ) 
            return false;
        for( int i = 1; i < s.length(); i++ ) 
        {
            if( !Character.isJavaIdentifierPart( s.charAt( i ) ) )
                return false;
        }
        return true;
    }
    
    
    //+++++++++++++++++++++++++ inner class +++++++++++++++++++++++++++++++
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
        Interpreter app = new Interpreter( args );
    }
}