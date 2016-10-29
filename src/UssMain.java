import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.sql.*;

/**
 * @author Toomas
 * @version 23/10/16
*/
public class UssMain extends Application {
    private boolean up = false;//kui up=TRUE siis uss liigub üles
    private boolean left = false;//kui left=TRUE siis uss liigub vasakule
    private boolean down = true;//...
    private boolean right = false;//...
    private int x;//ussi X kordinaat
    private int y;//ussi Y kordinaat
    private Circle[] ussike = new Circle[500];//Ussike koosneb ringikujulistest lülidest
    private Timer timer;//timer, et mäng toimiks ja animeeruks
    private Rectangle mangukast;//ala, mille vastu ei tohi minna uss
    private Circle nomnom;//nomnom - eat that!
    private Text gameOver;//SP
    private Text counter;//loendab mitu nomnomi ussike on ära söönud
    private int counterInt = 0;//selleks, et loendada - algselt on int, mis muudetakse stringiks ekraanile
    private int nomX = 250;//esimese nomnomi koordinaat
    private int nomY = 250;//esimese nomnomi ordinaat
    private int countdownInt;//loenduri int väärtused
    private Text countdown;//loendur tekstina java FXis
    private Connection conn = null;
    private ArrayList<Integer> asukohtX = new ArrayList();
    private ArrayList<Integer> asukohtY = new ArrayList();
    private Pane rootPane;
    private String[] test = new String[20];
    int ussilylid = 1;
    int i = 0;
    private AnimationTimer aeg;

