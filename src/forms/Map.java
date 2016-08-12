package forms;

import javax.swing.*;

import myClass.XOGames;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Владимир on 06.08.2016.
 */
public class Map extends JPanel {

    private XOGames field;
    private int lineX;
    private int lineY;
    //private int lineWin;
    private int sizeCell;
    private byte players;
    private boolean pvp;
    private MyWindow owner;
    private String movesResidue = "Осталось ходов - ";
    private String lastCoordinates = "Ход Игорка";
    private String gameOverMessage;
    private boolean win;

    public Map(MyWindow owner) {
        lineX = 0;
        lineY = 0;

        this.owner = owner;
        owner.jlMovesResidue.setText("Начните игру");
        owner.jlInfo.setText(" ");

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if ((lineX != 0) && (lineY !=0) && (field.getMovesResidue() != 0) && (!win)){
                    if (field.humanTurn(((int)(e.getX()/sizeCell)), ((int)(e.getY()/sizeCell)), players)){
                        switch (field.checkWin(players)){
                            case 1:
                                win = true;
                                owner.jlInfo.setText("ПОБЕДА");
                                gameOverMessage = "Победил игрок " + players;
                                repaint();
                                return;
                            case 2:
                                win = true;
                                owner.jlInfo.setText("ПОБЕДА");
                                gameOverMessage = "Победил игрок " + players;
                                repaint();
                                return;
                            case -1:
                                win = true;
                                owner.jlInfo.setText("НИЧЬЯ");
                                gameOverMessage = "Ничья";
                                repaint();
                                return;
                        }
                        players = (byte) (3 - players);
                        owner.jlInfo.setText(lastCoordinates + players);
                        owner.jlMovesResidue.setText(movesResidue + field.getMovesResidue());
                        repaint();

                        if (!pvp){
                            /*try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e1) {
                                e1.printStackTrace();
                            }*/
                            field.aiTurn();
                            switch (field.checkWin(players)){
                                case 2:
                                    win = true;
                                    owner.jlInfo.setText("ПОБЕДА");
                                    gameOverMessage = "Победил компьютер";
                                    repaint();
                                    return;
                                case -1:
                                    win = true;
                                    owner.jlInfo.setText("НИЧЬЯ");
                                    gameOverMessage = "Ничья";
                                    repaint();
                                    return;
                            }
                            players = (byte) (3 - players);
                            owner.jlInfo.setText(lastCoordinates + players);
                            owner.jlMovesResidue.setText(movesResidue + field.getMovesResidue());
                            repaint();
                        }
                    }
                }
            }
        });

        setVisible(false);
    }

    public void newMap(int lineX, int lineY, int lineWin,  int sizeCell, boolean pvp) {
        this.lineX = lineX;
        this.lineY = lineY;
        this.sizeCell = sizeCell;
        this.pvp = pvp;
        players = 1;

        field = new XOGames(lineX, lineY, lineWin);
        setVisible(true);
        owner.jlMovesResidue.setText(movesResidue + field.getMovesResidue());
        owner.jlInfo.setText(lastCoordinates + players);
        win = false;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;

        g2D.setStroke(new BasicStroke(1));
        switch (players){
            case 1:
                g.setColor(Color.GREEN);
                break;
            case 2:
                g.setColor(Color.RED);
                break;
            default:
                g.setColor(Color.BLACK);
        }
        g.fillRect(0,0, lineX*sizeCell, lineY*sizeCell);
        g.setColor(Color.WHITE);
        g.fillRect(3,3, lineX*sizeCell-6, lineY*sizeCell-6);

        g.setColor(Color.BLACK);
        g.drawRect(0,0, lineX*sizeCell, lineY*sizeCell);
        g.drawRect(3,3, (lineX*sizeCell-6), (lineY*sizeCell-6));
        for (int i = 0; i < lineX; i++) g.drawLine(sizeCell*i, 3, sizeCell*i, sizeCell*lineY-3);
        for (int i = 0; i < lineY; i++) g.drawLine(3, sizeCell*i, sizeCell*lineX-3, sizeCell*i);

        g2D.setStroke(new BasicStroke(5));
        for (int i = 0; i < lineX; i++) {
            for (int j = 0; j < lineY; j++) {
                switch (field.getCellValue(i, j)) {
                    case 1:
                        g.setColor(Color.GREEN);
                        g.drawLine((i*sizeCell + 8), (j*sizeCell + 8), ((i + 1) * sizeCell - 8), ((j + 1) * sizeCell) - 8);
                        g.drawLine((i*sizeCell + 8), ((j + 1) * sizeCell - 8), ((i + 1) * sizeCell - 8), (j*sizeCell + 8));
                        break;
                    case 2:
                        g.setColor(Color.RED);
                        g.drawOval((i*sizeCell + 8), (j*sizeCell + 8), (sizeCell-16), (sizeCell-16));
                        break;
                }
            }
        }

        if(win){
            g.setColor(Color.DARK_GRAY);
            g.fillRect(0,230,this.getWidth(),60);
            g.setFont(new Font("Arial", Font.BOLD, 48));
            g.setColor(Color.black);
            g.drawString(gameOverMessage, 22,272);
            g.setColor(Color.yellow);
            g.drawString(gameOverMessage, 20,270);
        }
    }
}
