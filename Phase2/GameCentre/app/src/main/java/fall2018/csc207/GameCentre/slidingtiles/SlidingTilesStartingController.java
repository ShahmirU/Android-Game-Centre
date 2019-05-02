package fall2018.csc207.GameCentre.slidingtiles;

import java.util.ArrayList;
import java.util.Observable;

import fall2018.csc207.GameCentre.LoginController;
import fall2018.csc207.GameCentre.Player;

/**
 * The sliding tiles controller for the starting activity.
 */
public class SlidingTilesStartingController extends Observable {

    /**
     * Update complexityChosen with the complexity the user specifies.
     */
    public static void getGame(int complexityChosen) {

        if (complexityChosen == 3) {
            SlidingTilesStartingActivity.gameChosen = "Sliding Tiles 3x3";
        } else if (complexityChosen == 4) {
            SlidingTilesStartingActivity.gameChosen = "Sliding Tiles 4x4";
        } else if (complexityChosen == 5) {
            SlidingTilesStartingActivity.gameChosen = "Sliding Tiles 5x5";
        }
    }

    /**
     * Returns whether the player input a valid complexity for the sliding tiles board
     *
     * @return if the sliding tiles board is of a valid complexity
     */
    public static boolean isValidComplexity(int complexity) {
        return complexity == 3 || complexity == 4 || complexity == 5;
    }

    /**
     * Returns a new board manager that resets all features
     *
     * @param complexity               the complexity of the board
     * @param slidingTilesBoardManager the board manager to be reset
     * @return a reset sliding tiles board manager
     */
    public static SlidingTilesBoardManager resetBoardManager(int complexity, SlidingTilesBoardManager slidingTilesBoardManager) {
        SlidingTilesBoard.setNumCols(complexity);
        SlidingTilesBoard.setNumRows(complexity);
        SlidingTilesGameActivity.undosDone = 0;
        slidingTilesBoardManager = new SlidingTilesBoardManager();
        SlidingTilesGameActivity.undosDone = 0;

        return slidingTilesBoardManager;
    }


    /**
     * Update the player with a saved instance of sliding tiles board manager and score
     *
     * @param loadedPlayers            the list of players in the game centre
     * @param slidingTilesBoardManager the sliding tiles board manager
     * @return the list of players in the game centre.
     */
    public static ArrayList<Player> updatePlayer(ArrayList<Player> loadedPlayers, SlidingTilesBoardManager slidingTilesBoardManager) {
        for (int i = 0; i < loadedPlayers.size(); i++) {
            if (loadedPlayers.get(i).getUsername().equals(LoginController.getCurrentPlayer().getUsername())) {
                loadedPlayers.get(i).setSavedSlidingTilesGame(slidingTilesBoardManager);
                loadedPlayers.get(i).setSavedSlidingTilesGameScore(SlidingTilesGameActivity.score);
                loadedPlayers.get(i).setSavedSlidingTilesGameComplexity(slidingTilesBoardManager.getSlidingTilesBoard().getNumCols());
            }
        }
        return loadedPlayers;
    }


    /**
     * Find the current player logged into the game centre from the list of players.
     */
    void findCurrentPlayer(ArrayList<Player> loadedPlayers) {
        for (int x = 0; x < loadedPlayers.size(); x++) {
            if (loadedPlayers.get(x).getUsername().equals(LoginController.getCurrentPlayer().getUsername())) {
                LoginController.setCurrentPlayer(loadedPlayers.get(x));
            }
        }
    }

    /**
     * Load the sliding tiles board manager.
     *
     * @return the sliding tiles board manager.
     */
    SlidingTilesBoardManager loadBoardManager(SlidingTilesBoardManager slidingTilesBoardManager) {
        slidingTilesBoardManager = LoginController.getCurrentPlayer().getSavedSlidingTilesGame();
        slidingTilesBoardManager.slidingTilesBoard.setNumRows(LoginController.getCurrentPlayer().getSavedSlidingTilesGameComplexity());
        slidingTilesBoardManager.slidingTilesBoard.setNumCols(LoginController.getCurrentPlayer().getSavedSlidingTilesGameComplexity());

        return slidingTilesBoardManager;
    }
}
