//++++++++++++++++++++++++++ TreeNode ++++++++++++++++++++++++++++++++++++
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
/**
 * TreeNode class for Aces Up.
 *    The data is the current game state
 *    Node keeps track of its children, its parent,
 *    the move that caused the game to get to this state
 *    and the best move that leads to the best game state
 * 
 * Created 7/13/10 for general use by jb
 * Edited rdb March 2015 for Aces Up
 * @author jb rdb sna4
 */

public class TreeNode
{
    //------------- instance variables ----------------------------------
    ///////////////////////////////////////////////////////////////////
    // You do not need to use all or any of the instance variables defined
    // below. They are here partly to help you identify what kinds of 
    // information is needed and also to make sure that the finished toString
    // methods compile.
    /////////////////////////////////////////////////////////////////////
    private GameState           gameState;
    private Move                bestMove;
    private int                 bestChild;
    private TreeNode            parent;
    private Move                moveToThisState;
    private int                 nodeScore = 0; // best discard size for this
    
    private int                 depth;         // a debugging tool
    private int                 height;        // height of this subtree
    private int                 numNodes;      // # descendant nodes.
    private Move[]              moves;         // 
    private TreeNode[]          childNodes; // simpler for getting all children
    private ArrayList<Move>     validMoves;
    
    //+++++++++++++++++++++ constructor +++++++++++++++++++++++++++++++++++
    /**
     * Constructor. 
     * @param game state of game for that node
     * @param p TreeNode
     */
    public TreeNode( GameState game, TreeNode p ) 
    {
        setGameState( game );
        setParent( p );
        validMoves = getPosMoves();
        calc();
    }
    
    /**Method that figures out next available move.
     */
    void calc()
    {
        bestChild = -1;
        numNodes = 0;
        
        childNodes = new TreeNode[ validMoves.size() ];
        
        for( int i = 0; i < validMoves.size(); i++ ) 
        {
            childNodes[i] = child( validMoves.get( i ) );
            int curDepth = childNodes[i].getHeight() + 1;
            
            if( curDepth > height )
                height = curDepth;
            
            numNodes += childNodes[i].getIndexNodes() + 1;
            
            if( bestChild == -1 || childNodes[i].
                   getScore() > childNodes[bestChild].getScore() ) 
            {
                bestChild = i;
                bestMove = validMoves.get( i );
            }
        }
        
        if( bestChild == -1 ) 
        {
            numNodes = 0;
            height = 0;
        }
        
        if( parent == null )
            depth = 0;
        else
            depth = parent.getDepth() + 1;
    }
    
    /**Sets state of game.
     * @param game GameState
     */
    public void setGameState( GameState game ) 
    {
        this.gameState = game;
    }
    
    /**Gets state of game.
     * @return gameState
     */
    public GameState getGameState()
    {
        return gameState;
    }
    
    /**gets Valid moves from current gameState.
     * @return validMoves from current gameState
     */
    private ArrayList<Move> getPosMoves() 
    {
        ArrayList<Move> valMove = new ArrayList<Move>();
        Move disMove = getDiscard();
        
        if( disMove != null ) 
        {
            valMove.add( disMove ); 
        }
        valMove.addAll( getReplaceMove() );
        
        if( valMove.isEmpty() ) 
        {
            Stack<Card> drawStack = gameState.getDrawPile();
            if( drawStack.size() >= 4 ) 
                valMove.add( new Draw( gameState ) );
        }
        return valMove;
    }
    
    
    /**Arraylist method that returns a move. 
     * @return valid replace moves from current position
     */
    private ArrayList<Move> getReplaceMove()
    {
        ArrayList<Move> valMoves = new ArrayList<Move>();
        ArrayList< Stack<Card> > stacks = gameState.getPiles();
        
        int emptyPile = -1;
        List<Integer> fullPile = new ArrayList<Integer>();
        
        for( int i = 0; i < stacks.size(); i++ ) 
        {
            if( stacks.get( i ).empty() ) 
            {
                emptyPile = i;
            }
            else if( stacks.get( i ).size() > 1 )
            {
                fullPile.add( i );
            }
        }
        
        if( emptyPile == -1 )
            return valMoves;
        
        return valMoves;
    }
    
