package fall2018.csc207.GameCentre;

import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.Assert.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class SignUpControllerTest {

    ArrayList<Player> setUpPlayers() {
        ArrayList<Player> players = new ArrayList<>();
        Player p1 = new Player("Bob", "1234", "Bob");
        Player p2 = new Player("Harry", "12", "Harry");

        players.add(p1);
        players.add(p2);

        return players;
    }

    @Test
    public void checkUsernameCorrectTest() {
        ArrayList<Player> players = setUpPlayers();
        assertTrue(SignUpController.checkUsername("Bill", players));
    }

    @Test
    public void checkUsernameIncorrectTest() {
        ArrayList<Player> players = setUpPlayers();
        assertFalse(SignUpController.checkUsername("Bob", players));
    }

}
