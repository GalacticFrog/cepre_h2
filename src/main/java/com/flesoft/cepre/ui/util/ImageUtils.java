/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flesoft.cepre.ui.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author edrev
 */
public class ImageUtils {

    public static BufferedImage convertToBufferedImage(Image image) {
        BufferedImage newImage = new BufferedImage(
                image.getWidth(null), image.getHeight(null),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = newImage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return newImage;
    }

    public static void save(String fileName, BufferedImage image, String ext) throws IOException {
        File file = new File(fileName + "." + ext);
        file.getParentFile().mkdirs();//create dir if not exist
        ImageIO.write(image, ext, file);  // ignore returned boolean

    }

//    public static void saveTiff(String fileName, BufferedImage image) throws IOException, ImageWriteException {
//        // BufferedImage img = ImageIO.read(file_);
//        File file = new File(fileName + ".tif");
//        file.getParentFile().mkdirs();//create dir if not exist
//
//        Map params = new HashMap();
//        params.put(ImagingConstants.PARAM_KEY_PIXEL_DENSITY, PixelDensity.createFromPixelsPerCentimetre(500, 500));
//        Sanselan.writeImage(image, file, ImageFormats.TIFF, params);
//
//    }
//    public static void save(String ruta, BufferedImage img) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
    public static BufferedImage toBufferedImage(Image src) {
        int w = src.getWidth(null);
        int h = src.getHeight(null);
        int type = BufferedImage.TYPE_INT_RGB;  // other options
        BufferedImage dest = new BufferedImage(w, h, type);
        Graphics2D g2 = dest.createGraphics();
        g2.drawImage(src, 0, 0, null);
        g2.dispose();
        return dest;
    }

    private static BufferedImage drawString(BufferedImage old) {
        int w = old.getWidth() / 3;
        int h = old.getHeight() / 3;
        BufferedImage img = new BufferedImage(
                w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        g2d.drawImage(old, 0, 0, null);
        g2d.setPaint(Color.red);
        g2d.setFont(new Font("Serif", Font.BOLD, 20));
        String s = "Hello, world!";
        FontMetrics fm = g2d.getFontMetrics();
        int x = img.getWidth() - fm.stringWidth(s) - 5;
        int y = fm.getHeight();
        g2d.drawString(s, x, y);
        g2d.dispose();
        return img;
    }

}
