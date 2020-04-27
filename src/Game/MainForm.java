package Game;

import javax.swing.*;
import java.awt.*;

public class MainForm extends JFrame {

    private final MainGameField gameField;
    private final JLabel playerNumber;
    private final JLabel whosMove;
    private final JLabel waitingPlayer;

    public MainForm() {

        setTitle("Connect6 Game");
        setBounds(300, 300, 475, 525);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        gameField = MainGameField.getInstance();
        add(gameField, BorderLayout.CENTER);

        JPanel panel = new JPanel(new GridLayout());
        add(panel, BorderLayout.SOUTH);
        playerNumber = new JLabel("You are player #");
        whosMove = new JLabel("");
        panel.add(playerNumber);
        panel.add(whosMove);

        waitingPlayer = new JLabel("Waiting another player...");
        panel.add(waitingPlayer);

        setVisible(true);
    }

    public void setPlayerFirst(boolean first) {
        if (first) {
            gameField.setPlayerNum(0);
            playerNumber.setText("You are player #" + Integer.toString(1));
            whosMove.setText("Your move!");
        } else {
            gameField.setPlayerNum(1);
            playerNumber.setText("You are player #" + Integer.toString(2));
            whosMove.setText("Opponent's move");
        }
    }

    public void startGame() {
        gameField.startNewGame();
        waitingPlayer.setVisible(false);
    }

    public void move( int x, int y, int playerNum) {
        gameField.setXY(x, y);
        gameField.game(playerNum);

        if (gameField.getPlayerNum() == 0 & gameField.playerIsShotReady() != 0)
            whosMove.setText("Your move!");
        if (gameField.getPlayerNum() == 1 & gameField.playerIsShotReady() == 0)
            whosMove.setText("Opponent's move!");

        if (gameField.getPlayerNum() == 1 & gameField.playerIsShotReady() != 0)
            whosMove.setText("Your move!");
        if (gameField.getPlayerNum() == 0 & gameField.playerIsShotReady() == 0)
            whosMove.setText("Opponent's move");
    }
}