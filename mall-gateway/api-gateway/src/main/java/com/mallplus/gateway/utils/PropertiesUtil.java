package com.mallplus.gateway.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.ConfigurationFactory;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.springframework.stereotype.Component;

/**
 * 修改 properties 文件时，每次都要重启应用程序， Commons-Configuration 调用 getXxxx 方法获取到的内容都是
 * properties 文件中最新的，无需重启应用
 *
 * @author zhaigx
 * @DATA 2011-5-27
 */
@Slf4j
@Component
public class PropertiesUtil {
    static String propertiesFile = "config/backIp.properties";
    static PropertiesConfiguration propConfig;
    static Configuration multiConfig;
    static boolean isReload = true;

    private PropertiesUtil() {

    }

    private static void reloadPropFile() {
        if (isReload) {
            try {
                propConfig = new PropertiesConfiguration(propertiesFile);
                propConfig.setReloadingStrategy(new FileChangedReloadingStrategy());
            } catch (ConfigurationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        isReload = false;
    }

    public static Object getValueFromPropFile(String key) {
        reloadPropFile();
        return propConfig.getProperty(key);
    }

    public static String[] getArrFromPropFile(String key) {
        reloadPropFile();
        return propConfig.getStringArray(key);
    }


    private static void reloadMultiConfigFile() {
        ConfigurationFactory factory = new ConfigurationFactory();
        String file = "config/xml-prop.xml";
        System.out.println("multiConfigFile==> " + file);
        factory.setConfigurationFileName(file);

        try {
            multiConfig = factory.getConfiguration();
        } catch (ConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static Object getValueFromMultiFile(String key) {
        reloadMultiConfigFile();
        return multiConfig.getProperty(key);
    }
}