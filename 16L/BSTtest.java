/**
 * A class to test the BinarySearchTree class
 * 
 * @author rdb
 * 03/28/11
 */
import java.util.*;

public class BSTtest
{
   //---------------------- class variables -------------------------------
   static int exceptionCount = 0;
   
   //------------------------batchTest() -----------------------------------
   /**
    * This method provides some basic tests for BinarySearchTree, focusing on
    * the remove functionality. Because its common for bugs to introduce 
    * infinite loops, each execution only tests a few cases. The tests are
    * controlled by command line parameters. There are 7 cases:
    *   
    *   R-l root with no left child
    *   Rl  root with left but no right
    *   Rlr root with 2 children
    *   l-l left child with no left
    *   ll  left child with left
    *   r-r right child with no right
    *   rr  right child with right
    * 
    * 0 or more of these cases can be included as command line args
    * If no args, all tests are run, otherwise, just the tests 
    */
   public static void main( String[] args )
   {
      if ( args.length > 0 )
         System.out.println( "|" + args[ 0 ] + "|" );
      if ( args.length == 0 ) // test all at once
       {
         testAllAtOnce();
         if ( exceptionCount > 0 )
         {
            System.out.println( "========= errors in testAll; testing parts " );
            testInParts();
         }
      }
      else if ( args[ 0 ].equals( "a" ))  //  testAllAtOnce
         testAllAtOnce();
      else if ( args[ 0 ].equals( "p" ))  // test all in parts
         testInParts();
      else     // specific tests are requested
        tester( args );
   }
   //----------------------- tester( String[] ) ---------------------------
   /**
    * Make tests specified on command line
    */
   private static void tester( String[] args )
   {
      // string position  0   4   8   12  16  20  24
      // test index       0   1   2   3   4   5   6
      String testCodes = "R-l.Rl..Rlr.l-l.ll..r-r.rr..not.e..."; 
      for ( int t = 0; t < args.length; t++ )
      {
         String testCode = args[ t ];
         int pos = testCodes.indexOf( testCode );
         if ( pos < 0 )
            System.out.println( "Invalid test code:  " + testCode );
         else
         {
            pos = pos / 4;   // yields 
            switch ( pos )
            {
               case 0:  // R-l  Root without a left child
                  rootNoLeft();
                  break;
               case 1:  // Rl   Root with a left child, but no right child
                  rootWithLeft();
                  break;
               case 2:  // Rlr  Root with 2 children
                  rootWithBoth();
                  break;
               case 3:  // l-l  Interior left child, without a left child
                  leftNoLeft();
                  break;
               case 4:  // ll   Interior left child, with a left child
                  leftWithLeft();
                  break;
               case 5:  // r-r  Interior right child, without a right child
                  rightNoRight();
                  break;
               case 6:  // rr   Interior right child, with a right child
                  rightWithRight();
                  break;
               case 7:  // bad  Delete a node that isn't there
                  keyNotInTree();
                  break;
               case 8:  // e    Delete from empty tree
                  emptyTree();
                  break;
            }
         }
      }
   }
   //----------------------- testAllAtOnce() ------------------------------
   /**
    * From experience, we know that the following generated tree will test
    * all cases.
    */
   private static void testAllAtOnce()
   {
      String[] rmKeys = { "bad", "no", "gd", "un", "wb", "hm", "aw", "re", 
         "pv", "sg", "fp", "gk", "kh", "ka", "ne", "bad" };
      BinarySearchTree bst = makeTree( 14, 2 );
      System.out.println( "---------------- Initial tree: ---------------\n"
                            + bst.inOrderString() + "\n----\n" + bst );
      
      for ( int r = 0; r < rmKeys.length; r++ )
      {
         remove( bst, rmKeys[ r ], null );
         checkParents( bst.root() );
      }
    }
   //----------------------- testInParts() ---------------------------
   /**
    * Make tests one at a time, by calling individual tests.
    */
   private static void testInParts( )
   {
      System.out.println( "----------------------------------------------" );
      System.out.println( " Testing all cases, one at a time" );
      try { rootNoLeft(); } catch ( Exception ex ) 
      { handleException( "rootNoLeft", ex ); }
      try { rootWithLeft(); } catch ( Exception ex ) 
      { handleException( "rootWithLeft", ex ); }
      try { rootWithBoth(); } catch ( Exception ex ) 
      { handleException( "rootWithBoth", ex ); }
      try { leftNoLeft(); } catch ( Exception ex ) 
      { handleException( "leftNoLeft", ex ); }
      try { leftWithLeft(); } catch ( Exception ex ) 
      { handleException( "leftWithLeft", ex ); }
      try { rightNoRight(); } catch ( Exception ex ) 
      { handleException( "rightNoRight", ex ); }
      try { rightWithRight(); } catch ( Exception ex ) 
      { handleException( "rightWithRight", ex ); }
      try { keyNotInTree(); } catch ( Exception ex ) 
      { handleException( "keyNotInTree", ex ); }
      try { emptyTree(); } catch ( Exception ex ) 
      { handleException( "emptyTree", ex ); }
   }
   //------------------- handleException -------------------------------
   private static void handleException( String test, Exception ex )
   {
       System.err.println( "*** ERROR: " + ex.getClass().getName() ); 
       ex.printStackTrace();
   }
   //--------------------- rootNoLeft() --------------------------------
   private static void rootNoLeft()   // R-l
   {
      String title = "delete root with NO left child ";
      String[] keys = { "m", "q", "o", "s" };
      BinarySearchTree bst = makeTree( keys );
      remove( bst, "m", title );
      checkParents( bst.root() );
   }
   //--------------------- rootWithLeft() --------------------------------
   private static void rootWithLeft() // Rl
   {
      String title =  "delete root WITH a left child, no right child ";
      String[] keys = { "m", "e", "c", "g" };
      BinarySearchTree bst = makeTree( keys );
      remove( bst, "m", title );
      checkParents( bst.root() );
   }
   //--------------------- rootWithBoth() --------------------------------
   private static void rootWithBoth()  // Rlr
   {
      String title =  "delete root with BOTH children ";
      String[] keys = { "m", "e", "c", "g", "q", "o", "s" };
      BinarySearchTree bst = makeTree( keys );
      remove( bst, "m", title );
      checkParents( bst.root() );
   }
   //--------------------- leftNoLeft() --------------------------------
   private static void leftNoLeft()       // l-l
   {
      String title =  "delete a left with NO left child ";
      String[] keys = { "m", "e", "c", "g", "d", "q" };
      BinarySearchTree bst = makeTree( keys );
      remove( bst, "c", title );
      checkParents( bst.root() );
   }
   //--------------------- leftWithLeft() --------------------------------
   private static void leftWithLeft()    // ll
   {
      String title =  "delete a left WITH a left child ";
      String[] keys = { "m", "e", "c", "a", "g", "d", "q" };
      BinarySearchTree bst = makeTree( keys );
      remove( bst, "c", title );
      checkParents( bst.root() );
   }
   //--------------------- rightNoRight() --------------------------------
   private static void rightNoRight()    // r-r
   {
      String title =  "delete a right with NO right child ";
      String[] keys = { "m", "e", "q", "n", "u", "s", "r", "t" };
      BinarySearchTree bst = makeTree( keys );
      remove( bst, "u", title );
      checkParents( bst.root() );
   }
   //--------------------- rightWithRight() --------------------------------
   private static void rightWithRight()     // rr 
   {
      String title =  "delete a right WITH a right child ";
      String[] keys = { "m", "e", "q", "n", "s", "u", "r", "t" };
      BinarySearchTree bst = makeTree( keys );
      remove( bst, "s", title );
      checkParents( bst.root() );
   }
   //--------------------- keyNotInTree() --------------------------------
   private static void keyNotInTree()     // rr 
   {
      String title =  "search for a key NOT in the tree ";
      String[] keys = { "m", "e", "q", "n", "s", "u", "r", "t" };
      BinarySearchTree bst = makeTree( keys );
      remove( bst, "x", title );
      checkParents( bst.root() );
   }
   //--------------------- emptyTree() --------------------------------
   private static void emptyTree()     // rr 
   {
      String title =  "search for a key in an empty tree ";
      BinarySearchTree bst = new BinarySearchTree();
      remove( bst, "x", title );
      checkParents( bst.root() );
   }
   //------------------- remove( tree, key, title ) --------------------------
   private static void remove( BinarySearchTree bst, String key, String title )
   {
      if ( title != null )
      {
         System.out.println( "---------------- " + title + " ---------------" );
         System.out.println( bst.inOrderString() + "\n---\n" + bst );
      }
      System.out.println( "---- Removing: " + key );
      try {
         Data d = bst.delete( key );
         if ( d == null )
            System.out.println( "********** Not found " );
         else
            System.out.println( bst.inOrderString() + "\n---\n" + bst );
      }
      catch ( Exception ex )
      {
         System.out.println( "A test failed: " + ex.getClass().getName() 
                               + ": " + ex.getMessage() );
         ex.printStackTrace();
         System.out.println( "+++++++++++++++++++++++++++++++++++++++++++++" );
         exceptionCount++;
      }
   }
   //---------------------- checkParents( Node ) ------------------------------
   /**
    * check that all parent links are correct
    */
   private static void checkParents( BinarySearchTree.Node node )
   {
      if ( node == null )
         return;
      if ( node.left != null && node.left.parent != node )
         parentError( "left", node, node.left.parent );
      else if ( node.right != null && node.right.parent != node )
         parentError( "right", node, node.right.parent );
   }
   //---------------------- parentError( String, Node, Node ) --------
   private static void parentError( String side, 
                                         BinarySearchTree.Node parent, 
                                         BinarySearchTree.Node bad )
   {
      String badRef = "null";
      if ( bad != null )
         badRef = bad.data.toString();
      System.out.println( "****Parental ERROR: " + side + " child of " + 
                          parent.data + " has parent: " + badRef );
   }
      
   //---------------------- makeTree( int, int ) -----------------------------
   /**
    * generate a new DataList and tree
    */
   private static BinarySearchTree makeTree( int size, int seed )
   {  
      ArrayList<Data> list = TreeApp.generateData( size, seed );
      
      BinarySearchTree bst = new BinarySearchTree();
      
      Iterator<Data> iter = list.iterator();
      while ( iter.hasNext() )
         bst.add( iter.next() );
    
      return bst;
   }
   //---------------------- makeTree( String[] ) -----------------------------
   /**
    * make a tree from an array of String
    */
   private static BinarySearchTree makeTree( String[] keys )
   {  
      BinarySearchTree bst = new BinarySearchTree();
      
      for ( int k = 0; k < keys.length; k++ )
         bst.add( new Data( keys[ k ], "xxx", k ));
    
      return bst;
   }
}
