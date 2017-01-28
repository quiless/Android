package com.quiles.workout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

public class TelaPrincipal extends AppCompatActivity {

    LoginButton loginButton;
    CallbackManager callbackManager;
    Boolean valor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_telaprincipal);

        loginButton = (LoginButton) findViewById(R.id.btn_loginFacebook);

        valor = true;

        //seta as permissões que o login poderá obter do perfil
        loginButton.setReadPermissions(Arrays.asList("public_profile, email, user_friends"));
        //objeto que verifica se o botão obteve login ou não
        callbackManager = CallbackManager.Factory.create();

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("valor", valor);
                startActivity(intent);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    //quando clica no botão, chama outra atividade e o metodo é retornado na funcao abaixo
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // access token verifica se o usuario está logado
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if(accessToken != null){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    }

    public void criarConta(View view){
        Log.d("Logx", "Clicou no criar conta");
        Intent intent = new Intent(getApplicationContext(), CriarConta.class);
        startActivity(intent);
    }

    public void login(View view){
        Log.d("Logx", "Clicou no login");
        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
    }

}
