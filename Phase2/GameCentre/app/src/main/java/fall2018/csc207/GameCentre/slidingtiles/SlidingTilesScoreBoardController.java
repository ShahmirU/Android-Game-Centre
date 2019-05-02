package fall2018.csc207.GameCentre.slidingtiles;

import java.util.ArrayList;
import java.util.Observable;

import fall2018.csc207.GameCentre.Player;

public class SlidingTilesScoreBoardController extends Observable {

    /**
     * Set the highest scores and usernames of the player's who achieved such scores in the sliding
     * tiles game.
     *
     * @param player             the player of interest
     * @param highestScore       the highest score for the game
     * @param highestScorePlayer the player who achieved the highest score
     * @param game               the game of interest for generating the scoreboard
     */
    public Player setPlayer(Player player, int highestScore, String highestScorePlayer, String game, ArrayList<Player> loadedPlayers) {
        player.setUsername(highestScorePlayer);
        player.setHighScore(game, highestScore);

        for (int x = 0; x < loadedPlayers.size(); x++) {
            int currentPlayerScore = loadedPlayers.get(x).getHighScore(game);
            String currentPlayerName = loadedPlayers.get(x).getUsername();

            if (currentPlayerScore > highestScore) {
                highestScore = currentPlayerScore;
                player.setHighScore(game, currentPlayerScore);
                player.setUsername(currentPlayerName);
            }
        }
        return player;
    }
}
