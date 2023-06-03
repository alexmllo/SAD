import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JOptionPane;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Server {
    private static final ConcurrentHashMap<String, MySocket> clientsMap = new ConcurrentHashMap<>();
    private static final int PORT = 8888;

    public static void main(String[] args) {
        MyServerSocket serverSocket = new MyServerSocket(PORT);
        System.out.println("Server listening");
        while (true) {
            /* Messages only for the client that connects now */
            /* Accept method blocks until a client connects */
            MySocket clientSocket = serverSocket.accept();
            String clientNick = clientSocket.readLine();
            while (clientsMap.containsKey(clientNick) || clientNick.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null,
                "That nick is already in use. Please introduce another nick",
                "DUPLICATED USER",
                JOptionPane.ERROR_MESSAGE);
                clientNick = clientSocket.readLine();
            }
            clientsMap.put(clientNick, clientSocket);
            String line1 = "*** You joined the chat ***\n";
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            DateFormat hourFormat = new SimpleDateFormat("HH:mm");
            String date = dateFormat.format(new Date());
            String hour = hourFormat.format(new Date());
            clientSocket.printLine(line1 + "Connected at " + date);
            /* Message only for the server */
            System.out.println(hour + " [" + clientNick + "] joined the chat");

            /* Message of connected clients */
            String usersList = "";
            for (ConcurrentHashMap.Entry<String, MySocket> entry : clientsMap.entrySet()) {
                usersList = usersList + ";" + entry.getKey();
                if (!entry.getKey().equals(clientNick)) {
                    entry.getValue().printLine("[ NEW USER ] " + hour + " [" + clientNick + "] joined the chat");
                }
            }
            if(clientsMap.get(clientNick) != null){
                clientSocket.printLine(usersList);
            }
            final String finalNick = clientNick;
            new Thread(() -> {
                /* Message only for the client that just joined the chat */
                String line;
                while ((line = clientSocket.readLine()) != null) {
                    for (ConcurrentHashMap.Entry<String, MySocket> entry : clientsMap.entrySet()) {
                        if (!entry.getKey().equals(finalNick)) {
                            entry.getValue().printLine(hour + " [ " + finalNick + " ]: " + line);
                        }
                    }
                }
                /* Message for the server */
                System.out.println(hour + " [" + finalNick + "] has left.");
                /* Message for the clients */
                for (ConcurrentHashMap.Entry<String, MySocket> entry : clientsMap.entrySet()) {
                    if (!entry.getKey().equals(finalNick)) {
                        entry.getValue().printLine("(EXIT)" + hour + " [ SERVER ]: " + finalNick + " has left");
                    }
                }
                clientsMap.remove(finalNick);
            }).start();

        }
    }
}