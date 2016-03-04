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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import afens.trabajo2t.R;
import afens.trabajo2t.SQLite.DAO;
import afens.trabajo2t.modelos.Alumno;
import afens.trabajo2t.modelos.Visita;

/**
 * Created by Afens on 28/02/2016.
 */
public class ProximasAdapter extends GenericAdapter {
    public ProximasAdapter(List<Visita> mDatos) {
        super(mDatos);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View visitaView;
        final RecyclerView.ViewHolder viewHolder;

        visitaView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_visitas_proximas, parent, false);
        viewHolder = new ProximasViewHolder(visitaView);

        visitaView.setOnClickListener(new View.OnClickListener() {
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
        ((ProximasViewHolder) holder).onBind(mDatos.get(position));
    }

    class ProximasViewHolder extends RecyclerView.ViewHolder {

        private final TextView lblNombre;
        private final TextView lblDia;
        private final TextView lblHora;
        private final ImageView imgAvatar;

        public ProximasViewHolder(View itemView) {
            super(itemView);
            lblDia = (TextView) itemView.findViewById(R.id.lblDia);
            lblHora = (TextView) itemView.findViewById(R.id.lblHora);
            lblNombre = (TextView) itemView.findViewById(R.id.lblNombre);
            imgAvatar = (ImageView) itemView.findViewById(R.id.imgAvatar);
        }

        public void onBind(Visita visita) {
            SimpleDateFormat formatHora = new SimpleDateFormat("HH:mm", Locale.getDefault());
            Date dia = visita.getDia();
            lblDia.setText(new SimpleDateFormat("dd/MM/yy", Locale.getDefault()).format(visita.getDia()));
            lblHora.setText(formatHora.format(dia) + "-" + formatHora.format(new Date(dia.getTime() + 3600000)));
            //Se obtiene el alumno dueño de la visita.
            Alumno alumno = DAO.getInstance(itemView.getContext()).getAlumno(visita.getIdAlumno());
            lblNombre.setText(alumno.getNombre());
            //Si el alumno no contiene imagen o no la encuentra cargará su primera letras más un fondo de color
            Drawable drawable = TextDrawable.builder()
                    .beginConfig()
                    .width(100)
                    .height(100)
                    .toUpperCase()
                    .endConfig()
                    .rect().build(alumno.getNombre().substring(0, 1), ColorGenerator.MATERIAL.getColor(alumno.getNombre()));
            if (alumno.getFoto().isEmpty())
                imgAvatar.setImageDrawable(drawable);
            else
                Picasso.with(itemView.getContext()).load(alumno.getFoto()).error(drawable).into(imgAvatar);

        }
    }
}
