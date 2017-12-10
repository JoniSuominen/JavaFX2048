/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2048;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pkg2048.UI.Game;
import pkg2048.game.Direction;

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
