package minesweeper.minesweeper;

import android.widget.Button;

import java.util.ArrayList;

public class Aufdecken {

    private Felder felder;
    private int [] felderarray;
    private Button[] buttonarray;
    private int breite;
    private ArrayList<Integer> linkerrand;
    private ArrayList<Integer> rechterrand;
    private int[] playstatus;
    private int minenanzahl;
    private ArrayList<Integer> weitereaufdecken;

    void startaufdecken(Button btn, int btntag, int[] felderarray, Button[] buttonarray, int breite, ArrayList<Integer> linkerrand, ArrayList<Integer> rechterrand, int minenanzahl, int[] playstatus){
        felder=new Felder();
        this.felderarray = felderarray;
        this.buttonarray = buttonarray;
        this.breite = breite;
        this.rechterrand = rechterrand;
        this.linkerrand = linkerrand;
        this.minenanzahl = minenanzahl;
        this.weitereaufdecken = new ArrayList<>();
        this.playstatus = playstatus;
        aufdecken(btn, btntag);
    }

    void aufdecken(Button btn, int btntag){
        if (istzero(btntag)==true ){
            //Wenn frei dann umfeld aufdecken
            //Umfeldaufdecken gibt ín der ArrayList die Buttons zurück, welche noch aufgedeckt werden sollen
            //d.H. die Methode checkt auf Nullen und den Playstatus!
            btnumfeldaufdecken(btntag);

            System.out.println("Weiteraufdecken Inhalt      " + weitereaufdecken.toString());
            if (!weitereaufdecken.isEmpty()){
                for (int i=0; i<weitereaufdecken.size(); i++){
                    //Für den Übernommen Button soll ebenefalls das Umfeld aufgedeckt werden.
                    btnumfeldaufdecken(weitereaufdecken.get(i));
                    //System.out.println("Weiteraufdecken Inhalt      " + weitereaufdecken.toString());
                }
            }
        }else {
            //Wenn es keine Null ist, dann muss es eine Zahl sein
            //Somit kann man das Feld aufdecken und auf played setzen
            felder.setnumbericon(btn, felderarray);
            setplayed(btn);
        }
        System.out.println("Playstatus ausgeben Aufdecken Main");
        Game.arrayausgeben(playstatus);
        checkwin();
    }

    void checkwin(){
        //Für alle Felder den Playstatus checken
        //Wenn alle Felder gepsielt wurden und nur noch Minen überig sind, hat man gewonnen.
        int counter=0;
        for(int i=0; i<playstatus.length; i++) {
            //Wenn Spielestatus 0 ist und das Feld eine Mine ist, trozdem als gespielt zählen.
            //Er kann das Feld ja absichtlich freigelassen haben, da es eine Mine ist
            if (playstatus[i] == 0 && felderarray[i] == 9) {
                counter++;
            //Wenn alle Playstatus Felder auf 1 sind, hat man gewonnen
            } else if (playstatus[i] == 1) {
                counter++;
            //Wenn Das Feld ungespielt ist und keine Mine ist, dann kann das Spiel noch nicht gewonnen sein.
            } else if (playstatus[i] == 0 && felderarray[i] != 9) {
                counter = 0;
            }
        }
        //Wenn die Checks bis auf Breite*Breite=Spielfeldgröße hochgezählt haben, hat man gewonnen, da alle Felder gespielt wurden.
        if (counter == breite*breite){
            Game game = new Game();
            game.gethandler().removeCallbacksAndMessages(null);
            game.goToEndscreen(1);
        }
        System.out.println("Wincounter " +counter);
    }

    void setplayed(Button button){
        int btntag = (int) button.getTag();
        if (playstatus[btntag]==0){
            playstatus[btntag] = 1;
        }
    }

    boolean istzero(int btntag){
        if (felderarray[btntag]==0){
            return true;
        }else{
            return false;
        }
    }

