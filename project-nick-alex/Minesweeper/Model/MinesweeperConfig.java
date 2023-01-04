package Minesweeper.Model;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import Minesweeper.Model.Minesweeper.GameState;
import backtracker.Backtracker;
import backtracker.Configuration;

public class MinesweeperConfig implements Configuration{

    public Minesweeper game;
    public LinkedList<Location> selections = new LinkedList<>();
    
    public MinesweeperConfig(Minesweeper game){
        this.game = new Minesweeper(game);
    }

    public MinesweeperConfig(Minesweeper game, LinkedList<Location> selections){
        this.game = new Minesweeper(game);
        for(Location l:selections){
            this.selections.add(l);
        }
        
    }

    @Override
    public Collection<Configuration> getSuccessors() {
        Set<Configuration> successors = new HashSet<>();
        for(Location l : game.board.keySet()){
            // For every uncovered location in the game
            if(game.board.get(l) == '-'){
                Minesweeper newGame = new Minesweeper(game);
                // Create a deepy copy of the game
                newGame.makeSelection(l);
                // Make a selection at that location
                MinesweeperConfig successor = new MinesweeperConfig(newGame, selections);
                if(successor.isValid()){
                    successor.selections.add(l);
                }
                // Add the selection to the list of selections and make it a new config
                successors.add(successor);
                // Add it to successors
            }
        }
        return successors;
    }

    @Override
    public boolean isValid() {
        boolean valid = true;
        for(Location l : game.board.keySet()){
            if(game.board.get(l) == 'M'){
                valid = false;
            }
        }
        return valid;
    }

    @Override
    public boolean isGoal() {
        if(game.getGameState() == GameState.WON){
            return true;
        }else{
            return false;
        }
    }

    public LinkedList<Location> getSelections(){
        return this.selections;
    }

    @Override
    public String toString(){
        String toString = "Selections: " + this.selections;
        toString += "\n" + this.game;
        return toString;
    }

    public static Configuration solveGame(Minesweeper game){
        Backtracker backtracker = new Backtracker(true);
        Configuration solution = backtracker.solve(new MinesweeperConfig(game));
        return solution;
    }
    
}
