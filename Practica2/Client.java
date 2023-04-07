import java.io.*;

public class Client {
    public static final int PORT = 8888;
    public static final String HOST = "localhost";

    public static void main(String[] args) {

        MySocket clientSocket = new MySocket(HOST, PORT);

        /* INPUT */
        new Thread(() -> {
            String msg;
            String nick;
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

            try {
                /* Reads the name (nick) */
                nick = input.readLine();
                /* Sends it to the server throught the socket */
                clientSocket.printLine(nick);

                /* Reads the text message */
                while ((msg = input.readLine()) != null) {
                    clientSocket.printLine(msg);
                }
            } catch (Exception e) {
                System.err.println("Error reading input.");
                e.printStackTrace();
            }

        }).start();

        /* OUTPUT */
        new Thread(() -> {
            String msg;
            /* Reads the input of the clientSocket */
            while ((msg = clientSocket.readLine()) != null) {
                System.out.println(msg);
            }

        }).start();

    }
}