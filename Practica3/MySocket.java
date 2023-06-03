import java.io.*;
import java.net.Socket;

public class MySocket {

    private Socket mySocket;
    private BufferedReader in;
    private PrintWriter out;

    public MySocket(String host, int port) {
        try {
            this.mySocket = new Socket(host, port);
            this.in = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
            this.out = new PrintWriter(mySocket.getOutputStream(), true);
        } catch (IOException e) {
            System.err.println("Error creating the socket");
            e.printStackTrace();
        }
    }

    /* Necessary for the MyServerSocket class in the accept method */
    public MySocket(Socket sock) {
        try {
            this.mySocket = sock;
            this.in = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
            this.out = new PrintWriter(mySocket.getOutputStream(), true);
        } catch (Exception e) {
            System.err.println("Error creating the socket");
            e.printStackTrace();
        }
    }

    public String readLine() {
        try {
            return in.readLine();
        } catch (IOException e) {
            System.err.println("Error reading the socket");
            e.printStackTrace();
        }
        return null;
    }

    public void printLine(String s) {
        try {
            out.println(s);
        } catch (Exception e) {
            System.err.println("Error writting the socket");
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            this.mySocket.close();
        } catch (IOException e) {
            System.err.println("Error closing the socket");
            e.printStackTrace();
        }
    }
}
