package br.ufc.quixada.dadm.variastelas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.ufc.quixada.dadm.variastelas.dao.ContactDao;
import br.ufc.quixada.dadm.variastelas.dao.impl.ContactDaoImpl;
import br.ufc.quixada.dadm.variastelas.transactions.Constants;
import br.ufc.quixada.dadm.variastelas.transactions.Contato;

public class MainActivity extends AppCompatActivity implements ContactDaoImpl.DataStatus{

    private ContactDao contactDao;

    int selected;
    List<Contato> listaContatos;
    ExpandableListAdapter adapter;
    ExpandableListView listViewContatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        contactDao = new ContactDaoImpl(this);

        selected = -1;
        listaContatos = contactDao.findAll();

        adapter = new ExpandableListAdapter( this, listaContatos );

        listViewContatos = ( ExpandableListView ) findViewById( R.id.expandableListView );
        listViewContatos.setAdapter( adapter );
        listViewContatos.setSelector( android.R.color.holo_blue_light );

        listViewContatos.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener()
        {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id)
            {
                selected = groupPosition;
                return false;
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate( R.menu.menu_main_activity, menu );
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected( MenuItem item ) {
        switch(item.getItemId())
        {
            case R.id.add:
                clicarAdicionar();
                break;
            case R.id.edit:
                clicarEditar();
                break;
            case R.id.delete:
                apagarItemLista();
                break;
            case R.id.settings:
                break;
            case R.id.about:
                break;
        }
        return true;
    }

    private void apagarItemLista(){
        if( listaContatos.size() > 0 && selected > -1){
            Contato c = (Contato) listViewContatos.getAdapter().getItem(selected);
            contactDao.remove(c);
        } else {
            selected = -1;
        }
    }

    public void clicarAdicionar(){
        Intent intent = new Intent( this, ContactActivity.class );
        startActivityForResult( intent, Constants.REQUEST_ADD );
    }

    public void clicarEditar(){
        if(selected > -1){
            Intent intent = new Intent( this, ContactActivity.class );

            Contato contato = listaContatos.get( selected );

            intent.putExtra( "id", contato.getId() );
            intent.putExtra( "nome", contato.getNome() );
            intent.putExtra( "telefone", contato.getTelefone() );
            intent.putExtra( "endereco", contato.getEndereco() );

            startActivityForResult( intent, Constants.REQUEST_EDIT );
        }
    }


    @Override
    protected void onActivityResult( int requestCode, int resultCode, @Nullable Intent data ) {
        super.onActivityResult(requestCode, resultCode, data);

      if( requestCode == Constants.REQUEST_ADD && resultCode == Constants.RESULT_ADD ){

          String nome = ( String )data.getExtras().get( "nome" );
          String telefone = ( String )data.getExtras().get( "telefone" );
          String endereco = ( String )data.getExtras().get( "endereco" );

          Contato contato = new Contato( nome, telefone, endereco );

          contactDao.save(contato);

      } else if( requestCode == Constants.REQUEST_EDIT && resultCode == Constants.RESULT_ADD ){

          String nome = ( String )data.getExtras().get( "nome" );
          String telefone = ( String )data.getExtras().get( "telefone" );
          String endereco = ( String )data.getExtras().get( "endereco" );
          String idEditar = ( String) data.getExtras().get( "id" );

          Contato c = new Contato( idEditar, nome, telefone, endereco );
          contactDao.update(c);

      } //Retorno da tela de contatos com um conteudo para ser adicionado
        //Na segunda tela, o usuario clicou no botão ADD
      else if( resultCode == Constants.RESULT_CANCEL ){
            Toast.makeText( this,"Cancelado",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void DataIsLoaded(List<String> ids) {
        adapter.setIds(ids);
        adapter.notifyDataSetChanged();
    }
}
