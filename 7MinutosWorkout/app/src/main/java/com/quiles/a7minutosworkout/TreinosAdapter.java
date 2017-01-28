package com.quiles.a7minutosworkout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jefferson on 21/01/2017.
 */

public class TreinosAdapter extends ArrayAdapter {

    List list = new ArrayList();

    //contrutor
    public TreinosAdapter(Context context, int resource) {
        super(context, resource);
    }

    //adiciona os dados a lista
    public void add(Object object){
        super.add(object);
        list.add(object);
    }

    // tamanho da lista
    public int getCount(){
        return this.list.size();
    }

    // pegar item da lista
    public Object getItem (int position){
        return  this.list.get(position);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View row;

        row = convertView;
        DataHandler  handler;

        // popular a lista usando a celula_treino layout, associando o valor dos campos.
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row =   inflater.inflate(R.layout.celula_treino, parent, false);
            handler = new DataHandler();
            handler.icone = (ImageView) row.findViewById(R.id.image_treino);
            handler.inicial = (TextView) row.findViewById(R.id.txt_idTreino);
            handler.treino = (TextView) row.findViewById(R.id.txt_nomeTreino);
            row.setTag(handler);
        } else {
            handler = (DataHandler)row.getTag();
        }

        //passa os valores ao adaptador
        TreinosDataProvider dataProvider;
        dataProvider = (TreinosDataProvider) this.getItem(position);
        handler.icone.setImageResource(dataProvider.getListaIcones());
        handler.treino.setText(dataProvider.getListaTreinos());
        handler.inicial.setText(dataProvider.getListaIniciais());


        return row;
    }

    static class DataHandler{
        ImageView icone;
        TextView treino;
        TextView inicial;
    }

}
