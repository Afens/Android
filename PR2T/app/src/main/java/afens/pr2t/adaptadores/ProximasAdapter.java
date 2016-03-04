package afens.pr2t.adaptadores;


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

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import afens.pr2t.R;
import afens.pr2t.SQLite.DAO;
import afens.pr2t.modelos.Alumno;
import afens.pr2t.modelos.Visita;


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
                if (listener != null) {
                    Visita visita = mDatos.get(viewHolder.getAdapterPosition());
                    visita.setId(0);
                    listener.onVisitaClick(v, visita, viewHolder.getAdapterPosition());
                }
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
        private final ImageView imgAvatar;

        public ProximasViewHolder(View itemView) {
            super(itemView);
            lblDia = (TextView) itemView.findViewById(R.id.lblDia);
            lblNombre = (TextView) itemView.findViewById(R.id.lblNombre);
            imgAvatar = (ImageView) itemView.findViewById(R.id.imgAvatar);
        }

        public void onBind(Visita visita) {
            SimpleDateFormat formatHora = new SimpleDateFormat("HH:mm", Locale.getDefault());
            Date previsto = new Date(visita.getInicio().getTime() + TimeUnit.DAYS.toMillis(15));
            //Si existe esta visita
            if (visita.getId() != 0) {
                //Aparecerá como fecha
                lblDia.setText(new SimpleDateFormat("dd/MM/yy", Locale.getDefault()).format(previsto));
                //Si el alumno no ha realizado ninguna visita, le aparecerá un mensaje al usuario informandole de que debe ir cuanto antes.
            } else
                lblDia.setText(R.string.cuantoAntes);

            visita.setInicio(new Date());

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
                Picasso.with(itemView.getContext()).load(new File(alumno.getFoto())).error(drawable).into(imgAvatar);

        }
    }
}
