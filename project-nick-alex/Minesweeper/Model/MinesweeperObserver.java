package Minesweeper.Model;

/**
 * observer interface for memory
 * @param <Location>
 */
public interface MinesweeperObserver<Location> {
    void cellUpdated(Location location);
}
