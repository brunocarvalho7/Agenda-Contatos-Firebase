package br.ufc.quixada.dadm.variastelas.dao.impl;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.ufc.quixada.dadm.variastelas.dao.ContactDao;
import br.ufc.quixada.dadm.variastelas.transactions.Contato;

public class ContactDaoImpl implements ContactDao {

    private static final String REFERENCE_CONTATO = "contatos";

    private DatabaseReference mDatabase;
    private List<Contato> contatos;
    private DataStatus dataStatus;

    public ContactDaoImpl(final DataStatus dataStatus){
        contatos = new ArrayList<>();
        this.dataStatus = dataStatus;

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> ids = new ArrayList<>();
                contatos.clear();
                for(DataSnapshot contato: dataSnapshot.child(REFERENCE_CONTATO).getChildren()){
                    Contato c = contato.getValue(Contato.class);

                    contatos.add(c);
                    ids.add(c.getId());
                }
                dataStatus.DataIsLoaded(ids);

                Log.i("ContactDaoImpl", "Carregou a lista de contatos: " + contatos.toString() );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("ContactDaoImpl", "onCancelled: " + databaseError.toString() );
            }
        });
    }

    @Override
    public void save(Contato contato) {
        contato.setId(this.getNewId());

        mDatabase
                .child(REFERENCE_CONTATO)
                .child(contato.getId())
                .setValue(contato);
    }

    @Override
    public List<Contato> findAll() {
        return contatos;
    }

    @Override
    public void update(Contato contato) {
        mDatabase
                .child(REFERENCE_CONTATO)
                .child(contato.getId())
                .setValue(contato);
    }

    @Override
    public void remove(Contato contato) {
        mDatabase
                .child(REFERENCE_CONTATO)
                .child(contato.getId())
                .removeValue();
    }

    @Override
    public String getNewId() {
        return mDatabase
                .child(REFERENCE_CONTATO)
                .push()
                .getKey();
    }

    public interface DataStatus{
        void DataIsLoaded(List<String> ids);
    }
}
