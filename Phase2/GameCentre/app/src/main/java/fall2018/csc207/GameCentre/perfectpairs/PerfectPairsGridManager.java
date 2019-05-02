package fall2018.csc207.GameCentre.perfectpairs;

import android.support.v7.app.AppCompatActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PerfectPairsGridManager extends AppCompatActivity implements Serializable {

    /**
     * The Perfect Pairs board being managed.
     */
    public PerfectPairsGrid perfectPairsGrid;

    /**
     * Manage and populate a Perfect Pairs Grid.
     */
    public PerfectPairsGridManager() {
        List<PerfectPairsTile> tiles = new ArrayList<>();

        tiles.add(new PerfectPairsTile("square"));
        tiles.add(new PerfectPairsTile("triangle"));
        tiles.add(new PerfectPairsTile("star"));
        tiles.add(new PerfectPairsTile("bolt"));
        tiles.add(new PerfectPairsTile("smiley"));
        tiles.add(new PerfectPairsTile("heart"));
        tiles.add(new PerfectPairsTile("crescent"));
        tiles.add(new PerfectPairsTile("arrow"));
        tiles.add(new PerfectPairsTile("square"));
        tiles.add(new PerfectPairsTile("triangle"));
        tiles.add(new PerfectPairsTile("star"));
        tiles.add(new PerfectPairsTile("bolt"));
        tiles.add(new PerfectPairsTile("smiley"));
        tiles.add(new PerfectPairsTile("heart"));
        tiles.add(new PerfectPairsTile("crescent"));
        tiles.add(new PerfectPairsTile("arrow"));

        Collections.shuffle(tiles);
        this.perfectPairsGrid = new PerfectPairsGrid(tiles);
    }

    /**
     * Return the perfectPairsGrid for the game.
     *
     * @return The perfectPairsGrid for the game.
     */
    public PerfectPairsGrid getPerfectPairsGrid() {
        return this.perfectPairsGrid;
    }

    public void setPerfectPairsGrid(PerfectPairsGrid perfectPairsGrid) {
        this.perfectPairsGrid = perfectPairsGrid;
    }

    /**
     * Return whether the card being clicked is currently facing away from the currentPlayer.
     *
     * @param position The tile to check.
     * @return whether the card is facing down.
     */
    public boolean isValidTap(int position) {
        int row = position / 4;
        int col = position % 4;
        return !perfectPairsGrid.getTile(row, col).isRevealing();
    }

    /**
     * Process a touch at position in the perfectPairsGrid, turning tiles as appropriate.
     *
     * @param position The position
     */
    public void touchMove(int position) {
        int row = position / 4;
        int col = position % 4;
        if (isValidTap(position)) {
            perfectPairsGrid.turnTile(perfectPairsGrid.getTile(row, col));
        }
    }

    /**
     * Return true if the game is over.
     *
     * @return true if all tiles in the perfectPairsGrid are revealing.
     */
    public boolean puzzleSolved() {
        for (PerfectPairsTile tile : perfectPairsGrid.getTiles()) {
            if (!tile.isRevealing()) {
                return false;
            }
        }
        return true;
    }
}
