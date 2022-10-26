//++++++++++++++++++++++++++ TestFindLast.java +++++++++++++++++++++++++++
import java.util.*;
import java.io.*;
/**
 * TestFindLast.java -- 
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

public class TestFindLast 
{
    //---------------------- class variables ----------------------
    private static boolean interactive = true;
    private static Scanner terminal;
    private static String dashes = " ---------------------- ";
    
    //---------------------- instance variables ----------------------
    //--------------------------- runFind ----------------------------
    /**
     * Test the Recursion.findLast method.
     */
    public static void runFind()
    {
        System.out.println( dashes + " FindLast Test " + dashes );
        System.out.flush();

        String listPrompt = "Enter a list of words on 1 line.";
        
        String   input = readLine( listPrompt );
        while ( input != null && input.length() > 0 )
        {
            String[] words = input.split( " " );
            int n = words.length;
            
            if ( n <= 1 )
                System.out.println( "***ERROR*** Must enter >= 1 word" );
            else
            {
                System.out.println( dashes + "Input array" + dashes );
                for ( int i = 0; i < n; i++ )
                    System.out.print( "   " + words[ i ] );
                System.out.println();
                String fPrompt = "Enter a word to find. Return to quit.";
                input = readLine( fPrompt );
                while ( input != null && input.length() > 0 )
                {
                    Scanner parse = new Scanner( input );
                    if ( parse.hasNext() )
                    {
                        String key = parse.next();
                        int keyPos = -1;
                        try
                        {
                            keyPos = Recursion.findLast( words, n, key );
                            System.out.print( key + ": " );
                            if ( keyPos >= 0 )
                                System.out.println( " is at " + keyPos );
                            else
                                System.out.println( " NOT FOUND " );
                        }
                        catch ( Exception ex )
                        {
                            System.out.println( "\n**** findLast crash: "
                                                   + ex.getClass().getName() );
                        }
                    }
                    else
                        System.out.println( "Bad input, no key: " + input );
                    input = readLine( fPrompt );
                }               
            }
            input = readLine( listPrompt );
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
        runFind(); 
    }
}
