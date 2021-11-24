package main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static util.ImageUtil.*;

import util.GammaDlg;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;


public class Main {

    public static void main(String[] args) throws IOException {
// import file with FileChooser
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Select image");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JPEG", "jpeg", "jpg", "png", "bmp", "gif");
        fc.addChoosableFileFilter(filter);

        BufferedImage inputImg = null;

        if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fc.getSelectedFile();
            try {
                inputImg = ImageIO.read(selectedFile);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

// display original image
        displayImage(inputImg, "Original image");

        displayImage(redImg(inputImg), " Original red");
        displayImage(greenImg(inputImg), "Original green");
        displayImage(blueImg(inputImg), "Original blue");

// apply gamma settings
        BufferedImage testImg = applySettingsDlg(inputImg, new GammaDlg());
        displayImage(testImg, "Gamma Contrast");

        displayImage(redImg(testImg), "Gamma red");
        displayImage(greenImg(testImg), "Gamma green");
        displayImage(blueImg(testImg), "Gamma blue");



// save file
        JFileChooser fileChooser = new JFileChooser();
        int returnVal = fileChooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            ImageIO.write(testImg, "jpg", file);


        }
    }
}

