package Minesweeper.Model;
/**
 * A exception for the MineSweeper class if something goes wrong
 * @author Nick Kariotis
 * @author Alex Blondale
 */
public class MinesweeperException extends Exception{
    /**
     * error message
     * @param exception the error message for the exception
     */
    public MinesweeperException(String exception){
        super(exception);
    }
}
