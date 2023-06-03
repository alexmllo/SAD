
import javax.swing.*;
import java.awt.*;

public class ChatPanel extends JPanel {
    private final JTextArea chatText;
    private final JButton send;
    private final JButton disconnect;
    private final JTextField messageField;
    private final DefaultListModel usersListModel;
    private final JList<String> users;
    private final GridBagConstraints grid;
    private Image backgroundImage;
    private JLabel text1;

    public ChatPanel(String connectionString) {
        super(new GridBagLayout());
        chatText = new JTextArea(connectionString);
        send = new JButton("Send");
        disconnect = new JButton("Disconnect");
        messageField = new JTextField();
        grid = new GridBagConstraints();
        usersListModel = new DefaultListModel<>();
        users = new JList<>(usersListModel);
        backgroundImage = new ImageIcon("./photos/fondo3.jpg").getImage();

        setupGUI();
    }

    private void setupGUI() {

        text1 = new JLabel("MESSAGES:");
        text1.setFont(new Font("", Font.PLAIN, 25));
        text1.setForeground(Color.WHITE);
        grid.weightx = 1.0;
        grid.weighty = 0.0;
        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.anchor = GridBagConstraints.WEST;
        grid.insets = new Insets(10, 40, 0, 0);
        grid.gridx = 0;
        grid.gridy = 0;
        add(text1, grid);

        grid.gridx = 0;
        grid.gridy = 0;
        grid.gridheight = 2;
        grid.weightx = 1.0;
        grid.weighty = 1.0;
        grid.fill = GridBagConstraints.BOTH;
        grid.anchor = GridBagConstraints.CENTER;
        grid.insets = new Insets(40, 40, 10, 10);
        chatText.setEditable(false);
        chatText.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(chatText);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, grid);

        grid.gridy = 1;
        grid.gridx = 1;
        grid.gridheight = 1;
        grid.weightx = 0.0;
        grid.weighty = 0.0;
        grid.fill = GridBagConstraints.VERTICAL;
        grid.anchor = GridBagConstraints.NORTHEAST;
        grid.insets = new Insets(0, 10, 10, 40);
        grid.ipadx = 50;
        users.setLayoutOrientation(JList.VERTICAL);
        users.setBackground(Color.LIGHT_GRAY);
        add(new JScrollPane(users), grid);

        grid.gridx = 0;
        grid.gridy = 2;
        grid.weightx = 1.0;
        grid.weighty = 0.0;
        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.anchor = GridBagConstraints.NORTH;
        grid.insets = new Insets(10, 40, 40, 10);
        grid.ipady = 10;
        grid.ipadx = 0;
        add(messageField, grid);

        grid.gridx = 1;
        grid.gridy = 2;
        grid.weightx = 0;
        grid.weighty = 0;
        grid.ipady = 0;
        grid.fill = GridBagConstraints.NONE;
        grid.anchor = GridBagConstraints.WEST;
        grid.insets = new Insets(15, 15, 40, 40);
        grid.ipadx = 0;
        add(send, grid);

        grid.gridx = 1;
        grid.gridy = 2;
        grid.weightx = 0;
        grid.weighty = 0;
        grid.fill = GridBagConstraints.NONE;
        grid.anchor = GridBagConstraints.SOUTHWEST;
        grid.insets = new Insets(15, 15, 15, 15);
        add(disconnect, grid);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the background image
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }

    public JButton getSendButton() {
        return send;
    }

    public JButton getDisconnectButton() {
        return disconnect;
    }

    public JTextArea getChatText() {
        return chatText;
    }

    public JTextField getMessageField() {
        return messageField;
    }

    public DefaultListModel getUsersList() {
        return usersListModel;
    }
}