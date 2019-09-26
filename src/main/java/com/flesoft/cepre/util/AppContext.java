/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flesoft.cepre.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

/**
 *
 * @author frg
 */
public class AppContext {

    private static GenericXmlApplicationContext ctx;

    private AppContext() {

    }

    public static GenericXmlApplicationContext getInstance() {
        if (ctx == null) {
            ctx = new GenericXmlApplicationContext();
            ctx.load("classpath:app-context.xml");
            ctx.refresh();

            //ctx = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
        }

        return ctx;
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