    /**Method that gets discarded move. 
     * @return discard moves if it is possible
     */
    public Move getDiscard() 
    {
        ArrayList< Stack<Card> > stacks = gameState.getPiles();
        int pileIndex = -1;
        
        for( int i = 0; i < stacks.size(); i++ ) 
        {
            boolean stop = false;
            for( int z = 0; z < stacks.size(); z++ ) 
            {
                if( i == z || stacks.get( i ).empty() 
                       || stacks.get( z ).empty() )
                    continue;
                if( stacks.get( i ).peek().getSuit() == 
                    stacks.get( z ).peek().getSuit() 
                       && 
                    stacks.get( i ).peek()
                       .compareTo( stacks.get( z ).peek() ) < 0 ) 
                {
                    stop = true;
                    pileIndex = i;
                    break;
                }
            }
            if( stop )
                break;
        }
        
        if( pileIndex == -1 )
            return null;
        
        Discard m = new Discard( gameState, pileIndex );
        return m;
    }
    
    /**Move to a different state.
     * @param m Move
     */
    public void setMoveState( Move m ) 
    {
        moveToThisState = m;
    }
    
    /**Gets best child. 
     * @return the child with higest score
     */
    public TreeNode getBestChild() 
    {
        if( bestChild == -1 )
            return null;
        return childNodes[ bestChild ];
    }
    
    /**Sets parent of given node.
     * @param p TreeNode
     */
    public void setParent( TreeNode p ) 
    {
        this.parent = p;
    }
    
    /**Gets parent of given node.
     * @return parent
     */
    public TreeNode getParent() 
    {
        return parent;
    }
    
    /**Gets depth. 
     * @return depth of this subtree
     */
    public int getDepth() 
    {
        return depth;
    }
    
    /**Gets height. 
     * @return depth 
     */
    public int getHeight() 
    {
        return height;
    }
    
    /**Returns index of node. 
     * @return number 
     */
    public int getIndexNodes() 
    {
        return numNodes;
    }
    
    /**moves child.
     * @param m Move
     * @return TreeNode
     */
    public TreeNode child( Move m )
    {
        TreeNode node = new TreeNode( m.moved(), this );
        node.setMoveState( m );
        return node;
    }
    
    //---------------- getDrawString() ------------------------------------
    /**
     * Returns a String representing a Draw move at this state as a set of
     *   space separate cards that are part of the draw.
     * @return String.
     */
    public String getDrawString()
    {
        String cardStr = "(";
        //////////////////////////////////////////////////////////////////
        // find the 4 top cards for the Draw pile and invoke their toString
        //   methods and concatenate the result (in left to right order)
        //   with exactly 1 blank between as in:
        //     "5H QS 2D TC"  (without the " in your string)
        //////////////////////////////////////////////////////////////////
        
        Stack<Card> drawPile = gameState.getDrawPile();
        ArrayList< Stack<Card> > piles = gameState.getPiles();
        
        for( int i = 0; i < piles.size(); i++ ) 
        {
            cardStr += drawPile.get( drawPile.size() - i - 1 );
            if( i != piles.size() - 1 )
                cardStr += " ";
            else
                cardStr += ")";
        }
        
        return cardStr;
    }
    
    //---------------- getPathString() ------------------------------------
    /**
     * Returns a String representing a path from this node to the best 
     *     leaf node. It should be a space separated list of the 2-char
     *     id's generated by the Card.toString() method.
     * @return String.
     */
    public String getPathString()
    {
        /////////////////////////////////////////////////////////
        // This string does not have parens around it and you don't
        //    have to worry about an extra trailing space.
        
        String s = "";
        
        if( bestChild != -1 )
            s += bestMove + " " 
                + childNodes[ bestChild ].getPathString() + " ";
        
        return s;        
    }
    
    
    //+++++++++++++++++++++++++++++ Private Methods +++++++++++++++++++++++   
    
