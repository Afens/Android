package afens.pr020popupmenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

/**
 * Created by Usuario on 06/11/2015.
 */
public class Adaptador extends ArrayAdapter<Contacto> {
    private final ArrayList<Contacto> datos;
    private final LayoutInflater mInflador;

    public Adaptador(Context context, ArrayList<Contacto> datos) {

        super(context, R.layout.targetas, datos);
        this.datos = datos;
        mInflador = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        // Si no se puede reciclar.
        if (convertView == null) {
            // Se obtiene la vista-fila inflando el layout.
            convertView = mInflador.inflate(R.layout.targetas, parent, false);
            // Se crea el contenedor de vistas para la vista-fila.
            holder = new ViewHolder(convertView);
            // Se almacena el contenedor en la vista.
            convertView.setTag(holder);
        }
        // Si se puede reciclar.
        else {
            // Se obtiene el contenedor de vistas desde la vista reciclada.
            holder = (ViewHolder) convertView.getTag();
        }
        // Se escriben los datos en las vistas del contenedor de vistas.
        onBindViewHolder(holder, position);
        // Se retorna la vista que representa el elemento.
        return convertView;
    }

    // Cuando se deben escribir los datos en la vista del elemento.
    private void onBindViewHolder(ViewHolder holder, int position) {
        Contacto contacto = datos.get(position);
        holder.lblNombre.setText(contacto.getNombre());
        holder.lblTelefono.setText(contacto.getTlf());
        holder.lblCurso.setText(contacto.getCurso());
        // Se crea un nuevo objeto listener para cuando se pulse en la
        // imagen, a cuyo construtor se le pasa el mAlumno del que se trata.
        holder.imgMnu.setOnClickListener(new imgPopMenuOnClickListener(
                datos.get(position)));

    }

    private static class ViewHolder {
        private final TextView lblNombre;
        private final TextView lblCurso;
        private final TextView lblTelefono;
        private final ImageView imgMnu;

        public ViewHolder(View itemView) {
            lblNombre = (TextView) itemView.findViewById(R.id.lblNombre);
            lblCurso = (TextView) itemView.findViewById(R.id.lblCurso);
            lblTelefono = (TextView) itemView.findViewById(R.id.lblTelefono);
            imgMnu = (ImageView) itemView.findViewById(R.id.imgMnu);
        }
    }

    // Clase listener para pulsación sobre el icono del PopupMenu.
    private class imgPopMenuOnClickListener implements OnClickListener {
        private final Contacto contacto;

        private imgPopMenuOnClickListener(Contacto contacto) {
            this.contacto = contacto;
        }

        // Cuando se hace click sobre el icono.
        public void onClick(View view) {
            PopupMenu popup = new PopupMenu(getContext(), view);
            MenuInflater inflador = popup.getMenuInflater();
            inflador.inflate(R.menu.main_menu, popup.getMenu());
            // Se crea el listener para cuando se pulse un ítem del menú, a cuyo
            // constructor se le pasa el mAlumno asociado.
            popup.setOnMenuItemClickListener(new PopupMenuOnMenuItemClickListener(contacto));
            popup.show();
        }
    }

    private class PopupMenuOnMenuItemClickListener implements OnMenuItemClickListener {
        private final Contacto contacto;

        private PopupMenuOnMenuItemClickListener(Contacto contacto) {
            this.contacto = contacto;
        }

        // Cuando se selecciona un ítem del PopupMenu.
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.mnuLlamar:
                    Toast.makeText(getContext(), getContext().getString(R.string.llamar) + " " +
                            contacto.getNombre(), Toast.LENGTH_LONG).show();
                    break;
                case R.id.mnuMensaje:
                    Toast.makeText(getContext(), getContext().getString(R.string.mensaje) +
                            " " + contacto.getNombre(), Toast.LENGTH_LONG).show();
                    break;
            }
            return true;
        }
    }
}
