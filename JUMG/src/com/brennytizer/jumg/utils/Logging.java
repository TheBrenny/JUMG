package com.brennytizer.jumg.utils;

import java.util.Calendar;

/**
 * This class takes care of printing to the console... pretty much it.
 * 
 * @author Jarod Brennfleck
 */
public class Logging {
	public static LoggingSpice loggingLevel = LoggingSpice.MILD;
	public static boolean shouldLog = false;
	
	/**
	 * Sets the flag for whether or not logging should be enabled.
	 * 
	 * @param flag
	 *        - Whether or not to turn logging on or off.
	 */
	public static void setLogging(boolean flag) {
		if(!flag) log(LoggingSpice.MILD, "Turning off logging...");
		Logging.shouldLog = flag;
		if(flag) log(LoggingSpice.MILD, "Turning on logging...");
	}
	
	public static void setLoggingLevel(LoggingSpice ls) {
		Logging.loggingLevel = LoggingSpice.MILD;
		log(LoggingSpice.MILD, "Setting logging level to: " + ls.name());
		Logging.loggingLevel = ls;
	}
	
	public static void printStackTrace() {
		printStackTrace(10);
	}
	public static void printStackTrace(int count) {
		if(!shouldLog) return;
		log(LoggingSpice.MILD, "Start stack trace of " + count + " counts.");
		for(int i = count - 1; i >= 0; i--) {
			StackTraceElement ste = Thread.currentThread().getStackTrace()[i];
			System.out.println("  [" + i + "]:\t" + Words.padTo(ste.getClassName(), 20, " ", true) + "." + Words.padTo(ste.getMethodName(), 20, " ", true));
		}
		log(LoggingSpice.MILD, "Stack trace printing finished.");
	}
	
	/**
	 * Logs a message to the console.
	 * 
	 * @param spice
	 *        - Is the message bad?
	 * @param message
	 *        - What you want the message to say.
	 */
	public static void log(LoggingSpice spice, String message) {
		if(shouldLog) {
			StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
			String className = ste.getClassName();
			String[] asd = className.split("\\.");
			className = asd[asd.length - 1];
			className = Words.padTo(className, 15, " ", true);
			String methodName = ste.getMethodName();
			String info = "[" + Words.padTo(Thread.currentThread().getName(), 7, " ", true) + ":" + className.trim() + "." + methodName;
			info = Words.padTo(info, 20, " ", true);
			System.out.println(info + "]-[" + spice.getWarning() + "] > " + message);
		}
		if(spice == LoggingSpice.DEADLY) {
			System.exit(1);
		}
	}
	
	public static String getLoggingTimeTrace() {
		Calendar c = Calendar.getInstance();
		String year = (c.get(Calendar.YEAR) + "").substring(2);
		String month = Words.padTo(c.get(Calendar.MONTH) + 1 + "", 2, "0", false);
		String day = Words.padTo(c.get(Calendar.DAY_OF_MONTH) + "", 2, "0", false);
		String hour = Words.padTo(c.get(Calendar.HOUR_OF_DAY) + "", 2, "0", false);
		String minute = Words.padTo(c.get(Calendar.MINUTE) + "", 2, "0", false);
		String second = Words.padTo(c.get(Calendar.SECOND) + "", 2, "0", false);
		String millisecond = Words.padTo(c.get(Calendar.MILLISECOND) + "", 3, "0", false).substring(0, 2);
		return Words.insert("{0}-{1}-{2} {3}:{4}:{5}:{6}", year, month, day, hour, minute, second, millisecond);
	}
	
	/**
	 * The different categories of spiciness for logging different bits of
	 * information.
	 * 
	 * @author Jarod Brennfleck
	 */
	public static enum LoggingSpice {
		MILD("information     "),
		MEDIUM("should note     "),
		HOT("uhh.. problem   "),
		EXTRA_HOT("something bad   "),
		DEADLY("ABORTING MISSION");
		private String warning;
		
		LoggingSpice(String warning) {
			this.warning = warning;
		}
		
		/**
		 * Returns the warning message of this spice.
		 */
		public String getWarning() {
			return warning;
		}
	}
}