//------------------------------- CardLab -----------------------------
import javax.swing.*;
import java.awt.*;
/**
 * CardLab -- App class for experimenting with JLabels, enums and a card
 *            game tool.
 * @author rdb
 * 03/10/09
 */

public class CardLab extends JFrame
{
    //---------------------- instance variables ----------------------
    private GUIPanel _appPanel;      // the app's JPanel
    
    //--------------------------- constructor -----------------------
    /**
     * Constructor.
     * @param title String window title
     * @param cardImageSource String  not null => name of image source
     */
    public CardLab( String title, String cardImageSource )     
    {
        super( title );
        
        this.setBackground( Color.LIGHT_GRAY );
        this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        
        if ( cardImageSource != null )
        {
            Card.imageSource = cardImageSource;
            System.out.println( "**** Image Source: " + cardImageSource );
        }
        
        _appPanel = new GUIPanel();
        this.add( _appPanel );
        
        this.setSize( _appPanel.getWidth(), _appPanel.getHeight() + 100 );
        
        this.setVisible( true );
    }
    //------------------- main: convenience test ----------------------
    /**
     * Appl invocation.
     * @param args String[]  command line arguments
     */
    public static void main( String [ ] args ) 
    {
        String imageSource = null;
        if ( args.length > 0 )
            imageSource = args[ 0 ];
        new CardLab( "CardLab", imageSource );
    }
}
