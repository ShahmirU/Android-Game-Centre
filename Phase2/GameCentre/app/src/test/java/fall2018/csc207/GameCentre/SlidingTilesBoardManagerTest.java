package fall2018.csc207.GameCentre;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import fall2018.csc207.GameCentre.slidingtiles.SlidingTilesBoard;
import fall2018.csc207.GameCentre.slidingtiles.SlidingTilesBoardManager;
import fall2018.csc207.GameCentre.slidingtiles.Tile;

import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class SlidingTilesBoardManagerTest {

    /**
     * The board manager for testing.
     */
    fall2018.csc207.GameCentre.slidingtiles.SlidingTilesBoardManager boardManager;

    /**
     * Make a set of tiles that are in order.
     *
     * @return a set of tiles that are in order
     */
    private List<Tile> makeTiles(int complexity) {
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = complexity * complexity;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(tileNum + 1, tileNum));
        }
        return tiles;
    }

    /**
     * Make a solved Board.
     */
    private void setUpCorrect(int complexity) {
        SlidingTilesBoard.setNumCols(complexity);
        SlidingTilesBoard.setNumRows(complexity);
        List<Tile> tiles = makeTiles(complexity);
        SlidingTilesBoard board = new SlidingTilesBoard(tiles);
        boardManager = new fall2018.csc207.GameCentre.slidingtiles.SlidingTilesBoardManager(board);
    }

    private void swapTiles(int row1, int col1, int row2, int col2) {
        boardManager.getSlidingTilesBoard().swapTiles(row1, col1, row2, col2);
    }

    @Test
    public void testCountInversionsNone3x3() {
        int complexity = 3;
        setUpCorrect(complexity);

        SlidingTilesBoard board = boardManager.getSlidingTilesBoard();
        Tile[] tilesArray1D = boardManager.convertTilesTo1DArray(board, complexity);

        assertEquals(0, boardManager.countInversions(3, tilesArray1D, complexity));
    }

    @Test
    public void testCountInversionsOneSwap3x3() {
        int complexity = 3;
        setUpCorrect(complexity);
        swapTiles(0, 0, 2, 1);

        SlidingTilesBoard board = boardManager.getSlidingTilesBoard();
        Tile[] tilesArray1D = boardManager.convertTilesTo1DArray(board, complexity);

        assertEquals(0, boardManager.countInversions(7, tilesArray1D, complexity));
    }

    @Test
    public void testCountInversionsTwoSwaps3x3() {
        int complexity = 3;
        setUpCorrect(complexity);
        swapTiles(1, 2, 2, 2);
        swapTiles(2, 2, 0, 1);

        SlidingTilesBoard board = boardManager.getSlidingTilesBoard();
        Tile[] tilesArray1D = boardManager.convertTilesTo1DArray(board, complexity);

        assertEquals(4, boardManager.countInversions(1, tilesArray1D, complexity));
    }

    @Test
    public void testSumInversionsNone3x3() {
        int complexity = 3;
        setUpCorrect(complexity);

        SlidingTilesBoard board = boardManager.getSlidingTilesBoard();
        Tile[] tilesArray1D = boardManager.convertTilesTo1DArray(board, complexity);

        assertEquals(0, boardManager.sumInversions(tilesArray1D, complexity));
    }

    @Test
    public void testSumInversionsOneSwap3x3() {
        int complexity = 3;
        setUpCorrect(complexity);
        swapTiles(0, 0, 2, 1);

        SlidingTilesBoard board = boardManager.getSlidingTilesBoard();
        Tile[] tilesArray1D = boardManager.convertTilesTo1DArray(board, complexity);

        assertEquals(13, boardManager.sumInversions(tilesArray1D, complexity));
    }

    @Test
    public void testSumInversionsTwoSwaps3x3() {
        int complexity = 3;
        setUpCorrect(complexity);
        swapTiles(0, 0, 2, 1);
        swapTiles(1, 1, 2, 1);

        SlidingTilesBoard board = boardManager.getSlidingTilesBoard();
        Tile[] tilesArray1D = boardManager.convertTilesTo1DArray(board, complexity);

        assertEquals(12, boardManager.sumInversions(tilesArray1D, complexity));
    }

    @Test
    public void testSumInversionsOneMoveAway3x3() {
        int complexity = 3;
        setUpCorrect(complexity);
        swapTiles(2, 1, 2, 2);

        SlidingTilesBoard board = boardManager.getSlidingTilesBoard();
        Tile[] tilesArray1D = boardManager.convertTilesTo1DArray(board, complexity);

        assertEquals(0, boardManager.sumInversions(tilesArray1D, complexity));
    }


    @Test
    public void testIsSolvableCorrect3x3() {
        int complexity = 3;
        setUpCorrect(complexity);

        SlidingTilesBoard board = boardManager.getSlidingTilesBoard();
        Tile[] tilesArray1D = boardManager.convertTilesTo1DArray(board, complexity);

        assertTrue(boardManager.isSolvable(tilesArray1D, complexity, 0));
    }

    @Test
    public void testIsSolvableSolvable3x3() {
        int complexity = 3;
        setUpCorrect(complexity);
        swapTiles(0, 0, 2, 1);
        swapTiles(1, 1, 2, 1);

        SlidingTilesBoard board = boardManager.getSlidingTilesBoard();
        Tile[] tilesArray1D = boardManager.convertTilesTo1DArray(board, complexity);

        assertTrue(boardManager.isSolvable(tilesArray1D, complexity, 0));
    }

    @Test
    public void testIsSolvableUnsolvable3x3() {
        int complexity = 3;
        setUpCorrect(complexity);
        swapTiles(2, 0, 2, 1);

        SlidingTilesBoard board = boardManager.getSlidingTilesBoard();
        Tile[] tilesArray1D = boardManager.convertTilesTo1DArray(board, complexity);

        assertFalse(boardManager.isSolvable(tilesArray1D, complexity, 0));
    }

    @Test
    public void testGetEmptyRowEnd() {
        setUpCorrect(4);

        SlidingTilesBoard board = boardManager.getSlidingTilesBoard();
        Tile[][] tilesArray2D = boardManager.convertTilesTo2DArray(board, 4);

        assertEquals(1, boardManager.getEmptyRow(tilesArray2D, 4));
    }

    @Test
    public void testGetEmptyRowMiddle() {
        setUpCorrect(4);
        swapTiles(3, 3, 2, 1);

        SlidingTilesBoard board = boardManager.getSlidingTilesBoard();
        Tile[][] tilesArray2D = boardManager.convertTilesTo2DArray(board, 4);

        assertEquals(2, boardManager.getEmptyRow(tilesArray2D, 4));
    }

    @Test
    public void testIsSolvableUnsolvable4x4() {
        int complexity = 4;
        setUpCorrect(complexity);
        swapTiles(3, 1, 3, 2);

        SlidingTilesBoard board = boardManager.getSlidingTilesBoard();
        Tile[] tilesArray1D = boardManager.convertTilesTo1DArray(board, complexity);
        Tile[][] tilesArray2D = boardManager.convertTilesTo2DArray(board, complexity);

        int emptyRow = boardManager.getEmptyRow(tilesArray2D, complexity);

        assertFalse(boardManager.isSolvable(tilesArray1D, complexity, emptyRow));
    }

    @Test
    public void testIsSolvableSolvable4x4() {
        int complexity = 4;
        setUpCorrect(complexity);
        swapTiles(3, 3, 2, 3);

        SlidingTilesBoard board = boardManager.getSlidingTilesBoard();
        Tile[] tilesArray1D = boardManager.convertTilesTo1DArray(board, complexity);
        Tile[][] tilesArray2D = boardManager.convertTilesTo2DArray(board, complexity);

        int emptyRow = boardManager.getEmptyRow(tilesArray2D, complexity);

        assertTrue(boardManager.isSolvable(tilesArray1D, complexity, emptyRow));
    }

    @Test
    public void testIsSolvableUnSolvable5x5() {
        int complexity = 5;
        setUpCorrect(complexity);
        swapTiles(4, 2, 4, 3);

        SlidingTilesBoard board = boardManager.getSlidingTilesBoard();
        Tile[] tilesArray1D = boardManager.convertTilesTo1DArray(board, complexity);
        Tile[][] tilesArray2D = boardManager.convertTilesTo2DArray(board, complexity);

        int emptyRow = boardManager.getEmptyRow(tilesArray2D, complexity);

        assertFalse(boardManager.isSolvable(tilesArray1D, complexity, emptyRow));
    }

    @Test
    public void testIsSolvableSolvable5x5() {
        int complexity = 5;
        setUpCorrect(complexity);
        swapTiles(4, 2, 4, 3);
        swapTiles(4, 0, 4, 2);

        SlidingTilesBoard board = boardManager.getSlidingTilesBoard();
        Tile[] tilesArray1D = boardManager.convertTilesTo1DArray(board, complexity);
        Tile[][] tilesArray2D = boardManager.convertTilesTo2DArray(board, complexity);

        int emptyRow = boardManager.getEmptyRow(tilesArray2D, complexity);

        assertTrue(boardManager.isSolvable(tilesArray1D, complexity, emptyRow));
    }

    @Test
    public void testGetEmptyRowBottom() {
        int complexity = 4;
        setUpCorrect(complexity);

        SlidingTilesBoard board = boardManager.getSlidingTilesBoard();
        Tile[][] tilesArray2D = boardManager.convertTilesTo2DArray(board, complexity);

        assertEquals(1, boardManager.getEmptyRow(tilesArray2D, complexity));
    }

    @Test
    public void testIsValidTap() {
        int complexity = 4;
        setUpCorrect(complexity);
        assertEquals(true, boardManager.isValidTap(11));
        assertEquals(true, boardManager.isValidTap(14));
        assertEquals(false, boardManager.isValidTap(10));
    }

    @Test
    public void testIsSolved() {
        int complexity = 5;
        setUpCorrect(complexity);
        assertEquals(true, boardManager.puzzleSolved());
        boardManager.getSlidingTilesBoard().swapTiles(0, 0, 0, 1);
        assertEquals(false, boardManager.puzzleSolved());

    }

    @Test
    public void testConvertTilesTo2DArray() {
        int complexity = 3;
        setUpCorrect(complexity);

        boolean isEqual = true;

        SlidingTilesBoard board = boardManager.getSlidingTilesBoard();
        Tile[][] tilesArray2D = boardManager.convertTilesTo2DArray(board, complexity);
        Tile[][] expectedTilesArray2D = {{new Tile(0), new Tile(1), new Tile(2)},
                {new Tile(3), new Tile(4), new Tile(5)},
                {new Tile(6), new Tile(7), new Tile(8)}};

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tilesArray2D[i][j].id != expectedTilesArray2D[i][j].id) {
                    isEqual = false;
                }
            }
        }

        assertTrue(isEqual);
    }

    @Test
    public void testConvertTilesTo1DArray() {
        int complexity = 3;
        setUpCorrect(complexity);

        boolean isEqual = true;

        SlidingTilesBoard board = boardManager.getSlidingTilesBoard();
        Tile[] tilesArray1D = boardManager.convertTilesTo1DArray(board, complexity);
        Tile[] expectedTilesArray1D = {new Tile(0), new Tile(1), new Tile(2),
                new Tile(3), new Tile(4), new Tile(5),
                new Tile(6), new Tile(7), new Tile(8)};

        for (int i = 0; i < 9; i++) {
            if (tilesArray1D[i].id != expectedTilesArray1D[i].id) {
                isEqual = false;
            }

        }
        assertTrue(isEqual);
    }

    @Test
    public void testTouchMove() {
        int complexity = 3;
        setUpCorrect(complexity);


        SlidingTilesBoardManager newBoardManager = boardManager;
        newBoardManager.touchMove(0);

        SlidingTilesBoard boardNew = newBoardManager.getSlidingTilesBoard();
        SlidingTilesBoard boardOld = boardManager.getSlidingTilesBoard();

        boolean isEqual = true;

        Tile[] oldTilesArray1D = boardManager.convertTilesTo1DArray(boardOld, complexity);
        Tile[] newTilesArray1D = newBoardManager.convertTilesTo1DArray(boardNew, complexity);

        for (int i = 0; i < 9; i++) {
            if (oldTilesArray1D[i].id != newTilesArray1D[i].id) {
                isEqual = false;
            }

        }

        assertTrue(isEqual);


    }

}

