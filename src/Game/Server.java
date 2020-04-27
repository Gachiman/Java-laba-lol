package Game;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class Server {
    private static final int PORT = 6666;
    private ArrayList<ClientHandler> clients = new ArrayList<ClientHandler>();

    public static void main(String[] args) {
        Server server = new Server();
    }

    public Server() {
        Socket clientSocket = null;
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(PORT);

            while (true) {
                clientSocket = serverSocket.accept();
                ClientHandler client = new ClientHandler(clientSocket, this);
                clients.add(client);

                if (clients.size() == 2) {
                    new Thread(clients.get(0)).start();
                    clients.get(0).sendMsg("Player 1");
                    new Thread(clients.get(1)).start();
                    clients.get(1).sendMsg("Player 2");

                    this.sendMessageToAllClients("Start game");
                }
            }
        }
        catch (IOException ex){
        }

        try {
            clientSocket.close();
            serverSocket.close();
        }
        catch (IOException ex) {
        }
    }

    public void sendMessageToAllClients(String msg) {
        for (ClientHandler lol: clients)
            lol.sendMsg(msg);
    }
}