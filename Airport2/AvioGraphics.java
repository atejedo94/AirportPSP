package aeroport;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Jumi i Jaume
 */
public class AvioGraphics {

    public static BufferedImage imgAvio1;

    private static boolean isLoad = false;

    private static void loadAvioImageH() {
        if (AvioGraphics.isLoad) return;        
        imgAvio1 = loadImage("Imagenes/avioH.png");
        isLoad = true;
    }

    private static BufferedImage loadImage(String fileName) {
        BufferedImage img;

        img = null;
        try {
            img = ImageIO.read(new File(fileName));
        } catch (IOException e) {
            System.out.println("Load image error: <" + fileName + ">");
            img = null;
        }

        return img;
    }
    
    public static BufferedImage getPlaneImage(Avio avio) {
        BufferedImage imgSource, imgTarget;
        Graphics2D g2d;

        if (!AvioGraphics.isLoad) {
            AvioGraphics.loadAvioImage();
        }

        imgTarget = imgSource = null;

        imgSource = AvioGraphics.imgAvio1;


        if (imgSource != null) {
            imgTarget = new BufferedImage(imgSource.getWidth(), imgSource.getHeight(), BufferedImage.TYPE_INT_ARGB);
            g2d = (Graphics2D) imgTarget.getGraphics();
            g2d.drawImage(imgSource, 0, 0, imgSource.getWidth(), imgSource.getHeight(), null);
            g2d.dispose();
        }

        return imgTarget;
    }
}