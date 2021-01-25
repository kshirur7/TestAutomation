package com.eurowings.qa.logger;

import com.eurowings.qa.resource.ResourceHelper;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class loggerHelper {

    private static boolean root=false;

    public static Logger getLogger(Class cls){
        if(root){
            return Logger.getLogger(cls);
        }
        PropertyConfigurator.configure(ResourceHelper.getResourcePath("src/main/resources/log4j.properties"));
        root = true;
        return Logger.getLogger(cls);
    }

    public static void main(String[] args) {
        Logger log = loggerHelper.getLogger(loggerHelper.class);
        log.info("logger is configured");


    }
}
