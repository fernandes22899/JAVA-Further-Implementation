//++++++++++++++++++++++++ Recursion.java ++++++++++++++++++++++
import java.util.*;
import java.io.*;
/**
 * Recursion.java - a collection of recursive static methods that 
 *                 need to be completed.
 * @author rdb
 * 
 * Last modified: 09/15/10 rdb
 */

public class Recursion
{  
    ////////////////////////////////////////////////////////////////
    //---------------------  palindrome( String ) --------------------
    /**
     *  Returns true if this String is a palindrome.
     *    A palindromic string is a mirrored about its center, such as
     *          abba, otto, civic
     * @param input String
     * @return boolean
     */
    ////////////////////////////////////////////////////////////////
    // Algorithm:
    //    if string length <= 1 
    //       return true
    //    compare the first and last characters in the string
    //    if they are not equal, 
    //       return false
    //    recursion case:
    //       this string is a palindrome if and only if 
    //       the substring between the first and last is
    //       a palindrome, so return the result of recursively
    //       calling the method with that substring
    //////////////////////////////////////////////////////////////     
    public static boolean palindrome( String input )
    {
        //------------- start palindrome ------------
        //---- 2 base cases ----
        //     1. empty string (length 0) is a palindrome
        //     2. string of length 1 is a palindrome
        if ( input.length() <= 1 ) 
            return true;
        
        //********** Delete or comment out the next line *****************
        //System.out.println( "*** palindrome not implemented!***" );

        //---- 3rd base case: first char doesn't match last
        //    fill in the code here
        if( input.charAt( 0 ) == input.charAt( input.length()-1 ) )
            return palindrome( input.substring( 1, input.length()-1 ) );
        
        //---- recursion case -----
        //    fill in the code here
        
        return false;
    }   //------------- end palindrome ------------
    
    ////////////////////////////////////////////////////////////////
    //-------------------- countLetter( String, char ) ---------------
    /**
     * Returns the number of occurrences of the letter "letter"
     *    in the string.
     * @param input String    String to search through for counting
     * @param letter char     Character to count
     * @return int            the cont of the occurrences of letter in string
     */
    //////////////////////////////////////////////////////////////////
    // Algorithm:
    //    if string length is 0 
    //       return 0
    //    count the occurrences of the letter in the substring that
    //        excludes the first character
    //    if  first character == the letter we are counting 
    //        add 1 to the count
    //    return the count
    /////////////////////////////////////////////////////////////////
    public static int countLetter( String input, char letter )
    {
        int occur = 0;
        //------------- start countLetter ------------
        // base case, the string is the empty string, so it has 0
        //   occurrences of the letter
        if ( input.length() == 0 )
            return 0;

        //********** Delete or comment out the next line *****************
        //System.out.println( "*** countLetter not implemented!***" );
        
        //---- recursion case -----
        //    fill in the code here
        
        if( input.charAt( 0 ) == letter )
        {
            return 1 + countLetter( input.substring( 1 ), letter );
        }
        else
            return countLetter( input.substring( 1 ), letter );
        
        
    }   //------------- end countLetter ------------ 
    
    ////////////////////////////////////////////////////////////////
    //-------------- isSubstring( String str, String sub ) ----------------  
    /** 
     * Search through the first argument for the first occurrence of the
     *      2nd argument.
     * @param str String    String to search THROUGH
     * @param sub String    String to search FOR
     * @return boolean      true if sub is a substring of str
     */
    //////////////////////////////////////////////////////////////////
    // Algorithm:
    //    if length of sub > length of str
    //       return false: larger string can't be substring of smaller
    //    if str startsWith sub
    //       return true
    //    recurse using substring of str that excludes the first char
    //////////////////////////////////////////////////////////////////
    
    public static boolean isSubstring( String str, String sub )
    { 
        //------------- start isSubstring ------------
        // base case 1: the length of sub > length of str
        //    return false; sub cannot match string shorter than itself
        if ( sub.length() > str.length() )
            return false;
        
        //********** Delete or comment out the next line *****************
        //System.out.println( "*** isSubstring not implemented!***" );

        // base case 2: the start of str matches sub, return true
        //    fill in the code here

        //---- recursion case -----
        //    fill in the code here
        if( str.startsWith( sub ) )
            return true;
        
        return isSubstring( str.substring( 1 ), sub );
    }   //------------- end isSubstring ------------

    ////////////////////////////////////////////////////////////////////
    //---------------- findLast( words, n, key ) ---------------------
    /**  Find the last "key" in array words[ 0,...,n-1 ].
     *      n is number of elements in the array
     *      return the index of a found element ( or -1 if not found)
     * @param words String[]  Array to search through
     * @param n int           # entries to consider
     * @param key String      key we are looking for
     * @return int            index in array of last key that matches
     *                           or -1 if not found
     */
    //////////////////////////////////////////////////////////////////
    // Algorithm:
    //    if n == 0 (remaining array has zero length) 
    //       return -1 (not found)
    //    else if last element in array equals key
    //       return n-1 (index of last element)
    //    else
    //       return result of calling search with first n-1 elements
    //////////////////////////////////////////////////////////////////
    
    public static int findLast( String[] words, int n, String key )
    {
        //------------- start findLast ------------
        //--- base case 1: array has 0 length, key isn't there, return -1
        if ( n <= 0 )
            return -1;
        
        //********** Delete or comment out the next line *****************
        //System.out.println( "**** findLast not implemented!***" );  
        
        /////////////////////////////////////////////////////////
        //--- base case 2: last element of array matches key
        /////////////////////////////////////////////////////////
        //    fill in the code here
        else if( words[ n -1 ].equals( key ) )
            return n - 1;
        //--- recursion case ----
        //////////////////////////////////////////////////////////
        // invoke find on the first n-1 elements of the array
        //    return whatever that returns.
        //////////////////////////////////////////////////////////
        //    fill in the code here
        else
            return findLast( words, n - 1 , key );
        
    }   //------------- end findLast ------------
    
    ////////////////////////////////////////////////////////////////
    //------------------- maxValue -------------------------
    /**
     * Find the max value in array list[0,...,n-1].
     *     n is number of elements in the array
     * @param list int[]
     * @param n int 
     * @return int   max value
     */
     //////////////////////////////////////////////////////////////
    // Algorithm:
    //   if there is 1 entry in the array, it must be the max
    //      return it (base case)
    //   else
    //      return the max of:
    //       -last entry in the array, and
    //       -max of the first n-1 entries in the array (recursion)
    //
    // Note: algorithm fails if the array has 0 length! That's ok.
    //////////////////////////////////////////////////////////////
    
    public static int maxValue( int[] list, int n )
    {
        //------------- start maxValue ------------
        int maxVal = Integer.MIN_VALUE;  // the initial value is irrelevant
        //--- base case: n = 1
        if( n == 1 )
            maxVal = list[ 0 ];
        else // recursion case
        {    
            //********** Delete or comment out the next line **************
            //System.out.println( "***** maxValue not implemented!***" );
            
            // find maximum of first n-1 elements ( 0 ... n-2 )
            // return maximum of that number and the last one in this range.
            maxVal = Math.max( list[n - 1], list[n - 2] );
            return Math.max( maxValue( list, n - 1 ), maxVal );
            
        }
        return maxVal;
    }   //------------- end maxValue ------------
}