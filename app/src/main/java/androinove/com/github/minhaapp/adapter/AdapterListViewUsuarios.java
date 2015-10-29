package androinove.com.github.minhaapp.adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androinove.com.github.minhaapp.R;
import androinove.com.github.minhaapp.entity.Usuario;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by PedroFelipe on 28/10/2015.
 */
public class AdapterListViewUsuarios extends BaseAdapter {

    private Usuario[] mUsuarios;
    private LayoutInflater mLayoutInflater;

    public AdapterListViewUsuarios(Context context, Usuario[] usuarios) {
        this.mUsuarios = usuarios;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return this.mUsuarios.length;
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public Object getItem(int position) {
        return this.mUsuarios[position];
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = this.mLayoutInflater.inflate(R.layout.list_view_usuarios, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }

        viewHolder.txtNome.setText(this.mUsuarios[position].getNome());
        viewHolder.txtEmail.setText(this.mUsuarios[position].getEmail());
        viewHolder.txtTelefone.setText(this.mUsuarios[position].getTelefone());

        return convertView;
    }

    public class ViewHolder {
        @Bind(R.id.txt_nome) TextView txtNome;
        @Bind(R.id.txt_email) TextView txtEmail;
        @Bind(R.id.txt_telefone) TextView txtTelefone;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
