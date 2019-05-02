package fall2018.csc207.GameCentre;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Observable;

/**
 * PlayerScoreBoardController class
 */

public class PlayerScoreBoardController extends Observable {


    /**
     * @return the new list of accounts already existing.
     */
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
     * Populate the list of high scores of the current player.
     *
     * @param loadedPlayers the list of accounts already existing.
     */
    void setHighScores(ArrayList<Player> loadedPlayers) {
        for (int i = 0; i < loadedPlayers.size(); i++) {
            if (LoginController.getCurrentPlayer().getUsername().equals(loadedPlayers.get(i).getUsername())) {
                for (int j = 0; j < PlayerScoreBoardActivity.getGames().length; j++) {
                    PlayerScoreBoardActivity.SCORES[j] = Integer.toString(loadedPlayers.get(i).getHighScore(PlayerScoreBoardActivity.getGames()[j]));
                }
            }

        }
    }
}
