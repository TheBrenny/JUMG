package com.brennytizer.jumg.utils.fileio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.brennytizer.jumg.utils.Logging;
import com.brennytizer.jumg.utils.Logging.LoggingSpice;
import com.brennytizer.jumg.utils.fileio.compression.BrennyPress;

/**
 * A class that contains two subclasses that are dedicated to one form of IO
 * each.
 * 
 * @author Jarod Brennfleck
 */
public class FileIO {
	public static String userHome = System.getProperty("user.home");
	public static String keySpace = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.=";
	public static boolean compressData = true;
	public boolean performingAction = false;
	
	/**
	 * Creates a file writer and returns it based off the fileLocation you give.
	 * 
	 * @param fileLocation
	 *        - What file to write to.
	 * @return The new {@link FileOutput} object created.
	 * @see #startFileReader(String)
	 */
	public static FileOutput startFileWriter(String fileLocation) {
		Logging.log(LoggingSpice.MILD, ""); // TODO: WRITE LOGS FOR ALL ACTIONS AND STUFF.
		FileOutput fo = new FileOutput(new File(fileLocation));
		return fo;
	}
	
	/**
	 * Creates a file reader and returns it based off the fileLocation you give.
	 * 
	 * @param fileLocation
	 *        - What file to write to.
	 * @return The new {@link FileInput} object created.
	 * @see #startFileWriter(String)
	 */
	public static FileInput startFileReader(String fileLocation) {
		FileInput fi = new FileInput(new File(fileLocation));
		return fi;
	}
	
	/**
	 * Sets whether the data is compressed or not.
	 * 
	 * @param compress
	 *        - The flag indicating whether the IO is to use the compression
	 *        algorithm.
	 */
	public static void setCompression(boolean compress) {
		FileIO.compressData = compress;
	}
	
	/**
	 * A class that is dedicated to writing output data to a file.
	 * 
	 * @author Jarod Brennfleck
	 */
	public static class FileOutput extends FileIO {
		public File file;
		public ArrayList<String> data;
		
		/**
		 * Constructs a new FileOutput object.
		 * 
		 * @param file
		 *        - The file to write to.
		 */
		public FileOutput(File file) {
			this.file = file;
			this.data = new ArrayList<String>();
		}
		
		/**
		 * Writes the data to the file.
		 */
		public void writeData() {
			if(performingAction) return;
			performingAction = true;
			Logging.log(LoggingSpice.MILD, "Preparing to write data...");
			BufferedWriter bw = null;
			try {
				bw = new BufferedWriter(new FileWriter(file));
				for(String line : data) {
					line = compressData ? BrennyPress.convert(line, true) : line;
					Logging.log(LoggingSpice.MILD, "  Writing line: " + line);
					bw.write(line);
					bw.newLine();
				}
				Logging.log(LoggingSpice.MILD, "Done. Flushing. Closing connection to file.");
				bw.flush();
				bw.close();
			} catch(IOException e) {
				e.printStackTrace();
				Logging.log(LoggingSpice.HOT, "Oh no! Something wen wrong when writing to: " + file.getName());
			}
			Logging.log(LoggingSpice.MILD, "Finished writing.");
			performingAction = false;
		}
		
		/**
		 * Appends the given data line to the current data.
		 * 
		 * @param data
		 *        - The data to append.
		 * @see #appendData(ArrayList)
		 */
		public void appendData(String data) {
			if(performingAction) return;
			Logging.log(LoggingSpice.MILD, "  Appending data: " + data);
			this.data.add(data);
		}
		
		/**
		 * Appends the given data list to the current data.
		 * 
		 * @param data
		 *        - The data to append.
		 * @see #appendData(String)
		 */
		public void appendData(ArrayList<String> data) {
			if(performingAction) return;
			for(String line : data) {
				appendData(line);
			}
		}
	}
	
	/**
	 * A class this is dedicated to reading input data from a file.
	 * 
	 * @author Jarod Brennfleck
	 */
	public static class FileInput extends FileIO {
		public File file;
		public ArrayList<String> data;
		
		/**
		 * Constructs a new FileInput object.
		 * 
		 * @param file
		 *        - The file to read from.
		 */
		public FileInput(File file) {
			this.file = file;
			this.data = new ArrayList<String>();
		}
		
		/**
		 * Reads the data from the file.
		 */
		public void readData() {
			if(performingAction) return;
			performingAction = true;
			Logging.log(LoggingSpice.MILD, "Preparing to read data...");
			BufferedReader br = null;
			try {
				br = new BufferedReader(new FileReader(file));
				String line = "";
				while((line = br.readLine()) != null) {
					data.add(line = (compressData ? BrennyPress.convert(line, false) : line));
					Logging.log(LoggingSpice.MILD, "  Read line: " + line);
				}
				Logging.log(LoggingSpice.MILD, "Closing connection to file.");
				br.close();
			} catch(IOException e) {
				e.printStackTrace();
				Logging.log(LoggingSpice.HOT, "Oh no! Something wen wrong when reading from: " + file.getName());
			}
			Logging.log(LoggingSpice.MILD, "Finished reading.");
			performingAction = false;
		}
		
		/**
		 * Returns the data that has been retrieved from the file. Only
		 * available when the data is not being read.
		 */
		public ArrayList<String> getData() {
			if(performingAction) return null;
			return data;
		}
	}
	
	static {
		BrennyPress.changeKeySpace(keySpace);
	}
}