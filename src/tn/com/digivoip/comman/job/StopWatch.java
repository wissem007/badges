package tn.com.digivoip.comman.job;

/** Simple StopWatch class for timing stuff.
 * <p>
 * Usage to measure an operation: <code>
 * StopWatch timer = new StopWatch();
 * <Time consuming operation>
 * System.out.println( timer ); // outputs the time between the creation of Timer and "now"
 * </code>
 * <p>
 * Usage to use the StopWatch in different methods and classes: <code>
 * public void test()
 * {
 *   StopWatch.instance().start();
 *   <time consuming operation>
 *   doOther();
 * }
 * public void doOther()
 * {
 *   <time consuming operation>
 *   System.out.println( StopWatch.instance() ); // outputs the time between the instance().start() and "now"
 * }
 * </code>
 * @author redsolo */
public class StopWatch{

	private static StopWatch	instance	= null;
	private long				startTime	= 0;
	private long				stopTime	= -1;

	/** Creates a new instance of StopWatch Starts the timing from the time the object was created */
	public StopWatch() {
		start();
	}
	/** Returns a StopWatch instance. This can be used to measure the time between different methods/classes.
	 * @return a static StopWatch instance */
	public static StopWatch instance() {
		if (StopWatch.instance == null) {
			StopWatch.instance = new StopWatch();
		}
		return StopWatch.instance;
	}
	/** Starts the watch. Resets the start time and resets the stop time as well. */
	public final void start() {
		startTime = System.currentTimeMillis();
		stopTime = -1;
	}
	/** Stops the watch.
	 * @return the time passed since the StopWatch was started. */
	public final long stop() {
		stopTime = System.currentTimeMillis();
		return (stopTime - startTime);
	}
	/** Gets the time (ms) elapsed from the start() method was run until now OR the stop() method was run. If stop() is executed then this method will return the same all the time, BUT if the stop()
	 * hasnt been executed this method returns the time (ms) elapsed from the latest start()
	 * @return the time since the StopWatch was started; or if it has been stopped, the time between start() and stop() */
	public long getTiming() {
		long time;
		if (stopTime == -1) {
			time = (System.currentTimeMillis() - startTime);
		} else {
			time = (stopTime - startTime);
		}
		return time;
	}
	/** Returns the time elapsed from the start() until now, OR until stop() was executed
	 * @return the time (ms) as a string */
	public String toString() {
		return String.valueOf(getTiming()) + " ms"; //$NON-NLS-1$
	}
}
