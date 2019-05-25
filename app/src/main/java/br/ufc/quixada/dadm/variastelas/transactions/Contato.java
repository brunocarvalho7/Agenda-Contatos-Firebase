package br.ufc.quixada.dadm.variastelas.transactions;

import android.support.annotation.Nullable;

public class Contato {

    private String id;
    private String nome;
    private String telefone;
    private String endereco;

    public Contato(){

    }

    public Contato( String nome, String telefone, String endereco) {
        this.nome = nome;
        this.telefone = telefone;
        this.endereco = endereco;
    }

    public Contato( String id, String nome, String telefone, String endereco) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.endereco = endereco;
    }

    public String getId() {
        return id;
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

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    @Override
    public String toString() {
        return nome;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return this.id == ((Contato) obj).getId();
    }
}
