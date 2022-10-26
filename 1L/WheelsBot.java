//++++++++++++++++++++++ WheelsBot.java +++++++++++++++++++++++++++++
import wheelsunh.users.*;
import java.awt.Color;

/** 
 * WheelsBot.java: A simple 2D image of a robot using wheels.
 * 
 * @author rdb
 */

public class WheelsBot
{ 
    //---------------- instance variables ------------------------------
    Ellipse    body;
    Rectangle  wheel1;
    Rectangle  wheel2;
    Rectangle  caster;
    Rectangle  head;
    Rectangle  sensor;
    Line       antenna;
    int        x, y;
    Color      bodyColor = java.awt.Color.BLUE;
    
    // ---------------------- WheelsBot() --------------------------------
    /** The constructor creates all the robot parts, and sets a default
      * location and body color (BLUE).
      */
    public WheelsBot()
    {   
        wheel1 = new Rectangle();   
        wheel1.setSize( 20, 30 );
        wheel1.setFrameColor( java.awt.Color.BLACK );
        wheel1.setFillColor( java.awt.Color.BLACK );
        
        wheel2 = new Rectangle();     
        wheel2.setSize( 20, 30 );
        wheel2.setFillColor( java.awt.Color.BLACK );
        wheel2.setFrameColor( java.awt.Color.BLACK );
        
        caster = new Rectangle();    
        caster.setSize( 16, 20 );
        caster.setFillColor( java.awt.Color.BLACK );
        caster.setFrameColor( java.awt.Color.BLACK );
        
        body = new Ellipse(); 
        body.setSize( 60, 60 );
        body.setFrameColor( java.awt.Color.BLACK );
        body.setFillColor( bodyColor );
        
        head = new Rectangle();   
        head.setSize( 50, 5 );
        head.setFillColor( java.awt.Color.GREEN );
        head.setFrameColor( java.awt.Color.BLACK );
        
        sensor = new Rectangle();  
        sensor.setSize( 10, 10 );
        sensor.setFillColor( java.awt.Color.RED );
        sensor.setFrameColor( java.awt.Color.BLACK );
        
        antenna = new Line( x + 40, y + 30 , x + 40, y + 20 );  
        antenna.setColor( java.awt.Color.BLUE );
        
        setLocation( 0, 0 );
    }
    
    // ------------------- WheelsBot( int, int ) ----------------------
    /**
     * Constructor includes an explicit location.
     * 
     * @param xLoc int x location
     * @param yLoc int y location
     */
    public WheelsBot( int xLoc, int yLoc )
    {   
        this();
        setLocation( xLoc, yLoc );
    }
    
    // ---------------------- WheelsBot( Color ) ----------------------
    /** 
     * Constructor includes explicit color and default location ( 0,0 ).
     * 
     * @param c Color  body color for the bot
     */
    public WheelsBot ( Color c )
    {   
        this();
        setLocation( 0, 0 );
        setColor( c );
    }
    
    //--------------------- setColor( Color ) ------------------------- 
    /**
     * set body color to parameter and default colors for all other parts.
     *
     * @param c Color  main color for the bot
     */
    public void setColor( Color c )
    {
        bodyColor = c;
        body.setFillColor( c );
    }
    
    //------------------- setLocation( int, int ) --------------------- 
    /**
     * set the location of the robot to the paramters.
     * 
     * @param xLoc int  x-coord of bot
     * @param yLoc int  y-coord of bot
     */
    public void setLocation( int xLoc, int yLoc )
    {
        this.x = xLoc;
        this.y = yLoc;
        
        wheel1.setLocation( x, y + 60 );
        wheel2.setLocation( x + 60, y + 60 );
        caster.setLocation( x + 32, y );
        body.setLocation( x + 10, y + 15 );
        head.setLocation( x + 15, y + 40 );
        sensor.setLocation( x + 35, y + 30 );
        antenna.setPoints( x + 40, y + 30 , x + 40, y - 20 );
    }
    
    //--------------------- int getXLocation() ------------------------
    /**
     * return the x location of the robot.
     * 
     * @return int  x coordinate
     */
    public int getXLocation()
    {
        return x;       
    }
    
    //--------------------- int getYLocation() ------------------------
    /**
     * return the y location of the robot.
     * 
     * @return int  y coordinate
     */
    public int getYLocation()
    {
        return y;    
    }
    
    //--------------------- int getColor() ----------------------------
    /**
     * return the body color of the robot.
     * 
     * @return Color  body color
     */
    public Color getColor()
    {
        return bodyColor;    
    }
    
    //------------------------  main ----------------------------------
    /**
     * Unit test for WheelsBot.
     * 
     * @param args String[]  command line arguments.
     */
    public static void main( String[] args )
    {
        new Frame();
        WheelsBot myWheelsBot = new WheelsBot();
        myWheelsBot.setColor( Color.ORANGE );
        myWheelsBot.setLocation( 200, 400 );
    }
}
