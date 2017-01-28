package com.quiles.workout;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    String nome;
    String email;
    String id;
    Boolean valor;
    Boolean firebase;

    private FirebaseDatabase database;
    private DatabaseReference myRef;


    AccessToken accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        valor = intent.getBooleanExtra("valor",true);

        //Intent intent2 = getIntent();
        //firebase = intent.getBooleanExtra("firease", false);

    }

    public void sair(View view) {
        LoginManager.getInstance().logOut();
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();


        if(valor) {
            Log.d("Logx", "testanduuu");
            accessToken = AccessToken.getCurrentAccessToken();
            if (accessToken == null) {
                finish();
            }
            //graphRequest tras os dados do usuario logado e devolve em um JSON
            GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject object, GraphResponse response) {
                    try {
                        nome = object.getString("name");
                        email = object.getString("email");
                        id = object.getString("id");

                        database = FirebaseDatabase.getInstance();
                        myRef = database.getReference();

                        Map<String, Object> infos = new HashMap<>();
                        infos.put("id", id);
                        infos.put("nome", nome);
                        infos.put("email", email);
                        myRef.child(id).updateChildren(infos);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,email");
            request.setParameters(parameters);
            request.executeAsync();

        }

    }
}