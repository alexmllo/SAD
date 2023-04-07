import java.util.concurrent.ConcurrentHashMap;

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
            clientSocket.printLine("[ SERVER ]: Introduce your nick: ");
            String clientNick = clientSocket.readLine();
            while (clientsMap.containsKey(clientNick)) {
                clientSocket.printLine(("[ SERVER ]: That nick is already in use.\nPlease introduce another nick: "));
                clientNick = clientSocket.readLine();
            }
            clientSocket.printLine("*** You joined the chat ***");
            /* Message only for the server */
            System.out.println("[" + clientNick + "] joined the chat");
            /* Message for all the clients */
            for (ConcurrentHashMap.Entry<String, MySocket> entry : clientsMap.entrySet()) {
                if (!entry.getKey().equals(clientNick)) {
                    entry.getValue().printLine("[ SERVER ]: " + clientNick + " joined the chat");
                }
            }
            final String finalNick = clientNick;
            clientsMap.put(clientNick, clientSocket);
            new Thread(() -> {
                /* Message only for the client that just joined the chat */
                String line;
                while ((line = clientSocket.readLine()) != null) {
                    for (ConcurrentHashMap.Entry<String, MySocket> entry : clientsMap.entrySet()) {
                        if (!entry.getKey().equals(finalNick)) {
                            entry.getValue().printLine("[ " + finalNick + " ]: " + line);
                        }
                    }
                }
                clientSocket.close();
                clientsMap.remove(finalNick);
                /* Message for the server */
                System.out.println("[" + finalNick + "] has left.");
                /* Message for the clients */
                for (ConcurrentHashMap.Entry<String, MySocket> entry : clientsMap.entrySet()) {
                    if (!entry.getKey().equals(finalNick)) {
                        entry.getValue().printLine("[ SERVER ]: " + finalNick + " has left");
                    }
                }
            }).start();

        }
    }
}