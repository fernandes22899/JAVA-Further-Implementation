//++++++++++++++++++++++++++ TestMaxValue.java ++++++++++++++++++++++++
import java.util.*;
import java.io.*;
/**
 * TestMaxValue.java -- a driver for Recursion lab.
 * 
 * @author rdb
 * 02/08/15 
 *    Created a separate test class for each method.
 */

public class TestMaxValue 
{
    //---------------------- class variables ----------------------
    private static boolean interactive = true;
    private static Scanner terminal;
    private static String dashes = " ---------------------- ";
    
    //---------------------- instance variables ----------------------
    //--------------------------- runMax -----------------------------
    /**
     * Test the Recursion.maxValue method.
     */
    public static void runMax()
    {
        System.out.println( dashes + " MaxFind Test " + dashes );
        System.out.flush();
        
        String prompt = "Enter list of ints on 1 line. <cr> to quit.";      
        String input = readLine( prompt );
        while ( input != null && input.length() > 0 )
        {
            Scanner list = new Scanner( input );
            int  temp [] = new int[ 1000 ]; // way bigger than possible
            int n = 0;
            while ( list.hasNextInt() )
                temp[ n++ ] = list.nextInt();
            int actual[] = new int[ n ];
            for ( int i = 0; i < n; i++ )
                actual[ i ] = temp[ i ];
            
            if ( n == 0 )
                System.out.println( "***ERROR*** No numbers entered" );
            else 
            {
                try
                {
                    //System.out.println( dashes );
                    System.out.println( "Max of " + input );
                    System.out.println( Recursion.maxValue( actual, n ) );   
                }
                catch ( Exception ex )
                {
                    System.out.println( "\n**** maxValue crash: "
                                           + ex.getClass().getName() );
                }
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
        runMax(); 
    }
}
