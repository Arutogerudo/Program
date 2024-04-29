package thd.gameobjects.base;

import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;

import java.util.Objects;

/**
 * Represents an object in the game.
 */
public abstract class GameObject {

    protected final GameView gameView;
    protected final Position position;
    protected final Position targetPosition;
    protected double speedInPixel;
    protected double rotation;
    protected double size;
    protected double width;
    protected double height;
    protected final GamePlayManager gamePlayManager;
    protected static final double GAMEVIEW_WIDTH = 1280;
    protected static final double GAMEVIEW_HEIGHT = 720;
    protected char distanceToBackground;

    /**
     * Crates a new GameObject.
     *
     * @param gameView        GameView to show the game object on.
     * @param gamePlayManager Game Play Manager
     */
    public GameObject(GameView gameView, GamePlayManager gamePlayManager) {
        this.gameView = gameView;
        position = new Position();
        targetPosition = new Position();
        this.gamePlayManager = gamePlayManager;
    }

    /**
     * Updates the position of the game object.
     */
    public void updatePosition() {
    }

    /**
     * Updates the status of the game object.
     */
    public void updateStatus() {
    }

    /**
     * Draws the game object to the canvas.
     */
    public abstract void addToCanvas();

    /**
     * Returns the current position of the game object.
     *
     * @return position of the game object.
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Returns width of game object.
     *
     * @return Width of game object
     */
    public double getWidth() {
        return width;
    }

    /**
     * Returns height of game object.
     *
     * @return Height of game object
     */
    public double getHeight() {
        return height;
    }

    protected int widthOfBlockImage(String blockImage) {
        int widthBlockImage = 0;
        String[] linesOfBlockImages = blockImage.split("\n");
        for (String line : linesOfBlockImages) {
            widthBlockImage = Math.max(widthBlockImage, line.length());
        }
        return widthBlockImage;
    }

    protected int heightOfBlockImage(String blockImage) {
        return blockImage.split("\n").length;
    }

    public char getDistanceToBackground(){
        return distanceToBackground;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GameObject gameObject = (GameObject) o;
        if (Double.compare(this.position.getX(), gameObject.position.getX()) != 0) {
            return false;
        } else if (!this.position.equals(gameObject.position)) {
            return false;
        } else if (!this.targetPosition.equals(gameObject.position)) {
            return false;
        } else if (Double.compare(this.speedInPixel, gameObject.speedInPixel) != 0) {
            return false;
        } else if (Double.compare(this.size, gameObject.size) != 0) {
            return false;
        } else if (Double.compare(this.rotation, gameObject.rotation) != 0) {
            return false;
        } else if (Double.compare(this.height, gameObject.height) != 0) {
            return false;
        } else if (this.distanceToBackground == gameObject.distanceToBackground) {
            return false;
        } else {
            return Double.compare(this.width, gameObject.width) == 0;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, targetPosition, speedInPixel, size, rotation, height, width, distanceToBackground);
    }
}