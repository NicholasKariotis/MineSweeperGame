package Minesweeper.View;
import Minesweeper.Model.Location;
import Minesweeper.Model.Minesweeper;
import Minesweeper.Model.MinesweeperConfig;
import Minesweeper.Model.Minesweeper.GameState;

import java.util.Random;
import java.util.Scanner;

public class MinesweeperCLI {
    public static int BOARD_ROWS = 4;
    public static int BOARD_COLS= 4;
    public static void main(String[] args){
        Minesweeper game = new Minesweeper(BOARD_ROWS, BOARD_COLS, 2);
        Scanner input = new Scanner(System.in);

        System.out.println("Mines: " + game.mineCount);
        System.out.println(helpMessage());

        System.out.println("Moves: " + game.moveCount);
        System.out.println("\n" + game);

        String[] command;
        String stringCommand;
        do{
            System.out.print("Enter Command: ");
            stringCommand = input.nextLine();
            command = stringCommand.split(" "); 
        }while(checkCommand(stringCommand) == false);
        
       
        if(!command[0].equals("quit")){
            while(game.getGameState() == GameState.IN_PROGRESS){
                if(command[0].equals("pick")){
                    Location pick = new Location(Integer.parseInt(command[1]), Integer.parseInt(command[2]));
                    game.makeSelection(pick);
                    if(game.board.get(pick) == 'M'){
                        System.out.println("\nMoves: " + game.moveCount);
                        System.out.println("\n" + game);
                        game.state = GameState.LOST;
                        break;
                    }
                }else if(command[0].equals("help")){
                    System.out.println(helpMessage());
                }else if(command[0].equals("hint")){
                    Location hint;
                    Random random = new Random();
                    Object[] locationArray = game.getAllSafeSelections().toArray();
                    hint = (Location)locationArray[random.nextInt(locationArray.length)];
                    game.makeSelection(hint);

                }else if(command[0].equals("reset")){
                    game = new Minesweeper(BOARD_ROWS, BOARD_COLS, 2);
                    System.out.println("--RESET--");
                }else if(command[0].equals("solve")){
                    MinesweeperConfig.solveGame(game);
                    game.state = GameState.IN_PROGRESS;
                }else if(command[0].equals("quit")){
                    game.state = GameState.NOT_STARTED;
                }
                if(game.getPossibleSelections().size() == game.mineCount){
                    game.state = GameState.WON;
                }else{
                    if(game.state == GameState.IN_PROGRESS){
                        System.out.println("\nMoves: " + game.moveCount);
                        System.out.println("\n" + game);
                        do{
                            System.out.print("Enter Command: ");
                            stringCommand = input.nextLine();
                            command = stringCommand.split(" "); 
                        }while(checkCommand(stringCommand) == false); 
                    }
                    
                }
                if(game.state == GameState.NOT_STARTED){
                    System.out.println("Good Bye!");
                }
                
            }
        }else{
            System.out.println("Good Bye!");
        }
        if(game.getGameState() == GameState.LOST){
            System.out.println("BOOM, you lose...");
        }else if(game.getGameState() == GameState.WON){
            System.out.println("Congratulations!");
            for(Location l: game.getPossibleSelections()){
                game.makeSelection(l);
            }
            System.out.println("\n" + game);
        }
        input.close();
    }

    public static String helpMessage(){
        /**
         *  Returns hep menu
         */
        return "Commands: " + "\n"
            + "\thelp - this help message\n"
            + "\tpick <row> <col> - uncovers cell <row> <col>\n"
            + "\thint - displays a safe selection\n"
            + "\treset - resets to a new game\n"
            + "\tquit - exits the game\n" 
            + "\tsolve - Solve the board";
    }

    public static boolean checkCommand(String stringCommand){
        /**
         *  Makes sure a command is valid
         */
        String[] command = stringCommand.split(" ");
        if(command[0].equals("pick")){
            if(command.length != 3){
                return false;
            }
            if(Integer.parseInt(command[1]) < BOARD_ROWS && Integer.parseInt(command[2]) < BOARD_COLS){
                return true;
            }
            return false;
        }
        String[] valid = {"pick", "help", "quit", "hint", "reset", "solve"};
        for(String v: valid){
            if(command[0].equals(v)){
                return true;
            }
        }
        return false;
    }

}
