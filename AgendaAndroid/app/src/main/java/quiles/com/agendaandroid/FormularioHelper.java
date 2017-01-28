package quiles.com.agendaandroid;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * Created by Jefferson on 17/01/2017.
 */

public class FormularioHelper {

    private Contato contato;

    private EditText nome;
    private EditText email;
    private EditText site;
    private EditText telefone;
    private EditText endereco;
    private ImageView foto;
    private Button addFoto;

    public FormularioHelper(FormularioActivity activity) {

        this.contato = new Contato();
        this.nome = (EditText) activity.findViewById(R.id.txtNomeFormulario);;
        this.email = (EditText) activity.findViewById(R.id.txtEmailFormulario);
        this.site = (EditText) activity.findViewById(R.id.txtSiteFormulario);
        this.endereco = (EditText) activity.findViewById(R.id.txtEndereçoFormulario);
        this.telefone = (EditText) activity.findViewById(R.id.txtTelefoneFormulario);
        this.foto = (ImageView) activity.findViewById(R.id.imageFotoFormulario);
        this.addFoto = (Button) activity.findViewById(R.id.btn_photo);
    }

    public Button getAddFoto() {

        return addFoto;
    }

    public Contato recebeFormulario(){

        contato.setNome(nome.getText().toString());
        contato.setEmail(email.getText().toString());
        contato.setSite(site.getText().toString());
        contato.setTelefone(telefone.getText().toString());
        contato.setEndereco(endereco.getText().toString());
        contato.setFoto((String) foto.getTag());
        return contato;
    }

    public void  colocaFormulario(Contato contato){

        nome.setText(contato.getNome());
        email.setText(contato.getEmail());
        telefone.setText(contato.getTelefone());
        site.setText(contato.getSite());
        endereco.setText(contato.getEndereco());
        foto.setTag(contato.getFoto());
        carregaImagem(contato.getFoto());
    }

    public void carregaImagem(String localFoto){
        if(localFoto != null){
            Bitmap imagemFoto = BitmapFactory.decodeFile(localFoto);
            foto.setImageBitmap(imagemFoto);
            foto.setTag(imagemFoto);
        }
    }
}

