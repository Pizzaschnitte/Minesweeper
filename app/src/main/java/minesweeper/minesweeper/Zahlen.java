package minesweeper.minesweeper;

import android.widget.Button;

import java.util.ArrayList;
import java.util.Random;

public class Zahlen {

    private ArrayList linkerrand;
    private ArrayList rechterrand;


    protected int random(int anzahlfelder){
        Random randomgenerator = new Random();
        int feldnummer = randomgenerator.nextInt(anzahlfelder-1) ;
        return feldnummer;
    }

    protected int checkmine(int number, int[] felderarray){
        //gibt 1 für Mine oder 0 für keine Mine zurück
        try{
            if (felderarray[number]==(9)){
                return 1;
            }else {
                return 0;
            }
        }catch(Exception e){
            System.out.println("Check Mine out of Bounds or Fatal Error!");
            return 0;
        }
    }

    void generateminen(int breite, int[] felderarray, int minenanzahl, int startbutton){
        //Solange Minenanzahl nicht den vorgegebenen Wert erreicht hat,
        //erstelle eine Zufallszahls-Feldzahl, welchem die Mine zugewiesen wird
        while(0<minenanzahl){
            int random = random(breite*breite);
            //falls diese Feld-Zahl schon eine Mine hat, oder Sie im Umfeld des Anfangsfelds liegt,
            //wird die Minenzahl nicht verändert.
            if(checkmine(random, felderarray)!=1 && random!=startbutton
                    && random!=startbutton-1 && random!=startbutton+1
                    && random!=startbutton-breite && random!=startbutton+breite
                    && random!=startbutton-(breite+1)  && random!=startbutton+(breite+1)
                    && random!=startbutton-(breite-1) && random!=startbutton+(breite-1)){
                felderarray[random] = 9;
                minenanzahl--;
            }
        }
    }

    void createnumbers(Button button, int breite, int[] felderarray){
        //Nummer des Buttons auslesen
        int buttonnumber = (int) button.getTag();
        //In der Variablen ZW wird der Wert zwischengespeichert, der dem Button dann gegenen wird.
        //Startet mit 0 == Leeres Feld
        int zw = 0;
        //Jetzt werden die umleigenden Felder überprüft.
        //Wenn eine Mine angrenzt, wird die Feldzahl erhöht.
        zw = zw + checkmine(buttonnumber-1, felderarray);
        zw = zw + checkmine(buttonnumber+1, felderarray);
        zw = zw + checkmine(buttonnumber-(breite-1), felderarray);
        zw = zw + checkmine(buttonnumber+(breite-1), felderarray);
        zw = zw + checkmine(buttonnumber-breite, felderarray);
        zw = zw + checkmine(buttonnumber+breite, felderarray);
        zw = zw + checkmine(buttonnumber-(breite+1),felderarray);
        zw = zw + checkmine(buttonnumber+(breite+1), felderarray);
        felderarray[buttonnumber]=zw;
    }

    void createnumberslinkerrand(Button button, int breite, int[] felderarray){
        int buttonnumber = (int) button.getTag();
        int zw = 0;
        zw = zw + checkmine(buttonnumber+1, felderarray);
        zw = zw + checkmine(buttonnumber-(breite -1), felderarray);
        zw = zw + checkmine(buttonnumber- breite, felderarray);
        zw = zw + checkmine(buttonnumber+ breite, felderarray);
        zw = zw + checkmine(buttonnumber+(breite +1), felderarray);
        felderarray[buttonnumber]=zw;
    }

    void createnumbersrechterrand(Button button, int breite, int[] felderarray){
        int buttonnumber = (int) button.getTag();
        int zw = 0;
        zw = zw + checkmine(buttonnumber-1,felderarray);
        zw = zw + checkmine(buttonnumber+(breite-1), felderarray);
        zw = zw + checkmine(buttonnumber-breite, felderarray);
        zw = zw + checkmine(buttonnumber+breite, felderarray);
        zw = zw + checkmine(buttonnumber-(breite+1), felderarray);
        felderarray[buttonnumber]=zw;
    }

    void createnumbersmain(int buttonnumber, Button btn, int[] felderarray, int breite){
        rechterrand = Game.rechterrand;
        linkerrand = Game.linkerrand;

        if(checkmine(buttonnumber, felderarray)!=1){
            if(linkerrand.contains(buttonnumber) ){
                createnumberslinkerrand(btn, breite, felderarray);
            }
            if(rechterrand.contains(buttonnumber)){
                createnumbersrechterrand(btn, breite, felderarray);
            }
            if(!rechterrand.contains(buttonnumber) && !linkerrand.contains(buttonnumber)){
                createnumbers(btn, breite, felderarray);
            }
        }
    }
}
