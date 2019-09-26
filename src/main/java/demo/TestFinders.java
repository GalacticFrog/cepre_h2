/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author user
 */
public class TestFinders {

    private static final Log logger = LogFactory.getLog(TestFinders.class);

    public static void main(String[] args) {

    }

//    public static void testMatchOneToManyParallel() throws IOException {
//        UserDao udao = AppContext.getInstance().getBean(UserDao.class);
//        List<User> users = udao.list();
//        Collections.shuffle(users);
//        for (User user : users) {
//            long startTime = System.currentTimeMillis();
//            //System.out.println(user.getName());
//            byte[] probeImage = Files.readAllBytes(Paths.get("huellascepre/" + user.getName()));
//            FingerprintTemplate probe = new FingerprintTemplate()
//                    .dpi(500)
//                    .create(probeImage);
//            System.out.print(user.getName() + "->");
//
//            findParallel(probe, users);
//            long stopTime = System.currentTimeMillis();
//            long elapsedTime = stopTime - startTime;
//            System.out.println(" time: (" + elapsedTime + ")");
//        }
//
//        //return high >= threshold ? match : null;
//    }
//
//    public static void findParallel(FingerprintTemplate probe, List<User> users) {
//
//        FingerprintMatcher matcher = new FingerprintMatcher()
//                .index(probe);
//
//        double high = 0;
//
////        User buscado = users
////                .parallelStream()
////                .min(Comparator.comparing(user -> matcher.match(user.getTemplate())))
////                .orElse(null);
//        User buscado = users.parallelStream()
//                .filter(user -> matcher.match(user.getTemplate()) > 550)
//                .findAny()
//                .orElse(null);
//        System.out.print(" User: (" + buscado + ") ");
//    }
}
