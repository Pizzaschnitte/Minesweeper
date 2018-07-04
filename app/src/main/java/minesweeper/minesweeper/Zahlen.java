package minesweeper.minesweeper;

import android.widget.Button;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public class Zahlen {

    protected int random(int anzahlfelder){
        Random randomgenerator = new Random();
        int feldnummer = randomgenerator.nextInt(anzahlfelder-1) ;
        return feldnummer;
    }

    protected int checkmine(int number, int[] felderarray){
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

    void generateminen(int breite, int[] felderarray, int minenanzahl){
        while(0<minenanzahl){
            int random = random(breite*breite);
            if(checkmine(random, felderarray)!=1){
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

    private ArrayList linkerrand;
    private ArrayList rechterrand;

    void createnumbersmain(int buttonnumber, Button btn, int[] felderarray, int breite){
        boolean contains;
        createraender(breite);

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

    void createraender(int breite){
        rechterrand = new ArrayList<>();
        linkerrand = new ArrayList<>();
        //Ränder beschreiben
        // Erleichtert die Checks, da nur abgefragt werden muss, ob der Button im Rand ist.
        int rechts = -1;
        int links = 0;
        do{
            linkerrand.add(links);
            links=links+breite;
        }while(links<breite*breite);
        do{
            if(rechts>0){
                rechterrand.add(rechts);
            }
            rechts=rechts+breite;
        }while(rechts<breite*breite);
        System.out.println("Linker Rand: " + linkerrand);
        System.out.println("Rechter Rand: " + rechterrand);
    }
}
