import java.awt.Point;

/**
 * Line
 * We use the parametric form of a line to represent any point on
 * the line. If the line goes through p0 and p1, then
 *   p(t) = p0  + t * ( p1 - p0 )
 * is a point on the infinite line for any value of t
 *
 * This representation is based on p0 being one endpoint and (p1-p0)
 *     being the vector from p0 to p1, or the "direction" of the line
 *  Values of t between 0 and 1 are all the points of the line segment
 *   between p0 and p1.
 * The above equation is shorthand for 2 equations, one in x and one in y:
 *   x(t) = x1 + t*(x2 - x1)
 *   y(t) = y1 + t*(y2 - y1)  for t between 0 and 1
 * The t parameter to this method specifies a specific point we want
 *   to find: ( xt, yt ) which is the subdivision point for this line

 * It's also useful to use the other implicit representation:
 *     Ax + By + C = 0 
 * where 
 *   A = m, B = -1 and C = b
 * The vector (A,B) is perpendicular to the line; we want a line
 *   that is perpendicular to this line that starts at (xt, yt)
 * The line through (xt,yt) perpendicular to this edge is parallel
 *   to the vector (A,B). 
 * If we find the unit vector (nx,ny) in the same direction as (A,B),
 * we can represent any point on that line by
 *   (x,y) = (xt,yt) + t (nx,ny) for some t (different from the param)
 * 
 * We can represent any line with the slope-intercept form:
 *      y = mx + b 
 * where 
 *   m = (y2 - y1) / (x2 -x1)
 *   b = y1 -  mx1
 * If we want the point (x3,y3) that is h units from (xt,yt), then that
 * point is 
 *      (x3,y3) = (xt,yt) + h*(nx,ny)
 *
 * The Normals to a vector in the direction ( dx, dy ) is just the vectors
 *     ( dy, -dx ) and ( -dy, dx ).
 * By convention we'll choose the direction that represents a 90 degree 
 * rotation in the counter clockwise direction. i.e. maps the x axis towards
 * the y-axis. That is
 *     ( -dy, dx )
 * 
 * @author rdb
 * 09/17/10
 */
public class Line
{
    //------------------------- instance variables ------------------------
    Point   p0;
    Point   p1;
    //--- Some useful values
    private double  _m;         // slope of the line
    private double  _a, _b, _c; // coefficients of the line equation
    private int     _dx;        // p1.x - p0.x
    private int     _dy;        // p1.y - p0.y
    private double  _nx, _ny;   // unit vector perpendicular to line
   
    //------------------------- constructors ------------------------------
    /**
     * Constructor with 2 Point objects.
     *
     * @param pt0 First endpoint.
     * @param pt1 Second endpoint.
     *
     */
    public Line( Point pt0, Point pt1 )
    {
        p0 = pt0;
        p1 = pt1;
      
        // compute useful values
        _dx = p1.x - p0.x;
        _dy = p1.y - p0.y;
      
        //  Want to compute m = dy/dx, but what if p0.x = p1.x?
        //    the slope, m, is infinity. We test for that case
        //  It is just the implicit representation:
        //      x = b
        //  or
        //     1*x + 0*y - b = 0
        // 
        //  Handle that case separately
        if ( _dx == 0 ) 
        {
            _a = 1;
            _b = 0;
            _c = -p0.x;
        }
        else
        {
            _m = (double) _dy / (double) _dx; 
            _a = _m;
            _b = -1;
            _c = p0.y - _m * p0.x;
        }
        // compute the unit norml
        double len = Math.sqrt( _dx * _dx + _dy * _dy );
        _nx = -_dy / len;
        _ny = _dx / len;
    }
    //---------------- getPointAt( float t ) -----------------------------
    /**
     * Get a single point on the line.
     *
     * @param t Distance along line from p0.
     *
     * @return a Point on the line at parametric value, t.
     */
    public Point getPointAt( double t )
    {
        double xt = p0.x + t * ( _dx );
        double yt = p0.y + t * ( _dy );
        return new Point( (int)Math.round( xt ), (int)Math.round( yt ) );
    }
    //---------------- getPointOnNormalAt( double t, int h ) --------------
    /**
     * get the line of height, h, that is perpendicular to the point on the
     *   line at parametric value, t.
     *
     * @param t Distance along line from p0.
     * @param h Height.
     *
     * @return a Point on the line perpendicular to this one.
     */
    public Point getPointOnNormalAt( double t, double h )
    {      
        Point newP1 = getPointAt( t );
     
        // We now need to find the other end of the line perpendicular to 
        //   this line with length "height", another parameter

        // find the new point
        double x3 = newP1.x + h * _nx;
        double y3 = newP1.y + h * _ny;  
      
        return new Point( (int)x3, (int)y3 );
    }
    //------------------ length() ----------------------------------------
    /**
     * Length of the line.
     *
     * @return the length of the line as a float
     */
    public double length()
    { 
        double dx = p0.x - p1.x;
        double dy = p0.y - p1.y;
        return  Math.sqrt( dx * dx + dy * dy );
    }
    //------------------ toString() --------------------------------------
    /**
     * toString -- converts object to String.
     * @return String representation.
     */
    public String toString()
    {
        return "[ " + p0 + ", " + p1 + " ]";
    }
    //--------------------- main unit test -------------------------------
    /**
     * Main method.
     *
     * @param args Array of arguments.
     *
     */
    public static void  main( String[] args )
    {
        Point p0 = new Point( 0, 0 );
        Point p1 = new Point( 0, 100 );
        Point p3 = new Point( 100, 100 );
        Point p4 = new Point( 100, 0 );

        testLine( "Horizontal line  ", p0, p4, 0.5f, 50 );
        testLine( "Reverse Horizon  ", p4, p0, 0.5f, 50 );
        testLine( "Vertical   line  ", p0, p1, 0.5f, 50 );
        testLine( "Reverse Vertical ", p1, p0, 0.5f, 50 );
        testLine( "DownRight Diagonal ", p0, p3, 0.5f, 70 );
        testLine( "Reverse DownRight  ", p3, p0, 0.5f, 70 );
        testLine( "Up Left Diagonal   ", p1, p4, 0.5f, 70 );
        testLine( "Reverse Up Left    ", p4, p1, 0.5f, 70 );
                        
    }
    //----------------- testLine ------------------------------
    /**
     * Test basic Line methods.
     *
     * @param title Window title.
     * @param p0 First endpoint.
     * @param p1 Second endpoint.
     * @param t Distance along line from p0.
     * @param len Length of perpendicular line.
     */
    public static void testLine( String title, Point p0, Point p1, 
                                 float t, int len )
    {
        Line l = new Line( p0, p1 );
        System.out.println( title + " line: " + l );
      
        Point p = l.getPointOnNormalAt( t, len );
        System.out.println( "Point on Normal @ " + t + " len: " +
                            len + " is " + p );
        System.out.println( "-------------------------------------" );
    }
}
