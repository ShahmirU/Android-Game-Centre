package fall2018.csc207.GameCentre;

import java.util.ArrayList;
import java.util.Observable;

public class LoginController extends Observable {

    /**
     * The current player logged in the game centre.
     */
    private static Player currentPlayer;


    /**
     * returns the current player logged into the game centre
     *
     * @return the current player
     */
    public static Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * sets the current player that is logged into the game centre
     *
     * @param player the player logged into the game centre
     */
    public static void setCurrentPlayer(Player player) {
        currentPlayer = player;
    }

    /**
     * returns whether a user is a valid player
     *
     * @param username      the username of the player
     * @param password      the password of the player
     * @param loadedPlayers the list of players in the game centre
     * @return whether the player is valid
     */
    static boolean isValidUser(String username, String password, ArrayList<Player> loadedPlayers) {

        boolean successfulSignIn = false;

        for (int x = 0; x < loadedPlayers.size(); x++) {
            String currentUser = loadedPlayers.get(x).getUsername();
            String currentPass = loadedPlayers.get(x).getPassword();
            if (currentUser.equals(username) && currentPass.equals(password)) {
                currentPlayer = loadedPlayers.get(x);
                successfulSignIn = true;
            }
        }

        return successfulSignIn;
    }


}
