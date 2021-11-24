package util;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.image.LookupOp;
import java.awt.image.ShortLookupTable;


public class ImageUtil {

    public static void displayImage(BufferedImage img, String title) {
        if (img == null)
            return;
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImagePanel imagePanel = new ImagePanel();
        imagePanel.setFitToScreen(false);
        imagePanel.setImage(img);
        frame.setContentPane(new JScrollPane(imagePanel));
        frame.pack();
        frame.setVisible(true);
    }

    public static void displayImage(BufferedImage img) {
        displayImage(img, "");
    }

    public static BufferedImage applySettingsDlg(BufferedImage img, AbstractSettingsDialog dialog) {
        if (img == null)
            return null;
        JFrame frame = new JFrame();

        ImagePanel imagePanel = new ImagePanel();
        imagePanel.setFitToScreen(false);
        imagePanel.setImage(img);
        frame.setContentPane(new JScrollPane(imagePanel));
        frame.pack();
        frame.setVisible(true);

        dialog.setImagePanel(imagePanel);
        dialog.pack();
        dialog.setVisible(true);

        frame.dispose();
        return imagePanel.getImage();
    }

    public static int constrain(int val, int min, int max) {
        return val > max ? max : (val < min ? min : val);
    }

    public static int constrain(int val) {
        return constrain(val, 0, 255);
    }


// red, green, blue channels

    public static BufferedImage redImg(BufferedImage inImg) {
        BufferedImage rImg = new BufferedImage(inImg.getWidth(), inImg.getHeight(), BufferedImage.TYPE_INT_ARGB);
        //get width and height
        int width = inImg.getWidth();
        int height = inImg.getHeight();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int p = inImg.getRGB(x, y);

                int a = (p >> 24) & 0xff;
                int r = (p >> 16) & 0xff;

                //set new RGB
                p = (a << 24) | (r << 16) | (0 << 8) | 0;

                rImg.setRGB(x, y, p);
            }
        }
        return rImg;
    }

    public static BufferedImage greenImg(BufferedImage inImg) {
        BufferedImage gImg = new BufferedImage(inImg.getWidth(), inImg.getHeight(), BufferedImage.TYPE_INT_ARGB);
        //get width and height
        int width = inImg.getWidth();
        int height = inImg.getHeight();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int p = inImg.getRGB(x, y);

                int a = (p >> 24) & 0xff;
                int g = (p >> 8) & 0xff;

                //set new RGB
                p = (a << 24) | (0 << 16) | (g << 8) | 0;

                gImg.setRGB(x, y, p);
            }
        }
        return gImg;
    }

    public static BufferedImage blueImg(BufferedImage inImg) {
        BufferedImage bImg = new BufferedImage(inImg.getWidth(), inImg.getHeight(), BufferedImage.TYPE_INT_ARGB);
        //get width and height
        int width = inImg.getWidth();
        int height = inImg.getHeight();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int p = inImg.getRGB(x, y);

                int a = (p >> 24) & 0xff;
                int b = p & 0xff;

                //set new RGB
                p = (a << 24) | (0 << 16) | (0 << 8) | b;

                bImg.setRGB(x, y, p);
            }
        }
        return bImg;
    }

//
//    public static BufferedImage recompose (BufferedImage redCh, BufferedImage greenCh, BufferedImage blueCh){
//        BufferedImage resultImg = new BufferedImage(greenCh.getWidth(),greenCh.getHeight(),greenCh.getType());
//
//        int width = greenCh.getWidth();
//        int height = greenCh.getHeight();
//        for(int y = 0; y < height; y++) {
//            for (int x = 0; x < width; x++) {
//                int pRed = redCh.getRGB(x, y);
//                int pGreen=greenCh.getRGB(x,y);
//                int pBlue = blueCh.getRGB(x, y);
//
//                int a = (pGreen >> 24) & 0xff;
//                int r = (pRed >> 16) & 0xff;
//                int g = (pGreen >> 8)  & 0xff;
//                int b = pBlue & 0xff;
//
//                //set new RGB
//                int p = (a << 24) | (r << 16) | (g << 8) | b;
//
//                resultImg.setRGB(x, y, p);
//            }
//        }
//        return resultImg;
//    }


    public static BufferedImage contrastGamma(BufferedImage inImg, double gammaR, double gammaG, double gammaB) {
        BufferedImage outImg = new BufferedImage(inImg.getWidth(), inImg.getHeight(), inImg.getType());

        short[] rLUT = new short[256];
        short[] gLUT = new short[256];
        short[] bLUT = new short[256];

        short[][] rgbLUT = {rLUT, gLUT, bLUT};

        for (int i = 0; i < 256; i++) {

            double aR = i / 255.0; // scale to [0.0 ... 1.0]
            double bR = Math.pow(aR, 1.0 / gammaR);
            double cR = bR * 255.0; // scale to [0 ... 255]

            double aG = i / 255.0; // scale to [0.0 ... 1.0]
            double bG = Math.pow(aG, 1.0 / gammaG);
            double cG = bG * 255.0; // scale to [0 ... 255]

            double aB = i / 255.0; // scale to [0.0 ... 1.0]
            double bB = Math.pow(aB, 1.0 / gammaB);
            double cB = bB * 255.0; // scale to [0 ... 255]

            rLUT[i] = (short) constrain((int) Math.round(cR));
            gLUT[i] = (short) constrain((int) Math.round(cG));
            bLUT[i] = (short) constrain((int) Math.round(cB));

//            System.out.print(rgbLUT[i] + " ");
        }

        ShortLookupTable shortLookupTable = new ShortLookupTable(0, rgbLUT);
        LookupOp lookupOp = new LookupOp(shortLookupTable, null);
        lookupOp.filter(inImg, outImg);

        return outImg;
    }
}

