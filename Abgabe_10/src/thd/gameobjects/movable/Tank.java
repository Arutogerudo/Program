package thd.gameobjects.movable;

import thd.game.managers.GamePlayManager;
import thd.gameobjects.base.CollidingGameObject;
import thd.game.utilities.GameView;
import thd.gameobjects.base.MainCharacter;
import thd.gameobjects.base.ShiftableGameObject;
import thd.gameobjects.unmovable.RadioactivePack;
import java.util.LinkedList;
import java.util.List;

/**
 * movable Gameobject Tank (maincharacter).
 */
public class Tank extends CollidingGameObject implements MainCharacter, ShiftableGameObject {
    private final int shotDurationInMilliseconds;
    private static final int POINTS_PER_PACK = 100;
    private int dimension;
    private boolean unbeatable;
    private List<CollidingGameObject> collidingGameObjectsForPathDecision;
    private State currentState;

    private enum State {
        LIFE_LOST("tankleftwb"), LEFT_BW("tankleftbw.png"), LEFT_WB("tankleftwb.png"), RIGHT_BW("tankrightbw.png"),
        RIGHT_WB("tankrightwb.png"), UP_BW("tankupbw.png"), UP_WB("tankupwb.png"),
        DOWN_BW("tankdownbw.png"), DOWN_WB("tankdownwb.png");
        private String image;

        State(String image) {
            this.image = image;
        }
    }

    /**
     * Creates the tank in the given gameview.
     *
     * @param gameView         provides gameview
     * @param gamePlayManager  manages the gamePlay
     */
    public Tank(GameView gameView, GamePlayManager gamePlayManager) {
        super(gameView, gamePlayManager);
        size = 0.1;
        speedInPixel = 2;
        rotation = 0;
        width = 63;
        height = 55;
        hitBoxOffsets(0, 0, 0, 0);
        shotDurationInMilliseconds = 300;
        distanceToBackground = 1;
        this.position.updateCoordinates(100, 100);
        dimension = 1;
        unbeatable = true;
        currentState = State.DOWN_BW;

        shoot();
    }

    /**
     * Hand over a list of collinding game objects for path decision.
     *
     * @param collidingGameObjectsForPathDecision List of colliding Game objects for path decision.
     */
    public void setCollidingGameObjectsForPathDecision(LinkedList<CollidingGameObject> collidingGameObjectsForPathDecision){
        this.collidingGameObjectsForPathDecision = collidingGameObjectsForPathDecision;
    }

    @Override
    public void reactToCollisionWith(CollidingGameObject other) {
        if (other instanceof RadioactivePack) {
            gamePlayManager.addPoints(POINTS_PER_PACK);
            gamePlayManager.collectPack();
            gamePlayManager.refillAmmunition();
        } else if (!(other instanceof Shot) && !(other instanceof Tank)) {
            if (!unbeatable) {
                gamePlayManager.lifeLost();
                unbeatable = true;
            }
        }
    }

    /**
     * Tank moves left, regarding Colliding Objects he cannot went through.
     * and with shifting the world when he is at the right edge of the gameview.
     *
     * @param shiftCounterPerLevel gives the actual number of already done shifts, before the move
     *
     * @return returns the number of already done left shift in the level, after the move
     */
    public int left(int shiftCounterPerLevel) {
        currentState = State.LEFT_BW;
        if (position.getX() / dimension <= GAMEVIEW_WIDTH && position.getX() / dimension >= 0){
            position.left(speedInPixel);
            for (CollidingGameObject collidingGameObject : collidingGameObjectsForPathDecision) {
                if (collidesWith(collidingGameObject)) {
                    reactToCollisionWith(collidingGameObject);
                    position.right(speedInPixel);
                    break;
                }
            }
            return shiftCounterPerLevel;
        } else {
            gamePlayManager.moveWorldToRight(GAMEVIEW_WIDTH);
            return shiftCounterPerLevel - 1;
        }
    }

    /**
     * Tank moves right, regarding Colliding Objects he cannot went through.
     * and with shifting the world when he is at the right edge of the gameview.
     *
     * @param shiftCounterPerLevel gives the actual number of already done shifts, before the move
     *
     * @return returns the number of already done left shift in the level, after the move
     */
    public int right(int shiftCounterPerLevel) {
        currentState = State.RIGHT_BW;
        if (position.getX() < GAMEVIEW_WIDTH){
            position.right(speedInPixel); // Bewegung zunächst wie geplant durchführen.
            for (CollidingGameObject collidingGameObject : collidingGameObjectsForPathDecision) {
                if (collidesWith(collidingGameObject)) {
                    reactToCollisionWith(collidingGameObject);
                    position.left(speedInPixel); // Bewegung im Fall einer Kollision wieder zurücknehmen!
                    break;
                }
            }
            return shiftCounterPerLevel;
        } else {
            gamePlayManager.moveWorldToLeft(GAMEVIEW_WIDTH);
            dimension += 1;
            return shiftCounterPerLevel + 1;
        }


    }

