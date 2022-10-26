/** 
 * JSnowMan.java:
 * 
 * Displays a simple snow man using the A-wrappers for awt implementation.
 * This class is also an AShape since it implements the AShape interface.
 * 
 * @author rdb
 * Created 9/11/07; derived from cs415 demo program, Start.java 
 * Last modified: rdb 01/22/13 to make JSnowMan an AShape and to use 
 *                an ArrayList
 */
import java.awt.*;
import java.util.*;
import javax.swing.*;

public class JSnowMan extends JComponent
{
    //---------------- instance variables ------------------------------
    // Components need to be accessed when displaying, put them into a list
    private ArrayList<AShape> aObjects;
    
    // -----------------------------------------------------------------
    /** 
     * Constructor for the JSnowMan class. Arguments are the position.
     */
    public JSnowMan( int x, int y )
    {
        // create the list to hold all the A-components of the JSnowMan
        aObjects = new ArrayList<AShape>();
        
        // local variables define location/sizes of components
        int    hatX   = 38;
        int    hatY   = 0;
        int    hatW   = 25;
        int    hatH   = 13;
        
        int    headX     = 25;
        int    headY     = hatY + hatH;
        int    headSize  = 50;
        
        int    bodyX     = 10;
        int    bodyY     = headY + headSize - 10;
        int    bodySize  = 80; 
        
        int    mouthX    = 40;
        int    mouthY    = headY + 30;
        
        int[]  smileX     = { 0, 5, 15, 20 };
        int[]  smileY     = { 0, 2, 2, 0 };
        
        int    leftEyeX  = 40;
        int    leftEyeY  = headY + 15;
        int    rightEyeX = 55;
        int    rightEyeY = leftEyeY;
        int    eyeSize   = 4;
        
        int    brimX  = headX + 5;
        int    brimY  = headY;
        int    brimW = headSize - 10;
        int    brimH = 4;
        
        int    lArmX1 =  0;
        int    lArmY1 = leftEyeY;
        int    lArmX2 = bodyX + 5;
        int    lArmY2 = bodyY + 20;
        
        int    rArmX1 = bodyX + bodySize - 5;
        int    rArmY1 = lArmY2;
        int    rArmX2 = bodyX + bodySize + 40;
        int    rArmY2 = mouthY + 14;
        
        
        
        // create the two eyes
        JEllipse leftEye = new JEllipse(  leftEyeX,  leftEyeY );
        leftEye.setColor( Color.BLACK );
        leftEye.setSize( eyeSize, eyeSize );
        this.add( leftEye );
        
        JEllipse rightEye = new JEllipse(  rightEyeX,  rightEyeY );
        rightEye.setColor( Color.BLACK );
        rightEye.setSize( eyeSize, eyeSize );
        this.add( rightEye );
        
        // create a smile
        JLine smilePart;
        for ( int i = 0; i < smileX.length - 1; i++ )
        {
            smilePart = new JLine( Color.BLACK );
            smilePart.setPoints(   mouthX + smileX[ i ], 
                                 mouthY + smileY[ i ], 
                                 mouthX + smileX[ i + 1 ], 
                                 mouthY + smileY[ i + 1 ] );
            smilePart.setLineWidth( 2 );
            this.add( smilePart );
        }
        
        // create the hat and its brim
        JRectangle hatBody = new JRectangle(  hatX,  hatY );
        hatBody.setSize( hatW, hatH );
        hatBody.setColor( Color.BLACK );
        this.add( hatBody );
        
        JRectangle hatBrim = new JRectangle(  brimX,  brimY );
        hatBrim.setSize( brimW, brimH );
        hatBrim.setColor( Color.BLACK );
        this.add( hatBrim );
        
        // create the head
        JEllipse head = new JEllipse(  headX,  headY );
        head.setSize( headSize, headSize );
        head.setFillColor( new Color( 255, 255, 255, 128 ) );
        this.add( head );
        
        // create the body
        JEllipse body = new JEllipse(  bodyX,  bodyY );
        body.setSize( bodySize, bodySize );
        body.setFillColor( Color.WHITE );
        this.add( body );
        
                
        // create some arms
        JLine leftArm = new JLine( Color.BLACK );
        leftArm.setPoints(  lArmX1,  lArmY1, 
                           lArmX2,  lArmY2 );
        leftArm.setLineWidth( 3 );
        this.add( leftArm );
        
        JLine rightArm = new JLine( Color.BLACK  );
        rightArm.setPoints(  rArmX1,  rArmY1, 
                            rArmX2,  rArmY2 );
        rightArm.setLineWidth( 3 );
        this.add( rightArm );
        
        setSize( rArmX2, bodyY + bodySize );
        setLocation( x, y );
    }
    
}//End of Class JSnowMan
