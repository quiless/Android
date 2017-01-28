package com.quiles.workout;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class Inicio extends AppCompatActivity {

    ImageView logo;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        logo = (ImageView) findViewById(R.id.img_inicio);
        logo.animate().alpha(1f).setDuration(4000);
        Thread welcomeThread = new Thread() {

            @Override
            public void run() {
                try {
                    super.run();
                    sleep(6000);  //Delay 10 segundos
                } catch (Exception e) {

                } finally {

                    Intent i = new Intent(Inicio.this, TelaPrincipal.class);
                    startActivity(i);
                    finish();
                }
            }
        };
        welcomeThread.start();
    }

}
