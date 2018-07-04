package minesweeper.minesweeper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class Endscreen extends AppCompatActivity {

    private Button btnnochmalspielen;
    private Button btnendscreenende;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endscreen);

        //Intent Variablen auslesen
        Intent endscreen = getIntent();
        int Funktion = endscreen.getIntExtra("Funktion", 2);
        int dauer = endscreen.getIntExtra("Dauer", 0);

        //Buttons erstellen
        btnendscreenende=findViewById(R.id.btnendscreenende);
        btnendscreenende.setOnClickListener(clickendscreen);
        btnnochmalspielen=findViewById(R.id.btnnochmalspielen);
        btnnochmalspielen.setOnClickListener(clickendscreen);

        Game.start = false;

        //Labels
        TextView txtdauer = findViewById(R.id.txtdauer);
        TextView txtendscreen = findViewById(R.id.txtendscreen);
        switch (Funktion) {
            case 1:
                //Gewonnen
                txtendscreen.setText("Du hast gewonnen!");
                txtdauer.setText("Du hast " + dauer + " Sekunden gebraucht! ");
                break;
            case 2:
                //Verloren
                txtendscreen.setText("Du hast verloren! ");
                txtdauer.setText("Du hast " + dauer + " Sekunden verschwendet! ");
                break;
            default:
                break;
        }
    }

    void nochmal(){
        Intent Startscreen = new Intent(this, Startscreen.class);
        Startscreen.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(Startscreen);
    }

    void ButtonOnClickEndscreen(View view2){
        switch (view2.getId()){
            case R.id.btnnochmalspielen:
                nochmal();
                break;
            case R.id.btnendscreenende:
                System.out.println("Beenden");
                Intent ende = new Intent();
                ende.setAction(Intent.ACTION_MAIN);
                ende.addCategory(Intent.CATEGORY_HOME);
                startActivity(ende);
                break;
        }
    }

    View.OnClickListener clickendscreen = new View.OnClickListener() {
        @Override
        public void onClick(View viewendscreen) {
            switch (viewendscreen.getId()){
                case R.id.btnnochmalspielen:
                    nochmal();
                    break;
                case R.id.btnendscreenende:
                    System.out.println("Beenden");
                    Intent ende = new Intent();
                    ende.setAction(Intent.ACTION_MAIN);
                    ende.addCategory(Intent.CATEGORY_HOME);
                    startActivity(ende);
                    break;
            }
        }
    };
}
