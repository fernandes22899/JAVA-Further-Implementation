//++++++++++++++++++++++ ReadArgs.java ++++++++++++++++++++++++++++++++++++
/**
 * A simple utility class for parsing command line arguments. Application 
 *    passes the command line args, the desired argument's index and a 
 *    default value for the argument if it is missing or not of the right type.
 * There are 3 methods: one for int arguments, one for float arguments and one
 *    for String arguments.
 * It is an error if the argument is present, but no exception is thrown. An
 *    error  message is printed to standard error and the default is used.
 * It is NOT an error if the argument is missing. 
 * Example calls:
 *     String title = ReadArgs.getArg( args, 0, "" ); 
 *     int    nSteps = ReadArgs.getArg( args, 1, 5 );
 *     int    stepSize = ReadArgs.getArg( args, 2, 1.0f );   
 * 
 * @author rdb
 *            Extracted from earlier Utilities.java
 */ 

public class ReadArgs
{
    //---------------------- getArg( String[], int, int ) -----------------
    /**
     * integer version of getArg. Last parameter defines default and type.
     * @param args String[]   command line argument array
     * @param which int       index of desired 
     * @param defaultVal int  value returned if argument missing or incorrect
     * @return int
     */
    public static int getArg( String[ ] args, int which, int defaultVal )
    {
        try
        {
            return Integer.parseInt( args[ which ] );
        }
        catch ( ArrayIndexOutOfBoundsException oob )  // not an error
        { }
        catch ( NumberFormatException nfe )
        {
            System.err.println( "Error: Bad command line argument: " + which
                                 + " = " + args[ which ] + ". Should be int!"
                                 + " Using default value: " + defaultVal );
        }  
        return defaultVal;  // also return default if arg is missing
    }
    //---------------------- getArg( String[], int, float ) -----------------
    /**
     * float version of getArg. Last parameter defines default and type.
     * @param args String[]   command line argument array
     * @param which int       index of desired 
     * @param defaultVal float  value returned if argument missing or incorrect
     * @return float
     */
    public static float getArg( String[ ] args, int which, float defaultVal )
    {
        try
        {
            return Float.parseFloat( args[ which ] );
        }
        catch ( ArrayIndexOutOfBoundsException oob )  // not an error
        { }
        catch ( NumberFormatException nfe )
        {
            System.err.println( "Error: Bad command line argument: " + which  
                                 + " = " + args[ which ] + ". Should be float!"
                                 + " Using default value: " + defaultVal );
        }  
        return defaultVal;  // also return default if arg is missing
    }
    //---------------------- getArg( String[], int, String ) -----------------
    /**
     * String version of getArg. Last parameter defines default and type.
     * @param args String[]   command line argument array
     * @param which int       index of desired 
     * @param defaultVal String  value returned if argument missing
     * @return String
     */
    public static String getArg( String[ ] args, int which, String defaultVal )
    {
        try
        {
            return args[ which ];
        }
        catch ( ArrayIndexOutOfBoundsException oob )  // not an error
        { }
        return defaultVal;
    }
}