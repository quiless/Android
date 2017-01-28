package com.quiles.a7minutosworkout;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class TelaExercicios extends AppCompatActivity {

    ListView listaExercicios;
    String exercicioSelecionado;
    int treinoSelecionado;
    TextToSpeech object;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_exercicios);

        listaExercicios = (ListView) findViewById(R.id.list_exercicio);
        treinoSelecionado = getIntent().getExtras().getInt("numeroTreino");

        String[] listaTreino =  {};

        switch(treinoSelecionado){

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

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_2, android.R.id.text1, listaTreino);

        listaExercicios.setAdapter(adapter);

        listaExercicios.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int itemPosition = position;
                String itemValue = (String) listaExercicios.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), "Item : "+ itemPosition + "Lista :" + itemValue, Toast.LENGTH_SHORT).show();
                exercicioSelecionado = itemValue;
                loadTelaVideos();
            }
        });

        object = new TextToSpeech(TelaExercicios.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS){
                    object.speak("Select your exercise", TextToSpeech.QUEUE_FLUSH, null);
                } else {
                    Toast.makeText(getApplicationContext(), "Não suporta VoiceToSpeech", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void loadTelaVideos(){
        Intent intent = new Intent(getApplicationContext(), TelaVideos.class);
        intent.putExtra("nomeVideo", exercicioSelecionado);
        startActivity(intent);
    }

}
