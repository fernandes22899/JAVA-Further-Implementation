/*
import java.util.ArrayList;
import java.util.Stack;
/**
 * ReplaceMove. 
 * @author Abdulla 
 * 
public class ReplaceMove implements Move
{
    private Card card = null;
    private GameState gameState;
    private int pileIndex;
    private int emptyPileIndex;
    /**
     * ReplaceMove. 
     * @param gameState GameState
     * @param pileIndex int 
     * @param emptyPileIndex int 
     * 
     * 
    public ReplaceMove( GameState gameState, 
                       int pileIndex, int emptyPileIndex ) 
    {
        this.gameState = gameState;
        this.pileIndex = pileIndex;
        this.emptyPileIndex = emptyPileIndex;
    }
    /**
     * GameState. 
     * @return newGameState
     * 
    public GameState movedState() 
    {
        ArrayList< Stack<Card> > piles = gameState.getPiles();
        
        if( !piles.get( emptyPileIndex ).empty() ) 
            throw new RuntimeException( "Pile " 
                                           + emptyPileIndex 
                                           + " must be empty to replace card" );
        
        if( piles.get( pileIndex ).empty() )
            throw new RuntimeException( 
                                       "Pile " 
                                           + 
                                       emptyPileIndex 
                                           + 
                                       "mustnot beempty to move card from it" );
        
        card = piles.get( pileIndex ).peek();
        piles.get( emptyPileIndex ).push( piles.get( pileIndex ).pop() );
        
        GameState newGameState = new GameState( 
                                               gameState.getDrawPile(), 
                                               piles, 
                                               gameState.getDiscardPile() );
        return newGameState;
    }
    
}
*/