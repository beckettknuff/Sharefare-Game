import java.awt.*;

//the Puck class is the hockey puck
class Puck {
    public int x, y;  // puck position
    public int velocityX = 0, velocityY = 10; // velocity of the puck
    private final int size = 20;  // puck size
    private final int screenWidth, screenHeight;  //dimensions of the rink, boundaries

    // initializes a new puck with specific starting position and game area dimensions
    public Puck(int startX, int startY, int screenWidth, int screenHeight) {
        this.x = startX;  // set the initial horizontal and vertical position
        this.y = startY;
        this.screenWidth = screenWidth;  // Set the width of the playing area
        this.screenHeight = screenHeight;  // Set the height of the playing area
    }

    // Update the position of the puck based on its velocity
    public void update() {
        x += velocityX;  // new horizontal position
        y += velocityY;  // new vertical position

        // check to see if the puck hits the left or right borders of the rink
        if (x < 0 || x > screenWidth - size) {
            velocityX *= -1;  // reverse the direction of the puck
        }
        // check if the puck hits the top or bottom borders of the rink too
        if (y < 0 || y > screenHeight - size) {
            velocityY *= -1;  // same thing as before but y
        }
    }

    // draws the puck
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);  // color to black
        g.fillOval(x, y, size, size);
    }

    //returns the bounding rectangle of the puck for collision detection
    public Rectangle getBounds() {
        return new Rectangle(x, y, size, size);
    }

    //adjusts the puck's velocity based on where it collides with a mallet
    public void collideWithMallet(Point malletPosition) {
        int malletX = malletPosition.x;
        int malletY = malletPosition.y;
        int dx = x - malletX;  // horizontal distance from the puck to the mallet
        int dy = y - malletY;  // vertical distance from the puck to the mallet

        // calculate new velocities based on the collision point, the response should be proportional to the distance
        velocityX = 3 * dx / size;  //then change horizontal velocity based on horizontal distance
        velocityY = 3 * dy / size;  // do again, change vertical velocity based on vertical distance

        // ensure the puck always has some movement to prevent it from stopping
        velocityX = velocityX == 0 ? 1 : velocityX;
        velocityY = velocityY == 0 ? 1 : velocityY;
    }

    // resets the puck to the center of the rink in place
    public void reset() {
        x = screenWidth / 2 - size / 2;  //center the puck horizontally and vertically
        y = screenHeight / 2 - size / 2;
        velocityX = 0;  // set to zero
        velocityY = 0;
    }
}

