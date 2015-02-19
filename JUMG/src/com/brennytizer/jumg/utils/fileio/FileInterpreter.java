package com.brennytizer.jumg.utils.fileio;

import java.awt.image.BufferedImage;
import java.lang.reflect.Field;
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
	public static final int MANDATORY_DATA = 0;
	public static final int INITIALISE_OBJECT = 1;
	public static final int END_OBJECT_INIT = 2;
	public static final int OBJECT_INIT_PARAM = 3;
	public static final int OBJECT_DATA = 4;
	public static final int FUNCTION = 5;
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
	
	public int getTriggerStyle(String line) {
		if(line.startsWith("$..$") && line.split("\\$\\.\\.\\$").length >= 3) return MANDATORY_DATA;
		if(line.startsWith("$$$") && line.split("\\$\\$\\$").length == 3) return INITIALISE_OBJECT;
		if(line.startsWith("$$$") && line.split("\\$\\$\\$").length == 2) return END_OBJECT_INIT;
		if(line.startsWith("$$") && line.split("\\$\\$").length == 2) return OBJECT_INIT_PARAM;
		if(line.startsWith("$") && line.split("\\$").length == 3) return OBJECT_DATA;
		if(line.startsWith("$") && line.split("\\$").length == 3) return FUNCTION;
		return -1;
	}
	
	@SuppressWarnings("unchecked")
	public void read(String line) {
		int triggerStyle = getTriggerStyle(line);
		String[] split;
		switch(triggerStyle) {
		default:
		case -1:
			Logging.log(LoggingSpice.MEDIUM, "  Invalid line! Skipping...");
			return;
		case MANDATORY_DATA:
			Logging.log(LoggingSpice.MILD, "  Mandatory data.");
			split = line.split("\\$\\.\\.\\$");
			cache.put(split[1], split[2]);
			mandatories.put(split[1], split[2]);
			break;
		case INITIALISE_OBJECT:
			Logging.log(LoggingSpice.MILD, "  Initialise object.");
			split = line.split("\\$\\$\\$");
			objectInitData = new ArrayList<String>();
			objectInitData.add(split[2]);
			objectInitData.add(split[1]);
			break;
		case END_OBJECT_INIT:
			Logging.log(LoggingSpice.MILD, "  Instantiating object with given parameters.");
			try {
				Class<? extends FileInstantiable> clazz = (Class<? extends FileInstantiable>) Class.forName((String) objectInitData.get(1), false, this.getClass().getClassLoader());
				String name = objectInitData.get(0);
				Logging.log(LoggingSpice.MILD, String.format("    Object: %s", Arrays.asList(objectInitData.get(0), objectInitData.get(1))));
				objectInitData.remove(0);
				objectInitData.remove(0);
				String[] data = objectInitData.toArray(new String[0]);
				Logging.log(LoggingSpice.MILD, String.format("    Params: %s", objectInitData));
				cache.put(name, clazz.newInstance().instantiateWithParams(data));
			} catch(ClassNotFoundException | InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
				Logging.log(LoggingSpice.HOT, "  Couldn't initialise the class (Reflection exception).");
			}
			break;
		case OBJECT_INIT_PARAM:
			Logging.log(LoggingSpice.MILD, "  Parameter data.");
			objectInitData.add(line.substring(2, Words.indexOf(line, "$$", -1)));
			break;
		case OBJECT_DATA:
			Logging.log(LoggingSpice.MILD, "  Object data.");
			split = line.split("\\$\\$");
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
			line = line.substring(2, Words.indexOf(line, "$$", -1));
			if(line.startsWith("F.")) {
				line = line.replace("$.$", "/");
				String file = relativeLocation + "/" + line;
				FileInterpreter fInt = new FileInterpreter(this.cache);
				FileIO.FileInput fi = FileIO.startFileReader(file);
				fi.readData();
				fInt.read(fi.getData());
			}
			break;
		}
	}
	public void setObjectData(Object obj, Field field, String newObj) throws IllegalArgumentException, IllegalAccessException {
		field.set(obj, generateObject(newObj)[1]);
	}
	public static Object[] generateObject(String newObj) {
		ReflectiveType type = ReflectiveType.getReflectiveType(newObj);
		Object theRealNewObj = null;
		String[] split = null;
		newObj = newObj.substring(type.trigger.length());
		switch(type) {
		case ANGLE:
			theRealNewObj = new Angle(Float.parseFloat(newObj));
			break;
		case POINT:
			split = newObj.split("\\$\\.\\$");
			float x = Float.parseFloat(split[0]);
			float y = Float.parseFloat(split[1]);
			theRealNewObj = new Point2D(x, y);
			break;
		case POLYGON:
			split = newObj.split("\\$\\.\\$");
			Point2D[] points = new Point2D[split.length / 2];
			for(int i = 0; i < split.length; i += 2) {
				float xa = Float.parseFloat(split[i]);
				float ya = Float.parseFloat(split[i + 1]);
				points[i / 2] = new Point2D(xa, ya);
			}
			theRealNewObj = new Polygon(points);
			break;
		case RECTANGLE:
			split = newObj.split("\\$\\.\\$");
			float xb = Float.parseFloat(split[0]);
			float yb = Float.parseFloat(split[1]);
			float width = Float.parseFloat(split[2]);
			float height = Float.parseFloat(split[3]);
			theRealNewObj = new Rectangle2D(xb, yb, width, height);
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
		ANGLE("A.", Angle.class);
		
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
		
	}
}