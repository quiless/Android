package com.quiles.workout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private FirebaseAuth minhaAuth;
    private FirebaseAuth.AuthStateListener minhaAuthListener;

    EditText senha;
    EditText email;
    Boolean valor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        minhaAuth = FirebaseAuth.getInstance();

        email = (EditText) findViewById(R.id.txt_emailLogin);
        senha = (EditText) findViewById(R.id.txt_senhaLogin);

        valor = false;

        minhaAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d("Logx", "Usuário conectado" + user.getUid());
                } else {
                    Log.d("Logx", "Sem usuário conectados");
                }
            }
        };
    }

    public void validacao(View view) {
        if (email.getText().toString().length() == 0) {
            email.setError("Necessário preencher o email");
        } else if (senha.getText().toString().length() == 0) {
            senha.setError("Necessário preencher a senha");
        } else {
            clicaLogin();
        }
    }

    public void clicaLogin() {
        minhaAuth.signInWithEmailAndPassword(email.getText().toString(), senha.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            AlertDialog.Builder builder2 = new AlertDialog.Builder(Login.this);
                            builder2.setTitle("LOGIN");
                            builder2.setMessage("Email e/ou Senha não conferem!");
                            builder2.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Log.d("Logx", "Falha na autenticação");
                                }
                            });
                            builder2.show();
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);

                            builder.setTitle("LOGIN");
                            builder.setMessage("Login realizado com sucesso!");
                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Log.d("Logx", "Sucessuuuu");
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    intent.putExtra("valor", valor);
                                    startActivity(intent);
                                }
                            });
                            builder.show();
                        }
                    }
                });
    }

    public void sair(View view){
        //comando manda a view voltar para seu pai
        NavUtils.navigateUpFromSameTask(Login.this);
        Log.d("Logx", "clicou no cancelar");
    }
}
