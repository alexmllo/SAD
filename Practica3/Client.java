import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Client {
    private static final int port = 8888;

    private static String userName;
    private static ChatFrame chatFrame;
    private static MySocket mySocket;

    public static void main(String[] args) {
        chatFrame = new ChatFrame();
        chatFrame.getLoginPanel().getNicknameField().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                chatFrame.getLoginPanel().getJoinButton().doClick();
            }
        });

        chatFrame.getLoginPanel().getJoinButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                userName = chatFrame.getLoginPanel().getNicknameField().getText();
                if (userName.isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                            "You must specify an username",
                            "Empty username",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    connectToServer();
                }

            }
        });
    }

    public static void setUpChat(String connection) {
        chatFrame.setupChatPanel(userName);
        chatFrame.getChatPanel().getChatText().append(connection);

        // Send Button clicked
        chatFrame.getChatPanel().getSendButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String message = chatFrame.getChatPanel().getMessageField().getText();
                if (message.isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                            "You can't send an empty message!",
                            "Empty Message",
                            JOptionPane.ERROR_MESSAGE);

                } else {
                    mySocket.printLine(message);
                    DateFormat dateFormat = new SimpleDateFormat("HH:mm");
                    chatFrame.getChatPanel().getChatText()
                            .append("\n" + dateFormat.format(new Date()) + " [" + userName + "]: " + message);
                    chatFrame.getChatPanel().getMessageField().setText("");
                }
            }
        });

        // Disconnect button clicked
        chatFrame.getChatPanel().getDisconnectButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                mySocket.close();
                chatFrame.dispose();
                System.exit(0);
            }
        });
    }

    public static void connectToServer() {
        mySocket = new MySocket("localhost", port);
        mySocket.printLine(userName);

        // For info comming from the server
        Thread outputThread = new Thread(() -> {
            String line;
            while ((line = mySocket.readLine()) != null) {
                if (line.contains("You joined the chat")) {
                    String str = mySocket.readLine();
                    setUpChat(line + "\n" + str);

                    String users = mySocket.readLine();
                    while (users.contains(";")) {
                        String user;
                        if (users.indexOf(";", 1) != -1) {
                            user = users.substring(1, users.indexOf(";", 1));
                            users = users.substring(users.indexOf(";", 1));
                        } else {
                            user = users.substring(1);
                            users = "";
                        }
                        chatFrame.getChatPanel().getUsersList().addElement(user);
                        chatFrame.revalidate();
                        chatFrame.repaint();
                    }

                } else if (line.contains("[ NEW USER ]")) {
                    String str = line.substring(line.indexOf("]") + 1);
                    String str2 = str.substring(str.indexOf("[") + 1, str.indexOf("]"));
                    chatFrame.getChatPanel().getUsersList().addElement(str2);
                    chatFrame.getChatPanel().getChatText().append("\n" + str);

                } else if (line.contains("(EXIT)")) {
                    String str = line.substring(line.indexOf(")") + 1);
                    String str2 = str.substring(str.indexOf("]") + 3, str.indexOf("h") - 1);
                    chatFrame.getChatPanel().getUsersList().removeElement(str2);
                    chatFrame.getChatPanel().getChatText().append("\n" + str);

                } else {
                    chatFrame.getChatPanel().getChatText().append("\n" + line);
                }
            }
        });
        outputThread.start();
    }
}