    /**
     * Tank moves up.
     */
    public void up() {
        //position.up(speedInPixel);
        currentState = State.UP_BW;
        position.up(speedInPixel); // Bewegung zunächst wie geplant durchführen.
        for (CollidingGameObject collidingGameObject : collidingGameObjectsForPathDecision) {
            if (collidesWith(collidingGameObject)) {
                reactToCollisionWith(collidingGameObject);
                position.down(speedInPixel); // Bewegung im Fall einer Kollision wieder zurücknehmen!
                break;
            }
        }

    }

    /**
     * Tank moves down.
     */
    public void down() {
        //position.down(speedInPixel);
        currentState = State.DOWN_BW;
        position.down(speedInPixel); // Bewegung zunächst wie geplant durchführen.
        for (CollidingGameObject collidingGameObject : collidingGameObjectsForPathDecision) {
            if (collidesWith(collidingGameObject)) {
                reactToCollisionWith(collidingGameObject);
                position.up(speedInPixel); // Bewegung im Fall einer Kollision wieder zurücknehmen!
                break;
            }
        }

    }

    private String shotDirection(){
        switch (currentState){
            case DOWN_BW, DOWN_WB:
                return "down";
            case UP_BW, UP_WB:
                return "up";
            case RIGHT_BW, RIGHT_WB:
                return "right";
            case LEFT_BW, LEFT_WB:
                return "left";
        }
        return "None";
    }

    @Override
    public void shoot() {
        if (gameView.timer(shotDurationInMilliseconds, gameView) && gamePlayManager.getAmmunition() > 0) {
            Shot shot = new Shot(gameView, gamePlayManager, position.getX() + calculateAdditionalX(), position.getY() + calculateAdditionalY(), shotDirection());
            gamePlayManager.spawnGameObject(shot);
            gamePlayManager.shot();
        }
    }
    private static final double ADDITIONAL_X_VALUE_FOR_SHOT_POSITION = 70;
    private static final double ADDITIONAL_Y_VALUE_FOR_SHOT_POSITION = 19;

    private double calculateAdditionalY() {
        switch (currentState){
            case DOWN_BW, DOWN_WB:
                return height;
            case UP_BW, UP_WB:
                return 0;
            case RIGHT_BW, RIGHT_WB, LEFT_BW, LEFT_WB:
                return height/2.0 - 8;
        }
        return -99;
    }

    private double calculateAdditionalX() {
        switch (currentState){
            case DOWN_BW, DOWN_WB, UP_BW, UP_WB:
                return width/2.0 - 9;
            case RIGHT_BW, RIGHT_WB:
                return width;
            case LEFT_BW, LEFT_WB:
                return 0;
        }
        return -99;
    }

    @Override
    public String toString() {
        return "Tank: " + position;
    }

    @Override
    public void updateStatus(){
        if (unbeatable && gameView.timer(1000, this)){
            unbeatable = false;
        }
        switch (currentState) {
            case UP_BW:
                if (gameView.timer(150, this)) {
                    currentState = State.UP_WB;
                }
                break;
            case UP_WB:
                if (gameView.timer(150, this)) {
                    currentState = State.UP_BW;
                }
                break;
            case DOWN_BW:
                if (gameView.timer(150, this)) {
                    currentState = State.DOWN_WB;
                }
                break;
            case DOWN_WB:
                if (gameView.timer(150, this)) {
                    currentState = State.DOWN_BW;
                }
                break;
            case LEFT_BW:
                if (gameView.timer(150, this)) {
                    currentState = State.LEFT_WB;
                }
                break;
            case LEFT_WB:
                if (gameView.timer(150, this)) {
                    currentState = State.LEFT_BW;
                }
                break;
            case RIGHT_BW:
                if (gameView.timer(150, this)) {
                    currentState = State.RIGHT_WB;
                }
                break;
            case RIGHT_WB:
                if (gameView.timer(150, this)) {
                    currentState = State.RIGHT_BW;
                }
                break;
        }
    }


    @Override
    public void addToCanvas() {
        gameView.addImageToCanvas(currentState.image, position.getX(), position.getY(), size, rotation);
    }
}
