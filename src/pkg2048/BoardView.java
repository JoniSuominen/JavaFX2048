/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2048;

import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import pkg2048.UI.TileSprite;

/**
 *
 * @author Joni
 */
public class BoardView {

    private GridPane playArea;
    private int score;
    private TileSprite[][] tiles;

    public BoardView() {
        this.score = 0;
        this.tiles = new TileSprite[4][4];
    }

    /**
     * Initializes the view and returns the root node (view element).
     *
     * @return view root element
     */
    public Parent getView() {
        //parent container
        VBox root = new VBox();

        StackPane gameContainer = new StackPane();
        GridPane utilityView = new GridPane();

        GridPane background = new GridPane();
        this.drawBackground(background);

        this.playArea = new GridPane();

        root.getChildren().add(utilityView);
        root.getChildren().add(gameContainer);
        root.getChildren().add(background);

        return root;
    }

    public void drawBackground(GridPane pane) {
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {

                Rectangle r = new Rectangle();
                r.setFill(Color.LIGHTGRAY);
                r.setWidth(120);
                r.setHeight(120);
                r.setArcWidth(20);
                r.setArcHeight(20);
                pane.add(r, y, x);

            }
        }
    }

}
