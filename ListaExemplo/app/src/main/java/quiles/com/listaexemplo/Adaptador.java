package quiles.com.listaexemplo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Jefferson on 14/01/2017.
 */

public class Adaptador extends ArrayAdapter {

    public Adaptador(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public void add(Object object) {
        super.add(object);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row;

        row = convertView;
        DataHandler handler;

        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.celula,parent, false);
            handler = new DataHandler();
            handler.imageView = (ImageView) row.findViewById(R.id.imageView);
            handler.textName = (TextView) row.findViewById(R.id.textName);
            handler.textDescription = (TextView) row.findViewById(R.id.textDescription);
            row.setTag(handler);
        } else {
            handler = (DataHandler)row.getTag();
        }

        DataProvider dataProvider;
        dataProvider = (DataProvider) this.getItem(position);
        handler.imageView.setImageResource(dataProvider.getIcone());
        handler.textName.setText(dataProvider.getNome());
        handler.textDescription.setText(dataProvider.getDescricao());

        return row;

    }

    private class DataHandler{

        ImageView imageView;
        TextView textName;
        TextView textDescription;
    }
}
