package quiles.com.twitter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth minhaAuth;
    private FirebaseAuth.AuthStateListener minhaAuthListener;

    EditText nome;
    EditText senha;
    EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        minhaAuth = FirebaseAuth.getInstance();

        nome = (EditText) findViewById(R.id.txt_nome);
        senha = (EditText) findViewById(R.id.txt_senha);
        email = (EditText) findViewById(R.id.txt_email);

        minhaAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d("Logx", "Usuário conectado" + user.getUid());
                    Intent intent = new Intent(getApplicationContext(), SegundaTela.class);
                    startActivity(intent);
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

    @Override
    protected void onStop() {
        super.onStop();
        minhaAuth.removeAuthStateListener(minhaAuthListener);
    }

    public void clicaLogin(View view) {
        minhaAuth.signInWithEmailAndPassword(email.getText().toString(), senha.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Log.d("Logx", "Falha na autenticação");
                        }
                    }
                });
    }

    public void clicaCriarUsuario(View view) {
        minhaAuth.createUserWithEmailAndPassword(email.getText().toString(), senha.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Log.d("Logx", "Falha na criação da conta");
                        } else {
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference reference = database.getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                            reference.child("nome").setValue(nome.getText().toString());
                            reference.child("uid").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
                        }
                    }
                });
    }

}