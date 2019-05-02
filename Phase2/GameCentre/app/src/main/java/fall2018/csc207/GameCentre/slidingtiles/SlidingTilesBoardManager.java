package fall2018.csc207.GameCentre.slidingtiles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Manage a slidingTilesBoard, including swapping tiles, checking for a win, and managing taps.
 */
public class SlidingTilesBoardManager implements Serializable {

    /**
     * The slidingTilesBoard being managed.
     */
    public SlidingTilesBoard slidingTilesBoard;

    /**
     * The number of undos used in this board manager.
     */
    public int undosUsed = 0;

    /**
     * The positions of the last two tiles swapped.
     */
    private List positions = new ArrayList();

    /**
     * Constants for surrounding tiles.
     */
    private final int ABOVE = 0;
    private final int BELOW = 1;
    private final int LEFT = 2;
    private final int RIGHT = 3;

    /**
     * Manage a slidingTilesBoard that has been pre-populated.
     *
     * @param slidingTilesBoard the slidingTilesBoard
     */
    public SlidingTilesBoardManager(SlidingTilesBoard slidingTilesBoard) {
        this.slidingTilesBoard = slidingTilesBoard;
    }


    /**
     * Manage a new shuffled slidingTilesBoard and ensure that the shuffled board is solvable.
     */
    public SlidingTilesBoardManager() {
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = SlidingTilesBoard.getNumRows() * SlidingTilesBoard.getNumCols();
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(tileNum));
        }


        int complexity = SlidingTilesBoard.getNumRows();
        int emptyRow;

        Tile[][] tilesArray2D;
        Tile[] tilesArray1D;

        //ensures a solvable board is created before a game begins
        do {
            Collections.shuffle(tiles);
            SlidingTilesBoard board = new SlidingTilesBoard(tiles);

            tilesArray1D = convertTilesTo1DArray(board, complexity);
            tilesArray2D = convertTilesTo2DArray(board, complexity);

            emptyRow = getEmptyRow(tilesArray2D, complexity);
        }
        while (!isSolvable(tilesArray1D, complexity, emptyRow));

        this.slidingTilesBoard = new SlidingTilesBoard(tiles);
    }

    /**
     * Return the current slidingTilesBoard.
     */
    public SlidingTilesBoard getSlidingTilesBoard() {
        return slidingTilesBoard;
    }

    public List getPositions() {
        return positions;
    }

    /**
     * Return a 1D array representing the tiles on a board
     *
     * @param board      the sliding tiles board
     * @param complexity the complexity of the board
     * @return the 1D array of tiles on the board
     */
    public Tile[] convertTilesTo1DArray(SlidingTilesBoard board, int complexity) {
        Tile[] tilesArray1D = new Tile[complexity * complexity];

        Iterator<Tile> tiles = board.iterator();
        for (int i = 0; i < complexity * complexity; i++) {
            tilesArray1D[i] = tiles.next();
        }
        return tilesArray1D;
    }

    /**
     * Return a 2D array representing the tiles on a board
     *
     * @param board      the sliding tiles board
     * @param complexity the complexity of the board
     * @return the 2D array of tiles on the board
     */
    public Tile[][] convertTilesTo2DArray(SlidingTilesBoard board, int complexity) {
        Tile[][] tilesArray2D = new Tile[complexity][complexity];

        Iterator<Tile> tiles = board.iterator();
        for (int i = 0; i < complexity; i++) {
            for (int j = 0; j < complexity; j++) {
                tilesArray2D[i][j] = tiles.next();
            }
        }
        return tilesArray2D;
    }

    /*
    The following 4 methods are used to help determine solvable boards from unsolvable boards
    Logic from: https://www.sitepoint.com/randomizing-sliding-puzzle-tiles/
    */

    /**
     * Return the row that contains the blank tile (counted from the bottom up)
     *
     * @param tilesArray2D the 2D array representing the tiles on the board
     * @param complexity   the complexity of the board
     * @return the row number from the bottom containing the blank tile
     */
    public int getEmptyRow(Tile[][] tilesArray2D, int complexity) {
        int row = 0;
        for (int i = 0; i < complexity; i++) {
            for (int j = 0; j < complexity; j++) {

                if (tilesArray2D[i][j].getId() == complexity * complexity) {
                    row = complexity - i;
                }
            }
        }
        return row;
    }

    /**
     * Return the number of possible inversions of a specific tile on a shuffled board
     *
     * @param num          the index of the row of the tile on the board
     * @param tilesArray1D the 2D array representing the tiles on the board
     * @return the number of inversions of this tile on the board
     */
    public int countInversions(int num, Tile[] tilesArray1D, int complexity) {
        int inversions = 0;
        int tileValue = tilesArray1D[num].getId();

        for (int i = num + 1; i < tilesArray1D.length; i++) {
            int currentTile = tilesArray1D[i].getId();
            if (currentTile != complexity * complexity && tileValue > currentTile) {
                inversions++;
            }
        }
        return inversions;
    }

    /**
     * Return the total number of inversions for the whole board
     *
     * @param tilesArray1D the 1D array representing the tiles on the board
     * @return the total number of inversions on the board
     */
    public int sumInversions(Tile[] tilesArray1D, int complexity) {
        int inversions = 0;
        for (int i = 0; i < tilesArray1D.length; i++) {
            if (tilesArray1D[i].getId() != complexity * complexity) {
                inversions += countInversions(i, tilesArray1D, complexity);
            }
        }
        return inversions;
    }

    /**
     * Return whether a shuffled board is solvable
     *
     * @param tilesArray1D the 1D array representing the tiles on the board
     * @param emptyRow     the row on the board that contains the blank tile
     * @return whether the board is solvable or not
     */
    public boolean isSolvable(Tile[] tilesArray1D, int complexity, int emptyRow) {
        if (complexity % 2 == 1) {
            return (sumInversions(tilesArray1D, complexity) % 2 == 0);
        } else {
            if (emptyRow % 2 == 0) {
                return ((sumInversions(tilesArray1D, complexity)) % 2 == 1);
            } else {
                return ((sumInversions(tilesArray1D, complexity)) % 2 == 0);
            }
        }
    }

    /**
     * Return whether the tiles are in row-major order.
     *
     * @return whether the tiles are in row-major order
     */
    public boolean puzzleSolved() {

        Iterator<Tile> iterator = slidingTilesBoard.iterator();
        Tile currentTile = iterator.next();
        while (iterator.hasNext()) {
            Tile nextTile = iterator.next();
            if (currentTile.compareTo(nextTile) < 0) {
                return false;
            }
            currentTile = nextTile;

        }

        return true;
    }

    /**
     * Return whether any of the four surrounding tiles is the blank tile.
     *
     * @param position the tile to check
     * @return whether the tile at position is surrounded by a blank tile
     */
    public boolean isValidTap(int position) {

        int row = position / SlidingTilesBoard.getNumRows();
        int col = position % SlidingTilesBoard.getNumCols();
        int blankId = slidingTilesBoard.numTiles();

        Tile[] tiles = getSurroundingTiles(row, col);
        return (tiles[BELOW] != null && tiles[BELOW].getId() == blankId)
                || (tiles[ABOVE] != null && tiles[ABOVE].getId() == blankId)
                || (tiles[LEFT] != null && tiles[LEFT].getId() == blankId)
                || (tiles[RIGHT] != null && tiles[RIGHT].getId() == blankId);
    }

    /**
     * Process a touch at position in the slidingTilesBoard, swapping tiles as appropriate.
     *
     * @param position the position
     */
    public void touchMove(int position) {
        int row = position / SlidingTilesBoard.getNumRows();
        int col = position % SlidingTilesBoard.getNumCols();
        int blankId = slidingTilesBoard.numTiles();
        int blankRow = 0;
        int blankCol = 0;

        if (isValidTap(position)) {
            Tile[] tiles = getSurroundingTiles(row, col);

            //Getting the location of the blank tile
            if (tiles[ABOVE] != null && tiles[ABOVE].getId() == blankId) {
                blankRow = row - 1;
                blankCol = col;

            } else if (tiles[BELOW] != null && tiles[BELOW].getId() == blankId) {
                blankRow = row + 1;
                blankCol = col;
            } else if (tiles[LEFT] != null && tiles[LEFT].getId() == blankId) {
                blankRow = row;
                blankCol = col - 1;
            } else if (tiles[RIGHT] != null && tiles[RIGHT].getId() == blankId) {
                blankRow = row;
                blankCol = col + 1;
            }
            //keep track of the position of the swapped tiles
            positions.add(position);
            positions.add(blankRow);
            positions.add(blankCol);

            slidingTilesBoard.swapTiles(row, col, blankRow, blankCol);
        }
    }

    /**
     * Reverse the move that was just done by swapping the two tiles (undo).
     *
     * @param position the position
     */
    public void reverseMove(int position) {

        int pos = (int) positions.get(positions.size() - 3);

        int row = pos / SlidingTilesBoard.getNumRows();
        int col = pos % SlidingTilesBoard.getNumCols();

        int posR = (int) positions.get(positions.size() - 2);
        int posC = (int) positions.get(positions.size() - 1);

        for (int i = 0; i < 3; i++) {
            positions.remove(positions.size() - 1);
        }

        slidingTilesBoard.swapTiles(posR, posC, row, col);

    }


    /**
     * Get all the tiles surrounding the chosen tile
     *
     * @param row the row of the position
     * @param col the col of the position
     * @return the 4 tiles surrounding the chosen tile
     */
    private Tile[] getSurroundingTiles(int row, int col) {

        Tile above = row == 0 ? null : slidingTilesBoard.getTile(row - 1, col);
        Tile below = row == SlidingTilesBoard.getNumRows() - 1 ? null : slidingTilesBoard.getTile(row + 1, col);
        Tile left = col == 0 ? null : slidingTilesBoard.getTile(row, col - 1);
        Tile right = col == SlidingTilesBoard.getNumCols() - 1 ? null : slidingTilesBoard.getTile(row, col + 1);

        return new Tile[]{above, below, left, right};
    }

}
