/**
 * Created by toomas on 7.11.16.
 */
public class Nomnom {
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
}
