/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flesoft.cepre.util;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 *
 * @author user
 */
@PropertySource("classpath:config.properties")
public class ShiroUtils {

    public static String getSalt() {
        return new SecureRandomNumberGenerator().nextBytes().toBase64();
    }

    public static String getApplicationSalt() {
        AppEnviroment env = EnvContext.getInstance();
        return env.getProperty("shiro.applicationSalt");
    }

    public static String getCombinedSalt(String salt) {
        AppEnviroment env = EnvContext.getInstance();
        return env.getProperty("shiro.applicationSalt") + ":" + salt;
    }

    public static Integer getIterations() {
        AppEnviroment env = EnvContext.getInstance();
        return Integer.parseInt(env.getProperty("shiro.hashIterations"));
    }

    public static String encodePassphrase(String rawPassphrase, String salt) {
        return new Sha512Hash(rawPassphrase, getCombinedSalt(salt), getIterations()).toBase64();
    }

}
