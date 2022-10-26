//++++++++++++++++++++++++++ TestIsSubstring.java +++++++++++++++++++++++++++
import java.util.*;
import java.io.*;
/**
 * TestIsSubstring.java -- 
 *      A driver for Recursion lab.
 * 
 * @author rdb
 * 
 * 09/15/10 rdb
 *    Added loops in each test, so can do multiple tests of the same
 *    method without picking method each time
 * 02/15/14 rdb
 *    Added try-catch in palinedrome (do it for other???)
 * 02/08/15 rdb
 *    Created a separate test class for each method.
 */

public class TestIsSubstring 
{
    //---------------------- class variables ----------------------
    private static boolean interactive = true;
    private static Scanner terminal;
    private static String dashes = " ---------------------- ";
    
    //---------------------- instance variables ----------------------
    
    //----------------------- runSubstring ---------------------------
    /**
     * Test the Recursion.isSubstring method.
     */
    public static void runSubstring()
    {
        System.out.println( dashes + " IsSubstring Test " + dashes );
        System.out.flush();

        String prompt = "Enter a string to search";
        String subPrompt = "Enter a substring to search for";
        
        String input = readLine( prompt );
        while ( input != null && input.length() > 0 )
        {
            System.out.println( "<<<<<<<<" + input + ">>>>>>>>>>>" );
            
            String subInput = readLine( subPrompt );
            while ( subInput.length() > 0 )
            {
                try 
                {
                    if ( Recursion.isSubstring( input, subInput ) )
                        System.out.println( "Yes: \"" + subInput + "\"" );
                    else     
                        System.out.println( "No:  \"" + subInput + "\"" );
                }
                catch ( Exception ex )
                {
                    System.out.println( "\n**** isSubstring crash: "
                                           + ex.getClass().getName() );
                }
                subInput = readLine( subPrompt );
            }
            input = readLine( prompt );
        }
    }
    
    //---------------------- readLine( String ) ----------------------
    /**
     * Issue a prompt if we are in interactive mode and read a line
     * of input from the terminal.
     * @param prompt String
     * @return String
     */
    static String readLine( String prompt )
    {
        if ( interactive )
            System.out.println( "\n" + prompt );
        if ( terminal.hasNextLine() )
            return terminal.nextLine();
        else
            return "";
    }
    
    //----------------------- main -----------------------------------
    /**
     * Main program allows user to select which test to run. If there is
     *   a command line argument, this is a batch run. The argument will be
     *   a file to read.
     * @param args String[]
     * @throws FileNotFoundException 
     */
    public static void main( String[] args ) throws FileNotFoundException
    {
        if ( args.length == 0 ) 
            terminal = new Scanner( System.in );
        else   // running in batch, don't prompt and open file
        {  
            interactive = false;
            terminal = new Scanner( new File( args[ 0 ] ) );
        }
        runSubstring(); 
    }
}
