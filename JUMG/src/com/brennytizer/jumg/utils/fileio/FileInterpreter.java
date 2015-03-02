package com.brennytizer.jumg.utils.fileio;

import java.awt.image.BufferedImage;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import com.brennytizer.jumg.utils.Logging;
import com.brennytizer.jumg.utils.Logging.LoggingSpice;
import com.brennytizer.jumg.utils.Words;
import com.brennytizer.jumg.utils.geom.Angle;
import com.brennytizer.jumg.utils.geom.Point2D;
import com.brennytizer.jumg.utils.geom.Polygon;
import com.brennytizer.jumg.utils.geom.Rectangle2D;

public class FileInterpreter implements Interpreter {
	public static HashMap<Object, Object> mandatories = new HashMap<Object, Object>();
	public HashMap<Object, Object> cache;
	public ArrayList<String> objectInitData;
	public String relativeLocation = FileIO.userHome + "/jumg/io/";
	
	public FileInterpreter() {
		this(new HashMap<Object, Object>());
	}
	
	public FileInterpreter(HashMap<Object, Object> cache) {
		this.cache = cache;
	}
	
	public String getKeySpace() {
		return FileIO.keySpace;
	}
	
	public void read(ArrayList<String> lines) {
		for(String line : lines) {
			Logging.log(LoggingSpice.MILD, "Interpreting: " + line);
			read(line);
		}
	}
	
	public SyntaxJumg getTriggerStyle(String line) {
		SyntaxJumg sj = SyntaxJumg.getJumgSyntax(line);
		if(sj.hasMore) {
			sj = SyntaxJumg.getJumgSyntax(sj.getTrigger(), Words.count(line, sj.getTrigger(), false));
		}
		return sj;
	}
	
	@SuppressWarnings("unchecked")
	public void read(String line) {
		SyntaxJumg type = getTriggerStyle(line);
		String[] split = line.split(type.getTrigger());
		switch(type) {
		default:
		case INVALID:
			Logging.log(LoggingSpice.MEDIUM, "  Invalid line! Skipping...");
			return;
		case MANDATORY_DATA:
			Logging.log(LoggingSpice.MILD, "  Mandatory data.");
			cache.put(split[1], split[2]);
			mandatories.put(split[1], split[2]);
			break;
		case OBJECT_INITIALISE:
			Logging.log(LoggingSpice.MILD, "  Initialise object.");
			objectInitData = new ArrayList<String>();
			objectInitData.add(split[2]);
			objectInitData.add(split[1]);
			break;
		case OBJECT_END_INIT:
			Logging.log(LoggingSpice.MILD, "  Instantiating object with given parameters.");
			try {
				Class<? extends FileInstantiable<?>> clazz = (Class<? extends FileInstantiable<?>>) Class.forName(objectInitData.get(1), false, this.getClass().getClassLoader());
				String name = objectInitData.get(0);
				Logging.log(LoggingSpice.MILD, String.format("    Object: %s", Arrays.asList(objectInitData.get(0), objectInitData.get(1))));
				objectInitData.remove(0);
				objectInitData.remove(0); //Call this twice to remove the first two items.
				String[] data = objectInitData.toArray(new String[0]);
				Logging.log(LoggingSpice.MILD, String.format("    Params: %s", objectInitData));
				cache.put(name, clazz.newInstance().instantiateWithParams(data));
			} catch(ClassNotFoundException | InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
				Logging.log(LoggingSpice.HOT, "  Couldn't initialise the class (Reflection exception).");
			}
			break;
		case OBJECT_PARAMETER:
			Logging.log(LoggingSpice.MILD, "  Parameter data.");
			objectInitData.add(line.substring(2, Words.indexOf(line, "$$", -1)));
			break;
		case OBJECT_DATA:
			Logging.log(LoggingSpice.MILD, "  Object data.");
			split = new String[] {split[1], split[2]};
			String[] first = split[0].split("\\.", 2);
			Object main = cache.get(first[0]);
			try {
				Field f = main.getClass().getField(first[1]);
				setObjectData(main, f, split[1]);
			} catch(NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
				Logging.log(LoggingSpice.HOT, "  Couldn't set object data (Reflection exception).");
			}
			break;
		case FUNCTION:
			Logging.log(LoggingSpice.MILD, "  Executing a function.");
			line = line.substring(2, Words.indexOf(line, type.getTrigger(), -1));
			split = line.split("\\.");
			if(line.startsWith("F.")) {
				line = line.replace("$.$", "/");
				String file = relativeLocation + "/" + line;
				FileInterpreter fInt = new FileInterpreter(this.cache);
				FileIO.FileInput fi = FileIO.startFileReader(file);
				fi.readData();
				fInt.read(fi.getData());
			} else {
				Object funcMain = cache.get(split[0]);
				try {
					Method meth = funcMain.getClass().getMethod(split[1]);
					meth.invoke(funcMain);
				} catch(NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
					Logging.log(LoggingSpice.HOT, "  Couldn't call the function asked for (Reflection exception).");
					Logging.log(LoggingSpice.HOT, "  Data:");
					Logging.log(LoggingSpice.HOT, "  Object: " + split[0]);
					Logging.log(LoggingSpice.HOT, "  Method: " + split[1]);
				}
			}
			break;
		}
	}
	
