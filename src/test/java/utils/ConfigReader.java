package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConfigReader {
	  private static final Logger log = LogManager.getLogger(ConfigReader.class);
	    private static final Properties PROPS = new Properties();
	    
	    static {
	        try (InputStream is = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties")) {
	            if (is == null) {
	                throw new RuntimeException("config.properties not found in classpath!");
	            }
	            PROPS.load(is);
	        } catch (IOException e) {
	            throw new RuntimeException("Failed to load config.properties", e);
	        }
	    }
	    
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
