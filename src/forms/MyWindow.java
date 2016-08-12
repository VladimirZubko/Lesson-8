package forms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by Владимир on 06.08.2016.
 */
public class MyWindow extends JFrame {
    final static int MAX_SIZE_CELL = 50;

    Map jMap;
    SNGame snGame;
    JLabel jlMovesResidue = new JLabel("Игра еще не началась.");
    JLabel jlInfo = new JLabel("Начните новую игру.");

    public MyWindow(){
        super();
        setTitle("Крестики-Нолики");
        setSize(550,650);
        setResizable(false);
        setLocation(400,150);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);

        ActionListener actionListener = new BtnGroupActionListener();
        JButton jbStart = new JButton("Новая игра");
            jbStart.setActionCommand("strt");
            jbStart.addActionListener(actionListener);
            jbStart.setSize(150,35);
            jbStart.setLocation(10,10);
        JButton jbEnd = new JButton("Закончить игру");
            jbEnd.setActionCommand("end");
            jbEnd.addActionListener(actionListener);
            jbEnd.setSize(150,35);
            jbEnd.setLocation(170,10);
        add(jbStart);
        add(jbEnd);

        jlMovesResidue.setLocation(10, 600);
        jlMovesResidue.setSize(150,25);
        jlInfo.setLocation(400, 600);
        jlInfo.setSize(150,25);
        add(jlMovesResidue);
        add(jlInfo);

        jMap = new Map(this);
        jMap.setLocation(10, 55);
        jMap.setSize((getWidth()-20), (getHeight()-120));
        add(jMap);

        snGame = new SNGame(this);

        setVisible(true);
    }

    public void newGame (int lineX, int lineY,int lineWin, boolean pvp) {
        int sizeCell = MAX_SIZE_CELL;

        if (lineX >= lineY) {
            if (jMap.getWidth()/lineX < MAX_SIZE_CELL) sizeCell = (int) (jMap.getWidth()/ lineX);
        } else {
            if (jMap.getHeight()/ lineY < MAX_SIZE_CELL) sizeCell = (int) (jMap.getHeight()/ lineY);
        }

        jMap.newMap(lineX, lineY, lineWin, sizeCell, pvp);
    }

    public class BtnGroupActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()){
                case "strt":
                    snGame.setVisible(true);
                    setEnabled(false);
                    break;
                case "end":
                    System.exit(0);
                    break;
            }

        }
    }
}
