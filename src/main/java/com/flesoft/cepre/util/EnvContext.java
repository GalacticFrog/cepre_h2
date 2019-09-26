/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flesoft.cepre.util;

/**
 *
 * @author frg
 */
public class EnvContext {

    private static AppEnviroment enviroment;

    private EnvContext() {

    }

    public static AppEnviroment getInstance() {
        if (enviroment == null) {
            enviroment = AppContext.getInstance().getBean("appEnviroment", AppEnviroment.class);
        }
        return enviroment;
    }

    /*    public static Object getBean(String beanName) {
        return getInstance().getBean(beanName);
    }

    public static String getI18NMessage(String key) {
        ApplicationContext appContext = getInstance();
        return appContext.getMessage(key, null, "Error reading resource", null);
    }
     */
}
