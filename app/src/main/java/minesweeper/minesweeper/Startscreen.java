package minesweeper.minesweeper;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class Startscreen extends AppCompatActivity {
    private static Context mContext;
    private Intent intent;
    private Button btnleicht;
    private Button btnexperte;
    private Button btnfort;
    private Button mainende;
    private Button mainstart;
    private SeekBar s;
    private SeekBar s2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startscreen);
        erstellen();
        mContext=this;
    }

    void erstellen(){
        //Objekte genau definieren
        s = (SeekBar) findViewById(R.id.seekbar);
        s2 = (SeekBar) findViewById(R.id.seekBarMinen);
        btnleicht = findViewById(R.id.btnleicht);
        btnleicht.setOnClickListener(clickstartscreen);
        btnfort = findViewById(R.id.btnfortg);
        btnfort.setOnClickListener(clickstartscreen);
        btnexperte = findViewById(R.id.btnexperte);
        btnexperte.setOnClickListener(clickstartscreen);
        mainende = findViewById(R.id.btnmainende);
        mainende.setOnClickListener(clickstartscreen);
        mainstart = findViewById(R.id.btnmainstart);
        mainstart.setOnClickListener(clickstartscreen);
        final TextView text = (TextView) findViewById(R.id.textfeld);
        final TextView txtMinen = (TextView) findViewById(R.id.txtMinen);

        s.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int breite=seekBar.getProgress();
                text.setText("Spielfeldbreite: " + breite);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        s2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int value=seekBar.getProgress();
                txtMinen.setText("Minenanzahl: " + value);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public static Context getContext(){
        return mContext;
    }

    protected void start(){
        int breite = s.getProgress();
        int minenanzahl = s2.getProgress();
        intent = new Intent(this, Game.class);
        intent.putExtra("gamebreite", breite);
        intent.putExtra("minenanzahl", minenanzahl);
        startActivity(intent);
    }

    private void schwierigkeit(int funktion) {
        switch (funktion){
            case 1:
                s.setProgress(8);
                s2.setProgress(10);
                break;
            case 2:
                s.setProgress(16);
                s2.setProgress(40);
                break;
            case 3:
                s.setProgress(22);
                s2.setProgress(100);
                break;
        }
    }

    View.OnClickListener clickstartscreen = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btnmainstart:
                    start();
                    break;
                case R.id.btnmainende:
                    System.exit(0);
                    break;
                case R.id.btnleicht:
                    schwierigkeit(1);
                    break;
                case R.id.btnfortg:
                    schwierigkeit(2);
                    break;
                case R.id.btnexperte:
                    schwierigkeit(3);
                    break;
            }
        }
    };
}
