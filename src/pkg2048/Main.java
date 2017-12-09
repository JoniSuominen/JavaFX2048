/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2048;

import java.util.List;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import pkg2048.UI.Game;
import pkg2048.game.Direction;
import pkg2048.game.GameGrid;
import pkg2048.game.Tile;

/**
 *
 * @author Joni
 */
public class Main extends Application {

    public static void main(String[] args) {
        // TODO code application logic here
        launch(Main.class);

    }

    @Override
    public void start(Stage ikkuna) throws Exception {
        Game game = new Game();
        game.initialize();
        BoardView ui = new BoardView();
        Scene scene = new Scene(game.getUI(), 600, 800);

        scene.setOnKeyPressed((event) -> {
            if (game.canMove()) {
                switch (event.getCode()) {
                    case UP:
                        game.handleMovements(Direction.UP);
                        break;
                    case DOWN:
                        game.handleMovements(Direction.DOWN);
                        break;
                    case RIGHT:
                        game.handleMovements(Direction.RIGHT);
                        break;
                    case LEFT:
                        game.handleMovements(Direction.LEFT);
                        break;
                    default:
                        break;
                }
            }
        });
        ikkuna.setScene(scene);
        ikkuna.show();

    }

}