	public HashMap<Object, Object> getCache() {
		return getCache(false);
	}
	
	public HashMap<Object, Object> getCache(boolean includeMandatories) {
		HashMap<Object, Object> retVal = new HashMap<Object, Object>();
		if(includeMandatories) retVal.putAll(mandatories);
		retVal.putAll(cache);
		return retVal;
	}
	
	public void setObjectData(Object obj, Field field, String newObj) throws IllegalArgumentException, IllegalAccessException {
		field.set(obj, generateObject(newObj)[1]);
	}
	
	@SuppressWarnings("all")
	public static Object[] generateObject(String newObj) {
		ReflectiveType type = ReflectiveType.getReflectiveType(newObj);
		Object theRealNewObj = null;
		String[] split = null;
		newObj = newObj.substring(type.getTrigger().length());
		String splitter = ReflectiveType.getArgSplitter();
		switch(type) {
		case ANGLE:
			theRealNewObj = new Angle(Float.parseFloat(newObj));
			break;
		case POINT:
			split = newObj.split(splitter);
			float x = Float.parseFloat(split[0]);
			float y = Float.parseFloat(split[1]);
			theRealNewObj = new Point2D(x, y);
			break;
		case POLYGON:
			split = newObj.split(splitter);
			Point2D[] points = new Point2D[split.length / 2];
			for(int i = 0; i < split.length; i += 2) {
				float xa = Float.parseFloat(split[i]);
				float ya = Float.parseFloat(split[i + 1]);
				points[i / 2] = new Point2D(xa, ya);
			}
			theRealNewObj = new Polygon(points);
			break;
		case RECTANGLE:
			split = newObj.split(splitter);
			float xb = Float.parseFloat(split[0]);
			float yb = Float.parseFloat(split[1]);
			float width = Float.parseFloat(split[2]);
			float height = Float.parseFloat(split[3]);
			theRealNewObj = new Rectangle2D(xb, yb, width, height);
			break;
		case LIST:
			split = newObj.split(splitter);
			ArrayList array = new ArrayList();
			for(String s : split) array.add(s);
			theRealNewObj = array;
			break;
		case IMAGE:
			try {
				Class<?> imageClazz = Class.forName((String) mandatories.get("Images"), false, FileInterpreter.class.getClassLoader());
				theRealNewObj = imageClazz.getField(Words.swapChars(newObj, ".", "_")).get(null);
			} catch(ClassNotFoundException | NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
				Logging.log(LoggingSpice.HOT, "  Oh no! Couldn't snag that image you were trying to get: " + newObj);
			}
			break;
		default:
		case INVALID:
			break;
		}
		return new Object[] {type, theRealNewObj};
	}
	
	public enum ReflectiveType {
		INVALID("INVALID", null),
		POINT("P.", Point2D.class),
		IMAGE("I.", BufferedImage.class),
		POLYGON("PO.", Polygon.class),
		RECTANGLE("R.", Rectangle2D.class),
		ANGLE("A.", Angle.class),
		LIST("L.", ArrayList.class);
		
		public String trigger;
		public Class<?> type;
		
		ReflectiveType(String trigger, Class<?> type) {
			this.trigger = trigger;
			this.type = type;
		}
		
		public String getTrigger() {
			return trigger;
		}
		
		public Class<?> getType() {
			return type;
		}
		
		public static ReflectiveType getReflectiveType(String trigger) {
			for(ReflectiveType rt : ReflectiveType.values()) {
				if(rt.trigger.equals(Words.cutAt(trigger, rt.trigger.length()))) return rt;
			}
			return INVALID;
		}
		
		public static ReflectiveType getReflectiveType(Class<?> type) {
			for(ReflectiveType rt : ReflectiveType.values()) {
				if(type == rt.type) return rt;
			}
			return INVALID;
		}
		
		public static String getArgSplitter() {
			return "\\$\\.\\$";
		}
	}
}