    void btnumfeldaufdecken(int btntag){
        //Ersten Button Icon setzen und playstatus setzen
        felder.setnumbericon(buttonarray[btntag], felderarray);
        setplayed(buttonarray[btntag]);

        //Für die anderen (um den Button liegende) Buttons jetzt das gleiche machen.
        //Wenn der Umleigende Button frei ist, wird diser in einen Array geschrieben
        // und für ihn die ganze Prozedur nocheinmal ausgeführt
        try{
            if (!linkerrand.contains(btntag)){
                if (felderarray[btntag-1]==0 && playstatus[btntag-1]==0 ){
                    weitereaufdecken.add(btntag-1);
                    felder.setnumbericon(buttonarray[btntag-1], felderarray);
                    setplayed(buttonarray[btntag-1]);
                }else if (felderarray[btntag-1]>0 && felderarray[btntag-1]<9 && playstatus[btntag-1]==0){
                    felder.setnumbericon(buttonarray[btntag-1], felderarray);
                    setplayed(buttonarray[btntag-1]);
                }
            }
        }catch (Exception ignored){

        }

        try{
            if (!rechterrand.contains(btntag)){
                if (felderarray[btntag+1]==0 && playstatus[btntag+1]==0){
                    weitereaufdecken.add(btntag+1);
                    felder.setnumbericon(buttonarray[btntag+1], felderarray);
                    setplayed(buttonarray[btntag+1]);
                }else if (felderarray[btntag+1]>0 && felderarray[btntag+1]<9 && playstatus[btntag+1]==0){
                    felder.setnumbericon(buttonarray[btntag+1], felderarray);
                    setplayed(buttonarray[btntag+1]);
                }
            }
        }catch (Exception ignored){

        }

        try{
            if (felderarray[btntag-breite]==0 && playstatus[btntag-breite]==0){
                weitereaufdecken.add(btntag-breite);
                felder.setnumbericon(buttonarray[btntag-breite], felderarray);
                setplayed(buttonarray[btntag-breite]);
            }else if (felderarray[btntag-breite]>0 && felderarray[btntag-breite]<9 && playstatus[btntag-breite]==0){
                felder.setnumbericon(buttonarray[btntag-breite], felderarray);
                setplayed(buttonarray[btntag-breite]);
            }
        }catch (Exception ignored){

        }

        try{
            if (felderarray[btntag+breite]==0 && playstatus[btntag+breite]==0){
                weitereaufdecken.add(btntag+breite);
                felder.setnumbericon(buttonarray[btntag+breite], felderarray);
                setplayed(buttonarray[btntag+breite]);
            }else if (felderarray[btntag+breite]>0 && felderarray[btntag+breite]<9 && playstatus[btntag+breite]==0){
                felder.setnumbericon(buttonarray[btntag+breite], felderarray);
                setplayed(buttonarray[btntag+breite]);
            }
        }catch (Exception ignored){

        }

        try{
            if (!rechterrand.contains(btntag)){
                if (felderarray[btntag-(breite-1)]==0 && playstatus[btntag-(breite-1)]==0){
                    weitereaufdecken.add(btntag-(breite-1));
                    felder.setnumbericon(buttonarray[btntag-(breite-1)], felderarray);
                    setplayed(buttonarray[btntag-(breite-1)]);
                }else if (felderarray[btntag-(breite-1)]>0 && felderarray[btntag-(breite-1)]<9 && playstatus[btntag-(breite-1)]==0){
                    felder.setnumbericon(buttonarray[btntag-(breite-1)], felderarray);
                    setplayed(buttonarray[btntag-(breite-1)]);
                }
            }
        }catch (Exception ignored){

        }

        try{
            if (!linkerrand.contains(btntag)){
                if (felderarray[btntag+(breite-1)]==0 && playstatus[btntag+(breite-1)]==0){
                    weitereaufdecken.add(btntag+(breite-1));
                    felder.setnumbericon(buttonarray[btntag+(breite-1)], felderarray);
                    setplayed(buttonarray[btntag+(breite-1)]);
                }else if (felderarray[btntag+(breite-1)]>0 && felderarray[btntag+(breite-1)]<9 && playstatus[btntag+(breite-1)]==0){
                    felder.setnumbericon(buttonarray[btntag+(breite-1)], felderarray);
                    setplayed(buttonarray[btntag+(breite-1)]);
                }
            }
        }catch (Exception ignored){

        }

        try{
            if (!linkerrand.contains(btntag)){
                if (felderarray[btntag-(breite+1)]==0 && playstatus[btntag-(breite+1)]==0){
                    weitereaufdecken.add(btntag-(breite+1));
                    felder.setnumbericon(buttonarray[btntag-(breite+1)], felderarray);
                    setplayed(buttonarray[btntag-(breite+1)]);
                }else if (felderarray[btntag-(breite+1)]>0 && felderarray[btntag-(breite+1)]<9 && playstatus[btntag-(breite+1)]==0){
                    felder.setnumbericon(buttonarray[btntag-(breite+1)], felderarray);
                    setplayed(buttonarray[btntag-(breite+1)]);
                }
            }
        }catch (Exception ignored){

        }

        try{
            if (!rechterrand.contains(btntag)){
                if (felderarray[btntag+(breite+1)]==0 && playstatus[btntag+(breite+1)]==0){
                    weitereaufdecken.add(btntag+(breite+1));
                    felder.setnumbericon(buttonarray[btntag+(breite+1)], felderarray);
                    setplayed(buttonarray[btntag+(breite+1)]);
                }else if (felderarray[btntag+(breite+1)]>0 && felderarray[btntag+(breite+1)]<9 && playstatus[btntag+(breite+1)]==0){
                    felder.setnumbericon(buttonarray[btntag+(breite+1)], felderarray);
                    setplayed(buttonarray[btntag+(breite+1)]);
                }
            }
        }catch (Exception ignored){

        }
        System.out.println("Playstatus ausgeben nach dem Aufdecken");
        Game.arrayausgeben(playstatus);
    }
}
