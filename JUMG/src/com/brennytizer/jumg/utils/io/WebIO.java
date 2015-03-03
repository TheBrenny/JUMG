package com.brennytizer.jumg.utils.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import com.brennytizer.jumg.utils.Logging;
import com.brennytizer.jumg.utils.Logging.LoggingSpice;

public class WebIO {
	public static WebWriter startWWebWriter(String location) {
		Logging.log(LoggingSpice.HOT, "WEB WRITING WILL COME SOON. (Think about release...)");
		return null;
	}
	
	public static WebReader startWebReader(String location) throws MalformedURLException {
		Logging.log(LoggingSpice.MILD, ""); // TODO: WRITE LOGS FOR ALL ACTIONS AND STUFF.
		WebReader fo = new WebReader(new URL(location));
		return fo;
	}
	
	public static class WebWriter {
		public ArrayList<String> data;
		public URL url;
		public boolean writing;
		
		public WebWriter(URL url) {
			this.url = url;
			data = new ArrayList<String>();
		}
		
		public void appendData(String line) {
			data.add(line);
		}
		
		public void appendData(ArrayList<String> lines) {
			data.addAll(lines);
		}
	}
	
	public static class WebReader {
		public ArrayList<String> data;
		public URL url;
		
		public WebReader(URL url) {
			this.url = url;
			data = new ArrayList<String>();
		}
		
		public ArrayList<String> read() {
			URLConnection con;
			try {
				con = url.openConnection();
				InputStream is = con.getInputStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				String line = null;
				while((line = br.readLine()) != null)
					data.add(line);
			} catch(IOException e) {
				e.printStackTrace();
			}
			return data;
		}
		public ArrayList<String> getData() {
			return data;
		}
	}
}