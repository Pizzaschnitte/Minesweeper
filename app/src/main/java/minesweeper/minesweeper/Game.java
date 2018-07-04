package minesweeper.minesweeper;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;

import static android.widget.LinearLayout.HORIZONTAL;

public class Game extends AppCompatActivity{
    private Button btntime;
    private Button btnmark;
    private Button btnende;
    //Verweiß auf die Buttons
    private Button buttonarray[] = null;
    //Enthält die Zahlenwerte: 1-8 sind die Zahlen // 0 == frei // 9 == Mine
    private int felderarray[];
    //Enthält Status darüber ob der Button markiert ist.
    private int[] markspeicher;
    //Enthält den Status ob der Button schon einmal gespielt wurde
    int[] playstatus;
    private int breite=0;
    private int minenanzahl=0;
    private boolean markieren=false;
    private boolean pause = false;
    //Klassen initalisieren
    private Felder felder;
    private Aufdecken Aufdecken;
    //Rändern, welche die Aufdeckenabfrage erleichtern
    protected static ArrayList<Integer> rechterrand;
    protected static ArrayList<Integer> linkerrand;
    //Timer
    private static int time = 0;
    private Runnable timerunnable = null;
    private static Handler handler = null;

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

    private void createbuttons(TableLayout tableLayout, int breite){
        //Buttons erstellen und einer Reihe hinzufügen
        int buttonnumber=0;
        for(int reihe=0; reihe<breite; reihe++){
            TableRow row = new TableRow(this);
            for (int i=0; i<breite; i++){
                Button btn = new Button(this);
                //Tag == Markierung für den Button unter dem man ihn aufrufen kann!
                btn.setTag(buttonnumber);
                //Check damit des Scheiß Array net überläuft
                if (buttonnumber<breite*breite){
                    buttonarray[buttonnumber]=btn;
                    buttonnumber++;
                }
                btn.setBackgroundResource(R.drawable.unplayed);
                btn.setLayoutParams(new TableRow.LayoutParams(150,150));
                btn.setOnClickListener(clickgame);
                row.addView(btn);
            }
            tableLayout.addView(row);
        }
        HorizontalScrollView hr = (HorizontalScrollView) findViewById(R.id.hr);
        hr.addView(tableLayout);
    }

    Button markierenbutton(Button checkbutton){
        int btntag = (int) checkbutton.getTag();
        //Abfrage ob der Button schon markiert ist und ob der Button schon gespielt wurde.
        System.out.println("Markieren Playstatus");
        arrayausgeben(playstatus);

        //Wenn auf dem Button noch kein Marker ist (markspeicher == 0)
        //und der spielstatus Null ist
        if(markspeicher[btntag]==0 && playstatus[btntag]==0 ){
            markspeicher[btntag]=1;
            //Aufdecken.setplayed(buttonarray[btntag]);
            System.out.println("Markiert Button mit dem Tag: " + btntag);
            checkbutton.setBackgroundResource(R.drawable.marker);
        }else if(markspeicher[btntag]==1){
            markspeicher[btntag] = 0;
            //Aufdecken.setplayed(buttonarray[btntag]);
            System.out.println("UnMarkiert den Button mit dem Tag: " + btntag);
            checkbutton.setBackgroundResource(R.drawable.unplayed);
        }
        return checkbutton;
    }

    static Handler gethandler(){
        return handler;
    }

    public void goToEndscreen(int Funktion) {
        Context mContext = Startscreen.getContext();
        Intent endscreen = new Intent(mContext, Endscreen.class);
        endscreen.putExtra("Funktion", Funktion);
        endscreen.putExtra("Dauer", time);
        mContext.startActivity(endscreen);
    }

    static void arrayausgeben(int[] array){
        for(int i=0; i<array.length; i++){
            System.out.print(array[i]);
        }
    }

    void createobjekts(){
        //Arrays erstellen lassen
        buttonarray = new Button[breite*breite];
        markspeicher = new int[breite*breite];
        felderarray = new int[breite*breite];
        rechterrand = new ArrayList<>();
        linkerrand = new ArrayList<>();
        playstatus = new int[breite*breite];
        btnende = findViewById(R.id.btnende);
        btnende.setOnClickListener(clickgamemenue);
        btntime = findViewById(R.id.btntime);
        btntime.setOnClickListener(clickgamemenue);
        btnmark = findViewById(R.id.btnmark);
        btnmark.setOnClickListener(clickgamemenue);
        felder = new Felder();
        Aufdecken = new Aufdecken();
        //Timer erstellen
        time=0;
        handler = new Handler();
        timerunnable = new Runnable() {
            @Override
            public void run() {
                time++;
                Button btntime = findViewById(R.id.btntime);
                String timestring = String.valueOf(time);
                btntime.setText(timestring);
                //System.out.println(time);
                handler.postDelayed(this, 1000);
            }
        };
    }

