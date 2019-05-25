package br.ufc.quixada.dadm.variastelas;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.ufc.quixada.dadm.variastelas.transactions.Contato;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<Contato> agenda;
    private List<String> ids;


    public ExpandableListAdapter(Context context, List<Contato> agenda ) {
        this.context = context;
        this.agenda = agenda;
    }

    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        Contato contato = ( Contato ) getGroup(groupPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate( R.layout.list_group, null );
        }

        ImageView imageViewGroup = ( ImageView )convertView.findViewById( R.id.imageViewGroup );

        if( groupPosition % 2 == 0 ){
            imageViewGroup.setImageResource( R.drawable.ic_action_check );
        } else{
            imageViewGroup.setImageResource( R.drawable.ic_action_deletegroup );
        }


        TextView txViewNomeContato = ( TextView )convertView.findViewById( R.id.listHeader );
        txViewNomeContato.setTypeface(null, Typeface.BOLD);
        txViewNomeContato.setText( contato.getNome() );

        return convertView;

    }

    public Object getGroup(int groupPosition) {
        return getObjectFromIndexId(groupPosition);
    }

    @Override
    public Object getChild( int groupPosition, int childPosition ) {
        return getObjectFromIndexId(groupPosition);
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        Contato contato = ( Contato ) getChild( groupPosition, childPosition );

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate( R.layout.rowlayout, null );
        }

        TextView txViewContentTelefone = convertView.findViewById( R.id.textViewContentTelefone );
        TextView txViewContentEndereco = convertView.findViewById( R.id.textViewContentEndereco );

        txViewContentEndereco.setText( contato.getEndereco() );
        txViewContentTelefone.setText( contato.getTelefone() );

        return convertView;

    }

    @Override
    public long getGroupId( int groupPosition ) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return groupPosition;
    }

    public int getGroupCount() {
        return agenda.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public Object getObjectFromIndexId(int indexId){
        String id = this.ids.get(indexId);

        for(Contato c : agenda){
            if(c.getId().equals(id))
                return c;
        }
        return null;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }
}
