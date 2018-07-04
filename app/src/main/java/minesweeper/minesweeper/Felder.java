package minesweeper.minesweeper;

import android.widget.Button;

public class Felder {

    boolean feldcheck(Button button, int breite, int[] markspeicher, Button[] buttonarray, int[] felderarray){
        int buttonnumber = (int) button.getTag();
        //Wenn das Feld eine Mine ist wird true zurückgegeben.
        if (felderarray[buttonnumber] == 9){
            allefelderaufdecken(buttonarray, breite, felderarray);
            return true;
        }else{
            return false;
        }
    }

    //Für jeden Button das Bild setzen
    //Arraysize minus 1, da die ArrayList bei 0 anfängt!
    void allefelderaufdecken(Button[] buttonarray, int breite, int[] felderarray){
        for(int u=0; u<breite*breite; u++){
            Button btn = buttonarray[u];
            setnumbericon(btn, felderarray);
        }
    }

    void allefelderunplayed(Button[] buttonarray, int breite){
        for (int i=0; i<breite*breite;i++){
            Button btn = buttonarray[i];
            btn.setBackgroundResource(R.drawable.unplayed);
        }
    }

    //je nach der Nummer des übergebenen Buttons wird ddas Icon gesetzt.
    //Wert des Buttons steht in der felder arraylist
    void setnumbericon(Button button, int[] felderarray){
        int buttonnumber = (int) button.getTag();
        switch (felderarray[buttonnumber]){
            default:
                System.out.println("Error, konnte Icon nicht setzen");
            case 9:
                button.setBackgroundResource(R.drawable.mine);
                break;
            case 0:
                button.setBackgroundResource(R.drawable.frei);
                break;
            case 1:
                button.setBackgroundResource(R.drawable.f1);
                break;
            case 2:
                button.setBackgroundResource(R.drawable.f2);
                break;
            case 3:
                button.setBackgroundResource(R.drawable.f3);
                break;
            case 4:
                button.setBackgroundResource(R.drawable.f4);
                break;
            case 5:
                button.setBackgroundResource(R.drawable.f5);
                break;
            case 6:
                button.setBackgroundResource(R.drawable.f6);
                break;
            case 7:
                button.setBackgroundResource(R.drawable.f7);
                break;
            case 8:
                button.setBackgroundResource(R.drawable.f8);
                break;
        }
    }

    static void setstart(int breite, int[] felderarray, Button[] buttonarray){
        //Diese Methode setzt für den ersten Freien Button eine Markierung, damit man einenPunkt zum Beginnen hat.
        for (int i=0; i<breite*breite; i++){
            if (felderarray[i]==0){
                buttonarray[i].setBackgroundResource(R.drawable.start);
                break;
            }
        }
    }

}
