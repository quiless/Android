package quiles.com.listaexemplo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ListView minhaLista;

    private String [] listaNomes = {
            "Homer",
            "Bart",
            "Marggie",
            "Lisa",
            "Mag",
            "Burns",
            "Krusty",
            "Barney",
            "Ned",
            "Milhouse"
    };

    private String [] listaDescricao = {
            "Oi, eu sou Homer",
            "Oi, eu sou Bart",
            "Oi, eu sou Marggie",
            "Oi, eu sou Lisa",
            "Oi, eu sou Mag",
            "Oi, eu sou Burns",
            "Oi, eu sou Krusty",
            "Oi, eu sou Barney",
            "Oi, eu sou Ned",
            "Oi, eu sou Milhouse"
    };

    int[] listaImagens = {
            R.drawable.simpsons1,
            R.drawable.simpsons2,
            R.drawable.simpsons3,
            R.drawable.simpsons4,
            R.drawable.simpsons5,
            R.drawable.simpsons6,
            R.drawable.simpsons7,
            R.drawable.simpsons8,
            R.drawable.simpsons9,
            R.drawable.simpsons10,
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        minhaLista = (ListView) findViewById(R.id.lista);

        ArrayAdapter<String> Adaptador = new ArrayAdapter<String>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                listaNomes
        );

        //minhaLista.setAdapter(Adaptador);

        minhaLista.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            //position devolverá a posição que será clicada dentro do array
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String nomePersonagem = listaNomes[position];
                Toast.makeText(getApplicationContext(), nomePersonagem, Toast.LENGTH_SHORT).show();
            }
        });

        Adaptador adaptador;

        int i = 0;

        adaptador = new Adaptador(
                getApplicationContext(),
                R.layout.celula
        );

        for(String nome:listaNomes){
            DataProvider dataProvider = new DataProvider(listaImagens[i], nome, listaDescricao[i]);
            adaptador.add(dataProvider);
            i++;
        }

        minhaLista.setAdapter(adaptador);

    }
}
