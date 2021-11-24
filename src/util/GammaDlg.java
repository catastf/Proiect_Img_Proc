package util;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Hashtable;

public class GammaDlg extends AbstractSettingsDialog {

    private JSlider slider1, slider2, slider3;
    private JTextField textField1, textField2, textField3;

    public GammaDlg() {
        super();
        setTitle("Gamma Contrast");

        mainPanel.setLayout(new GridLayout(3, 1));
// first slider
        JPanel panel1 = new JPanel();
        textField1 = new JTextField(5);
        slider1 = new JSlider(0, 500, 100);
        slider1.setPreferredSize(new Dimension(400, 50));
        slider1.setMinorTickSpacing(20);

        Hashtable<Integer, JLabel> lables = new Hashtable();
        lables.put(0, new JLabel("0.0"));
        lables.put(100, new JLabel("1.0"));
        lables.put(500, new JLabel("5.0"));


        slider1.setLabelTable(lables);

        slider1.setPaintLabels(true);
        slider1.setPaintTicks(true);

        panel1.add(textField1);
        panel1.add(slider1);

// second slider
        JPanel panel2 = new JPanel();
        textField2 = new JTextField(5);
        slider2 = new JSlider(0, 500, 100);
        slider2.setPreferredSize(new Dimension(400, 50));
        slider2.setMinorTickSpacing(20);

        //Hashtable<Integer,JLabel> lables = new Hashtable();
        lables.put(0, new JLabel("0.0"));
        lables.put(100, new JLabel("1.0"));
        lables.put(500, new JLabel("5.0"));

        slider2.setLabelTable(lables);

        slider2.setPaintLabels(true);
        slider2.setPaintTicks(true);

        panel2.add(textField2);
        panel2.add(slider2);

// third slider
        JPanel panel3 = new JPanel();
        textField3 = new JTextField(5);
        slider3 = new JSlider(0, 500, 100);
        slider3.setPreferredSize(new Dimension(400, 50));
        slider3.setMinorTickSpacing(20);

        //Hashtable<Integer,JLabel> lables = new Hashtable();
        lables.put(0, new JLabel("0.0"));
        lables.put(100, new JLabel("1.0"));
        lables.put(500, new JLabel("5.0"));

        slider3.setLabelTable(lables);

        slider3.setPaintLabels(true);
        slider3.setPaintTicks(true);

        panel3.add(textField3);
        panel3.add(slider3);

// add panels to main panel
        mainPanel.add(panel1);
        mainPanel.add(panel2);
        mainPanel.add(panel3);

        slider1.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                onSlide();
            }
        });
        slider2.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                onSlide();
            }
        });
        slider3.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                onSlide();
            }
        });
    }

    private void onSlide() {
        double valR = (double) slider1.getValue() / 100.0;
        textField1.setText("" + valR);
        double valG = (double) slider2.getValue() / 100.0;
        textField2.setText("" + valG);
        double valB = (double) slider3.getValue() / 100.0;
        textField3.setText("" + valB);


        BufferedImage img = ImageUtil.contrastGamma(originalImg, valR, valG, valB);
        imagePanel.setImage(img);

    }
}
