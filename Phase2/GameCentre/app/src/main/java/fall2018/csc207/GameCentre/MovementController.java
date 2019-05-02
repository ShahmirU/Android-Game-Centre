package fall2018.csc207.GameCentre;

import android.content.Context;
import android.widget.Toast;

import fall2018.csc207.GameCentre.perfectpairs.PerfectPairsGridManager;
import fall2018.csc207.GameCentre.slidingtiles.SlidingTilesBoardManager;
import fall2018.csc207.GameCentre.slidingtiles.SlidingTilesGameActivity;

/**
 * A class to control the moves being made on the sliding tiles slidingTilesBoard.
 */
public class MovementController {

    /**
     * The slidingTilesBoard that the moves are being made on
     */
    private SlidingTilesBoardManager slidingTilesBoardManager = null;

    /**
     * The MatchingBoardManager.
     */
    private PerfectPairsGridManager perfectPairsGridManager = null;

    /**
     * A boolean representing if the slidingTilesBoard is solved or not.
     */
    private boolean solved;

    /**
     * A constructor for the movement controller
     */
    public MovementController() {
    }


    /**
     * Set a slidingTilesBoard for the game
     */
    void setSlidingTilesBoardManager(SlidingTilesBoardManager slidingTilesBoardManager) {
        this.slidingTilesBoardManager = slidingTilesBoardManager;
    }

    /**
     * Set the perfectPairsGridManager to manager.
     *
     * @param perfectPairsGridManager the new MatchingBoardManager.
     */
    public void setPerfectPairsGridManager(PerfectPairsGridManager perfectPairsGridManager) {
        this.perfectPairsGridManager = perfectPairsGridManager;
    }

    /**
     * Process the move made in the game, checking for valid moves, decrementing the score, and
     * checking for a solved game accordingly
     *
     * @param context  the context of the slidingTilesBoard
     * @param position the position of the tile for the move
     * @param display  the display of the slidingTilesBoard
     */
    void processTapMovement(Context context, int position, boolean display) {
        if (slidingTilesBoardManager.isValidTap(position)) {
            SlidingTilesGameActivity.score -= 5;
            slidingTilesBoardManager.touchMove(position);
            if (slidingTilesBoardManager.puzzleSolved()) {
                Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show();
                solved = true;
            }
        } else {
            Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Process the move made in the game, checking for valid moves, decrementing the score, and
     * checking for a solved game accordingly
     *
     * @param context  the context of the perfectPairsBoard
     * @param position the position of the tile for the move
     * @param display  the display of the perfectPairsBoard
     */
    public void processPerfectPairsTapMovement(Context context, int position, boolean display) {
        if (perfectPairsGridManager.isValidTap(position)) {
            perfectPairsGridManager.touchMove(position);
            perfectPairsGridManager.getPerfectPairsGrid().matchTiles();
            if (perfectPairsGridManager.puzzleSolved()) {
                Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * A getter method for the solved variable
     *
     * @retunr whether the board has been solved
     */
    public boolean getSolved() {
        return solved;
    }

}
