package fall2018.csc207.GameCentre.slidingtiles;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

/**
 * The sliding tiles slidingTilesBoard.
 */

public class SlidingTilesBoard extends Observable implements Serializable, Iterable<Tile> {

    /**
     * The number of rows.
     */

    private static int NUM_ROWS = 3;

    /**
     * The number of columns.
     */
    private static int NUM_COLS = 3;

    /**
     * The tiles on the slidingTilesBoard in row-major order.
     */
    private Tile[][] tiles = new Tile[NUM_ROWS][NUM_COLS];

    /**
     * A new slidingTilesBoard of tiles in row-major order.
     * Precondition: len(tiles) == NUM_ROWS * NUM_COLS
     *
     * @param tiles the tiles for the slidingTilesBoard
     */
    public SlidingTilesBoard(List<Tile> tiles) {

        Iterator<Tile> iter = tiles.iterator();

        for (int row = 0; row != SlidingTilesBoard.NUM_ROWS; row++) {
            for (int col = 0; col != SlidingTilesBoard.NUM_COLS; col++) {
                this.tiles[row][col] = iter.next();
            }
        }

    }

    /**
     * Return the number of rows on the slidingTilesBoard.
     *
     * @return the number of rows on the slidingTilesBoard
     */
    public static int getNumRows() {
        return NUM_ROWS;
    }


    /**
     * Set the number of rows on the SlidingTilesBoard.
     */
    public static void setNumRows(int numRows) {
        NUM_ROWS = numRows;
    }

    /**
     * Return the number of columns on the slidingTilesBoard.
     *
     * @return the number of columns on the slidingTilesBoard
     */
    public static int getNumCols() {
        return NUM_COLS;
    }

    /**
     * Set the number of columns on the SlidingTilesBoard.
     */
    public static void setNumCols(int numCols) {
        NUM_COLS = numCols;
    }

    /**
     * Return the number of tiles on the slidingTilesBoard.
     *
     * @return the number of tiles on the slidingTilesBoard
     */
    public int numTiles() {
        return NUM_ROWS * NUM_COLS;
    }

    /**
     * Return the tile at (row, col)
     *
     * @param row the tile row
     * @param col the tile column
     * @return the tile at (row, col)
     */
    public Tile getTile(int row, int col) {
        return tiles[row][col];
    }

    /**
     * Swap the tiles at (row1, col1) and (row2, col2)
     *
     * @param row1 the first tile row
     * @param col1 the first tile col
     * @param row2 the second tile row
     * @param col2 the second tile col
     */
    public void swapTiles(int row1, int col1, int row2, int col2) {
        Tile first = getTile(row1, col1);
        Tile second = getTile(row2, col2);
        tiles[row1][col1] = second;
        tiles[row2][col2] = first;
        setChanged();
        notifyObservers();
    }

    @Override
    public String toString() {
        return "SlidingTilesBoard{" +
                "tiles=" + Arrays.toString(tiles) +
                '}';
    }

    @Override
    @NonNull
    public Iterator<Tile> iterator() {
        return new TilesIterator();
    }

    /**
     * An iterator to go through each tile on the slidingTilesBoard.
     */
    private class TilesIterator implements Iterator<Tile> {

        //The indices of the next tile on the slidingTilesBoard.
        int nextRow = 0;
        int nextCol = 0;

        @Override
        public boolean hasNext() {
            return nextRow != NUM_ROWS && nextCol != NUM_COLS;
        }

        @Override
        public Tile next() {
            Tile result = tiles[nextRow][nextCol];
            if (nextCol < NUM_COLS - 1) {
                nextCol++;
            } else {
                nextRow++;
                nextCol = 0;
            }

            return result;
        }

    }
}
