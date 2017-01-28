package quiles.com.listaexemplo;

/**
 * Created by Jefferson on 14/01/2017.
 */

public class DataProvider {

    private int icone;
    private String nome;
    private String descricao;

    public DataProvider(int icone, String nome, String descricao) {
        this.icone = icone;
        this.nome = nome;
        this.descricao = descricao;
    }

    public int getIcone() {
        return icone;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }
}
