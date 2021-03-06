package nl.hva.ict.ds;

import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertEquals;

/**
 * If you have any tests that should be overwritten or added please put them to this class.
 */
public class ExtendedPlayerFinderTest extends HighScorePlayerFinderTest {
    @Test
    public void findJansenInBigList() {
        collisionsShouldHappen();
     //Kai
        List<Player> headless = highscores.findPlayer(null, "Jansen");
        for (Player p:headless) {
            System.out.println(p);
        }
        assertEquals("Jansen", headless.get(0).getLastName());
    }
}
