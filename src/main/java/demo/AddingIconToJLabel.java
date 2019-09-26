/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo;

import com.flesoft.cepre.dao.UsuarioDao;
import com.flesoft.cepre.ui.util.Silk;
import com.flesoft.cepre.util.AppContext;
import com.flesoft.cepre.util.AppEnviroment;
import com.flesoft.cepre.util.EnvContext;
import com.flesoft.cepre.util.ShiroUtils;
import com.revolsys.famfamfam.silk.SilkIconLoader;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import org.springframework.core.env.Environment;

public class AddingIconToJLabel {

    public static void main(String[] a) {
        //appEnviroment
        AppEnviroment env = EnvContext.getInstance();
        //AppEnviroment su = AppContext.getInstance().getBean("appEnviroment", AppEnviroment.class);
        //Environment env = su.getEnv();
        System.out.println(env.getProperty("shiro.applicationSalt"));
        System.out.println("ShiroUtils" + ShiroUtils.getSalt());
    }

}
