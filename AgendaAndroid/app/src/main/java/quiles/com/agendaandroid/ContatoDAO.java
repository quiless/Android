package quiles.com.agendaandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jefferson on 18/01/2017.
 */

public class ContatoDAO extends SQLiteOpenHelper {

    private static final int VERSAO = 1;
    private static final String TABELA = "Contatos";
    private static final String DATABASE = "MinhaAgenda";

    public ContatoDAO(Context context) {
        super(context, DATABASE, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String ddl = "CREATE TABLE " + TABELA
                + "(id INTEGER PRIMARY KEY,"
                + "nome TEXT NOT NULL,"
                + "email TEXT,"
                + "site TEXT,"
                + "telefone TEXT,"
                + "endereco TEXT,"
                + "foto TEXT);";

        db.execSQL(ddl);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if(oldVersion == 1){
            String sql = "ALTER TABLE" + TABELA + "ADD COLUMN foto TEXT;";
            db.execSQL(sql);
        }
    }

    public void inserirContato (Contato contato){

        ContentValues values = new ContentValues();

        values.put("nome", contato.getNome());
        values.put("email", contato.getEmail());
        values.put("site", contato.getSite());
        values.put("telefone", contato.getTelefone());
        values.put("endereco", contato.getEndereco());
        values.put("foto", contato.getFoto());


        getWritableDatabase().insert(TABELA, null, values);
    }

    public void apagarContato (Contato contato){

        SQLiteDatabase db = getWritableDatabase();

        String[] argumentos = {contato.getId().toString()};
        db.delete("contatos", "id=?", argumentos);

    }

    public void alterarContato (Contato contato){

        ContentValues values = new ContentValues();
        values.put("nome", contato.getNome());
        values.put("email", contato.getEmail());
        values.put("site", contato.getSite());
        values.put("endereco", contato.getEndereco());
        values.put("telefone", contato.getTelefone());
        values.put("foto", contato.getFoto());

        String[] idAlterar =  {contato.getId().toString()};
        getWritableDatabase().update(TABELA, values, "id=?", idAlterar);

    }

    public boolean isContato (String telefone){

        String[] parametros = {telefone};
        Cursor cursor = getReadableDatabase().rawQuery("SELECT telefone FROM " + TABELA + " WHERE telefone=?", parametros);
        int total = cursor.getCount();
        return total > 0;
    }

    public List<Contato> getLista(){

        List<Contato> contatos = new ArrayList<Contato>();
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM " + TABELA +";", null);

        while (cursor.moveToNext()){
            Contato contato = new Contato();
            contato.setId(cursor.getLong(cursor.getColumnIndex("id")));
            contato.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            contato.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            contato.setSite(cursor.getString(cursor.getColumnIndex("site")));
            contato.setTelefone(cursor.getString(cursor.getColumnIndex("telefone")));
            contato.setEndereco(cursor.getString(cursor.getColumnIndex("endereco")));
            contato.setFoto(cursor.getString(cursor.getColumnIndex("foto")));
            contatos.add(contato);
        }

        cursor.close();
        return contatos;
    }
}
