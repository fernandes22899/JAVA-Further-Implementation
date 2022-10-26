//++++++++++++++++++++++++ TestPow.java ++++++++++++++++++++++++++++++
import java.util.*;
import java.io.*;

/**
 * TestPow.java -- A driver for testing a recursive method.
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
public class TestPow 
{
    //---------------------- instance variables ----------------------
    private static boolean interactive = true;
    private static Scanner terminal;
    
    private static String dashes = "----------------------------";
        
    //--------------------- runPow ----------------------------------
    /**
     * Get parameters for a number and an exponent and invoke the exp method.
     * Keep testing until an empty line is entered.
     */
    public static void runPow()
    {
        String prompt = "Enter 'number exponent' on 1 line; " + 
            " empty line to quit";
        String line = readLine( prompt );
        
        while ( line.length() > 0 )
        {
            int num;
            int exp;
            Scanner parse = new Scanner( line );
            if ( parse.hasNextInt() )
            {
                num = parse.nextInt();
                if ( parse.hasNextInt() )
                {
                    exp = parse.nextInt();
                    System.out.println( dashes );
                    try 
                    {
                        float result = Recursion2.pow( num, exp );
                        System.out.println( result + " = " + num + "^" + exp );
                    }
                    catch ( Exception ex )
                    {
                        System.out.println( "****** Exception thrown: " +
                                           ex.getClass().getName() );
                        System.out.println( dashes + dashes );
                    }
                }
                else
                    System.out.println( "Error: exp field not an int" );
            }
            else
                System.out.println( "Error: number field not valid number: " );
            line = readLine( prompt );
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
        runPow();
    }
}

