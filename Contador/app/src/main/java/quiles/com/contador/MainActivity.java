package quiles.com.contador;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int numeroHomem = 0;
    int numeroMulher = 0;
    int numeroPessoa = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textoPessoa = (TextView) findViewById(R.id.textViewNumeroPessoas);
        Button btnReset = (Button) findViewById(R.id.btn_reset);
        final Button btnHomem = (Button) findViewById(R.id.btn_homem);
        final Button btnMulher = (Button) findViewById(R.id.btn_mulher);

        btnHomem.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                numeroHomem ++;
                numeroPessoa++;

                String mensagem = Integer.toString(numeroPessoa);
                textoPessoa.setText("Total de pessoas: " + mensagem);
                btnHomem.setText(Integer.toString(numeroHomem));

            }
        });

        btnMulher.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
                numeroMulher++;
                numeroPessoa++;

                String mensagem = Integer.toString(numeroPessoa);
                textoPessoa.setText("Total de pessoas: " + mensagem);
                btnMulher.setText(Integer.toString(numeroMulher));
            }
        });

        btnReset.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
                numeroMulher=0;
                numeroHomem=0;
                numeroPessoa=0;

                String mensagem = Integer.toString(numeroPessoa);
                textoPessoa.setText("Total de pessoas: " + mensagem);
                btnMulher.setText(Integer.toString(numeroMulher));
                btnHomem.setText(Integer.toString(numeroHomem));
            }
        });

    }

}