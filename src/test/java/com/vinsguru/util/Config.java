package com.vinsguru.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private  static  final Logger log = LoggerFactory.getLogger(Config.class);
    private static final String DEAFULT_PROPERTIES = "./test-data/config/default.properties";
    private static Properties properties;

    public static void initialize(){
        // load default properties
        properties = loadProperties();
        // check for any override
        for(String key: properties.stringPropertyNames()){
            if(System.getProperties().contains(key)){
                System.out.println("key: "+key);
                System.out.println("before setting the property for key : "+key + properties.getProperty(key));
                properties.setProperty(key,System.getProperty(key));
                System.out.println("after setting the property for key : "+key + properties.getProperty(key));
            }
        }
        //print
        log.info("Test properties");
        log.info("------------------------------");
        for(String key:properties.stringPropertyNames()){
            log.info("{}={}",key,properties.getProperty(key));
        }
        log.info("------------------------------");
    }
    public static String get(String key){
        return properties.getProperty(key);
    }

    public static Properties loadProperties(){
        Properties properties = new Properties();
        try(InputStream stream = ResourceLoader.getResource(DEAFULT_PROPERTIES)){
            properties.load(stream);
        }  catch (Exception e) {
            throw new RuntimeException(e);
        }
        return properties;
    }

}
