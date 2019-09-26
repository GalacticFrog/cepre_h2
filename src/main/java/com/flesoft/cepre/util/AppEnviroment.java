/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flesoft.cepre.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

/**
 *
 * @author user
 */
//@PropertySource("classpath:config.properties")
public class AppEnviroment {

    @Autowired
    private Environment env;

    public AppEnviroment() {
    }

    public void setEnv(Environment env) {
        this.env = env;
    }

    public Environment getEnv() {
        return env;
    }

    public String getProperty(String prop) {
        return env.getProperty(prop);
    }

}