    public static void main(String[] args) {
        launch(args);

        andmebaas test = new andmebaas();
        ResultSet res;

        try {
            res = test.displayUsers();
            while(res.next()){
                System.out.println(res.getString("fname") + res.getString("score"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void start(Stage primaryStage) throws Exception {



        rootPane = new Pane();//pea paneel(ilma layoutita)
        Scene rootScene = new Scene(rootPane, 500, 500);//500,500 suuruses ja stseenis on rootpane
        rootScene.setFill(Color.LIGHTGRAY);//sest et miks mitte
        mangukast = new Rectangle(480, 450);//mänguala pole ruut, :O
        mangukast.setStroke(Color.DARKGRAY);//et sa ikka näeks mänguala
        mangukast.setStrokeWidth(5);//ka ilma prillideta
        mangukast.setFill(Color.TRANSPARENT);//siis näed ka kastisisu
        mangukast.setTranslateX(10);//paneme kasti X paika
        mangukast.setTranslateY(40);//y paika

        primaryStage.setScene(rootScene);
        primaryStage.setResizable(false);//EI TEE mänguala suuremaks!

        x = 250;//stardipositsiooni koordinaat
        y = 250;//stardipositsiooni ordinaat
        ussike[0] = new Circle(x, y, 5, Color.RED);//woop we got a worm

        nomnom = new Circle(nomX, nomY, 5, Color.CHOCOLATE); //nomnom söö ära

        counter = new Text();//loenda
        counter.setFont(Font.font(20));//siis näed ka
        counter.setX(250);//loenduri asukoha X
        counter.setY(25);//loenduri asukoha Y


        gameOver = new Text("GAME OVER JEE");//said lutti
        gameOver.setFont(Font.font(0));//alguses sa ei näe, et niikuinii saad kunagi lutti
        gameOver.setX(125);//kuhu see esile tõsta koordinaat
        gameOver.setY(250);//kuhu see esile tõsta ordinaat
        gameOver.setFill(Color.RED);//punane

        countdownInt = 3;
        countdown = new Text(Integer.toString(countdownInt));
        countdown.setFont(Font.font(50));
        countdown.setX(225);
        countdown.setY(100);


        rootPane.getChildren().addAll(countdown, gameOver, counter, nomnom, ussike[0], mangukast);//kõik peab ikka ekraanile jääma

        primaryStage.show();//abraka dabra, kõike on näha
        ussike[0].requestFocus();//kontrolli ussi

        // jälgib nuppude liigutusi
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

/*
        timer = new Timer();//et hakkaks aega loendama
        //mängu animatsioon ja counter refresh iga 0,5 sek tagant
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(countdownInt == 0){
                    timer.cancel();
                    timer = new Timer();
                    timer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        counter.setText(Integer.toString(counterInt));
                        ussikeliigub();
                        countdown.setFont(Font.font(0));
                        System.out.println(asukohtX + " : " + asukohtY);

                        if(ussike[0].getCenterX() == nomX && ussike[0].getCenterY() == nomY) {//kui ussike läheb samale asukohale kus on nomnom, siis tee järgmist
                            counterInt++;//suurenda skoori
                            makenomnom();

                        }
                        if (counterInt <= asukohtX.size()){
                            asukohtX.remove(0);
                            asukohtY.remove(0);
                        }
                    }
                } , 0, 200);
                } else {
                    countdownInt = countdownInt - 1;
                    countdown.setText(Integer.toString(countdownInt));
                }

            }
        }, 1000, 1000);
*/

        aeg = new AnimationTimer() {
            @Override
            public void handle(long now) {
                counter.setText(Integer.toString(counterInt));
                ussikeliigub();
                countdown.setFont(Font.font(0));
                System.out.println(asukohtX + " : " + asukohtY);
                if(ussike[0].getCenterX() == nomX && ussike[0].getCenterY() == nomY) {//kui ussike läheb samale asukohale kus on nomnom, siis tee järgmist
                    counterInt++;//suurenda skoori
                    makenomnom();
                    ussike[counterInt] = new Circle(asukohtX.get(counterInt-1), asukohtY.get(counterInt-1), 5, Color.GREEN);
                    //ussike[1] = new Circle(asukohtX.get(0), asukohtY.get(0), 5, Color.GREEN);
                    //rootPane.getChildren().removeAll(countdown, gameOver, counter, nomnom, ussike[0], mangukast);
                    //rootPane.getChildren().addAll(countdown, gameOver, counter, nomnom, ussike[1], ussike[0], mangukast);
                }
                if (counterInt <= asukohtX.size()){
                    asukohtX.remove(0);
                    asukohtY.remove(0);
                }
                if(counterInt>0){
                    rootPane.getChildren().addAll()
                }

            }




    };
    aeg.start();

    }




    private void ussikeliigub(){//kõik mida ussike peab tegema kui ta liigub mingis suunas

        if(up){//ussike liigub üles
            setCenter();//iga kord kui preset arv sek möödas, siis muudab ussi asukohta ja triggerib uuesti setCenterX ja Y
            y= y-10;
            asukohtX.add(x);
            asukohtY.add(y+10);
            if(ussike[0].getCenterX() == nomX && ussike[0].getCenterY() == nomY){//kui ussike läheb samale asukohale kus on nomnom, siis tee järgmist
                counterInt++;//suurenda skoori
                makenomnom();
            }
            if(y<=40){//kui lähed alast välja
                gameOverJEE();
            }
        }
        if(left){
            setCenter();
            x=x-10;
            asukohtX.add(x+10);
            asukohtY.add(y);
            if(ussike[0].getCenterX() == nomX && ussike[0].getCenterY() == nomY){
                counterInt++;
                makenomnom();
            }
            if(x<=10){
                gameOverJEE();
            }
        }
        if(right){
            setCenter();
            x=x+10;
            asukohtX.add(x-10);
            asukohtY.add(y);
            if(ussike[0].getCenterX() == nomX && ussike[0].getCenterY() == nomY){
                counterInt++;
                makenomnom();
            }
            if(x>=490){
                gameOverJEE();
            }

        }

        if(down){
            setCenter();
            y=y+10;
            asukohtX.add(x);
            asukohtY.add(y-10);
            if(ussike[0].getCenterX() == nomX && ussike[0].getCenterY() == nomY){
                counterInt++;
                makenomnom();
            }
            if(y>=490){
                gameOverJEE();
            }
        }
    }
    private void makenomnom(){//lühendada koodi
        nomX = (int) ((Math.round(((Math.random() * 500) + 5)) / 10) * 10);//niimoodi kalkuleerib arvu vahemikus 10-500
        nomY = (int) ((Math.round(((Math.random() * 500) + 5)) / 10) * 10);//niimoodi kalkuleerib arvu vahemikus 10-500
        while (nomX >=490 || nomX <= 10) {//jälgib, et arv jääks mänguruumi alasse
            nomX = (int) ((Math.round(((Math.random() * 500) + 5)) / 10) * 10);
        }
        while(nomY >=490 || nomY <= 50) {//jälgib, et arv jääks mänguruumi alasse
            nomY = (int) ((Math.round(((Math.random() * 500) + 5)) / 10) * 10);
        }
        nomnom.setCenterX(nomX);//võta nomnomile uus nomX
        nomnom.setCenterY(nomY);//võta nomnomile uus nomY
    }
    private void setCenter(){//lühendada koodi
        ussike[0].setCenterX(x);
        ussike[0].setCenterY(y);

    }
    private void gameOverJEE(){//lühendada koodi
        gameOver.setFont(Font.font(30));//gameover yeee
        aeg.stop();//mäng läbi, enam pole animatsiooni
    }
    private void allDirectionsFalse(){//ükskõik mis noolt vajutada, kõigepealt paneb kõik falseks ja kohe peale seda muudab ühe suuna trueks
        up = false;
        down = false;
        right = false;
        left = false;
    }
    private final void ussiSaba(){


    }
}
