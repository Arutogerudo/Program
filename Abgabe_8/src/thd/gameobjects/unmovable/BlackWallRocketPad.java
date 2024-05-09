package thd.gameobjects.unmovable;

import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;
import thd.gameobjects.base.ActivatableGameObject;
import thd.gameobjects.base.CollidingGameObject;
import thd.gameobjects.base.ShiftableGameObject;

/**
 * unmovable Gameobject Black Wall in Rocket Pad (game field).
 */
public class BlackWallRocketPad extends CollidingGameObject implements ShiftableGameObject, ActivatableGameObject {

    private static final String BLACK_WALL = "LLLLLLLLL\nLL  LL   \n  LL  LL \nLL  LL   \nLLLLLLLLL";

    /**
     * Crates a new game object that is able to collide.
     *
     * @param gameView        Window to show the game object on.
     * @param gamePlayManager Controls the game play.
     */
    public BlackWallRocketPad(GameView gameView, GamePlayManager gamePlayManager) {
        super(gameView, gamePlayManager);
        size = 3;
        rotation = 0;
        width = widthOfBlockImage(BLACK_WALL) * size;
        height = heightOfBlockImage(BLACK_WALL) * size;
        hitBoxOffsets(0, 0, 0, 0);
        distanceToBackground = 0;
    }

    @Override
    public void reactToCollisionWith(CollidingGameObject other) {

    }

    @Override
    public void addToCanvas() {
        gameView.addBlockImageToCanvas(BLACK_WALL, position.getX(), position.getY(), size, rotation);
    }

    @Override
    public String toString() {
        return "Black Wall in Rocket Pad: " + position;
    }

    @Override
    public boolean tryToActivate(Object info) {
        return position.getX() < GameView.WIDTH;
    }
}
