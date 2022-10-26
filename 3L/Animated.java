/**
 * Animated.java
 * 
 * Interface for objects that can respond to frame events generated 
 * by the FrameTimer interface. 
 * 
 * The setAnimated method allows individual instances to be frozen and
 * unfrozen by other classes.
 */
public interface Animated extends NewFrame
{
    // public void newFrame(); // from newFrame interface
    public void setAnimated( boolean onOff );
    public boolean isAnimated();
}
