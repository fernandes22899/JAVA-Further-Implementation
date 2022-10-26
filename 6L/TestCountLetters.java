//++++++++++++++++++++++++++ TestCountLetters.java +++++++++++++++++++++++++++
import java.util.*;
import java.io.*;
/**
 * TestCountLetters.java -- 
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

public class TestCountLetters 
{
    //---------------------- class variables ----------------------
    private static boolean interactive = true;
    private static Scanner terminal;
    private static String dashes = " ---------------------- ";
    
    //---------------------- instance variables ----------------------
    
    //-------------------- runCountLetters ----------------------------
    /**
     * Test the Recursion.countLetter method.
     */
    public static void runCountLetters()
    {
        System.out.println( dashes + " Count Letters Test " + dashes );
        System.out.flush();

        String strPrompt = "Enter a string for counting letters";
        String input = readLine( strPrompt );
        while ( input != null && input.length() > 0 )
        {
            System.out.println( "<<<<<<<<" + input + ">>>>>>>>>>>" );
            String letPrompt = "Enter the letter to count ";
            String letterLine = readLine( letPrompt );
            while ( letterLine.length() > 0 )
            {
                char letter = letterLine.charAt( 0 );
                
                if ( interactive )
                    System.out.println( dashes );
                int count = -1;
                try
                {
                    count = Recursion.countLetter( input, letter );
                    System.out.println( count + " " + letter );     
                }
                catch ( Exception ex )
                {
                    System.out.println( "\n**** countLetter crash: "
                                           + ex.getClass().getName() );
                }
                letterLine = readLine( letPrompt );
            }
            input = readLine( strPrompt );
        }
    }
        
    //---------------------- readLine( String ) ----------------------
    /**
     * Issue a strPrompt if we are in interactive mode and read a line
     * of input from the terminal.
     * @param strPrompt String
     * @return String
     */
    static String readLine( String strPrompt )
    {
        if ( interactive )
            System.out.println( "\n" + strPrompt );
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
        runCountLetters(); 
    }
}
