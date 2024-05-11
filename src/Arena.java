import javax.swing.*;

public class Arena extends JFrame {
    public Arena() {
        // Frame dimensions and requirements
        setTitle("NHL Zero");
        setSize(450, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        GamePanel gamePanel = new GamePanel(this.getWidth(), this.getHeight());
        add(gamePanel);
        setFocusable(true); // https://stackoverflow.com/questions/14095018/setfocusable-method-or-focusing-components-java
        setFocusTraversalKeysEnabled(false);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Arena()); // https://stackoverflow.com/questions/17672886/swing-gui-one-simple-thing
    }
}