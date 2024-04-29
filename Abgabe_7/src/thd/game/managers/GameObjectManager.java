package thd.game.managers;

import thd.gameobjects.base.GameObject;

import java.util.*;

/**
 * Class to manage the gameobjects.
 */
public class GameObjectManager extends CollisionManager {
    private final List<GameObject> gameObjects;
    private final List<GameObject> gameObjectsToBeAdded;
    private final List<GameObject> gameObjectsToBeRemoved;
    private static final int MAXIMUM_NUMBER_OF_GAME_OBJECTS = 500;

    GameObjectManager(){
        gameObjects = new LinkedList<>();
        gameObjectsToBeAdded = new LinkedList<>();
        gameObjectsToBeRemoved = new LinkedList<>();
    }

    void add(GameObject gameObject){
        gameObjectsToBeAdded.add(gameObject);
    }
    void remove(GameObject gameObject){
        gameObjectsToBeRemoved.add(gameObject);
    }
    void removeAll(){
        gameObjectsToBeAdded.clear();
        gameObjectsToBeRemoved.addAll(gameObjects);
    }

    void gameLoopUpdate() {
        updateLists();
        for (GameObject gameObject : gameObjects) {
            gameObject.updateStatus();
            gameObject.updatePosition();
            gameObject.addToCanvas();
        }
        manageCollisions(true);
    }
    private void updateLists() {
        removeFromGameObjects();
        addToGameObjects();

        if (gameObjects.size() > MAXIMUM_NUMBER_OF_GAME_OBJECTS) {
            throw new TooManyGameObjectsException("Too many Game Objects are existing! " + gameObjects.size() + " are existing but just " + MAXIMUM_NUMBER_OF_GAME_OBJECTS + " are allowed.");
        }
    }

    private void addToGameObjects() {
        for (GameObject toAdd:gameObjectsToBeAdded) {
            addToCollisionManagement(toAdd);
            sortIntoGameObjects(toAdd);
        }
        gameObjectsToBeAdded.clear();
    }

    private void removeFromGameObjects() {
        for (GameObject gameObject:gameObjectsToBeRemoved) {
            removeFromCollisionManagement(gameObject);
            gameObjects.remove(gameObject);
        }
        gameObjectsToBeRemoved.clear();
    }

    private void sortIntoGameObjects(GameObject toAdd) {
        int indexToSortIn = 0;
        for (GameObject gameObject : gameObjects) {
            if (gameObject.getDistanceToBackground() >= toAdd.getDistanceToBackground()) {
                break;
            }
            indexToSortIn++;
        }
        gameObjects.add(indexToSortIn, toAdd);
    }
}
