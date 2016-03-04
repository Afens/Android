package afens.pr2t.adaptadores;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import afens.pr2t.SQLite.DAO;
import afens.pr2t.modelos.Visita;


/**
 * Created by Afens on 28/02/2016.
 */
public abstract class GenericAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected List<Visita> mDatos;
    protected OnVisitaClickListener listener;
    private View emptyView;

    public interface OnVisitaClickListener{
        void onVisitaClick(View view, Visita visita, int position);
    }

    public GenericAdapter(List<Visita> mDatos) {
        this.mDatos = mDatos;
    }

    @Override
    public int getItemCount() {
        return mDatos.size();
    }


    //CONTROL del adaptador
    public void addItem(Visita visita){
        notifyItemInserted(mDatos.size()-1);
        mDatos.add(visita);
    }
    public void removeItem(int posicion){
        DAO.getInstance(null).deleteVisita(mDatos.remove(posicion).getId());
        notifyItemRemoved(posicion);
        checkIfEmpty();
    }
    public void replaceAll(List<Visita> listaVisitas){
        mDatos.clear();
        mDatos.addAll(listaVisitas);
        notifyDataSetChanged();
    }


    private void checkIfEmpty() {
        if(emptyView != null)
            emptyView.setVisibility(getItemCount() > 0 ? View.GONE : View.VISIBLE);
    }
    // Establece el listener a informar cuando se hace click sobre un elemento de la lista.
    public void setOnItemClickListener(OnVisitaClickListener listener) {
        this.listener = listener;
    }

    // Establece la empty view para la lista.
    public void setEmptyView(View emptyView) {
        this.emptyView = emptyView;
        // Muestra la empty view si  la lista está vacía.
        checkIfEmpty();
    }
}
