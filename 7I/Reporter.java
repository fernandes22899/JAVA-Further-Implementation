//+++++++++++++++++++++ Reporter +++++++++++++++++++++++++++++++
import javax.swing.*;
import java.io.*;

/**
 * Reporter receives pre-defined report information at various points
 *     during the Target game application. Depending on static settings, these
 *     reports may be presented in a JOptionPane, or just recorded in a LOG 
 *     file -- or other options.
 * This is essentially a collection of static methods tailored to the app.
 * 
 * @author rdb
 * 02/09/15 
 */

public class Reporter
{
    //-------------------- class variables ---------------------------
    static boolean interactive = true;
    static boolean noLogFile = false; // set if log file can't be opened
    static PrintStream log = null;
    
    //-------------------- log -------------------------------
    /**
     * Log a message to the standard output, but don't put it in popup box.
     * @param msg String
     */
    public static void log( String msg )
    {
        if ( log == null && !noLogFile )
            openLog();
        if ( noLogFile )
            System.err.println( msg );
        else
        {
            log.println( msg );
            log.flush();   // always flush since won't close
        }
    }  
    //---------------- openLog -----------------------------------
    /**
     * Open the log file.
     */
    private static void openLog()
    {
        try
        {
            log = new PrintStream( "log.txt" ); 
        }
        catch ( FileNotFoundException fnf )
        {
            System.err.println( "Unable to open log file: " 
                                   + fnf.getMessage() + "\n" 
                                   + "  --------> Writing to System.err." );
            noLogFile = true;
        }
    }

    //-------------------- logMove-------------------------------
    /**
     * Log the Move made to log file. 
     * @param moveCode char  o, t, f, k
     * @param card String    the 2-char representation of the base card
     */
    public static void logMove( char moveCode, String card )
    {
        log( "<-->" + moveCode + ": " + card );
    }            

    //-------------------- logMoves-------------------------------
    /**
     * Log a Move to the standard log file, but don't put it in popup box.
     * @param moveCode char
     * @param cardIds String
     */
    public static void logMoves( char moveCode, String cardIds )
    {
        log( "?" + moveCode + ": " + cardIds );
    }            

    //-------------------- report -------------------------------
    /**
     * Report any message.
     * @param msg String
     */
    public static void report( String msg )
    {
        if ( interactive )
            JOptionPane.showMessageDialog( null, msg );
        System.out.println( msg );
    }            
    //-------------------- reportResponse -------------------------------
    /**
     * Report a message that allows user to respond about continuation.
     * @param msg String
     * @return boolean    true Continue, false Cancel
     */
    public static boolean reportResponse( String msg )
    {
        if ( interactive )
        {
            int choice = JOptionPane.showConfirmDialog( null, msg, "Choose",  
                                          JOptionPane.CANCEL_OPTION );
            System.out.println( "Option = " + choice );
            
            return choice == 0;
        }
        else
        {
            System.out.println( msg + "--> Cancel"  );
            return false;
        }
    }            
}