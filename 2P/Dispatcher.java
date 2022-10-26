import java.util.*;
import java.awt.*;

/**
 * Dispatcher.java -- Controls the activities of the EMTVehicle
 *    Resonsibilities:
 *    1. Know the current state of the vehicle: is it at home,
 *       heading to an emergency, heading for hospital, heading home.
 *    2. Tell the vehicle what to do next. Normally, this will be
 *       after it gets to its next destination. However, if the
 *       vehicle is heading home and an emergency site gets added,
 *       it should get re-directed to the site.
 *    3. It must know about or be able to get information from
 *       a. the emergency sites collection
 *       b. the hospital collection
 *       c. the timer (because it needs to know when that 
 *          vehicle needs to wait for 2 seconds. The easiest
 *          way to do this is to simply "restart()" the timer.
 *          This works because there is nothing else moving in
 *          this application except one vehicle. If that weren't
 *          the case, the timer would have to keep running and
 *          the code would have to count events in order to figure
 *          out when 2 seconds had elapsed.)
 * 
 * Framework:
 *    This class should implement the Animated interface, and get
 *    called (by EMTPanel) on every frame event. For example, 
 *    if the vehicle is home or going home and there is an entry 
 *    in the sites array, the dispatcher needs to tell the vehicle 
 *    to go to the next site in the array. There are of course 
 *    several other cases.
 * 
 * @author rdb
 * Last editted: 01/04/14 Format 
 * 
 */
public class Dispatcher implements Animated
{
    //-------------------- instance variables ---------------------
    private ArrayList<Hospital> _hospitals;
    private ArrayList<EmergencySite> _site;
    private FrameTimer _timer;
    private String hospital[];
    private JLine _line;
    private EMTVehicle amb;
    private EmergencySite site;
    private Boolean choice = true;
    private Point p;
    private int _pX = 20, _pY = 20;
    //------------------ constructor -----------------------------
    /**
     * Identify the next emergency site, if there is one.
     *
     * You can add other parameters to this method as needed.
     *
     *@param hospitals List of hospitals.
     * @param time FrameTimer
     * @param sitez List of hospitals
     * @param line JLine
     * @param ambu EMTVehicle
     */
    public Dispatcher( ArrayList<Hospital> hospitals, FrameTimer time
                     , JLine line, EMTVehicle ambu
                          , ArrayList<EmergencySite> sitez )
    {
        _hospitals = hospitals;
        // most/all of what you get will be saved in instance variables
        _timer = time;
        _line = line;
        _site = sitez;
        amb = ambu;
        p = new Point( _pX, _pY );
    }
    //---------------------- setNextSite() -------------------------
    /**
     * Identify the next emergency site, if there is one.
     *
     * @return Next emergency site.
     */
    private EmergencySite getNextSite()
    {
        EmergencySite next = null;
        //////////////////////////////////////////////////////////////
        // get next site from list of sites or from someone who has
        //    list of sites.
        //////////////////////////////////////////////////////////////
        for( int x = 0; x < _site.size(); x++ )
            next = _site.get( x );
        
        return next;
    }
    //--------------- getClosestHospital( Point ) --------------------
    /**
     * Find the closest hospital site to the parameter.
     *    This method is complete.
     * Note that we don't bother to compute the correct distance,
     *    we make the decision based on the square of the distance
     *    which saves the computation of the square root, a reasonably
     *    costly operation that serves no purpose for this test.
     * 
     * @param  loc  Point  location of emt vehicle
     * @return      Hospital closest hospital to vehicle at loc
     */
    private Hospital getClosestHospital( Point loc )
    {
        double distanceSq = Float.MAX_VALUE;
        Hospital   closest    = null;
        for ( Hospital h: _hospitals )
        {
            double d2 = loc.distanceSq( h.getLocation() );
            if ( d2 < distanceSq )
            {
                distanceSq = d2;
                closest = h;
            }
        }
        return closest;
    }
    
    //++++++++++++++++++++++ Animated interface ++++++++++++++++++++++
    private boolean _animated = true;
    //---------------------- isAnimated() ----------------------------
    /**
     * Determine whether object should be animated.
     *
     * @return Boolean indicating whether object should be 
     * animated.
     */
    public boolean isAnimated()
    {
        return _animated;
    }
    //---------------------- setAnimated( boolean ) -----------------
    /**
     * Set animated state for this object.
     *
     * @param onOff  New animated setting.
     */
    public void setAnimated( boolean onOff )
    {
        _animated = onOff;
    }
    //---------------------- newFrame ------------------------------
    /**
     * invoked for each frame of animation; figure out what to do
     * with the vehicle at this frame.
     */
    public void newFrame()
    {
        //////////////////////////////////////////////////////////////
        // If vehicle is moving, update its position (by calling its 
        //    newFrame method. 
        // Somehow, need to know if it has reached its goal position. 
        //    If so, figure out what the next goal should be.
        //
        //    If previous goal was emergency site, new goal is hospital
        //
        //    If previous goal was a hospital (or if it was at home,
        //       or if it was going home), new goal is the next
        //       emergency site, if there is one, or home if no more 
        //       emergencies.
        //////////////////////////////////////////////////////////////
        amb.newFrame();
        
        if ( choice == true )
        {
            amb.newFrame();
        }
        
        else if ( amb.isAnimated() == false )
        {
            p = new Point( (int)amb.getBounds().
                               getX(), ( int )amb.getBounds().getY() );
            if( choice == true )
            {
                amb.travelTo( getClosestHospital( p ).getLocation().x,
                                 getClosestHospital( p ).
                                     getLocation().y, EMTApp
                                     .highSpeed );
                amb.repaint();
                choice = false;
            }
            else
            {
                if( _site.size() == 0 )
                {
                    amb.travelTo( 30, 30, EMTApp.normalSpeed );
                    amb.repaint();
                    
                }
                else if( _site.size() > 0 )
                {
                    
                    _site.remove( 0 );
                    if( _site.size() == 0 )
                    {
                        amb.travelTo( _pX, _pY, EMTApp.normalSpeed );
                        amb.repaint( );
                    }
                    
                    else
                    {
                        amb.travelTo( ( int )_site.get( 0 ).
                                             getBounds().getX(),
                                         ( int )_site.get( 0 ).
                                             getBounds().getY(), 
                                         EMTApp.highSpeed );
                        if( _site.get( 0 ).getX() == amb.getX() 
                               && _site.get( 0 ).getX() == amb.getX() )
                        {  
                            _site.get( 0 );
                            _site.remove( 0 );
                            choice = true;
                        }
                        
                        if( _timer != null )
                        {
                            _timer.restart();
                        }
                        else if( amb.getLocation( ) == 
                                _site.get( 0 ).getLocation( ) ) 
                        { 
                            _site.get( 0 );
                            
                            _timer.restart( );
                        }
                        
                        amb.repaint();
                        
                    }
                }
            }
        }
    }
    
    //+++++++++++++++ end Animated interface +++++++++++++++++++++++++
}