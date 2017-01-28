package com.quiles.a7minutosworkout;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

public class TelaVideos extends AppCompatActivity {

    WebView video;
    TextView titulo;
    TextToSpeech object;
    int treinoSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_videos);

        video = (WebView) findViewById(R.id.web_viewVideo);
        titulo = (TextView) findViewById(R.id.txt_nomeExercicio);

        video.setWebViewClient(new MyBrowser());
        video.getSettings().setJavaScriptEnabled(true);
        video.loadUrl("https://www.youtube.com/embed/" + "mKT5HrkozcI" + "?autoplay=1&vq=small");
        video.setWebChromeClient(new WebChromeClient());

        String nomeVideo = getIntent().getExtras().getString("nomeVideo");
        titulo.setText(nomeVideo);

        object = new TextToSpeech(TelaVideos.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS){
                    object.speak("Watch the videos and start your workout", TextToSpeech.QUEUE_FLUSH, null);
                } else {
                    Toast.makeText(getApplicationContext(), "Não suporta VoiceToSpeech", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private class MyBrowser extends WebViewClient{
        public boolean overrideUrlLoading (WebView view, String url){
            view.loadUrl(url);
            return true;
        }
    }

    public void loadTelaRun(View view){
        Intent intent = new Intent(getApplicationContext(), TelaRun.class);
        intent.putExtra("numeroTreino", treinoSelecionado);
        startActivity(intent);
    }

}
