package myClass;

import org.jetbrains.annotations.Contract;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by Владимир on 06.08.2016.
 */
public final class XOGames {
    private byte[][] field;
    private int lengthFieldX;
    private int lengthFieldY;
    private int lineWin;
    private int movesResidue;
    private Random rand = new Random();

    public XOGames(int lengthFieldX, int lengthFieldY, int lineWin) {  //конструктор
        this.lengthFieldX = lengthFieldX;
        this.lengthFieldY = lengthFieldY;

        //Проверяем коректность длины выиграшной последовательности
        if (lengthFieldX <= lengthFieldY) this.lineWin = (lineWin > lengthFieldX) ? lengthFieldX : lineWin;
        else this.lineWin = (lineWin > lengthFieldY) ? lengthFieldY : lineWin;

        field = new byte[lengthFieldY][lengthFieldX];
        for (int i = 0; i < lengthFieldY; i++) {
            Arrays.fill(field[i], (byte) 0);
        }

        movesResidue = movesResidue();
    }

    private boolean isCellInspection(int x, int y) {   //проверка на занятость клетки

        if ((x >= lengthFieldY) || (y >= lengthFieldX)) return true;
        switch (field[x][y]) {
            case 1:
                return true;
            case 2:
                return true;
            default:
                return false;
        }
    }

    public boolean humanTurn(int x, int y, byte numberPlayer) {

        if (!isCellInspection(y, x) && ((numberPlayer == 1) || (numberPlayer == 2))) {
            field[y][x] = numberPlayer;
            movesResidue--;
            return true;
        }
        return false;
    }

    public void aiTurn() {
        int x, y;

        if (movesResidue == 0) return;
        if (!aiAnalitic()) {
            do {
                x = rand.nextInt(lengthFieldX);
                y = rand.nextInt(lengthFieldY);

            } while (isCellInspection(y, x));
            field[y][x] = 2;
        }
        movesResidue--;
    }

    public boolean aiAnalitic (){
        //метод перебора клеток на выигреш 1 игрока

        for (int i = 0; i < lengthFieldY; i++) {
            for (int j = 0; j < lengthFieldX; j++) {
                if (field[i][j] == 0){
                    field[i][j] = 1;
                    if (checkWin((byte) 1) == 1){
                        field[i][j] = 2;
                        return true;
                    }
                    field[i][j] = 0;
                }
            }
        }
        return false;
    }
    public byte getCellValue(int x, int y){
        return field[y][x];
    }

    public String getValue(int x, int y){

        if ((x >= lengthFieldY) || (y >= lengthFieldX)) return "Координаты вне поля";
        switch (field[x][y]) {
            case 1:
                return "Клетка уже занята - игрок1";
            case 2:
                return "Клетка уже занята - игрок2";
            default:
                return new String("Координаты " + x + "x" + y);
        }
    }

    public void printField() {
        //Выводим поле на экран

        for (int i = 0; i < lengthFieldY; i++) {
            for (int j = 0; j < lengthFieldX; j++) {
                System.out.print(field[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private int movesResidue(){      //Подсчет оставшихся ходов
        int res = 0;
        for (int i = 0; i < lengthFieldY; i++) {
            for (int j = 0; j < lengthFieldX; j++) {
                if (field[i][j] == 0) res++;
            }
        }

        return res;
    }

    public int getMovesResidue() {
        return movesResidue;
    }

    private boolean checkLine(int cx, int cy, int vx, int vy, int l, byte players) {
        for (int i = 0; i < l; i++) {
            if (field[cx + i * vy][cy + i * vx] != players) return false;
        }
        return true;
    }

    public byte checkWin(byte player) {

        for (int i = 0; i < lengthFieldY; i++) {
            // проверяем горизонтальные линии
            for (int j = 0; j < lengthFieldX; j++) {
                if ((lengthFieldX - j) < lineWin) break;
                if (checkLine(i, j, 1, 0, lineWin, player)) return player;
            }
        }

        for (int i = 0; i < lengthFieldY; i++) {
            // проверяем вертикальные линии
            if ((lengthFieldY - i) < lineWin) break;
            for (int j = 0; j < lengthFieldX; j++) {
                if (checkLine(i, j, 0, 1, lineWin, player)) return player;
            }
        }

        for (int i = 0; i < lengthFieldY; i++) {
            // проверяем диагональные сверху-вправо линии
            if ((lengthFieldY - i) < lineWin) break;
            for (int j = 0; j < lengthFieldX; j++) {
                if ((lengthFieldX - j) < lineWin) break;
                if (checkLine(i, j, 1, 1, lineWin, player)) return player;
            }
        }

        for (int i = lengthFieldY - 1; i > 0; i--) {
            // проверяем диагональные снизу-вправо линии
            if (i < lineWin) break;
            for (int j = 0; j < lengthFieldX; j++) {
                if ((lengthFieldX - j) < lineWin) break;
                if (checkLine((i), j, 1, -1, lineWin, player)) return player;
            }
        }

        if (movesResidue > 0) return (byte) 0;

        return (byte)-1;
    }
}


