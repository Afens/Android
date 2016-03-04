package afens.trabajo2t.adaptadores;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import afens.trabajo2t.R;
import afens.trabajo2t.modelos.Visita;

/**
 * Created by Afens on 28/02/2016.
 */
public abstract class GenericAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected List<Visita> mDatos;
    protected OnVisitaClickListener mOnVisitaClickListener;
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
        mDatos.remove(posicion);
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
        this.mOnVisitaClickListener = listener;
    }

    // Establece la empty view para la lista.
    public void setEmptyView(View emptyView) {
        this.emptyView = emptyView;
        // Muestra la empty view si  la lista está vacía.
        checkIfEmpty();
    }
}
