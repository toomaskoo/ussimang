import javafx.animation.AnimationTimer;
import javafx.application.Application;
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
    private int nomX = 200;//esimese nomnomi koordinaat
    private int nomY = 200;//esimese nomnomi ordinaat
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
    private long lastRender = 0;
    private int mangukiirus = 100000000;

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

        aeg = new AnimationTimer(){
            @Override
            public void handle(long now) {
                if(now < lastRender + mangukiirus) {//muudab mängu kiirust
                    return;
                }
                lastRender = now;
                counter.setText(Integer.toString(counterInt));
                ussikeliigub();
                countdown.setFont(Font.font(0));
                if (counterInt <= asukohtX.size()){
                    asukohtX.remove(0);
                    asukohtY.remove(0);
                }
            }
    };
    aeg.start();
    }






    public void gameOverJEE(){//lühendada koodi
        gameOver.setFont(Font.font(30));//gameover yeee
        aeg.stop();//mäng läbi, enam pole animatsiooni
    }

}