    //--------------- toString( String ) ------------------
    /**
     * Recursive method that returns a string of all of the children
     * Each treenode is represented by the card moved to get to its point
     *     If it was a draw, it says draw instead
     * If there is only one child, it will display itself on the same line
     * If there is more than one child, it indents and displays the gamestate
     *    then indents again and acts like there was only one child.
     * @param indent String
     * @return String
     */
    private String toString( String indent )
    {
        ////////////////////////////////////////////////////////////////////
        // The starting code is a skeleton that if used correctly will 
        // produce the properly formatted representation of your tree that
        // can be used for efficient comparison of your solution with ours.
        //
        // Edit the pieces noted below.
        //////////////////////////////////////////////////////////////////
        String extraIndent = ". ";   // additional indent string at each level 
        String s = "";
        
        //---- You need to replace this with the correct reference
        //---- to YOUR collection of valid moves from this node.
        ArrayList<Move> moveSet = validMoves;
        //if only one move
        if ( moveSet.size() == 1 )
        {
            Move m = moveSet.get( 0 );
            
            //get the characters from the move and add it
            s += ">" + m.toString();
            
            //then recurse
            TreeNode child = child( m );
            if ( child != null )
                s += child.toString( indent );        
        }
        //if more than one move...
        else
        {
            //for each move
            for ( Move m: moveSet )
            {
                TreeNode child = child( m );
                //////////////////////////////////////////////////////////
                // Every  move in moveSet should have a child in the tree.
                //    If this is not true, tree needs to get fixed.
                //////////////////////////////////////////////////////////
                if ( child == null )
                    System.err.println( "Tree error: null child in print; " );
                else
                {
                    // parent score starts the line.
                    int score = child.getScore();
                    s += "\n" + indent + "" + score + ":";
                    s += "{" + m.toString() + "}.";
                    s += depth + " "; // add depth
                    // and add in state information and new line
                    s += gameState.toString() + "\n" + indent + extraIndent;
                    
                    //recurse with bigger indent
                    s += child.toString( indent + extraIndent );
                }
            }
        }
        return s;
    }  
    
    
    
    
    //++++++++++++++++ public methods that are complete +++++++++++++++++++
    
    //////////////////////////////////////////////////////////////////////////
    // If you use the variable names declared in start version, you should
    // not need to change any of the methods below, which gather information
    // about the state of the node.
    /////////////////////////////////////////////////////////////////////////
    //---------------------- getScore() -------------------------------------
    /**
     * Returns the score for the best move.
     * @return int
     */
    public int getScore()
    {
        if( validMoves.size() == 0 )
            nodeScore = gameState.getDiscardPile().size();
        else
            nodeScore = childNodes[bestChild].getScore();
        return nodeScore;
    }
    
    //---------------- getStatsString() ------------------------------------
    /**
     * Returns a String with stats information about the subtree rooted at this 
     *     node:  # nodes, depth of root, height of subtree, score of the node.
     * @return String.
     */
    public String getStatsString()
    {
        return "Score = " + nodeScore + "    depth = " + depth;
    }
    
    //------------------------ treeToString() --------------------------
    /**
     * Starts a recursive call that prints the string representing the tree
     *     rooted at this node.
     * @return String
     */
    public String treeToString()
    {
        return "+++++++++++++\n" + nodeScore + ": " + toString( "" );
    }
    
    //------------------------ toString() ------------------------------
    /**
     * Print information about this node only, not the tree rooted here.
     * @return String
     */
    public String toString()
    {
        return "Depth: " + depth + "  score: " + nodeScore + " #moves: "
            + validMoves.size() + " best: " + bestMove
            + "  parentMove: " + moveToThisState;
    }
}
