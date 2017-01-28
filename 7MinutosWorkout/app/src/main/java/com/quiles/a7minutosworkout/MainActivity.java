package com.quiles.a7minutosworkout;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.StringDef;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int treinoSelecionado; /* variavel para identificar qual treino foi selecional */
    ImageView imagemTreinos;
    TextToSpeech object;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView listView;
        // as listas receberão os valores das strings.xml
        String[] lista_nomes_treinos;
        String[] inicial_treinos;
        TreinosAdapter adapter;

        int[] lista_de_icones = {
                R.drawable.star,
                R.drawable.star,
                R.drawable.star,
                R.drawable.star,
                R.drawable.star,
                R.drawable.star,
                R.drawable.star,
                R.drawable.star,
                R.drawable.star,
                R.drawable.star,
                R.drawable.star
        };

        //lista do content_main
        listView = (ListView) findViewById(R.id.list_treino);
        imagemTreinos = (ImageView)findViewById(R.id.image_frango);
        imagemTreinos.animate().alpha(1f).setDuration(2000);
        lista_nomes_treinos = getResources().getStringArray(R.array.lista_treinos);
        inicial_treinos = getResources().getStringArray(R.array.inicial_treinos);

        // Popular a lista

        int i = 0;
        adapter = new TreinosAdapter(getApplicationContext(), R.layout.celula_treino);
        listView.setAdapter(adapter);

        for(String titles: lista_nomes_treinos){

            TreinosDataProvider dataProvider = new TreinosDataProvider(lista_de_icones[i], titles, inicial_treinos[i]);
            adapter.add(dataProvider);
            i++;
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                treinoSelecionado = position;
                Toast.makeText(getApplicationContext(), "Hello " + String.valueOf(position), Toast.LENGTH_SHORT).show();
                loadTelaExercicios();
            }
        });

        object = new TextToSpeech(MainActivity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS){
                    object.speak("Select your trainning", TextToSpeech.QUEUE_FLUSH, null);
                } else {
                    Toast.makeText(getApplicationContext(), "Não suporta VoiceToSpeech", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void loadTelaExercicios(){
        Intent intent = new Intent(getApplicationContext(), TelaExercicios.class);
        intent.putExtra("numeroTreino", treinoSelecionado);
        startActivity(intent);
    }
}
