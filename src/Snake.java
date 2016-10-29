import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.lang.reflect.Array;
import java.util.ArrayList;
/*
public class Snake extends Application {
    GridPane gameField = new GridPane();
    Pane rootPane = new Pane();
    Scene gameScreen;
    Scene endScreen;
    Rectangle[] snake = new Rectangle[625];
    Circle nomnom = new Circle();
    boolean up = false;
    boolean down = false;
    boolean right = true;
    boolean left = false;
    ArrayList<Integer> locationX = new ArrayList();
    ArrayList<Integer> locationY = new ArrayList();
    int counter = 0;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        gameScreen = new Scene(gameField, 500, 600);
        gameScreen.setFill(Color.LAWNGREEN);
        gameField.setGridLinesVisible(true);
        gameField.setMaxSize(500, 500);
        gameField.setMinSize(500,500);
        int a = 0;
        while(a <= 25){
            gameField.getColumnConstraints().add(new ColumnConstraints(20));
            gameField.getRowConstraints().add(new RowConstraints(20));
            a++;
        }

        gameField.setTranslateY(100);
        snake[0] = new Rectangle(20, 20, Color.RED);
        locationX.add(0);
        locationY.add(10);
        gameField.add(snake[0],locationX.get(counter), locationY.get(counter));


        primaryStage.setScene(gameScreen);
        primaryStage.show();
    }
    private void snakedirection(){
        snake[0].setOnKeyReleased(
                e -> {
                    if (up) {
                        switch (e.getCode()) {//kui liigub üles, siis saab pöörata ainult paremale ja vasakule
                            case LEFT:
                                allDirectionsFalse();
                                left = true;
                                break;
                            case RIGHT:
                                allDirectionsFalse();
                                right = true;
                        }
                    }
                    if (down) {
                        switch (e.getCode()) {//kui liigub alla siis saab pöörata ainult paremale ja vasakule
                            case LEFT:
                                allDirectionsFalse();
                                left = true;
                                break;
                            case RIGHT:
                                allDirectionsFalse();
                                right = true;
                        }
                    }
                    if (right) {
                        switch (e.getCode()) {//kui liigub paremale, siis saab pöörata üles ja alla
                            case UP:
                                allDirectionsFalse();
                                up = true;
                                break;
                            case DOWN:
                                allDirectionsFalse();
                                down = true;
                        }
                    }
                    if (left) {
                        switch (e.getCode()) {//kui liigub vasakule, siis saab pöörata üles ja alla
                            case UP:
                                allDirectionsFalse();
                                up = true;
                                break;
                            case DOWN:
                                allDirectionsFalse();
                                down = true;
                        }
                    }
                }
        );


    }
    private void allDirectionsFalse(){
        up = false;
        down = false;
        right = false;
        left = false;
    }
    private void snakeMoves(){
    if(up){
       // gameField.getChildren().a
    }
    }
}
*/