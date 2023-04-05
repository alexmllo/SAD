import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServerSocket {
    
    private ServerSocket serverSocket;

    public MyServerSocket(int port){
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println("Could not bind the port");
            e.printStackTrace();
        }
    }

    /* Needs to return a MySocket */
    /* Create a new MySocket passing a Socket */
    public MySocket accept(){
        try {
            return new MySocket(this.serverSocket.accept());
        } catch (Exception e) {
            System.err.println("Error accepting connection");
            e.printStackTrace();
        }
        return null;
    }

    public void close(){
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            System.err.println("Error closing server socket");
            e.printStackTrace();
        }
    }
}
