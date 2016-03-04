package afens.trabajo2t.adaptadores;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.squareup.picasso.Picasso;

import java.util.List;

import afens.trabajo2t.R;
import afens.trabajo2t.modelos.Alumno;

/**
 * Created by Afens on 28/02/2016.
 */
public class AlumnosAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private final List<Alumno> mDatos;
    private OnItemClickListener onItemClickListener;
    private View emptyView;

    // Interfaz que debe implementar el listener para cuando se haga click sobre un elemento.
    public interface OnItemClickListener {
        void onItemClick(View view, Alumno alumno, int position);
    }

    public AlumnosAdapter(List<Alumno> mDatos) {
        this.mDatos = mDatos;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        //Se infla el item de alumno.
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alumno, parent, false);
        final RecyclerView.ViewHolder viewHolder = new AlumnoViewHolder(itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener != null)
                    onItemClickListener.onItemClick(v, mDatos.get(viewHolder.getAdapterPosition()), viewHolder.getAdapterPosition());
            }
        });
        //Se retorna el contenedor con la vista del item de alumno en su interior.
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((AlumnoViewHolder)holder).onBind(mDatos.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatos.size();
    }

    class AlumnoViewHolder extends RecyclerView.ViewHolder{

        private final ImageView imgAvatar;
        private final TextView lblNombre;

        public AlumnoViewHolder(View itemView) {
            super(itemView);
            imgAvatar = (ImageView) itemView.findViewById(R.id.imgAvatar);
            lblNombre = (TextView) itemView.findViewById(R.id.lblNombre);
        }
        public void onBind(Alumno alumno){
            lblNombre.setText(alumno.getNombre());
            TextDrawable.IBuilder mDrawableBuilder = TextDrawable.builder()
                    .beginConfig()
                    .width(100)
                    .height(100)
                    .toUpperCase()
                    .endConfig()
                    .round();
            Drawable drawable = mDrawableBuilder.build(alumno.getNombre().substring(0, 1), ColorGenerator.MATERIAL.getColor(alumno.getNombre()));

            //Si el alumno no contiene imagen o no la encuentra cargará su primera letras más un fondo de color
            if(alumno.getFoto().isEmpty())
                imgAvatar.setImageDrawable(drawable);
            else
                Picasso.with(itemView.getContext()).load(alumno.getFoto()).error(drawable).into(imgAvatar);

        }
    }

    public void addItem(Alumno alumno){
        notifyItemInserted(mDatos.size()-1);
        mDatos.add(alumno);
    }
    public void removeItem(int posicion){
        mDatos.remove(posicion);
        notifyItemRemoved(posicion);
        checkIfEmpty();
    }
    public void replaceAll(List<Alumno> listaAlumnos){
        mDatos.clear();
        mDatos.addAll(listaAlumnos);
        notifyDataSetChanged();
    }
    public List<Alumno> getAlumnos(){
        return mDatos;
    }


    // Establece el listener a informar cuando se hace click sobre un elemento de la lista.
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    private void checkIfEmpty() {
        if(emptyView != null)
            emptyView.setVisibility(getItemCount() > 0 ? View.GONE : View.VISIBLE);
    }
    // Establece la empty view para la lista.
    public void setEmptyView(View emptyView) {
        this.emptyView = emptyView;
        // Muestra la empty view si la lista está vacía.
        checkIfEmpty();
    }
}
