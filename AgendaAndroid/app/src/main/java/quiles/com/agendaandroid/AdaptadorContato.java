package quiles.com.agendaandroid;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AdaptadorContato extends BaseAdapter {

    private final List<Contato> contato;
    private final Activity activity;

    public AdaptadorContato(Activity activity, List<Contato> contato) {
        this.activity = activity;
        this.contato = contato;
    }

    @Override
    public int getCount() {
        return this.contato.size();
    }

    @Override
    public Object getItem(int position) {
        return this.contato.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.contato.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View linha = convertView;
        Contato contatos = contato.get(position);
        Bitmap bitmap;

        if (linha == null) {
            linha = this.activity.getLayoutInflater().inflate(R.layout.contato_agenda, parent, false);
        }

        TextView nome = (TextView) linha.findViewById(R.id.txt_nome_agenda);
        TextView telefone = (TextView) linha.findViewById(R.id.txt_telefone_agenda);
        TextView endereco = (TextView) linha.findViewById(R.id.txt_endereco_agenda);
        TextView site = (TextView) linha.findViewById(R.id.txt_site_agenda);
        TextView email = (TextView) linha.findViewById(R.id.txt_email_agenda);
        ImageView foto = (ImageView) linha.findViewById(R.id.image_foto_agenda);

        nome.setText(contatos.getNome());

        if (email != null) {
            email.setText(contatos.getEmail());
        }
        if (telefone != null) {
            telefone.setText(contatos.getTelefone());
        }
        if (endereco != null) {
            endereco.setText(contatos.getEndereco());
        }
        if (site != null) {
            site.setText(contatos.getSite());
        }

        if (contatos.getFoto() != null) {
            bitmap = BitmapFactory.decodeFile(contatos.getFoto());
        } else {
            bitmap = BitmapFactory.decodeResource(activity.getResources(), R.drawable.foto);
        }

        // redimensionar um bitmap

        bitmap = Bitmap.createScaledBitmap(bitmap, 110,110, true);
        foto.setImageBitmap(bitmap);

        return linha;

    }
}