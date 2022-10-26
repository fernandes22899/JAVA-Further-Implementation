//++++++++++++++++++++++++++++++++ Regex +++++++++++++++++++++++++++++++++++++
import java.util.regex.*;
import java.io.*;
import java.text.*;
/**
 * Regex - this class encapsulates some of Java's regular expression behavior
 *         into a single class.
 * @author rdb
 * Last Modified: April 2014
 */

public class Regex
{
    //--------------------- instance variables ----------------------------
    
    private Pattern          _pattern;
    private Matcher          _matcher;
    
    private String           _regexString;    // currently active reg expr
    private String           _inputString;
    
    //////////////////////////////////////////////////////////////////
    // Step 1a. Initialize _flags to Pattern.MULTILINE
    //////////////////////////////////////////////////////////////////
    private int              _flags = Pattern.MULTILINE;
    
    
    //---------------------- constructor ------------------------------
    /**
     * Build a Regex object; none of require information is available yet.
     */
    public Regex()
    {
        _regexString = null;
        _inputString   = null;
        _pattern     = null;
        _matcher     = null;
    }
    //---------------------- setRegex( String) ------------------------
    /**
     * Given a regular expression as a string, create a Pattern object
     * and also a Matcher if we have an input string already.
     * @param regex String
     */
    public void setRegex( String regex )
    {  
        ///////////////////////////////////////////////////////////////
        // Step 1b.
        //   If the argument is not null and has length > 0, 
        //      - save it in the instance variable _regexString
        //      - invoke the static 'compile' method of Pattern with the
        //        regex string and _flags as arguments.
        //      - assign the returned pattern object to the instance
        //        variable _pattern.
        //      - call the private method "makeMatcher" to make a Matcher
        //        object if it can.
        ///////////////////////////////////////////////////////////////

        if( regex != null && regex.length() > 0 )
        {
          _regexString = regex;
          _pattern = _pattern.compile( regex, _flags );
          
          makeMatcher();
        }
        
        
        
    }
    //------------------ setInput() ------------------------
    /**
     * Save the new input, create a Matcher if you have a pattern.
     * @param testString String
     */
    public void setInput( String testString )
    {
        /////////////////////////////////////////////////////////
        // Step 1c.
        //   - Save the parameter in the instance variable _inputString
        //   - Call the private makeMatcher method to create a Matcher
        //     object if it can.
        /////////////////////////////////////////////////////////
      _inputString = testString;
      
      if( _inputString != null && _inputString.length() > 0 )
        makeMatcher();
       
    }
    
    //------------------ makeMatcher() ------------------------
    /**
     * If an input string and pattern have been defined, use them
     *    to create a Matcher object.
     */
    public void makeMatcher(  )
    {
        /////////////////////////////////////////////////////////////
        // Step 1d.
        //   - if _pattern has been created and an _inputString specified
        //     make a Matcher object by calling the "matcher" method using
        //     _pattern. Assign it to the _matcher instance variable.
        //////////////////////////////////////////////////////////////
      if( _pattern != null && _inputString != null )
      {
        _matcher = _pattern.matcher( _inputString );
      }
    
    
    }
    //---------------------- find() -----------------------------
    /**
     * Return the next match (not all of them).
     * @return String
     */
    public String find( )
    {  
        /////////////////////////////////////////////////////////////
        // Step 1e.
        //     IF the matcher is not defined print an error message to 
        //        System.err.
        //     else
        //        call the find method of the Matcher object
        //        if it returns true
        //            call the group() method of the matcher object
        //            and return the results.
        //        else 
        //            return null
        /////////////////////////////////////////////////////////////
        String results = null;
        
        if( _matcher == null )
          System.err.println( "Could not find" );
        else if( _matcher.find() )
          return _matcher.group();
        
        
        return results;
    }
    
