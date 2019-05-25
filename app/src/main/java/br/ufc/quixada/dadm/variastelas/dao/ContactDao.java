package br.ufc.quixada.dadm.variastelas.dao;

import java.util.List;

import br.ufc.quixada.dadm.variastelas.transactions.Contato;

public interface ContactDao {
    void save(Contato contato);
    List<Contato> findAll();
    void update(Contato contato);
    void remove(Contato contato);
    String getNewId();
}
