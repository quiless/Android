package com.quiles.a7minutosworkout;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class TelaRun extends AppCompatActivity {

    int exercicio = 0;
    int tempoExercicio;
    int tempoDescanso = 20;
    boolean estadoTreino = false;
    boolean acabandoTempo;

    String[] listaTreino = {
                    "Ombro1",
                    "Ombro2",
                    "Ombro3",
                    "Ombro4",
                    "Ombro5",
                    "Ombro6",
                    "Ombro7",
                    "Ombro8",
                    "Ombro9",
                    "Ombro10",
    };

    int[] listaTempos = {
            10,
            10,
            10,
            20,
            20,
            20,
            30,
            30,
            30,
            30
    };


    ProgressBar progressBarTempo;
    TextView exercicioAtual;
    TextView tempo;
    TextView textoProximoExercicio;
    TextView proximoExercicio;
    ImageView imagemEstrela;
    RelativeLayout fundoTela;
    Button btnIniciar;
    CountDownTimer countDownTimer;
    TextToSpeech object;
    int result;
    int treinoSelecionado;
    ImageView estrela;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_run);

        progressBarTempo = (ProgressBar) findViewById(R.id.progressbar_tempo);
        exercicioAtual = (TextView) findViewById(R.id.txt_exercicioAtual);
        tempo = (TextView) findViewById(R.id.txt_tempo);
        textoProximoExercicio = (TextView) findViewById(R.id.txt_textoProximo);
        proximoExercicio = (TextView) findViewById(R.id.txt_proximoExercicio);
        btnIniciar = (Button) findViewById(R.id.btn_iniciarTreino);
        fundoTela = (RelativeLayout) findViewById(R.id.content_tela_run);
        estrela = (ImageView) findViewById(R.id.image_star);

        tempoExercicio = listaTempos[exercicio];
        tempo.setText(String.valueOf(exercicio));
        progressBarTempo.setMax(listaTreino.length * 2 );
        progressBarTempo.setProgress(1);

        treinoSelecionado = getIntent().getExtras().getInt("numeroTreino");
        String[] listaTreino =  {};
        switch (treinoSelecionado){
            case 0: listaTreino = new String[]{
                    "Ombro1",
                    "Ombro2",
                    "Ombro3",
                    "Ombro4",
                    "Ombro5",
                    "Ombro6",
                    "Ombro7",
                    "Ombro8",
                    "Ombro9",
                    "Ombro10",
            };
                break;
            case 1: listaTreino = new String[]{
                    "Biceps1",
                    "Biceps2",
                    "Biceps3",
                    "Biceps4",
                    "Biceps5",
                    "Biceps6",
                    "Biceps7",
                    "Biceps8",
                    "Biceps9",
                    "Biceps10",
            };
                break;
            case 2: listaTreino = new String[]{
                    "Triceps1",
                    "Triceps2",
                    "Triceps3",
                    "Triceps4",
                    "Triceps5",
                    "Triceps6",
                    "Triceps7",
                    "Triceps8",
                    "Triceps9",
                    "Triceps10",
            };
                break;
            default: listaTreino = new String[]{
                    "Teste",
                    "Teste",
                    "Teste",
                    "Teste",
                    "Teste",
                    "Teste",
                    "Teste",
                    "Teste",
                    "Teste",
                    "Teste",
            };
        }

        exercicioAtual.setText(String.valueOf(listaTreino[exercicio]));
        proximoExercicio.setText(String.valueOf(listaTreino[exercicio + 1]));

        object = new TextToSpeech(TelaRun.this, new TextToSpeech.OnInitListener(){
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS){
                    result = object.setLanguage(Locale.ENGLISH);
                    object.speak("Get Ready", TextToSpeech.QUEUE_FLUSH, null);
                } else {
                    Toast.makeText(getApplicationContext(), "Não suporta VoiceSpeech", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public  void clicaBotao (View view){
        if(!estadoTreino){
            estadoTreino = true;
            btnIniciar.setText("PAUSE");
            nextExercise();
        } else {
            estadoTreino = false;
            btnIniciar.setText("CONTINUE");
            stopTempo();
        }
    }

    public void stopTempo(){
        countDownTimer.cancel();
    }

    public void atualizaLabelTempo(int secondsLeft) {
        if (secondsLeft < 4 && !acabandoTempo){
            acabandoTempo = true;
        object.speak("3 seconds remind", TextToSpeech.QUEUE_FLUSH, null);
        }
        tempo.setText(Integer.toString(secondsLeft));
    }


    public void nextExercise(){
        object.speak("Start", TextToSpeech.QUEUE_FLUSH, null);
        acabandoTempo = false;
        progressBarTempo.setProgress(progressBarTempo.getProgress()+1);
        //tempo de exercicio * 1000
        countDownTimer = new CountDownTimer(tempoExercicio * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                atualizaLabelTempo ((int) millisUntilFinished / 1000 );
            }
            @Override
            public void onFinish() {
                if(exercicio < listaTreino.length){
                    iniciaDescanso();
                } else {
                    acabouTreino();
                }
            }
        }.start();

        fundoTela.setBackgroundColor(Color.parseColor("#373737"));
        exercicioAtual.setText(String.valueOf(listaTreino[exercicio]));
        if(exercicio<listaTreino.length-1){
            proximoExercicio.setText(String.valueOf(listaTreino[exercicio+1]));
        } else {
            proximoExercicio.setText("...");
        }
            tempo.setText(String.valueOf(tempoExercicio));
            exercicio++;
    }

    public void iniciaDescanso() {
        object.speak("Rest!", TextToSpeech.QUEUE_FLUSH, null);
        acabandoTempo = false;
        fundoTela.setBackgroundColor(Color.parseColor("#567EC3"));
        exercicioAtual.setText("Descansar");
        tempoExercicio = tempoDescanso;
        progressBarTempo.setProgress(progressBarTempo.getProgress() + 1);
        countDownTimer = new CountDownTimer(tempoExercicio * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                atualizaLabelTempo((int) millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {

                nextExercise();
            }
        }.start();

    }

    public void acabouTreino(){
        exercicioAtual.setText("Treino finalizado");
        imagemEstrela.setScaleX(0);
        imagemEstrela.setScaleX(0);
        imagemEstrela.animate().scaleX(0.85f).scaleY(0.85f).rotationBy(720).setDuration(2000);
        imagemEstrela.animate().alpha(1f).setDuration(2000);
        fundoTela.setBackgroundColor(Color.parseColor("#373737"));
        MediaPlayer somPlin = MediaPlayer.create(getApplicationContext(), R.raw.kalimba);
        somPlin.start();

        btnIniciar.setEnabled(false);
        exercicioAtual.setAlpha(0);
        proximoExercicio.setAlpha(0);
        tempo.setAlpha(0);
        textoProximoExercicio.setText("Treino concluído");
    }

    public void onDestroy (){
        super.onDestroy();
        if(object != null){
            object.stop();
            object.shutdown();
        }
    }
}
