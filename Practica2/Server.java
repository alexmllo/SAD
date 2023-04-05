import java.util.concurrent.ConcurrentHashMap;

public class Server{
    private static final ConcurrentHashMap<String, MySocket> clientsMap = new ConcurrentHashMap<>();
    private static final int PORT = 8888;

    public static void main(String[] args){
        MyServerSocket serverSocket = new MyServerSocket(PORT);
        System.out.println("Server listening");
        while(true){
            /* Messages only for the client that connects now */
             MySocket clientSocket = serverSocket.accept();
             clientSocket.printLine("[ SERVER ]: Introduce your nick: ");
             String clientNick = clientSocket.readLine();
             if(clientsMap.containsKey(clientNick)){
                clientSocket.printLine(("[ SERVER ]: That nick is already in use.\nPlease introduce another nick: "));
             }
             else{
                clientSocket.printLine("*** You joined the chat ***");
                /* Message only for the server */
                System.out.println("[ " + clientNick + " ] joined the chat");
                /* Message for all the clients */
                for (ConcurrentHashMap.Entry<String, MySocket> entry : clientsMap.entrySet()) {
                    if (!entry.getKey().equals(clientNick)) {
                        entry.getValue().printLine("[ SERVER ]: " + clientNick + " joined the chat");
                    }
                }
                clientsMap.put(clientNick, clientSocket);
                new Thread(() -> {
                    /* Message only for the client that just joined the chat */
                    while(true){
                        String line = clientSocket.readLine();
                        if(line == null) break;
                        for (ConcurrentHashMap.Entry<String, MySocket> entry : clientsMap.entrySet()) {
                            if (!entry.getKey().equals(clientNick)) {
                                entry.getValue().printLine("[" + clientNick + "]: " + line);
                            }
                        }
                    }
                    clientSocket.close();
                    clientsMap.remove(clientNick);
                    /* Message for the server */
                    System.out.println("[" + clientNick + "] has left.");
                     /* Message for the clients */
                     for (ConcurrentHashMap.Entry<String, MySocket> entry : clientsMap.entrySet()) {
                        if (!entry.getKey().equals(clientNick)) {
                            entry.getValue().printLine("[ SERVER ]: " + clientNick + " has left");
                        }
                    }
                }).start();
             }
        }
    }
}