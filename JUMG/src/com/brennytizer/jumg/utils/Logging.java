package com.brennytizer.jumg.utils;

public class Logging {
	public static boolean shouldLog = false;
	
	public static void log(LoggingSpice spice, String message) {
		if(shouldLog) System.out.println("[" + spice.getWarning() + "] > " + message);
		if(spice == LoggingSpice.DEADLY) {
			System.exit(1);
		}
	}
	
	public static enum LoggingSpice {
		MILD     ("information     "),
		MEDIUM   ("should note     "),
		HOT      ("uhh.. problem   "),
		EXTRA_HOT("something bad   "),
		DEADLY   ("ABORTING MISSION");
		
		private String warning;
		
		LoggingSpice(String warning) {
			this.warning = warning;
		}
		
		public String getWarning() {
			return warning;
		}
	}
}