import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

// extend JPanel and implements KeyListener for handling keyboard input
class GamePanel extends JPanel implements KeyListener {
    private Image background; // arena image for the rink
    private Puck puck; // puck object
    private Player player1, player2; // both player objects
    private int currentImageIndex = 0; //index to track which background image is being used
    private int screenWidth, screenHeight; // dimensions of the screen
    private boolean[] keys; // array to track which keys are currently pressed

    // Constructor of the GamePanel, initializes the game environment
    public GamePanel(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        keys = new boolean[256]; // Array covering all key codes (i did this to allow the user to customize their bindings)
        loadBackground();
        setFocusable(true);
        addKeyListener(this);
        requestFocusInWindow();  // request focus to receive key inputs

        // Initialize puck and players with the proper positions and colors
        puck = new Puck(215, 390, screenWidth, screenHeight);
        player1 = new Player(215, 680, new Color(255, 215, 0), screenWidth, screenHeight); // Gold color for player 1
        player2 = new Player(215, 100, new Color(0, 0, 128), screenWidth, screenHeight); // Navy blue color for player 2

        // Timer to periodically request focus and update game state
        Timer focusTimer = new Timer(1000, e -> requestFocusInWindow());
        focusTimer.start();

        // Timer state 60 times per second (rendering https://stackoverflow.com/questions/69367971/how-to-make-smoother-movement-in-my-java-code)
        Timer gameTimer = new Timer(1000 / 60, e -> updateGame());
        gameTimer.start();
    }

    // Loads the initial background image
    private void loadBackground() {
        updateBackground();
    }

    // update the background image based on the current image index (goals scored)
    private void updateBackground() {
        try {
            //must be caught or thrown
            background = ImageIO.read(new File("Resources/arena" + (currentImageIndex + 1) + ".png"));
        } catch (Exception e) {
            System.out.println("error with rink. " + e.getMessage());
        }
    }

    // Main game loop that updates game objects, checks for collisions and repaints the panel
    private void updateGame() {
        movePlayers();
        puck.update();
        checkCollision();
        checkGoal();
        repaint();
    }

    // Paints puck and players on the panel
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (background != null) {
            g.drawImage(background, 0, 0, this);
        }
        puck.draw(g);
        player1.draw(g);
        player2.draw(g);
    }

    // checks if a goal has been scored by either player
    private void checkGoal() {
        if (puck.y <= 10 && puck.x >= screenWidth / 2 - 50 && puck.x <= screenWidth / 2 + 50) {
            scoreGoal();
        } else if (puck.y >= screenHeight - 20 && puck.x >= screenWidth / 2 - 50 && puck.x <= screenWidth / 2 + 50) {
            scoreGoal();
        }
    }

    //paint background and resets the puck when a goal is scored
    private void scoreGoal() {
        currentImageIndex = (currentImageIndex + 1) % 5;
        updateBackground();
        puck.reset();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

    // Moves the players based on their pressed keys
    private void movePlayers() {
        int moveAmount = 5;
        if (keys[KeyEvent.VK_W]) player1.move(0, -moveAmount);
        if (keys[KeyEvent.VK_S]) player1.move(0, moveAmount);
        if (keys[KeyEvent.VK_A]) player1.move(-moveAmount, 0);
        if (keys[KeyEvent.VK_D]) player1.move(moveAmount, 0);
        if (keys[KeyEvent.VK_UP]) player2.move(0, -moveAmount);
        if (keys[KeyEvent.VK_DOWN]) player2.move(0, moveAmount);
        if (keys[KeyEvent.VK_LEFT]) player2.move(-moveAmount, 0);
        if (keys[KeyEvent.VK_RIGHT]) player2.move(moveAmount, 0);
    }

    // Checks for collisions between the puck and the players (https://stackoverflow.com/questions/15690846/java-collision-detection-between-two-shape-objects)
    private void checkCollision() {
        Rectangle puckBounds = puck.getBounds();
        if (puckBounds.intersects(player1.getBounds())) {
            puck.collideWithMallet(new Point(player1.x, player1.y));
        } else if (puckBounds.intersects(player2.getBounds())) {
            puck.collideWithMallet(new Point(player2.x, player2.y));
        }
    }
}



