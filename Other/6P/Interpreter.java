import javax.swing.*;
import java.util.*;
import java.io.*;

/**
 * Interpreter.java - parses string input representing an infix arithmetic
 *                 expression into toks, and builds an expression tree.
 *                 The expression can use the op =, +, -, *, /, %.
 *                 and can contain arbitrarily nested parentheses.
 *                 The = operator is assignment and must be absolutely lowest
 *                 precedence.
 * March 2013
 * @author rdb
 */
public class Interpreter
{
    //---------------------- instance variables ----------------------
    private boolean      _printTree = true;  // if true print tree after each
    private SymbolTable _st;
    private Stack<TreeNode> _opStack = new Stack<TreeNode>();
    private Stack<TreeNode> _operStack = new Stack<TreeNode>();
    private TreeNode _tn = null;
    
    //----------- constants
    private String opsPos = "=()+-*/%";
    
    //--------------------------- constructor -----------------------
    /**
     * If there is a command line argument use it as an input file.
     * otherwise invoke an interactive dialog.
     *
     * @param args Array of arguments.
     */
    public Interpreter( String[] args ) 
    {      
        _st = SymbolTable.instance();
        
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
            Scanner scan = new Scanner( new FileReader( fileName ) );
            String line;
            
            while( scan.hasNextLine() )
            {
                line = scan.nextLine();
                if( !line.equals( "" ) )
                {
                    line = line.trim();
                    processLine( line );
                }
            }
            
            scan.close();
        }
        catch( Exception e )
        {
            System.out.println( "Error: " + e );
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
    
    /**processExpression.
     * @param str String.
     * @return String.
     */
    public String processExpr( String str )
    {      
        String res = "";
        
        if( str.equals( "@print" ) ) 
        {
            res += " ---------    " + str + " -----------\n";
            res += _tn.print( 0 );
        }
        else if( str.equals( "@print off" ) ) 
        {
            res += " ---------    " + str + " ----------\n";
            _printTree = false;
        }
        else if( str.equals( "@print on" ) ) 
        {
            res += " ----------    " + str + " ---------\n";
            _printTree =  true;
        }
        else
        {
            try
            {
                TreeNode root = parseTree( str );
                if( _printTree ) 
                {
                    res += "Tree:\n";
                    res += root.print( 0 ) + "\n";      
                }     
                res += " -----------    " + str + " -------\n";
                res += root.evaluate( _st );
                _tn = root;
            } 
            catch ( Exception e ) 
            {
                res += e.getMessage() + ": invalid expression" + "\n";
                if( _printTree ) 
                {
                    res += "Empty result tree\n";
                    res += "Tree:" + "\n" + "null\n";      
                }     
                res += " ----------    " + str + " --------\n";
                res += "Not a valid expression" + ":\n" + str;
                _tn = null;
            }       
        }                
        res += "\n=============================";
        return res;
    }
    
    /**precedence. 
     * @param ch1 char
     * @param ch2 char
     * @return int
     * */
    private int precedence( char ch1, char ch2 ) 
    {
        int p1;
        int p2;
        if( ch1 == ch2 )
            return 0;
        if( ch1 == '=' )
            return -1;
        if( ch2 == '=' )
            return 1;
        
        if( ch1 == '+' || ch1 == '-' )
            p1 = 1;
        else
            p1 = 2;
        if( ch2 == '+' || ch2 == '-' )
            p2 = 1;
        else
            p2 = 2;
        return ( p1 - p2 );
    }
    
    /**ParseTree.
     * @param l String
     * @return TreeNode
     * @throws Exception e
     */
    private TreeNode parseTree( String l ) throws Exception 
    {
        Scanner scan = new Scanner( l );
        _operStack.clear();
        _opStack.clear();
        
        while ( scan.hasNext() ) 
        {
            String str = scan.next();
            if( str.equals( ")" ) )
            {
                while( !_opStack.isEmpty() ) 
                {
                    OperatorNode opNode = ( OperatorNode )_opStack.pop();
                    
                    if( opNode.getOp() == '(' )
                        break;
                    
                    if( _operStack.isEmpty() ) 
                        throw new Exception( "Operator: " 
                                                + opNode.getOp() +
                                            " does not have 1st operator" );
                    
                    TreeNode right = _operStack.pop();
                    
                    if( _operStack.isEmpty() ) 
                        throw new Exception( "Operator: " +
                                            opNode.getOp() +
                                            " does not have 2nd operator" );
                    
                    TreeNode left = _operStack.pop();
                    opNode.setLeft( left );
                    opNode.setRight( right );
                    _operStack.push( opNode );
                }
            }
            else 
            {
                TreeNode node = parseToken( str );
                if ( node instanceof OperatorNode ) 
                {
                    OperatorNode opNode = ( OperatorNode )node;
                    if( opNode.getOp() != '(' && opNode.getOp() != '=' ) 
                    {
                        while( !_opStack.isEmpty() ) 
                        {
                            OperatorNode highNode = 
                                ( OperatorNode )_opStack.peek();
                            if( highNode.getOp() == '(' )
                                break;
                            if( precedence( opNode.getOp(), 
                                                  highNode.getOp() ) <= 0 ) 
                            {
                                if( _operStack.isEmpty() ) 
                                    throw new Exception( 
                                                        "Operator: " 
                                                            + 
                                                        highNode.getOp() 
                                                            + 
                                                       " missing 1st operand" );
                                
                                TreeNode right = _operStack.pop();
                                if( _operStack.isEmpty() ) 
                                
                                    throw new Exception( 
                                                        "Operator: " 
                                                            + 
                                                        highNode.getOp() 
                                                            + 
                                                       " missing 2nd operand" );
                                
                                TreeNode left = _operStack.pop();
                                highNode.setLeft( left );
                                highNode.setRight( right );
                                _operStack.push( highNode );
                                _opStack.pop();
                            }
                            else break;
                        }
                    }     
                    _opStack.push( opNode );
                }
                else
                    _operStack.push( node );
            }   
        }
        while ( !_opStack.isEmpty() ) 
        {
            OperatorNode opNode = ( OperatorNode )_opStack.pop();
            if( _operStack.isEmpty() ) 
            {
                throw new Exception( "Operator: " 
                                        + opNode.getOp() 
                                        + " missing 1st operand" );
            }
            TreeNode right = _operStack.pop();
            if( _operStack.isEmpty() ) 
            {
                throw new Exception( "Operator: " 
                                        + opNode.getOp() 
                                        + " missing 2nd operand" );
            }
            TreeNode left = _operStack.pop();
            opNode.setLeft( left );
            opNode.setRight( right );
            _operStack.push( opNode );
        }
        TreeNode root = _operStack.pop();
        if( !_operStack.isEmpty() )
            throw new Exception( "'Extra' operands: " 
                                    + _operStack.pop().toString() );
        return root;
    }
    
    /**Parse the Token. 
     * @param tok String
     * @return TreeNode
     */
    private TreeNode parseToken( String tok ) 
    {
        if ( valid( tok ) ) 
        {
            VariableNode vn = new VariableNode( tok );
            return vn;
        } else if ( opsPos.contains( tok ) && tok.length() == 1 ) 
        {
            OperatorNode ops = new OperatorNode( tok.charAt( 0 ) );
            return ops;
        } else 
        {
            try 
            {
                NumberNode nn = new NumberNode( Double.parseDouble( tok ) );
                return nn;
            } 
            catch ( Exception e ) 
            {
                System.out.println( "Error" + e );
                return null;
            }
        }
    }
    
    /**Method that checks for valid identifier.
     * @param s String
     * @return boolean
     */
    private boolean valid( String s ) 
    {
        if( !Character.isJavaIdentifierStart( s.charAt( 0 ) ) ) 
            return false;
        for( int i = 1; i < s.length(); i++ ) 
            if( !Character.isJavaIdentifierPart( s.charAt( i ) ) )
                return false;
        
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
