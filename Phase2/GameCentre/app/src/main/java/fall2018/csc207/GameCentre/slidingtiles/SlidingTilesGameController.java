package fall2018.csc207.GameCentre.slidingtiles;

import java.util.List;
import java.util.Observable;

/**
 * SlidingTilesGameController class to handle logic in the sliding tiles game.
 */
public class SlidingTilesGameController extends Observable {

    /**
     * Check if the current player has any undos left
     *
     * @param positions                the list of positions taken
     * @param slidingTilesBoardManager the current board manager
     * @return whether the current player has any undos left
     */
    public static boolean undosFinished(List positions, SlidingTilesBoardManager slidingTilesBoardManager) {
        boolean undosFinished = false;
        if (positions.size() == 0 || slidingTilesBoardManager.undosUsed ==
                SlidingTilesStartingActivity.undosChosen) {
            undosFinished = true;
        }
        return undosFinished;
    }

}
