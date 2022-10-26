//++++++++++++++++++++++++ TestCharPair.java ++++++++++++++++++++++++++++++
import java.util.*;
import java.io.*;

/**
 * TestCharPair.java -- A driver for testing a recursive method.
 * 
 * @author rdb
 * 09/19/10 rdb
 *     Added loops for individual test, so can re-test same method
 *     many times.
 * 02/15/14 rdb
 *     Added try-catch around calls to student written methods
 * 02/09/15 rdb
 *     Made separate driver programs for each method.
 */
public class TestCharPair 
{
    //---------------------- instance variables ----------------------
    private static boolean interactive = true;
    private static Scanner terminal;
    
    private static String dashes = "----------------------------";
    
    //-------------------- runPairTest ----------------------------
    /**
     * Get input string and invoke the charPairTest method to check 
     * if string is composed of successive pairs of matching characters.
     * 
     * Keep testing until an empty line is entered.
     */
    public static void runPairTest()
    {
        String prompt = "Enter string to test for matching pairs; " + 
            "empty line to quit";
        String input = readLine( prompt );
        while ( input.length() > 0 )
        {
            System.out.println( dashes );
            System.out.print( "\"" + input + "\""  );  
            try 
            {
                if ( Recursion2.charPairTest( input ) )
                    System.out.println( " IS a sequence of pairs" );
                else 
                    System.out.println( " IS NOT a sequence of pairs" );
            }
            catch ( Exception ex )
            {
                System.out.println( "****** Exception thrown: " +
                                   ex.getClass().getName() );
                System.out.println( dashes + dashes );
            }
            input = readLine( prompt );
        }
    }
    //------------------- array2String( int[] -------------------------
    /**
     * Utility method to convert an int array to a String representation.
     * @param array int[]   
     * @return String
     */
    private static String array2String( int[] array )
    {
        StringBuffer out = new StringBuffer();
        out.append( "[ " );
        for ( int i = 0; i < array.length; i++ )
            out.append( array[ i ] + ", " );
        out.replace( out.length() - 2, out.length(), " ]" );
        return out.toString();
    }
    
    //---------------------- readIntArray() -------------------------------
    /**
     * reads one line of input, parses to an int array and returns it.
     *     A 0 length array means no input.
     * @param prompt String
     * @return int[]
     */
    private static int[] readIntArray( String prompt )
    {
        String arrayLine = readLine( prompt );
        
        Scanner list = new Scanner( arrayLine );
        int[] temp = new int[ 1000 ];   // way bigger than possible to enter
        int n = 0;
        while ( list.hasNextInt() )
            temp[ n++ ] = list.nextInt();
        int[] ret = new int[ n ];
        for ( int i = 0; i < n; i++ )
            ret[ i ] = temp[ i ];
        return ret;
    }
    
    //---------------------- readLine( String ) -----------------------------
    /**
     * iIssue a prompt if we are in interactive mode and read a line
     *     of input from the terminal.
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
    
    //----------------------- main ----------------------------------------
    /**
     * Test programs allows user to select which test to run by simply running
     *   the desired class. 
     * If there is a command line argument, this is a batch run. 
     *    The argument will be a file that contains input for this test.
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
        runPairTest();
    }
}