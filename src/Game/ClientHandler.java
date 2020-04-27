package Game;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Runnable {
    private Server server;
    private PrintWriter outMes;
    private Scanner inMes;

    public ClientHandler(Socket socket, Server server) {
        try {
            this.server = server;
            this.outMes = new PrintWriter(socket.getOutputStream());
            this.inMes = new Scanner(socket.getInputStream());
        }
        catch (IOException ex) {
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (inMes.hasNext()) {
                    String clientMessage = inMes.nextLine();
                    System.out.println(clientMessage);
                    server.sendMessageToAllClients(clientMessage);
                }
                Thread.sleep(100);
            }
        }
        catch (InterruptedException ex) {
        }
    }

    public void sendMsg(String msg) {
        try {
            outMes.println(msg);
            outMes.flush();
        }
        catch (Exception ex) {
        }
    }
}