    void ButtonOnClickGame(View view1){
        Button btn = findViewById(view1.getId());
        switch (view1.getId()){
            case R.id.btnende:
                handler.removeCallbacksAndMessages(null);
                goToEndscreen(2);
                break;
            case R.id.btntime:
                if (pause==false){
                    pause=true;
                    handler.removeCallbacksAndMessages(null);
                }else if (pause==true){
                    pause=false;
                    timerunnable.run();
                }
                break;
            case R.id.btnmark:
                if (markieren==true){
                    markieren=false;
                    btn.setText("Markieren aus");
                }else if (markieren==false){
                    markieren=true;
                    btn.setText("Markieren an");
                }
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //Aktivität erstellen
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Die folgenden Aktionen werden immer nur ein mal ausgeführt beim starten des Intents

        //Daten aus Intent auslesen
        Intent gfh = getIntent();
        breite = gfh.getIntExtra("gamebreite", 0);
        minenanzahl = gfh.getIntExtra("minenanzahl",0);
        System.out.println("Spielfeldbreite: " + breite);

        //Tablelayout für die Buttons erstellen
        TableLayout tableLayout = new TableLayout(this);
        tableLayout.setOrientation(HORIZONTAL);

        //Objekte erstellen inkl. Timer
        createobjekts();

        //Ränder erstellen
        createraender(breite);

        //Spielfeld-Buttons erstellen
        createbuttons(tableLayout, breite);

    }

    View.OnClickListener clickgamemenue = new View.OnClickListener() {
        @Override
        public void onClick(View viewgamemenue) {
            Button btn = findViewById(viewgamemenue.getId());
            switch (viewgamemenue.getId()){
                case R.id.btnende:
                    handler.removeCallbacksAndMessages(null);
                    goToEndscreen(2);
                    break;
                case R.id.btnmark:
                    if (markieren==true){
                        markieren=false;
                        btn.setText("Markieren aus");
                    }else if (markieren==false){
                        markieren=true;
                        btn.setText("Markieren an");
                    }
                    break;
                case R.id.btntime:
                    if (pause==false){
                        pause=true;
                        handler.removeCallbacksAndMessages(null);
                    }else if (pause==true){
                        pause=false;
                        timerunnable.run();
                    }
                    break;
            }
        }
    };

    //Ist beim Start flase
    //wir beim ersten Buttonklick auf true gesetzt
    //Wenn die Variable noch auf False steht, bedeutet das, dass Alle Feldwerte usw. generiert werden müssen.
    //Wenn sie auf True steht, wird ganz normal das Feld aufgedeckt.
    protected static boolean start = false;
    //Für Debugging Zweck erstmal auf true gestellt.

    private void firststart(){
        //Bei ersten Klick müssen die Zahlen gerniert werden und an der Stelle wo man klickt
        //muss das Feld frei bleiben
        start=true;

        //Debug Ausgaben
        System.out.println("Breite " + breite + minenanzahl);

        //Zahlen erstellen
        Zahlen zahlen = new Zahlen();
        zahlen.generateminen(breite, felderarray, minenanzahl);

        //Neue Zahlen für jedes Feld generieren, Breite Holt er sich aus der Globalen statischen Variablen
        for(int i=0; i<breite*breite; i++){
            Button btn = buttonarray[i];
            int buttonnumber = (int) btn.getTag();
            zahlen.createnumbersmain(buttonnumber, btn, felderarray, breite);
        }

        //Startfeld setzen
        Felder.setstart(breite, felderarray, buttonarray);

        //Timer starten
        timerunnable.run();
    }

    View.OnClickListener clickgame = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (start==false){
                firststart();
            }else if (start==true){
                //Buttontext auslesen und in eine Zahl umwandeln
                Button btn = (Button)view;
                int btntag= (int) btn.getTag();
                System.out.println(btntag);

                //Wenn Markieren aus Wahr ist, wird der Button markiert
                //Wenn Markiern auf Falsch ist, und der Markspeicher keinen wert für den
                //Button hat, wir die Methode für den Feldcheck aufgerufen.
                if (markieren==false && markspeicher[btntag]==0 && pause == false) {
                    //Check ob der Button eine Mine ist und gibt "false" oder "true" zurück.
                    if (!felder.feldcheck(btn, breite, markspeicher, buttonarray, felderarray)){
                        Aufdecken.startaufdecken(btn, btntag, felderarray, buttonarray, breite, linkerrand, rechterrand, minenanzahl, playstatus);
                    }else{
                        handler.removeCallbacksAndMessages(null);
                        goToEndscreen(2);
                    }
                }else if (markieren==true && pause == false){
                    System.out.println("markieren" + btn);
                    markierenbutton(btn);
                }
            }
        }
    };
}
