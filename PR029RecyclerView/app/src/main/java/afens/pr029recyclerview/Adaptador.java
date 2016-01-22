package afens.pr029recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Usuario on 14/01/2016.
 */
public class Adaptador extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final ArrayList<ListItem> mDatos;
    private View emptyView;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public interface OnItemClickListener {
        void onItemClick(View view, Datos datos, int position);
    }
    // Constructor. Recibe contexto y datos.
    public Adaptador(ArrayList<ListItem> datos) {
        mDatos = datos;
    }

    @Override
    public int getItemViewType(int position) {
        return mDatos.get(position).getType();
    }

    // Retorna el número de ítems de datos.
    @Override
    public int getItemCount() {
        return mDatos.size();
    }

    // Comprueba si la lista está vacía.
    private void checkIfEmpty() {
        if (emptyView != null) {
            // Muestra u oculta la empty view dependiendo de si la lista está vacía o no.
            emptyView.setVisibility(getItemCount() > 0 ? View.GONE : View.VISIBLE);
        }
    }

    // Establece la empty view para la lista.
    public void setEmptyView(View emptyView) {
        this.emptyView = emptyView;
        // Muestra la empty view si  la lista está vacía.
        checkIfEmpty();
    }
    // Elimina un elemento de la lista.
    public void removeItem(int position) {
        mDatos.remove(position);
        notifyItemRemoved(position);
        // Si la lista ha quedado vacía se muestra la empty view.
        checkIfEmpty();
    }

    // Añade un elemento a la lista.
    public void addItem(Datos datos) {
        // Se añade el elemento.
        mDatos.add(datos);
        // Se notifica que se ha insertado un elemento en la última posición.
        notifyItemInserted(mDatos.size() - 1);
        // Si la lista estaba vacía se oculta la empty view.
        checkIfEmpty();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ListItem.TYPE_DATA) {
            return onCreateDatosViewHolder(parent);
        } else {
            return onCreateTituloViewHolder(parent);
        }
    }

    private RecyclerView.ViewHolder onCreateTituloViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_main_grupo, parent, false);
        final RecyclerView.ViewHolder viewHolder = new TituloViewHolder(itemView);
        return viewHolder;
    }

    public RecyclerView.ViewHolder onCreateDatosViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_main_item, parent, false);
        final RecyclerView.ViewHolder viewHolder = new DatosViewHolder(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    // Se informa al listener.
                    onItemClickListener.onItemClick(v,
                            (Datos) mDatos.get(viewHolder.getAdapterPosition()),
                            viewHolder.getAdapterPosition());
                }
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (mDatos.get(position).getType() == ListItem.TYPE_DATA) {
            ((DatosViewHolder) holder).onBind((Datos) mDatos.get(position));
        } else {
            ((TituloViewHolder) holder).onBind((Titulo) mDatos.get(position));
        }
    }



    // Contenedor de vistas para la vista correspondiente al elemento.
    static class DatosViewHolder extends RecyclerView.ViewHolder {

        // El contenedor de vistas para un elemento de la lista debe contener...
        private final TextView lblItem;

        public DatosViewHolder(View itemView) {
            super(itemView);
            lblItem = (TextView) itemView.findViewById(R.id.lblItem);

        }

        public void onBind(Datos datos) {
            lblItem.setText(datos.uno);
        }
    }
    // Contenedor de vistas para la vista correspondiente al elemento.
    static class TituloViewHolder extends RecyclerView.ViewHolder {

        // El contenedor de vistas para un elemento de la lista debe contener...
        private final TextView lblTitulo;


        public TituloViewHolder(View itemView) {
            super(itemView);
            // Se obtienen las subvistas de la vista correspondiente al elemento.
            lblTitulo = (TextView) itemView.findViewById(R.id.lblGrupo);
        }

        public void onBind(Titulo titulo) {
            lblTitulo.setText(titulo.titulo);
        }
    }

}
