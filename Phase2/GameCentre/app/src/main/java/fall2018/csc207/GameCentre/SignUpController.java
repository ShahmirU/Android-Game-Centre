package fall2018.csc207.GameCentre;

import java.util.ArrayList;
import java.util.Observable;

class SignUpController extends Observable {

    /**
     * Return whether the username entered is unique or not.
     *
     * @param user          The user being checked
     * @param loadedPlayers the list of accounts already existing.
     */
    static boolean checkUsername(String user, ArrayList<Player> loadedPlayers) {
        boolean usernameUnique = true;
        for (int x = 0; x < loadedPlayers.size(); x++) {
            if (loadedPlayers.get(x).getUsername().equals(user)) {
                usernameUnique = false;
            }
        }
        return usernameUnique;
    }


}
