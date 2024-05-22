package thd.gameobjects.movable;

import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;
import thd.gameobjects.base.*;

/**
 * movable Gameobject Accordion (enemy).
 */
public class Accordion extends Enemy {
    private enum State {
        SMALL("accordionsmall.png"), BIG("accordionbig.png");
        private final String image;

        State(String image){
            this.image = image;
        }
    }

    private State currentState;

    /**
     * Creates a Accordion in the given gameview.
     *
     *
     * @param gameView        provides gameview
     * @param gamePlayManager manages the gamePlay
     * @param start           given start Position
     * @param pixelToGoWidth  pixels to go horizontal
     * @param pixelToGoHeight pixels to go vertical
     * @param direction       direction of first move
     * @param pattern         given movement pattern of object
     */
    public Accordion(GameView gameView, GamePlayManager gamePlayManager, Position start, int pixelToGoWidth, int pixelToGoHeight, String direction, String pattern) {
        super(gameView, gamePlayManager);
        position.updateCoordinates(new Position(start));
        size = 0.1;
        width = 75;
        height = 53;
        hitBoxOffsets(0, 0, 0, 0);
        enemyMovementPatterns = new EnemyMovementPatterns(pattern, pixelToGoWidth, pixelToGoHeight, direction);
        targetPosition.updateCoordinates(enemyMovementPatterns.nextTargetPosition(position));
        currentState = State.SMALL;
    }


    @Override
    public String toString() {
        return "Accordion: " + position;
    }

    @Override
    public void updatePosition() {
        if (position.similarTo(targetPosition)) {
            targetPosition.updateCoordinates(enemyMovementPatterns.nextTargetPosition(position));
        }
        position.moveToPosition(targetPosition, speedInPixel);
    }

    @Override
    public void updateStatus(){
        switch (currentState) {
            case SMALL, BIG:
                if (gameView.timer(200, this)) {
                    switchToNextState();
                }
                break;
        }
    }

    private void switchToNextState() {
        int nextState = (currentState.ordinal() + 1) % Accordion.State.values().length;
        currentState = Accordion.State.values()[nextState];
    }

    @Override
    public void addToCanvas() {
        gameView.addImageToCanvas(currentState.image, position.getX(), position.getY(), size, rotation);
    }
}