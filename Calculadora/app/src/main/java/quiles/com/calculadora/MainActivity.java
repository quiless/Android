package quiles.com.calculadora;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textResult;
    public float numeroInicial = 0;
    String operacao = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textResult = (TextView) findViewById(R.id.textResultado);
        textResult.setText(null);

    }

    public void clicaBotao(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
                calculaNumero("+");
                break;
            case R.id.btn_division:
                calculaNumero("/");
                break;
            case R.id.btn_mult:
                calculaNumero("*");
                break;
            case R.id.btn_sub:
                calculaNumero("-");
                break;
            case R.id.btn_clear:
                textResult.setText(null);
                numeroInicial = 0;
                operacao = "";
                break;
            case R.id.btn_equal:
                Resultado();
                break;
            default:
                String num;
                num = ((Button) view).getText().toString();
                getKeyboard(num);
                break;
        }
    }


    public void calculaNumero(String tipoOperacao) {
        numeroInicial = Float.parseFloat(textResult.getText().toString());
        operacao = tipoOperacao;
        textResult.setText(null);
    }

    public void getKeyboard(String string){
        String str = textResult.getText().toString();
        str += string;
        textResult.setText(str);
    }

    public void Resultado(){
        float numeroFinal = Float.parseFloat(textResult.getText().toString());
        float result = 0;

        if(operacao.equals("+")){
            result = numeroInicial + numeroFinal;
        }
        if(operacao.equals("-")){
            result = numeroInicial - numeroFinal;
        }
        if(operacao.equals("/")){
            result = numeroInicial / numeroFinal;
        }
        if(operacao.equals("*")){
            result = numeroInicial * numeroFinal;
        }
        textResult.setText(String.valueOf(result));
    }
}