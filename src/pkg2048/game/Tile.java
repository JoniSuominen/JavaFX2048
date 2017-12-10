/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2048.game;

import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 *
 * @author Joni
 */
public class Tile extends StackPane {

    private int value;
    private Rectangle r;
    private Text t;
    private StackPane pane;
    private boolean merged;
    private int x;
    private int y;

    public Tile(int value) {
        this.merged = false;
        this.value = value;
        this.r = new Rectangle();
        this.t = new Text();
        t.setFont(Font.font("Verdana", 40));
        this.pane = new StackPane();
        r.setWidth(120);
        r.setHeight(120);
        r.setArcWidth(20);
        r.setArcHeight(20);
        this.setColor();
        this.getChildren().addAll(r, t);
    }

    public StackPane getPane() {
        return this;

    }

    /**
     * sets the color of the tile depending on the integer value of the tile
     */
    public void setColor() {
        switch (value) {
            case 0:
                r.setFill(Color.TRANSPARENT);
            case 2:
                r.setFill(Color.web("#FFFAFA"));
                break;
            case 4:
                r.setFill(Color.web("#FFF5EE"));
                break;
            case 8:
                r.setFill(Color.web("#FF7F50"));
                break;
            case 16:
                r.setFill(Color.web("#FF9933"));
                break;
            case 32:
                r.setFill(Color.web("#FF6347"));
                break;
            case 64:
                r.setFill(Color.web("#B22222"));
                break;
            case 128:
                r.setFill(Color.web("#ffff99"));
                break;
            case 256:
                r.setFill(Color.web("#ffff7f"));
                break;
            case 512:
                r.setFill(Color.web("#ffff66"));
                break;
            case 1024:
                r.setFill(Color.web("#ffff4c"));
                break;
            case 2048:
                r.setFill(Color.web("#ffff32"));
                break;
            default:
                break;
        }
    }

    /**
     * sets the value of the tile on the label
     */
    public void update() {
        t.setText("" + getValue());

        setColor();
    }

    /**
     *
     * @return the tile's current value
     */
    public int getValue() {
        return this.value;
    }

    /**
     *
     * @param value the new tile value after merging or creating
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     *
     * @param merged the tile merged on top of another tile
     */
    public void setMerged(boolean merged) {
        this.merged = merged;
    }

    /**
     *
     * @return true if the tile did merge.
     */
    public boolean isMerged() {
        return merged;
    }

    /**
     * Returns TranslateTransition-object which performs animation of the tile
     * moving.
     *
     * @return transition-object of the animation.
     */
    public TranslateTransition transition() {

        TranslateTransition tt = new TranslateTransition(Duration.millis(100), this);

        tt.setToY(x);
        tt.setToX(y);

        tt.setCycleCount(1);//set to 1
        tt.setAutoReverse(true); //dont need this

        return tt;

    }

    /**
     * Performs the Pop-effect which happens when a tile merges or pops up as a
     * new tile.
     */
    public void popTile() {
        ScaleTransition st = new ScaleTransition(Duration.millis(100), this);
        st.setByX(0.2f);
        st.setByY(0.2f);
        st.setAutoReverse(true);
        st.setCycleCount(2);
        st.play();
        setMerged(false);
    }

    /**
     *
     * @return x position of the tile on grid
     */
    public int getX() {
        return x;
    }

    /**
     *
     * @param x set the x position of the tile on the grid
     */
    public void setX(int x) {
        this.x = x * 120 + x * 10;
    }

    /**
     *
     * @return y position of the tile on the grid
     */
    public int getY() {
        return y;
    }

    /**
     *
     * @param y set the y position of the tile on the grid
     */
    public void setY(int y) {
        this.y = y * 120 + y * 10;
    }

}
