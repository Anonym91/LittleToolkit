package com.anonymous.config;

import lombok.extern.slf4j.Slf4j;

import java.util.ResourceBundle;

/**
 * @Author: DLF
 * @Date: 2019/11/4 0004 16:51
 * @Version 1.0
 * @Description： Config file obtain
 */
@Slf4j
public class Configurations {

    private static class ConfigurationsInstance{
        private static final Configurations instance = new Configurations();
    }

    private Configurations(){}

    public static Configurations getInstance(){
        return ConfigurationsInstance.instance;
    }

    private static ResourceBundle rb; // 资源配置文件读取
    private static String dev = "dev";
    private static String prod = "prod";
    private static String test = "test";

    static {
        String deploy = null;
        String configName = null;
        try {
            configName = "config";
            rb = ResourceBundle.getBundle(configName);
            deploy = rb.getString(ConfigStr.DEPLOY);
            if (dev.equals(deploy) || test.equals(deploy) || prod.equals(deploy)) {
                configName = "config-" + deploy;
            }
            rb = ResourceBundle.getBundle(configName);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Config File [" + configName + "] not existed!");
            throw new RuntimeException("Config File [" + configName + "] not existed!");
        }
    }
    public String getString(String key) {
        return rb.getString(key);
    }

    public Short getShort(String key) {
        return Short.parseShort(rb.getString(key));
    }

    public Integer getInteger(String key) {
        return Integer.parseInt(rb.getString(key));
    }


    public Boolean getBoolean(String key) {
        return Boolean.parseBoolean(rb.getString(key));
    }



}
