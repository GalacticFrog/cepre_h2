/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo;

import com.digitalpersona.uareu.Fmd;
import com.digitalpersona.uareu.UareUGlobal;
import com.flesoft.cepre.dao.AulaEstudianteDao;
import com.flesoft.cepre.dao.EstudianteDao;
import com.flesoft.cepre.dao.HuellaDao;
import com.flesoft.cepre.model.AulaEstudiante;
import com.flesoft.cepre.model.Estudiante;
import com.flesoft.cepre.model.Huella;
import com.flesoft.cepre.util.AppContext;
import com.machinezoo.sourceafis.FingerprintTemplate;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import javax.swing.JOptionPane;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

/**
 *
 * @author user
 */
public class shirodemo {

    private static final Log logger = LogFactory.getLog(shirodemo.class);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        //final File folder = new File("E:\\cepre\\HUELLAS\\RAW");
        //generateTemplates(folder);
        //listFilesForFolder(folder);
        //renameFiles(folder);
        indexTemplates();
    }

    private static void indexTemplates() {
        AulaEstudianteDao aeDao = AppContext.getInstance().getBean(AulaEstudianteDao.class);
        HuellaDao hDao = AppContext.getInstance().getBean(HuellaDao.class);
        List<AulaEstudiante> aulaEstudiantes = aeDao.listar();
//        Huella buscado = hDao.buscarPorDni("75825559");
//        System.out.println(buscado);
//        System.out.println(buscado.getDni());
//        System.out.println(buscado.getHuella());
        // m_listOfRecords = huellas;
        int total = 0;
        for (AulaEstudiante aulaEstudiante : aulaEstudiantes) {
            if (aulaEstudiante.getHuella() == null) {
                System.out.println("-->" + aulaEstudiante.getId() + "null");
                String dni = aulaEstudiante.getEstudiante().getDni();
                Huella buscado = hDao.buscarPorDni(dni);
                if (buscado != null) {
                    aulaEstudiante.setHuella(buscado.getHuella());
                    aeDao.guardar(aulaEstudiante);
                    total++;
                }
            }
        }
        System.out.println("total->" + total);
        System.exit(0);
    }

    private static void testTemplates() {
        AulaEstudianteDao aeDao = AppContext.getInstance().getBean(AulaEstudianteDao.class);
        List<AulaEstudiante> huellas = aeDao.listarConHuella();
        // m_listOfRecords = huellas;
        int total = 0;
        for (AulaEstudiante huella : huellas) {
            if (huella.getHuella() != null) {
                //Blob blob = huella.getHuella();
                int blobLength = huella.getHuella().length;
                if (blobLength == 1640) {
//                    huella.setHuella(null);
//                    aeDao.guardar(huella);
                    total++;
                }

                System.out.println("blobLength->" + blobLength);

                byte[] bBytes = huella.getHuella();
//                Fmd fmd = UareUGlobal.GetImporter().ImportFmd(bBytes, com.digitalpersona.uareu.Fmd.Format.DP_REG_FEATURES, com.digitalpersona.uareu.Fmd.Format.DP_REG_FEATURES);
//                m_fmdList.add(fmd);
            }
        }
        System.out.println("total->" + total);
        System.exit(0);
    }

    /**
     * rename and remove slashes
     */
    public static void renameFiles(final File folder) throws IOException {

        File[] files = folder.listFiles();
        int total = 0;
        for (final File fileEntry : files) {

            if (fileEntry.isFile()) {

                System.out.println(fileEntry.getName());
                String[] cut = fileEntry.getName().split("_");
                String outfile = cut[cut.length - 1];

                File originalFile = new File("E:\\cepre\\HUELLAS\\RAW\\" + fileEntry.getName());
                File newFile = new File("E:\\cepre\\HUELLAS\\2RENAMED\\" + outfile);
                System.out.println("originalFile->" + originalFile);
                System.out.println("newFil->" + newFile);
                FileUtils.copyFile(originalFile, newFile);
//                String dni = FilenameUtils.removeExtension(fileEntry.getName());
//                System.out.println("dni->" + dni);
                //String dni2 = FilenameUtils.removeExtension(cut[1]);
            }

            //}
        }
        System.out.println("total" + total);
    }

    public static void generateTemplates(final File folder) throws IOException {

        AulaEstudianteDao auDao = AppContext.getInstance().getBean(AulaEstudianteDao.class);

        File[] files = folder.listFiles();
        int total = 0;
        for (final File fileEntry : files) {

            if (fileEntry.isFile()) {

                System.out.println(fileEntry.getName());
                String[] cut = fileEntry.getName().split("_");
                String dni = FilenameUtils.removeExtension(fileEntry.getName());
                System.out.println("dni->" + dni);
                //String dni2 = FilenameUtils.removeExtension(cut[1]);
                AulaEstudiante aest = auDao.buscarPorEstudianteDni(dni);
                if (aest != null) {
                    total++;
                    System.out.println("exites->" + aest.getEstudiante().getDni());
                    byte[] image = Files.readAllBytes(Paths.get("E:/java/cepre-unsch-java11/huellas/estudiantes/" + fileEntry.getName()));
                    FingerprintTemplate template = new FingerprintTemplate()
                            .dpi(500)
                            .create(image);
                    String json = template.serialize();
                    aest.setJsonTemplate(json);
                    auDao.guardar(aest);

                } else {
                    System.out.println("------------------------------------------------------------");
                }
//                AulaEstudiante aest2 = auDao.buscarPorEstudianteDni(dni2);
//                if (aest2 != null) {
//                    total++;
//                    System.out.println("exites->" + aest.getEstudiante().getDni());
//                    byte[] image = Files.readAllBytes(Paths.get("E:/java/cepre-unsch-java11/huellas/estudiantes/" + fileEntry.getName()));
//                    FingerprintTemplate template = new FingerprintTemplate()
//                            .dpi(500)
//                            .create(image);
//                    String json = template.serialize();
//                    aest.setJsonTemplate(json);
//                    auDao.guardar(aest);
//
//                } else {
//                    System.out.println("------------------------------------------------------------");
//                }

            }

            //}
        }
        System.out.println("total" + total);
    }

    public static void makeTemplates() throws IOException {
        int total = 0;
        AulaEstudianteDao aulaEstudianteDao = AppContext.getInstance().getBean(AulaEstudianteDao.class);

        List<AulaEstudiante> estudiantes = aulaEstudianteDao.listar();
        for (AulaEstudiante aulaEstudiante : estudiantes) {
            String dni = aulaEstudiante.getEstudiante().getDni();
            File imagepng = new File("E:\\cepre\\HUELLAS\\3PROCESED500PP\\" + dni + ".png");
            if (imagepng.exists() && imagepng.isFile()) {
                total++;
                System.out.println("dni->" + aulaEstudiante.getEstudiante().getDni());
                byte[] image = Files.readAllBytes(Paths.get("E:\\cepre\\HUELLAS\\3PROCESED500PP\\" + dni + ".png"));
                FingerprintTemplate template = new FingerprintTemplate()
                        .dpi(500)
                        .create(image);
                String json = template.serialize();
                aulaEstudiante.setJsonTemplate(json);
                aulaEstudianteDao.guardar(aulaEstudiante);
            } else {
                System.out.println("No existe");
            }
        }

    }
