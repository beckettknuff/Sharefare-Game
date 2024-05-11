import java.awt.*;

//Player class (https://github.com/SethuEapen/2D-Air-Hockey/blob/master/airHockey/src/main/Player.java)
class Player {
    public int x, y;  // coordinates for the center of the mallet
    private final int radius = 25;  // size of mallet
    private final int screenWidth, screenHeight;  // dimensions of the rink to stay in bounds
    private Color color;  // color of the mallet


    //initializes a new Player (mallet)
    public Player(int startX, int startY, Color color, int screenWidth, int screenHeight) {
        this.x = startX;  //set the initial horizontal position
        this.y = startY;  // and set the initial vertical position
        this.color = color;  // color
        this.screenWidth = screenWidth;  // width bounds
        this.screenHeight = screenHeight;  // length bounds
    }

    // moves the mallet within the bounds of the game area
    public void move(int dx, int dy) {
        int newX = x + dx;  //new horizontal position
        int newY = y + dy;  //and vertical position

        // check boundaries to ensure the mallet stays within the visible game area
        //horizontal movement boundary check
        if (newX - radius >= 0 && newX + radius <= screenWidth) {
            x = newX;  // new x cord if new position is within horizontal bounds
        }
        // Vertical movement boundary check
        if (newY - radius >= 0 && newY + radius <= screenHeight) {
            y = newY;  // new y cord if new position is within vertical bounds
        }
    }

    // draws the mallet
    public void draw(Graphics g) {
        g.setColor(color);  //set mallets to each color of menlo
        g.fillOval(x - radius, y - radius, 2 * radius, 2 * radius);  // Draw an oval representing the mallet
    }

    // returns the bounding rectangle of the mallet for collision detection
    public Rectangle getBounds() {
        return new Rectangle(x - radius, y - radius, 2 * radius, 2 * radius);
    }
}

