package thd.gameobjects.unmovable;

import thd.game.managers.GamePlayManager;
import thd.gameobjects.base.CollidingGameObject;
import thd.game.utilities.GameView;
import thd.gameobjects.base.ShiftableGameObject;
import thd.gameobjects.movable.Tank;

/**
 * unmovable Gameobject radioactive pad.
 * Must be collected in various locations.
 * By collecting you gain ammutition.
 */
public class RadioactivePack extends CollidingGameObject implements ShiftableGameObject {

    private static final int WIDTH = 30;
    private static final int HEIGHT = 63;

    /**
     * Creates a radioactive pack in the given gameview.
     *
     * @param gameView        provides gameview
     * @param gamePlayManager manages the gamePlay
     */
    public RadioactivePack(GameView gameView, GamePlayManager gamePlayManager) {
        super(gameView, gamePlayManager);
        size = SIZE;
        rotation = 0;
        width = WIDTH;
        height = HEIGHT;
        hitBoxOffsets(0, 0, 0, 0);
        distanceToBackground = 2;
    }

    @Override
    public void reactToCollisionWith(CollidingGameObject other) {
        if (other instanceof Tank){
            gamePlayManager.destroyGameObject(this);
        }
    }

    @Override
    public String toString() {
        return "Score: " + position;
    }


    @Override
    public void addToCanvas() {
        gameView.addImageToCanvas("radioactivepack.png", position.getX(), position.getY(), size, rotation);
    }

}
