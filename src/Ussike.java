import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Timer;

/**
 * Created by toomas on 7.11.16.
 */
public class Ussike {

    private boolean up = false;//kui up=TRUE siis uss liigub üles
    private boolean left = false;//kui left=TRUE siis uss liigub vasakule
    private boolean down = true;//...
    private boolean right = false;//...
    private int x;//ussi X kordinaat
    private int y;//ussi Y kordinaat
    private Circle[] ussike = new Circle[500];//Ussike koosneb ringikujulistest lülidest
    private int counterInt = 0;//selleks, et loendada - algselt on int, mis muudetakse stringiks ekraanile
    private int nomX = 200;//esimese nomnomi koordinaat
    private int nomY = 200;//esimese nomnomi ordinaat
    private Text gameOver;//SP
    private ArrayList<Integer> asukohtX = new ArrayList();
    private ArrayList<Integer> asukohtY = new ArrayList();

    int ussilylid = 1;

    private void poora() {

        ussike[0].setOnKeyPressed(
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


    private void ussikeliigub(){//kõik mida ussike peab tegema kui ta liigub mingis suunas

        if(up){//ussike liigub üles
            y= y-10;
            ussiSaba();
            if(y<=40){//kui lähed alast välja
                gameOverJEE();
            }
        }
        if(left){
            x=x-10;
            ussiSaba();
            if(x<=10){
                gameOverJEE();
            }
        }
        if(right){

            x=x+10;
            ussiSaba();
            if(x>=490){
                gameOverJEE();
            }
        }
        if(down){
            y=y+10;
            ussiSaba();
            if(y>=490){
                gameOverJEE();
            }
        }
    }




    private void allDirectionsFalse(){//ükskõik mis noolt vajutada, kõigepealt paneb kõik falseks ja kohe peale seda muudab ühe suuna trueks
        up = false;
        down = false;
        right = false;
        left = false;
    }

    private void setCenter(){//lühendada koodi
        ussike[0].setCenterX(x);
        ussike[0].setCenterY(y);
        while (ussilylid <= counterInt) {
            ussike[ussilylid].setCenterX(asukohtX.get(counterInt - ussilylid));
            ussike[ussilylid].setCenterY(asukohtY.get(counterInt - ussilylid));
            if(ussike[0].getCenterX() == ussike[ussilylid].getCenterX() && ussike[0].getCenterY() == ussike[ussilylid].getCenterY()){
                gameOverJEE();
            }
            ussilylid++;

        }
        ussilylid = 1;

    }


    private final void ussiSaba(){
        asukohtX.add(x);
        asukohtY.add(y);
        if(ussike[0].getCenterX() == nomX && ussike[0].getCenterY() == nomY){//kui ussike läheb samale asukohale kus on nomnom, siis tee järgmist
            counterInt++;//suurenda skoori
            makenomnom();
            asukohtX.add(x);
            asukohtY.add(y);
            ussike[counterInt] = new Circle(asukohtX.get(counterInt-1), asukohtY.get(counterInt-1), 5, Color.GREEN);
            rootPane.getChildren().add(ussike[counterInt]);
        }
        setCenter();

    }
    public void gameOverJEE(){//lühendada koodi
        gameOver.setFont(Font.font(30));//gameover yeee
        aeg.stop();//mäng läbi, enam pole animatsiooni
    }
}
