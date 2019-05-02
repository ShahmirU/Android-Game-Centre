package fall2018.csc207.GameCentre;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import fall2018.csc207.GameCentre.perfectpairs.PerfectPairsGridManager;
import fall2018.csc207.GameCentre.perfectpairs.PerfectPairsTile;
import fall2018.csc207.GameCentre.slidingtiles.SlidingTilesBoardManager;

/**
 * A new player account created on the game centre
 */
public class Player implements Serializable {

    /**
     * The unique username of this player
     */
    private String username;

    /**
     * The password of this player's account
     */
    private String password;

    /**
     * The email address of this player's account
     */
    private String email;

    /**
     * The scores of the player for each of the games in the game centre
     */
    private HashMap<String, Integer> userHighScores = new HashMap<>();

    /**
     * The latest saved game of the player
     */
    private SlidingTilesBoardManager savedSlidingTilesGame;

    private PerfectPairsGridManager savedPerfectPairsGame;

    /**
     * The score of the latest saved game linked to the player
     */
    private int savedSlidingTilesGameScore;

    /**
     * The score of the latest saved game linked to the player
     */
    private int savedPerfectPairsGameScore;

    /**
     * The complexity of the saved sliding tiles game linked to the player
     */
    private int savedSlidingTilesGameComplexity;

    private PerfectPairsTile[] savedPerfectPairsTiles;

    public ArrayList<PerfectPairsTile> clickedTiles = new ArrayList<>();

    /**
     * Initialize a new player in the game centre
     *
     * @param username the username of the player
     * @param password the password of the player
     * @param email    the email of the player
     */
    public Player(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.userHighScores.put("Sliding Tiles 3x3", 0);
        this.userHighScores.put("Sliding Tiles 4x4", 0);
        this.userHighScores.put("Sliding Tiles 5x5", 0);
        this.userHighScores.put("Tic Tac Toe", 0);
        this.userHighScores.put("Perfect Pairs", 0);
    }

    /**
     * Initialize a temporary player with no information stored on it
     */
    public Player() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }


    /**
     * Returns the high score for the user of game
     *
     * @param game the game of interest
     */
    public int getHighScore(String game) {
        return userHighScores.get(game);
    }

    /**
     * Sets a new high score newHighScore for Player for game
     *
     * @param game         the game of interest
     * @param newHighScore the new high score of the Player
     */
    public void setHighScore(String game, int newHighScore) {
        userHighScores.put(game, newHighScore);
    }

    /**
     * Gets a Saved Sliding Tiles game.
     */
    public SlidingTilesBoardManager getSavedSlidingTilesGame() {
        return savedSlidingTilesGame;
    }

    /**
     * Sets a saved sliding tiles game.
     */
    public void setSavedSlidingTilesGame(SlidingTilesBoardManager savedSlidingTilesGame) {
        this.savedSlidingTilesGame = savedSlidingTilesGame;
    }

    /**
     * Obtains a saved sliding tiles game score.
     */
    public int getSavedSlidingTilesGameScore() {
        return savedSlidingTilesGameScore;
    }

    /**
     * Sets a saved sliding tiles game score.
     */
    public void setSavedSlidingTilesGameScore(int savedSlidingTilesGameScore) {
        this.savedSlidingTilesGameScore = savedSlidingTilesGameScore;
    }

    /**
     * Obtains a saved sliding tiles game complexity.
     */
    public int getSavedSlidingTilesGameComplexity() {
        return savedSlidingTilesGameComplexity;
    }

    /**
     * Sets a saved sliding tiles game complexity.
     */
    public void setSavedSlidingTilesGameComplexity(int savedSlidingTilesGameComplexity) {
        this.savedSlidingTilesGameComplexity = savedSlidingTilesGameComplexity;
    }

}
