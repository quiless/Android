package quiles.com.agendaandroid;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;
import java.util.jar.Manifest;

public class MainActivity extends AppCompatActivity {

    ListView listaAgenda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FormularioActivity.class);
                startActivity(intent);
            }
        });

        listaAgenda = (ListView) findViewById(R.id.listView);
        registerForContextMenu(listaAgenda);

        listaAgenda.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Contato contato = (Contato) parent.getItemAtPosition(position);
                Intent intent = new Intent(MainActivity.this, FormularioActivity.class);
                intent.putExtra("contatoSelecionado", contato);
                startActivity(intent);
            }
        });

        permissaoSMS();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

        final Contato contato = (Contato) listaAgenda.getAdapter().getItem(info.position);

        final MenuItem ligar = menu.add("Ligar para contato");
        final MenuItem sms = menu.add("Enviar SMS");
        final MenuItem site = menu.add("Visitar site");
        final MenuItem apagar = menu.add("Apagar contato");
        super.onCreateContextMenu(menu, v, menuInfo);

        apagar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(R.drawable.alert)
                        .setTitle("Apagar contato")
                        .setMessage("Deseja realmente apagar o contato?")
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ContatoDAO dao = new ContatoDAO(MainActivity.this);
                                dao.apagarContato(contato);
                                dao.close();
                                carregaLista();
                            }
                        })
                        .setNegativeButton("Não", null).show();
                return false;
            }
        });

        sms.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("sms:" + contato.getTelefone()));

                startActivity(intent);
                return false;
            }
        });

        site.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://" + contato.getSite()));

                startActivity(intent);
                return false;
            }
        });

        ligar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    //verificar se a flag do android está ativada após uma permissão ser negada
                    if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, android.Manifest.permission.CALL_PHONE)) {
                    } else {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.CALL_PHONE}, 0);
                    }
                } else {

                    ligarParaContato(contato);
                }
                return false;
            }
        });


    }


    @Override
    protected void onResume() {
        carregaLista();
        super.onResume();
    }

    private void carregaLista() {

        ContatoDAO dao = new ContatoDAO(this);
        List<Contato> contatos = dao.getLista();
        dao.close();

        AdaptadorContato adaptador = new AdaptadorContato(this, contatos);

        this.listaAgenda.setAdapter(adaptador);
    }

    public void permissaoSMS() {

        //verifica se a permissão é garantida
        if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            //verificar se a flag do android está ativada após uma permissão ser negada
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, android.Manifest.permission.RECEIVE_SMS)) {
            } else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.RECEIVE_SMS}, 0);
            }
        }
    }

    public void ligarParaContato(Contato contato){
        if(contato != null){
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + contato.getTelefone()));
        }
    }
}