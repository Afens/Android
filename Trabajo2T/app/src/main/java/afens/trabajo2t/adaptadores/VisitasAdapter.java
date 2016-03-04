package afens.trabajo2t.adaptadores;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import afens.trabajo2t.R;
import afens.trabajo2t.modelos.Visita;

/**
 * Created by Afens on 28/02/2016.
 */
public class VisitasAdapter extends GenericAdapter {

    public VisitasAdapter(List<Visita> mDatos) {
        super(mDatos);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View visitasView;
        final RecyclerView.ViewHolder viewHolder;

        visitasView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_visita, parent, false);
        viewHolder = new VisitaViewHolder(visitasView);

        visitasView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnVisitaClickListener != null)
                    mOnVisitaClickListener.onVisitaClick(v, mDatos.get(viewHolder.getAdapterPosition()), viewHolder.getAdapterPosition());
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((VisitaViewHolder) holder).onBind(mDatos.get(position));
    }

    static class VisitaViewHolder extends RecyclerView.ViewHolder {

        private final TextView lblFecha;
        private final TextView lblHora;
        private final TextView lblComentario;

        public VisitaViewHolder(View itemView) {
            super(itemView);
            lblFecha = (TextView) itemView.findViewById(R.id.lblFecha);
            lblHora = (TextView) itemView.findViewById(R.id.lblHora);
            lblComentario = (TextView) itemView.findViewById(R.id.lblComentario);
        }

        public void onBind(Visita visita) {
            SimpleDateFormat formatHora = new SimpleDateFormat("HH:mm");
            Date dia = visita.getDia();
            lblFecha.setText(new SimpleDateFormat("dd/MM/yyyy").format(dia));
            lblHora.setText(formatHora.format(dia) + "-" + formatHora.format(new Date(dia.getTime() + 3600000)));
            lblComentario.setText(visita.getComentario());
        }
    }
}
