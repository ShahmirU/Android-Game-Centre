package fall2018.csc207.GameCentre.perfectpairs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

public class PerfectPairsGrid extends Observable implements Serializable {

    /**
     * The tiles on the PerfectPairsGrid in row-major order.
     */
    private PerfectPairsTile[] tiles;

    /**
     * The non-discovered tiles that have currently been turned.
     */
    public ArrayList<PerfectPairsTile> clickedTiles = new ArrayList<>();

    public static ArrayList<String> tilesRevealing = new ArrayList<>();

    public boolean[] tilesDiscovered = new boolean[16];

    /**
     * A new slidingTilesBoard of tiles in row-major order.
     *
     * @param tiles the tiles for the PerfectPairsGrid.
     */
    public PerfectPairsGrid(List<PerfectPairsTile> tiles) {
        super();
        this.tiles = new PerfectPairsTile[16];
        Iterator<PerfectPairsTile> iter = tiles.iterator();
        for (int length = 0; length < tiles.size(); length++)
            this.tiles[length] = iter.next();
    }

    /**
     * Return the tiles in the PerfectPairsGrid.
     *
     * @return The list of tiles in the grid.
     */
    public PerfectPairsTile[] getTiles() {
        return tiles;
    }

    public void setTiles(PerfectPairsTile[] tiles) {
        this.tiles = tiles;
    }

    /**
     * Return the tile at (row, col).
     *
     * @param row the tile row
     * @param col the tile column
     * @return the tile at (row, col)
     */
    public PerfectPairsTile getTile(int row, int col) {
        return tiles[(4 * row) + col];
    }


    /**
     * Turn a tile to the opposite side.
     *
     * @param tile The tile being turned.
     */
    public void turnTile(PerfectPairsTile tile) {
        tile.turnTile();
        setChanged();
        notifyObservers();
    }


    /**
     * Check if two most recently flipped tiles match.
     */
    public void matchTiles() {

        tilesRevealing.clear();

        ArrayList<PerfectPairsTile> turnedTiles = new ArrayList<>();

        for (PerfectPairsTile tile : tiles) {
            if (tile.isRevealing() && !tile.isDiscovered()) {
                turnedTiles.add(tile);
                tilesRevealing.add("true");
            } else {
                tilesRevealing.add("false");
            }
        }

        if (turnedTiles.size() == 3) {
            PerfectPairsTile tile1 = clickedTiles.get(0);
            PerfectPairsTile tile2 = clickedTiles.get(1);
            if (tile1.equals(tile2)) {
                tile1.setDiscovered(true);
                tile2.setDiscovered(true);
                tilesDiscovered[tile1.getId()] = true;
                tilesDiscovered[tile2.getId()] = true;
            }
            if (!tile1.isDiscovered()) {
                PerfectPairsMainActivity.score -= 5;
                this.turnTile(tile1);
            }
            if (!tile2.isDiscovered()) {
                PerfectPairsMainActivity.score -= 5;
                this.turnTile(tile2);
            }
            this.clickedTiles.clear();
        } else {
            this.clickedTiles.clear();
            this.clickedTiles.addAll(turnedTiles);
        }
    }
}
