package fall2018.csc207.GameCentre.perfectpairs;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Observable;

import fall2018.csc207.GameCentre.Player;


public class PerfectPairsScoreBoardController extends Observable {
    /**
     * Save file to store the scores of the players.
     */
    private static String PERFECTPAIRS_GAME_SCORES_SAVE_FILE = "perfect_pairs_game_scores.ser";

    ArrayList<Player> loadPlayers() {
        ArrayList<Player> loadedPlayers = new ArrayList<>();
        try {
            FileInputStream var2 = new FileInputStream("/data/data/csc207.fall2018.gamecentre/files/save_player.ser");
            BufferedInputStream var3 = new BufferedInputStream(var2);
            ObjectInputStream var4 = new ObjectInputStream(var3);
            loadedPlayers = (ArrayList<Player>) var4.readObject();
            var4.close();
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        }
        return loadedPlayers;
    }

    /**
     * Save the highest scores per game to fileName.
     */
    public void saveScores(ArrayList<Player> topThreeScores, Context context) {
        ArrayList<Player> scoresCopy = (ArrayList<Player>) topThreeScores.clone();
        try {
            FileOutputStream fileOut = context.openFileOutput(PERFECTPAIRS_GAME_SCORES_SAVE_FILE, Activity.MODE_PRIVATE);
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOut);
            outputStream.writeObject(scoresCopy);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * Load the highest scores per game from fileName.
     */
    public ArrayList<Player> loadScores() {
        ArrayList<Player> topThreeScores = new ArrayList<>();
        try {
            FileInputStream var2 = new FileInputStream("/data/data/csc207.fall2018.gamecentre/files/perfect_pairs_game_scores.ser");
            BufferedInputStream var3 = new BufferedInputStream(var2);
            ObjectInputStream var4 = new ObjectInputStream(var3);
            topThreeScores = (ArrayList<Player>) var4.readObject();
            var4.close();
        } catch (FileNotFoundException e) {
            Log.e("score Perfect Pairs Scoreboard activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("score Perfect Pairs Scoreboard activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("score Perfect Pairs Scoreboard activity", "File contained unexpected data type: " + e.toString());
        }
        return topThreeScores;
    }

    /**
     * Set the highest scores and usernames of the player's who achieved such scores in the sliding
     * tiles game.
     *
     * @param player             the player of interest
     * @param highestScore       the highest score for the game
     * @param highestScorePlayer the player who achieved the highest score
     */
    Player setPlayer(Player player, int highestScore, String highestScorePlayer, ArrayList<Player> loadedPlayers) {
        player.setUsername(highestScorePlayer);
        player.setHighScore("Perfect Pairs", highestScore);
        if (loadPlayers().size() == 0) {
            return player;
        }
        for (int x = 0; x < loadedPlayers.size(); x++) {
            int currentPlayerScore = loadedPlayers.get(x).getHighScore("Perfect Pairs");
            String currentPlayerName = loadedPlayers.get(x).getUsername();

            if (currentPlayerScore > highestScore) {
                highestScore = currentPlayerScore;
                player.setHighScore("Perfect Pairs", currentPlayerScore);
                player.setUsername(currentPlayerName);
            }
        }
        return player;
    }
}
