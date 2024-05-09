package thd.gameobjects.unmovable;

import thd.game.managers.GamePlayManager;
import thd.game.utilities.GameView;
import thd.gameobjects.base.ActivatableGameObject;
import thd.gameobjects.base.CollidingGameObject;
import thd.gameobjects.base.ShiftableGameObject;

/**
 * movable Gameobject Pilons (game field).
 */
public class Pilons extends CollidingGameObject implements ShiftableGameObject, ActivatableGameObject {

    /**
     * Creates a Rocket in the given gameview.
     *
     * @param gameView        provides gameview
     * @param gamePlayManager manages the gamePlay
     */
    public Pilons(GameView gameView, GamePlayManager gamePlayManager) {
        super(gameView, gamePlayManager);
        size = 0.12;
        rotation = 0;
        width = 60;
        height = 45;
        hitBoxOffsets(0, 0, 0, 0);
        distanceToBackground = 0;
    }

    @Override
    public void reactToCollisionWith(CollidingGameObject other) {

    }

    @Override
    public String toString(){
        return "Pilons: " + position;
    }

    @Override
    public void addToCanvas() {
        gameView.addImageToCanvas("pilonsrocketpad.png", position.getX(), position.getY(), size, rotation);
    }

    @Override
    public boolean tryToActivate(Object info) {
        return position.getX() < GameView.WIDTH;
    }
}
