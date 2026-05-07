package utils;

import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConfigReader {
	  private static final Logger log = LogManager.getLogger(ConfigReader.class);
	    private static final Properties PROPS = new Properties();
	    
	    public static String get(String key, String defaultValue) {
	        return System.getProperty(key, PROPS.getProperty(key, defaultValue));
	    }
	    
	    public static String get(String key) {
	        String value = System.getProperty(key, PROPS.getProperty(key));
	        if (value == null) {
	            throw new RuntimeException(
	                    "Required config key '" + key + "' is not defined. " +
	                    "Add it to config.properties or pass -D" + key + "=<value>.");
	        }
	        return value;
	    }
}
