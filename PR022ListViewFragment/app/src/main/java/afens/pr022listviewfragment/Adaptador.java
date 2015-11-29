package afens.pr022listviewfragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Afens on 29/11/2015.
 */
public class Adaptador extends ArrayAdapter<Contacto> {
    private final ArrayList<Contacto> datos;

    public Adaptador(Context context, ArrayList<Contacto> datos) {
        super(context, R.layout.item, datos);
        this.datos = datos;

    }

    // Clase estática contenedora de vistas de fila
    private static class ViewHolder {
        private final TextView lblNombre;
        private final TextView lblEdad;
        private final TextView lblLocalidad;
        private final ImageView imgAvatar;

        public ViewHolder(View itemView) {
            lblNombre = (TextView) itemView.findViewById(R.id.lblNombre);
            lblEdad = (TextView) itemView.findViewById(R.id.lblEdad);
            lblLocalidad = (TextView) itemView.findViewById(R.id.lblLocalidad);
            imgAvatar = (ImageView) itemView.findViewById(R.id.imgAvatar);
        }
    }

    // Método que construye y retorna la vista a usar para mostrar un
    // elemento de la lista.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Contenedor de las vistas que conforman la vista-fila.
        ViewHolder holder;
        // Si no hay vista-fila para reciclar, se infla el layut.
        if (convertView == null) {
            // Se obtiene un inflador de vista que se usa para inflar la
            // especificación XML del layout
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item, parent, false);
            // Se crea un nuevo objeto contenedor de las vistas de la vista-fila.
            holder = new ViewHolder(convertView);
            // Se almacena el contenedor en la propiedad Tag de la vista-fila.
            convertView.setTag(holder);
        }
        // Si la vista-fila corresponde a un objeto reciclable.
        else {
            // Se obtienen el objeto contenedor desde la propiedad Tag de la vista-fila.
            holder = (ViewHolder) convertView.getTag();
        }
        // Se "escriben" los datos en dichas vistas. Para obtener el dato
        // concreto se utiliza el parámetro position que actúa como índice del
        // array de datos gestionados por el adaptador.
        onBindViewHolder(holder, position);
        // Se retorna la vista-fila.
        return convertView;
        }

    private void onBindViewHolder(ViewHolder holder, int position) {
        Contacto contacto=datos.get(position);
        holder.lblNombre.setText(contacto.getNombre());
        holder.lblEdad.setText(String.format(getContext().getString(R.string.a), contacto.getEdad()));
        holder.lblLocalidad.setText(contacto.getLocalidad());
        Picasso.with(getContext().getApplicationContext()).load(contacto.getFoto()).resize(50, 50).into(holder.imgAvatar);

    }
}
