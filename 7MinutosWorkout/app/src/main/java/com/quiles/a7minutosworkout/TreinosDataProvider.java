package com.quiles.a7minutosworkout;

/**
 * Created by Jefferson on 21/01/2017.
 */

public class TreinosDataProvider {

    private int listaIcones;
    private String listaTreinos;
    private String listaIniciais;

    public TreinosDataProvider(int listaIcones, String listaTreinos, String listaIniciais) {
        this.setListaIcones(listaIcones);
        this.setListaTreinos(listaTreinos);
        this.setListaIniciais(listaIniciais);
    }

    public String getListaIniciais() {
        return listaIniciais;
    }

    public void setListaIniciais(String listaIniciais) {
        this.listaIniciais = listaIniciais;
    }

    public int getListaIcones() {
        return listaIcones;
    }

    public void setListaIcones(int listaIcones) {
        this.listaIcones = listaIcones;
    }

    public String getListaTreinos() {
        return listaTreinos;
    }

    public void setListaTreinos(String listaTreinos) {
        this.listaTreinos = listaTreinos;
    }
}
