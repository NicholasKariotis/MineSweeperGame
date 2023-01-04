package Minesweeper.Model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
/**
 * basic implementation of MineSweeper game
 * @author Nick Kariotis
 * @author Alex Blondale 
 */
public class Minesweeper implements MinesweeperObserver<Location>{
    /**
     * enum possibilitys for gamestate
     */
    public enum GameState{
        NOT_STARTED,
        IN_PROGRESS,
        WON,
        LOST
    }
    /**
     * symbol for mine
     */
    public final char MINE = 'M';
    /**
     * symbol for covered location
     */
    public final char COVERED = '-';
    /**
     * The board of the game
     */
    public Map<Location, Character> board = new HashMap<>();
    /**
     * Mines in the game
     */
    public Set<Location> mines = new HashSet<>();
    /**
     * state of game
     */
    public GameState state = GameState.NOT_STARTED;
    /**
     * total moves in game
     */
    public int moveCount = 0;
     /**
     * number of rows
     */
    public int rows;
     /**
     * number of cols
     */
    public int cols;
     /**
     * number of mines
     */
    public int mineCount;

    public MinesweeperObserver<Location> observer;
    /**
     * initializes the Minesweeper game to be played
     * @param rows - the number of rows
     * @param columns - the number of columns
     * @param mineCount - number of mines
     */
    public Minesweeper(int rows, int cols, int mineCount){
        this.rows = rows;
        this.cols = cols;
        this.mineCount = mineCount;
        state = GameState.IN_PROGRESS;
        
        for(int i = 0; i < rows; i++){
            for(int e = 0; e < cols; e++){
                board.put(new Location(i,e), COVERED);
            }
        }
        Random random = new Random();
        while(mines.size() < mineCount){
            mines.add(new Location(random.nextInt(rows), random.nextInt(cols)));
        }
    }

    public Minesweeper(Minesweeper minesweeper){
        /**
         *  Copy Constructor
         */
        this.board = minesweeper.board.entrySet().stream()
        .collect(Collectors.toMap(e -> (Location)e.getKey(), e -> (Character) (e.getValue())));
        // Deep copy of the board

        for(Location l : minesweeper.mines){
            this.mines.add(new Location(l.getRow(), l.getCol()));
        }
        // Deep copy of the set of mines
        
        this.state = minesweeper.state;
        this.rows = minesweeper.rows;
        this.cols = minesweeper.cols;
        this.mineCount = minesweeper.mineCount;

    }

    public void makeSelection(Location location){
        /**
         *  Checks if the selection is a mine, if it is it replaces the '-'
         *  with a 'M' and the game is over.  Other wise it updates the board with 
         *  the number of surrouding mines.
         * 
         *  
         */
        int row = location.getRow();
        int col = location.getCol();
        if(mines.contains(location)){
            state = GameState.LOST;
            board.replace(new Location(row,col), COVERED, MINE);
            moveCount +=1;
        }else{
            char c = (char)(getNeighbors(location)+ '0');
            board.replace(new Location(row, col), COVERED, c);
            moveCount += 1;
        }
        if(getAllSafeSelections().size() == 0 && state != GameState.LOST){
            state = GameState.WON;
        }
        

    }
    /**
     * checks for the number of adjecent mines
     * from selection and returns that number
     * @param location - location whose neighbors are checked
     */

    public int getNeighbors(Location location){
        int count = 0;
        for(Location L: mines){
            if(L.getRow() - 1 == location.getRow() && (L.getCol() == location.getCol() || L.getCol() - 1 == location.getCol() || L.getCol() + 1 == location.getCol())){
                count += 1;
            }else if(L.getRow() + 1 == location.getRow() && (L.getCol() == location.getCol() || L.getCol() - 1 == location.getCol() || L.getCol() + 1 == location.getCol())){
                count += 1;
            }else if(L.getRow() == location.getRow() && (L.getCol() - 1 == location.getCol() || L.getCol() + 1 == location.getCol())){
                count += 1;
            }
        }
        return count;

    }

    /**
     * returns the amount of moves
     */

    public int getMoveCount(){
        return moveCount;
    }
    /**
     * returns the games state
     */

    public GameState getGameState(){
        return this.state;
    }
    /**
     * returns a HashSet of possible selectios
     * for the next move
     */
    public Set<Location> getPossibleSelections(){
        /**
         * Loops through each location on the board and returns
         *  the ones that are covered.
         */
        Set<Location> possibleLocations = new HashSet<>();
        for(Location l : board.keySet()){
            if(board.get(l) == COVERED){
                possibleLocations.add(l);
            }
        }

        return possibleLocations;
    }

    public Set<Location> getAllSafeSelections(){
        /**
         *  Returns all possible delections that are not mines
         */
        Set<Location> locations = new HashSet<>();
        for(Location l : board.keySet()){
            if(board.get(l) == COVERED && !mines.contains(l)){
                locations.add(l);
            }
        }
        return locations;
    }
    /**
     * toString method to return the 
     * board as a string
     */

    @Override
    public String toString(){
        String toString = "";
        for(int i=0; i<rows;i++){
            for(int e = 0; e<cols; e++){
                if(board.get(new Location(i, e)) != ' '){
                    toString += board.get(new Location(i, e));
                }else{
                    toString += " ";
                }
                
            }
            toString += "\n";
        }
        return toString;
    }

    @Override
    public void cellUpdated(Location location) {
        if(getSymbol(location) == 'M'){
            this.state = GameState.LOST;
        }
        
    }

    public void register(MinesweeperObserver<Location> ob){
        this.observer = ob;
    }

    public void notifyObserver(Location l){
        cellUpdated(l);
    }

    public char getSymbol(Location l){
        return board.get(l);
    }
    public boolean isCovered(Location l){
        Set<Location> covered = getPossibleSelections();
        return covered.contains(l);
    }

}
