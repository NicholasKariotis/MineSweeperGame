package Minesweeper.Model;

import org.junit.Test;
import org.junit.platform.commons.annotation.Testable;

@Testable
public class MinesweeperTests {
    
    @Test
    public void minesweeperTest(){
        Minesweeper game = new Minesweeper(4, 4, 2);
        game.mines.clear();
        game.mines.add(new Location(0, 0));
        game.mines.add(new Location(1, 1));
        // Two mines now at 0,0 and 1,1 instead of random location
        assert game.mines.size() == 2;
        assert game.mines.contains(new Location(0, 0));
        assert game.mines.contains(new Location(1, 1));

        game.makeSelection(new Location(2, 2));
        // Location 2,2 should no longer be a possible selection
        assert !game.getPossibleSelections().contains(new Location(2, 2));

        assert game.getNeighbors(new Location(0,1)) == 2;
        assert game.getNeighbors(new Location(2,1)) == 1;
        assert game.getNeighbors(new Location(3, 3)) == 0;
    }
}
