package forms;

import forms.MyWindow;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class SNGame extends JFrame {
    public SNGame(MyWindow owner){
        setTitle("Options");
        setSize(350,250);
        setLocation(900,300);
        setResizable(false);
        setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
        JLabel jl1 = new JLabel("Игроки");
        jl1.setFont(new Font("Arial",Font.BOLD,16));
        add(jl1);
        JRadioButton jrb1 = new JRadioButton("Игрок vs Компьютер");
        JRadioButton jrb2 = new JRadioButton("Игрок vs Игрок");
        jrb1.setSelected(true);

        ButtonGroup bg = new ButtonGroup();
        bg.add(jrb1);
        bg.add(jrb2);
        add(jrb1);
        add(jrb2);


        JLabel jMapSizeLabel = new JLabel("Размер поля : 5x3");
        jMapSizeLabel.setFont(new Font("Arial",Font.BOLD,16));
        add(jMapSizeLabel);
        JSlider sliderX = new JSlider();
        JSlider sliderY = new JSlider();
        JSlider sliderWin = new JSlider();
        sliderX.setValue(5);
        sliderX.setMinimum(3);
        sliderX.setMaximum(25);
        sliderY.setValue(3);
        sliderY.setMinimum(3);
        sliderY.setMaximum(25);
        add(sliderX);
        add(sliderY);

        sliderX.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                jMapSizeLabel.setText("Рамер поля: "+sliderX.getValue()
                +"x" +sliderY.getValue());
                if (sliderX.getValue() <= sliderY.getValue()) sliderWin.setMaximum(sliderX.getValue());
                else sliderWin.setMaximum(sliderY.getValue());
            }
        });

        sliderY.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                jMapSizeLabel.setText(" Map size: "+sliderX.getValue()
                        +"x" +sliderY.getValue());
                if (sliderX.getValue() <= sliderY.getValue()) sliderWin.setMaximum(sliderX.getValue());
                else sliderWin.setMaximum(sliderY.getValue());
            }
        });

        JLabel jLineWin = new JLabel("Для победы линию из 3 фишек.");
        jLineWin.setFont(new Font("Arial",Font.BOLD,16));
        add(jLineWin);
        sliderWin.setValue(3);
        sliderWin.setMinimum(3);
        sliderWin.setMaximum(5);
        add(sliderWin);

        sliderWin.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                jLineWin.setText("Для победы линию из " + sliderWin.getValue()
                        +" фишек.");
            }
        });

        JButton jbOk = new JButton("Ok");
        add(jbOk);

        jbOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                owner.setEnabled(true);
                owner.newGame(sliderX.getValue(), sliderY.getValue(), sliderWin.getValue(), jrb2.isSelected());
                owner.jMap.repaint();
                setVisible(false);
            }
        });

    }
}
