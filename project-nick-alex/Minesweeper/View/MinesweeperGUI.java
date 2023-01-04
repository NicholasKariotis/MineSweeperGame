/**
 * JavaFX GUI for Minesweeper Game
 */

package Minesweeper.View;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import Minesweeper.Model.Location;
import Minesweeper.Model.Minesweeper;
import Minesweeper.Model.MinesweeperConfig;
import Minesweeper.Model.Minesweeper.GameState;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MinesweeperGUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        Minesweeper game = new Minesweeper(10, 10, 10);
        
        VBox vb = new VBox();

        HBox hb = new HBox();

        VBox info = new VBox();
        Label mineCount = new Label("Mines: " + String.valueOf(game.mineCount));
        mineCount.setMinHeight(50);
        mineCount.setMinWidth(75);
        mineCount.setAlignment(Pos.CENTER);

        Label moves = new Label("Moves: "+ game.getMoveCount());
        moves.setMinHeight(50);
        moves.setMinWidth(75);
        moves.setAlignment(Pos.CENTER);

        Button hint = new Button("Hint");
        hint.setMinHeight(50);
        hint.setMinWidth(75);
        hint.setOnAction(b ->{
            if(game.getGameState() == GameState.IN_PROGRESS){
                Set<Location> possible = game.getAllSafeSelections();
                Location[] possibleList = new Location[possible.size()];
                int counter = 0;
                for(Location l : possible){
                    possibleList[counter] = l;
                    counter ++;
                }
                Random rn = new Random();
                Location pick = possibleList[rn.nextInt(possibleList.length - 1)];
                int r = pick.getRow();
                int c = pick.getCol();
                hint.setText(new Location(r, c).toString());
            }
        }
        );

        Label bottomMessage = new Label("Game Ongoing");

        VBox cells = new VBox();
        Map<Location, Button> buttons = new HashMap<>();
        for(int r = 0; r < game.rows; r++){
            HBox row = new HBox();
            for(int c = 0; c < game.cols; c++){
                Location l = new Location(r, c);
                Button b = new Button();
                buttons.put(l, b);
                b.setMinHeight(50);
                b.setMinWidth(50);
                b.setOnAction(e -> {
                    if(game.getMoveCount() == 90 && game.getGameState() == GameState.IN_PROGRESS){
                        game.state = GameState.WON;
                        bottomMessage.setText("You Won!! Hit restart to play again!");
                    }
                    if(game.getPossibleSelections().contains(l) && game.state == GameState.IN_PROGRESS){
                        Image image;
                        if(game.mines.contains(l)){
                            image = new Image("./media/images/mine.jpg.png");
                            b.setBackground(new Background(new BackgroundImage(image, null, null, null, 
                                new BackgroundSize(50, 50, false, false, false, false))));
                            
                        }else{
                            if(game.getNeighbors(l) == 0){
                                image = new Image("/media/images/blank.png");
                                b.setBackground(new Background(new BackgroundImage(image, null, null, null,
                                new BackgroundSize(50, 50, false, false, false, false))));
                            }else if(game.getNeighbors(l) == 1){
                                image = new Image("/media/images/1.png");
                                b.setBackground(new Background(new BackgroundImage(image, null, null, null,
                                new BackgroundSize(50, 50, false, false, false, false))));
                            }else if(game.getNeighbors(l) == 2){
                                image = new Image("/media/images/2.png");
                                b.setBackground(new Background(new BackgroundImage(image, null, null, null,
                                new BackgroundSize(50, 50, false, false, false, false))));
                            }else if(game.getNeighbors(l) == 3){
                                image = new Image("/media/images/3.png");
                                b.setBackground(new Background(new BackgroundImage(image, null, null, null,
                                new BackgroundSize(50, 50, false, false, false, false))));
                            }else if(game.getNeighbors(l) == 4){
                                image = new Image("/media/images/4.png");
                                b.setBackground(new Background(new BackgroundImage(image, null, null, null,
                                new BackgroundSize(50, 50, false, false, false, false))));
                            }else if(game.getNeighbors(l) == 5){
                                image = new Image("/media/images/5.png");
                                b.setBackground(new Background(new BackgroundImage(image, null, null, null,
                                new BackgroundSize(50, 50, false, false, false, false))));
                            }else if(game.getNeighbors(l) == 6){
                                image = new Image("/media/images/6.png");
                                b.setBackground(new Background(new BackgroundImage(image, null, null, null,
                                new BackgroundSize(50, 50, false, false, false, false))));
                            }else if(game.getNeighbors(l) == 7){
                                image = new Image("/media/images/7.png");
                                b.setBackground(new Background(new BackgroundImage(image, null, null, null,
                                new BackgroundSize(50, 50, false, false, false, false))));
                            }else if(game.getNeighbors(l) == 8){
                                image = new Image("/media/images/8.png");
                                b.setBackground(new Background(new BackgroundImage(image, null, null, null,
                                new BackgroundSize(50, 50, false, false, false, false))));
                            }

                        }
                        game.makeSelection(l);
                        moves.setText("Moves: "+ game.getMoveCount()); 
                        game.notifyObserver(l);
                        if(game.state == GameState.LOST){
                            bottomMessage.setText("BOOM!! You Lose...");
                        }
                    }
                });
                buttons.put(l, b);
                row.getChildren().add(b);
            }
            cells.getChildren().add(row);
        }


        // Creates reset button 
        Button reset = new Button("Reset");
        reset.setMinHeight(50);
        reset.setMinWidth(75);
        reset.setOnAction(b -> {
            try {
                start(stage);
            } catch (Exception e) {
                System.out.println("Error restarting game...");
            }
        });


        // Create solve button
        Button solve = new Button("Solve");
        solve.setMinHeight(50);
        solve.setMinWidth(75);
        solve.setOnAction(b -> {
            MinesweeperConfig.solveGame(game);
            game.state = GameState.IN_PROGRESS;
            for(Location l : game.getAllSafeSelections()){
                buttons.get(l).fire();
            }
            bottomMessage.setText("Hit Restart To Play Again!!!!");
        });

        
        info.getChildren().addAll(mineCount, moves, hint, reset, solve);

        hb.getChildren().addAll(info, cells);

        vb.getChildren().addAll(hb, bottomMessage);

        Scene view = new Scene(vb);

        stage.setScene(view);
        stage.setTitle("MineSweeper");
        stage.setMaxWidth(575);
        stage.show(); 
    }
    
}
