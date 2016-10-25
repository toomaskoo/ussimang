import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import javafx.stage.Stage;

import java.util.Timer;
import java.util.TimerTask;

import static javafx.scene.input.KeyCode.DOWN;


/**
 * @author Toomas
 * @version 23/10/16
 */
public class UssMain extends Application  {
    boolean up;
    boolean left;
    boolean down;
    boolean right;
    Circle ussike;
    int x;//ussi X kordinaat
    int y;//ussi Y kordinaat
    Timer timer;
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        Pane rootPane = new Pane();

        Scene rootScene = new Scene(rootPane, 500, 500);

        rootPane.setMinSize(500, 500);
        rootPane.setMaxSize(500, 500);
        rootScene.setFill(Color.CYAN);

        primaryStage.setScene(rootScene);
        primaryStage.setResizable(false);
        ussike = new Circle(10);
        x = 250;
        y = 250;

        ussike.setFill(Color.RED);
        ussike.setCenterX(x);
        ussike.setCenterY(y);
        rootPane.getChildren().addAll(ussike);

        primaryStage.show();
        ussike.requestFocus();

        timer = new Timer();
        System.out.println(3);
        Thread.sleep(1000);
        System.out.println(2);
        Thread.sleep(1000);
        System.out.println(1);
        Thread.sleep(1000);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                ussikeliigub();
            }
        }, 1, 500);

        up = false;
        down = true;
        left = false;
        right = false;


            ussike.setOnKeyReleased(
                    e -> {
                        switch (e.getCode()) {
                            case DOWN:
                                up = false;
                                left = false;
                                right = false;
                                down = true;
                                break;
                            case LEFT:
                                up = false;
                                down = false;
                                right = false;
                                left = true;
                                break;
                            case RIGHT:
                                up = false;
                                left = false;
                                down = false;
                                right = true;
                                break;
                            case UP:
                                left = false;
                                down = false;
                                right = false;
                                up = true;
                        }
                    }
            );
    }

    private void ussikeliigub(){

        if(up){
            ussike.setCenterY(y);
            ussike.setCenterX(x);
            y = y-10;
        }
        if(left){
            ussike.setCenterY(y);
            ussike.setCenterX(x);
            x = x-10;
        }
        if(right){
            ussike.setCenterY(y);
            ussike.setCenterX(x);
            x= x+10;
        }

        if(down){
            ussike.setCenterY(y);
            ussike.setCenterX(x);
            y = y+10;
        }
    }

}