    //------------------ findAll() ------------------------
    /**
     * Find all matches in the input string to the pattern.
     *  Return all matches in one string separated by line feeds.
     * @return String
     */
    public String findAll()
    {
        ///////////////////////////////////////////////////////////////////
        // Step 2.
        //    IF matcher has not been created, 
        //       print an error message to System.err 
        //       return null
        //    else
        //      keep calling find method of matcher until it returns false.
        //      For each true match, 
        //        -call the group() method of matcher
        //           (it  returns the complete string matched by the pattern)
        //        -add string returned from group() plus \n with previous
        //    return string of all matches (which could be null)
        //////////////////////////////////////////////////////////////////
        String results = null;
        
        if( _matcher == null )
        {
          System.err.println( "FindAll: Matcher is null" );
          return null;
        }
        else
        {
          while( _matcher.find() )
          {
            results += _matcher.group() + "\n";
            
          }
        }
        
        return results;
    }
    //------------------ split() ------------------------
    /**
     * Split the input string according to the r.e.
     * @return String
     */
    public String split()
    {
        /////////////////////////////////////////////////////////////
        // Step 3:
        //   IF the pattern is defined and the inputString is defined
        //       Use the split method on the pattern 
        //           it returns an array of strings.
        //       You need to concatenate them together  (with \n) into a 
        //       single string and return it.
        /////////////////////////////////////////////////////////////
        String results = "";
        String[] arr;

        if( _pattern != null && _inputString != null )
        {
          
          
          arr = _pattern.split( _inputString );
          
          for( int i = 0; i < arr.length; i++ )
          {
            results += arr[i] + "\n";
          }
        }
        
        
        return results;
    }
    
    //---------------------- group() -----------------------------
    /**
     * Return the entire match string from previous match.
     * @return String
     */
    public String group( )
    {  
        //////////////////////////////////////////////////////
        // Step 4a
        // IF matcher not defined
        //    print an error message to System.err
        // ELSE need to return the results of calling the Matcher
        //      method, group(), 
        //
        // EXCEPT need to be sure that a match has occurred.
        // 2 choices:
        //   1. Make an instance variable that "remembers" whether
        //      the last match operation succeeded or not and set
        //      that everywhere in the program
        //   2. Put the group() call in a try - catch that will
        //      catch the IllegalStateException.
        // Return null if previous match failed
        ////////////////////////////////////////////////////// 
        String results = null;
        
        try
        {
          if( _matcher == null )
            System.err.println( "Group matcher is null" );
          else
            return _matcher.group();
        }
        catch( Exception e )
        {
          System.err.println( e );
          return null;
        }
        
        return results;
    }
    //---------------------- group( int ) -----------------------------
    /**
     * Return i-th group from previous match.
     * @param i int
     * @return String
     */
    public String group( int i )
    {  
        //////////////////////////////////////////////////////////////
        // Step 4b. 
        // IF matcher is not defined
        //    return null
        // ELSE
        //    Need to return the results from calling the 
        //         Matcher method, group( i ). 
        //
        //    if there is no group i, return null.
        //
        // The problem is to avoid an IndexOutOfBoundsException. This
        // is thrown if the i-th group doesn't exist or if it did not 
        // match anything in the current test string.
        //
        // There are 2 ways to do this: 
        // 1. Use the groupCount() method.
        //    Note: although group( 0 ) returns the ENTIRE match, this match
        //          is not included in groupCount.
        // 2. Put your invocation of group( i ) in a try block and catch the
        //    IndexOutOfBound exception if it is thrown.
        //
        // You also have to worry about whether the previous match failed
        //    as described in the group() method above, in which case an
        //    IllegalStateException is thrown.
        ///////////////////////////////////////////////////////////////////
        String results = null;
        
        try
        {
          if( _matcher == null )
            return null;
          else
            return _matcher.group( i );
        }
        catch( Exception e )
        {
          System.err.println( e );
        }
        
        return results;
    }
    
    //---------------------- setFlags( int ) ---------------------------
    /**
     * Some flags have changed, need to recompile the pattern and reset.
     * @param flags int
     */
    public void setFlags( int flags )
    {
        ////////////////////////////////////////////////////////////////
        // Step 6.  
        //    - assign the "flags" parameter to the _flags instance variable
        //    - call setRegex with the existing _regexString
        ////////////////////////////////////////////////////////////////   
      _flags = flags;
      setRegex( _regexString );
        
        
        
        
    }
    //---------------------- reset() -----------------------------
    /**
     * Reset the regular expression processing on the current input string.
     * So, next matching will start over again on the same input string.
     */
    public void reset( )
    {  
        if ( _matcher != null )
            _matcher.reset();
    }
    //--------------------- main -----------------------------------------
    /**
     * Convenience method for DrJava that invokes main application start.
     * @param args String[]    Not used
     */
    public static void main( String[] args )
    {
        RegexInJava.main( args );
    }
}
