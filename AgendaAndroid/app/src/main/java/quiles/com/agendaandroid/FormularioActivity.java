package quiles.com.agendaandroid;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.InputStream;


public class FormularioActivity extends AppCompatActivity {

    private FormularioHelper helper;
    private Contato contato;
    private String localFoto;
    private static final int TIRA_FOTO = 123;
    private boolean fotoResource = false;
    private Bitmap bitmap;
    private ImageView imagemContato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.helper = new FormularioHelper(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imagemContato = (ImageView) findViewById(R.id.imageFotoFormulario);

        this.helper = new FormularioHelper(this);
        final Button camera = helper.getAddFoto();

        Intent intent = this.getIntent();
        this.contato = (Contato) intent.getSerializableExtra("contatoSelecionado");
        if (this.contato != null) {
            this.helper.colocaFormulario(this.contato);
        }

        if (toolbar != null) {
            toolbar.setTitle("Editar contato");
            toolbar.setNavigationIcon(R.drawable.voltar);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NavUtils.navigateUpFromSameTask(FormularioActivity.this);
                }
            });
        }
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertaSourceImagem();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuform, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.menu_form:
                Contato contato = helper.recebeFormulario();
                ContatoDAO dao = new ContatoDAO(FormularioActivity.this);

                if(contato.getId() == null){
                    dao.inserirContato(contato);
                } else {
                    dao.alterarContato(contato);
                }
                dao.close();
                finish();
                return false;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void carregaFotoCamera(){

        fotoResource = true;
        localFoto = getExternalFilesDir(null) + "/"+ System.currentTimeMillis()+".jpg";

        Intent irParaCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        irParaCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(localFoto)));
        startActivityForResult(irParaCamera, 123);
    }

    public void carregaFotoBiblioteca(){

        fotoResource = false;
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
    }

    @Override
    // função para carregar a foto no caminho que salvei na função carrega foto
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (!fotoResource) {
            if (resultCode == RESULT_OK
                    && null != data) {

                Uri imagemSel = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                Cursor cursor = getContentResolver().query(imagemSel,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String caminhoFoto = cursor.getString(columnIndex);
                cursor.close();

                helper.carregaImagem(caminhoFoto);
            }
            }else{
            if (requestCode == TIRA_FOTO) {
                if(resultCode == Activity.RESULT_OK) {
                    helper.carregaImagem(this.localFoto);
                } else {
                    this.localFoto = null;
                }
            }
        }
    }

    public  void alertaSourceImagem(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.app_name).setMessage("Selecione a fonte da Imagem:");
        builder.setPositiveButton("Camera", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                carregaFotoCamera();
            }
        });
        builder.setNegativeButton("Biblioteca", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                carregaFotoBiblioteca();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}

