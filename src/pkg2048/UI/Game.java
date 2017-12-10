/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2048.UI;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import pkg2048.game.Direction;
import pkg2048.game.GameGrid;
import pkg2048.game.Tile;

/**
 *
 * @author Joni
 */
public class Game {
    
    private Scene scene;
    private boolean canMove;
    private BorderPane ui;
    private BorderPane utilityView;
    private GameGrid grid;
    private StackPane tileView;
    private GridPane pane;
    private Pane gameGrid;
    private Label l;
    private int score;
    
    public Game() {
        this.canMove = true;
        this.utilityView = new BorderPane();
        this.tileView = new StackPane();
        this.ui = new BorderPane();
        this.gameGrid = new Pane();
        ui.setPadding(new Insets(10, 10, 10, 10));
        this.pane = new GridPane();
        ui.setCenter(utilityView);
        this.tileView.getChildren().addAll(pane, gameGrid);
        this.pane.setHgap(10);
        this.pane.setVgap(10);
        this.gameGrid.setMaxWidth(520);
        this.pane.setMaxWidth(520);
    }
    
    public void handleMovements(Direction d) {
        if (!grid.isGameOver() || !grid.isGameOver() && grid.getHighestTile() < 2048) {
            this.score += grid.move(d);
            doTransitions();
        } else if (grid.getHighestTile() == 2048) {
            
        } else {
            openLoseDialog();
        }
    }
    
    public BorderPane getUI() {
        return this.ui;
    }
    
    public void initialize() {
        this.score = 0;
        this.l = new Label("" + this.score);
        grid = new GameGrid();
        this.gameGrid.getChildren().clear();
        BorderPane.setMargin(utilityView, new Insets(50, 10, 10, 40));
        utilityView.setBottom(l);
        ui.setBottom(tileView);
        initializeGrid();
        System.out.println(grid.toString());
        redrawGrid();
    }
    
    public void initializeGrid() {
        //creates the grid background image
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
        addInitialTiles();
    }
    
    public void addInitialTiles() {
        for (int i = 0; i < 2; i++) {
            grid.randomNewTile();
        }
    }
    
    public void doTransitions() {
        canMove = false;
        Tile[][] tiles = grid.getTiles();
        List<Tile> transitions = new ArrayList<>();
        
        for (int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles.length; x++) {
                if (tiles[y][x] != null) {
                    tiles[y][x].setX(y);
                    tiles[y][x].setY(x);
                    transitions.add(tiles[y][x]);
                }
            }
        }
        final int size = transitions.size();
        for (int i = 0; i < transitions.size(); i++) {
            
            TranslateTransition tt = transitions.get(i).transition();
            
            final int x = i;
            
            final boolean isMerged = transitions.get(i).isMerged();
            
            tt.play();
            // we handle drawing the pop-animations on merge and re-draw
            // the tiles when all transitions are done here
            tt.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if (isMerged) {
                        
                        transitions.get(x).update();
                        transitions.get(x).popTile();
                        
                    }
                    
                    if (x == size - 1) {
                        if (grid.moved) {
                            grid.randomNewTile();
                        }
                        
                        redrawGrid();
                        System.out.println(grid.toString());
                        canMove = true;
                    }
                }
                
            });
            
        }
    }
    
    public void redrawGrid() {
        Tile[][] tiles = grid.getTiles();
        gameGrid.getChildren().clear();
        for (int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles.length; x++) {
                
                if (tiles[x][y] != null && tiles[x][y].getValue() >= 2) {
                    
                    int uusiX = x * 120 + x * 10;
                    int uusiY = y * 120 + y * 10;
                    
                    tiles[x][y].setTranslateY(tiles[x][y].getX());
                    tiles[x][y].setTranslateX(tiles[x][y].getY());
                    
                    gameGrid.getChildren().add(tiles[x][y]);
                    
                }
            }
        }
        this.l.setText("" + score);
    }
    
    private int xyTo1D(int x, int y) {
        return 4 * x + y;
    }
    
    public boolean canMove() {
        return this.canMove;
    }
    
    public void openLoseDialog() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("GAME LOST");
        alert.setHeaderText("Game Notification");
        alert.setContentText("Click  OK to play again \n \n Click Cancel to exit to desktop");
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            this.initialize();
        } else {
            // ... user chose CANCEL or closed the dialog
        }
        
    }
}
