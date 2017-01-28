package quiles.com.quizzmobile;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private RadioButton radioA;
    private RadioButton radioB;
    private RadioButton radioC;
    private TextView txtTitulo;
    private Button btnEnviar;

    String Perguntas[] = {
            "Primeira pergunta?",
            "Segunda pergunta?",
            "Terceira pergunta",
            "Quarta pergunta",
            "Quinta pergunta"
    };

    String OpcaoA[] = {
            "Resposta A pergunta um",
            "Resposta A pergunta dois",
            "Resposta A pergunta três",
            "Resposta A pergunta quatro",
            "Resposta A pergunta cinco",
    };

    String OpcaoB[] = {
            "Resposta B pergunta um",
            "Resposta B pergunta dois",
            "Resposta B pergunta três",
            "Resposta B pergunta quatro",
            "Resposta B pergunta cinco",
    };

    String OpcaoC[] = {
            "Resposta C pergunta um",
            "Resposta C pergunta dois",
            "Resposta C pergunta três",
            "Resposta C pergunta quatro",
            "Resposta C pergunta cinco",
    };

    int[] respostas = new int[Perguntas.length];
    int[] gabarito = {1, 2, 3, 1, 2};
    int acertos = 0;
    int numPergunta = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioGroup = (RadioGroup) findViewById(R.id.grupoRadio);
        btnEnviar = (Button) findViewById(R.id.btn_send);
        btnEnviar.setEnabled(false);
        txtTitulo = (TextView) findViewById(R.id.txtResultado);
        radioA = (RadioButton) findViewById(R.id.opcaoA);
        radioB = (RadioButton) findViewById(R.id.opcaoB);
        radioC = (RadioButton) findViewById(R.id.opcaoC);

        clicaBotao(btnEnviar);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.opcaoA:
                        Log.d("s", "Opcao um");
                        respostas[numPergunta - 1] = 1;
                        break;
                    case R.id.opcaoB:
                        Log.d("s", "Opcao dois");
                        respostas[numPergunta - 1] = 2;
                        break;
                    case R.id.opcaoC:
                        Log.d("s", "Opcao tres");
                        respostas[numPergunta - 1] = 3;
                        break;
                }
                btnEnviar.setEnabled(true);
            }
        });
    }


    public void clicaBotao(View view) {

        if (numPergunta == Perguntas.length) {
            radioGroup.clearCheck();
            radioA.setEnabled(false);
            radioB.setEnabled(false);
            radioC.setEnabled(false);
            confereResultado();


        } else {
            txtTitulo.setText(Perguntas[numPergunta]);
            radioA.setText(OpcaoA[numPergunta]);
            radioB.setText(OpcaoB[numPergunta]);
            radioC.setText(OpcaoC[numPergunta]);
            numPergunta++;
            radioGroup.clearCheck();
            btnEnviar.setEnabled(false);
        }
    }

    public void confereResultado(){
        int cont = 0;
        for (int numero: respostas) {
            System.out.println(numero);
            if(numero == gabarito[cont]){
                acertos++;
                System.out.println("Resposta correta!");
            } else {
                System.out.println("Resposta incorreta!");
            }
            cont++;
        }
        resultado(btnEnviar);
    }

    public void resultado(View view){
        AlertDialog alertDialog;
        alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Resultado do Quizz");
        alertDialog.setMessage("Você acertou: " + acertos + " questões");
        alertDialog.show();
        btnEnviar.setEnabled(false);
    }
}

