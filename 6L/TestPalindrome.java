//++++++++++++++++++++++++++ TestPalindrome.java +++++++++++++++++++++++++++
import java.util.*;
import java.io.*;
/**
 * TestPalindrome.java -- 
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

public class TestPalindrome 
{
    //---------------------- class variables ----------------------
    private static boolean interactive = true;
    private static Scanner terminal;
    private static String dashes = " ------------------------ ";
    
    //---------------------- instance variables ----------------------
    //------------------------ runPalindrome -------------------------
    /**
     * Test the Recursion.palindrome method.
     */
    public static void runPalindrome()
    {
        System.out.println( dashes + " Palindrome Test " + dashes );
        System.out.flush();
        
        String prompt = "Enter a string to check for palindrome";
        String input = readLine( prompt );
        while ( input.length() > 0 )
        {
            try 
            {
                if ( Recursion.palindrome( input ) ) 
                    System.out.println( "Yes: \"" + input + "\"" );
                else     
                    System.out.println( "No:  \"" + input + "\"" );
            }
            catch ( Exception ex )
            {
                System.out.println( "\n**** Palindrome crash:  "
                                       + ex.getClass().getName() );
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
        runPalindrome(); 
    }
}
