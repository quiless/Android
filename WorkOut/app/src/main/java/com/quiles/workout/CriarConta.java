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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class CriarConta extends AppCompatActivity {

    private FirebaseAuth minhaAuth;
    private FirebaseAuth.AuthStateListener minhaAuthListener;

    private FirebaseDatabase database;
    private DatabaseReference myRef;

    EditText email;
    EditText senha;
    EditText confirmarSenha;
    EditText name;

    Boolean valor;

    String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_conta);

        minhaAuth = FirebaseAuth.getInstance();

        name = (EditText) findViewById(R.id.txt_nomeCriarConta);
        email = (EditText) findViewById(R.id.txt_emailCriarConta);
        senha = (EditText) findViewById(R.id.txt_senhaCriarConta);
        confirmarSenha = (EditText) findViewById(R.id.txt_confirmarSenhaCriarConta);

        valor = false;

        minhaAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d("Logx", "Usuário conectado" + user.getUid());
                    //Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    // startActivity(intent);
                } else {
                    Log.d("Logx", "Sem usuário conectados");
                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        minhaAuth.addAuthStateListener(minhaAuthListener);
    }

    public void sair(View view) {
        //comando manda a view voltar para seu pai
        NavUtils.navigateUpFromSameTask(CriarConta.this);
        Log.d("Logx", "clicou no cancelar");
        minhaAuth.removeAuthStateListener(minhaAuthListener);
    }

    public void validacao(View view) {
        if (name.getText().toString().length() == 0) {
            name.setError("Necessário preencher o nome");
        } else if (email.getText().toString().length() == 0) {
            email.setError("Necessário preencher o nome");
        } else if (senha.getText().toString().length() == 0 || senha.getText().toString().length() < 6){
            senha.setError("Necessário preencher a senha com mais de cinco caracteres");
        } else if (!senha.getText().toString().equals(confirmarSenha.getText().toString())){
            senha.setError("Os campos senha e confirmar senha não conhecidem");
            confirmarSenha.setError("Os campos senha e confirmar senha não conhecidem");
        } else {
            criarConta();
        }
    }


    public void criarConta() {
        minhaAuth.createUserWithEmailAndPassword(email.getText().toString(), senha.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Log.d("Logx", "Falha na criação da conta");
                            AlertDialog.Builder builder2 = new AlertDialog.Builder(CriarConta.this);
                            builder2.setTitle("LOGIN");
                            builder2.setMessage("Email e/ou Senha não conferem!");
                            builder2.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Log.d("Logx", "Falha na autenticação");
                                    //Intent intent = new Intent(getApplicationContext(), SegundaTela.class);
                                    //startActivity(intent);
                                }
                            });
                            builder2.show();
                        } else {

                            database = FirebaseDatabase.getInstance();
                            myRef = database.getReference();
                            uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                            myRef = database.getReference(uid).child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                            myRef.child("nome").setValue(name.getText().toString());
                            myRef.child("email").setValue(email.getText().toString());
                            myRef.child("uid").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());

                            AlertDialog.Builder builder2 = new AlertDialog.Builder(CriarConta.this);
                            builder2.setTitle("CRIAR CONTA");
                            builder2.setMessage("Sua conta foi criada com sucesso");
                            builder2.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Log.d("Logx", "Falha na autenticação");
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    intent.putExtra("valor", valor);
                                    startActivity(intent);
                                }
                            });
                            builder2.show();

                        }
                    }
                });
    }

    @Override
    protected void onStop() {
        super.onStop();
        minhaAuth.removeAuthStateListener(minhaAuthListener);
    }
}