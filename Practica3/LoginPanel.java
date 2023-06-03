import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {

    private JLabel nick;
    private JLabel info, welcome;
    private JTextField nickField;
    private JButton join;
    private GridBagConstraints grid;
    private Image backgroundImage;

    public LoginPanel() {
        super(new GridBagLayout());
        backgroundImage = new ImageIcon("./photos/fondo.jpg").getImage();
        startLogin();
    }

    public void startLogin() {

        setBackground(Color.LIGHT_GRAY);

        grid = new GridBagConstraints();

        // Introducir
        info = new JLabel("Insert your username:");
        info.setForeground(Color.WHITE);
        grid.weightx = 1.0;
        grid.weighty = 1.0;
        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.anchor = GridBagConstraints.SOUTHWEST;
        grid.insets = new Insets(40, 0, 0, 40);
        grid.gridx = 2;
        grid.gridy = 0;
        add(info, grid);

        // nick
        nick = new JLabel("Name:");
        nick.setFont(new Font("", Font.PLAIN, 15));
        nick.setForeground(Color.WHITE);
        grid.weightx = 1.0;
        grid.weighty = 0.0;
        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.anchor = GridBagConstraints.EAST;
        grid.insets = new Insets(0, 40, 0, 0);
        grid.gridx = 1;
        grid.gridy = 1;
        add(nick, grid);

        // Bienvenido
        welcome = new JLabel("WELCOME TO THE CHAT");
        welcome.setFont(new Font("", Font.BOLD, 25));
        welcome.setForeground(Color.WHITE);
        grid.weightx = 1.0;
        grid.weighty = 1.0;
        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.anchor = GridBagConstraints.CENTER;
        grid.insets = new Insets(40, 0, 0, 40);
        grid.gridx = 2;
        grid.gridy = 0;
        add(welcome, grid);

        // Nick Field
        nickField = new JTextField();
        grid.weightx = 1.0;
        grid.weighty = 0;
        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.anchor = GridBagConstraints.WEST;
        grid.insets = new Insets(5, 0, 0, 40);
        grid.gridx = 2;
        grid.gridy = 1;
        add(nickField, grid);

        // Entrar
        join = new JButton("Join");
        grid.weightx = 0.0;
        grid.weighty = 0.0;
        grid.fill = GridBagConstraints.NONE;
        grid.anchor = GridBagConstraints.NORTH;
        grid.gridx = 2;
        grid.gridy = 2;
        grid.insets = new Insets(15, 0, 40, 40);
        add(join, grid);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the background image
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }

    public JTextField getNicknameField() {
        return nickField;
    }

    public JButton getJoinButton() {
        return join;
    }
}