//copia las imagenes de los dni existentes

    public static void listFilesForFolder(final File folder) throws IOException {

        EstudianteDao edao = AppContext.getInstance().getBean(EstudianteDao.class);
        AulaEstudianteDao udao = AppContext.getInstance().getBean(AulaEstudianteDao.class);

        int total = 0;
        List<Estudiante> ests = edao.listar();
        for (Estudiante es : ests) {
            System.out.println("e->" + es);
            File dnijpg = new File("E:/java/SourceAfis/500ppp/" + es.getDni() + ".jpg");
            if (dnijpg.exists() && dnijpg.isFile()) {
                System.out.println("existe->" + dnijpg.getName());
                total++;
                System.out.println(dnijpg.getName());
                File newFile = new File("E:/java/cepre-unsch-java11/huellas_/" + dnijpg.getName());
                FileUtils.copyFile(dnijpg, newFile);
            } else {
                System.out.println("No existe");
            }
        }
        System.out.println("total->" + total);
//
//            if (fileEntry.isFile()) {
//                String ext = FilenameUtils.getExtension(fileEntry.getName());
//                System.out.println(fileEntry.getName());
//                String fileNameWithOutExt = FilenameUtils.removeExtension(fileEntry.getName());
//
//                File newFile = new File("huellascepre/" + index + "_huella." + ext);
//                FileUtils.copyFile(fileEntry, newFile);
//
//            }
    }

}
