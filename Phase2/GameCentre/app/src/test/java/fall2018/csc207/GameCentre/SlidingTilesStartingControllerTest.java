package fall2018.csc207.GameCentre;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import fall2018.csc207.GameCentre.slidingtiles.SlidingTilesBoard;
import fall2018.csc207.GameCentre.slidingtiles.SlidingTilesBoardManager;
import fall2018.csc207.GameCentre.slidingtiles.SlidingTilesGameActivity;
import fall2018.csc207.GameCentre.slidingtiles.SlidingTilesStartingActivity;
import fall2018.csc207.GameCentre.slidingtiles.SlidingTilesStartingController;
import fall2018.csc207.GameCentre.slidingtiles.Tile;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class SlidingTilesStartingControllerTest {

    SlidingTilesBoardManager boardManager;

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

    ArrayList<Player> setUpPlayers() {
        ArrayList<Player> players = new ArrayList<>();
        Player p1 = new Player("Bob", "1234", "Bob");
        Player p2 = new Player("Harry", "12", "Harry");
        Player p3 = new Player("Tom", "12", "Tom");

        players.add(p1);
        players.add(p2);
        players.add(p3);

        return players;
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

    @Test
    public void testIsValidComplexity() {
        assertTrue(SlidingTilesStartingController.isValidComplexity(3));
        assertFalse(SlidingTilesStartingController.isValidComplexity(6));
    }

    @Test
    public void testGetGame() {
        SlidingTilesStartingController.getGame(3);
        assertEquals("Sliding Tiles 3x3", SlidingTilesStartingActivity.gameChosen);
        SlidingTilesStartingController.getGame(4);
        assertEquals("Sliding Tiles 4x4", SlidingTilesStartingActivity.gameChosen);
    }

    @Test
    public void testUpdatePlayer() {

        ArrayList<Player> previousPlayers = setUpPlayers();
        LoginController.setCurrentPlayer(previousPlayers.get(0));
        SlidingTilesGameActivity.score = 10;

        SlidingTilesBoardManager boardManager = new SlidingTilesBoardManager();

        previousPlayers.get(0).setSavedSlidingTilesGame(boardManager);
        previousPlayers.get(0).setSavedSlidingTilesGameScore(4);
        previousPlayers.get(0).setSavedSlidingTilesGameComplexity(boardManager.getSlidingTilesBoard().getNumCols());

        ArrayList<Player> newPlayers = setUpPlayers();
        newPlayers.get(0).setSavedSlidingTilesGame(boardManager);
        newPlayers.get(0).setSavedSlidingTilesGameScore(10);
        newPlayers.get(0).setSavedSlidingTilesGameComplexity(3);

        assertEquals(newPlayers.get(0).getUsername(), SlidingTilesStartingController.updatePlayer(previousPlayers, boardManager).get(0).getUsername());
    }

    SlidingTilesBoardManager loadBoardManager(SlidingTilesBoardManager slidingTilesBoardManager) {
        slidingTilesBoardManager = LoginController.getCurrentPlayer().getSavedSlidingTilesGame();
        slidingTilesBoardManager.slidingTilesBoard.setNumRows(LoginController.getCurrentPlayer().getSavedSlidingTilesGameComplexity());
        slidingTilesBoardManager.slidingTilesBoard.setNumCols(LoginController.getCurrentPlayer().getSavedSlidingTilesGameComplexity());

        return slidingTilesBoardManager;
    }


}
