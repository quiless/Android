package quiles.com.agendaandroid;

import java.io.Serializable;

/**
 * Created by Jefferson on 17/01/2017.
 */

public class Contato implements Serializable {

    private String nome;
    private String email;
    private String site;
    private String endereco;
    private String telefone;
    private String foto;

    private Long id;

    @Override
    public String toString() {
        return " ( " + id + " ) " + nome;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }
}
