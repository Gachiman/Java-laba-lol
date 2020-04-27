package Game;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class MainClient {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 6666;

    private static Socket clientSoc;
    private static Scanner inMes;
    private static PrintWriter outMes;

    public static void main(String[] args) {
        try {
            clientSoc = new Socket(SERVER_HOST, SERVER_PORT);
            inMes = new Scanner(clientSoc.getInputStream());
            outMes = new PrintWriter(clientSoc.getOutputStream());
        }
        catch (IOException ex) {
        }
        MainForm clientWindow = new MainForm();
        new Lifecycle(clientWindow, inMes).start();
    }

    public static void sendMsg(String msg) {
        outMes.println(msg);
        outMes.flush();
    }

}