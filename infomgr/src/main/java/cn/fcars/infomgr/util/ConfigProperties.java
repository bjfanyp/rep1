package cn.fcars.infomgr.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigProperties {
	public static String getValue(String key){
		Properties prop = new Properties();
        InputStream in = ConfigProperties.class.getResourceAsStream("/jdbc.properties");
        try {   
            prop.load(in);   
            return prop.getProperty(key).trim();
        } catch (IOException e) {
            e.printStackTrace();   
        } 
        return  null;
	}
}
