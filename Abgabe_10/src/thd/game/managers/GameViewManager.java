package thd.game.managers;

import thd.game.utilities.GameView;

import java.awt.*;

/**
 * Class to manage the gameobjects in the gameview.
 */
public class GameViewManager extends GameView {
    private GameManager gameManager;
    @Override
    public void initialize() {
        gameManager = new GameManager(this);
        setWindowTitle("Tiger Tank");
        setStatusText("Lara Altgeld - Java Programmierung SS 2024");
        setWindowIcon("tigertank.png");
        changeBackgroundColor(new Color(168, 112, 47));
        gameManager.startNewGame();
    }
    @Override
    public void gameLoop() { // Dieser Code wird 60 mal pro Sekunde ausgeführt.
        gameManager.gameLoopUpdate();
    }
}
