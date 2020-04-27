package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static Game.MainClient.sendMsg;

public class MainGameField extends JPanel {
    private static MainGameField instance = null;
    private static final int FIELD_SIZE = 456;
    private final String NOT_SIGN = "*";

    public boolean gameOver = false;
    private String overMessage = "";

    private static int linesCount = 19;
    private static int countToWin = 6;

    private int cellSize;
    private int x, y;
    public void setXY (int _x, int _y) { x = _x; y = _y; }

    private int myPlayerNum;
    public void setPlayerNum(int num) { myPlayerNum = num; }
    public int getPlayerNum() { return myPlayerNum; }
    private Player[] players = new Player[2];
    public String[][] cell;

    public static synchronized MainGameField getInstance() {
        if (instance == null)
            instance = new MainGameField();
        return instance;
    }

    void startNewGame() {
        gameOver = false;
        cellSize = FIELD_SIZE / linesCount;
        cell = new String[linesCount][linesCount];
        repaint();
        for (int i = 0; i < linesCount; i++) {
            for (int j = 0; j < linesCount; j++) {
                cell[i][j] = NOT_SIGN;
            }
        }
        setVisible(true);
    }

    private MainGameField() {
        setVisible(false);
        players[0] = new Player("X");
        players[1] = new Player("O");
        players[0].isShotReady = 1;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                x = e.getX() / cellSize;
                y = e.getY() / cellSize;

                sendMsg(Integer.toString(x) + "/" + Integer.toString(y) + "/" + myPlayerNum);
            }
        });
    }

    public int playerIsShotReady() {
        return players[myPlayerNum].isShotReady;
    }

    private boolean spotIsEmpty(int x, int y) {
        return cell[x][y].equals(NOT_SIGN);
    }

    public void game(int playerNum) {
        int anotherPlayerNum = 2;
        switch (playerNum) {
            case 0:
                anotherPlayerNum = 1;
                break;
            case 1:
                anotherPlayerNum = 0;
                break;
        }

        if (players[playerNum].isShotReady > 0 & players[anotherPlayerNum].isShotReady == 0)
            if (spotIsEmpty(x, y)) {
                players[playerNum].shot(x, y);
                players[playerNum].isShotReady--;
                if (players[playerNum].isShotReady == 0)
                    players[anotherPlayerNum].isShotReady = 2;
            }

        if (players[playerNum].win()) {
            gameOver = true;
            overMessage = "Player #" + (playerNum + 1) + " WIN!";
        }

        repaint();
    }

    boolean isCellBusy(int x, int y) {
        if (x < 0 || y < 0 || x > linesCount - 1 || y > linesCount - 1) {
            return false;
        }
        return !cell[x][y].equals(NOT_SIGN);
    }

    private boolean checkLine(int start_x, int start_y, int dx, int dy, String sign) {
        int count = 0;
        for (int i = 0; i < countToWin; i++)
            if (cell[start_x + i * dx][start_y + i * dy].equals(sign)) {
                count++;
                if (count == countToWin)
                    return true;
            }
        return false;
    }

    public boolean checkWin(String sign) {
        for (int i = 0; i <= linesCount - countToWin; i++)
            for (int j = 0; j < linesCount; j++) {
                if (checkLine(i, j, 1, 0, sign)) return true;
            }

        for (int i = 0; i < linesCount; i++)
            for (int j = 0; j <= linesCount - countToWin; j++) {
                if (checkLine(i, j, 0, 1, sign)) return true;
            }

        for (int i = 0; i <= linesCount - countToWin; i++)
            for (int j = 0; j <= linesCount - countToWin; j++) {
                if (checkLine(i, j, 1, 1, sign)) return true;
            }

        for (int i = countToWin - 1; i < linesCount; i++)
            for (int j = 0; j <= linesCount - countToWin; j++) {
                if (checkLine(i, j, -1, 1, sign)) return true;
            }

        return false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i <= this.linesCount; i++) {
            g.drawLine(0, i * this.cellSize, FIELD_SIZE, i * this.cellSize);
            g.drawLine(i * this.cellSize, 0, i * this.cellSize, FIELD_SIZE);
        }
        for (int i = 0; i < linesCount; i++) {
            for (int j = 0; j < linesCount; j++) {
                if (!cell[i][j].equals(NOT_SIGN)) {
                    if (cell[i][j].equals("X")) {
                        g.setColor(Color.BLACK);
                        g.fillOval(i * cellSize, j * cellSize, cellSize, cellSize);
                    }
                    if (cell[i][j].equals("O")) {
                        g.setColor(Color.BLUE);
                        g.fillOval((i * cellSize), (j * cellSize), cellSize, cellSize);
                    }
                }
            }
        }

        if (gameOver) {
            System.out.println(overMessage);
        }
    }
}