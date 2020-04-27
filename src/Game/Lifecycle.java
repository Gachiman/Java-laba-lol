package Game;

import java.util.Scanner;

public class Lifecycle {
    private final MainForm mainForm;
    private final Scanner inMessage;


    public Lifecycle(MainForm mainForm, Scanner inMessage) {
        this.mainForm = mainForm;
        this.inMessage = inMessage;
    }

    public void start() {
        while (true) {
            if (inMessage.hasNext()) {
                String inMsg = inMessage.nextLine();
                switch (inMsg) {
                    case "Player 1":
                        mainForm.setPlayerFirst(true);
                        break;

                    case "Player 2":
                        mainForm.setPlayerFirst(false);
                        break;

                    case "Start game":
                        mainForm.startGame();
                        break;

                    default:
                        String[] data = inMsg.split("/");
                        int x = Integer.parseInt(data[0]);
                        int y = Integer.parseInt(data[1]);
                        int playerNum = Integer.parseInt(data[2]);

                        mainForm.move(x, y, playerNum);
                        break;
                }
            }
        }
    }
}