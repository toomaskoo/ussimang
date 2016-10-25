import javafx.application.Application;
import javafx.scene.Scene;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.Timer;
import java.util.TimerTask;



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
    Rectangle mangukast;
    Circle nomnom;

    Text gameOver;

    Text counter;
    int counterInt = 0;
    int nomX = 200;
    int nomY = 200;

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
        mangukast = new Rectangle(480, 450);
        mangukast.setStroke(Color.LIGHTGRAY);
        mangukast.setStrokeWidth(5);
        mangukast.setFill(Color.TRANSPARENT);
        mangukast.setTranslateX(10);
        mangukast.setTranslateY(40);

        primaryStage.setScene(rootScene);
        primaryStage.setResizable(false);
        ussike = new Circle(5);
        x = 250;
        y = 250;

        ussike.setFill(Color.RED);
        ussike.setCenterX(x);
        ussike.setCenterY(y);

        nomnom = new Circle(5);



        counter = new Text();
        counter.setFont(Font.font(20));
        counter.setX(250);
        counter.setY(25);
        counter.setTextAlignment(TextAlignment.JUSTIFY);

        gameOver = new Text("GAME OVER JEE");
        gameOver.setFont(Font.font(0));
        gameOver.setX(125);
        gameOver.setY(250);
        gameOver.setFill(Color.RED);


        rootPane.getChildren().addAll(gameOver, counter, nomnom, ussike, mangukast);

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
                counter.setText(Integer.toString(counterInt));
                nomnom.setCenterX(nomX);
                nomnom.setCenterY(nomY);

            }
        }, 1, 500);

        up = false;
        down = true;
        left = false;
        right = false;


        // jälgib nuppude liigutusi, et
        ussike.setOnKeyReleased(
                e -> {
                    if (up) {
                        switch (e.getCode()) {
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
                        }
                    }
                    if (down) {
                        switch (e.getCode()) {
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
                        }
                    }
                    if (right) {
                        switch (e.getCode()) {
                            case UP:
                                left = false;
                                down = false;
                                right = false;
                                up = true;
                                break;
                            case DOWN:
                                up = false;
                                left = false;
                                right = false;
                                down = true;
                        }
                    }
                    if (left) {
                        switch (e.getCode()) {
                            case UP:
                                left = false;
                                down = false;
                                right = false;
                                up = true;
                                break;
                            case DOWN:
                                up = false;
                                left = false;
                                right = false;
                                down = true;
                        }
                    }
                }
        );
    }


    private void ussikeliigub(){

        if(up){
            ussike.setCenterY(y);
            ussike.setCenterX(x);
            y = y-10;
            if(ussike.getCenterX() == nomnom.getCenterX() && ussike.getCenterY() == nomnom.getCenterX()){
                counterInt++;
                nomX = (int) ((Math.round(((Math.random() * 500) + 5)) / 10) * 10);
                nomY = (int) ((Math.round(((Math.random() * 500) + 5)) / 10) * 10);
                while (nomX >=490 || nomX <= 10) {
                    nomX = (int) ((Math.round(((Math.random() * 500) + 5)) / 10) * 10);
                }
                while(nomY >=490 || nomY <= 50) {
                    nomY = (int) ((Math.round(((Math.random() * 500) + 5)) / 10) * 10);
                }
                

            }
            if(y<=50){
                gameOver.setFont(Font.font(30));
                timer.cancel();
            }
        }
        if(left){
            ussike.setCenterY(y);
            ussike.setCenterX(x);
            x = x-10;
            if(ussike.getCenterX() == nomnom.getCenterX() && ussike.getCenterY() == nomnom.getCenterX()){
                counterInt++;
            }
            if(x<=10){
                gameOver.setFont(Font.font(30));
                timer.cancel();
            }
        }
        if(right){
            ussike.setCenterY(y);
            ussike.setCenterX(x);
            x= x+10;
            if(ussike.getCenterX() == nomnom.getCenterX() && ussike.getCenterY() == nomnom.getCenterX()){
                counterInt++;
            }
            if(x>=490){
                gameOver.setFont(Font.font(30));
                timer.cancel();
            }
        }

        if(down){
            ussike.setCenterY(y);
            ussike.setCenterX(x);
            y = y+10;
            if(ussike.getCenterX() == nomnom.getCenterX() && ussike.getCenterY() == nomnom.getCenterX()){
                counterInt++;
            }
            if(y>=490){
                gameOver.setFont(Font.font(30));
                timer.cancel();
            }
        }
    }

}
